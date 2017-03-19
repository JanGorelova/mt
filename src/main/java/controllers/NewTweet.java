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
 * Servlet gets new tweet data from the request and sends it to the database. Responce contains the auto-generated
 * id of the new tweet.
 */
@WebServlet("/NewTweet")
public class NewTweet extends HttpServlet {

    private DaoFactory daoFactory;

    /**
     * Method gets the common Dao Factory from servlet context.
     *
     * @throws ServletException - standard Servlet exception
     */
    @Override
    public void init() throws ServletException {
        daoFactory = (DaoFactory) getServletContext().getAttribute("daoFactory");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");
        if (user != null) {
            String tweetText = request.getParameter("tweetText");
            if (tweetText != null && !tweetText.isEmpty()) {
                Message message = new Message(user.getUserId(), 0, LocalDateTime.now(), tweetText.trim());
                long result = daoFactory.getMessageDao().createMessage(message);
                response.getWriter().write(Long.toString(result));
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
