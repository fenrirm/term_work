package com.example.javafxlogin;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Button button_log_in;
    @FXML
    private TextField tf_nickname;
    @FXML
    private TextField tf_password;
    @FXML
    private Button button_sign_up;
    @FXML
    private Label createTestsLabel, passTestsLabel, getResultsLabel, getStatisticsLabel;
    @FXML
    private Label nicknameLabel, passwordLabel;
    @FXML
    private Label notAUserLabel;
    private ResourceBundle bundle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_log_in.setOnAction(event -> DBUtils.logInUser(event, tf_nickname.getText().trim(), tf_password.getText()));
        button_sign_up.setOnAction(event -> DBUtils.changeScene(event, "sign-up.fxml", "sign up page"));

    }




}