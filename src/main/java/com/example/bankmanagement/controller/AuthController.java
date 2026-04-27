package com.example.bankmanagement.controller;

import com.example.bankmanagement.model.User;
import com.example.bankmanagement.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = authService.login(email, password);
        if (user == null) {
            model.addAttribute("error", "Invalid email or password.");
            return "login";
        }
        session.setAttribute("loggedInUser", user);
        return "redirect:/dashboard";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String fullName,
                           @RequestParam String email,
                           @RequestParam String password,
                           Model model) {
        String result = authService.register(fullName, email, password);
        if (!"Registration successful. Please login.".equals(result)) {
            model.addAttribute("error", result);
            return "register";
        }
        model.addAttribute("success", result);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
