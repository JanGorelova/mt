package controllers;

import model.Subscription;
import model.Tweet;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Air on 11/03/2017.
 */
class ProcessTweets {

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

    static String process(List<Tweet> tweets, List<Subscription> subscriptions, ResourceBundle bundle, DateTimeFormatter formatter) {
        StringBuilder out = new StringBuilder();
        String subscribe;
        String functionName;
        for (Tweet tweet : tweets) {
            subscribe = bundle.getString("subscribe");
            functionName = "subscribe";
            for (Subscription subscription : subscriptions) {
                if (subscription.getSubscriptedUserId() == tweet.getUserId()) {
                    subscribe = bundle.getString("unsubscribe");
                    functionName = "unsubscribe";
                    break;
                }
            }
            out.append("<div class=\"singleTweet\" id=\"").append(tweet.getMessageId()).append("\">");
            out.append(" <div class=\"tweetPic\">");
            out.append("     <img src=\"/img/tweet_icon.png\" />");
            out.append(" </div>");

            out.append(" <div class=\"tweetContent\">");
            out.append("     <span class=\"tweetUser\" id=\"").append(tweet.getUserId()).append("\">")
                    .append(tweet.getLogin()).append("</span>");
            out.append("     <span class=\"tweetInstrument\">").append(tweet.getInstruments()).append("</span>");
            out.append("     <span class=\"tweetDate\"> - ").append(tweet.getMessageDate().format(formatter)).append("</span><br>");
            out.append("     <span class=\"tweetText\">").append(tweet.getMessageText()).append("</span><br>");
            out.append("     <span class=\"tweetLike\"><a href=\"javascript:likePressed(")
                    .append(tweet.getMessageId()).append(")\">").append(bundle.getString("like")).append("</a> (")
                    .append(tweet.getLikes()).append("), ")
                    .append("<a href=\"javascript:").append(functionName).append("(").append(tweet.getUserId()).append(")\">").append(subscribe)
                    .append("</a></span>");
            out.append(" </div>");
            out.append("</div><div class=\"clear\"><hr></div>");
        }
        if (tweets.size() / 20 > 0) {
            out.append("  <span><a id=\"next\" onclick=\"getNextTweets()\">")
                    .append(bundle.getString("next"))
                    .append("</span>");
        }
        return out.toString();
    }
}
