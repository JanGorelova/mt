package controllers;

import dao.DaoFactory;
import model.Subscription;
import model.Tweet;
import model.User;
import services.ProcessTweets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by iMac on 12/03/17.
 */
@WebServlet("/GetInstrumentTweets")
public class GetInstrumentTweets extends HttpServlet {

    private DaoFactory daoFactory;

    @Override
    public void init() throws ServletException {
        daoFactory = (DaoFactory) getServletContext().getAttribute("daoFactory");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        try (Writer out = response.getWriter()) {
            HttpSession session = request.getSession();

            User user = (User) session.getAttribute("User");
            List<Subscription> subscriptions = (List<Subscription>) session.getAttribute("Subscriptions");

            // Pagination
            String pageCountString = request.getParameter("pageCount");
            String resetLimit = request.getParameter("resetLimit");
            int limit = 20;
            int offset = 0;
            if (pageCountString != null && !pageCountString.isEmpty()) {
                int pageCount = Integer.parseInt(pageCountString);
                offset = pageCount * limit;
            }
            if (resetLimit != null) {
                limit = offset;
                offset = 0;
            }

            Locale locale = (Locale) session.getAttribute("locale");
            ResourceBundle bundle = ResourceBundle.getBundle("main", locale);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withLocale(locale);

            if (daoFactory != null && user != null) {
                List<Tweet> tweets = daoFactory.getMessageDao().getInstrumentMessages(user.getUserId(), limit, offset);
                out.write(ProcessTweets.process(tweets, subscriptions, bundle, formatter));
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
