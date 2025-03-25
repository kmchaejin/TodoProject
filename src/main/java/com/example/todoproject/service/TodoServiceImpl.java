package com.example.todoproject.service;

import com.example.todoproject.dto.TodoRequestDto;
import com.example.todoproject.dto.TodoResponseDto;
import com.example.todoproject.dto.TodoWithoutIdResponseDto;
import com.example.todoproject.dto.UserPwRequestDto;
import com.example.todoproject.repository.TodoRepository;
import com.example.todoproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    // todo 생성
    @Override
    public TodoResponseDto addTodo(TodoRequestDto dto) {
        // DB에 User 데이터 삽입해서 자동 생성되는 userId 반환
        long userId = userRepository.addUser(dto.getUserName(), dto.getPassword());

        // userId와 요청 데이터를 DB에 저장하여 Todo 생성
        return todoRepository.addTodo(dto.getContents(), userId);
    }

    // 조건에 맞는 todo 전체 조회
    @Override
    public List<TodoResponseDto> findAllTodos(String updatedDate, String userName) {
        return todoRepository.findAllTodos(updatedDate, userName);
    }

    // todo 단건 조회
    @Override
    public TodoWithoutIdResponseDto findById(long todoId) {
        return todoRepository.findById(todoId);
    }

    // todo 수정
    @Override
    public TodoWithoutIdResponseDto updateTodo(long todoId, TodoRequestDto dto) {
        // 각 테이블에 데이터가 저장됐는지 확인하는 필드 선언
        int updateNameResult = 1, updateContentsResult = 1;

        // todoRepo - userId 반환
        long userId = todoRepository.findUserId(todoId);

        // userRepo - password 반환
        String rightPassword = userRepository.findPassword(userId);

        // password 일치하면 update
        if (dto.getPassword().equals(rightPassword)) {
            // 요청 데이터에 userName이 비어있지 않으면 userName 수정
            if (!dto.getUserName().isEmpty()) {
                updateNameResult = userRepository.updateUserName(userId, dto.getUserName());
            }

            // 요청 데이터에 contents가 비어있지 않으면 contents 수정
            if (!dto.getContents().isEmpty()) {
                updateContentsResult = todoRepository.updateTodoContents(todoId, dto.getContents());
            }

            // update된 레코드의 수가 0인 경우, 에러 메시지 출력
            if (updateNameResult == 0 || updateContentsResult == 0) {
                throw new NoSuchElementException("요청하신 Todo는 존재하지 않습니다.");
            }

            // findById로 수정된 Todo 반환
            return todoRepository.findById(todoId);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    // todo 삭제
    @Override
    public void deleteTodo(long todoId, UserPwRequestDto dto) {
        // todoRepo - userId 반환
        long userId = todoRepository.findUserId(todoId);

        // userRepo - password 반환
        String rightPassword = userRepository.findPassword(userId);

        // password 일치하면 delete
        if (dto.getPassword().equals(rightPassword)) {
            int result = todoRepository.deleteTodo(todoId);

            // delete된 레코드 수가 1이 아닌 경우, 에러 메시지 출력
            if (result != 1) {
                throw new NoSuchElementException("요청하신 Todo는 존재하지 않습니다.");
            }
        }else{
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
