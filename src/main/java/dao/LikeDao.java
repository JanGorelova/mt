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

    long getLikeId(Like like) throws SQLException;

    void updateLike(Like like);

    void deleteLike(long likeId);

    int getLikeCount(long messageId);

    List<User> getLikedUsers(long messageId);

}
