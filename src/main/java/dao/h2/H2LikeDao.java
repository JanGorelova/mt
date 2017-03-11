package dao.h2;

import dao.LikeDao;
import model.Like;
import model.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by iMac on 11/03/17.
 */
public class H2LikeDao implements LikeDao {

    private DataSource dataSource;

    private final String CREATE_LIKE_SQL = "INSERT INTO Likes (user_id, message_id) VALUES (?, ?);";

    private final String GET_LIKE_COUNT_SQL = "SELECT COUNT(like_id) AS like_count FROM Likes WHERE message_id = ?;";

    private final String GET_LIKE_ID_SQL = "SELECT like_id FROM Likes WHERE user_id = ? AND message_id = ?;";

    private final String DELETE_LIKE_SQL = "DELETE FROM Likes WHERE like_id = ?;";

    public H2LikeDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public long createLike(Like like) {
        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(CREATE_LIKE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, like.getUserId());
            statement.setLong(2, like.getMessageId());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("createLike() - " + e.getMessage());
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
            System.out.println("updateLike() - " + e.getMessage());
        }
    }

    @Override
    public long getLikeId(Like like) throws SQLException {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(GET_LIKE_ID_SQL)) {
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
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(DELETE_LIKE_SQL)){
            statement.setLong(1, likeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deleteLike() - " + e.getMessage());
        }
    }

    @Override
    public int getLikeCount(long messageId) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(GET_LIKE_COUNT_SQL)) {
            statement.setLong(1, messageId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("like_count");
                }
            }
        } catch (SQLException e) {
            System.out.println("getLikeCount() - " + e.getMessage());
        }
        return 0;
    }

    @Override
    public List<User> getLikedUsers(long messageId) {
        return null;
    }
}
