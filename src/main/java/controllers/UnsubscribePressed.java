package controllers;

import dao.DaoFactory;
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

/**
 * Servlet handles the pressed Unsubscribe link and sends the data to the database
 */
@WebServlet("/UnsubscribePressed")
public class UnsubscribePressed extends HttpServlet {

    private DaoFactory daoFactory;

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

        // Proceed only if the user is authorized and user id to unsubscribe is correct
        if (user != null && !subscribedUserId.isEmpty()) {
            long subscribedUserIdLong = Long.parseLong(subscribedUserId);
            daoFactory.getSubscriptionDao().deleteSubscription(new Subscription(0, user.getUserId(), subscribedUserIdLong));
            for (Subscription subscription: subscriptions) {
                if (subscription.getUserId() == user.getUserId()
                        && subscription.getSubscriptedUserId() == subscribedUserIdLong) {
                    subscriptions.remove(subscription);
                    break;
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
