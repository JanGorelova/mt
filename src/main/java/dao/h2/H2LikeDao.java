package dao.h2;

import dao.LikeDao;
import model.Like;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;

/**
 * LikeDao implementation for the H2 database.
 */
public class H2LikeDao implements LikeDao {

    private DataSource dataSource;
    private static final Logger log = LoggerFactory.getLogger(H2LikeDao.class);

    // SQL queries for all necessary operations:
    private static final String CREATE_LIKE_SQL = "INSERT INTO Likes (user_id, message_id) VALUES (?, ?);";

    private static final String GET_LIKE_COUNT_SQL = "SELECT COUNT(like_id) AS like_count FROM Likes WHERE message_id = ?;";

    private static final String GET_LIKE_ID_SQL = "SELECT like_id FROM Likes WHERE user_id = ? AND message_id = ?;";

    private static final String DELETE_LIKE_SQL = "DELETE FROM Likes WHERE like_id = ?;";

    /**
     * Simple constructor of the LikeDao implementation for the H2 database.
     * @param dataSource any DataSource
     */
    H2LikeDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Creates one like in the database
     * @param like Like object to add
     * @return generated id for new Like
     */
    @Override
    public long createLike(Like like) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_LIKE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, like.getUserId());
            statement.setLong(2, like.getMessageId());
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

    /**
     * Method adds new like if it is not exists, or deletes it if it is exists.
     * @param like Like to update
     */
    @Override
    public void updateLike(Like like) {
        try {
            long likeId = getLikeId(like);
            if (likeId > 0) {
                deleteLike(likeId);
            } else {
                createLike(like);
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }
    }

    /**
     * Method gets the id of the like for specified message and user
     * @param like Like to get the id
     * @return Like id
     * @throws SQLException if there's no such Like
     */
    @Override
    public long getLikeId(Like like) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_LIKE_ID_SQL)) {
            statement.setLong(1, like.getUserId());
            statement.setLong(2, like.getMessageId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("like_id");
                }
            }
        }
        return 0;
    }

    /**
     * Deletes the record for the Like with specified id
     * @param likeId Like id to delete
     */
    @Override
    public void deleteLike(long likeId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_LIKE_SQL)) {
            statement.setLong(1, likeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }
    }

    /**
     * Method returns the total number of likes for the specified message
     * @param messageId message id
     * @return number of likes
     */
    @Override
    public int getLikeCount(long messageId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_LIKE_COUNT_SQL)) {
            statement.setLong(1, messageId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("like_count");
                }
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }
        return 0;
    }
}
