package com.example.todoproject.repository;

import com.example.todoproject.dto.RequestPostDto;
import com.example.todoproject.dto.TodoResponseDto;

import java.util.List;

public interface TodoRepository {
    TodoResponseDto addTodo(RequestPostDto dto);

    List<TodoResponseDto> findAllTodos(String updatedDate, String userName);
}
