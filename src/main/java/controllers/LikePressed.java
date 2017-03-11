package controllers;

import dao.DaoFactory;
import model.Like;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by iMac on 11/03/17.
 */
@WebServlet("/LikePressed")
public class LikePressed extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DaoFactory daoFactory = (DaoFactory) getServletContext().getAttribute("daoFactory");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");
        String messageId = request.getParameter("messageId");

        if (user != null && !messageId.isEmpty()) {
            daoFactory.getLikeDao().updateLike(new Like(0, user.getUserId(), Long.parseLong(messageId)));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
