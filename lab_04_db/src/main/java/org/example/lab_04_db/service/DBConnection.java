package org.example.lab_04_db.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() {
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