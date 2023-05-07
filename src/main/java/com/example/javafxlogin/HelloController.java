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
    private TextField tf_phone_number;
    @FXML
    private TextField tf_password;
    @FXML
    private Button button_sign_up;
    @FXML
    private Label createTestsLabel, createGroupsLabel, createCoursesLabel, passTestsLabel, getResultsLabel, getStatisticsLabel;
    @FXML
    private Label phoneNumberLabel, passwordLabel;
    @FXML
    private Label notAUserLabel;

    private ResourceBundle bundle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_log_in.setOnAction(event -> DBUtils.logInUser(event, Integer.parseInt(tf_phone_number.getText()), tf_password.getText()));
        button_sign_up.setOnAction(event -> DBUtils.changeScene(event, "sign-up.fxml", "sign up page"));

    }



    public void changeToUkrainian(){
        bundle = ResourceBundle.getBundle("resources", new Locale("ua"));
        createTestsLabel.setText(bundle.getString("createTestsLabel"));
        createGroupsLabel.setText(bundle.getString("createGroupsLabel"));
        createCoursesLabel.setText(bundle.getString("createCoursesLabel"));
        passTestsLabel.setText(bundle.getString("passTestsLabel"));
        getResultsLabel.setText(bundle.getString("getResultsLabel"));
        getStatisticsLabel.setText(bundle.getString("getStatisticsLabel"));
        phoneNumberLabel.setText(bundle.getString("phoneNumberLabel"));
        passwordLabel.setText(bundle.getString("passwordLabel"));
        notAUserLabel.setText(bundle.getString("notAUserLabel"));
        button_log_in.setText(bundle.getString("button_log_in"));
        button_sign_up.setText(bundle.getString("button_sign_up"));
    }

    public void changeToEnglish(){
        bundle = ResourceBundle.getBundle("resources", new Locale("en"));
        createTestsLabel.setText(bundle.getString("createTestsLabel"));
        createGroupsLabel.setText(bundle.getString("createGroupsLabel"));
        createCoursesLabel.setText(bundle.getString("createCoursesLabel"));
        passTestsLabel.setText(bundle.getString("passTestsLabel"));
        getResultsLabel.setText(bundle.getString("getResultsLabel"));
        getStatisticsLabel.setText(bundle.getString("getStatisticsLabel"));
        phoneNumberLabel.setText(bundle.getString("phoneNumberLabel"));
        passwordLabel.setText(bundle.getString("passwordLabel"));
        notAUserLabel.setText(bundle.getString("notAUserLabel"));
        button_log_in.setText(bundle.getString("button_log_in"));
        button_sign_up.setText(bundle.getString("button_sign_up"));
    }
}