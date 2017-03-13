package controllers;

import dao.DaoFactory;
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
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Air on 26/02/2017.
 */
@WebServlet("/login")
public class Login extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        request.setCharacterEncoding("utf-8");
        String localeString = (String) session.getAttribute("locale");
        if (localeString == null || localeString.isEmpty()) {
            localeString = "en";
        }
        Locale locale = new Locale(localeString);
        ResourceBundle bundle = ResourceBundle.getBundle("login", locale);

        DaoFactory daoFactory = (DaoFactory) getServletContext().getAttribute("daoFactory");
        String login = request.getParameter("login").trim();
        String password = request.getParameter("password").trim();

        if (!login.isEmpty() && !password.isEmpty()) {
            if (login.length() > 0 && login.length() <= 32) {
                if (password.length() > 0 && password.length() <= 256) {
                    User user = daoFactory.getUserDao().readUserByLogin(login);
                    if (user != null) {
                        if (user.getPassword().equals(password)) {
                            List<Instrument> instruments = daoFactory.getInstrumentDao().getUserInstruments(user.getUserId());
                            List<Subscription> subscriptions = daoFactory.getSubscriptionDao().getUserSubscriptions(user.getUserId());
                            session.setAttribute("User", user);
                            session.setAttribute("Instruments", instruments);
                            session.setAttribute("Subscriptions", subscriptions);
                            request.removeAttribute("loginError");
                            request.getRequestDispatcher("/WEB-INF/main.jsp").forward(request, response);
                        }
                    }
                }
            }
        } else {
            request.setAttribute("loginError", bundle.getString("loginError"));
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
