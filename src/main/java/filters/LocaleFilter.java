package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by iMac on 17/03/17.
 */
@WebFilter("/*")
public class LocaleFilter implements Filter {
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
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
