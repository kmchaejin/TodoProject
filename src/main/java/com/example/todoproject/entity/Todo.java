package com.example.todoproject.entity;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class Todo {
    private long todoId;
    private long userId;
    private String contents;
    private Timestamp createdDate;
    private Timestamp updatedDate;

    // addTodo
    public Todo(String contents) {
        this.contents = contents;
    }

    // get Timestamp from DB
//    public Todo(Timestamp createdDate, Timestamp updatedDate) {
//        this.createdDate = createdDate;
//        this.updatedDate = updatedDate;
//    }

    public Todo(long todoId, long userId, String contents, Timestamp createdDate, Timestamp updatedDate) {
        this.todoId = todoId;
        this.userId = userId;
        this.contents = contents;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
