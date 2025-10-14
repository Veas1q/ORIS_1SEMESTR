package org.example.lab_04_db.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;
    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }else{
            try {
                connection =
                        DriverManager.getConnection(
                                // адрес БД, имя пользователя, пароль
                                "jdbc:postgresql://localhost:5432/demo","postgres","Hepi_pro323");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    public static void releaseConnection() {
        try {
            if (connection != null && connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


