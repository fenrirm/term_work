package com.example.javafxlogin;

import com.example.database_utils.DatabaseHandler;
import com.example.users_utils.Course;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CreateCourseController {

    @FXML
    private TextField courseName;

    @FXML
    private Button createButton;
    private String authorNickname;

    @FXML
    private TextField courseJoinKey;
    public void setAuthorNickname(String authorNickname){
        this.authorNickname = authorNickname;
    }

    public void handleCreateButton() throws SQLException {
        if(DatabaseHandler.joinKeyExists(courseJoinKey.getText().trim())){
            DBUtils.showAlertMessage("This join key is already exist! Try another.");
        } else if (courseJoinKey.getText().isBlank()||courseName.getText().isBlank()) {
            DBUtils.showAlertMessage("There are unfilled fields!");
        } else {
            Course newCourse = new Course(courseName.getText().trim(), authorNickname, courseJoinKey.getText().trim());
            DatabaseHandler.insertCourse(newCourse);
            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();
        }
    }
}
