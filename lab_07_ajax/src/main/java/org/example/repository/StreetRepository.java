package org.example.repository;

import org.example.models.City;
import org.example.models.Street;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StreetRepository {
    public List<Street> getAll() throws SQLException {
        List<Street> streets = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = null;
        PreparedStatement preparedStatement =  conn.prepareStatement("select id, name, city_id from street");
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            streets.add(new Street(rs.getLong("id"), rs.getString("name"), rs.getLong("city_id")));

        }
        rs.close();
        preparedStatement.close();
        conn.close();
        return streets;
    }
    public List<Street> getAllByCityId(Long cityId) throws SQLException {
        List<Street> streets = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = null;
        PreparedStatement preparedStatement =  conn.prepareStatement("select id, name, city_id from street where city_id = ?");
        preparedStatement.setLong(1, cityId);
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            streets.add(new Street(rs.getLong("id"), rs.getString("name"), rs.getLong("city_id")));
        }
        rs.close();
        preparedStatement.close();
        conn.close();
        return streets;
    }
}
