package com.example.users_utils;

import java.util.List;

public class SingleChoiceQuestion extends Question{
    private AnswerOption correctAnswer;
    public SingleChoiceQuestion(String question, List<AnswerOption> answerOptions, AnswerOption correctAnswer, int weight) {
        super(question, answerOptions, weight);
        this.correctAnswer = correctAnswer;
    }
}
