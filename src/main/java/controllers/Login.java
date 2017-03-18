package controllers;

import dao.DaoFactory;
import model.Instrument;
import model.Subscription;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.HashGenerator;

import javax.servlet.ServletContext;
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

    private DaoFactory daoFactory;
    private HashGenerator hashGenerator;
    private static final Logger log = LoggerFactory.getLogger(Login.class);

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        daoFactory = (DaoFactory) context.getAttribute("daoFactory");
        hashGenerator = (HashGenerator) context.getAttribute("hashGenerator");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute("locale");
        if (locale == null) {
            locale = new Locale("en");
        }
        ResourceBundle bundle = ResourceBundle.getBundle("login", locale);

        String login = request.getParameter("login").trim();
        String password = request.getParameter("password").trim();

        if (!login.isEmpty() && !password.isEmpty()) {
            if (login.length() > 0 && login.length() <= 32) {
                if (password.length() > 0 && password.length() <= 256) {
                    User user = daoFactory.getUserDao().readUserByLogin(login);
                    if (user != null) {
                        if (user.getPassword().equals(hashGenerator.getHash(password))) {
                            List<Instrument> instruments = daoFactory.getInstrumentDao().getUserInstruments(user.getUserId());
                            List<Subscription> subscriptions = daoFactory.getSubscriptionDao().getUserSubscriptions(user.getUserId());
                            session.setAttribute("User", user);
                            session.setAttribute("Instruments", instruments);
                            session.setAttribute("Subscriptions", subscriptions);
                            request.removeAttribute("loginError");
                            log.info("User login: " + user.getLogin());
                            request.getRequestDispatcher("/WEB-INF/main.jsp").forward(request, response);
                            return;
                        }
                    }
                }
            }
        }
        request.setAttribute("loginError", bundle.getString("loginError"));
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
