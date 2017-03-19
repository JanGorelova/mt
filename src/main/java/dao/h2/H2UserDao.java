package dao.h2;

import dao.UserDao;
import model.Countries;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;

/**
 * UserDao implementation for the H2 database.
 */
public class H2UserDao implements UserDao {

    private DataSource dataSource;
    private static final Logger log = LoggerFactory.getLogger(H2UserDao.class);

    // SQL queries for all necessary operations:
    private static final String SELECT_USER_BY_ID_SQL =
            "SELECT login, password, first_name, last_name, country FROM Users WHERE user_id = ?";

    private static final String SELECT_USER_BY_LOGIN_SQL =
            "SELECT user_id, password, first_name, last_name, country  FROM Users WHERE login = ?";

    private static final String CREATE_USER_SQL =
            "INSERT INTO Users (login, password, first_name, last_name, country) VALUES (?, ?, ?, ?, ?)";

    private static final String UPDATE_USER_SQL =
            "UPDATE Users SET login = ?, password = ?, first_name = ?, last_name = ?, country = ? WHERE user_id = ?;";

    /**
     * Simple constructor of the UserDao implementation for the H2 database.
     *
     * @param dataSource any DataSource
     */
    H2UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Creates new User in the database
     *
     * @param user User to create
     * @return new user's id
     */
    @Override
    public long createUser(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getCountry().toString());
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

    /**
     * Gets the User with the specified id from the database.
     *
     * @param userId - user id
     * @return User object
     */
    @Override
    public User readUserById(long userId) {
        User user = new User();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID_SQL)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setCountry(Countries.valueOf(resultSet.getString("country")));
                user.setUserId(userId);
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }
        return user;
    }

    /**
     * Gets User with the specified login from the database.
     *
     * @param login User's login
     * @return User object
     */
    @Override
    public User readUserByLogin(String login) {
        User user = new User();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_LOGIN_SQL)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                user.setUserId(resultSet.getLong("user_id"));
                user.setPassword(resultSet.getString("password"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setCountry(Countries.valueOf(resultSet.getString("country")));
                user.setLogin(login);
            }
        } catch (SQLException e) {
            log.info("Checking if login is free: " + e.getMessage());
            return null;
        }
        return user;
    }

    /**
     * Updates the specified User in the database
     *
     * @param user User to update
     * @return user id or 0 if login is used
     */
    @Override
    public long updateUser(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_SQL)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getCountry().toString());
            statement.setLong(6, user.getUserId());
            statement.executeUpdate();
            return user.getUserId();
        } catch (SQLException e) {
            log.warn(e.getMessage());
            return 0;
        }
    }
}
