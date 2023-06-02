package com.example.users;

public class Student extends User{
    private int studentId;
    public Student(String name, String surname, String nickname) {
        super(name, surname, nickname);
    }

    public Student(int id,String name, String surname, String nickname){
        super(name, surname, nickname);
        this.studentId = id;
    }

    public int getStudentId() {
        return studentId;
    }

    @Override
    public String getNickname() {
        return super.getNickname();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getSurname() {
        return super.getSurname();
    }
}
