package model;

/**
 * Created by iMac on 10/03/17.
 */
public class Tweet extends Message {

    private String login;
    private String instruments;
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
