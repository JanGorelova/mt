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
 * Created by iMac on 13/03/17.
 */
@WebServlet("/UnsubscribePressed")
public class UnsubscribePressed extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DaoFactory daoFactory = (DaoFactory) getServletContext().getAttribute("daoFactory");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");
        List<Subscription> subscriptions = (List<Subscription>) session.getAttribute("Subscriptions");
        String subscriptedUserId = request.getParameter("userId");

        if (user != null && !subscriptedUserId.isEmpty()) {
            long subscriptedUserIdLong = Long.parseLong(subscriptedUserId);
            daoFactory.getSubscriptionDao().deleteSubscription(new Subscription(0, user.getUserId(), subscriptedUserIdLong));
            for (Subscription subscription: subscriptions) {
                if (subscription.getUserId() == user.getUserId()
                        && subscription.getSubscriptedUserId() == subscriptedUserIdLong) {
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
