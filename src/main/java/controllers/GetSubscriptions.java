package controllers;

import dao.DaoFactory;
import model.Tweet;
import model.User;

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
 * Created by iMac on 10/03/17.
 */
@WebServlet("/GetSubscriptions")
public class GetSubscriptions extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        Writer out = response.getWriter();
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("User");
        String localeString = (String) session.getAttribute("locale");
        Locale locale = new Locale(localeString);
        ResourceBundle bundle = ResourceBundle.getBundle("main", locale);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm").withLocale(locale);

        DaoFactory daoFactory = (DaoFactory) getServletContext().getAttribute("daoFactory");
        if (daoFactory != null && user != null) {
            List<Tweet> tweets = daoFactory.getMessageDao().getSubscriptionMessages(user.getUserId());
            out.write(ProcessTweets.process(tweets, bundle, formatter));
        }
    }

//            <div class="singleTweet">
//            <div class="tweetPic">
//                <img src="<c:url value="/img/tweet_icon.png"/>">
//            </div>
//            <div class="tweetContent">
//                <span class="tweetUser">Jagger</span>
//                <span class="tweetInstrument">vocals, guitar</span>
//                <span class="tweetDate"> - 01.01.2017 12:22</span><br>
//                <span class="tweetText">This is just a text... I can't get no satisfaction!</span><br>
//                <span class="tweetLike"><a href="#">${like}</a> (34), <a href="#">${subscribe}</a></span>
//            </div>
//        </div>
//        <div class="clear">
//            <hr>
//        </div>
//

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
