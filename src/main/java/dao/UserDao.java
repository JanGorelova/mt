package dao;

import model.User;

/**
 * Interface which defines all necessary methods for operations with User objects
 */
public interface UserDao {

    /**
     * Creates new User in the database
     *
     * @param user User to create
     * @return new user's id or 0 if login has been used
     */
    long createUser(User user);

    /**
     * Gets the User with the specified id from the database.
     *
     * @param userId - user id
     * @return User object
     */
    User readUserById(long userId);

    /**
     * Gets User with the specified login from the database.
     *
     * @param login User's login
     * @return User object
     */
    User readUserByLogin(String login);

    /**
     * Updates the specified User in the database
     *
     * @param user User to update
     * @return user id or 0 if login has been used
     */
    long updateUser(User user);
}
