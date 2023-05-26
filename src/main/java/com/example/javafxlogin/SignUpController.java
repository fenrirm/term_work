package com.example.javafxlogin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private Button button_log_in;

    @FXML
    private Button button_sign_up;

    @FXML
    private RadioButton rb_student;

    @FXML
    private RadioButton rb_teacher;

    @FXML
    private TextField tf_name;

    @FXML
    private TextField tf_password;

    @FXML
    private TextField tf_nickname;

    @FXML
    private TextField tf_surname;

    @FXML
    private Label nameLabel, surnameLabel, teacherOrStudentLabel, nicknameLabel, passwordLabel, alreadyAMemberLabel;

    @FXML
    private Label createTestsLabel,  createCoursesLabel, passTestsLabel, getResultsLabel, getStatisticsLabel;

    @FXML
    private Button uaLanguageButton, enLanguageButton;

    private ResourceBundle bundle;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ToggleGroup toggleGroup = new ToggleGroup();
        rb_student.setToggleGroup(toggleGroup);
        rb_teacher.setToggleGroup(toggleGroup);

        button_sign_up.setOnAction(e -> {
            String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
            if (!tf_name.getText().trim().isEmpty() && !tf_surname.getText().trim().isEmpty() &&
                    !tf_nickname.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()) {
                if(!tf_nickname.getText().matches("[0-9]+")){
                    DBUtils.showAlertMessage("PLease enter correct symbols in the line \"phone number\"");
                }
                DBUtils.signUpUser(e, tf_name.getText(), tf_surname.getText(),
                        toggleName, tf_nickname.getText(), tf_password.getText());
            } else {
                DBUtils.showAlertMessage("PLease fill in all information to sign up successfully!");
            }


        });

        button_log_in.setOnAction(event -> DBUtils.changeScene(event, "hello-view.fxml", "log in page"));


    }
    private void setLanguage(Locale locale) {
        bundle = ResourceBundle.getBundle("resources", locale);
        createTestsLabel.setText(bundle.getString("createTestsLabel"));
        createCoursesLabel.setText(bundle.getString("createCoursesLabel"));
        passTestsLabel.setText(bundle.getString("passTestsLabel"));
        getResultsLabel.setText(bundle.getString("getResultsLabel"));
        getStatisticsLabel.setText(bundle.getString("getStatisticsLabel"));
        nicknameLabel.setText(bundle.getString("phoneNumberLabel"));
        passwordLabel.setText(bundle.getString("passwordLabel"));
        alreadyAMemberLabel.setText(bundle.getString("alreadyAMemberLabel"));
        nameLabel.setText(bundle.getString("nameLabel"));
        surnameLabel.setText(bundle.getString("surnameLabel"));
        teacherOrStudentLabel.setText(bundle.getString("teacherOrStudentLabel"));
        rb_student.setText(bundle.getString("rb_student"));
        rb_teacher.setText(bundle.getString("rb_teacher"));
        button_log_in.setText(bundle.getString("button_log_in"));
        button_sign_up.setText(bundle.getString("button_sign_up"));
    }
}
