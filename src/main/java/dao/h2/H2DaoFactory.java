package dao.h2;

import dao.*;
import model.User;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Air on 26/02/2017.
 */
public class H2DaoFactory implements DaoFactory {

    private final DataSource dataSource;
    private final UserDao userDao;
    private final InstrumentDao instrumentDao;
    private final MessageDao messageDao;
    private final SubscriptionDao subscriptionDao;
    private final LikeDao likeDao;

    public H2DaoFactory(DataSource dataSource) {
        this.dataSource = dataSource;
        userDao = new H2UserDao(dataSource);
        instrumentDao = new H2InstrumentDao(dataSource);
        messageDao = new H2MessageDao(dataSource);
        subscriptionDao = new H2SubscriptionDao(dataSource);
        likeDao = new H2LikeDao(dataSource);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public UserDao getUserDao() {
        return userDao;
    }

    @Override
    public InstrumentDao getInstrumentDao() {
        return instrumentDao;
    }

    @Override
    public MessageDao getMessageDao() {
        return messageDao;
    }

    @Override
    public SubscriptionDao getSubscriptionDao() {
        return subscriptionDao;
    }

    @Override
    public LikeDao getLikeDao() {
        return likeDao;
    }

    @Override
    public void close() throws Exception {
    }
}
