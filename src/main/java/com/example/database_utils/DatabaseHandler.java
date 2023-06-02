package com.example.database_utils;


import com.example.users.Student;
import com.example.users.Teacher;
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

    public static boolean joinKeyExists(String joinKey) throws SQLException {
        String sql = "SELECT * FROM course WHERE joinKey = ?";
        try (Connection connection = getConnection();
             PreparedStatement psCheckJoinKeyExists = connection.prepareStatement(sql)) {

            psCheckJoinKeyExists.setString(1, joinKey);
            try (ResultSet resultSet = psCheckJoinKeyExists.executeQuery()) {
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

    public static void insertTeacher(Teacher teacher) throws SQLException {
        String sql = "INSERT INTO teacher (name, surname,nickname) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement psInsert = connection.prepareStatement(sql)) {
            psInsert.setString(1, teacher.getName());
            psInsert.setString(2, teacher.getSurname());
            psInsert.setString(3, teacher.getNickname());
            psInsert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void insertStudent(Student student) throws SQLException {
        String sql = "INSERT INTO student (name, surname,nickname) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement psInsert = connection.prepareStatement(sql)) {
            psInsert.setString(1, student.getName());
            psInsert.setString(2, student.getSurname());
            psInsert.setString(3, student.getNickname());
            psInsert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void insertCourse(Course course) throws SQLException {
        String sql = "INSERT INTO course (name,  authorNickname, joinKey) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement psInsert = connection.prepareStatement(sql)) {
            psInsert.setString(1, course.getName());
            psInsert.setString(2, course.getAuthor());
            psInsert.setString(3, course.getJoinKey());
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
            preparedStatement.setString(1, nickname);
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

    public static void addImage(String imagePath, String nickname) {
        String sql = "UPDATE user SET image = ? WHERE nickname = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, imagePath);
            preparedStatement.setString(2, nickname);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void saveTest(Test test) {
        try (Connection connection = getConnection()) {
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

    public static double getTestResult(int testId) {
        double testResult = -1;
        try (Connection connection = getConnection()) {
            String query = "SELECT testResult FROM student_passed_test WHERE testId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, testId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                testResult = resultSet.getDouble("testResult");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return testResult;
    }

    public static List<Test> getTestsByAuthor(String authorNickname) {
        List<Test> tests = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM test WHERE author = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, authorNickname);
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

    public static void addCourseTest(int courseId, int testId) {
        String query = "INSERT INTO course_test (courseId, testId) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, courseId);
            statement.setInt(2, testId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getCourseIdByTestId(int testId) {
        int courseId = -1; // Default value in case courseId is not found

        try (Connection connection = getConnection()) {
            String query = "SELECT courseId FROM student_active_test WHERE testId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, testId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    courseId = resultSet.getInt("courseId");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseId;
    }

    public static void saveStudentResult(int studentId, int testId, int courseId, double testResult) {
        try (Connection connection = getConnection()) {
            String sqlStatement = "INSERT INTO student_passed_test (studentId, testId,courseId, testResult) VALUES (?, ?, ?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, testId);
            preparedStatement.setInt(3, courseId);
            preparedStatement.setDouble(4, testResult);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addCourseStudent(int courseId, int studentId) {
        String query = "INSERT INTO course_student (courseId, studentId) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, courseId);
            statement.setInt(2, studentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getCourseIdByCourseKey(String courseKey) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM course WHERE joinKey = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, courseKey);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static String getCourseKeyByCourseID(int courseId) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM course WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, courseId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("joinKey");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int getStudentIdByNickname(String nickname) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM student WHERE nickname = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nickname);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("studentId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void addActiveTestsByCourse(int courseId, int studentId) {
        List<Test> activeTests = getTestsByCourseId(courseId);
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO student_active_test (studentId, testId, courseId) VALUES (?, ?, ?)";
            PreparedStatement psInsert = connection.prepareStatement(sql);
            psInsert.setInt(1, studentId);
            for (Test test : activeTests) {
                psInsert.setInt(2, test.getId());
                psInsert.setInt(3, courseId);
                psInsert.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void addTestToStudentActiveTest(int courseId, int testId) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {
            // Get the studentId from the "course_student" table based on the courseId
            String getStudentIdQuery = "SELECT studentId FROM course_student WHERE courseId = ?";
            PreparedStatement getStudentIdStatement = connection.prepareStatement(getStudentIdQuery);
            getStudentIdStatement.setInt(1, courseId);
            ResultSet studentIdResultSet = getStudentIdStatement.executeQuery();

            // Iterate through each studentId and insert the testId into "student_active_test" table
            while (studentIdResultSet.next()) {
                int studentId = studentIdResultSet.getInt("studentId");

                // Insert the testId and studentId into "student_active_test" table
                String insertTestQuery = "INSERT INTO student_active_test (studentId, testId, courseId) VALUES (?, ?, ?)";
                PreparedStatement insertTestStatement = connection.prepareStatement(insertTestQuery);
                insertTestStatement.setInt(1, studentId);
                insertTestStatement.setInt(2, testId);
                insertTestStatement.setInt(3, courseId);
                insertTestStatement.executeUpdate();
            }

            System.out.println("Test added to student_active_test successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding test to student_active_test: " + e.getMessage());
        }
    }

    public static void deleteStudentCourseFromDatabase(int studentId, int courseId) {
        try (Connection connection = DatabaseHandler.getConnection()) {
            // Delete the course's tests from the student_active_test table
            String deleteTestsQuery = "DELETE FROM student_active_test WHERE courseId = ?";
            PreparedStatement deleteTestsStatement = connection.prepareStatement(deleteTestsQuery);
            deleteTestsStatement.setInt(1, courseId);
            deleteTestsStatement.executeUpdate();

            String deletePassedTestsQuery = "DELETE FROM student_passed_test WHERE courseId = ?";
            PreparedStatement deletePassedTestsStatement = connection.prepareStatement(deletePassedTestsQuery);
            deletePassedTestsStatement.setInt(1, courseId);
            deletePassedTestsStatement.executeUpdate();

            // Delete the course from the student_course table
            String deleteCourseQuery = "DELETE FROM student_course WHERE studentId = ? AND courseId = ?";
            PreparedStatement deleteCourseStatement = connection.prepareStatement(deleteCourseQuery);
            deleteCourseStatement.setInt(1, studentId);
            deleteCourseStatement.setInt(2, courseId);
            deleteCourseStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteActiveTests(int courseId) {
        try (Connection connection = getConnection()) {
            String sql = "DELETE FROM student_active_test WHERE courseId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, courseId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static Student getStudentById(int studentId) {
        Student student = null;

        try (Connection connection = getConnection()){
            // Assuming you have a database connection named "connection"

            // Prepare the SQL statement
            String query = "SELECT * FROM student WHERE studentId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, studentId);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Process the result set
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String nickname = resultSet.getString("nickname");

                student = new Student(studentId, name, surname, nickname);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;
    }
    public static List<Student> getStudentsByCourseId(int courseId) {
        List<Student> students = new ArrayList<>();

        try (Connection connection = getConnection()){
            // Prepare the SQL statement
            String query = "SELECT studentId FROM course_student WHERE courseId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, courseId);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                int studentId = resultSet.getInt("studentId");
                System.out.println(studentId);
                Student student = getStudentById(studentId);
                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }



    public static List<Test> getActiveTestsByStudent(String studentNickname) {
        int studentId = getStudentIdByNickname(studentNickname);
        List<Test> tests = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM student_active_test WHERE studentId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                int testId = resultSet.getInt("testId");
                Test test = getTestFromDatabase(testId);
                tests.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tests;
    }

    public static List<Test> getPassedTestsByStudent(String studentNickname) {
        int studentId = getStudentIdByNickname(studentNickname);
        List<Test> tests = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM student_passed_test WHERE studentId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                int testId = resultSet.getInt("testId");
                Test test = getTestFromDatabase(testId);
                tests.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tests;
    }

    public static List<Test> getPassedTestsByStudentId(int studentId, int courseId) {
        List<Test> tests = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM student_passed_test WHERE studentId = ? AND courseId =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                int testId = resultSet.getInt("testId");
                double testResult = resultSet.getDouble("testResult");
                Test test = getTestFromDatabase(testId);
                Test newTest = new Test(testId, test.getName(), testResult);
                tests.add(newTest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tests;
    }

    public static void deletePassedTests() {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM student_active_test WHERE testId IN " +
                    "(SELECT testId FROM student_passed_test)";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Course> getCoursesByAuthor(String authorNickname) {
        List<Course> courses = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM course WHERE authorNickname = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, authorNickname);
            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                int courseId = resultSet.getInt("id");
                String courseName = resultSet.getString("name");
                String author = resultSet.getString("authorNickname");

                Course course = new Course(courseId, courseName, author);
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public static List<Course> getStudentCoursesByStudentNickname(String studentNickname) {
        List<Course> courses = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String sql = "SELECT c.* " +
                    "FROM course c " +
                    "JOIN course_student cs ON c.id = cs.courseId " +
                    "JOIN student s ON s.studentId = cs.studentId " +
                    "WHERE s.nickname = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentNickname);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int courseId = resultSet.getInt("id");
                String courseName = resultSet.getString("name");
                String authorNickname = resultSet.getString("authorNickname");
                // Retrieve other course information from the result set if needed

                Course course = new Course(courseId, courseName, authorNickname);
                courses.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public static List<Test> getActiveTestsByCourseId(int courseId, int studentId) {
        List<Test> tests = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM student_active_test WHERE courseId =? AND studentId = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, courseId);
            statement.setInt(2, studentId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int testId = resultSet.getInt("testId");
                Test test = getTestFromDatabase(testId);
                tests.add(test);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tests;
    }

    public static List<Test> getPassedTestsByCourseId(int courseId, int studentId) {
        List<Test> tests = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM student_passed_test WHERE courseId =? AND studentId = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, courseId);
            statement.setInt(2, studentId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int testId = resultSet.getInt("testId");
                Test test = getTestFromDatabase(testId);
                tests.add(test);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tests;
    }


    public static List<Test> getTestsByCourseId(int courseId) {
        List<Test> tests = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String sql = "SELECT t.* " +
                    "FROM test t " +
                    "JOIN course_test ct ON t.id = ct.testId " +
                    "JOIN course c ON c.id = ct.courseId " +
                    "WHERE c.id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, courseId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int testId = resultSet.getInt("id");
                String testName = resultSet.getString("name");
                String deadline = resultSet.getString("deadline");
                String authorNickname = resultSet.getString("author");

                Test test = new Test(testId, testName, deadline, authorNickname);
                tests.add(test);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tests;
    }


    public static void deleteTestFromDatabase(int testId) {
        try (Connection connection = DatabaseHandler.getConnection()) {
            // Delete the associated course_test records
            String deleteCourseTestQuery = "DELETE FROM course_test WHERE testId = ?";
            PreparedStatement deleteCourseTestStatement = connection.prepareStatement(deleteCourseTestQuery);
            deleteCourseTestStatement.setInt(1, testId);
            deleteCourseTestStatement.executeUpdate();
            // Delete the associated student_active_test records
            String deleteStudentActiveTestQuery = "DELETE FROM student_active_test WHERE testId = ?";
            PreparedStatement deleteStudentActiveTestStatement = connection.prepareStatement(deleteStudentActiveTestQuery);
            deleteStudentActiveTestStatement.setInt(1, testId);
            deleteStudentActiveTestStatement.executeUpdate();

            // Delete the associated student_passed_test records
            String deleteStudentPassedTestQuery = "DELETE FROM student_passed_test WHERE testId = ?";
            PreparedStatement deleteStudentPassedTestStatement = connection.prepareStatement(deleteStudentPassedTestQuery);
            deleteStudentPassedTestStatement.setInt(1, testId);
            deleteStudentPassedTestStatement.executeUpdate();

            // Delete the associated word questions first
            String deleteWordQuestionsQuery = "DELETE FROM word_question WHERE id IN (SELECT id FROM question WHERE testId = ?)";
            PreparedStatement deleteWordQuestionsStatement = connection.prepareStatement(deleteWordQuestionsQuery);
            deleteWordQuestionsStatement.setInt(1, testId);
            deleteWordQuestionsStatement.executeUpdate();

            // Delete the associated answer options
            String deleteAnswerOptionsQuery = "DELETE FROM answer WHERE questionId IN (SELECT id FROM question WHERE testId = ?)";
            PreparedStatement deleteAnswerOptionsStatement = connection.prepareStatement(deleteAnswerOptionsQuery);
            deleteAnswerOptionsStatement.setInt(1, testId);
            deleteAnswerOptionsStatement.executeUpdate();

            // Delete the associated correct answers
            String deleteCorrectAnswersQuery = "DELETE FROM correct_answer WHERE questionId IN (SELECT id FROM question WHERE testId = ?)";
            PreparedStatement deleteCorrectAnswersStatement = connection.prepareStatement(deleteCorrectAnswersQuery);
            deleteCorrectAnswersStatement.setInt(1, testId);
            deleteCorrectAnswersStatement.executeUpdate();

            // Delete the associated questions
            String deleteQuestionsQuery = "DELETE FROM question WHERE testId = ?";
            PreparedStatement deleteQuestionsStatement = connection.prepareStatement(deleteQuestionsQuery);
            deleteQuestionsStatement.setInt(1, testId);
            deleteQuestionsStatement.executeUpdate();

            // Delete the test from the Test table
            String deleteTestQuery = "DELETE FROM test WHERE id = ?";
            PreparedStatement deleteTestStatement = connection.prepareStatement(deleteTestQuery);
            deleteTestStatement.setInt(1, testId);
            deleteTestStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCourseFromDatabase(int courseId) {
        try (Connection connection = DatabaseHandler.getConnection()) {
            // Delete the associated course_test records
            String deleteCourseTestQuery = "DELETE FROM course_test WHERE courseId = ?";
            PreparedStatement deleteCourseTestStatement = connection.prepareStatement(deleteCourseTestQuery);
            deleteCourseTestStatement.setInt(1, courseId);
            deleteCourseTestStatement.executeUpdate();

            // Delete the associated course_student records
            String deleteCourseStudentQuery = "DELETE FROM course_student WHERE courseId = ?";
            PreparedStatement deleteCourseStudentStatement = connection.prepareStatement(deleteCourseStudentQuery);
            deleteCourseStudentStatement.setInt(1, courseId);
            deleteCourseStudentStatement.executeUpdate();

            String sql = "DELETE FROM student_active_test WHERE courseId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, courseId);
            preparedStatement.executeUpdate();

            String sql2 = "DELETE FROM student_passed_test WHERE courseId = ?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setInt(1, courseId);
            preparedStatement2.executeUpdate();

            String deleteCourseQuery = "DELETE FROM course WHERE id = ?";
            PreparedStatement deleteCourseStatement = connection.prepareStatement(deleteCourseQuery);
            deleteCourseStatement.setInt(1, courseId);
            deleteCourseStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*public static void deleteStudentCourseFromDatabase(int studentId, int courseId) {
        try (Connection connection = DatabaseHandler.getConnection()) {
            String deleteCourseQuery = "DELETE FROM student_course WHERE studentId = ? AND courseId = ?";
            PreparedStatement deleteCourseStatement = connection.prepareStatement(deleteCourseQuery);
            deleteCourseStatement.setInt(1, studentId);
            deleteCourseStatement.setInt(2, courseId);
            deleteCourseStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/


    public static Test getTestFromDatabase(int testId) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM test WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, testId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String testName = resultSet.getString("name");
                String testDeadline = resultSet.getString("deadline");
                String author = resultSet.getString("author");

                Test test = new Test(testId, testName, testDeadline, author);

                // Retrieve and set additional information for the test (questions, answers, and correct answers)
                List<Question> questions = getQuestionsForTest(testId);
                test.setQuestions(questions);

                return test;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if the test with the given ID is not found
    }

    public static List<Question> getQuestionsForTest(int testId) {
        List<Question> questions = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD)) {
            String questionQuery = "SELECT * FROM question WHERE testId = ?";
            PreparedStatement questionStatement = connection.prepareStatement(questionQuery);
            questionStatement.setInt(1, testId);
            ResultSet questionResultSet = questionStatement.executeQuery();

            while (questionResultSet.next()) {
                int questionId = questionResultSet.getInt("id");
                String questionText = questionResultSet.getString("questionText");
                int weight = questionResultSet.getInt("weight");
                String imagePath = questionResultSet.getString("imagePath");
                String questionType = questionResultSet.getString("type");

                Question question;
                switch (questionType) {
                    case "Single choice question" -> {
                        List<AnswerOption> answerOptions = getAnswerOptionsForQuestion(questionId, connection);
                        question = new SingleChoiceQuestion(questionText, answerOptions, weight);
                        int correctAnswerIndex = getCorrectAnswerIndexForQuestion(questionId, connection);
                        ((SingleChoiceQuestion) question).setCorrectAnswerIndex(correctAnswerIndex);
                    }
                    case "Multiple choice question" -> {
                        List<AnswerOption> answerOptions = getAnswerOptionsForQuestion(questionId, connection);
                        question = new MultipleChoiceQuestion(questionText, answerOptions, weight);
                        List<Integer> correctAnswerIndexes = getCorrectAnswerIndexesForQuestion(questionId, connection);
                        ((MultipleChoiceQuestion) question).addCorrectAnswerIndex(correctAnswerIndexes);
                    }
                    case "Word question" -> {
                        String correctAnswer = getCorrectAnswerForWordQuestion(questionId, connection);
                        question = new WordQuestion(questionText, correctAnswer, weight);
                    }
                    default -> {
                        // Handle unknown question types
                        continue;
                    }
                }

                question.setImage(imagePath);
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }


    private static List<AnswerOption> getAnswerOptionsForQuestion(int questionId, Connection connection) throws SQLException {
        List<AnswerOption> answerOptions = new ArrayList<>();

        String answerOptionQuery = "SELECT * FROM answer WHERE questionId = ?";
        PreparedStatement answerOptionStatement = connection.prepareStatement(answerOptionQuery);
        answerOptionStatement.setInt(1, questionId);
        ResultSet answerOptionResultSet = answerOptionStatement.executeQuery();

        while (answerOptionResultSet.next()) {
            String answerText = answerOptionResultSet.getString("answerText");
            AnswerOption answerOption = new AnswerOption(answerText);
            answerOptions.add(answerOption);
        }

        return answerOptions;
    }

    private static int getCorrectAnswerIndexForQuestion(int questionId, Connection connection) throws SQLException {
        String correctAnswerQuery = "SELECT answerIndex FROM correct_answer WHERE questionId = ?";
        PreparedStatement correctAnswerStatement = connection.prepareStatement(correctAnswerQuery);
        correctAnswerStatement.setInt(1, questionId);
        ResultSet correctAnswerResultSet = correctAnswerStatement.executeQuery();

        if (correctAnswerResultSet.next()) {
            return correctAnswerResultSet.getInt("answerIndex");
        }

        return -1; // Return -1 if no correct answer is found
    }

    private static List<Integer> getCorrectAnswerIndexesForQuestion(int questionId, Connection connection) throws SQLException {
        List<Integer> correctAnswerIndexes = new ArrayList<>();

        String correctAnswerQuery = "SELECT answerIndex FROM correct_answer WHERE questionId = ?";
        PreparedStatement correctAnswerStatement = connection.prepareStatement(correctAnswerQuery);
        correctAnswerStatement.setInt(1, questionId);
        ResultSet correctAnswerResultSet = correctAnswerStatement.executeQuery();

        while (correctAnswerResultSet.next()) {
            int answerIndex = correctAnswerResultSet.getInt("answerIndex");
            correctAnswerIndexes.add(answerIndex);
        }

        return correctAnswerIndexes;
    }

    private static String getCorrectAnswerForWordQuestion(int questionId, Connection connection) throws SQLException {
        String wordQuestionQuery = "SELECT correctAnswer FROM word_question WHERE id = ?";
        PreparedStatement wordQuestionStatement = connection.prepareStatement(wordQuestionQuery);
        wordQuestionStatement.setInt(1, questionId);
        ResultSet wordQuestionResultSet = wordQuestionStatement.executeQuery();

        if (wordQuestionResultSet.next()) {
            return wordQuestionResultSet.getString("correctAnswer");
        }

        return null; // Return null if no correct answer is found
    }


}

