package com.example.todoproject.controller;

import com.example.todoproject.dto.RequestPostDto;
import com.example.todoproject.dto.ResponseDto;
import com.example.todoproject.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<ResponseDto> addTodo(@RequestBody RequestPostDto dto){ //@Valid 추가
        return new ResponseEntity<>(todoService.addTodo(dto),HttpStatus.CREATED);
    }
}
