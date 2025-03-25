package com.example.todoproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TodoRequestDto {
    private String contents;
    private String userName;
    @NotBlank(message = "비밀번호를 입력해주세요.") // 수정, 삭제 시 검증용
    private String password;
}
