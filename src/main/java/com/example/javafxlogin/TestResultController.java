package com.example.javafxlogin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TestResultController {
    @FXML
    private Label resultLabel;

    public void setTestResult(double testResult){
        this.resultLabel.setText(String.valueOf(testResult));
    }
}
