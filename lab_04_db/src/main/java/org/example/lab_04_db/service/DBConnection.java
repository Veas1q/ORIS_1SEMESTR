package org.example.lab_04_db.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() {
        try {
            // ВСЕГДА создаем новое соединение
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/demo",
                    "postgres",
                    "Hepi_pro323"  // ← твой пароль
            );
            System.out.println("✅ Создано новое подключение к БД");
            return connection;
        } catch (SQLException e) {
            System.out.println("❌ Ошибка подключения к БД: " + e.getMessage());
            throw new RuntimeException("Не удалось подключиться к БД", e);
        }
    }
}