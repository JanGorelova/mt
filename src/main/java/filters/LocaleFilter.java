package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by iMac on 17/03/17.
 */
@WebFilter("/*")
public class LocaleFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String localeRequest = request.getParameter("locale");
        HttpSession session = request.getSession(true);
        if (localeRequest == null || localeRequest.isEmpty()) {
            Locale localeSession = (Locale) session.getAttribute("locale");
            if (localeSession == null) {
                session.setAttribute("locale", req.getLocale());
            }
        } else {
            session.setAttribute("locale", new Locale(localeRequest));
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
    }

}
