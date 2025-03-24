package com.example.todoproject.repository;

import com.example.todoproject.dto.RequestPostDto;
import com.example.todoproject.dto.ResponseDto;

public interface TodoRepository {
    ResponseDto addTodo(RequestPostDto dto);
}
