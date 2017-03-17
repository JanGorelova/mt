package controllers;

import dao.DaoFactory;
import model.Countries;
import model.Instrument;
import model.User;
import services.AddNewInstruments;
import services.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by iMac on 16/03/17.
 */
@WebServlet("/EditUser")
public class EditUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Object obj = session.getAttribute("User");

        if (obj != null) {
            User user = (User) obj;
            String localeString = (String) session.getAttribute("locale");
            if (localeString == null) {
                localeString = "en";
            }
            ResourceBundle bundle = ResourceBundle.getBundle("register", new Locale(localeString));
            DaoFactory daoFactory = (DaoFactory) getServletContext().getAttribute("daoFactory");
            List<Instrument> instrumentList = (List<Instrument>) session.getAttribute("Instruments");

            if (Validator.validateUpdatedProfile(request, bundle, daoFactory, user)) {
                request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
                return;
            } else {
                String login = request.getParameter("login").trim();
                String firstName = request.getParameter("firstName").trim();
                String lastName = request.getParameter("lastName").trim();
                Countries country = Countries.valueOf(request.getParameter("country"));
                String instrument = request.getParameter("instrument");
                String password = request.getParameter("password").trim();
                user.setLogin(login);
                if (!password.isEmpty()) {
                    user.setPassword(password);
                }
                user.setCountry(country);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                daoFactory.getUserDao().updateUser(user);


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
