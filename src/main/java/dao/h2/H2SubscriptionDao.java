package dao.h2;

import dao.SubscriptionDao;
import model.Instrument;
import model.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iMac on 09/03/17.
 */
public class H2SubscriptionDao implements SubscriptionDao {

    private DataSource dataSource;
    static final Logger log = LoggerFactory.getLogger(H2SubscriptionDao.class);

    private final String CREATE_SUBSCRIPTION_SQL =
            "INSERT INTO Subscriptions (user_id, subscripted_user_id) VALUES (?, ?);";

    private final String GET_USER_SUBSCRIPTIONS_SQL =
            "SELECT subscription_id, subscripted_user_id FROM Subscriptions WHERE user_id = ?";

    private final String DELETE_SUBSCRIPTION_SQL =
            "DELETE FROM Subscriptions WHERE (user_id = ? AND subscripted_user_id = ?);";

    public H2SubscriptionDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public long createSubscription(Subscription subscription) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_SUBSCRIPTION_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, subscription.getUserId());
            statement.setLong(2, subscription.getSubscriptedUserId());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }
        return 0;
    }

    @Override
    public Subscription readSubscription(long subscriptionId) throws SQLException {
        return null;
    }

    @Override
    public void updateSubscription(Subscription subscription) throws SQLException {

    }

    @Override
    public void deleteSubscription(Subscription subscription)  {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SUBSCRIPTION_SQL)) {
            statement.setLong(1, subscription.getUserId());
            statement.setLong(2, subscription.getSubscriptedUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }
    }

    @Override
    public List<Subscription> getUserSubscriptions(long userId) {
        List<Subscription> subscriptions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_SUBSCRIPTIONS_SQL)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Subscription subscription = new Subscription();
                    subscription.setSubscriptionId(resultSet.getLong("subscription_id"));
                    subscription.setUserId(userId);
                    subscription.setSubscriptedUserId(resultSet.getLong("subscripted_user_id"));
                    subscriptions.add(subscription);
                }
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }
        return subscriptions;
    }
}
