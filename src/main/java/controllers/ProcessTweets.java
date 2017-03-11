package controllers;

import model.Tweet;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Air on 11/03/2017.
 */
public class ProcessTweets {

    public static String process(List<Tweet> tweets, ResourceBundle bundle, DateTimeFormatter formatter) {
        StringBuilder out = new StringBuilder();
        for (Tweet tweet : tweets) {
            out.append("<div class=\"singleTweet\" id=\"").append(tweet.getMessageId()).append("\">");
            out.append(" <div class=\"tweetPic\">");
            out.append("     <img src=\"/img/tweet_icon.png\" />");
            out.append(" </div>");

            out.append(" <div class=\"tweetContent\">");
            out.append("     <span class=\"tweetUser\" id=\"").append(tweet.getUserId()).append("\">").append(tweet.getLogin()).append("</span>");
            out.append("     <span class=\"tweetInstrument\">").append(tweet.getInstruments()).append("</span>");
            out.append("     <span class=\"tweetDate\"> - ").append(tweet.getMessageDate().format(formatter)).append("</span><br>");
            out.append("     <span class=\"tweetText\">").append(tweet.getMessageText()).append("</span><br>");
            out.append("     <span class=\"tweetLike\"><a href=\"javascript:likePressed(")
                    .append(tweet.getMessageId()).append(")\">").append(bundle.getString("like")).append("</a> (")
                    .append(tweet.getLikes()).append("), <a href=\"#\">").append(bundle.getString("subscribe"))
                    .append("</a></span>");
            out.append(" </div>");
            out.append("</div><div class=\"clear\"><hr></div>");
        }
        return out.toString();
    }
}
