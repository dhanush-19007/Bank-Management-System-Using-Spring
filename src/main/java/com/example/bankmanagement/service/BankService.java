package com.example.bankmanagement.service;

import com.example.bankmanagement.model.Account;
import com.example.bankmanagement.model.Transaction;
import com.example.bankmanagement.repository.AccountRepository;
import com.example.bankmanagement.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public BankService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<Account> getAccountsByUser(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    public Account getAccount(Long accountId) {
        return accountRepository.findById(accountId);
    }

    public List<Transaction> getTransactions(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    @Transactional
    public String deposit(Long accountId, BigDecimal amount, String note) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) return "Amount must be greater than zero.";
        Account account = accountRepository.findById(accountId);
        if (account == null) return "Account not found.";
        accountRepository.updateBalance(accountId, account.getBalance().add(amount));
        transactionRepository.save(accountId, "DEPOSIT", amount, (note == null || note.isBlank()) ? "Cash deposit" : note.trim());
        return "Deposit completed successfully.";
    }

    @Transactional
    public String withdraw(Long accountId, BigDecimal amount, String note) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) return "Amount must be greater than zero.";
        Account account = accountRepository.findById(accountId);
        if (account == null) return "Account not found.";
        if (account.getBalance().compareTo(amount) < 0) return "Insufficient balance.";
        accountRepository.updateBalance(accountId, account.getBalance().subtract(amount));
        transactionRepository.save(accountId, "WITHDRAW", amount, (note == null || note.isBlank()) ? "ATM withdrawal" : note.trim());
        return "Withdrawal completed successfully.";
    }
}
