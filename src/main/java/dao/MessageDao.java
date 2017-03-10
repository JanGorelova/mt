package dao;

import model.Message;
import model.Tweet;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Air on 25/02/2017.
 */
public interface MessageDao {

    long createMessage(Message message);

    Message readMessage(long messageId);

    void updateMessage(Message message) throws SQLException;

    void deleteMessage(long messageId) throws SQLException;

    List<Tweet> getUserMessages(long userId);

    List<Tweet> getSubscriptionMessages(long userId);

    List<Tweet> getInstrumentMessages(long userId);

    List<Tweet> getCountryMessages(long userId);

}
