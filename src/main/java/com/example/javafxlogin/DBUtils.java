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

        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        User user = new User(name, surname, position, phoneNumber, password, null);

        try {
            //checks if any registered user has the same phone number
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/javafx", "fenrirm", "fenrirm");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM user WHERE phoneNumber = ?");
            psCheckUserExists.setString(1, String.valueOf(phoneNumber));
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User with this number is already exists");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO user (name, surname, position, phoneNumber, password) VALUES (?, ?, ?, ?, ?)");
                psInsert.setString(1, name);
                psInsert.setString(2, surname);
                psInsert.setString(3, position);
                psInsert.setString(4, String.valueOf(phoneNumber));
                psInsert.setString(5, password);
                psInsert.executeUpdate();

                if(position.equals("Teacher")) {
                    changeScene(event, "logged-in.fxml", "logged-in", name, surname, position, phoneNumber, null);
                }else {
                    changeScene(event, "logged-in-student.fxml", "logged-in", name, surname, position, phoneNumber, null);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null) {
                try {
                    psCheckUserExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void logInUser(ActionEvent event, int phoneNumber, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/javafx", "fenrirm", "fenrirm");
            preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE phoneNumber = ?");
            preparedStatement.setString(1, String.valueOf(phoneNumber));
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            }else {
                while (resultSet.next()){
                    String retrievedName = resultSet.getString("name");
                    String retrievedSurname = resultSet.getString("surname");
                    String retrievedPosition = resultSet.getString("position");
                    String retrievedPhoneNumber = resultSet.getString("phoneNumber");
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedImage = resultSet.getString("image");
                    System.out.println(retrievedImage);

                    if(retrievedPassword.equals(password)){
                        if(retrievedPosition.equals("Teacher")){
                            changeScene(event, "logged-in.fxml", "logged-in-window", retrievedName, retrievedSurname, retrievedPosition, Integer.parseInt(retrievedPhoneNumber),retrievedImage);
                        }else {
                            changeScene(event, "logged-in-student.fxml", "logged-in-window", retrievedName, retrievedSurname, retrievedPosition, Integer.parseInt(retrievedPhoneNumber), retrievedImage);
                        }
                    }else {
                        System.out.println("Password did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided credentials are incorrect!");
                        alert.show();
                    }
                }

            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement != null){
                try{
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

    }
}
