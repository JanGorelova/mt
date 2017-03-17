package dao;

import model.User;

import java.sql.SQLException;

/**
 * Created by Air on 25/02/2017.
 */
public interface UserDao {

    long createUser(User user);

    User readUserById(long userId);
    
    User readUserByLogin(String login);

    void updateUser(User user);

    void deleteUser(User user) throws SQLException;
}
