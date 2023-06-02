package com.example.javafxlogin;

import com.example.database_utils.DatabaseHandler;
import com.example.users_utils.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.*;

public class TestPassingController implements Initializable {


    @FXML
    private CheckBox checkBoxAnswer1;

    @FXML
    private CheckBox checkBoxAnswer2;

    @FXML
    private CheckBox checkBoxAnswer3;

    @FXML
    private CheckBox checkBoxAnswer4;

    @FXML
    private CheckBox checkBoxAnswer5;

    @FXML
    private Button finishTestButton;

    @FXML
    private ImageView imageViewMultipleChoiceQuestion;

    @FXML
    private ImageView imageViewSingleChoiceQuestion;

    @FXML
    private ImageView imageViewWordQuestion;

    @FXML
    private Pane multipleChoiceQuestionPane;

    @FXML
    private Button nextQuestionButton;

    @FXML
    private Button previousQuestionButton;

    @FXML
    private Pane questionPane;

    @FXML
    private Label questionTextLabel;

    @FXML
    private StackPane questionTypeStackPane;

    @FXML
    private RadioButton rbAnswer1;

    @FXML
    private RadioButton rbAnswer2;

    @FXML
    private RadioButton rbAnswer3;

    @FXML
    private RadioButton rbAnswer4;

    @FXML
    private Pane singleChoiceQuestionPane;

    @FXML
    private AnchorPane testAnchorPane;

    @FXML
    private TextField tfWordAnswer;

    @FXML
    private Pane wordQuestionPane;

    private Test test;
    private List<Question> questions;

    private int studentId;

    private int questionIndex = 0;

    private Test passedTest;

    private boolean previousButtonIsClicked = false;

    private int testId;


    public void setTest(Test test) {
        this.test = test;
        this.testId = test.getId();
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void initializeController() {
        passedTest = new Test();
        showQuestion(questionIndex);

    }

    private void showQuestion(int questionIndex) {
        Question question = questions.get(questionIndex);
        questionTextLabel.setText(question.getQuestionText());
        switch (question.getType()) {
            case "Single choice question" -> {
                SingleChoiceQuestion singleChoiceQuestion = (SingleChoiceQuestion) question;
                ToggleGroup singleChoiceQuestionAnswers = new ToggleGroup();
                rbAnswer1.setToggleGroup(singleChoiceQuestionAnswers);
                rbAnswer2.setToggleGroup(singleChoiceQuestionAnswers);
                rbAnswer3.setToggleGroup(singleChoiceQuestionAnswers);
                rbAnswer4.setToggleGroup(singleChoiceQuestionAnswers);
                rbAnswer1.setText(singleChoiceQuestion.getAnswerOptions().get(0).getText());
                rbAnswer2.setText(singleChoiceQuestion.getAnswerOptions().get(1).getText());
                rbAnswer3.setText(singleChoiceQuestion.getAnswerOptions().get(2).getText());
                rbAnswer4.setText(singleChoiceQuestion.getAnswerOptions().get(3).getText());
                if (singleChoiceQuestion.getImagePath() != null) {
                    Image image = new Image(singleChoiceQuestion.getImagePath());
                    imageViewSingleChoiceQuestion.setImage(image);
                }
                imageViewSingleChoiceQuestion.setVisible(true);
                imageViewSingleChoiceQuestion.toFront();
                imageViewMultipleChoiceQuestion.setVisible(false);
                imageViewWordQuestion.setVisible(false);
                singleChoiceQuestionPane.setVisible(true);
                singleChoiceQuestionPane.toFront();
                multipleChoiceQuestionPane.setVisible(false);
                wordQuestionPane.setVisible(false);
            }
            case "Multiple choice question" -> {
                MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) question;
                checkBoxAnswer1.setText(multipleChoiceQuestion.getAnswerOptions().get(0).getText());
                checkBoxAnswer2.setText(multipleChoiceQuestion.getAnswerOptions().get(1).getText());
                checkBoxAnswer3.setText(multipleChoiceQuestion.getAnswerOptions().get(2).getText());
                checkBoxAnswer4.setText(multipleChoiceQuestion.getAnswerOptions().get(3).getText());
                checkBoxAnswer5.setText(multipleChoiceQuestion.getAnswerOptions().get(4).getText());
                if (multipleChoiceQuestion.getImagePath() != null) {
                    Image image = new Image(multipleChoiceQuestion.getImagePath());
                    imageViewMultipleChoiceQuestion.setImage(image);
                }
                imageViewMultipleChoiceQuestion.setVisible(true);
                imageViewMultipleChoiceQuestion.toFront();
                imageViewSingleChoiceQuestion.setVisible(false);
                imageViewWordQuestion.setVisible(false);
                multipleChoiceQuestionPane.setVisible(true);
                multipleChoiceQuestionPane.toFront();
                singleChoiceQuestionPane.setVisible(false);
                wordQuestionPane.setVisible(false);
            }
            case "Word question" -> {
                WordQuestion wordQuestion = (WordQuestion) question;
                if (wordQuestion.getImagePath() != null) {
                    Image image = new Image(wordQuestion.getImagePath());
                    imageViewWordQuestion.setImage(image);
                }
                imageViewWordQuestion.setVisible(true);
                imageViewWordQuestion.toFront();
                imageViewSingleChoiceQuestion.setVisible(false);
                imageViewMultipleChoiceQuestion.setVisible(false);
                wordQuestionPane.setVisible(true);
                wordQuestionPane.toFront();
                singleChoiceQuestionPane.setVisible(false);
                multipleChoiceQuestionPane.setVisible(false);
            }

        }
    }

