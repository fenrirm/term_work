package com.example.javafxlogin;

import com.example.database_utils.DatabaseHandler;
import com.example.users.Student;
import com.example.users_utils.Course;
import com.example.users_utils.Question;
import com.example.users_utils.Test;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
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
    private Pane overviewPane;
    @FXML
    private Label studentLabel;

    @FXML
    private ListView<Label> studentListView;


    @FXML
    private StackPane teacherStackPane;

    @FXML
    private Label testLabel;

    @FXML
    private ListView<Label> testListView;

    @FXML
    private ListView<Label> testOverviewListView;

    @FXML
    private ListView<Label> courseOverviewListView;


    @FXML
    private Pane testPane;
    @FXML
    private Pane coursePane;

    @FXML
    private Button uaLanguageButton;

    @FXML
    private Button uploadButton;

    @FXML
    private Pane testSettingPane;

    @FXML
    private ListView<Label> testForSettingListView;

    @FXML
    private ListView<Label> courseForSettingListView;

    @FXML
    private Button setButton;

    @FXML
    private Button setTestButton;

    @FXML
    private Button resultsButton;

    @FXML
    private ListView<Label> courseResultListView;

    @FXML
    private ListView<Label> studentResultListView;

    @FXML
    private ListView<Label> testResultListView;

    @FXML
    private Pane resultPane;

    @FXML
    private Button viewTestButton;

    @FXML
    private Label joinKeyLabel;


    private static List<Test> myTests = new ArrayList<>();


    public void handleMyTestsButton() {
        showPane(testPane);
        populateTestListView();
    }

    public void handleMyCoursesButton() {
        showPane(coursePane);
        populateCourseListView();

    }


    private void showPane(Pane pane) {
        testPane.setVisible(pane == testPane);
        coursePane.setVisible(pane == coursePane);

        pane.toFront();
    }

    public void handleOverviewButton() {
        populateCourseOverviewListView();
        populateTestOverviewListView();
        overviewPane.setVisible(true);
        overviewPane.toFront();
        coursePane.setVisible(false);
        testPane.setVisible(false);
        coursePane.toBack();
        testPane.toBack();
    }

    public void handleSetTestButton() {
        populateTestForSettingListView();
        populateCourseOverviewListView();
        populateCourseForSettingListView();
        testSettingPane.setVisible(true);
        testSettingPane.toFront();
        overviewPane.setVisible(false);
        overviewPane.toBack();
        coursePane.setVisible(false);
        testPane.setVisible(false);
        coursePane.toBack();
        testPane.toBack();

    }

    public void handleResultsButton() {
        populateCourseResultListView();
        populateStudentResultListView();
        populateTestResultListView();
        resultPane.setVisible(true);
        resultPane.toFront();
        testSettingPane.setVisible(false);
        testSettingPane.toBack();
        overviewPane.setVisible(false);
        overviewPane.toBack();
        coursePane.setVisible(false);
        testPane.setVisible(false);
        coursePane.toBack();
        testPane.toBack();

    }

    public void handleSetButton() {
        Label test = testForSettingListView.getSelectionModel().getSelectedItem();
        Label course = courseForSettingListView.getSelectionModel().getSelectedItem();
        if (test != null && course != null) {
            int testIndex = Integer.parseInt(test.getId());
            int courseIndex = Integer.parseInt(course.getId());
            DatabaseHandler.addCourseTest(courseIndex, testIndex);
            DatabaseHandler.addTestToStudentActiveTest(courseIndex, testIndex);
            testForSettingListView.getSelectionModel().clearSelection();
            courseForSettingListView.getSelectionModel().clearSelection();
        }

    }


    private void populateTestListView() {
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

    private void populateCourseResultListView() {
        ObservableList<Label> items = FXCollections.observableArrayList();
        courseResultListView.setItems(items);

        List<Course> courses = DatabaseHandler.getCoursesByAuthor(nicknameLabel.getText());

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

    private void populateStudentResultListView() {
        Label selectedCourse = courseResultListView.getSelectionModel().getSelectedItem();
        if (selectedCourse!=null) {
            System.out.println("not null");
            // Retrieve tests for the selected course
            int courseId = Integer.parseInt(courseResultListView.getSelectionModel().getSelectedItem().getId());

            List<Student> students = DatabaseHandler.getStudentsByCourseId(courseId);
            ObservableList<Label> items = FXCollections.observableArrayList(); // Create the ObservableList

            for (Student student : students) {
                int studentId = student.getStudentId();
                String studentName = student.getName();
                String studentSurname = student.getSurname();

                studentLabel = new Label(studentName + " " + studentSurname);
                setLabelStyle(studentLabel);
                studentLabel.setId(String.valueOf(studentId));

                items.add(studentLabel); // Add each student label to the ObservableList
            }

            studentResultListView.setItems(items); // Set the items for the studentResultListView
        }
    }


    private void populateTestResultListView() {
        Label selectedStudent = studentResultListView.getSelectionModel().getSelectedItem();
        if (selectedStudent!=null) {
            // Retrieve tests for the selected student
            int studentId = Integer.parseInt(studentResultListView.getSelectionModel().getSelectedItem().getId());
            int courseId = Integer.parseInt(courseResultListView.getSelectionModel().getSelectedItem().getId());
            List<Test> tests = DatabaseHandler.getPassedTestsByStudentId(studentId,courseId);

            ObservableList<Label> items = FXCollections.observableArrayList();

            for (Test test : tests) {
                int testId = test.getId();
                String testName = test.getName();
                double testResult = test.getTestResult();

                testLabel = new Label(testName + " Result:" + testResult);
                setLabelStyle(testLabel);
                testLabel.setId(String.valueOf(testId));

                items.add(testLabel);
            }
            testResultListView.setItems(items);
        }
    }

    private void updateCourseListView() {
        ObservableList<Label> items = courseListView.getItems();
        items.clear(); // Clear the existing items in the list view

        // Retrieve the updated courses from the database
        List<Course> courses = DatabaseHandler.getCoursesByAuthor(nicknameLabel.getText());

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

    private void populateCourseListView() {
        ObservableList<Label> items = FXCollections.observableArrayList();
        courseListView.setItems(items); // Set the testListView items with the observable list

        // Retrieve the courses from the database
        List<Course> courses = DatabaseHandler.getCoursesByAuthor(nicknameLabel.getText());
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

    private void populateTestForSettingListView() {
        ObservableList<Label> items = FXCollections.observableArrayList();
        testForSettingListView.setItems(items); // Set the testListView items with the observable list

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

    private void updateTestForSettingListView() {
        ObservableList<Label> items = testForSettingListView.getItems();
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

    private void updateCourseForSettingListView() {
        ObservableList<Label> items = courseForSettingListView.getItems();
        items.clear(); // Clear the existing items in the list view

        // Retrieve the updated courses from the database
        List<Course> courses = DatabaseHandler.getCoursesByAuthor(nicknameLabel.getText());

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

    private void populateCourseForSettingListView() {
        ObservableList<Label> items = FXCollections.observableArrayList();
        courseForSettingListView.setItems(items); // Set the testListView items with the observable list

        // Retrieve the courses from the database
        List<Course> courses = DatabaseHandler.getCoursesByAuthor(nicknameLabel.getText());
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

    private void updateTestOverviewListView() {
        ObservableList<Label> items = testOverviewListView.getItems();
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

    private void populateTestOverviewListView() {
        ObservableList<Label> items = FXCollections.observableArrayList();
        testOverviewListView.setItems(items);

        if (courseOverviewListView.getSelectionModel().isEmpty()) {
            List<Test> tests = DatabaseHandler.getTestsByAuthor(nicknameLabel.getText());
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
            List<Test> tests = DatabaseHandler.getTestsByCourseId(courseId);
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

    public int getCourseId() {
        Label selectedItem = courseOverviewListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            return Integer.parseInt(selectedItem.getId());
        } else {
            return -1;
        }
    }

    private void updateCourseOverviewListView() {
        ObservableList<Label> items = courseOverviewListView.getItems();
        items.clear(); // Clear the existing items in the list view

        // Retrieve the updated courses from the database
        List<Course> courses = DatabaseHandler.getCoursesByAuthor(nicknameLabel.getText());

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

    private void populateCourseOverviewListView() {
        ObservableList<Label> items = FXCollections.observableArrayList();
        courseOverviewListView.setItems(items); // Set the testListView items with the observable list

        // Retrieve the courses from the database
        List<Course> courses = DatabaseHandler.getCoursesByAuthor(nicknameLabel.getText());
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


    private void deleteSelectedTest() {
        Label selectedLabel = testListView.getSelectionModel().getSelectedItem();
        if (selectedLabel != null) {
            int testId = Integer.parseInt(selectedLabel.getId());
            DatabaseHandler.deleteTestFromDatabase(testId);
        }
    }

    private void deleteSelectedCourse() {
        Label selectedLabel = courseListView.getSelectionModel().getSelectedItem();
        if (selectedLabel != null) {
            int courseId = Integer.parseInt(selectedLabel.getId());
            DatabaseHandler.deleteCourseFromDatabase(courseId);
            //DatabaseHandler.deleteActiveTests(courseId);
        }
    }

    public void setLabelStyle(Label label) {
        Font font = Font.font("Consolas", FontWeight.BOLD, 12);
        if (label != null) {
            label.setFont(font);
        }
    }


    @Override
    public void setUserInformation(String name, String surname, String position, String nickname, String image) {
        super.setUserInformation(name, surname, position, nickname, image);
        populateTestListView();
        populateCourseListView();
        populateTestOverviewListView();
        populateCourseOverviewListView();
        populateCourseForSettingListView();
        populateTestForSettingListView();
    }

    @Override
    public void uploadPhoto() {
        super.uploadPhoto();
    }

    public String getJoinKey() {
        Label selectedCourse = courseListView.getSelectionModel().getSelectedItem();
        int courseId = -1;
        if (selectedCourse != null) {
            courseId = Integer.parseInt(selectedCourse.getId());
            return DatabaseHandler.getCourseKeyByCourseID(courseId);
        } else {
            return "";
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        courseListView.getSelectionModel().selectedItemProperty().addListener((observableValue, label, t1) -> {
            joinKeyLabel.setText("Join Key:");
            joinKeyLabel.setText(joinKeyLabel.getText() + getJoinKey());
        });


        button_log_out.setOnAction(event -> DBUtils.changeScene(event, "hello-view.fxml", "Log In"));

        createTestButton.setOnAction(event -> {
            DBUtils.changeSceneToTestController(event, "own_test.fxml", "test", nicknameLabel.getText(), 1252, 637);
        });
        deleteTestButton.setOnAction(event -> {
            deleteSelectedTest();
            updateTestListView();
            updateTestOverviewListView();
            updateTestForSettingListView();
        });

        createCourseButton.setOnAction(event -> {
            DBUtils.changeSceneToCreateCourseController(event, "create_course_window.fxml", "course", nicknameLabel.getText().trim(), 230, 160);
        });

        deleteCourseButton.setOnAction(event -> {
            deleteSelectedCourse();
            updateCourseListView();
            updateCourseOverviewListView();
            updateCourseForSettingListView();
        });

        viewTestButton.setOnAction(event -> {
            int index = Integer.parseInt(testListView.getSelectionModel().getSelectedItem().getId());
            System.out.println(index);
            Test test = DatabaseHandler.getTestFromDatabase(index);

            //List<Question> questions = DatabaseHandler.getQuestionsForTest(index);
            if (test != null) {
                DBUtils.changeSceneToViewTestController(event, "test_view.fxml", "Name:" + test.getName() + " Deadline:" + test.getDeadline(), test, 1030, 480);
            } else {
                System.out.println("test is null");
            }
        });

        courseOverviewListView.getSelectionModel().selectedItemProperty().addListener((observableValue, label, t1) -> {
            populateTestOverviewListView();
        });

        courseResultListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            populateStudentResultListView();
        });

        studentResultListView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->{
            populateTestResultListView();
        });




    }

}
