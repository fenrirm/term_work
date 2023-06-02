package com.example.users_utils;

public class Course {
    private String name;
    private String author;

    private String joinKey;
    private int id;

    public Course(String name, String authorNickname, String joinKey){
        this.name = name;
        this.author = authorNickname;
        this.joinKey = joinKey;
    }

    public Course(int id, String name, String authorNickname){
        this.id = id;
        this.name = name;
        this.author = authorNickname;
    }

    public String getJoinKey() {
        return joinKey;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }
}
