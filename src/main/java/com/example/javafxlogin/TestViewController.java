package com.example.javafxlogin;

import com.example.users_utils.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TestViewController implements Initializable {


    @FXML
    private ScrollPane scrollPaneTestPreview;

    @FXML
    private AnchorPane testPreviewAnchorPane;

    @FXML
    private VBox testPreviewVBox;

    private final double SINGLE_CHOICE_PANE_HEIGHT = 200;
    private final double SINGLE_CHOICE_PANE_WIDTH = 491;
    private final double MULTIPLE_CHOICE_PANE_HEIGHT = 230;
    private final double MULTIPLE_CHOICE_PANE_WIDTH = 491;
    private final double WORD_PANE_HEIGHT = 200;
    private final double WORD_PANE_WIDTH = 491;

    private double paneLayoutX = 20.0;
    private double paneLayoutY = 24.0;

    private double questionLabelLayoutX = 19.0;
    private double questionLabelLayoutY = 16.0;

    private final double QUESTION_LABEL_HEIGHT = 30.0;
    private final double QUESTION_LABEL_WIDTH = 460.0;

    private double radioButtonLayoutX = 14.0;
    private double radioButtonLayoutY = 55.0;

    private final double RADIO_BUTTON_HEIGHT = 25.0;
    private final double RADIO_BUTTON_WIDTH = 250.0;

    private double singleChoiceQuestionWeightLabelLayoutX = 14.0;
    private double singleChoiceQuestionWeightLabelLayoutY = 150.0;

    private final double WEIGHT_LABEL_WIDTH = 90;
    private final double WEIGHT_LABEL_HEIGHT = 30;

    private double checkBoxLayoutX = 20.0;
    private double checkBoxLayoutY = 50.0;

    private final double CHECK_BOX_HEIGHT = 25.0;
    private final double CHECK_BOX_WIDTH = 250.0;

    private double multipleChoiceQuestionWeightLabelLayoutX = 19.0;
    private double multipleChoiceQuestionWeightLabelLayoutY = 175.0;

    private double wordPAneAnswerLabelLayoutX = 19.0;
    private double wordPAneAnswerLabelLayoutY = 60.0;

    private final double WORD_PANE_ANSWER_LABEL_HEIGHT = 25;
    private final double WORD_PANE_ANSWER_LABEL_WIDTH = 250;

    private double wordQuestionWeightLabelLayoutX = 19.0;
    private double wordQuestionWeightLabelLayoutY = 90.0;

    private double IMAGE_VIEW_HEIGHT = 130.0;
    private double IMAGE_VIEW_WIDTH = 200.0;

    private double imageViewSingleChoiceQuestionLayoutX = 275.0;
    private double imageViewSingleChoiceQuestionLayoutY = 35.0;

    private double imageViewMultipleChoiceQuestionLayoutX = 275.0;
    private double imageViewMultipleChoiceQuestionLayoutY = 50.0;

    private double imageViewWordQuestionLayoutX = 275.0;
    private double imageViewWordQuestionLayoutY = 10.0;






    public void previewTest(){

        List<Question> questions = MyTestController.getMyTest().getQuestions();
        for (Question question : questions) {
            setPreviewTestWindow(question.getType(), question);
        }

    }

    private void setPreviewTestWindow(String questionType, Question question) {
        switch (questionType){
            case "Single choice question" ->{
                SingleChoiceQuestion singleChoiceQuestion = (SingleChoiceQuestion) question;

                Pane singleChoiceQuestionPane = new Pane();
                setSingleChoicePaneStyle(singleChoiceQuestionPane);

                Label questionText = new Label(singleChoiceQuestion.getQuestionText());
                setQuestionLabelStyle(questionText);

                RadioButton answer1 = new RadioButton();
                RadioButton answer2 = new RadioButton();
                RadioButton answer3 = new RadioButton();
                RadioButton answer4 = new RadioButton();
                setRadioButtonsStyle(answer1, answer2, answer3, answer4);

                List<AnswerOption> answerOptions = singleChoiceQuestion.getAnswerOptions();
                answer1.setText(answerOptions.get(0).getText());
                answer2.setText(answerOptions.get(1).getText());
                answer3.setText(answerOptions.get(2).getText());
                answer4.setText(answerOptions.get(3).getText());

                int correctAnswerIndex = singleChoiceQuestion.getCorrectAnswerIndex();
                RadioButton selectedRadioButton = switch (correctAnswerIndex) {
                    case 0 -> answer1;
                    case 1 -> answer2;
                    case 2 -> answer3;
                    case 3 -> answer4;
                    default -> null;
                };
                if (selectedRadioButton != null) {
                    selectedRadioButton.setSelected(true);
                }

                Label weight = new Label("weight: "+singleChoiceQuestion.getWeight());
                setSingleChoiceWeightLabelStyle(weight);

                ImageView imageView = new ImageView();
                setSingleChoiceQuestionImageViewStyle(imageView);
                if(singleChoiceQuestion.getImagePath()!=null){
                    imageView.setImage(new Image(singleChoiceQuestion.getImagePath()));
                }

                singleChoiceQuestionPane.getChildren().addAll(questionText, answer1, answer2, answer3, answer4, weight,imageView);
                testPreviewVBox.getChildren().add(singleChoiceQuestionPane);
            }
            case "Multiple choice question" ->{
                MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) question;

                Pane multipleChoiceQuestionPane = new Pane();
                setMultipleChoicePaneStyle(multipleChoiceQuestionPane);

                Label questionText = new Label(multipleChoiceQuestion.getQuestionText());
                setQuestionLabelStyle(questionText);

                CheckBox answer1 = new CheckBox();
                CheckBox answer2 = new CheckBox();
                CheckBox answer3 = new CheckBox();
                CheckBox answer4 = new CheckBox();
                CheckBox answer5 = new CheckBox();
                setCheckBoxStyle(answer1, answer2, answer3, answer4, answer5);

                List<AnswerOption> answerOptions = multipleChoiceQuestion.getAnswerOptions();
                answer1.setText(answerOptions.get(0).getText());
                answer2.setText(answerOptions.get(1).getText());
                answer3.setText(answerOptions.get(2).getText());
                answer4.setText(answerOptions.get(3).getText());
                answer5.setText(answerOptions.get(4).getText());

                List<Integer> correctAnswerIndices = multipleChoiceQuestion.getCorrectAnswerIndexes();
                answer1.setSelected(correctAnswerIndices.contains(0));
                answer2.setSelected(correctAnswerIndices.contains(1));
                answer3.setSelected(correctAnswerIndices.contains(2));
                answer4.setSelected(correctAnswerIndices.contains(3));
                answer5.setSelected(correctAnswerIndices.contains(4));

                Label weight = new Label("weight: "+multipleChoiceQuestion.getWeight());
                setMultipleChoiceWeightLabelStyle(weight);

                ImageView imageView = new ImageView();
                setMultipleChoiceQuestionImageViewStyle(imageView);
                if(multipleChoiceQuestion.getImagePath()!=null){
                    imageView.setImage(new Image(multipleChoiceQuestion.getImagePath()));
                }

                multipleChoiceQuestionPane.getChildren().addAll(questionText, answer1, answer2, answer3, answer4, answer5, weight, imageView);
                testPreviewVBox.getChildren().add(multipleChoiceQuestionPane);

            }
            case "Word question" ->{
                WordQuestion wordQuestion = (WordQuestion) question;

                Pane wordQuestionPane = new Pane();
                setWordPaneStyle(wordQuestionPane);

                Label questionText = new Label(wordQuestion.getQuestionText());
                setQuestionLabelStyle(questionText);

                Label answerText = new Label(wordQuestion.getCorrectAnswer());
                setWordPaneAnswerLabelStyle(answerText);

                Label weight = new Label("weight: "+wordQuestion.getWeight());
                setWordQuestionWeightLabelStyle(weight);

                ImageView imageView = new ImageView();
                setWordQuestionImageViewStyle(imageView);
                if(wordQuestion.getImagePath()!=null){
                    imageView.setImage(new Image(wordQuestion.getImagePath()));
                }

                wordQuestionPane.getChildren().addAll(questionText, answerText, weight, imageView);
                testPreviewVBox.getChildren().add(wordQuestionPane);
            }
        }
        //scrollPaneTestPreview.getChildrenUnmodifiable().add(testPreviewVBox);

    }

    public void setSingleChoicePaneStyle(Pane singleChoicePane){
        singleChoicePane.setLayoutX(paneLayoutX);
        singleChoicePane.setLayoutY(paneLayoutY);
        singleChoicePane.setMinSize(SINGLE_CHOICE_PANE_WIDTH, SINGLE_CHOICE_PANE_HEIGHT);
        singleChoicePane.setStyle("-fx-background-color: #496366;");
        paneLayoutY = paneLayoutY +SINGLE_CHOICE_PANE_HEIGHT;
    }

    public void setMultipleChoicePaneStyle(Pane multipleChoicePane){
        multipleChoicePane.setLayoutX(paneLayoutX);
        multipleChoicePane.setLayoutY(paneLayoutY);
        multipleChoicePane.setMinSize(MULTIPLE_CHOICE_PANE_WIDTH, MULTIPLE_CHOICE_PANE_HEIGHT);
        multipleChoicePane.setStyle("-fx-background-color: #496366;");
        paneLayoutY = paneLayoutY + MULTIPLE_CHOICE_PANE_HEIGHT;
    }

    public void setWordPaneStyle(Pane wordPane){
        wordPane.setLayoutX(paneLayoutX);
        wordPane.setLayoutY(paneLayoutY);
        wordPane.setMinSize(WORD_PANE_WIDTH, WORD_PANE_HEIGHT);
        wordPane.setStyle("-fx-background-color: #496366;");
        paneLayoutY = paneLayoutY + WORD_PANE_HEIGHT;
    }
    public void setQuestionLabelStyle(Label question){
        question.setLayoutX(questionLabelLayoutX);
        question.setLayoutY(questionLabelLayoutY);
        question.setPrefWidth(QUESTION_LABEL_WIDTH);
        question.setPrefHeight(QUESTION_LABEL_HEIGHT);
        question.setStyle("-fx-text-fill:WHITE; -fx-font-size: 12; -fx-font-family: 'Consolas Bold'; -fx-font-weight: bold;");
    }
    public void setRadioButtonsStyle(RadioButton radioButton1, RadioButton radioButton2, RadioButton radioButton3, RadioButton radioButton4){
        radioButton1.setLayoutX(radioButtonLayoutX);
        radioButton1.setLayoutY(radioButtonLayoutY);
        radioButton2.setLayoutX(radioButtonLayoutX);
        radioButton2.setLayoutY(radioButtonLayoutY+25);
        radioButton3.setLayoutX(radioButtonLayoutX);
        radioButton3.setLayoutY(radioButtonLayoutY+50);
        radioButton4.setLayoutX(radioButtonLayoutX);
        radioButton4.setLayoutY(radioButtonLayoutY+75);
        radioButton1.setPrefHeight(RADIO_BUTTON_HEIGHT);
        radioButton1.setPrefWidth(RADIO_BUTTON_WIDTH);
        radioButton2.setPrefHeight(RADIO_BUTTON_HEIGHT);
        radioButton2.setPrefWidth(RADIO_BUTTON_WIDTH);
        radioButton3.setPrefHeight(RADIO_BUTTON_HEIGHT);
        radioButton3.setPrefWidth(RADIO_BUTTON_WIDTH);
        radioButton4.setPrefHeight(RADIO_BUTTON_HEIGHT);
        radioButton4.setPrefWidth(RADIO_BUTTON_WIDTH);
        radioButton1.setStyle("-fx-text-fill:WHITE; -fx-font-size: 12; -fx-font-family: 'Consolas Bold';");
        radioButton2.setStyle("-fx-text-fill:WHITE; -fx-font-size: 12; -fx-font-family: 'Consolas Bold';");
        radioButton3.setStyle("-fx-text-fill:WHITE; -fx-font-size: 12; -fx-font-family: 'Consolas Bold';");
        radioButton4.setStyle("-fx-text-fill:WHITE; -fx-font-size: 12; -fx-font-family: 'Consolas Bold';");
        radioButton1.setDisable(true);
        radioButton2.setDisable(true);
        radioButton3.setDisable(true);
        radioButton4.setDisable(true);
    }

    public void setSingleChoiceWeightLabelStyle(Label weight){
        weight.setLayoutX(singleChoiceQuestionWeightLabelLayoutX);
        weight.setLayoutY(singleChoiceQuestionWeightLabelLayoutY);
        weight.setPrefHeight(WEIGHT_LABEL_HEIGHT);
        weight.setPrefWidth(WEIGHT_LABEL_WIDTH);
        weight.setStyle("-fx-text-fill:WHITE; -fx-font-size: 12; -fx-font-family: 'Consolas Bold';");

    }

    public void setCheckBoxStyle(CheckBox checkBox1, CheckBox checkBox2, CheckBox checkBox3, CheckBox checkBox4, CheckBox checkBox5){
        checkBox1.setLayoutX(checkBoxLayoutX);
        checkBox1.setLayoutY(checkBoxLayoutY);
        checkBox2.setLayoutX(checkBoxLayoutX);
        checkBox2.setLayoutY(checkBoxLayoutY+25);
        checkBox3.setLayoutX(checkBoxLayoutX);
        checkBox3.setLayoutY(checkBoxLayoutY+50);
        checkBox4.setLayoutX(checkBoxLayoutX);
        checkBox4.setLayoutY(checkBoxLayoutY+75);
        checkBox5.setLayoutX(checkBoxLayoutX);
        checkBox5.setLayoutY(checkBoxLayoutY+100);
        checkBox1.setPrefHeight(CHECK_BOX_HEIGHT);
        checkBox1.setPrefWidth(CHECK_BOX_WIDTH);
        checkBox2.setPrefHeight(CHECK_BOX_HEIGHT);
        checkBox2.setPrefWidth(CHECK_BOX_WIDTH);
        checkBox3.setPrefHeight(CHECK_BOX_HEIGHT);
        checkBox3.setPrefWidth(CHECK_BOX_WIDTH);
        checkBox4.setPrefHeight(CHECK_BOX_HEIGHT);
        checkBox4.setPrefWidth(CHECK_BOX_WIDTH);
        checkBox5.setPrefHeight(CHECK_BOX_HEIGHT);
        checkBox5.setPrefWidth(CHECK_BOX_WIDTH);
        checkBox1.setStyle("-fx-text-fill:WHITE; -fx-font-size: 12; -fx-font-family: 'Consolas Bold';");
        checkBox2.setStyle("-fx-text-fill:WHITE; -fx-font-size: 12; -fx-font-family: 'Consolas Bold';");
        checkBox3.setStyle("-fx-text-fill:WHITE; -fx-font-size: 12; -fx-font-family: 'Consolas Bold';");
        checkBox4.setStyle("-fx-text-fill:WHITE; -fx-font-size: 12; -fx-font-family: 'Consolas Bold';");
        checkBox5.setStyle("-fx-text-fill:WHITE; -fx-font-size: 12; -fx-font-family: 'Consolas Bold';");
        checkBox1.setDisable(true);
        checkBox2.setDisable(true);
        checkBox3.setDisable(true);
        checkBox4.setDisable(true);
        checkBox5.setDisable(true);

    }

    public void setMultipleChoiceWeightLabelStyle(Label weight){
        weight.setLayoutX(multipleChoiceQuestionWeightLabelLayoutX);
        weight.setLayoutY(multipleChoiceQuestionWeightLabelLayoutY);
        weight.setPrefHeight(WEIGHT_LABEL_HEIGHT);
        weight.setPrefWidth(WEIGHT_LABEL_WIDTH);
        weight.setStyle("-fx-text-fill:WHITE; -fx-font-size: 12; -fx-font-family: 'Consolas Bold';");


    }

    private void setWordPaneAnswerLabelStyle(Label label) {
        label.setLayoutX(wordPAneAnswerLabelLayoutX);
        label.setLayoutY(wordPAneAnswerLabelLayoutY);
        label.setPrefWidth(WORD_PANE_ANSWER_LABEL_WIDTH);
        label.setPrefHeight(WORD_PANE_ANSWER_LABEL_HEIGHT);
        label.setStyle("-fx-text-fill:WHITE; -fx-font-size: 12; -fx-font-family: 'Consolas Bold';");
    }

    private void setWordQuestionWeightLabelStyle(Label label) {
        label.setLayoutX(wordQuestionWeightLabelLayoutX);
        label.setLayoutY(wordQuestionWeightLabelLayoutY);
        label.setPrefWidth(WEIGHT_LABEL_WIDTH);
        label.setPrefHeight(WEIGHT_LABEL_HEIGHT);
        label.setStyle("-fx-text-fill:WHITE; -fx-font-size: 12; -fx-font-family: 'Consolas Bold';");

    }

    public void setSingleChoiceQuestionImageViewStyle(ImageView imageView){
        imageView.setLayoutX(imageViewSingleChoiceQuestionLayoutX);
        imageView.setLayoutY(imageViewSingleChoiceQuestionLayoutY);
        imageView.setFitWidth(IMAGE_VIEW_WIDTH);
        imageView.setFitHeight(IMAGE_VIEW_HEIGHT);
    }
    public void setMultipleChoiceQuestionImageViewStyle(ImageView imageView){
        imageView.setLayoutX(imageViewMultipleChoiceQuestionLayoutX);
        imageView.setLayoutY(imageViewMultipleChoiceQuestionLayoutY);
        imageView.setFitWidth(IMAGE_VIEW_WIDTH);
        imageView.setFitHeight(IMAGE_VIEW_HEIGHT);
    }
    public void setWordQuestionImageViewStyle(ImageView imageView){
        imageView.setLayoutX(imageViewWordQuestionLayoutX);
        imageView.setLayoutY(imageViewWordQuestionLayoutY);
        imageView.setFitWidth(IMAGE_VIEW_WIDTH);
        imageView.setFitHeight(IMAGE_VIEW_HEIGHT);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        previewTest();

    }
}
