package com.example.users_utils;

import java.util.ArrayList;
import java.util.List;

public class Test {
    private String name;
    private String deadline;
    private List<Question> questions;
    private String authorPhone;
    private int testId;

    public Test(){
        this.questions = new ArrayList<>();
    }

    public Test(int testId, String testName, String testDeadline, String authorPhone){
        this.testId = testId;
        this.name = testName;
        this.deadline = testDeadline;
        this.authorPhone = authorPhone;
    }

    public int getId(){
        return testId;
    }
    public void addQuestion(Question question){
        questions.add(question);
    }

    public void addQuestion(int index, Question question){
        questions.set(index, question);
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

    public String getName() {
        return name;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setAuthorPhone(String authorPhone) {
        this.authorPhone = authorPhone;
    }

    public String getAuthorNickname() {
        return authorPhone;
    }
}
