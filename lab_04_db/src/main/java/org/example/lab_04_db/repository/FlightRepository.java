package org.example.lab_04_db.repository;

import org.example.lab_04_db.model.Flight;
import org.example.lab_04_db.service.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FlightRepository {
    public List<Flight> findFlights(String airportCode, String boardType, LocalDate date) {
        List<Flight> flights = new ArrayList<>();

        System.out.println("🔍 Ищем рейсы: airport=" + airportCode + ", type=" + boardType + ", date=" + date);

        String sql;
        if ("departure".equals(boardType)) {
            sql = """
            SELECT 
                r.route_no as flight_no,
                f.scheduled_departure as time,
                a.airport_name->'ru' as airport_name,
                a.city->'ru' as city,
                r.arrival_airport as airport_code,
                f.status
            FROM bookings.flights f 
            JOIN bookings.routes r ON f.route_no = r.route_no
            JOIN bookings.airports_data a ON r.arrival_airport = a.airport_code
            WHERE r.departure_airport = ? 
              AND f.scheduled_departure::date = ?
            ORDER BY f.scheduled_departure
            """;
        } else {
            sql = """
            SELECT 
                r.route_no as flight_no,
                f.scheduled_arrival as time,
                a.airport_name->'ru' as airport_name,
                a.city->'ru' as city,
                r.departure_airport as airport_code,
                f.status
            FROM bookings.flights f 
            JOIN bookings.routes r ON f.route_no = r.route_no
            JOIN bookings.airports_data a ON r.departure_airport = a.airport_code
            WHERE r.arrival_airport = ? 
              AND f.scheduled_arrival::date = ?
            ORDER BY f.scheduled_arrival
            """;
        }

        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, airportCode);
            statement.setDate(2, Date.valueOf(date));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Flight flight = new Flight();
                flight.setFlightNo(resultSet.getString("flight_no"));

                // ВАЖНО: Преобразуем Timestamp в LocalDateTime
                Timestamp timestamp = resultSet.getTimestamp("time");
                if (timestamp != null) {
                    flight.setTime(timestamp.toLocalDateTime());
                } else {
                    // Если время null, устанавливаем текущее время
                    flight.setTime(java.time.LocalDateTime.now());
                }

                flight.setAirportName(resultSet.getString("airport_name"));
                flight.setCity(resultSet.getString("city"));
                flight.setAirportCode(resultSet.getString("airport_code"));
                flight.setStatus(resultSet.getString("status"));

                flights.add(flight);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("❌ Ошибка SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("✅ Найдено рейсов: " + flights.size());
        return flights;
    }
}