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

    private ResourceBundle bundle;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ToggleGroup toggleGroup = new ToggleGroup();
        rb_student.setToggleGroup(toggleGroup);
        rb_teacher.setToggleGroup(toggleGroup);

        button_sign_up.setOnAction(e -> {
            String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
            if (!tf_name.getText().trim().isEmpty() && !tf_surname.getText().trim().isEmpty() &&
                    !tf_nickname.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty() && !toggleName.isEmpty()) {
                DBUtils.signUpUser(e, tf_name.getText().trim(), tf_surname.getText().trim(),
                        toggleName, tf_nickname.getText().trim(), tf_password.getText().trim());
            } else {
                DBUtils.showAlertMessage("PLease fill in all information to sign up successfully!");
            }


        });

        button_log_in.setOnAction(event -> DBUtils.changeScene(event, "hello-view.fxml", "log in page"));


    }

}
