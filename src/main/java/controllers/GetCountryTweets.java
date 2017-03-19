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
 * Servlet requests all tweets from the country of the current user and returns them as a part of a web page.
 */
@WebServlet("/GetCountryTweets")
public class GetCountryTweets extends HttpServlet {

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

        // Do something only if the user logged in
        if (user != null) {
            response.setContentType("text/html; charset=UTF-8");
            try (Writer out = response.getWriter()) {

                @SuppressWarnings("unchecked")
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

                // Sends the query to the database, generates html and sends it to response
                if (daoFactory != null) {
                    List<Tweet> tweets = daoFactory.getMessageDao().getCountryMessages(user.getUserId(), limit, offset);
                    out.write(ProcessTweets.process(tweets, subscriptions, bundle, formatter));
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
