package com.example.todoproject.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{
    private final JdbcTemplate jdbcTemplate;

    // todo 생성할 때마다 사용자 정보 생성 후 userId 반환
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

    // 사용자 비밀번호 반환
    @Override
    public String findPassword(long userId) {
        List<String> userPwList = jdbcTemplate.query("select password from user where id = ?", userRowMapper(), userId);
        String password = userPwList.stream().findFirst().orElseThrow();

        return password;
    }

    // 사용자 이름 변경
    @Override
    public int updateUserName(long userId, String userName) {
        return jdbcTemplate.update("update user set user_name = ? where id = ?", userName, userId);
    }

    // password 데이터 매핑
    private RowMapper<String> userRowMapper() {
        return new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("password");
            }
        };
    }
}
