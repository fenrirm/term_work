package database_utils;

import users.User;

import java.sql.*;

public class DatabaseHandler {
    private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/javafx";
    private static final String DATABASE_USERNAME = "fenrirm";
    private static final String DATABASE_PASSWORD = "fenrirm";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
    }

    public static boolean userExists(int phoneNumber) throws SQLException {
        String sql = "SELECT * FROM user WHERE phoneNumber = ?";
        try (Connection connection = getConnection();
             PreparedStatement psCheckUserExists = connection.prepareStatement(sql)) {

            psCheckUserExists.setString(1, String.valueOf(phoneNumber));
            try (ResultSet resultSet = psCheckUserExists.executeQuery()) {
                return resultSet.isBeforeFirst();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void insertUser(User user) throws SQLException {
        String sql = "INSERT INTO user (name, surname, position, phoneNumber, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement psInsert = connection.prepareStatement(sql)) {
            psInsert.setString(1, user.getName());
            psInsert.setString(2, user.getSurname());
            psInsert.setString(3, user.getPosition());
            psInsert.setString(4, String.valueOf(user.getPhoneNumber()));
            psInsert.setString(5, user.getPassword());
            psInsert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static User getUser(int phoneNumber, String password) throws SQLException {
        String sql = "SELECT * FROM user WHERE phoneNumber = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, String.valueOf(phoneNumber));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    return null;
                }
                while (resultSet.next()) {
                    String retrievedName = resultSet.getString("name");
                    String retrievedSurname = resultSet.getString("surname");
                    String retrievedPosition = resultSet.getString("position");
                    String retrievedPhoneNumber = resultSet.getString("phoneNumber");
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedImage = resultSet.getString("image");
                    if (retrievedPassword.equals(password)) {
                        return new User(retrievedName, retrievedSurname, retrievedPosition, Integer.parseInt(retrievedPhoneNumber), retrievedPassword, retrievedImage);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }


}

