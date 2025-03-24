package com.example.todoproject.entity;

import lombok.Getter;

@Getter
public class User {
    private long userId;
    private String userName;
    private String password;

    // addTodo
    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
}
