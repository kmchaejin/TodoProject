package com.example.todoproject.repository;

import com.example.todoproject.dto.RequestPostDto;
import org.springframework.stereotype.Repository;

@Repository
public class TodoRepositoryImpl implements TodoRepository{
    @Override
    public void addTodo(RequestPostDto dto) {
        // DB에 데이터 삽입하는 코드 작성
    }
}
