package com.example.todoproject.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{
    private final JdbcTemplate jdbcTemplate;

    @Override
    public long addUser(String userName, String password) {
        // 삽입할 테이블, pk 설정
        SimpleJdbcInsert insertUserInfo = new SimpleJdbcInsert(jdbcTemplate);
        insertUserInfo.withTableName("user").usingGeneratedKeyColumns("id");

        // DB에 전달할 데이터를 파라미터에 저장
        Map<String, Object> userParameters = new HashMap<>();
        userParameters.put("user_name", userName);
        userParameters.put("password", password);

        // DB에 데이터 저장 후 userId 반환
        Number userId = insertUserInfo.executeAndReturnKey(new MapSqlParameterSource(userParameters));

        // userId 반환
        return userId.longValue();
    }
}
