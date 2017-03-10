package model;

import java.util.Date;

/**
 * Created by Air on 08/02/2017.
 */
public class Subscription {

    private long subscriptionId;
    private long userId;
    private long subscriptedUserId;

    public Subscription() {
    }

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
