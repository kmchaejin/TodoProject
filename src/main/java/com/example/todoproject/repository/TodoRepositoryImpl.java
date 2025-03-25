package com.example.todoproject.repository;

import com.example.todoproject.dto.RequestPostDto;
import com.example.todoproject.dto.TodoResponseDto;
import com.example.todoproject.entity.Todo;
import com.example.todoproject.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TodoRepositoryImpl implements TodoRepository {
    private final JdbcTemplate jdbcTemplate;
    LocalDateTime localDateTime = LocalDateTime.now();
    Timestamp timestamp = Timestamp.valueOf(localDateTime);

    public TodoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Todo addTodo(RequestPostDto dto) {
        Todo todo = new Todo(dto.getContents());
        User user = new User(dto.getUserName(), dto.getPassword());

        // 삽입할 테이블, pk 설정
        SimpleJdbcInsert insertUserInfo = new SimpleJdbcInsert(jdbcTemplate);
        insertUserInfo.withTableName("user").usingGeneratedKeyColumns("id");

        SimpleJdbcInsert insertTodoInfo = new SimpleJdbcInsert(jdbcTemplate);
        insertTodoInfo.withTableName("todo").usingGeneratedKeyColumns("id");

        // DB에 전달할 데이터를 파라미터에 저장
        Map<String, Object> userParameters = new HashMap<>();
        userParameters.put("user_name", user.getUserName());
        userParameters.put("password", user.getPassword());

        Map<String, Object> todoParameters = new HashMap<>();
        todoParameters.put("contents", todo.getContents());
        todoParameters.put("created_date", timestamp);
        todoParameters.put("updated_date", timestamp);

        // DB에 데이터 저장 후 userId, todoId 반환
        Number userId = insertUserInfo.executeAndReturnKey(new MapSqlParameterSource(userParameters));
        Number todoId = insertTodoInfo.executeAndReturnKey(new MapSqlParameterSource(todoParameters));

        // 투두 객체 가져오기
        // 조인mapper로 변경 필요
        List<Todo> todoList = jdbcTemplate.query("select * from todo where id = ?", todoRowMapper(), todoId);
        Todo foundTodo = todoList.stream().findFirst().orElseThrow(); // orElseThrow() : null이면 예외 발생

        return foundTodo;
    }

    @Override
    public List<TodoResponseDto> findAllTodos(Timestamp updatedDate, String userName) {
        // updatedDate 타입 String -> timestamp로 변환 필요
        Timestamp timestampUpdatedDate = Timestamp.valueOf(updatedDate.toLocalDateTime());

        // 유저 네임과 투두 객체가 필요함
        // 방법1. 각각 sql문을 작성해서 따로 가져오기
        // 방법2. 유저 네임과 투두 객체의 데이터를 모두 가지는 전용 dto 생성 -> userid가 제외된 것만 빼면 똑같아서 굳이 안만들어도 될 듯
        List<TodoResponseDto> todoResponseDtoList = jdbcTemplate.query("select id, user_name, contents, created_date, updated_date from todo left join user on todo.user_id = user.id where updateDate = ? or user_name = ?", joinRowMapper(), timestampUpdatedDate, userName);

        return todoResponseDtoList;
    }

    private RowMapper<Todo> todoRowMapper() {
        return new RowMapper<Todo>() {
            @Override
            public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Todo(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("contents"),
                        rs.getTimestamp("created_date"),
                        rs.getTimestamp("updated_date")
                );
            }
        };
    }

    private RowMapper<TodoResponseDto> joinRowMapper() {
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
}
