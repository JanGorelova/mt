package dao.h2;

import dao.MessageDao;
import model.Instrument;
import model.Message;
import model.Tweet;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iMac on 10/03/17.
 */
public class H2MessageDao implements MessageDao {

    private DataSource dataSource;
    static final Logger log = LoggerFactory.getLogger(H2MessageDao.class);

    private final String CREATE_MESSAGE_SQL =
            "INSERT INTO Messages (user_id, message_date, message_text) VALUES (?, ?, ?);";

    private final String GET_USER_MESSAGES_SQL =
            "SELECT message_id, message_date, message_text, " +
                    "(SELECT COUNT(like_id) FROM Likes WHERE Likes.message_id = m.message_id) AS like_count " +
                    "FROM Messages AS m WHERE user_id = ? ORDER BY m.message_date DESC LIMIT ? OFFSET ?;";

    private final String GET_SUBSCRIPTION_MESSAGES_SQL =
            "SELECT m.message_id, m.user_id, m.message_date, m.message_text, u.login, " +
                    "(SELECT COUNT(like_id) FROM Likes WHERE Likes.message_id = m.message_id) AS like_count, " +
                    "(SELECT GROUP_CONCAT(i.instrument_name SEPARATOR ', ') FROM Instruments AS i " +
                    "INNER JOIN Users_Instruments AS ui ON i.instrument_id = ui.instrument_id " +
                    "WHERE ui.user_id = m.user_id GROUP BY ui.user_id) AS instruments_string " +
                    "FROM Messages AS m " +
                    "INNER JOIN Subscriptions AS s " +
                    "ON (s.subscripted_user_id = m.user_id) " +
                    "INNER JOIN Users AS u " +
                    "ON (m.user_id = u.user_id) " +
                    "WHERE s.user_id = ? ORDER BY m.message_date DESC LIMIT ? OFFSET ?;";

    private final String GET_INSTRUMENT_MESSAGES_SQL =
            "SELECT m.message_id, m.user_id, m.message_date, m.message_text, u.login, " +
                    "(SELECT COUNT(like_id) FROM Likes WHERE Likes.message_id = m.message_id) AS like_count, " +
                    "(SELECT GROUP_CONCAT(i.instrument_name SEPARATOR ', ') FROM Instruments AS i " +
                    "INNER JOIN Users_Instruments AS ui ON i.instrument_id = ui.instrument_id " +
                    "WHERE ui.user_id = m.user_id GROUP BY ui.user_id) AS instruments_string " +
                    "FROM Messages AS m " +
                    "INNER JOIN Users AS u " +
                    "ON m.user_id = u.user_id " +
                    "INNER JOIN Users_Instruments AS ui1 " +
                    "ON (ui1.user_id = m.user_id) " +
                    "INNER JOIN Users_Instruments AS ui2 " +
                    "ON (ui1.instrument_id = ui2.instrument_id) " +
                    "WHERE ui2.user_id = ? GROUP BY m.message_id ORDER BY m.message_date DESC LIMIT ? OFFSET ?;";

    private final String GET_COUNTRY_MESSAGES_SQL =
            "SELECT m.message_id, m.user_id, m.message_date, m.message_text, u1.login, " +
                    "(SELECT COUNT(like_id) FROM Likes WHERE Likes.message_id = m.message_id) AS like_count, " +
                    "(SELECT GROUP_CONCAT(i.instrument_name SEPARATOR ', ') FROM Instruments AS i " +
                    "INNER JOIN Users_Instruments AS ui ON i.instrument_id = ui.instrument_id " +
                    "WHERE ui.user_id = m.user_id GROUP BY ui.user_id) AS instruments_string " +
                    "FROM Messages AS m " +
                    "INNER JOIN Users AS u1 " +
                    "ON m.user_id = u1.user_id " +
                    "INNER JOIN Users AS u2 " +
                    "ON u1.country = u2.country " +
                    "WHERE u2.user_id = ? ORDER BY m.message_date DESC LIMIT ? OFFSET ?;";

    public H2MessageDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public long createMessage(Message message) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_MESSAGE_SQL, Statement.RETURN_GENERATED_KEYS)) {
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
            log.warn(e.getMessage());
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
    public List<Tweet> getUserMessages(User user, String instruments, int limit, int offset) {
        List<Tweet> tweets = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_MESSAGES_SQL)) {
            statement.setLong(1, user.getUserId());
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Tweet tweet = new Tweet();
                    tweet.setMessageId(resultSet.getLong("message_id"));
                    tweet.setUserId(user.getUserId());
                    tweet.setMessageDate(resultSet.getTimestamp("message_date").toLocalDateTime());
                    tweet.setMessageText(resultSet.getString("message_text"));
                    tweet.setLogin(user.getLogin());
                    tweet.setLikes(resultSet.getInt("like_count"));
                    tweet.setInstruments(instruments);
                    tweets.add(tweet);
                }
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }
        return tweets;
    }

    @Override
    public List<Tweet> getSubscriptionMessages(long userId, int limit, int offset) {
        List<Tweet> tweets = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_SUBSCRIPTION_MESSAGES_SQL)) {
            statement.setLong(1, userId);
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                getTweetsFromResultSet(resultSet, tweets);
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }
        return tweets;
    }

    @Override
    public List<Tweet> getInstrumentMessages(long userId, int limit, int offset) {
        List<Tweet> tweets = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_INSTRUMENT_MESSAGES_SQL)) {
            statement.setLong(1, userId);
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                getTweetsFromResultSet(resultSet, tweets);
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }
        return tweets;
    }

    @Override
    public List<Tweet> getCountryMessages(long userId, int limit, int offset) {
        List<Tweet> tweets = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_COUNTRY_MESSAGES_SQL)) {
            statement.setLong(1, userId);
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                getTweetsFromResultSet(resultSet, tweets);
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }
        return tweets;
    }

    private void getTweetsFromResultSet(ResultSet resultSet, List<Tweet> tweets) throws SQLException {
        while (resultSet.next()) {
            Tweet tweet = new Tweet();
            tweet.setMessageId(resultSet.getLong("message_id"));
            tweet.setUserId(resultSet.getLong("user_id"));
            tweet.setMessageDate(resultSet.getTimestamp("message_date").toLocalDateTime());
            tweet.setMessageText(resultSet.getString("message_text"));
            tweet.setLogin(resultSet.getString("login"));
            tweet.setLikes(resultSet.getInt("like_count"));
            tweet.setInstruments(resultSet.getString("instruments_string") == null
                    ? "" : resultSet.getString("instruments_string"));
            tweets.add(tweet);
        }
    }
}