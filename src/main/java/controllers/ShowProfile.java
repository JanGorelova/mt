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

/**
 * Servlet prepares data to show it on the My Profile page, then forwards the request to the profile.jsp
 */
@WebServlet("/ShowProfile")
public class ShowProfile extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Object obj = session.getAttribute("User");

        // Proceed only if the user is logged in
        if (obj != null) {
            @SuppressWarnings("unchecked")
            List<Instrument> instrumentList = (List<Instrument>) session.getAttribute("Instruments");
            User user = (User) obj;
            StringBuilder instrumentsTemporary = new StringBuilder();
            for (Instrument instrument : instrumentList) {
                instrumentsTemporary.append(instrument.getInstrumentName()).append(", ");
            }
            request.setAttribute("instrument", instrumentsTemporary.toString());
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
