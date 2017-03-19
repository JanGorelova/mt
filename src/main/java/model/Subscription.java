package model;

/**
 * DTO class that represents subscription
 */
public class Subscription {

    // id
    private long subscriptionId;

    // id of the subscriber
    private long userId;

    // id of the subscribed user
    private long subscriptedUserId;

    /**
     * No-args constructor of the Subscription instance
     */
    public Subscription() {
    }

    /**
     * All-args constructor of the Subscription instance
     * @param subscriptionId id
     * @param userId id of the subscribed user
     * @param subscriptedUserId id of the subscribed user
     */
    public Subscription(long subscriptionId, long userId, long subscriptedUserId) {
        this.subscriptionId = subscriptionId;
        this.userId = userId;
        this.subscriptedUserId = subscriptedUserId;
    }

    public long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getSubscriptedUserId() {
        return subscriptedUserId;
    }

    public void setSubscriptedUserId(long subscriptedUserId) {
        this.subscriptedUserId = subscriptedUserId;
    }
}
