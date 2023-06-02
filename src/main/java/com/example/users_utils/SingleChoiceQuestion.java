package com.example.users_utils;

import java.util.List;

public class SingleChoiceQuestion extends Question{

    private List<AnswerOption> answerOptions;
    private int correctAnswerIndex;
    private String type;

    public SingleChoiceQuestion(String questionText, List<AnswerOption> answerOptions, int weight) {
        super(questionText, weight);
        this.answerOptions = answerOptions;
    }

    public List<AnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return "Single choice question";
    }
}
