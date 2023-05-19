package com.example.users;

public class Student extends User{
    public Student(String name, String surname, int phoneNumber) {
        super(name, surname, phoneNumber);
    }

    @Override
    public int getPhoneNumber() {
        return super.getPhoneNumber();
    }
}
