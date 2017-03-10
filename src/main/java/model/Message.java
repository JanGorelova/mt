package model;

import java.time.LocalDateTime;

/**
 * Created by Air on 07/02/2017.
 */
public class Message {

    protected long messageId;
    protected long userId;
    protected LocalDateTime messageDate;
    protected String messageText;

    public Message() {
    }

    public Message(long userId, long messageId, LocalDateTime messageDate, String messageText) {
        this.userId = userId;
        this.messageId = messageId;
        this.messageDate = messageDate;
        this.messageText = messageText;
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

    public LocalDateTime getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(LocalDateTime messageDate) {
        this.messageDate = messageDate;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
