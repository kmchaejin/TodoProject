package com.example.todoproject.service;

import com.example.todoproject.dto.RequestPostDto;
import com.example.todoproject.dto.TodoResponseDto;
import com.example.todoproject.entity.Todo;
import com.example.todoproject.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{
    private final TodoRepository todoRepository;

    @Override
    public TodoResponseDto addTodo(RequestPostDto dto) {
        Todo todo = todoRepository.addTodo(dto);
        // 레포지토리 레이어에서 각 필드를 responseDTO에 담기엔 서비스 레이어가 너무 일이 없는 것 같아서 여기서 진행
        // 투두 객체에 userName이 없어서 dto에서 가져옴(레포지토리에는 해당 유저 객체가 있긴 한데..)
        return new TodoResponseDto(todo.getTodoId(), todo.getContents(), dto.getUserName(), todo.getCreatedDate(), todo.getUpdatedDate());
    }

    @Override
    public List<TodoResponseDto> findAllTodos(Timestamp updatedDate, String userName) {
        return todoRepository.findAllTodos(updatedDate, userName);
    }
}
