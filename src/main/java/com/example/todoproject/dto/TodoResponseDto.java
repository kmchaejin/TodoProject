package com.example.todoproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class TodoResponseDto {
    private long todoId;
    private String contents;
    private String userName;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
