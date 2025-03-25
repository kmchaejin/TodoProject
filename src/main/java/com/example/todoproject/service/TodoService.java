package com.example.todoproject.service;

import com.example.todoproject.dto.RequestPostDto;
import com.example.todoproject.dto.ResponseDto;

import java.util.List;

public interface TodoService {
    ResponseDto addTodo(RequestPostDto dto);

    List<ResponseDto> findAllTodos(String updatedDate, String userName);
}
