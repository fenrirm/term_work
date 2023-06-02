package com.example.javafxlogin;

import com.example.database_utils.DatabaseHandler;
import com.example.users_utils.Course;
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
import java.util.List;
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
    private Button joinToCourseButton;

    @FXML
    private ImageView imageView;

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
    private Label testLabel;

    @FXML
    private ListView<Label> activeTestListView;

    @FXML
    private Pane testPane;

    @FXML
    private Button leaveCourseButton;

    @FXML
    private Button uploadButton;

    @FXML
    private Pane overviewPane;

    @FXML
    private ListView<Label> courseOverviewListView;

    @FXML
    private ListView<Label> activeTestOverviewListView;

    @FXML
    private ListView<Label> passedTestListView;

    @FXML
    private ListView<Label> passedTestOverviewListView;

    @FXML
    private Button showTestButton;


    public void handleMyTestsButton() {
        populateActiveTestListView();
        populatePassedTestListView();
        testPane.setVisible(true);
        coursePane.setVisible(false);
        testPane.toFront();
        coursePane.toBack();

    }

    public void handleMyCoursesButton() {
        populateCourseListView();
        coursePane.setVisible(true);
        testPane.setVisible(false);
        coursePane.toFront();
        testPane.toBack();

    }

    public void handleOverviewButton() {
        populateActiveTestOverviewListView();
        populatePassedTestOverviewListView();
        populateCourseOverviewListView();
        overviewPane.setVisible(true);
        overviewPane.toFront();
        coursePane.setVisible(false);
        testPane.setVisible(false);
        coursePane.toBack();
        testPane.toBack();

    }

    public void setLabelStyle(Label label) {
        Font font = Font.font("Consolas", FontWeight.BOLD, 12);
        label.setFont(font);
    }


    private void updateCourseListView() {
        ObservableList<Label> items = courseListView.getItems();
        items.clear(); // Clear the existing items in the list view

        // Retrieve the updated courses from the database
        List<Course> courses = DatabaseHandler.getStudentCoursesByStudentNickname(this.nicknameLabel.getText());

        if (courses.size() != 0) {
            for (Course course : courses) {
                int courseId = course.getId();
                String courseName = course.getName();

                // Create a Label to display the course name
                courseLabel = new Label(courseName);
                setLabelStyle(testLabel);
                courseLabel.setId(String.valueOf(courseId));

                // Add the Label to the observable list
                items.add(courseLabel);
            }
        }
    }

    private void populateCourseListView() {
        ObservableList<Label> items = FXCollections.observableArrayList();
        courseListView.setItems(items); // Set the testListView items with the observable list

        // Retrieve the courses from the database
        List<Course> courses = DatabaseHandler.getStudentCoursesByStudentNickname(this.nicknameLabel.getText());
        for (Course course : courses) {
            int courseId = course.getId();
            String courseName = course.getName();

            // Create a Label to display the course name
            courseLabel = new Label(courseName);
            setLabelStyle(courseLabel);
            courseLabel.setId(String.valueOf(courseId));

            // Add the Label to the observable list
            items.add(courseLabel);
        }
    }

    private void populateActiveTestListView() {
        ObservableList<Label> items = FXCollections.observableArrayList();
        activeTestListView.setItems(items); // Set the testListView items with the observable list

        // Retrieve the tests from the database
        List<Test> tests = DatabaseHandler.getActiveTestsByStudent(this.nicknameLabel.getText());

        for (Test test : tests) {
            int testId = test.getId();
            String testName = test.getName();


            // Create a Label to display the test name
            testLabel = new Label(testName + " Deadline:" + test.getDeadline());
            setLabelStyle(testLabel);
            testLabel.setId(String.valueOf(testId));

            // Add the Label to the observable list
            items.add(testLabel);
        }

    }

    private void populatePassedTestListView() {
        ObservableList<Label> items = FXCollections.observableArrayList();
        passedTestListView.setItems(items); // Set the testListView items with the observable list

        // Retrieve the tests from the database
        List<Test> tests = DatabaseHandler.getPassedTestsByStudent(this.nicknameLabel.getText());
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


    private void populateActiveTestOverviewListView() {
        ObservableList<Label> items = FXCollections.observableArrayList();
        activeTestOverviewListView.setItems(items);

        if (courseOverviewListView.getSelectionModel().isEmpty()) {
            // If no course is selected, retrieve all tests for the student
            List<Test> tests = DatabaseHandler.getActiveTestsByStudent(this.nicknameLabel.getText());
            for (Test test : tests) {
                int testId = test.getId();
                String testName = test.getName();

                testLabel = new Label(testName + " Deadline:" + test.getDeadline());
                setLabelStyle(testLabel);
                testLabel.setId(String.valueOf(testId));

                items.add(testLabel);
            }
        } else {
            // Retrieve tests for the selected course
            int courseId = getCourseId();
            int studentId = DatabaseHandler.getStudentIdByNickname(this.nicknameLabel.getText());
            List<Test> tests = DatabaseHandler.getActiveTestsByCourseId(courseId, studentId);
            for (Test test : tests) {
                int testId = test.getId();
                String testName = test.getName();

                testLabel = new Label(testName);
                setLabelStyle(testLabel);
                testLabel.setId(String.valueOf(testId));

                items.add(testLabel);
            }
        }
    }

    private void populatePassedTestOverviewListView() {
        ObservableList<Label> items = FXCollections.observableArrayList();
        passedTestOverviewListView.setItems(items);

        if (courseOverviewListView.getSelectionModel().isEmpty()) {
            // If no course is selected, retrieve all tests for the student
            List<Test> tests = DatabaseHandler.getPassedTestsByStudent(nicknameLabel.getText());
            for (Test test : tests) {
                int testId = test.getId();
                String testName = test.getName();

                testLabel = new Label(testName);
                setLabelStyle(testLabel);
                testLabel.setId(String.valueOf(testId));

                items.add(testLabel);
            }
        } else {
            // Retrieve tests for the selected course
            int courseId = getCourseId();
            int studentId = DatabaseHandler.getStudentIdByNickname(this.nicknameLabel.getText());
            List<Test> tests = DatabaseHandler.getPassedTestsByCourseId(courseId, studentId);
            for (Test test : tests) {
                int testId = test.getId();
                String testName = test.getName();

                testLabel = new Label(testName);
                setLabelStyle(testLabel);
                testLabel.setId(String.valueOf(testId));

                items.add(testLabel);
            }
        }
    }


    private void updateCourseOverviewListView() {
        ObservableList<Label> items = courseOverviewListView.getItems();
        items.clear(); // Clear the existing items in the list view

        // Retrieve the updated courses from the database
        List<Course> courses = DatabaseHandler.getStudentCoursesByStudentNickname(this.nicknameLabel.getText());

        for (Course course : courses) {
            int courseId = course.getId();
            String courseName = course.getName();

            // Create a Label to display the course name
            courseLabel = new Label(courseName);
            setLabelStyle(courseLabel);
            courseLabel.setId(String.valueOf(courseId));

            // Add the Label to the observable list
            items.add(courseLabel);
        }
    }

    private void populateCourseOverviewListView() {
        ObservableList<Label> items = FXCollections.observableArrayList();
        courseOverviewListView.setItems(items); // Set the testListView items with the observable list

        // Retrieve the courses from the database
        List<Course> courses = DatabaseHandler.getStudentCoursesByStudentNickname(this.nicknameLabel.getText());
        for (Course course : courses) {
            int courseId = course.getId();
            String courseName = course.getName();

            // Create a Label to display the course name
            courseLabel = new Label(courseName);
            setLabelStyle(courseLabel);
            courseLabel.setId(String.valueOf(courseId));

            // Add the Label to the observable list
            items.add(courseLabel);
        }
    }

    public void deleteSelectedCourse() {
        Label selectedLabel = courseListView.getSelectionModel().getSelectedItem();
        if (selectedLabel != null) {
            int courseId = Integer.parseInt(selectedLabel.getId());
            int studentId = DatabaseHandler.getStudentIdByNickname(this.nicknameLabel.getText());
            DatabaseHandler.deleteStudentCourseFromDatabase(courseId, studentId);
        }

    }

    public int getCourseId() {
        Label selectedItem = courseOverviewListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            return Integer.parseInt(selectedItem.getId());
        } else {
            return -1;
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        button_log_out.setOnAction(event -> DBUtils.changeScene(event, "hello-view.fxml", "Log In"));
        joinToCourseButton.setOnAction(event -> {
            DBUtils.changeSceneToJoinCourseController(event, "join_course_window.fxml", "course", this.nicknameLabel.getText(), 225, 120);

        });
        leaveCourseButton.setOnAction(event -> {
            deleteSelectedCourse();
            updateCourseListView();
            updateCourseOverviewListView();

        });

        courseOverviewListView.getSelectionModel().selectedItemProperty().addListener((observableValue, label, t1) -> {
            populateActiveTestOverviewListView();
            populatePassedTestOverviewListView();
        });

        passTestButton.setOnAction(event -> {
            int testId = Integer.parseInt(activeTestListView.getSelectionModel().getSelectedItem().getId());
            int studentId = DatabaseHandler.getStudentIdByNickname(this.nicknameLabel.getText());
            System.out.println(testId);
            Test test = DatabaseHandler.getTestFromDatabase(testId);
            if (test != null) {
                DBUtils.changeSceneToTestPassingController(event, "test_passing.fxml", "test_passing", test, studentId, 1030, 415);
            } else {
                System.out.println("test is null");
            }
        });

        showTestButton.setOnAction(event -> {
            int testId = Integer.parseInt(passedTestListView.getSelectionModel().getSelectedItem().getId());
            Test test = DatabaseHandler.getTestFromDatabase(testId);
            double testResult = DatabaseHandler.getTestResult(testId);
            DBUtils.changeSceneToViewTestController(event, "test_view.fxml", "Your result: " + testResult, test, 1030, 480);
        });


    }

    @Override
    public void setUserInformation(String name, String surname, String position, String nickname, String image) {
        super.setUserInformation(name, surname, position, nickname, image);
        populateCourseListView();
        populateActiveTestListView();
        populateCourseOverviewListView();
        populateActiveTestOverviewListView();
        populatePassedTestOverviewListView();
    }

    @Override
    public void uploadPhoto() {
        super.uploadPhoto();
    }


}
