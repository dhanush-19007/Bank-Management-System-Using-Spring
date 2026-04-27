<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bank Management System - Login</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
<div class="container login-shell">
    <div class="login-grid">
        <section class="brand-panel">
            <div>
                <div class="logo"><div class="logo-mark">B</div><div>BluePeak Bank</div></div>
                <h1 class="hero-title">Fixed banking login and registration with account transaction details.</h1>
                <p class="hero-text">Use the demo login or register a new user. Registered users stay available because the schema no longer resets on every restart.</p>
            </div>
            <div class="feature-list">
                <div class="feature-item"><strong>Registration</strong><br><span class="muted">Create a customer account directly from the frontend.</span></div>
                <div class="feature-item"><strong>Login</strong><br><span class="muted">Session-based access to the account dashboard.</span></div>
                <div class="feature-item"><strong>Transaction Details</strong><br><span class="muted">Detailed account history visible in the dashboard table.</span></div>
            </div>
        </section>
        <section class="form-panel">
            <div>
                <div class="small">Welcome back</div>
                <h2 class="form-title">Login to your account</h2>
                <p class="muted" style="margin-bottom:24px;">Demo credentials: <strong>user@bank.com</strong> / <strong>user123</strong></p>
                <% if (request.getAttribute("error") != null) { %><div class="alert"><%= request.getAttribute("error") %></div><% } %>
                <% if (request.getAttribute("success") != null) { %><div class="alert success"><%= request.getAttribute("success") %></div><% } %>
                <form method="post" action="/login">
                    <div class="form-group"><label>Email Address</label><input type="email" name="email" placeholder="Enter your email" required></div>
                    <div class="form-group"><label>Password</label><input type="password" name="password" placeholder="Enter your password" required></div>
                    <button class="btn btn-primary" type="submit">Login to Dashboard</button>
                </form>
                <div class="link-row"><span class="muted">New user?</span><a class="btn btn-secondary" href="/register" style="width:auto;">Create Account</a></div>
            </div>
        </section>
    </div>
</div>
</body>
</html>
