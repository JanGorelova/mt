package controllers;

import dao.DaoFactory;
import model.Countries;
import model.Instrument;
import model.Subscription;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private DaoFactory daoFactory;
    static final Logger log = LoggerFactory.getLogger(RegisterUser.class);

    @Override
    public void init() throws ServletException {
        daoFactory = (DaoFactory) getServletContext().getAttribute("daoFactory");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute("locale");
        if (locale == null) {
            locale = new Locale("en");
        }
        ResourceBundle bundle = ResourceBundle.getBundle("register", locale);

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
                } catch (SQLException e) {
                    request.setAttribute("invalidInstrument", bundle.getString("invalidInstrument"));
                    request.getRequestDispatcher("/register.jsp").forward(request, response);
                    return;
                }
            } else {
                session.setAttribute("Instruments", new ArrayList<Instrument>());
            }
            session.setAttribute("Subscriptions", new ArrayList<Subscription>());
            session.setAttribute("User", user);
            log.info("User registered: " + user.getLogin());
            request.getRequestDispatcher("/WEB-INF/main.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
