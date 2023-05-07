package com.example.javafxlogin;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import users.User;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class DBUtils {


    public static void changeScene(ActionEvent event, String fxmlFile, String title){
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(DBUtils.class.getResource(fxmlFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 700, 572));
        stage.show();
    }
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String name, String surname, String position, int phoneNumberLabel, String image) {
        Parent root = null;
        if (name != null && surname != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(name, surname, position, phoneNumberLabel, image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(Objects.requireNonNull(DBUtils.class.getResource(fxmlFile)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 700, 572));
        stage.show();
    }

    public static void signUpUser(ActionEvent event, String name, String surname,
                                  String position, int phoneNumber, String password) {

        User user = new User(name, surname, position, phoneNumber, password, null);

        if(userExists(phoneNumber)){
            showAlertMessage("User with this number is already exists");
        }else {
            insertUser(user);
            String fxmlFile = checkForPosition(position);
            changeScene(event, fxmlFile, "logged-in", name, surname, position, phoneNumber, null);
        }

    }


    public static void logInUser(ActionEvent event, int phoneNumber, String password) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/javafx", "fenrirm", "fenrirm");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE phoneNumber = ?")) {
            preparedStatement.setString(1, String.valueOf(phoneNumber));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    System.out.println("User not found in the database!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Provided credentials are incorrect!");
                    alert.show();
                    return;
                }
                while (resultSet.next()) {
                    String retrievedName = resultSet.getString("name");
                    String retrievedSurname = resultSet.getString("surname");
                    String retrievedPosition = resultSet.getString("position");
                    String retrievedPhoneNumber = resultSet.getString("phoneNumber");
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedImage = resultSet.getString("image");
                    System.out.println(retrievedImage);

                    if (retrievedPassword.equals(password)) {
                        String fxmlFile = checkForPosition(retrievedPosition);
                        changeScene(event, fxmlFile, "logged-in-window", retrievedName, retrievedSurname, retrievedPosition, Integer.parseInt(retrievedPhoneNumber), retrievedImage);
                    } else {
                        showAlertMessage("The provided credentials are incorrect!");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean userExists(int phoneNumber) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/javafx", "fenrirm", "fenrirm");
             PreparedStatement psCheckUserExists = connection.prepareStatement("SELECT * FROM user WHERE phoneNumber = ?")) {

            psCheckUserExists.setString(1, String.valueOf(phoneNumber));
            try (ResultSet resultSet = psCheckUserExists.executeQuery()) {
                return resultSet.isBeforeFirst();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static void insertUser(User user) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/javafx", "fenrirm", "fenrirm");
             PreparedStatement psInsert = connection.prepareStatement("INSERT INTO user (name, surname, position, phoneNumber, password) VALUES (?, ?, ?, ?, ?)")) {

            psInsert.setString(1, user.getName());
            psInsert.setString(2, user.getSurname());
            psInsert.setString(3, user.getPosition());
            psInsert.setString(4, String.valueOf(user.getPhoneNumber()));
            psInsert.setString(5, user.getPassword());
            psInsert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static String checkForPosition(String retrievedPosition){
        return switch (retrievedPosition) {
            case "Teacher" -> "logged-in.fxml";
            case "Student" -> "logged-in-student.fxml";
            default -> throw new IllegalArgumentException("Invalid position " + retrievedPosition);
        };

    }

    public static void showAlertMessage(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }


}
