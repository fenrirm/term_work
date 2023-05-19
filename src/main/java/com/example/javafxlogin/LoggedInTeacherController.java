package com.example.javafxlogin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInTeacherController extends LoggedInController implements Initializable {

    @FXML
    private Button button_log_out;

    @FXML
    private ImageView imageView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private StackPane stackPane;

    @FXML
    private Button buttonCreateTest;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_log_out.setOnAction(event -> DBUtils.changeScene(event, "hello-view.fxml", "Log In"));
        buttonCreateTest.setOnAction(event ->  DBUtils.changeScene(event, "test_choice.fxml", "Test choice", 260, 210));

    }

    @Override
    public void setUserInformation(String name, String surname, String position, int phoneNumber, String image) {
        super.setUserInformation(name, surname, position, phoneNumber, image);
    }

    @Override
    public void uploadPhoto() {
        super.uploadPhoto();
    }

}
