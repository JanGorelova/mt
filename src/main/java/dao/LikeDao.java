package dao;

import model.Like;
import java.sql.SQLException;

/**
 * Interface which defines all necessary methods for operations with Like objects
 */
public interface LikeDao {

    /**
     * Creates one like in the database
     * @param like Like object to add
     * @return generated id for new Like
     */
    long createLike(Like like);

    /**
     * Method gets the id of the like for specified message and user
     * @param like Like to get the id
     * @return Like id
     * @throws SQLException if there's no such Like
     */
    long getLikeId(Like like) throws SQLException;

    /**
     * Method adds new like if it is not exists, or deletes it if it is exists.
     * @param like Like to update
     */
    void updateLike(Like like);

    /**
     * Deletes the record for the Like with specified id
     * @param likeId Like id to delete
     */
    void deleteLike(long likeId);

    /**
     * Method returns the total number of likes for the specified message
     * @param messageId message id
     * @return number of likes
     */
    int getLikeCount(long messageId);

}
