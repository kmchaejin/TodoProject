package com.example.todoproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TodoRequestDto {
    private String contents;
    private String userName;
    private String password;
}
