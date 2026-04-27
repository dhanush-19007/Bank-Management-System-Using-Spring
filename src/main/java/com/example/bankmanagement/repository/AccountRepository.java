package com.example.bankmanagement.repository;

import com.example.bankmanagement.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class AccountRepository {
    private final JdbcTemplate jdbcTemplate;

    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Account> mapper = (rs, rowNum) -> {
        Account account = new Account();
        account.setId(rs.getLong("id"));
        account.setUserId(rs.getLong("user_id"));
        account.setAccountNumber(rs.getString("account_number"));
        account.setAccountType(rs.getString("account_type"));
        account.setBalance(rs.getBigDecimal("balance"));
        account.setStatus(rs.getString("status"));
        account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return account;
    };

    public List<Account> findByUserId(Long userId) {
        return jdbcTemplate.query("select * from accounts where user_id=? order by created_at desc", mapper, userId);
    }

    public Account findById(Long id) {
        List<Account> accounts = jdbcTemplate.query("select * from accounts where id=?", mapper, id);
        return accounts.isEmpty() ? null : accounts.get(0);
    }

    public Account findTopByUserId(Long userId) {
        List<Account> accounts = jdbcTemplate.query("select * from accounts where user_id=? order by created_at desc, id desc limit 1", mapper, userId);
        return accounts.isEmpty() ? null : accounts.get(0);
    }

    public void createDefaultAccount(Long userId, String accountNumber) {
        jdbcTemplate.update("insert into accounts(user_id, account_number, account_type, balance, status) values (?, ?, ?, ?, ?)",
                userId, accountNumber, "Savings", new BigDecimal("0.00"), "ACTIVE");
    }

    public void updateBalance(Long accountId, BigDecimal balance) {
        jdbcTemplate.update("update accounts set balance=? where id=?", balance, accountId);
    }
}
