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
import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepository {
    private final JdbcTemplate jdbcTemplate;

    // todo 생성 날짜, 시간
    LocalDateTime localDateTime = LocalDateTime.now();
    Timestamp timestamp = Timestamp.valueOf(localDateTime);

    // todo 생성
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

        // DB에 데이터 저장 후 todoId 반환
        Number todoId = insertTodoInfo.executeAndReturnKey(new MapSqlParameterSource(todoParameters));

        // 생성된 todo 반환
        List<TodoResponseDto> todoList = jdbcTemplate.query("select todo.id, user_name, contents, created_date, updated_date from todo left join user on todo.user_id = user.id where todo.id = ?", joinRowMapperV1(), todoId);
        TodoResponseDto addedTodo = todoList.stream().findFirst().orElseThrow();

        return addedTodo;
    }

    // 조건에 맞는 todo를 리스트로 조회
    @Override
    public List<TodoResponseDto> findAllTodos(String updatedDate, String userName) {
        Date formatUpdatedDate;

        // updatedDate가 null이 아닐 때만 String 타입의 updatedDate를 Date 타입으로 변환
        if (updatedDate == null) {
            formatUpdatedDate = null;
        } else {
            formatUpdatedDate = Date.valueOf(updatedDate);
        }

        // todo 테이블, user 테이블에 분리되어 있는 데이터를 하나의 객체에 저장하기 위해 DTO에 바로 저장
        List<TodoResponseDto> todoResponseDtoList = jdbcTemplate.query("select todo.id, contents, user_name, created_date, updated_date from todo left join user on todo.user_id = user.id " +
                "where DATE_FORMAT(updated_date, '%Y-%m-%d') = COALESCE(?, DATE_FORMAT(updated_date, '%Y-%m-%d')) " +
                "and user_name = COALESCE(?, user_name) " +
                "order by updated_date desc", joinRowMapperV1(), formatUpdatedDate, userName);

        return todoResponseDtoList;
    }

    // todo 단건 조회
    @Override
    public TodoWithoutIdResponseDto findById(long todoId) throws NoSuchElementException {
        List<TodoWithoutIdResponseDto> todoList = jdbcTemplate.query("select * from todo left join user on todo.user_id = user.id where todo.id = ?", joinRowMapperV2(), todoId);
        TodoWithoutIdResponseDto foundTodo = todoList.stream().findFirst().orElseThrow();

        return foundTodo;
    }

    // userId 반환
    @Override
    public long findUserId(long todoId) {
        List<UserIdResponseDto> userIdList = jdbcTemplate.query("select user_id from todo where id = ?", todoRowMapper(), todoId);
        UserIdResponseDto userId = userIdList.stream().findFirst().orElseThrow();

        return userId.getUserId();
    }

    // todo 내용 수정
    @Override
    public int updateTodoContents(long todoId, String contents) {
        return jdbcTemplate.update("update todo set contents = ? where id = ?", contents, todoId);
    }

    // todo 삭제
    @Override
    public int deleteTodo(long todoId) {
        return jdbcTemplate.update("delete from todo where id = ?", todoId);
    }

    // id가 포함된 반환 데이터 매핑
    private RowMapper<TodoResponseDto> joinRowMapperV1() {
        return new RowMapper<TodoResponseDto>() {
            @Override
            public TodoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TodoResponseDto(
                        rs.getLong("id"),
                        rs.getString("contents"),
                        rs.getString("user_name"),
                        rs.getTimestamp("created_date"),
                        rs.getTimestamp("updated_date")
                );
            }
        };
    }

    // id가 제외된 반환 데이터 매핑
    private RowMapper<TodoWithoutIdResponseDto> joinRowMapperV2() {
        return new RowMapper<TodoWithoutIdResponseDto>() {
            @Override
            public TodoWithoutIdResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TodoWithoutIdResponseDto(
                        rs.getString("contents"),
                        rs.getString("user_name"),
                        rs.getTimestamp("created_date"),
                        rs.getTimestamp("updated_date")
                );
            }
        };
    }

    // userId 매핑
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
