package model;

/**
 * DTO class that represents user
 */
public class User {

    private long userId;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private Countries country;

    /**
     * No-args constructor for User instance
     */
    public User() {
    }

    /**
     * All-args constructor for User instance
     * @param id user id
     * @param login login
     * @param password password hash
     * @param firstName first name
     * @param lastName last name
     * @param country country
     */
    public User(long id, String login, String password, String firstName, String lastName, Countries country) {
        this.userId = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Countries getCountry() {
        return country;
    }


    public void setCountry(Countries country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return userId + ". " + login + ", " + firstName + " " + lastName;
    }
}
