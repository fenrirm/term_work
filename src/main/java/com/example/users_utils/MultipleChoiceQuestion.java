package com.example.users_utils;

import java.util.List;

public class MultipleChoiceQuestion {
    private List<AnswerOption> correctAnswers;
    public MultipleChoiceQuestion(String question, List<AnswerOption> answerOptions, List<AnswerOption> correctAnswers) {

        this.correctAnswers = correctAnswers;
    }

    public List<AnswerOption> getCorrectAnswers() {
        return correctAnswers;
    }
}
