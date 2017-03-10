package listeners;

import model.Countries;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by iMac on 06/03/17.
 */

@WebListener
public class ServletContextInit implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("countries", Countries.values());
    }
}
