package services;

import dao.DaoFactory;
import model.Countries;
import model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

/**
 * Class with static methods for validating user's profile data.
 */
public class Validator {

    /**
     * Method validates user's data for update request
     *
     * @param request    current request
     * @param bundle     localization resources
     * @param daoFactory DAO
     * @param user       user with new data
     * @return validation status
     */
    public static boolean validateUpdatedProfile(HttpServletRequest request, ResourceBundle bundle, DaoFactory daoFactory, User user) {

        String login = request.getParameter("login");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        Countries country = Countries.valueOf(request.getParameter("country"));
        String instrument = request.getParameter("instrument");
        String password = request.getParameter("password");
        String passwordRepeat = request.getParameter("passwordRepeat");

        boolean errorFlag = false;

        if (login == null || login.isEmpty() || login.length() > 15) {
            errorFlag = true;
            request.setAttribute("invalidLogin", bundle.getString("invalidLogin"));
        } else {
            request.setAttribute("login", login.trim());
        }

        if (password != null && password.length() > 256) {
            errorFlag = true;
            request.setAttribute("invalidPassword", bundle.getString("invalidPassword"));
        }

        if ((password != null & passwordRepeat != null && !password.equals(passwordRepeat))
                || (password == null & passwordRepeat != null)
                || (password != null & passwordRepeat == null)) {
            errorFlag = true;
            request.setAttribute("invalidPasswordRepeat", bundle.getString("invalidPasswordRepeat"));
        }

        if (instrument != null && instrument.length() > 256) {
            errorFlag = true;
            request.setAttribute("invalidInstrument", bundle.getString("invalidInstrument"));
        } else if (instrument != null) {
            request.setAttribute("instrument", instrument.trim().toLowerCase());
        }

        if (firstName != null && firstName.length() > 32) {
            errorFlag = true;
            request.setAttribute("invalidFirstname", bundle.getString("invalidFirstname"));
        } else if (firstName != null) {
            request.setAttribute("firstName", firstName.trim());
        }

        if (lastName != null && lastName.length() > 32) {
            errorFlag = true;
            request.setAttribute("invalidLastname", bundle.getString("invalidLastname"));
        } else if (lastName != null) {
            request.setAttribute("lastName", lastName.trim());
        }

        request.setAttribute("country", country.toString());

        if (login != null && !login.equals(user.getLogin())) {
            if (daoFactory.getUserDao().readUserByLogin(login) != null) {
                errorFlag = true;
                request.setAttribute("loginExists", bundle.getString("loginExists"));
            }
        }
        return errorFlag;
    }

    /**
     * Method validates new user's data for create request
     *
     * @param request    current request
     * @param bundle     localization resources
     * @param daoFactory DAO
     * @return validation status
     */
    public static boolean validateNewUser(HttpServletRequest request, ResourceBundle bundle, DaoFactory daoFactory) {

        String login = request.getParameter("login");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        Countries country = Countries.valueOf(request.getParameter("country"));
        String instrument = request.getParameter("instrument");
        String password = request.getParameter("password");
        String passwordRepeat = request.getParameter("passwordRepeat");

        boolean errorFlag = false;

        if (login == null || login.isEmpty() || login.length() > 15) {
            errorFlag = true;
            request.setAttribute("invalidLogin", bundle.getString("invalidLogin"));
        } else {
            request.setAttribute("login", login.trim());
        }

        if (password == null || password.isEmpty() || password.length() > 256) {
            errorFlag = true;
            request.setAttribute("invalidPassword", bundle.getString("invalidPassword"));
        }

        if (passwordRepeat == null) {
            errorFlag = true;
            request.setAttribute("invalidPasswordRepeat", bundle.getString("invalidPasswordRepeat"));
        } else if (password != null && !password.equals(passwordRepeat)) {
            errorFlag = true;
            request.setAttribute("invalidPasswordRepeat", bundle.getString("invalidPasswordRepeat"));
        }

        if (instrument != null && instrument.length() > 256) {
            errorFlag = true;
            request.setAttribute("invalidInstrument", bundle.getString("invalidInstrument"));
        } else if (instrument != null) {
            request.setAttribute("instrument", instrument.trim().toLowerCase());
        }

        if (firstName != null && firstName.length() > 32) {
            errorFlag = true;
            request.setAttribute("invalidFirstname", bundle.getString("invalidFirstname"));
        } else if (firstName != null) {
            request.setAttribute("firstName", firstName.trim());
        }

        if (lastName != null && lastName.length() > 32) {
            errorFlag = true;
            request.setAttribute("invalidLastname", bundle.getString("invalidLastname"));
        } else if (lastName != null) {
            request.setAttribute("lastName", lastName.trim());
        }

        request.setAttribute("country", country.toString());

        if (daoFactory.getUserDao().readUserByLogin(login) != null) {
            errorFlag = true;
            request.setAttribute("loginExists", bundle.getString("loginExists"));
        }
        return errorFlag;
    }
}
