package dao;

import model.Subscription;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Air on 25/02/2017.
 */
public interface SubscriptionDao {

    long createSubscription(Subscription subscription);

    Subscription readSubscription(long subscriptionId) throws SQLException;

    void updateSubscription(Subscription subscription) throws SQLException;

    void deleteSubscription(Subscription subscription) throws SQLException;

    List<Subscription> getUserSubscriptions(long userId);

}
