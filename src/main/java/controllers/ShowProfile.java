package controllers;

import model.Instrument;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by iMac on 16/03/17.
 */
@WebServlet("/ShowProfile")
public class ShowProfile extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Object obj = session.getAttribute("User");

        String localeRequest = request.getParameter("locale");
        if (localeRequest != null && !localeRequest.isEmpty()) {
            session.setAttribute("locale", localeRequest);
        }

        if (obj != null) {
            List<Instrument> instrumentList = (List<Instrument>) session.getAttribute("Instruments");
            User user = (User) obj;
            StringBuilder instrumentsTemporary = new StringBuilder();
            for (Instrument instrument : instrumentList) {
                instrumentsTemporary.append(instrument.getInstrumentName()).append(", ");
            }
            request.setAttribute("instruments", instrumentsTemporary.toString());
            request.setAttribute("login", user.getLogin());
            request.setAttribute("firstName", user.getFirstName());
            request.setAttribute("lastName", user.getLastName());
            request.setAttribute("country", user.getCountry().toString());
            request.getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
