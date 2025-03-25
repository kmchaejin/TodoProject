package com.example.todoproject.service;

import com.example.todoproject.dto.TodoRequestDto;
import com.example.todoproject.dto.TodoResponseDto;
import com.example.todoproject.dto.TodoWithoutIdResponseDto;
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
    public TodoResponseDto addTodo(TodoRequestDto dto) {
        long userId = userRepository.addUser(dto.getUserName(), dto.getPassword());
        return todoRepository.addTodo(dto.getContents(), userId);
    }

    @Override
    public List<TodoResponseDto> findAllTodos(String updatedDate, String userName) {
        return todoRepository.findAllTodos(updatedDate, userName);
    }

    @Override
    public TodoWithoutIdResponseDto findById(long todoId) {
        return todoRepository.findById(todoId);
    }

    @Override
    public TodoWithoutIdResponseDto updateTodo(long todoId, TodoRequestDto dto) {
        int updateNameResult = 1, updateContentsResult = 1;

        // todoRepo - userId 반환
        long userId = todoRepository.findUserId(todoId);

        // userRepo - password 반환
        String rightPassword = userRepository.findPassword(userId);

        // password 일치하면 update
        if(dto.getPassword().equals(rightPassword)){
            if(!dto.getUserName().isEmpty()) {
                //userName 변경
                updateNameResult = userRepository.updateUserName(userId, dto.getUserName());
            }

            if(!dto.getContents().isEmpty()){
                //contents 변경
                updateContentsResult = todoRepository.updateTodoContents(todoId, dto.getContents());
            }

            if(updateNameResult == 0 || updateContentsResult == 0){
                throw new RuntimeException("변경이 실패했습니다. 이유는?");
            }

            // findById로 수정된 투두 반환
            return todoRepository.findById(todoId);
        }else{
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다. 할 일을 수정하실 수 없습니다.");
        }
    }
}
