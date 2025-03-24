package com.example.todoproject.entity;

import java.sql.Timestamp;

public class Todo {
    private long todoId;
    private long userId;
    private String contents;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
