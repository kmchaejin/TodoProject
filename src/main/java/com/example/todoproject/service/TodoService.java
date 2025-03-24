package com.example.todoproject.service;

import com.example.todoproject.dto.RequestPostDto;
import com.example.todoproject.dto.ResponseDto;

public interface TodoService {
    ResponseDto addTodo(RequestPostDto dto);
}
