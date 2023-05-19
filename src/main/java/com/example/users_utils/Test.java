package com.example.users_utils;

import java.util.ArrayList;
import java.util.List;

public class Test {
    private String name;
    private String deadline;
    private List<Question> questions;

    public Test(){
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question){
        questions.add(question);
    }
    public List<Question> getQuestions(){
        return questions;
    }
    public Question getQuestion(int index){
        return questions.get(index);
    }
    public void setDeadline(String deadline){
        this.deadline = deadline;
    }

    public void setName(String name) {
        this.name = name;
    }
}
