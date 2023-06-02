package com.example.javafxlogin;

import com.example.database_utils.DatabaseHandler;
import com.example.users.Student;
import com.example.users.Teacher;
import com.example.users_utils.Question;
import com.example.users_utils.Test;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;
import com.example.users.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DBUtils {

    private static final int WINDOW_WIDTH = 740;
    private static final int WINDOW_HEIGHT = 500;

    public static final String TEACHER_FXML = "logged-in.fxml";
    public static final String STUDENT_FXML = "logged-in-student.fxml";

    public static void changeScene(ActionEvent event, String fxmlFile, String title, double width, double height) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(DBUtils.class.getResource(fxmlFile)));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, width, height));
            // Center the window on the screen
            double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
            double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
            double windowX = (screenWidth - width) / 2;
            double windowY = (screenHeight - height) / 2;
            stage.setX(windowX);
            stage.setY(windowY);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void changeScene(ActionEvent event, String fxmlFile, double width, double height) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(DBUtils.class.getResource(fxmlFile)));

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, width, height));

            double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
            double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
            double windowX = (screenWidth - width) / 2;
            double windowY = (screenHeight - height) / 2;
            newStage.setX(windowX);
            newStage.setY(windowY);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeScene(ActionEvent event, String fxmlFile, String title) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(DBUtils.class.getResource(fxmlFile)));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void changeScene(ActionEvent event, String fxmlFile, String title, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            Parent root = loader.load();
            LoggedInController loggedInController = loader.getController();
            if (user != null) {
                loggedInController.setUserInformation(user.getName(), user.getSurname(), user.getPosition(), user.getNickname(), user.getImagePath());
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void changeSceneToViewTestController(ActionEvent event, String fxmlFile, String title, Test test, /*List<Question> questions,*/ double width, double height) {
        try {


            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            Parent root = loader.load();

            TestViewController testViewController = loader.getController();
            testViewController.setTest(test); // Set the test in the controller
            System.out.println(test.getName());
            testViewController.setQuestions(test.getQuestions()); // Set the questions in the controller

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, width, height));
            newStage.setTitle(title);
            double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
            double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
            double windowX = (screenWidth - width) / 2;
            double windowY = (screenHeight - height) / 2;
            newStage.setX(windowX);
            newStage.setY(windowY);


            newStage.setOnShown(e -> {
                // Call a separate method to initialize the controller
                Platform.runLater(testViewController::initializeController);
            });

            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void changeSceneToTestPassingController(ActionEvent event, String fxmlFile, String title, Test test, int studentId, double width, double height) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            Parent root = loader.load();

            TestPassingController testPassingController = loader.getController();
            testPassingController.setTest(test); // Set the test in the controller
            testPassingController.setQuestions(test.getQuestions()); // Set the questions in the controller
            testPassingController.setStudentId(studentId);// Set the student id in the controller

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, width, height));
            newStage.setTitle(title);
            double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
            double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
            double windowX = (screenWidth - width) / 2;
            double windowY = (screenHeight - height) / 2;
            newStage.setX(windowX);
            newStage.setY(windowY);


            newStage.setOnShown(e -> {
                // Call a separate method to initialize the controller
                Platform.runLater(testPassingController::initializeController);
            });

            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void changeSceneToCreateCourseController(ActionEvent event, String fxmlFile, String title, String authorNickname, double width, double height) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            Parent root = loader.load();
            CreateCourseController createCourseController = loader.getController();
            if (authorNickname != null) {
                createCourseController.setAuthorNickname(authorNickname);
            }
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, width, height));
            newStage.setTitle(title);
            double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
            double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
            double windowX = (screenWidth - width) / 2;
            double windowY = (screenHeight - height) / 2;
            newStage.setX(windowX);
            newStage.setY(windowY);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void changeSceneToTestResultController(ActionEvent event, String fxmlFile, String title, double testResult, double width, double height) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            Parent root = loader.load();
            TestResultController testResultController = loader.getController();
            testResultController.setTestResult(testResult);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, width, height));
            // Center the window on the screen
            double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
            double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
            double windowX = (screenWidth - width) / 2;
            double windowY = (screenHeight - height) / 2;
            stage.setX(windowX);
            stage.setY(windowY);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void changeSceneToJoinCourseController(ActionEvent event, String fxmlFile, String title, String studentNickname, double width, double height) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            Parent root = loader.load();
            JoinCourseController joinCourseController = loader.getController();
            if (studentNickname != null) {
                joinCourseController.setStudentNickname(studentNickname);
            }
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, width, height));
            newStage.setTitle(title);
            double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
            double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
            double windowX = (screenWidth - width) / 2;
            double windowY = (screenHeight - height) / 2;
            newStage.setX(windowX);
            newStage.setY(windowY);

            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void changeSceneToTestController(ActionEvent event, String fxmlFile, String title, String authorNickname, double width, double height) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            Parent root = loader.load();
            MyTestController myTestController = loader.getController();
            if (authorNickname != null) {
                myTestController.setAuthorNickname(authorNickname);
            }
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, width, height));
            newStage.setTitle(title);
            double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
            double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
            double windowX = (screenWidth - width) / 2;
            double windowY = (screenHeight - height) / 2;
            newStage.setX(windowX);
            newStage.setY(windowY);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void changeSceneToSaveTestController(ActionEvent event, String fxmlFile, String title, String authorNickname,Test test, double width, double height) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            Parent root = loader.load();
            TestNameWindowController testNameWindowController = loader.getController();
            if (authorNickname != null && test!=null) {
                testNameWindowController.setAuthorNickname(authorNickname);
                testNameWindowController.setTest(test);
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, width, height));
            // Center the window on the screen
            double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
            double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
            double windowX = (screenWidth - width) / 2;
            double windowY = (screenHeight - height) / 2;
            stage.setX(windowX);
            stage.setY(windowY);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void changeSceneToPreviewTestController(ActionEvent event, String fxmlFile, String title, Test test, double width, double height) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            Parent root = loader.load();
            TestPreviewController testPreviewController = loader.getController();
            if (test!=null) {
                testPreviewController.setTest(test); // Set the test in the controller
                testPreviewController.setQuestions(test.getQuestions());
            }
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, width, height));
            newStage.setTitle(title);
            double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
            double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
            double windowX = (screenWidth - width) / 2;
            double windowY = (screenHeight - height) / 2;
            newStage.setX(windowX);
            newStage.setY(windowY);
            newStage.setOnShown(e -> {
                // Call a separate method to initialize the controller
                Platform.runLater(testPreviewController::initializeController);
            });

            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void signUpUser(ActionEvent event, String name, String surname,
                                  String position, String nickname, String password) {

        User user = new User(name, surname, position, nickname, password, null);
        try {
            if (DatabaseHandler.userExists(nickname)) {
                showAlertMessage("User with this number is already exists");
            } else {
                DatabaseHandler.insertUser(user);
                if (position.equals("Teacher")) {
                    Teacher teacher = new Teacher(name, surname, nickname);
                    DatabaseHandler.insertTeacher(teacher);
                } else if (position.equals("Student")) {
                    Student student = new Student(name, surname, nickname);
                    DatabaseHandler.insertStudent(student);
                }
                String fxmlFile = checkForPosition(position);
                changeScene(event, fxmlFile, user.getPosition(), user);
            }
        } catch (SQLException e) {
            showAlertMessage("Error signing up user");
            e.printStackTrace();
        }

    }

    public static void logInUser(ActionEvent event, String nickname, String password) {
        try {
            User user = DatabaseHandler.getUser(nickname, password);
            if (user == null) {
                showAlertMessage("User was not found! Check provided credentials.");
            } else {
                if (user.getPassword().equals(password)) {
                    String fxmlFile = checkForPosition(user.getPosition());
                    changeScene(event, fxmlFile, user.getPosition(), user);
                } else {
                    showAlertMessage("Provided password are incorrect!");
                }
            }
        } catch (SQLException e) {
            showAlertMessage("Error logining in");
            e.printStackTrace();
        }
    }


    public static String checkForPosition(String retrievedPosition) {
        return switch (retrievedPosition) {
            case "Teacher" -> TEACHER_FXML;
            case "Student" -> STUDENT_FXML;
            default -> throw new IllegalArgumentException("Invalid position " + retrievedPosition);
        };

    }

    public static void showAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }


}
