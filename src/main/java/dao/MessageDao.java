package dao;

import model.Message;
import model.Tweet;
import model.User;

import java.util.List;

/**
 * Interface which defines all necessary methods for operations with Message and Tweet objects
 */
public interface MessageDao {

    /**
     * Creates new message in the database
     * @param message message to create
     * @return auto generated id of the new message
     */
    long createMessage(Message message);

    /**
     * Gets all tweets by the specified user ordered by date, newest first.
     * @param user User
     * @param instruments instruments string to be added to tweet
     * @param limit how many tweets to get
     * @param offset from what position
     * @return ArrayList of Tweets
     */
    List<Tweet> getUserMessages(User user, String instruments, int limit, int offset);

    /**
     * Get tweets from users, who are in the subscription list of the specified User,
     * ordered by date, newest first.
     * @param userId User's id
     * @param limit how many tweets to get
     * @param offset from what position
     * @return ArrayList of Tweets
     */
    List<Tweet> getSubscriptionMessages(long userId, int limit, int offset);

    /**
     * Gets tweets from users, who have the same instruments, ordered by date, newest first.
     * @param userId User's id
     * @param limit how many tweets to get
     * @param offset from what position
     * @return ArrayList of Tweets
     */
    List<Tweet> getInstrumentMessages(long userId, int limit, int offset);

    /**
     * Gets tweets from the same country as User, ordered by date, newest first.
     * @param userId User's id
     * @param limit how many tweets to get
     * @param offset from what position
     * @return ArrayList of Tweets
     */
    List<Tweet> getCountryMessages(long userId, int limit, int offset);

}
