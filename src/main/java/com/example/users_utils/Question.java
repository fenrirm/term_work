package com.example.users_utils;


public abstract class Question {
    private String questionText;
    private int weight;

    private String imagePath = null;

    public Question(String questionText, int weight) {
        this.questionText = questionText;
        this.weight = weight;
    }

    public String getQuestionText() {
        return questionText;
    }

    public int getWeight() {
        return weight;
    }

    public void setImage(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public abstract String getType();


}
