package com.example.users_utils;

public class WordQuestion extends Question{
    private String correctAnswer;

    public WordQuestion(String questionText, String correctAnswer, int weight) {
        super(questionText, weight);
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String getType() {
        return "Word question";
    }
}
