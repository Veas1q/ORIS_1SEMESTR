package com.example.repository;
import com.example.models.Player;
import com.example.services.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private Connection connection =  null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    public List<Player> findAll() throws SQLException {
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement("select * from ProPlayer");
            resultSet = preparedStatement.executeQuery();
            List<Player> players = new ArrayList<>();
            while (resultSet.next()) {
                Player player = createPlayer(resultSet);
                players.add(player);
            }
            return players;
        }finally {
            if (resultSet != null) {
                try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (preparedStatement != null) {
                try { preparedStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    public List<Player> findByNickname(String nickname) throws SQLException {
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement("select * from ProPlayer WHERE LOWER(nickname) = LOWER(?)");
            preparedStatement.setString(1, nickname);
            resultSet = preparedStatement.executeQuery();
            List<Player> players = new ArrayList<>();
            while (resultSet.next()) {
                Player player = createPlayer(resultSet);
                players.add(player);
            }
            return players;
        }finally {
            if (resultSet != null) {
                try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (preparedStatement != null) {
                try { preparedStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    public Player createPlayer(ResultSet resultSet) throws SQLException {
        Player player = new Player();
        player.setId(resultSet.getInt("id"));
        player.setName(resultSet.getString("name"));
        player.setCountry(resultSet.getString("country"));
        player.setActive(resultSet.getBoolean("is_active"));
        player.setGame(resultSet.getString("game"));
        player.setEarnings(resultSet.getDouble("earnings"));
        player.setNickname(resultSet.getString("nickname"));
        player.setTeam(resultSet.getString("team"));
        return player;
    }

    public void addPlayer(Player player) throws SQLException {
        Connection connection = DBConnection.getConnection();

        String sql = "INSERT INTO proplayer (nickname, name, game, team, is_active, earnings, country) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement =
                connection.prepareStatement(sql);

        statement.setString(1, player.getNickname());
        statement.setString(2, player.getName());
        statement.setString(3, player.getGame());
        statement.setString(4, player.getTeam());
        statement.setBoolean(5, player.isActive());
        statement.setDouble(6, player.getEarnings());
        statement.setString(7, player.getCountry());

        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    public void updatePlayer(Player player, String oldNickname) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "UPDATE proplayer SET nickname = ?, name = ?, game = ?, team = ?, is_active = ?, earnings = ?, country = ? WHERE LOWER(nickname) = LOWER(?)";
        PreparedStatement statement =
                connection.prepareStatement(sql);
        statement.setString(1, player.getNickname());
        statement.setString(2, player.getName());
        statement.setString(3, player.getGame());
        statement.setString(4, player.getTeam());
        statement.setBoolean(5, player.isActive());
        statement.setDouble(6, player.getEarnings());
        statement.setString(7, player.getCountry());
        statement.setString(8, oldNickname);

        statement.executeUpdate();
        statement.close();
        connection.close();
    }
}
