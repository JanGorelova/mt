package services;

import model.Subscription;
import model.Tweet;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Service class with static method for processing tweets.
 */
public class ProcessTweets {

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

    /**
     * Static method that generates a part of html page from List of tweets.
     *
     * @param tweets        List of Tweets
     * @param subscriptions String with subscriptions of the author
     * @param bundle        ResourceBundle with localized content
     * @param formatter     date formatter
     * @return html as String
     */
    public static String process(List<Tweet> tweets, List<Subscription> subscriptions, ResourceBundle bundle, DateTimeFormatter formatter) {
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

            // Userpic section
            out.append("<div class=\"singleTweet\" id=\"").append(tweet.getMessageId()).append("\">");
            out.append(" <div class=\"tweetPic\">");
            out.append("     <img src=\"/img/tweet_icon.png\" />");
            out.append(" </div>");

            // Main content
            out.append(" <div class=\"tweetContent\">");

            //User id
            out.append("     <span class=\"tweetUser\" id=\"").append(tweet.getUserId())
                    .append("\">").append(tweet.getLogin()).append("</span>");

            // Instruments
            out.append("     <span class=\"tweetInstrument\">").append(tweet.getInstruments()).append("</span>");

            // Date
            out.append("     <span class=\"tweetDate\"> - ").append(tweet.getMessageDate().format(formatter))
                    .append("</span><br>");

            // Tweet text
            out.append("     <span class=\"tweetText\">").append(tweet.getMessageText()).append("</span><br>");

            // Like link
            out.append("     <span class=\"tweetLike\"><a href=\"javascript:likePressed(")
                    .append(tweet.getMessageId()).append(")\">").append(bundle.getString("like")).append("</a> (")
                    .append(tweet.getLikes()).append("), ");

            // Subscribe or unsubscribe link
            out.append("<a href=\"javascript:").append(functionName).append("(").append(tweet.getUserId()).append(")\">").append(subscribe)
                    .append("</a></span>");

            // The rest
            out.append(" </div>");
            out.append("</div><div class=\"clear\"><hr></div>");
        }
        // Add "next" link if needed
        if (tweets.size() / 20 > 0) {
            out.append("  <span><a id=\"next\" onclick=\"getNextTweets()\">")
                    .append(bundle.getString("next"))
                    .append("</span>");
        }
        return out.toString();
    }
}
