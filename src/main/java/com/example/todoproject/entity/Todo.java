package com.example.todoproject.entity;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class Todo {
    private long todoId;
    private long userId;
    private String contents;
    private Timestamp createdDate;
    private String updatedDate;
}
