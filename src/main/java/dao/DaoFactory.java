package dao;

/**
 * Interface which defines getters for all DAO instances of this project's model
 */
public interface DaoFactory extends AutoCloseable {

    /**
     * DAO for CRUD operations with Users
     * @return UserDao
     */
    UserDao getUserDao();

    /**
     * DAO for CRUD operations with Instruments
     * @return InstrumentDao
     */
    InstrumentDao getInstrumentDao();

    /**
     * DAO for CRUD operations with Messages and Tweets
     * @return MessageDao
     */
    MessageDao getMessageDao();

    /**
     * DAO for CRUD operations with Subscriptions
     * @return SubscriptionDao
     */
    SubscriptionDao getSubscriptionDao();

    /**
     * DAO for CRUD operations with Likes
     * @return LikeDao
     */
    LikeDao getLikeDao();

}
