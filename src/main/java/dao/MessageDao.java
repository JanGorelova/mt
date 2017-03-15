package dao;

import model.Message;
import model.Tweet;
import model.User;

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

    List<Tweet> getUserMessages(User user, String instruments, int limit, int offset);

    List<Tweet> getSubscriptionMessages(long userId, int limit, int offset);

    List<Tweet> getInstrumentMessages(long userId, int limit, int offset);

    List<Tweet> getCountryMessages(long userId, int limit, int offset);

}
