package com.example.bankmanagement.controller;

import com.example.bankmanagement.model.Account;
import com.example.bankmanagement.model.User;
import com.example.bankmanagement.service.BankService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class BankController {
    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    private User sessionUser(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(required = false) Long accountId,
                            @RequestParam(required = false) String message,
                            HttpSession session,
                            Model model) {
        User user = sessionUser(session);
        if (user == null) {
            return "redirect:/login";
        }
        List<Account> accounts = bankService.getAccountsByUser(user.getId());
        Account selectedAccount = null;
        if (!accounts.isEmpty()) {
            selectedAccount = accountId != null ? bankService.getAccount(accountId) : accounts.get(0);
            if (selectedAccount == null || !selectedAccount.getUserId().equals(user.getId())) {
                selectedAccount = accounts.get(0);
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("accounts", accounts);
        model.addAttribute("selectedAccount", selectedAccount);
        model.addAttribute("transactions", selectedAccount != null ? bankService.getTransactions(selectedAccount.getId()) : List.of());
        model.addAttribute("message", message);
        return "dashboard";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam Long accountId,
                          @RequestParam BigDecimal amount,
                          @RequestParam(required = false) String note,
                          HttpSession session) {
        if (sessionUser(session) == null) return "redirect:/login";
        String message = bankService.deposit(accountId, amount, note);
        return "redirect:/dashboard?accountId=" + accountId + "&message=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam Long accountId,
                           @RequestParam BigDecimal amount,
                           @RequestParam(required = false) String note,
                           HttpSession session) {
        if (sessionUser(session) == null) return "redirect:/login";
        String message = bankService.withdraw(accountId, amount, note);
        return "redirect:/dashboard?accountId=" + accountId + "&message=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
    }
}
