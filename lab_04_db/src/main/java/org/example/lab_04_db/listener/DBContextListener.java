package org.example.lab_04_db.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.lab_04_db.service.DBConnection;

@WebListener
public class DBContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName("org.postgresql.Driver");
            DBConnection.getConnection();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void contextDestroyed(ServletContextEvent sce) {
        DBConnection.releaseConnection();

    }

}
