package com.example.listeners;

import com.example.services.DBConnection;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class BDConnectionListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        DBConnection.init();
    }

    public void contextDestroyed(ServletContextEvent sce) {
        DBConnection.destroyConnection();
    }
}
