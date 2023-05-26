package com.example.javafxlogin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class TestChoiceController implements Initializable {

    @FXML
    private Button buttonCreateFromTemplates;

    @FXML
    private Button buttonCreateMyOwn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonCreateFromTemplates.setOnAction(event -> DBUtils.changeScene(event, "template_choice.fxml", "Template choice", 452, 283));
        buttonCreateMyOwn.setOnAction(event -> DBUtils.changeScene(event,"own_test.fxml", 700, 700));
    }
}
