package com.example.todoproject.repository;

import com.example.todoproject.dto.TodoResponseDto;
import com.example.todoproject.dto.TodoWithoutIdResponseDto;

import java.util.List;

public interface TodoRepository {
    TodoResponseDto addTodo(String contents, long userId);

    List<TodoResponseDto> findAllTodos(String updatedDate, String userName);

    TodoWithoutIdResponseDto findById(long todoId);

    long findUserId(long todoId);

    int updateTodoContents(long todoId, String contents);
}
