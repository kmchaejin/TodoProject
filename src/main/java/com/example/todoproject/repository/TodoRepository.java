package com.example.todoproject.repository;

import com.example.todoproject.dto.ResponseDto;

import java.util.List;

public interface TodoRepository {
    ResponseDto addTodo(String contents, long userId);

    List<ResponseDto> findAllTodos(String updatedDate, String userName);
}
