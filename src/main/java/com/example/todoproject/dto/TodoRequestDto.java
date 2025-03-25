package com.example.todoproject.dto;

import lombok.Getter;

@Getter
public class TodoRequestDto {
    private String contents;
    private String userName;
    //@NonNull
    private String password;
}
