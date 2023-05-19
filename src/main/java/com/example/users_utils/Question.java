package com.example.users_utils;

import java.util.List;

public class Question {
    private String questionText;
    private List<AnswerOption> answerOptions;
    private int weight;
    private int correctAnswerIndex;

    public Question(String question, List<AnswerOption> answerOptions, int weight){
        this.questionText = question;
        this.answerOptions = answerOptions;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public List<AnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public String getQuestionText(){
        return questionText;
    }
}
