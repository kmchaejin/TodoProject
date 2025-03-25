package com.example.todoproject.controller;

import com.example.todoproject.dto.TodoRequestDto;
import com.example.todoproject.dto.TodoResponseDto;
import com.example.todoproject.dto.TodoWithoutIdResponseDto;
import com.example.todoproject.dto.UserPwRequestDto;
import com.example.todoproject.service.TodoService;
import jakarta.validation.Valid;
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
    public ResponseEntity<TodoResponseDto> addTodo(@Valid @RequestBody TodoRequestDto dto){
        return new ResponseEntity<>(todoService.addTodo(dto),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> findAllTodos(@RequestParam(required = false) String updatedDate, @RequestParam(required = false) String userName){
        return new ResponseEntity<>(todoService.findAllTodos(updatedDate, userName), HttpStatus.OK);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<TodoWithoutIdResponseDto> findById(@PathVariable long todoId){
        return new ResponseEntity<>(todoService.findById(todoId), HttpStatus.OK);
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<TodoWithoutIdResponseDto> updateTodo(@PathVariable long todoId, @Valid @RequestBody TodoRequestDto dto){
        return new ResponseEntity<>(todoService.updateTodo(todoId, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable long todoId, @Valid @RequestBody UserPwRequestDto dto){
        todoService.deleteTodo(todoId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
