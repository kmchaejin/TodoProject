package com.example.todoproject.repository;

import com.example.todoproject.dto.RequestPostDto;
import com.example.todoproject.dto.ResponseDto;
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
    public ResponseDto addTodo(RequestPostDto dto) {
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

        // Todo 객체 가져오기
        List<Todo> todoEntityList = jdbcTemplate.query("select * from todo where id = ?", todoRowMapper(), todoId);
        Todo todoEntity = todoEntityList.stream().findFirst().orElseThrow(); // orElseThrow() : null이면 예외 발생

        return new ResponseDto(todoId.longValue(), todo.getContents(), user.getUserName(), todoEntity.getCreatedDate(), todoEntity.getUpdatedDate());
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

    // Timestamp 값 가져오기
    //List<Timestamp> dates = jdbcTemplate.query("select created_date, updated_date from todo where id = ?", todoRowMapper(), todoId);

//    private RowMapper<List<Timestamp>> todoRowMapper() {
//        return new RowMapper<List<Timestamp>>() {
//            @Override
//            public List<Timestamp> mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return List.of( // 나중에 아예 Todo 객체로 가져오기
//                        rs.getTimestamp("created_date"),
//                        rs.getTimestamp("updated_date")
//                );
//            }
//        };
//    }
}
