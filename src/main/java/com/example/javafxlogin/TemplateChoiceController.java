package com.example.javafxlogin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

public class TemplateChoiceController implements Initializable {

    @FXML
    private Button enLanguageButton;

    @FXML
    private Button uaLanguageButton;

    @FXML
    private Button buttonCreateTemplate1;

    @FXML
    private Button buttonCreateTemplate2;

    @FXML
    private Button buttonCreateTemplate3;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonCreateTemplate1.setOnAction(event -> {DBUtils.changeScene(event, "template_1.fxml","Template_1",413, 841);});
    }


}
