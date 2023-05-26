package com.example.users;

public class Student extends User{
    public Student(String name, String surname, String nickname) {
        super(name, surname, nickname);
    }

    @Override
    public String getNickname() {
        return super.getNickname();
    }
}
