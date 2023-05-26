package com.example.users;

public class Teacher extends User {
    public Teacher(String name, String surname, String nickname) {
        super(name, surname, nickname);
    }

    @Override
    public String getNickname() {
        return super.getNickname();
    }
}
