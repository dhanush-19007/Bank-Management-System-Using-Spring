package com.example.bankmanagement.service;

import com.example.bankmanagement.model.Account;
import com.example.bankmanagement.model.User;
import com.example.bankmanagement.repository.AccountRepository;
import com.example.bankmanagement.repository.TransactionRepository;
import com.example.bankmanagement.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AuthService(UserRepository userRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public User login(String email, String password) {
        if (email == null || password == null) return null;
        return userRepository.findByEmailAndPassword(email.trim().toLowerCase(), password);
    }

    @Transactional
    public String register(String fullName, String email, String password) {
        try {
            if (fullName == null || fullName.isBlank() || email == null || email.isBlank() || password == null || password.isBlank()) {
                return "All fields are required.";
            }
            String cleanEmail = email.trim().toLowerCase();
            if (userRepository.findByEmail(cleanEmail) != null) {
                return "Email already registered.";
            }

            User user = new User();
            user.setFullName(fullName.trim());
            user.setEmail(cleanEmail);
            user.setPassword(password);
            user.setRole("CUSTOMER");
            userRepository.save(user);

            User savedUser = userRepository.findByEmail(cleanEmail);
            if (savedUser == null || savedUser.getId() == null) {
                return "Registration failed while creating user.";
            }

            String accountNumber = "AC" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
            accountRepository.createDefaultAccount(savedUser.getId(), accountNumber);

            Account createdAccount = accountRepository.findTopByUserId(savedUser.getId());
            if (createdAccount == null || createdAccount.getId() == null) {
                return "Registration failed while creating account.";
            }

            transactionRepository.save(createdAccount.getId(), "DEPOSIT", new BigDecimal("0.00"), "Account created successfully");
            return "Registration successful. Please login.";
        } catch (DataAccessException ex) {
            return "Database error during registration: " + ex.getMostSpecificCause().getMessage();
        } catch (Exception ex) {
            return "Registration failed: " + ex.getMessage();
        }
    }
}
