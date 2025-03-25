package com.example.todoproject.dto;

import lombok.Getter;

@Getter
public class UserPwRequestDto { // Controller에서 Service로 값 전달하는 과정에서 RequestBody 자체가 아니라 pw 데이터만 전달하기 위함
    private String password;
}
