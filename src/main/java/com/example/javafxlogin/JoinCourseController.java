package com.example.javafxlogin;

import com.example.database_utils.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class JoinCourseController {

    @FXML
    private Button buttonJoin;

    @FXML
    private TextField courseKeyField;

    private String nickname;

    public void setStudentNickname(String nickname){
        this.nickname = nickname;
    }

    public void handleJoinButton(){
        int courseId = DatabaseHandler.getCourseIdByCourseKey(courseKeyField.getText().trim());
        int studentId = DatabaseHandler.getStudentIdByNickname(nickname);
        DatabaseHandler.addCourseStudent(courseId,studentId);
        DatabaseHandler.addActiveTestsByCourse(courseId, studentId);
        Stage stage = (Stage) buttonJoin.getScene().getWindow();
        stage.close();
    }
}
