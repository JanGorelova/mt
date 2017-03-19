package model;

import java.time.LocalDateTime;

/**
 * DTO class that represents message
 */
public class Message {

    private long messageId;
    protected long userId;
    private LocalDateTime messageDate;
    private String messageText;

    /**
     * No-args constructor for Message instance
     */
    public Message() {
    }

    /**
     * All-args constructor for Message instance
     * @param userId author's id
     * @param messageId message id
     * @param messageDate date and time of the current message
     * @param messageText message content
     */
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

    @Override
    public String toString() {
        return messageId + " " + userId + " " + messageText;
    }
}
