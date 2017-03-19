package model;

/**
 * DTO class that represents Like for single Tweet
 */
public class Like {

    // Like id
    private long likeId;

    // Id of the user, who put this like
    private long userId;

    // Id of the liked message
    private long messageId;

    /**
     * All-args constructor for Like instance
     * @param likeId Like id
     * @param userId Id of the user, who put this like
     * @param messageId Id of the liked message
     */
    public Like(long likeId, long userId, long messageId) {
        this.likeId = likeId;
        this.userId = userId;
        this.messageId = messageId;
    }

    public long getLikeId() {
        return likeId;
    }

    public void setLikeId(long likeId) {
        this.likeId = likeId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }
}
