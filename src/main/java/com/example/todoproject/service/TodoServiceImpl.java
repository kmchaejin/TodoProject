package com.example.todoproject.service;

import com.example.todoproject.dto.RequestPostDto;
import com.example.todoproject.dto.ResponseDto;
import com.example.todoproject.repository.TodoRepository;
import com.example.todoproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseDto addTodo(RequestPostDto dto) {
        long userId = userRepository.addUser(dto.getUserName(), dto.getPassword());
        return todoRepository.addTodo(dto.getContents(), userId);
    }

    @Override
    public List<ResponseDto> findAllTodos(String updatedDate, String userName) {
        return todoRepository.findAllTodos(updatedDate, userName);
    }
}
