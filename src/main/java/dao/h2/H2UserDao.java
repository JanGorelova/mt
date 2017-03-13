package dao.h2;

import dao.UserDao;
import model.Countries;
import model.User;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by Air on 26/02/2017.
 */
public class H2UserDao implements UserDao {

    private DataSource dataSource;

    private static final String SELECT_USER_BY_ID_SQL =
            "SELECT login, password, first_name, last_name, country FROM Users WHERE user_id = ?";

    private static final String SELECT_USER_BY_LOGIN_SQL =
            "SELECT user_id, password, first_name, last_name, country  FROM Users WHERE login = ?";

    private static final String CREATE_USER_SQL =
            "INSERT INTO Users (login, password, first_name, last_name, country) VALUES (?, ?, ?, ?, ?)";

    public H2UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public long createUser(User user) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(CREATE_USER_SQL,
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
            e.printStackTrace();
        }
        return 0;
    }

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
            System.out.println("readUserById() - " + e.getMessage());
        }
        return user;
    }

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
            if (!e.getMessage().equals("No data is available [2000-193]")) {
                System.out.println("readUserByLogin() - " + e.getMessage());
            }
            return null;
        }
        return user;
    }

    @Override
    public void updateUser(User user) throws SQLException {

    }

    @Override
    public void deleteUser(User user) throws SQLException {

    }
}
