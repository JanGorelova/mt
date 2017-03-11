package model;

import java.util.Date;

/**
 * Created by Air on 08/02/2017.
 */
public class Like {

    private long likeId;
    private long userId;
    private long messageId;

    public Like() {
    }

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
