package controllers;

import dao.DaoFactory;
import model.Message;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by iMac on 10/03/17.
 */
@WebServlet("/NewTweet")
public class NewTweet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        DaoFactory daoFactory = (DaoFactory) getServletContext().getAttribute("daoFactory");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");
        String tweetText = request.getParameter("tweetText");

        if (user != null && tweetText != null && !tweetText.isEmpty()) {
            Message message = new Message(user.getUserId(), 0, LocalDateTime.now(), tweetText);
            long result = daoFactory.getMessageDao().createMessage(message);
            response.getWriter().write(Long.toString(result));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
