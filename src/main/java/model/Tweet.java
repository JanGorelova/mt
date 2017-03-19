package model;

/**
 * Wrapper class for Message, which includes user's login, message like count and String with all user's instruments.
 */
public class Tweet extends Message {

    // Login of the author
    private String login;

    // All user's instruments as String
    private String instruments;

    // Number of likes for this message
    private int likes;

    public Tweet() {
        super();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getInstruments() {
        return instruments;
    }

    public void setInstruments(String instruments) {
        this.instruments = instruments;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
