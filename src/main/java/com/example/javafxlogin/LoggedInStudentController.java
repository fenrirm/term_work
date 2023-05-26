package com.example.javafxlogin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInStudentController extends LoggedInController implements Initializable {

    @FXML
    private Button MyCoursesButton;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private AnchorPane anchorPane2;

    @FXML
    private Button button_log_out;

    @FXML
    private Label controlLabel;

    @FXML
    private Label courseLabel;

    @FXML
    private ListView<Label> courseListView;

    @FXML
    private Pane coursePane;

    @FXML
    private Button enrollToCourseButton;

    @FXML
    private ImageView imageView;

    @FXML
    private Button myTeachersButton;

    @FXML
    private Button myTestsButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Button passTestButton;

    @FXML
    private Label nicknameLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private StackPane stackPane;

    @FXML
    private StackPane studentStackPane;

    @FXML
    private Label teacherLabel;

    @FXML
    private ListView<Label> teacherListView;

    @FXML
    private Pane teacherPane;

    @FXML
    private Label testLabel;

    @FXML
    private ListView<Label> testListView;

    @FXML
    private Pane testPane;

    @FXML
    private Button unenrollFromCourseButton;

    @FXML
    private Button uploadButton;

    public void handleMyTestsButton(){
        testPane.setVisible(true);
        teacherPane.setVisible(false);
        coursePane.setVisible(false);
        testPane.toFront();
        coursePane.toBack();
        teacherPane.toBack();

    }
    public void handleMyTeachersButton(){
        teacherPane.setVisible(true);
        testPane.setVisible(false);
        coursePane.setVisible(false);
        teacherPane.toFront();
        coursePane.toBack();
        testPane.toBack();

    }
    public void handleMyCoursesButton(){
        coursePane.setVisible(true);
        testPane.setVisible(false);
        teacherPane.setVisible(false);
        coursePane.toFront();
        testPane.toBack();
        teacherPane.toBack();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_log_out.setOnAction(event -> DBUtils.changeScene(event, "hello-view.fxml", "Log In"));

        testListView.getItems().add(testLabel);
        teacherListView.getItems().add(teacherLabel);
        courseListView.getItems().add(courseLabel);
    }

    @Override
    public void setUserInformation(String name, String surname, String position, String nickname, String image) {
        super.setUserInformation(name, surname, position, nickname, image);
    }

    @Override
    public void uploadPhoto() {
        super.uploadPhoto();
    }


}
