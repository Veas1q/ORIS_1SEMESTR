package org.example.repository;

import org.example.models.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityRepository {
    public List<City> getAll() throws SQLException {
        List<City> cities = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        ResultSet rs = null;
        PreparedStatement preparedStatement =  conn.prepareStatement("select id, name from city");
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            cities.add(new City(rs.getLong("id"), rs.getString("name")));
        }
        rs.close();
        preparedStatement.close();
        conn.close();
        return cities;
    }
    public City getById(long id) throws SQLException {
        City city = null;
        Connection conn = DBConnection.getConnection();
        ResultSet rs = null;
        PreparedStatement preparedStatement =  conn.prepareStatement("select id, name from city where id=?");
        preparedStatement.setLong(1, id);
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            city = new City(rs.getLong("id"), rs.getString("name"),rs.getString("comment"));
        }
        rs.close();
        preparedStatement.close();
        conn.close();
        return city;
    }
}
