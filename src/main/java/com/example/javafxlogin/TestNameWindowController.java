package com.example.javafxlogin;

import com.example.database_utils.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TestNameWindowController implements Initializable {
    @FXML
    private Button saveButton;

    @FXML
    private TextField tfTestDeadline;

    @FXML
    private TextField tfTestName;

    @FXML
    private TextField tfAuthorNumber;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveButton.setOnAction(event -> {
            if(tfTestName!=null && tfTestDeadline!=null) {
                MyTestController.getMyTest().setName(tfTestName.getText());
                MyTestController.getMyTest().setDeadline(tfTestDeadline.getText());
                MyTestController.getMyTest().setAuthorPhone(tfAuthorNumber.getText());
                DatabaseHandler.saveTest(MyTestController.getMyTest());


            }else {
                DBUtils.showAlertMessage("There are unfilled fields!");
            }
        });
    }
}
