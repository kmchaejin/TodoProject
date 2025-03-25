package com.example.todoproject.service;

import com.example.todoproject.dto.RequestPostDto;
import com.example.todoproject.dto.TodoResponseDto;
import com.example.todoproject.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{
    private final TodoRepository todoRepository;

    @Override
    public TodoResponseDto addTodo(RequestPostDto dto) {
        return todoRepository.addTodo(dto);
    }

    @Override
    public List<TodoResponseDto> findAllTodos(String updatedDate, String userName) {
        return todoRepository.findAllTodos(updatedDate, userName);
    }
}
