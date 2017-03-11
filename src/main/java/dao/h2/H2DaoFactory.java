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

    public H2DaoFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public UserDao getUserDao() {
        return new H2UserDao(dataSource);
    }

    @Override
    public InstrumentDao getInstrumentDao() {
        return new H2InstrumentDao(dataSource);
    }

    @Override
    public MessageDao getMessageDao() {
        return new H2MessageDao(dataSource);
    }

    @Override
    public SubscriptionDao getSubscriptionDao() {
        return new H2SubscriptionDao(dataSource);
    }

    @Override
    public LikeDao getLikeDao() {
        return new H2LikeDao(dataSource);
    }

    @Override
    public void close() throws Exception {

    }
}
