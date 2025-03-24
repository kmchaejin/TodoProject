package com.example.todoproject.dto;

import lombok.Getter;

@Getter
public class RequestPostDto {
    private String contents;
    private String userName;
    //@NonNull
    private int password;
}
