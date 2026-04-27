package com.example.bankmanagement.repository;

import com.example.bankmanagement.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> mapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        return user;
    };

    public User findByEmailAndPassword(String email, String password) {
        List<User> users = jdbcTemplate.query("select * from users where email=? and password=?", mapper, email, password);
        return users.isEmpty() ? null : users.get(0);
    }

    public User findByEmail(String email) {
        List<User> users = jdbcTemplate.query("select * from users where email=?", mapper, email);
        return users.isEmpty() ? null : users.get(0);
    }

    public void save(User user) {
        jdbcTemplate.update("insert into users(full_name, email, password, role) values (?, ?, ?, ?)",
                user.getFullName(), user.getEmail(), user.getPassword(), user.getRole());
    }
}
