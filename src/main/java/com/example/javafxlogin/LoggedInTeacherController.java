package com.example.javafxlogin;

import com.example.database_utils.DatabaseHandler;
import com.example.users_utils.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoggedInTeacherController extends LoggedInController implements Initializable {


    @FXML
    private AnchorPane anchorPane;

    @FXML
    private AnchorPane anchorPane2;

    @FXML
    private Button button_log_out;

    @FXML
    private Label courseLabel;

    @FXML
    private ListView<Label> courseListView;

    @FXML
    private Pane coursePane;

    @FXML
    private Button createCourseButton;

    @FXML
    private Button createTestButton;

    @FXML
    private Button deleteCourseButton;

    @FXML
    private Button deleteTestButton;

    @FXML
    private Button enLanguageButton;

    @FXML
    private ImageView imageView;

    @FXML
    private Button myCoursesButton;

    @FXML
    private Button myStudentsButton;

    @FXML
    private Button myTestsButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Label nicknameLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private StackPane stackPane;

    @FXML
    private Label studentLabel;

    @FXML
    private ListView<Label> studentListView;

    @FXML
    private Pane studentPane;

    @FXML
    private StackPane teacherStackPane;

    @FXML
    private Label testLabel;

    @FXML
    private  ListView<Label> testListView;

    @FXML
    private Pane testPane;

    @FXML
    private Button uaLanguageButton;

    @FXML
    private Button uploadButton;

    private static List<Test> myTests = new ArrayList<>();

    public void handleMyTestsButton() {
        showPane(testPane);
        populateTestListView();
    }

    public void handleMyStudentsButton() {
        showPane(studentPane);
    }

    public void handleMyCoursesButton() {
        showPane(coursePane);
    }
    private void showPane(Pane pane) {
        testPane.setVisible(pane == testPane);
        studentPane.setVisible(pane == studentPane);
        coursePane.setVisible(pane == coursePane);

        pane.toFront();
        testPane.toBack();
        studentPane.toBack();
    }



    private  void populateTestListView() {
        ObservableList<Label> items = FXCollections.observableArrayList();
        testListView.setItems(items); // Set the testListView items with the observable list

        // Retrieve the tests from the database
        List<Test> tests = DatabaseHandler.getTestsByAuthor(nicknameLabel.getText());
        for (Test test : tests) {
            int testId = test.getId();
            String testName = test.getName();

            // Create a Label to display the test name
            testLabel = new Label(testName);
            setLabelStyle(testLabel);
            testLabel.setId(String.valueOf(testId));

            // Add the Label to the observable list
            items.add(testLabel);
        }
    }

    private void updateTestListView() {
        ObservableList<Label> items = testListView.getItems();
        items.clear(); // Clear the existing items in the list view

        // Retrieve the updated tests from the database
        List<Test> tests = DatabaseHandler.getTestsByAuthor(nicknameLabel.getText());

        for (Test test : tests) {
            int testId = test.getId();
            String testName = test.getName();

            // Create a Label to display the test name
            testLabel = new Label(testName);
            setLabelStyle(testLabel);
            testLabel.setId(String.valueOf(testId));

            // Add the Label to the observable list
            items.add(testLabel);
        }
    }

    public void setLabelStyle(Label label){
        Font font = Font.font("Consolas", FontWeight.BOLD, 12);
        label.setFont(font);
    }



    @Override
    public void setUserInformation(String name, String surname, String position, String nickname, String image) {
        super.setUserInformation(name, surname, position, nickname, image);
        populateTestListView();
    }

    @Override
    public void uploadPhoto() {
        super.uploadPhoto();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_log_out.setOnAction(event -> DBUtils.changeScene(event, "hello-view.fxml", "Log In"));
        createTestButton.setOnAction(event ->  {
            DBUtils.changeScene(event, "test_choice.fxml",  260, 210);
            updateTestListView();

        });



    }

}
