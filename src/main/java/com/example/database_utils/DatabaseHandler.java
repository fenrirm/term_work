package com.example.database_utils;

import com.example.users.User;
import com.example.users_utils.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/javafx";
    private static final String DATABASE_USERNAME = "fenrirm";
    private static final String DATABASE_PASSWORD = "fenrirm";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
    }

    public static boolean userExists(String nickname) throws SQLException {
        String sql = "SELECT * FROM user WHERE nickname = ?";
        try (Connection connection = getConnection();
             PreparedStatement psCheckUserExists = connection.prepareStatement(sql)) {

            psCheckUserExists.setString(1, nickname);
            try (ResultSet resultSet = psCheckUserExists.executeQuery()) {
                return resultSet.isBeforeFirst();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void insertUser(User user) throws SQLException {
        String sql = "INSERT INTO user (name, surname, position, nickname, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement psInsert = connection.prepareStatement(sql)) {
            psInsert.setString(1, user.getName());
            psInsert.setString(2, user.getSurname());
            psInsert.setString(3, user.getPosition());
            psInsert.setString(4, user.getNickname());
            psInsert.setString(5, user.getPassword());
            psInsert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static User getUser(String nickname, String password) throws SQLException {
        String sql = "SELECT * FROM user WHERE nickname = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,nickname);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    return null;
                }
                while (resultSet.next()) {
                    String retrievedName = resultSet.getString("name");
                    String retrievedSurname = resultSet.getString("surname");
                    String retrievedPosition = resultSet.getString("position");
                    String retrievedNickname = resultSet.getString("nickname");
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedImage = resultSet.getString("image");
                    if (retrievedPassword.equals(password)) {
                        return new User(retrievedName, retrievedSurname, retrievedPosition, retrievedNickname, retrievedPassword, retrievedImage);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    public static void addImage(String imagePath, String nickname){
        String sql = "UPDATE user SET image = ? WHERE nickname = ?";
        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, imagePath);
            preparedStatement.setString(2, nickname);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static void saveTest(Test test) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {
            // Save the test details to the Test table
            String testQuery = "INSERT INTO test (name, deadline, author) VALUES (?, ?, ?)";
            PreparedStatement testStatement = connection.prepareStatement(testQuery, Statement.RETURN_GENERATED_KEYS);
            //PreparedStatement testStatement = connection.prepareStatement(testQuery);
            testStatement.setString(1, test.getName());
            testStatement.setString(2, test.getDeadline());
            testStatement.setString(3, test.getAuthorNickname());
            testStatement.executeUpdate();

            // Get the generated test ID
            int testId;
            try (ResultSet generatedKeys = testStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    testId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve generated test ID.");
                }
            }

            // Save each question to the Question table
            String questionQuery = "INSERT INTO question (testId, questionText, weight, imagePath, type) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement questionStatement = connection.prepareStatement(questionQuery, Statement.RETURN_GENERATED_KEYS);

            for (Question question : test.getQuestions()) {
                questionStatement.setInt(1, testId);
                questionStatement.setString(2, question.getQuestionText());
                questionStatement.setInt(3, question.getWeight());
                questionStatement.setString(4, question.getImagePath());
                questionStatement.setString(5, question.getType());
                questionStatement.executeUpdate();

                // Get the generated question ID
                int questionId;
                try (ResultSet generatedKeys = questionStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        questionId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Failed to retrieve generated question ID.");
                    }
                }

                // Save answer options and correct answers based on the question type
                if (question instanceof SingleChoiceQuestion) {
                    saveSingleChoiceQuestion(connection, (SingleChoiceQuestion) question, questionId);
                } else if (question instanceof MultipleChoiceQuestion) {
                    saveMultipleChoiceQuestion(connection, (MultipleChoiceQuestion) question, questionId);
                } else if (question instanceof WordQuestion) {
                    saveWordQuestion(connection, (WordQuestion) question, questionId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void saveSingleChoiceQuestion(Connection connection, SingleChoiceQuestion question, int questionId) throws SQLException {
        String answerOptionQuery = "INSERT INTO answer (questionId, answerText) VALUES (?, ?)";
        PreparedStatement answerOptionStatement = connection.prepareStatement(answerOptionQuery);
        answerOptionStatement.setInt(1, questionId);
        for (AnswerOption answerOption : question.getAnswerOptions()) {
            answerOptionStatement.setString(2, answerOption.getText());
            answerOptionStatement.executeUpdate();
        }

        String correctAnswerQuery = "INSERT INTO correct_answer (questionId, answerIndex) VALUES (?, ?)";
        PreparedStatement correctAnswerStatement = connection.prepareStatement(correctAnswerQuery);
        correctAnswerStatement.setInt(1, questionId);
        correctAnswerStatement.setInt(2, question.getCorrectAnswerIndex());
        correctAnswerStatement.executeUpdate();
    }

    private static void saveMultipleChoiceQuestion(Connection connection, MultipleChoiceQuestion question, int questionId) throws SQLException {
        String answerOptionQuery = "INSERT INTO answer (questionId, answerText) VALUES (?, ?)";
        PreparedStatement answerOptionStatement = connection.prepareStatement(answerOptionQuery);
        answerOptionStatement.setInt(1, questionId);
        for (AnswerOption answerOption : question.getAnswerOptions()) {
            answerOptionStatement.setString(2, answerOption.getText());
            answerOptionStatement.executeUpdate();
        }

        String correctAnswerQuery = "INSERT INTO correct_answer (questionId, answerIndex) VALUES (?, ?)";
        PreparedStatement correctAnswerStatement = connection.prepareStatement(correctAnswerQuery);
        correctAnswerStatement.setInt(1, questionId);
        for (Integer correctAnswerIndex : question.getCorrectAnswerIndexes()) {
            correctAnswerStatement.setInt(2, correctAnswerIndex);
            correctAnswerStatement.executeUpdate();
        }
    }

    private static void saveWordQuestion(Connection connection, WordQuestion question, int questionId) throws SQLException {
        String wordQuestionQuery = "INSERT INTO word_question (id, correctAnswer) VALUES (?, ?)";
        PreparedStatement wordQuestionStatement = connection.prepareStatement(wordQuestionQuery);
        wordQuestionStatement.setInt(1, questionId);
        wordQuestionStatement.setString(2, question.getCorrectAnswer());
        wordQuestionStatement.executeUpdate();
    }

    public static List<Test> getTestsByAuthor(String authorPhone) {
        List<Test> tests = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM test WHERE author = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, authorPhone);
            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                int testId = resultSet.getInt("id");
                String testName = resultSet.getString("name");
                String testDeadline = resultSet.getString("deadline");
                String author = resultSet.getString("author");

                Test test = new Test(testId, testName, testDeadline, author);
                tests.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tests;
    }
}

