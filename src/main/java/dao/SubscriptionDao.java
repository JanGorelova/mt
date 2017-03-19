package dao;

import model.Subscription;
import java.util.List;

/**
 * Interface which defines all necessary methods for operations with Subscription objects
 */
public interface SubscriptionDao {

    /**
     * Creates single subscription.
     * @param subscription Subscription object
     * @return id of the created subscription
     */
    long createSubscription(Subscription subscription);

    /**
     * Delete the specified Subscription from the database.
     * @param subscription Subscription to delete
     */
    void deleteSubscription(Subscription subscription);

    /**
     * Returns the list of all subscriptions of the specified user.
     * @param userId user id
     * @return ArrayList with Subscriptions
     */
    List<Subscription> getUserSubscriptions(long userId);

}
