package ru.itis.dis403.lab11.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        } else {
            try {
                Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/demo",
                        "postgres",
                        "Hepi_pro323"
                );
                return connection;
            } catch (SQLException e) {
                throw new RuntimeException("Не удалось подключиться к БД", e);
            }
        }
    }

    public static void ReleaseConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
