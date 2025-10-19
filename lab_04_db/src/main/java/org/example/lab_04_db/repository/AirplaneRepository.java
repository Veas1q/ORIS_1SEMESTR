package org.example.lab_04_db.repository;

import org.example.lab_04_db.model.Airplane;
import org.example.lab_04_db.service.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirplaneRepository {

    public List<Airplane> findAll() {

        List<Airplane> airplanes = new ArrayList<>();

        try {
            Connection connection = DBConnection.getConnection();
            String sql = "select airplane_code, " +
                    " model -> 'ru' as model_ru, " +
                    " range, speed " +
                    " from bookings.airplanes_data";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Airplane airplane = new Airplane();
                    airplane.setCode(resultSet.getString("airplane_code"));
                    airplane.setModel(resultSet.getString("model_ru"));
                    airplane.setRange(resultSet.getInt("range"));
                    airplane.setSpeed(resultSet.getInt("speed"));
                    airplanes.add(airplane);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airplanes;
    }

}