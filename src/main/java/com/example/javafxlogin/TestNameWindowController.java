package com.example.javafxlogin;

import com.example.database_utils.DatabaseHandler;
import com.example.users_utils.Test;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TestNameWindowController implements Initializable {
    @FXML
    private Button saveButton;

    @FXML
    private TextField tfTestDeadline;

    @FXML
    private TextField tfTestName;

    private String authorNickname;

    private Test test;
    public void setAuthorNickname(String authorNickname){
        this.authorNickname =  authorNickname;
    }

    public void setTest(Test test) {
        this.test = new Test(test);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveButton.setOnAction(event -> {
            if(tfTestName!=null && tfTestDeadline!=null ) {
                test.setName(tfTestName.getText().trim());
                test.setDeadline(tfTestDeadline.getText().trim());
                test.setAuthorNickname(authorNickname);
                DatabaseHandler.saveTest(test);
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();

            }else {
                DBUtils.showAlertMessage("There are unfilled fields!");
            }
        });
    }
}
