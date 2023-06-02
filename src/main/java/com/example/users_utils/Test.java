package com.example.users_utils;

import java.util.ArrayList;
import java.util.List;

public class Test {
    private String name;
    private String deadline;
    private List<Question> questions;
    private String authorNickname;
    private int testId;
    private double testResult;

    public Test(){
        this.questions = new ArrayList<>();
    }

    public Test(int testId, String testName, String testDeadline, String authorNickname){
        this.testId = testId;
        this.name = testName;
        this.deadline = testDeadline;
        this.authorNickname = authorNickname;
    }

    public Test(int testId, String testName, double testResult){
        this.testId = testId;
        this.name = testName;
        this.testResult = testResult;
    }

    public Test(Test test){
        this.name = test.getName();
        this.deadline = test.getDeadline();
        this.authorNickname = test.getAuthorNickname();
        this.questions = test.getQuestions();
    }

    public double getTestResult() {
        return testResult;
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
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
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

    public void setAuthorNickname(String authorNickname) {
        this.authorNickname = authorNickname;
    }

    public String getAuthorNickname() {
        return authorNickname;
    }
}
