package org.example.lab_04_db.repository;

import org.example.lab_04_db.model.Airport;
import org.example.lab_04_db.service.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirportRepository {

    public List<Airport> findAllAirports() {
        List<Airport> airports = new ArrayList<>();

        String sql = """
            SELECT 
                airport_code,
                airport_name->'ru' as airport_name,
                city->'ru' as city
            FROM bookings.airports_data 
            ORDER BY city, airport_name
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Airport airport = new Airport();
                airport.setAirportCode(resultSet.getString("airport_code"));
                airport.setAirportName(resultSet.getString("airport_name"));
                airport.setCity(resultSet.getString("city"));
                airports.add(airport);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return airports;
    }

    // Метод для поиска аэропорта по коду
    public Airport findByCode(String airportCode) {
        String sql = """
            SELECT 
                airport_code,
                airport_name->'ru' as airport_name,
                city->'ru' as city
            FROM bookings.airports_data 
            WHERE airport_code = ?
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, airportCode);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Airport airport = new Airport();
                    airport.setAirportCode(resultSet.getString("airport_code"));
                    airport.setAirportName(resultSet.getString("airport_name"));
                    airport.setCity(resultSet.getString("city"));
                    return airport;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}