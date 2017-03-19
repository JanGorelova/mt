package listeners;

import model.Countries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.HashGenerator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.security.NoSuchAlgorithmException;

/**
 * Listener that adjusts some servlet context attributes: countries, hashGenerators
 */

@WebListener
public class ContextInitListener implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(ContextInitListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Servlet context initialization started...");
        ServletContext context = sce.getServletContext();
        context.setAttribute("countries", Countries.values());
        try {
            HashGenerator hashGenerator = new HashGenerator();
            context.setAttribute("hashGenerator", hashGenerator);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
