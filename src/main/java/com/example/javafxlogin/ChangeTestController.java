package com.example.javafxlogin;

import com.example.users_utils.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChangeTestController implements Initializable  {

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
    private Button buttonPreviewTest;

    @FXML
    private Button buttonSaveTest;

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
    private CheckBox checkBoxFinishedAnswer1;

    @FXML
    private CheckBox checkBoxFinishedAnswer2;

    @FXML
    private CheckBox checkBoxFinishedAnswer3;

    @FXML
    private CheckBox checkBoxFinishedAnswer4;

    @FXML
    private CheckBox checkBoxFinishedAnswer5;

    @FXML
    private ChoiceBox<String> choiceBoxQuestionType;

    @FXML
    private ChoiceBox<Integer> choiceBoxWeight;

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
    private TextField fieldMultipleAnswer1;

    @FXML
    private TextField fieldMultipleAnswer2;

    @FXML
    private TextField fieldMultipleAnswer3;

    @FXML
    private TextField fieldMultipleAnswer4;

    @FXML
    private TextField fieldMultipleAnswer5;

    @FXML
    private TextField fieldQuestionText;

    @FXML
    private StackPane finishedAnswerTypeStackPane;

    @FXML
    private ToggleGroup finishedAnswers;

    @FXML
    private StackPane finishedImageViewStackPane;

    @FXML
    private Pane finishedMultipleChoicePane;

    @FXML
    private Pane finishedMultipleChoiceQuestionImagePane;

    @FXML
    private ImageView finishedMultipleChoiceQuestionImageView;

    @FXML
    private Pane finishedPane;

    @FXML
    private Pane finishedSingleChoicePane;

    @FXML
    private Pane finishedSingleChoiceQuestionImagePane;

    @FXML
    private ImageView finishedSingleChoiceQuestionImageView;

    @FXML
    private Pane finishedWordPane;

    @FXML
    private Pane finishedWordQuestionImagePane;

    @FXML
    private ImageView finishedWordQuestionImageView;

    @FXML
    private StackPane imageViewStackPane;

    @FXML
    private Label labelQuestionText;

    @FXML
    private Label labelWeight;

    @FXML
    private Label labelWordAnswer;

    @FXML
    private ListView<Label> listOfQuestions;

    @FXML
    private Pane multipleChoicePane;

    @FXML
    private Pane multipleChoiceQuestionImagePane;

    @FXML
    private ImageView multipleChoiceQuestionImageView;

    @FXML
    private Button multipleChoiceQuestionUploadImageButton;

    @FXML
    private StackPane questionTypeConstructorStackPane;

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
    private Pane singleChoicePane;

    @FXML
    private Pane singleChoiceQuestionImagePane;

    @FXML
    private ImageView singleChoiceQuestionImageView;

    @FXML
    private Button singleChoiceQuestionUploadImageButton;

    @FXML
    private StackPane stackPane;

    @FXML
    private TextField tfWordAnswer;

    @FXML
    private Label wordFinishedAnswer;

    @FXML
    private Pane wordPane;

    @FXML
    private Pane wordQuestionImagePane;

    @FXML
    private ImageView wordQuestionImageView;

    @FXML
    private Button wordQuestionUploadImageButton;

    @FXML
    private Label testNameLabel;

    @FXML
    private Label testDeadlineLabel;

    private static Test myTest = new Test();

    List<Question> questions = new ArrayList<>();
    private Label questionLabel;
    File selectedFile;
    private Image image;
    private boolean isQuestionFinished = true;
    private boolean isQuestionChanging = false;
    private final Integer[] WEIGHTS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    private final String[] TYPES = {"Single choice question", "Multiple choice question", "Word question"};

    private String authorNickname;

    private int questionIndex;

    private boolean init = true;



    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        this.questionIndex = questions.size()-1;
    }

    public void setTest(Test test) {
        this.myTest = test;
    }


    public void handleDoneButtonAction() {

        List<AnswerOption> answerOptions = new ArrayList<>();

        String selectedQuestionType = choiceBoxQuestionType.getValue();
        System.out.println(selectedQuestionType);

        int selectedRadioButtonIndex = answers.getToggles().indexOf(answers.getSelectedToggle());

        System.out.println(isQuestionChanging);


        switch (selectedQuestionType) {
            case "Single choice question" -> {

                if(!areSingleQuestionConstructorPaneFieldsFilled()){
                    DBUtils.showAlertMessage("There are unfilled fields!");
                    return;
                }
                if (selectedRadioButtonIndex == -1) {
                    DBUtils.showAlertMessage("Please select the correct answer!");
                    return;
                }

                answerOptions.add(new AnswerOption(fieldAnswer1.getText()));
                answerOptions.add(new AnswerOption(fieldAnswer2.getText()));
                answerOptions.add(new AnswerOption(fieldAnswer3.getText()));
                answerOptions.add(new AnswerOption(fieldAnswer4.getText()));
                SingleChoiceQuestion newQuestion = new SingleChoiceQuestion(fieldQuestionText.getText(), answerOptions, choiceBoxWeight.getValue());
                newQuestion.setCorrectAnswerIndex(selectedRadioButtonIndex);

                if(selectedFile!=null) {
                    newQuestion.setImage(selectedFile.getAbsolutePath());
                }

                if(isQuestionChanging){
                    int selectedIndex = listOfQuestions.getSelectionModel().getSelectedIndex();
                    System.out.println(selectedIndex);
                    myTest.addQuestion(selectedIndex, newQuestion);
                    displayQuestionDetails(selectedIndex);
                    isQuestionChanging = false;

                } else {
                    myTest.addQuestion(newQuestion);
                    displayQuestionDetails(questionIndex);
                }

            }
            case "Multiple choice question" -> {

                if(!areMultipleQuestionConstructorPaneFieldsFilled()){
                    DBUtils.showAlertMessage("There are unfilled fields!");
                    return;
                }
                if(!checkBoxAnswer1.isSelected() && !checkBoxAnswer2.isSelected() && !checkBoxAnswer3.isSelected()
                        && !checkBoxAnswer4.isSelected() && !checkBoxAnswer5.isSelected()){
                    DBUtils.showAlertMessage("Please select at least one correct answer!");
                    return;
                }

                answerOptions.add(new AnswerOption(fieldMultipleAnswer1.getText()));
                answerOptions.add(new AnswerOption(fieldMultipleAnswer2.getText()));
                answerOptions.add(new AnswerOption(fieldMultipleAnswer3.getText()));
                answerOptions.add(new AnswerOption(fieldMultipleAnswer4.getText()));
                answerOptions.add(new AnswerOption(fieldMultipleAnswer5.getText()));

                List<Integer> correctAnswerIndices = new ArrayList<>();
                if (checkBoxAnswer1.isSelected()) {
                    correctAnswerIndices.add(0);
                }
                if (checkBoxAnswer2.isSelected()) {
                    correctAnswerIndices.add(1);
                }
                if (checkBoxAnswer3.isSelected()) {
                    correctAnswerIndices.add(2);
                }
                if (checkBoxAnswer4.isSelected()) {
                    correctAnswerIndices.add(3);
                }
                if (checkBoxAnswer5.isSelected()) {
                    correctAnswerIndices.add(4);
                }

                MultipleChoiceQuestion newQuestion = new MultipleChoiceQuestion(fieldQuestionText.getText(), answerOptions, choiceBoxWeight.getValue());
                newQuestion.addCorrectAnswerIndex(correctAnswerIndices);
                if(selectedFile!=null) {
                    newQuestion.setImage(selectedFile.getAbsolutePath());
                }

                if(isQuestionChanging){
                    int selectedIndex = listOfQuestions.getSelectionModel().getSelectedIndex();
                    myTest.addQuestion(selectedIndex, newQuestion);
                    displayQuestionDetails(selectedIndex);
                    isQuestionChanging = false;
                }else {
                    myTest.addQuestion(newQuestion);
                    displayQuestionDetails(questionIndex);
                }

            }
            case "Word question" -> {
                if(!areWordQuestionConstructorPaneFieldsFilled()){
                    DBUtils.showAlertMessage("There are unfilled fields!");
                    return;
                }

                String correctAnswer = tfWordAnswer.getText();
                WordQuestion newQuestion = new WordQuestion(fieldQuestionText.getText(), correctAnswer, choiceBoxWeight.getValue());
                if(selectedFile!=null) {
                    newQuestion.setImage(selectedFile.getAbsolutePath());
                }

                if(isQuestionChanging){
                    int selectedIndex = listOfQuestions.getSelectionModel().getSelectedIndex();
                    myTest.addQuestion(selectedIndex, newQuestion);
                    displayQuestionDetails(selectedIndex);
                    isQuestionChanging = false;

                }else {
                    myTest.addQuestion(newQuestion);
                    displayQuestionDetails(questionIndex);
                }

            }
            default -> {
                DBUtils.showAlertMessage("Invalid question type!");
                return;
            }


        }




        System.out.println("Added successfully");

        constructorPane.setVisible(false);
        finishedPane.setVisible(true);
        constructorPane.toBack();
        finishedPane.toFront();

        isQuestionFinished = true;
        selectedFile = null;



    }


    private void displayQuestionDetails(int index) {
        if (index < 0 || index >= questions.size()) {
            System.out.println("Invalid question index!");
            return;
        }

        Question question = questions.get(index);
        labelQuestionText.setText((index + 1) + ". " + question.getQuestionText());

        switch (question.getType()) {
            case "Single choice question" -> {
                SingleChoiceQuestion singleChoiceQuestion = (SingleChoiceQuestion) question;
                rbFinishedAnswer1.setText(singleChoiceQuestion.getAnswerOptions().get(0).getText());
                rbFinishedAnswer2.setText(singleChoiceQuestion.getAnswerOptions().get(1).getText());
                rbFinishedAnswer3.setText(singleChoiceQuestion.getAnswerOptions().get(2).getText());
                rbFinishedAnswer4.setText(singleChoiceQuestion.getAnswerOptions().get(3).getText());
                int correctAnswerIndex = singleChoiceQuestion.getCorrectAnswerIndex();
                RadioButton selectedRadioButton = switch (correctAnswerIndex) {
                    case 0 -> rbFinishedAnswer1;
                    case 1 -> rbFinishedAnswer2;
                    case 2 -> rbFinishedAnswer3;
                    case 3 -> rbFinishedAnswer4;
                    default -> null;
                };
                if (selectedRadioButton != null) {
                    selectedRadioButton.setSelected(true);
                }
                if (question.getImagePath() != null) {
                    Image image = new Image(question.getImagePath());
                    finishedSingleChoiceQuestionImageView.setImage(image);

                }
                finishedSingleChoicePane.setVisible(true);
                finishedMultipleChoicePane.setVisible(false);
                finishedWordPane.setVisible(false);
                finishedSingleChoicePane.toFront();
                finishedMultipleChoicePane.toBack();
                finishedWordPane.toBack();
                finishedSingleChoiceQuestionImagePane.setVisible(true);
                finishedSingleChoiceQuestionImagePane.toFront();
                finishedMultipleChoiceQuestionImagePane.setVisible(false);
                finishedMultipleChoiceQuestionImagePane.toBack();
                finishedWordQuestionImagePane.setVisible(false);
                finishedWordQuestionImagePane.toBack();
            }
            case "Multiple choice question" -> {
                MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) question;
                checkBoxFinishedAnswer1.setText(multipleChoiceQuestion.getAnswerOptions().get(0).getText());
                checkBoxFinishedAnswer2.setText(multipleChoiceQuestion.getAnswerOptions().get(1).getText());
                checkBoxFinishedAnswer3.setText(multipleChoiceQuestion.getAnswerOptions().get(2).getText());
                checkBoxFinishedAnswer4.setText(multipleChoiceQuestion.getAnswerOptions().get(3).getText());
                checkBoxFinishedAnswer5.setText(multipleChoiceQuestion.getAnswerOptions().get(4).getText());
                List<Integer> correctAnswerIndices = multipleChoiceQuestion.getCorrectAnswerIndexes();
                checkBoxFinishedAnswer1.setSelected(correctAnswerIndices.contains(0));
                checkBoxFinishedAnswer2.setSelected(correctAnswerIndices.contains(1));
                checkBoxFinishedAnswer3.setSelected(correctAnswerIndices.contains(2));
                checkBoxFinishedAnswer4.setSelected(correctAnswerIndices.contains(3));
                checkBoxFinishedAnswer5.setSelected(correctAnswerIndices.contains(4));

                if (question.getImagePath() != null) {
                    Image image = new Image(question.getImagePath());
                    finishedMultipleChoiceQuestionImageView.setImage(image);
                }

                finishedMultipleChoicePane.setVisible(true);
                finishedSingleChoicePane.setVisible(false);
                finishedWordPane.setVisible(false);
                finishedMultipleChoicePane.toFront();
                finishedSingleChoicePane.toBack();
                finishedWordPane.toBack();
                finishedMultipleChoiceQuestionImagePane.setVisible(true);
                finishedMultipleChoiceQuestionImagePane.toFront();
                finishedSingleChoiceQuestionImagePane.setVisible(false);
                finishedSingleChoiceQuestionImagePane.toBack();
                finishedWordQuestionImagePane.setVisible(false);
                finishedWordQuestionImagePane.toBack();

            }
            case "Word question" -> {
                WordQuestion wordQuestion = (WordQuestion) question;
                wordFinishedAnswer.setText(wordQuestion.getCorrectAnswer());
                if (question.getImagePath() != null) {
                    Image image = new Image(question.getImagePath());
                    finishedWordQuestionImageView.setImage(image);
                }
                finishedWordPane.setVisible(true);
                finishedMultipleChoicePane.setVisible(false);
                finishedSingleChoicePane.setVisible(false);
                finishedWordPane.toFront();
                finishedMultipleChoicePane.toBack();
                finishedSingleChoicePane.toBack();
                finishedWordQuestionImagePane.setVisible(true);
                finishedWordQuestionImagePane.toFront();
                finishedMultipleChoiceQuestionImagePane.setVisible(false);
                finishedMultipleChoiceQuestionImagePane.toBack();
                finishedSingleChoiceQuestionImagePane.setVisible(false);
                finishedSingleChoiceQuestionImagePane.toBack();

            }
        }

        labelWeight.setText("Weight: " + question.getWeight());


        finishedPane.setVisible(true);
        finishedPane.toFront();
        constructorPane.setVisible(false);
        constructorPane.toBack();
    }


    public void addQuestion() {
        if (!isQuestionFinished) {
            DBUtils.showAlertMessage("Please finish creating the current question.");
            return;
        }

        clearConstructorPane();

        // Add the new question to the list
        questionLabel = new Label();
        setLabelStyle();

        listOfQuestions.getItems().add(questionLabel);
        updateLabelQuestionNumbers();

        isQuestionFinished = false;
        questionIndex++;

        listOfQuestions.getSelectionModel().clearSelection();
        listOfQuestions.getSelectionModel().select(questionIndex);

        constructorPane.setVisible(true);
        constructorPane.toFront();
        finishedPane.setVisible(false);
        finishedPane.toBack();
    }

    private void clearConstructorPane() {
        // Clear the fields in the constructor pane
        fieldQuestionText.clear();
        choiceBoxQuestionType.getSelectionModel().clearSelection();
        choiceBoxWeight.getSelectionModel().clearSelection();
        checkBoxAnswer1.setSelected(false);
        checkBoxAnswer2.setSelected(false);
        checkBoxAnswer3.setSelected(false);
        checkBoxAnswer4.setSelected(false);
        checkBoxAnswer5.setSelected(false);
        fieldAnswer1.clear();
        fieldAnswer2.clear();
        fieldAnswer3.clear();
        fieldAnswer4.clear();
        fieldMultipleAnswer1.clear();
        fieldMultipleAnswer2.clear();
        fieldMultipleAnswer3.clear();
        fieldMultipleAnswer4.clear();
        fieldMultipleAnswer5.clear();
        tfWordAnswer.clear();
        singleChoiceQuestionImageView.setImage(null);
        multipleChoiceQuestionImageView.setImage(null);
        wordQuestionImageView.setImage(null);
        singleChoiceQuestionUploadImageButton.toFront();
        multipleChoiceQuestionUploadImageButton.toFront();
        wordQuestionUploadImageButton.toFront();
    }




    public void changeQuestion(){
        isQuestionChanging = true;
        if(!isQuestionFinished){
            DBUtils.showAlertMessage("Finish creating a question, please!");
            return;
        }
        int selectedIndex = listOfQuestions.getSelectionModel().getSelectedIndex();
        System.out.println(selectedIndex);
        if (selectedIndex != -1) {
            displayQuestionConstructor(selectedIndex);
        } else {
            DBUtils.showAlertMessage("Please select a question to delete!");
        }

    }

    private void displayQuestionConstructor(int index) {
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

        switch (question.getType()){
            case "Single choice question" ->{
                SingleChoiceQuestion singleChoiceQuestion = (SingleChoiceQuestion) question;
                fieldQuestionText.setText(singleChoiceQuestion.getQuestionText());
                choiceBoxQuestionType.setValue("Single choice question");
                fieldAnswer1.setText(singleChoiceQuestion.getAnswerOptions().get(0).getText());
                fieldAnswer2.setText(singleChoiceQuestion.getAnswerOptions().get(1).getText());
                fieldAnswer3.setText(singleChoiceQuestion.getAnswerOptions().get(2).getText());
                fieldAnswer4.setText(singleChoiceQuestion.getAnswerOptions().get(3).getText());
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
                choiceBoxQuestionType.setValue(singleChoiceQuestion.getType());

                singleChoicePane.setVisible(true);
                multipleChoicePane.setVisible(false);
                wordPane.setVisible(false);
                singleChoicePane.toFront();
                multipleChoicePane.toBack();
                wordPane.toBack();
                singleChoiceQuestionImageView.setVisible(true);
                multipleChoiceQuestionImageView.setVisible(false);
                wordQuestionImageView.setVisible(false);
                singleChoiceQuestionImageView.toFront();
                multipleChoiceQuestionImageView.toBack();
                wordQuestionImageView.toBack();
            }

            case "Multiple choice question" ->{
                System.out.println("here");
                MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) question;
                fieldQuestionText.setText(multipleChoiceQuestion.getQuestionText());
                fieldMultipleAnswer1.setText(multipleChoiceQuestion.getAnswerOptions().get(0).getText());
                fieldMultipleAnswer2.setText(multipleChoiceQuestion.getAnswerOptions().get(1).getText());
                fieldMultipleAnswer3.setText(multipleChoiceQuestion.getAnswerOptions().get(2).getText());
                fieldMultipleAnswer4.setText(multipleChoiceQuestion.getAnswerOptions().get(3).getText());
                fieldMultipleAnswer5.setText(multipleChoiceQuestion.getAnswerOptions().get(4).getText());
                List<Integer> correctAnswerIndices = multipleChoiceQuestion.getCorrectAnswerIndexes();
                checkBoxAnswer1.setSelected(correctAnswerIndices.contains(0));
                checkBoxAnswer2.setSelected(correctAnswerIndices.contains(1));
                checkBoxAnswer3.setSelected(correctAnswerIndices.contains(2));
                checkBoxAnswer4.setSelected(correctAnswerIndices.contains(3));
                checkBoxAnswer5.setSelected(correctAnswerIndices.contains(4));
                choiceBoxQuestionType.setValue(multipleChoiceQuestion.getType());
                multipleChoicePane.setVisible(true);
                singleChoicePane.setVisible(false);
                wordPane.setVisible(false);
                multipleChoicePane.toFront();
                singleChoicePane.toBack();
                wordPane.toBack();
                multipleChoiceQuestionImageView.setVisible(true);
                singleChoiceQuestionImageView.setVisible(false);
                wordQuestionImageView.setVisible(false);
                multipleChoiceQuestionImageView.toFront();
                singleChoiceQuestionImageView.toBack();
                wordQuestionImageView.toBack();

            }
            case "Word question" ->{
                WordQuestion wordQuestion = (WordQuestion) question;
                tfWordAnswer.setText(wordQuestion.getCorrectAnswer());
                choiceBoxQuestionType.setValue(wordQuestion.getType());
                wordPane.setVisible(true);
                multipleChoicePane.setVisible(false);
                singleChoicePane.setVisible(false);
                wordPane.toFront();
                multipleChoicePane.toBack();
                singleChoicePane.toBack();
                wordQuestionImageView.setVisible(true);
                singleChoiceQuestionImageView.setVisible(false);
                multipleChoiceQuestionImageView.setVisible(false);
                wordQuestionImageView.toFront();
                singleChoiceQuestionImageView.toBack();
                multipleChoiceQuestionImageView.toBack();
            }
        }

        if(question.getImagePath()!=null){
            switch (question.getType()){
                case "Single choice question" -> singleChoiceQuestionImageView.setImage(new Image(question.getImagePath()));
                case "Multiple choice question" -> multipleChoiceQuestionImageView.setImage(new Image(question.getImagePath()));
                case "Word question" -> wordQuestionImageView.setImage(new Image(question.getImagePath()));
            }
        }

        labelWeight.setText("Weight: " + question.getWeight());

        constructorPane.setVisible(true);
        constructorPane.toFront();
        finishedPane.setVisible(false);
        finishedPane.toBack();

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



    public void uploadImageToSingleChoiceQuestionImagePane(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter =
                new FileChooser.ExtensionFilter("Images (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(filter);

        selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            try{
                image = new Image(new FileInputStream(selectedFile));
                singleChoiceQuestionImageView.setImage(image);
            } catch (FileNotFoundException e) {
                System.out.println("Error while downloading image: "+ e.getMessage());
            }
        }
        singleChoiceQuestionImageView.toFront();
    }
    public void uploadImageToMultipleChoiceQuestionImagePane(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter =
                new FileChooser.ExtensionFilter("Images (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(filter);

        selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            try{
                image = new Image(new FileInputStream(selectedFile));
                multipleChoiceQuestionImageView.setImage(image);
            } catch (FileNotFoundException e) {
                System.out.println("Error while downloading image: "+ e.getMessage());
            }
        }
        multipleChoiceQuestionImageView.toFront();
    }
    public void uploadImageToWordQuestionPane(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter =
                new FileChooser.ExtensionFilter("Images (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(filter);

        selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            try{
                image = new Image(new FileInputStream(selectedFile));
                wordQuestionImageView.setImage(image);
            } catch (FileNotFoundException e) {
                System.out.println("Error while downloading image: "+ e.getMessage());
            }
        }
        wordQuestionImageView.toFront();
    }

    public void clearSingleChoicePane(){
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
        singleChoiceQuestionImageView.setImage(null);
    }
    public void clearMultipleChoicePane(){
        fieldQuestionText.clear();
        fieldMultipleAnswer1.clear();
        fieldMultipleAnswer2.clear();
        fieldMultipleAnswer3.clear();
        fieldMultipleAnswer4.clear();
        fieldMultipleAnswer5.clear();
        checkBoxAnswer1.setSelected(false);
        checkBoxAnswer2.setSelected(false);
        checkBoxAnswer3.setSelected(false);
        checkBoxAnswer4.setSelected(false);
        checkBoxAnswer5.setSelected(false);
        choiceBoxWeight.setValue(null);
        multipleChoiceQuestionImageView.setImage(null);
    }
    public void clearWordPane(){
        fieldQuestionText.clear();
        tfWordAnswer.clear();
        choiceBoxWeight.setValue(null);
        wordQuestionImageView.setImage(null);
    }

    public void clearFinishedSingleChoicePane(){
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
        finishedSingleChoiceQuestionImageView.setImage(null);
    }
    public void clearFinishedMultipleChoicePane(){
        labelQuestionText.setText("Question:");
        checkBoxFinishedAnswer1.setSelected(false);
        checkBoxFinishedAnswer2.setSelected(false);
        checkBoxFinishedAnswer3.setSelected(false);
        checkBoxFinishedAnswer4.setSelected(false);
        checkBoxFinishedAnswer5.setSelected(false);
        labelWeight.setText("Weight:");
        finishedMultipleChoiceQuestionImageView.setImage(null);
    }
    public void clearFinishedWordPane(){
        labelQuestionText.setText("Question:");
        wordFinishedAnswer.setText("");
        labelWeight.setText("Weight:");
        finishedWordQuestionImageView.setImage(null);
    }

    private boolean areSingleQuestionConstructorPaneFieldsFilled(){
        return isFieldFilled(fieldAnswer1) &&
                isFieldFilled(fieldAnswer2) &&
                isFieldFilled(fieldAnswer3) &&
                isFieldFilled(fieldAnswer4) &&
                isFieldFilled(fieldQuestionText) &&
                choiceBoxWeight.getValue() != null &&
                choiceBoxQuestionType.getValue() != null;
    }
    private boolean areMultipleQuestionConstructorPaneFieldsFilled(){
        return  isFieldFilled(fieldQuestionText)&&
                isFieldFilled(fieldMultipleAnswer1) &&
                isFieldFilled(fieldMultipleAnswer2) &&
                isFieldFilled(fieldMultipleAnswer3) &&
                isFieldFilled(fieldMultipleAnswer4) &&
                isFieldFilled(fieldMultipleAnswer5) &&
                !choiceBoxWeight.getSelectionModel().isEmpty() &&
                choiceBoxWeight.getValue()!=null &&
                !choiceBoxQuestionType.getSelectionModel().isEmpty()&&
                choiceBoxQuestionType.getValue()!=null;
    }
    private boolean areWordQuestionConstructorPaneFieldsFilled(){
        return  isFieldFilled(fieldQuestionText)&&
                isFieldFilled(tfWordAnswer) &&
                !choiceBoxWeight.getSelectionModel().isEmpty()&&
                choiceBoxWeight.getValue()!=null &&
                !choiceBoxQuestionType.getSelectionModel().isEmpty()&&
                choiceBoxQuestionType.getValue()!=null;
    }
    private boolean isFieldFilled(TextField textField) {
        return textField.getText() != null && !textField.getText().isEmpty();
    }


    private void updateLabelQuestionNumbers() {
        List<Label> questionLabels = listOfQuestions.getItems();
        for (int i = 0; i < questionLabels.size(); i++) {
            Label label = questionLabels.get(i);
            label.setText("Question " + (i + 1));
        }
    }

    private void showQuestionTypePane(String questionType) {
        singleChoicePane.setVisible(false);
        multipleChoicePane.setVisible(false);
        wordPane.setVisible(false);

        switch (questionType) {
            case "Single choice question" -> singleChoicePane.setVisible(true);
            case "Multiple choice question" -> multipleChoicePane.setVisible(true);
            case "Word question" -> wordPane.setVisible(true);
        }
    }


    public void setLabelStyle() {
        questionLabel.setPrefSize(169, 25);
        questionLabel.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff;");
        Font font = Font.font("Consolas", FontWeight.BOLD, 12);
        questionLabel.setFont(font);
        questionLabel.setText("Question" + (questionIndex + 1));
    }

    public void setLabelStyle(Label label) {
        label.setPrefSize(169, 25);
        label.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff;");
        Font font = Font.font("Consolas", FontWeight.BOLD, 12);
        label.setFont(font);
    }

    public void initializeController() {
        if (questions != null && !questions.isEmpty()) {
            for (int i = 0; i < questions.size(); i++) {
                questionLabel = new Label("Question" + (i + 1));
                setLabelStyle(questionLabel);
                listOfQuestions.getItems().add(questionLabel);
            }
            testNameLabel.setText(testNameLabel.getText() + myTest.getName());
            testDeadlineLabel.setText(testDeadlineLabel.getText() + myTest.getDeadline());

            listOfQuestions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                int index = listOfQuestions.getSelectionModel().getSelectedIndex();
                displayQuestionDetails(index);
            });

            listOfQuestions.getSelectionModel().select(0); // Select the first question by default
        }
    }


    public static Test getMyTest(){
        return myTest;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBoxWeight.getItems().addAll(WEIGHTS);

        choiceBoxQuestionType.getItems().addAll(TYPES);
        choiceBoxQuestionType.setValue("Single choice question");
        choiceBoxQuestionType.setOnAction(event -> {
            String selectedQuestionType = choiceBoxQuestionType.getValue();
            showQuestionTypePane(selectedQuestionType);
        });

        if (questions != null && !questions.isEmpty() ) {
            for (int i = 0; i < questions.size(); i++) {
                questionLabel = new Label("Question" + (i + 1));
                setLabelStyle(questionLabel);
                listOfQuestions.getItems().add(questionLabel);
            }

        }
        testNameLabel.setText(myTest.getName());
        testDeadlineLabel.setText(myTest.getDeadline());

        listOfQuestions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                int index = listOfQuestions.getSelectionModel().getSelectedIndex();
                displayQuestionDetails(index);
            }
        });


        buttonPreviewTest.setOnAction(event -> {
            DBUtils.changeScene(event, "test_preview.fxml", 480, 480);
        });

        buttonSaveTest.setOnAction(event -> {
            DBUtils.changeSceneToSaveTestController(event, "test_name_window.fxml","test", authorNickname,myTest,280, 150);
        });

    }

}
