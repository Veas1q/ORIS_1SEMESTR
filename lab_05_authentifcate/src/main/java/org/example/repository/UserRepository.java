package org.example.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.User;

import java.sql.*;
public class UserRepository {

    final static Logger logger = LogManager.getLogger(UserRepository.class);

    public User findByUsername(String username) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT u.id, u.username, u.hashpassword, ui.firstname, ui.lastname, ui.phone " +
                    "FROM users u JOIN userinfo ui ON u.id = ui.id " +
                    "WHERE u.username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setHashPassword(resultSet.getString("hashpassword"));
                user.setFirstName(resultSet.getString("firstname"));
                user.setLastName(resultSet.getString("lastname"));
                user.setPhone(resultSet.getString("phone"));
                return user;
            }
            return null;
        } finally {
            if (connection != null) connection.close();
            if (preparedStatement != null) preparedStatement.close();
            if (resultSet != null) resultSet.close();
        }

    };


    public void addUser(User user) throws SQLException {
        Connection connection = DBConnection.getConnection();
        connection.setAutoCommit(false);

        PreparedStatement statement =
                connection.prepareStatement("select nextval('user_seq') as id");

        ResultSet resultSet = statement.executeQuery();

        Long id = null;
        if (resultSet.next()) {
            id = resultSet.getLong("id");
        }
        resultSet.close();
        statement.close();

        if (id != null) {
            user.setId(id);
        } else {
            throw new SQLException("Не удалось присвоить идентификатор!");
        }

        statement = connection.prepareStatement(
                "insert into users (id, username, hashpassword) values (?, ?, ?)");
        statement.setLong(1, id);
        statement.setString(2, user.getUsername());
        statement.setString(3, user.getHashPassword());
        int countRow = statement.executeUpdate();
        statement.close();

        statement = connection.prepareStatement(
                "insert into userinfo (id, lastname, firstname, phone) values ( ?, ? , ? , ?)");
        statement.setLong(1, id);
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getFirstName());
        statement.setString(4, user.getPhone());
        countRow = statement.executeUpdate();
        statement.close();

        connection.commit();
        connection.close();
    }

}