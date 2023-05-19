package com.example.javafxlogin;

import com.example.database_utils.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;
import com.example.users.User;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class DBUtils {

    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 572;

    public static final String TEACHER_FXML = "logged-in.fxml";
    public static final String STUDENT_FXML = "logged-in-student.fxml";

    public static void changeScene(ActionEvent event, String fxmlFile, String title, double width, double height) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(DBUtils.class.getResource(fxmlFile)));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, width, height));
            // Center the window on the screen
            double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
            double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
            double windowX = (screenWidth - width) / 2;
            double windowY = (screenHeight - height) / 2;
            stage.setX(windowX);
            stage.setY(windowY);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void changeScene(ActionEvent event, String fxmlFile, String title) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(DBUtils.class.getResource(fxmlFile)));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void changeScene(ActionEvent event, String fxmlFile, String title, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            Parent root = loader.load();
            LoggedInController loggedInController = loader.getController();
            if (user != null) {
                loggedInController.setUserInformation(user.getName(), user.getSurname(), user.getPosition(), user.getPhoneNumber(), user.getImagePath());
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void signUpUser(ActionEvent event, String name, String surname,
                                  String position, int phoneNumber, String password) {

        User user = new User(name, surname, position, phoneNumber, password, null);
        try {
            if (DatabaseHandler.userExists(phoneNumber)) {
                showAlertMessage("User with this number is already exists");
            } else {
                DatabaseHandler.insertUser(user);
                String fxmlFile = checkForPosition(position);
                changeScene(event, fxmlFile, TEACHER_FXML, user);
            }
        }catch (SQLException e){
            showAlertMessage("Error signing up user");
            e.printStackTrace();
        }

    }
    public static void logInUser(ActionEvent event, int phoneNumber, String password) {
        try {
            User user = DatabaseHandler.getUser(phoneNumber, password);
            if (user == null) {
                showAlertMessage("User was not found! Check provided credentials.");
            }else {
                if(user.getPassword().equals(password)){
                    String fxmlFile = checkForPosition(user.getPosition());
                    changeScene(event, fxmlFile, STUDENT_FXML, user);
                }else {
                    showAlertMessage("Provided password are incorrect!");
                }
            }
        }catch (SQLException e){
            showAlertMessage("Error logining in");
            e.printStackTrace();
        }
    }


    public static String checkForPosition(String retrievedPosition) {
        return switch (retrievedPosition) {
            case "Teacher" -> TEACHER_FXML;
            case "Student" -> STUDENT_FXML;
            default -> throw new IllegalArgumentException("Invalid position " + retrievedPosition);
        };

    }

    public static void showAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }


}
