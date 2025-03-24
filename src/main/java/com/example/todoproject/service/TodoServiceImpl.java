package com.example.todoproject.service;

import com.example.todoproject.dto.RequestPostDto;
import com.example.todoproject.dto.ResponseDto;
import com.example.todoproject.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{
    private final TodoRepository todoRepository;

    @Override
    public ResponseDto addTodo(RequestPostDto dto) {
        todoRepository.addTodo(dto);

        return new ResponseDto();
    }
}
