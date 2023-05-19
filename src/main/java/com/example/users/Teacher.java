package com.example.users;

public class Teacher extends User {
    public Teacher(String name, String surname, int phoneNumber) {
        super(name, surname, phoneNumber);
    }

    @Override
    public int getPhoneNumber() {
        return super.getPhoneNumber();
    }
}
