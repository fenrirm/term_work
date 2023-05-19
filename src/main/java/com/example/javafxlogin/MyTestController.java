package com.example.javafxlogin;

import com.example.users_utils.AnswerOption;
import com.example.users_utils.Question;
import com.example.users_utils.SingleChoiceQuestion;
import com.example.users_utils.Test;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MyTestController implements Initializable {

    @FXML
    StackPane stackPane;
    @FXML
    private ToggleGroup answers;

    @FXML
    private Button buttonAddQuestion;

    @FXML
    private Button buttonChangeQuestion;

    @FXML
    private Button buttonDeleteQuestion;

    @FXML
    private Button buttonDone;

    @FXML
    private Button buttonSaveTest;

    @FXML
    private Label labelQuestion;

    @FXML
    private Pane constructorPane;

    @FXML
    private TextField fieldAnswer1;

    @FXML
    private TextField fieldAnswer2;

    @FXML
    private TextField fieldAnswer3;

    @FXML
    private TextField fieldAnswer4;

    @FXML
    private TextField fieldQuestionText;

    @FXML
    private ChoiceBox<Integer> choiceBoxWeight;

    @FXML
    private Label labelWeight;


    @FXML
    private ToggleGroup finishedAnswers;

    @FXML
    private Pane finishedPane;

    @FXML
    private Label labelQuestionText;

    @FXML
    private RadioButton rbAnswer1;

    @FXML
    private RadioButton rbAnswer2;

    @FXML
    private RadioButton rbAnswer3;

    @FXML
    private RadioButton rbAnswer4;

    @FXML
    private RadioButton rbFinishedAnswer1;

    @FXML
    private RadioButton rbFinishedAnswer2;

    @FXML
    private RadioButton rbFinishedAnswer3;

    @FXML
    private RadioButton rbFinishedAnswer4;

    @FXML
    private ListView<Label> listOfQuestions;
    Test myTest = new Test();
    private int questionIndex = 0;

    private boolean isQuestionFinished = true;
    private final Integer[] WEIGHTS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};




    public void handleDoneButtonAction() {
        if (!areFieldsFilled()) {
            DBUtils.showAlertMessage("There are unfilled fields!");
            return;
        }

        List<AnswerOption> answerOptions = new ArrayList<>();
        answerOptions.add(new AnswerOption(fieldAnswer1.getText()));
        answerOptions.add(new AnswerOption(fieldAnswer2.getText()));
        answerOptions.add(new AnswerOption(fieldAnswer3.getText()));
        answerOptions.add(new AnswerOption(fieldAnswer4.getText()));

        int selectedRadioButtonIndex = answers.getToggles().indexOf(answers.getSelectedToggle());
        if (selectedRadioButtonIndex == -1) {
            DBUtils.showAlertMessage("Please select the correct answer!");
            return;
        }

        AnswerOption correctAnswer = new AnswerOption(answers.getSelectedToggle().toString());
        Question newQuestion = new SingleChoiceQuestion(fieldQuestionText.getText(), answerOptions,
                correctAnswer, choiceBoxWeight.getValue());
        newQuestion.setCorrectAnswerIndex(selectedRadioButtonIndex);
        myTest.addQuestion(newQuestion);


        System.out.println(questionIndex);
        displayQuestionDetails(questionIndex);
        System.out.println("Added successfully");

        constructorPane.setVisible(false);
        finishedPane.setVisible(true);
        constructorPane.toBack();
        finishedPane.toFront();

        isQuestionFinished = true;

    }

    private void showTheListOfQuestions() {
        for (int i = 0; i < myTest.getQuestions().size(); i++) {
            System.out.println(i+"-["+myTest.getQuestions().get(i).getQuestionText()+"]");
            for (int j = 0; j < myTest.getQuestions().get(i).getAnswerOptions().size(); j++) {
                System.out.println(j+"-["+myTest.getQuestions().get(i).getAnswerOptions().get(j).getAnswerText()+"]");
            }
        }
    }

    private void displayQuestionDetails(int index) {
        List<Question> questions = myTest.getQuestions();
        if (questions.isEmpty()) {
            System.out.println("Your list of questions is empty!");
            return;
        }

        if (index < 0 || index >= questions.size()) {
            System.out.println("Invalid question index!");
            return;
        }

        Question question = questions.get(index);
        labelQuestionText.setText((index + 1) + "." + question.getQuestionText());

        List<AnswerOption> answerOptions = question.getAnswerOptions();
        if (answerOptions.size() >= 4) {
            rbFinishedAnswer1.setText(answerOptions.get(0).getAnswerText());
            rbFinishedAnswer2.setText(answerOptions.get(1).getAnswerText());
            rbFinishedAnswer3.setText(answerOptions.get(2).getAnswerText());
            rbFinishedAnswer4.setText(answerOptions.get(3).getAnswerText());
        }

        labelWeight.setText("Weight: " + question.getWeight());

        int selectedRadioButtonIndex = question.getCorrectAnswerIndex();
        Toggle selectedToggle = finishedAnswers.getToggles().get(selectedRadioButtonIndex);
        if (selectedToggle != null) {
            selectedToggle.setSelected(true);
        }
    }

    private boolean areFieldsFilled() {
        return isFieldFilled(fieldAnswer1) &&
                isFieldFilled(fieldAnswer2) &&
                isFieldFilled(fieldAnswer3) &&
                isFieldFilled(fieldAnswer4) &&
                isFieldFilled(fieldQuestionText)&&
                choiceBoxWeight.getValue()!=null;
    }

    private boolean isFieldFilled(TextField textField) {
        return textField.getText() != null && !textField.getText().isEmpty();
    }

    public void addQuestion() {
        if (isQuestionFinished) {

            labelQuestion = new Label();
            setLabelStyle();
            clearFinishedPane();
            clearConstructorPane();

            finishedPane.setVisible(false);
            constructorPane.setVisible(true);
            constructorPane.toFront();
            finishedPane.toBack();

            listOfQuestions.getItems().add(labelQuestion);
            updateLabelQuestionNumbers();


            isQuestionFinished = false;

            listOfQuestions.getSelectionModel().clearSelection();
            listOfQuestions.getSelectionModel().select(questionIndex);
        }else {
            DBUtils.showAlertMessage("You haven't finished creating previous question!");
        }
        questionIndex++;

    }

    public void deleteQuestion() {
        if(!isQuestionFinished){
            DBUtils.showAlertMessage("Finish creating a question, please!");
            return;
        }
        int selectedIndex = listOfQuestions.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            myTest.getQuestions().remove(selectedIndex);
            listOfQuestions.getItems().remove(selectedIndex);
            updateLabelQuestionNumbers();
        } else {
            DBUtils.showAlertMessage("Please select a question to delete!");
        }
    }


    private void updateLabelQuestionNumbers() {
        List<Label> questionLabels = listOfQuestions.getItems();
        for (int i = 0; i < questionLabels.size(); i++) {
            Label label = questionLabels.get(i);
            label.setText("Question " + (i + 1));
        }
    }
    public void setLabelStyle() {
        labelQuestion.setPrefSize(169, 25);
        labelQuestion.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff;");
        Font font = Font.font("Consolas", FontWeight.BOLD, 12);
        labelQuestion.setFont(font);
        labelQuestion.setText("Question" + (questionIndex + 1));
    }

    public void clearConstructorPane() {
        fieldQuestionText.clear();
        fieldAnswer1.clear();
        fieldAnswer2.clear();
        fieldAnswer3.clear();
        fieldAnswer4.clear();
        answers.getSelectedToggle().setSelected(false);
        rbAnswer1.setToggleGroup(answers);
        rbAnswer2.setToggleGroup(answers);
        rbAnswer3.setToggleGroup(answers);
        rbAnswer4.setToggleGroup(answers);
        choiceBoxWeight.setValue(null);

    }

    public void clearFinishedPane() {
        labelQuestionText.setText("Question:");
        Toggle selectedToggle = finishedAnswers.getSelectedToggle();
        if (selectedToggle != null) {
            selectedToggle.setSelected(false);
        }
        rbFinishedAnswer1.setToggleGroup(finishedAnswers);
        rbFinishedAnswer2.setToggleGroup(finishedAnswers);
        rbFinishedAnswer3.setToggleGroup(finishedAnswers);
        rbFinishedAnswer4.setToggleGroup(finishedAnswers);
        labelWeight.setText("Weight:");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBoxWeight.getItems().addAll(WEIGHTS);
        listOfQuestions.getItems().add(labelQuestion);
        listOfQuestions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int questionIndex = listOfQuestions.getSelectionModel().getSelectedIndex();
                displayQuestionDetails(questionIndex);
            }
        });


    }
}