    private void showAnsweredQuestion(int questionIndex) {
        Question question = passedTest.getQuestions().get(questionIndex);
        questionTextLabel.setText(question.getQuestionText());
        switch (question.getType()) {
            case "Single choice question" -> {
                SingleChoiceQuestion singleChoiceQuestion = (SingleChoiceQuestion) question;
                ToggleGroup singleChoiceQuestionAnswers = new ToggleGroup();
                rbAnswer1.setToggleGroup(singleChoiceQuestionAnswers);
                rbAnswer2.setToggleGroup(singleChoiceQuestionAnswers);
                rbAnswer3.setToggleGroup(singleChoiceQuestionAnswers);
                rbAnswer4.setToggleGroup(singleChoiceQuestionAnswers);
                rbAnswer1.setText(singleChoiceQuestion.getAnswerOptions().get(0).getText());
                rbAnswer2.setText(singleChoiceQuestion.getAnswerOptions().get(1).getText());
                rbAnswer3.setText(singleChoiceQuestion.getAnswerOptions().get(2).getText());
                rbAnswer4.setText(singleChoiceQuestion.getAnswerOptions().get(3).getText());
                int correctAnswerIndex = singleChoiceQuestion.getCorrectAnswerIndex();
                RadioButton selectedRadioButton = switch (correctAnswerIndex) {
                    case 0 -> rbAnswer1;
                    case 1 -> rbAnswer2;
                    case 2 -> rbAnswer3;
                    case 3 -> rbAnswer4;
                    default -> null;
                };
                if (selectedRadioButton != null) {
                    selectedRadioButton.setSelected(true);
                }
                if (singleChoiceQuestion.getImagePath() != null) {
                    Image image = new Image(singleChoiceQuestion.getImagePath());
                    imageViewSingleChoiceQuestion.setImage(image);
                }
                imageViewSingleChoiceQuestion.setVisible(true);
                imageViewSingleChoiceQuestion.toFront();
                imageViewMultipleChoiceQuestion.setVisible(false);
                imageViewWordQuestion.setVisible(false);
                singleChoiceQuestionPane.setVisible(true);
                singleChoiceQuestionPane.toFront();
                multipleChoiceQuestionPane.setVisible(false);
                wordQuestionPane.setVisible(false);
            }
            case "Multiple choice question" -> {
                MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) question;
                checkBoxAnswer1.setText(multipleChoiceQuestion.getAnswerOptions().get(0).getText());
                checkBoxAnswer2.setText(multipleChoiceQuestion.getAnswerOptions().get(1).getText());
                checkBoxAnswer3.setText(multipleChoiceQuestion.getAnswerOptions().get(2).getText());
                checkBoxAnswer4.setText(multipleChoiceQuestion.getAnswerOptions().get(3).getText());
                checkBoxAnswer5.setText(multipleChoiceQuestion.getAnswerOptions().get(4).getText());
                List<Integer> correctAnswerIndices = multipleChoiceQuestion.getCorrectAnswerIndexes();
                checkBoxAnswer1.setSelected(correctAnswerIndices.contains(0));
                checkBoxAnswer2.setSelected(correctAnswerIndices.contains(1));
                checkBoxAnswer3.setSelected(correctAnswerIndices.contains(2));
                checkBoxAnswer4.setSelected(correctAnswerIndices.contains(3));
                checkBoxAnswer5.setSelected(correctAnswerIndices.contains(4));

                if (multipleChoiceQuestion.getImagePath() != null) {
                    Image image = new Image(multipleChoiceQuestion.getImagePath());
                    imageViewMultipleChoiceQuestion.setImage(image);
                }
                imageViewMultipleChoiceQuestion.setVisible(true);
                imageViewMultipleChoiceQuestion.toFront();
                imageViewSingleChoiceQuestion.setVisible(false);
                imageViewWordQuestion.setVisible(false);
                multipleChoiceQuestionPane.setVisible(true);
                multipleChoiceQuestionPane.toFront();
                singleChoiceQuestionPane.setVisible(false);
                wordQuestionPane.setVisible(false);
            }
            case "Word question" -> {
                WordQuestion wordQuestion = (WordQuestion) question;
                tfWordAnswer.setText(wordQuestion.getCorrectAnswer());
                if (wordQuestion.getImagePath() != null) {
                    Image image = new Image(wordQuestion.getImagePath());
                    imageViewWordQuestion.setImage(image);
                }
                imageViewWordQuestion.setVisible(true);
                imageViewWordQuestion.toFront();
                imageViewSingleChoiceQuestion.setVisible(false);
                imageViewMultipleChoiceQuestion.setVisible(false);
                wordQuestionPane.setVisible(true);
                wordQuestionPane.toFront();
                singleChoiceQuestionPane.setVisible(false);
                multipleChoiceQuestionPane.setVisible(false);
            }

        }
    }

    public void handlePreviousQuestionButton() {
        previousButtonIsClicked = true;
        if (questionIndex > 0) {
            questionIndex--;
            showAnsweredQuestion(questionIndex);
        }
    }

    public void addPreviousQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            Question question = questions.get(index);
            switch (question.getType()) {
                case "Single choice question" -> {
                    SingleChoiceQuestion singleChoiceQuestion = (SingleChoiceQuestion) question;
                    SingleChoiceQuestion newSingleChoiceQuestion = new SingleChoiceQuestion(question.getQuestionText(), singleChoiceQuestion.getAnswerOptions(), singleChoiceQuestion.getWeight());
                    newSingleChoiceQuestion.setCorrectAnswerIndex(getSelectedRadioButtonIndex());
                    passedTest.addQuestion(questionIndex, newSingleChoiceQuestion);
                }
                case "Multiple choice question" -> {
                    MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) question;
                    MultipleChoiceQuestion newMultipleChoiceQuestion = new MultipleChoiceQuestion(question.getQuestionText(), multipleChoiceQuestion.getAnswerOptions(), multipleChoiceQuestion.getWeight());
                    newMultipleChoiceQuestion.addCorrectAnswerIndex(getSelectedCheckBoxIndexes());
                    passedTest.addQuestion(questionIndex, newMultipleChoiceQuestion);
                }
                case "Word question" -> {
                    WordQuestion wordQuestion = (WordQuestion) question;
                    WordQuestion newWordQuestion = new WordQuestion(question.getQuestionText(), tfWordAnswer.getText(), wordQuestion.getWeight());
                    passedTest.addQuestion(questionIndex, newWordQuestion);
                }
            }
        }
    }

    public void handleNextQuestionButton() {
        Question question = questions.get(questionIndex);
        if (questionIndex == questions.size() - 1) {
            switch (question.getType()) {
                case "Single choice question" -> {
                    SingleChoiceQuestion singleChoiceQuestion = (SingleChoiceQuestion) question;
                    SingleChoiceQuestion newSingleChoiceQuestion = new SingleChoiceQuestion(question.getQuestionText(), singleChoiceQuestion.getAnswerOptions(), singleChoiceQuestion.getWeight());
                    newSingleChoiceQuestion.setCorrectAnswerIndex(getSelectedRadioButtonIndex());
                    passedTest.addQuestion(newSingleChoiceQuestion);
                }
                case "Multiple choice question" -> {
                    MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) question;
                    MultipleChoiceQuestion newMultipleChoiceQuestion = new MultipleChoiceQuestion(question.getQuestionText(), multipleChoiceQuestion.getAnswerOptions(), multipleChoiceQuestion.getWeight());
                    newMultipleChoiceQuestion.addCorrectAnswerIndex(getSelectedCheckBoxIndexes());
                    passedTest.addQuestion(newMultipleChoiceQuestion);
                }
                case "Word question" -> {
                    WordQuestion wordQuestion = (WordQuestion) question;
                    WordQuestion newWordQuestion = new WordQuestion(question.getQuestionText(), tfWordAnswer.getText().trim().toLowerCase(), wordQuestion.getWeight());
                    passedTest.addQuestion(newWordQuestion);
                }
            }
            DBUtils.showAlertMessage("That is the last question. Finish the test, please.");
            return;
        }
        if(previousButtonIsClicked){
            addPreviousQuestion(questionIndex);
            previousButtonIsClicked = false;
            /*switch (question.getType()){
                case "Single choice question" ->{
                    SingleChoiceQuestion singleChoiceQuestion = (SingleChoiceQuestion) question;
                    SingleChoiceQuestion newSingleChoiceQuestion = new SingleChoiceQuestion(question.getQuestionText(),singleChoiceQuestion.getAnswerOptions(),singleChoiceQuestion.getWeight() );
                    newSingleChoiceQuestion.setCorrectAnswerIndex(getSelectedRadioButtonIndex());
                    passedTest.addQuestion(questionIndex,newSingleChoiceQuestion);
                }
                case "Multiple choice question" ->{
                    MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) question;
                    MultipleChoiceQuestion newMultipleChoiceQuestion = new MultipleChoiceQuestion(question.getQuestionText(), multipleChoiceQuestion.getAnswerOptions(),multipleChoiceQuestion.getWeight());
                    newMultipleChoiceQuestion.addCorrectAnswerIndex(getSelectedCheckBoxIndexes());
                    passedTest.addQuestion(questionIndex,newMultipleChoiceQuestion);
                }
                case "Word question" ->{
                    WordQuestion wordQuestion = (WordQuestion) question;
                    WordQuestion newWordQuestion = new WordQuestion(question.getQuestionText(), tfWordAnswer.getText(), wordQuestion.getWeight());
                    passedTest.addQuestion(questionIndex,newWordQuestion);
                }
            }*/
        }else {
            switch (question.getType()) {
                case "Single choice question" -> {
                    SingleChoiceQuestion singleChoiceQuestion = (SingleChoiceQuestion) question;
                    SingleChoiceQuestion newSingleChoiceQuestion = new SingleChoiceQuestion(question.getQuestionText(), singleChoiceQuestion.getAnswerOptions(), singleChoiceQuestion.getWeight());
                    newSingleChoiceQuestion.setCorrectAnswerIndex(getSelectedRadioButtonIndex());
                    passedTest.addQuestion(newSingleChoiceQuestion);
                }
                case "Multiple choice question" -> {
                    MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) question;
                    MultipleChoiceQuestion newMultipleChoiceQuestion = new MultipleChoiceQuestion(question.getQuestionText(), multipleChoiceQuestion.getAnswerOptions(), multipleChoiceQuestion.getWeight());
                    newMultipleChoiceQuestion.addCorrectAnswerIndex(getSelectedCheckBoxIndexes());
                    passedTest.addQuestion(newMultipleChoiceQuestion);
                }
                case "Word question" -> {
                    WordQuestion wordQuestion = (WordQuestion) question;
                    WordQuestion newWordQuestion = new WordQuestion(question.getQuestionText(), tfWordAnswer.getText(), wordQuestion.getWeight());
                    passedTest.addQuestion(newWordQuestion);
                }
            }
        }

        questionIndex++;
        showQuestion(questionIndex);

    }

    private List<Integer> getSelectedCheckBoxIndexes() {
        List<Integer> selectedIndexes = new ArrayList<>();
        CheckBox[] checkBoxes = {checkBoxAnswer1, checkBoxAnswer2, checkBoxAnswer3, checkBoxAnswer4, checkBoxAnswer5};

        for (int i = 0; i < checkBoxes.length; i++) {
            if (checkBoxes[i].isSelected()) {
                selectedIndexes.add(i);
            }
        }

        return selectedIndexes;
    }

    private int getSelectedRadioButtonIndex() {
        RadioButton[] radioButtons = {rbAnswer1, rbAnswer2, rbAnswer3, rbAnswer4};

        for (int i = 0; i < radioButtons.length; i++) {
            if (radioButtons[i].isSelected()) {
                return i;
            }
        }

        return -1; // Return a default value or handle the case when no radio button is selected
    }


    private double getMarkForTest() {
        double mark = 0;

        List<Question> testQuestions = this.test.getQuestions();
        List<Question> passedQuestions = this.passedTest.getQuestions();

        System.out.println("Size of passedQuestions: " + passedQuestions.size());

        System.out.println(testQuestions.size());
        for (int i = 0; i < testQuestions.size(); i++) {
            Question testQuestion = testQuestions.get(i);
            Question passedQuestion = passedQuestions.get(i);

            if (testQuestion.getType().equals(passedQuestion.getType())) {
                switch (testQuestion.getType()) {
                    case "Single choice question" -> {
                        SingleChoiceQuestion testSingleChoiceQuestion = (SingleChoiceQuestion) testQuestion;
                        SingleChoiceQuestion passedSingleChoiceQuestion = (SingleChoiceQuestion) passedQuestion;
                        if (testSingleChoiceQuestion.getCorrectAnswerIndex() == passedSingleChoiceQuestion.getCorrectAnswerIndex()) {
                            mark += testSingleChoiceQuestion.getWeight();
                        }
                    }
                    case "Multiple choice question" -> {
                        MultipleChoiceQuestion testMultipleChoiceQuestion = (MultipleChoiceQuestion) testQuestion;
                        MultipleChoiceQuestion passedMultipleChoiceQuestion = (MultipleChoiceQuestion) passedQuestion;
                        List<Integer> testCorrectAnswerIndices = testMultipleChoiceQuestion.getCorrectAnswerIndexes();
                        List<Integer> passedCorrectAnswerIndices = passedMultipleChoiceQuestion.getCorrectAnswerIndexes();
                        int notCorrectAnswers = testCorrectAnswerIndices.size() - passedCorrectAnswerIndices.size();
                        System.out.println(notCorrectAnswers);
                        int matchedCorrectAnswers = countMatchedCorrectAnswers(testCorrectAnswerIndices, passedCorrectAnswerIndices);
                        System.out.println(matchedCorrectAnswers);
                        double weightPerCorrectAnswer = (double) testMultipleChoiceQuestion.getWeight() / testCorrectAnswerIndices.size();
                        System.out.println(weightPerCorrectAnswer);
                        double questionMark = weightPerCorrectAnswer * matchedCorrectAnswers - notCorrectAnswers*weightPerCorrectAnswer/2.0;
                        System.out.println(questionMark);
                        mark += Math.ceil(questionMark);
                    }
                    case "Word question" -> {
                        WordQuestion testWordQuestion = (WordQuestion) testQuestion;
                        WordQuestion passedWordQuestion = (WordQuestion) passedQuestion;
                        if (testWordQuestion.getCorrectAnswer().equals(passedWordQuestion.getCorrectAnswer())) {
                            mark += testWordQuestion.getWeight();
                        }
                    }
                }
            }
        }

        return mark;
    }

    private int countMatchedCorrectAnswers(List<Integer> testCorrectAnswerIndices, List<Integer> passedCorrectAnswerIndices) {
        int count = 0;

        for (int index : passedCorrectAnswerIndices) {
            if (testCorrectAnswerIndices.contains(index)) {
                count++;
            }
        }

        return count;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        finishTestButton.setOnAction(event -> {
            double result = getMarkForTest();
            int courseId=DatabaseHandler.getCourseIdByTestId(testId);
            DatabaseHandler.saveStudentResult(studentId, testId,courseId, result);
            DBUtils.changeSceneToTestResultController(event, "test_result_window.fxml", "result", result, 210, 80);
            DatabaseHandler.deletePassedTests();
        });
    }
}
