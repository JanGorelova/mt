package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by iMac on 08/03/17.
 */
@WebFilter(urlPatterns = {"/index.jsp", "/WEB-INF/main.jsp"})
public class loginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(true);
        String localeRequest = request.getParameter("locale");
        if (localeRequest == null || localeRequest.isEmpty()) {
            String localeSession = (String) session.getAttribute("locale");
            if (localeSession == null) {
                session.setAttribute("locale", req.getLocale().toString());
            }
        } else {
            session.setAttribute("locale", localeRequest);
        }
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
