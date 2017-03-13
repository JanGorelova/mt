package controllers;

import dao.DaoFactory;
import model.Countries;
import model.Instrument;
import model.Subscription;
import model.User;

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
 * Created by iMac on 06/03/17.
 */
@WebServlet("/registerUser")
public class RegisterUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        request.setCharacterEncoding("utf-8");
        String localeString = (String) session.getAttribute("locale");
        if (localeString == null) {
            localeString = "en";
        }
        Locale locale = new Locale(localeString);
        ResourceBundle bundle = ResourceBundle.getBundle("register", locale);
        DaoFactory daoFactory = (DaoFactory) getServletContext().getAttribute("daoFactory");
        boolean errorFlag = false;

        String login = request.getParameter("login").trim();
        String firstName = request.getParameter("firstName").trim();
        String lastName = request.getParameter("lastName").trim();
        Countries country = Countries.valueOf(request.getParameter("country"));
        String instrument = request.getParameter("instrument").trim().toLowerCase();
        String password = request.getParameter("password").trim();
        String passwordRepeat = request.getParameter("passwordRepeat").trim();

        // Validation
        if (login == null || login.isEmpty() || login.length() > 15) {
            errorFlag = true;
            request.setAttribute("invalidLogin", bundle.getString("invalidLogin"));
        }
        if (password == null || password.isEmpty() || password.length() > 256) {
            errorFlag = true;
            request.setAttribute("invalidPassword", bundle.getString("invalidPassword"));
        }
        if (passwordRepeat == null) {
            errorFlag = true;
            request.setAttribute("invalidPasswordRepeat", bundle.getString("invalidPasswordRepeat"));
        } else if (!password.equals(passwordRepeat)) {
            errorFlag = true;
            request.setAttribute("invalidPasswordRepeat", bundle.getString("invalidPasswordRepeat"));
        }
        if (instrument != null && instrument.length() > 256) {
            errorFlag = true;
            request.setAttribute("invalidInstrument", bundle.getString("invalidInstrument"));
        }
        if (firstName != null && firstName.length() > 32) {
            errorFlag = true;
            request.setAttribute("invalidFirstname", bundle.getString("invalidFirstname"));
        }
        if (lastName != null && lastName.length() > 32) {
            errorFlag = true;
            request.setAttribute("invalidLastname", bundle.getString("invalidLastname"));
        }
        if (daoFactory.getUserDao().readUserByLogin(login) != null) {
            errorFlag = true;
            request.setAttribute("loginExists", bundle.getString("loginExists"));
        }
        if (errorFlag) {
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user.setCountry(country);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUserId(daoFactory.getUserDao().createUser(user));
            if (instrument != null) {
                try {
                    AddNewInstruments.addNewInstruments(instrument, daoFactory, session, user);
                    session.setAttribute("Subscriptions", new ArrayList<Subscription>());
                } catch (SQLException e) {
                    request.setAttribute("invalidInstrument", bundle.getString("invalidInstrument"));
                    request.getRequestDispatcher("/register.jsp").forward(request, response);
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
