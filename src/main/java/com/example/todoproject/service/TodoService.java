package com.example.todoproject.service;

import com.example.todoproject.dto.RequestPostDto;
import com.example.todoproject.dto.TodoResponseDto;

import java.sql.Timestamp;
import java.util.List;

public interface TodoService {
    TodoResponseDto addTodo(RequestPostDto dto);

    List<TodoResponseDto> findAllTodos(Timestamp updatedDate, String userName);
}
