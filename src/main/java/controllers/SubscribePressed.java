package controllers;

import dao.DaoFactory;
import model.Subscription;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet handles the pressed Subscribe link and sends the data to the database
 */
@WebServlet("/SubscribePressed")
public class SubscribePressed extends HttpServlet {

    private DaoFactory daoFactory;
    private static final Logger log = LoggerFactory.getLogger(Login.class);

    /**
     * Method gets the common Dao Factory from servlet context.
     *
     * @throws ServletException - standard Servlet exception
     */
    @Override
    public void init() throws ServletException {
        daoFactory = (DaoFactory) getServletContext().getAttribute("daoFactory");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");

        @SuppressWarnings("unchecked")
        List<Subscription> subscriptions = (List<Subscription>) session.getAttribute("Subscriptions");
        String subscribedUserId = request.getParameter("userId");

        // Proceed only if the user is authorized and user id to subscribe is correct
        if (user != null && !subscribedUserId.isEmpty()) {
            try {
                Subscription subscription = new Subscription(0, user.getUserId(), Long.parseLong(subscribedUserId));
                subscription.setSubscriptionId(daoFactory.getSubscriptionDao().createSubscription(subscription));
                subscriptions.add(subscription);
            } catch (NumberFormatException e) {
                log.warn("Incorrect user id for subscription.");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
