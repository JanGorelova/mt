package model;

/**
 * Created by iMac on 10/03/17.
 */
public class Tweet extends Message {

    private String login;
    private String[] instrument;
    private long likes;

    public Tweet() {
        super();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String[] getInstrument() {
        return instrument;
    }

    public void setInstrument(String[] instrument) {
        this.instrument = instrument;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }
}
