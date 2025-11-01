package org.example.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.repository.DBConnection;

@WebListener
public class DBConnectionListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        DBConnection.init();
    }
    public void contextDestroyed(ServletContextEvent event) {
        DBConnection.destroyConnection();
    }

}
