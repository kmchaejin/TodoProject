package com.example.todoproject.repository;

import com.example.todoproject.dto.TodoResponseDto;
import com.example.todoproject.dto.TodoWithoutIdResponseDto;
import com.example.todoproject.dto.UserIdResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepository {
    private final JdbcTemplate jdbcTemplate;

    LocalDateTime localDateTime = LocalDateTime.now();
    Timestamp timestamp = Timestamp.valueOf(localDateTime);

    @Override
    public TodoResponseDto addTodo(String contents, long userId) {
        // 삽입할 테이블, pk 설정
        SimpleJdbcInsert insertTodoInfo = new SimpleJdbcInsert(jdbcTemplate);
        insertTodoInfo.withTableName("todo").usingGeneratedKeyColumns("id");

        // DB에 전달할 데이터를 파라미터에 저장
        Map<String, Object> todoParameters = new HashMap<>();
        todoParameters.put("user_id", userId);
        todoParameters.put("contents", contents);
        todoParameters.put("created_date", timestamp);
        todoParameters.put("updated_date", timestamp);

        // DB에 데이터 저장 후 userId, todoId 반환
        Number todoId = insertTodoInfo.executeAndReturnKey(new MapSqlParameterSource(todoParameters));

        // 투두 객체 가져오기
        List<TodoResponseDto> todoList = jdbcTemplate.query("select * from todo left join user on todo.user_id = user.id where todo.id = ?", joinRowMapperV1(), todoId);
        TodoResponseDto addedTodo = todoList.stream().findFirst().orElseThrow(); // orElseThrow() : null이면 예외 발생

        return addedTodo;
    }

    @Override
    public List<TodoResponseDto> findAllTodos(String updatedDate, String userName) {
        // updatedDate 타입 String -> Date로 변환
        Date formatUpdatedDate = Date.valueOf(updatedDate);

        // 유저 네임과 투두 객체가 필요함
        // 방법1. 각각 sql문을 작성해서 따로 가져오기
        // 방법2. 유저 네임과 투두 객체의 데이터를 모두 가지는 전용 dto 생성 -> userid가 제외된 것만 빼면 똑같아서 굳이 안만들어도 될 듯
        List<TodoResponseDto> todoResponseDtoList = jdbcTemplate.query("select todo.id, user_name, contents, created_date, updated_date from todo left join user on todo.user_id = user.id where DATE_FORMAT(updated_date, '%Y-%m-%d') = ? or user_name = ?", joinRowMapperV1(), formatUpdatedDate, userName);

        return todoResponseDtoList;
    }

    @Override
    public TodoWithoutIdResponseDto findById(long todoId) {
        List<TodoWithoutIdResponseDto> todoList = jdbcTemplate.query("select * from todo left join user on todo.user_id = user.id where todo.id = ?", joinRowMapperV2(), todoId);
        TodoWithoutIdResponseDto foundTodo = todoList.stream().findFirst().orElseThrow();

        return foundTodo;
    }

    @Override
    public long findUserId(long todoId) {
        List<UserIdResponseDto> userIdList = jdbcTemplate.query("select user_id from todo where id = ?", todoRowMapper(), todoId);
        UserIdResponseDto userId = userIdList.stream().findFirst().orElseThrow();
        //dto 없앨까.. -> 못없앰!
        return userId.getUserId();
    }

    @Override
    public int updateTodoContents(long todoId, String contents) {
        return jdbcTemplate.update("update todo set contents = ? where id = ?", contents, todoId);
    }

    @Override
    public int deleteTodo(long todoId) {
        return jdbcTemplate.update("delete from todo where id = ?", todoId);
    }


    private RowMapper<TodoResponseDto> joinRowMapperV1() {
        return new RowMapper<TodoResponseDto>() {
            @Override
            public TodoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TodoResponseDto(
                        rs.getLong("id"),
                        rs.getString("user_name"),
                        rs.getString("contents"),
                        rs.getTimestamp("created_date"),
                        rs.getTimestamp("updated_date")
                );
            }
        };
    }

    // 투두 id 없는 버전
    private RowMapper<TodoWithoutIdResponseDto> joinRowMapperV2() {
        return new RowMapper<TodoWithoutIdResponseDto>() {
            @Override
            public TodoWithoutIdResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TodoWithoutIdResponseDto(
                        rs.getString("user_name"),
                        rs.getString("contents"),
                        rs.getTimestamp("created_date"),
                        rs.getTimestamp("updated_date")
                );
            }
        };
    }

    private RowMapper<UserIdResponseDto> todoRowMapper() {
        return new RowMapper<UserIdResponseDto>() {
            @Override
            public UserIdResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new UserIdResponseDto(
                        rs.getLong("user_id")
                );
            }
        };
    }
}
