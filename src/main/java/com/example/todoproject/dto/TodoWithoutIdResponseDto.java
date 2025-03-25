package com.example.todoproject.dto;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class TodoWithoutIdResponseDto {
    private String contents;
    private String userName;
    private Timestamp createdDate;
    private Timestamp updatedDate;

    public TodoWithoutIdResponseDto(String userName, String contents, Timestamp createdDate, Timestamp updatedDate) {
        this.contents = contents;
        this.userName = userName;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
