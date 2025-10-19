//package org.example;
//
//import java.sql.*;
//
//public class TestDB {
//    public static void main(String[] args) {
//
//        try {
//            Class.forName("org.postgresql.Driver");
//
//            Connection connection =
//                    DriverManager.getConnection(
//                            // адрес БД, имя пользователя, пароль
//                            "jdbc:postgresql://localhost:5432/demo","postgres","Hepi_pro323");
//
//            Statement statement = connection.createStatement();
//            //Boolean result = statement.execute("create table users(id bigint primary key, name varchar(50))");
//            String sql = "select * from bookings.airplanes_data where airplane_code = ";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//
//            preparedStatement.setString(1, "35X");
//
//
////            ResultSet resultSet = statement.executeQuery(sql);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//
//
//            while (resultSet.next()) {
//                System.out.print(resultSet.getString("airplane_code") + ";");
//                System.out.print(resultSet.getString("model") + ";");
//                System.out.print(resultSet.getString("range") + ";");
//                System.out.print(resultSet.getString("speed"));
//
//            }
//
//            String sqlInsert = "insert into bookings.airplanes_data  "
//                    + "(airplane_code, model, range, speed) values "
//                    + "('U22', '{\"en\":\"Sukhoy S100\", \"ru\":\"СУ S100\"}'::jsonb, 5000, 850)";
//
//            String sqlUpdate = "update boookings.airplanes_data set speed = 900 where airplane_code  = 'U22'";
//
////            statement.execute(sqlInsert);
//            String sqlDelete = "delete from bookings.airplanes_data where airplane_code = 'U22'";
////            statement.executeUpdate(sqlInsert);
////            statement.executeUpdate(sqlUpdate);
//            preparedStatement.executeQuery();
//            resultSet.close();
//            statement.close();
//            connection.close();
//
//
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//}