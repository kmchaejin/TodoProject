package com.example.todoproject.repository;

import com.example.todoproject.dto.RequestPostDto;

public interface TodoRepository {
    void addTodo(RequestPostDto dto);
}
