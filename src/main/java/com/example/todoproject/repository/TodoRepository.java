package com.example.todoproject.repository;

import com.example.todoproject.dto.RequestPostDto;
import com.example.todoproject.dto.TodoResponseDto;
import com.example.todoproject.entity.Todo;

import java.sql.Timestamp;
import java.util.List;

public interface TodoRepository {
    Todo addTodo(RequestPostDto dto);

    List<TodoResponseDto> findAllTodos(Timestamp updatedDate, String userName);
}
