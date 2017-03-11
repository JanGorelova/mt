package dao.h2;

import dao.MessageDao;
import model.Message;
import model.Tweet;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iMac on 10/03/17.
 */
public class H2MessageDao implements MessageDao {

    private DataSource dataSource;

    private final String CREATE_MESSAGE_SQL =
            "INSERT INTO Messages (user_id, message_date, message_text) VALUES (?, ?, ?);";

    private final String GET_SUBSCRIPTION_MESSAGES_SQL =
            "SELECT m.message_id, m.user_id, m.message_date, m.message_text, u.login, " +
                    "(SELECT COUNT(like_id) FROM Likes WHERE Likes.message_id = m.message_id) AS like_count " +
                    "FROM Messages AS m " +
                    "INNER JOIN Subscriptions AS s " +
                    "ON (s.subscripted_user_id = m.user_id) " +
                    "INNER JOIN Users AS u " +
                    "ON (m.user_id = u.user_id) " +
                    "WHERE s.user_id = ? ORDER BY m.message_date DESC;";

    public H2MessageDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public long createMessage(Message message) {
        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(CREATE_MESSAGE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, message.getUserId());
            statement.setTimestamp(2, Timestamp.valueOf(message.getMessageDate()));
            statement.setString(3, message.getMessageText());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("createMessage() - " + e.getMessage());
        }
        return 0;
    }

    @Override
    public Message readMessage(long messageId) {
        return null;
    }

    @Override
    public void updateMessage(Message message) throws SQLException {

    }

    @Override
    public void deleteMessage(long messageId) throws SQLException {

    }

    @Override
    public List<Tweet> getUserMessages(long userId) {
        return null;
    }

    @Override
    public List<Tweet> getSubscriptionMessages(long userId) {
        List<Tweet> tweets = new ArrayList<>();
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(GET_SUBSCRIPTION_MESSAGES_SQL)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Tweet tweet = new Tweet();
                    tweet.setMessageId(resultSet.getLong("message_id"));
                    tweet.setUserId(resultSet.getLong("user_id"));
                    tweet.setMessageDate(resultSet.getTimestamp("message_date").toLocalDateTime());
                    tweet.setMessageText(resultSet.getString("message_text"));
                    tweet.setLogin(resultSet.getString("login"));
                    tweet.setLikes(resultSet.getInt("like_count"));
                    tweets.add(tweet);
                }
            }
        } catch (SQLException e) {
            System.out.println("getSubscriptionMessages() - " + e.getMessage());
        }
        return tweets;
    }

    @Override
    public List<Tweet> getInstrumentMessages(long userId) {
        return null;
    }

    @Override
    public List<Tweet> getCountryMessages(long userId) {
        return null;
    }
}
