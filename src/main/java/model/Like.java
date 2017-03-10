package model;

import java.util.Date;

/**
 * Created by Air on 08/02/2017.
 */
public class Like {

    private long likeId;
    private long userId;
    private long messageId;
    private Date likeTime;

    public Like() {
    }

    public Like(long likeId, long userId, long messageId, Date date) {
        this.likeId = likeId;
        this.userId = userId;
        this.messageId = messageId;
        this.likeTime = date;
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

    public Date getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Date likeTime) {
        this.likeTime = likeTime;
    }
}
