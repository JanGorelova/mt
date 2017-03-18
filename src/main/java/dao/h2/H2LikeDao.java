package dao.h2;

import dao.LikeDao;
import model.Like;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * Created by iMac on 11/03/17.
 */
public class H2LikeDao implements LikeDao {

    private DataSource dataSource;
    static final Logger log = LoggerFactory.getLogger(H2LikeDao.class);

    private final String CREATE_LIKE_SQL = "INSERT INTO Likes (user_id, message_id) VALUES (?, ?);";

    private final String GET_LIKE_COUNT_SQL = "SELECT COUNT(like_id) AS like_count FROM Likes WHERE message_id = ?;";

    private final String GET_LIKE_ID_SQL = "SELECT like_id FROM Likes WHERE user_id = ? AND message_id = ?;";

    private final String DELETE_LIKE_SQL = "DELETE FROM Likes WHERE like_id = ?;";

    public H2LikeDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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

    @Override
    public List<User> getLikedUsers(long messageId) {
        return null;
    }
}
