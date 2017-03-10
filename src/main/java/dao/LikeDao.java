package dao;

import model.Like;
import model.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Air on 25/02/2017.
 */
public interface LikeDao {

    long createLike(Like like);

    Like readLike(long likeId);

    void updateLike(Like like) throws SQLException;

    void deleteLike(long likeId) throws SQLException;

    int getLikeCount(long messageId);

    List<User> getLikedUsers(long messageId);

}
