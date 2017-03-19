package controllers;

import dao.DaoFactory;
import model.Countries;
import model.User;
import services.AddNewInstruments;
import services.HashGenerator;
import services.Validator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Servlet for processing, validating and updating the edited user's information from My Profile page.
 */
@WebServlet("/EditUser")
public class EditUser extends HttpServlet {

    private DaoFactory daoFactory;
    private HashGenerator hashGenerator;

    /**
     * Method gets common Dao Factory and Hash Generator from servlet context.
     * @throws ServletException - standard Servlet exception
     */
    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        daoFactory = (DaoFactory) context.getAttribute("daoFactory");
        hashGenerator = (HashGenerator) context.getAttribute("hashGenerator");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Object obj = session.getAttribute("User");

        if (obj != null) {
            User user = (User) obj;
            Locale locale = (Locale) session.getAttribute("locale");
            ResourceBundle bundle = ResourceBundle.getBundle("register", locale);

            // If validation failed, get back to the profile settings page and show errors
            if (Validator.validateUpdatedProfile(request, bundle, daoFactory, user)) {
                request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
                return;

                // If validation is OK, process and update new info to the database
            } else {
                String login = request.getParameter("login").trim();
                String firstName = request.getParameter("firstName").trim();
                String lastName = request.getParameter("lastName").trim();
                Countries country = Countries.valueOf(request.getParameter("country"));
                String instrument = request.getParameter("instrument");
                String password = request.getParameter("password").trim();
                user.setLogin(login);
                if (!password.isEmpty()) {
                    user.setPassword(hashGenerator.getHash(password));
                }
                user.setCountry(country);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setUserId(daoFactory.getUserDao().updateUser(user));

                // If login has been used before validation and creating
                if (user.getUserId() == 0) {
                    request.setAttribute("loginExists", bundle.getString("loginExists"));
                    request.getRequestDispatcher("/profile.jsp").forward(request, response);
                    return;
                }

                try {
                    AddNewInstruments.addNewInstruments(instrument, daoFactory, session, user);
                } catch (SQLException e) {
                    request.setAttribute("invalidInstrument", bundle.getString("invalidInstrument"));
                    request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
                    return;
                }
            }
            response.sendRedirect("/");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
