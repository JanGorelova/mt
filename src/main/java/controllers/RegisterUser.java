package controllers;

import dao.DaoFactory;
import model.Countries;
import model.Subscription;
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
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by iMac on 06/03/17.
 */
@WebServlet("/registerUser")
public class RegisterUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String localeString = (String) session.getAttribute("locale");
        if (localeString == null) {
            localeString = "en";
        }
        Locale locale = new Locale(localeString);
        ResourceBundle bundle = ResourceBundle.getBundle("register", locale);
        DaoFactory daoFactory = (DaoFactory) getServletContext().getAttribute("daoFactory");

        if (Validator.validateNewUser(request, bundle, daoFactory)) {
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            String login = request.getParameter("login").trim();
            String firstName = request.getParameter("firstName").trim();
            String lastName = request.getParameter("lastName").trim();
            Countries country = Countries.valueOf(request.getParameter("country"));
            String instrument = request.getParameter("instrument").trim().toLowerCase();
            String password = request.getParameter("password").trim();
            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user.setCountry(country);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUserId(daoFactory.getUserDao().createUser(user));
            if (!instrument.isEmpty()) {
                try {
                    AddNewInstruments.addNewInstruments(instrument, daoFactory, session, user);
                    session.setAttribute("Subscriptions", new ArrayList<Subscription>());
                } catch (SQLException e) {
                    request.setAttribute("invalidInstrument", bundle.getString("invalidInstrument"));
                    request.getRequestDispatcher("/register.jsp").forward(request, response);
                    return;
                }
            }
            session.setAttribute("User", user);
            request.getRequestDispatcher("/WEB-INF/main.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
