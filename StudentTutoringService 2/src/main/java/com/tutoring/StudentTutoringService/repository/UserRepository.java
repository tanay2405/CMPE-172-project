package com.tutoring.StudentTutoringService.repository;

import com.tutoring.StudentTutoringService.model.UserInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserInfo findByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM UserInfo WHERE email = ? AND password = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                UserInfo user = new UserInfo();
                user.setUserID(rs.getInt("userID"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                return user;
            }, email, password);
        } catch (Exception e) {
            return null;
        }
    }

    public void insertUser(String name, String email, String password, String role) {
        String sql = "INSERT INTO UserInfo (name, email, password, role) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, name, email, password, role);
    }
}