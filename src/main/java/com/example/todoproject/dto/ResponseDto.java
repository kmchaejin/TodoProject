package com.example.todoproject.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Getter
@RequiredArgsConstructor
public class ResponseDto {
    private long todoId;
    private String contents;
    private String userName;
    private Timestamp createdDate;
    private Timestamp updatedDate;

    public ResponseDto(long todoId, String userName, String contents, Timestamp createdDate, Timestamp updatedDate) {
        this.todoId = todoId;
        this.contents = contents;
        this.userName = userName;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
