package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter that redirects authorized users to the main page and others to the login page.
 */
@WebFilter(urlPatterns = {"/index.jsp", "/WEB-INF/main.jsp"})
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(true);
        Object tempUser = session.getAttribute("User");
        if (tempUser == null) {
            req.getRequestDispatcher("/index.jsp").forward(request, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/main.jsp").forward(request, resp);
        }
        chain.doFilter(request, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }
}
