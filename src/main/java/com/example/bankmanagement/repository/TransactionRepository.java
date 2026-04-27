package com.example.bankmanagement.repository;

import com.example.bankmanagement.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;

    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Transaction> mapper = (rs, rowNum) -> {
        Transaction tx = new Transaction();
        tx.setId(rs.getLong("id"));
        tx.setAccountId(rs.getLong("account_id"));
        tx.setTransactionType(rs.getString("transaction_type"));
        tx.setAmount(rs.getBigDecimal("amount"));
        tx.setReferenceNote(rs.getString("reference_note"));
        tx.setTransactionTime(rs.getTimestamp("transaction_time").toLocalDateTime());
        return tx;
    };

    public List<Transaction> findByAccountId(Long accountId) {
        return jdbcTemplate.query("select * from transactions where account_id=? order by transaction_time desc, id desc", mapper, accountId);
    }

    public void save(Long accountId, String type, BigDecimal amount, String note) {
        jdbcTemplate.update("insert into transactions(account_id, transaction_type, amount, reference_note) values (?, ?, ?, ?)", accountId, type, amount, note);
    }
}
