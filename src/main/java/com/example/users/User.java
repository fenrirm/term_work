package com.example.users;

public class User {
    private String name, surname;
    private String position;
    private String nickname;
    private String password;
    private String imagePath;


    public User(String name, String surname, String position, String nickname, String password, String imagePath){
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.nickname = nickname;
        this.password = password;
        this.imagePath = imagePath;
    }

    public User(String name, String surname, String nickname){
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getNickname() {
        return nickname;
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
