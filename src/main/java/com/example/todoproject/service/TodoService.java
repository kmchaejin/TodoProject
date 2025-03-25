package com.example.todoproject.service;

import com.example.todoproject.dto.TodoRequestDto;
import com.example.todoproject.dto.TodoResponseDto;
import com.example.todoproject.dto.TodoWithoutIdResponseDto;

import java.util.List;

public interface TodoService {
    TodoResponseDto addTodo(TodoRequestDto dto);

    List<TodoResponseDto> findAllTodos(String updatedDate, String userName);

    TodoWithoutIdResponseDto findById(long todoId);

    TodoWithoutIdResponseDto updateTodo(long todoId, TodoRequestDto dto);
}
