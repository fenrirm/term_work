package com.example.users;

public class User {
    private String name, surname;
    private String position;
    private int phoneNumber;
    private String password;
    private String imagePath;


    public User(String name, String surname, String position, int phoneNumber, String password, String imagePath){
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.imagePath = imagePath;
    }

    public User(String name, String surname, int phoneNumber){
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getPosition() {
        return position;
    }

    public String getImagePath() {
        return imagePath;
    }
}
