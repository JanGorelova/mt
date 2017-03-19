package dao.h2;

import dao.*;

import javax.sql.DataSource;

/**
 * DaoFactory implementation for H2 database
 */
public class H2DaoFactory implements DaoFactory {

    private final UserDao userDao;
    private final InstrumentDao instrumentDao;
    private final MessageDao messageDao;
    private final SubscriptionDao subscriptionDao;
    private final LikeDao likeDao;

    /**
     * Constructor, which creates all specified DAOs using the DataSource from the argument.
     * @param dataSource any DataSource to work with
     */
    public H2DaoFactory(DataSource dataSource) {
        userDao = new H2UserDao(dataSource);
        instrumentDao = new H2InstrumentDao(dataSource);
        messageDao = new H2MessageDao(dataSource);
        subscriptionDao = new H2SubscriptionDao(dataSource);
        likeDao = new H2LikeDao(dataSource);
    }

    /**
     * Method returns UserDao instance for H2 database
     * @return H2 UserDao
     */
    @Override
    public UserDao getUserDao() {
        return userDao;
    }

    /**
     * Method returns InstrumentDao instance for H2 database
     * @return H2 InstrumentDao
     */
    @Override
    public InstrumentDao getInstrumentDao() {
        return instrumentDao;
    }

    /**
     * Method returns MessageDao instance for H2 database
     * @return H2 MessageDao
     */
    @Override
    public MessageDao getMessageDao() {
        return messageDao;
    }

    /**
     * Method returns SubscriptionDao instance for H2 database
     * @return H2 SubscriptionDao
     */
    @Override
    public SubscriptionDao getSubscriptionDao() {
        return subscriptionDao;
    }

    /**
     * Method returns LikeDao instance for H2 database
     * @return H2 LikeDao
     */
    @Override
    public LikeDao getLikeDao() {
        return likeDao;
    }

    /**
     * Empty realisation
     */
    @Override
    public void close() throws Exception {
    }
}
