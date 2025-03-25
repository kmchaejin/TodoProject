package com.example.todoproject.controller;

import com.example.todoproject.dto.RequestPostDto;
import com.example.todoproject.dto.TodoResponseDto;
import com.example.todoproject.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponseDto> addTodo(@RequestBody RequestPostDto dto){ //@Valid 추가
        return new ResponseEntity<>(todoService.addTodo(dto),HttpStatus.CREATED);
    }

    @GetMapping
    public List<TodoResponseDto> findAllTodos(@RequestParam String updatedDate, String userName){
        return todoService.findAllTodos(updatedDate, userName); // 상태코드 필요한가?
    }
}
