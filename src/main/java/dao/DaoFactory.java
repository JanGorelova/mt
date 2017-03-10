package dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Air on 25/02/2017.
 */
public interface DaoFactory extends AutoCloseable {

    Connection getConnection() throws SQLException;

    UserDao getUserDao();

    InstrumentDao getInstrumentDao();

    MessageDao getMessageDao();

    SubscriptionDao getSubscriptionDao();

    LikeDao getLikeDao();

}
