package org.example.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.repository.CityRepository;
import org.example.repository.DBConnection;
import org.example.repository.StreetRepository;


@WebListener
public class DBContextListener implements ServletContextListener {

    final static Logger logger = LogManager.getLogger(DBContextListener.class);

    public void contextInitialized(ServletContextEvent sce) {
        logger.debug("contextInitialized");
        DBConnection.init();
        CityRepository cityRepository = new CityRepository();
        StreetRepository streetRepository = new StreetRepository();
        sce.getServletContext().setAttribute("cityRepository", cityRepository);
        sce.getServletContext().setAttribute("streetRepository", streetRepository);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        logger.debug("contextDestroyed");
        DBConnection.destroy();
    }
}