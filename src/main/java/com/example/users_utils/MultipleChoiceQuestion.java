package com.example.users_utils;


import java.util.List;

public class MultipleChoiceQuestion extends Question {
    private List<AnswerOption> answerOptions;
    private List<Integer> correctAnswerIndexes;

    public MultipleChoiceQuestion(String questionText, List<AnswerOption> answerOptions, int weight) {
        super(questionText, weight);
        this.answerOptions = answerOptions;

    }

    public List<AnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public void addCorrectAnswerIndex(List<Integer> correctAnswerIndexes) {
        this.correctAnswerIndexes = correctAnswerIndexes;
    }

    public List<Integer> getCorrectAnswerIndexes() {
        return correctAnswerIndexes;
    }

    @Override
    public String getType() {
        return "Multiple choice question";
    }
}
