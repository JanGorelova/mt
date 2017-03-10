package cp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Air on 26/02/2017.
 */
public class ConnectionPool implements AutoCloseable{

    private final String DB_DRIVER = "org.h2.Driver";
    private final String DB_URL = "jdbc:h2:mem:test";
    private int DB_POOLSIZE;
    private BlockingQueue<Connection> connectionsAvailable;
    private BlockingQueue<Connection> connectionsUsing;

    public ConnectionPool(int poolSize) {
        this.DB_POOLSIZE = poolSize;
        connectionsAvailable = new ArrayBlockingQueue<Connection>(poolSize);
        connectionsUsing = new ArrayBlockingQueue<Connection>(poolSize);
        try {
            Class.forName(DB_DRIVER);
            for (int i = 0; i < DB_POOLSIZE; i++) {
                connectionsAvailable.add(new PooledConnection(DriverManager.getConnection(DB_URL), this));
            }
            System.out.println(connectionsAvailable.size() + " connections available...");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found!");
        } catch (SQLException e) {
            System.out.println("Database problems: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        try {
            Connection con = connectionsAvailable.take();
            connectionsUsing.add(con);
            return con;
        } catch (InterruptedException e) {
            throw new RuntimeException("Database error!");
        }
    }

    void returnConnection(Connection pooledConnection) {
        connectionsUsing.remove(pooledConnection);
        connectionsAvailable.add(pooledConnection);
    }

    @Override
    public void close() throws Exception {
        try {
            Connection connection;
            while ((connection = connectionsAvailable.poll()) != null) {
                if (!connection.getAutoCommit()) {
                    connection.commit();
                }
                ((PooledConnection) connection).reallyClose();
            }
            while ((connection = connectionsUsing.poll()) != null) {
                if (!connection.getAutoCommit()) {
                    connection.commit();
                }
                ((PooledConnection) connection).reallyClose();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
