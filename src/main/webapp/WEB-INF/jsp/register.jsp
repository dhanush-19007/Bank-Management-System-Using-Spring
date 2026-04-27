<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bank Management System - Register</title>
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
                <h1 class="hero-title">Register and get your default savings account instantly.</h1>
                <p class="hero-text">Every new registration creates a customer profile, a savings account, and an initial transaction entry.</p>
            </div>
            <div class="feature-list">
                <div class="feature-item"><strong>Fast Signup</strong><br><span class="muted">Simple form with full name, email, and password.</span></div>
                <div class="feature-item"><strong>Default Account</strong><br><span class="muted">Savings account is generated automatically for each user.</span></div>
                <div class="feature-item"><strong>Safe Reuse</strong><br><span class="muted">Existing users remain in the database across restarts.</span></div>
            </div>
        </section>
        <section class="form-panel">
            <div>
                <div class="small">Open a new account</div>
                <h2 class="form-title">Register</h2>
                <% if (request.getAttribute("error") != null) { %><div class="alert"><%= request.getAttribute("error") %></div><% } %>
                <form method="post" action="/register">
                    <div class="form-group"><label>Full Name</label><input type="text" name="fullName" placeholder="Enter full name" required></div>
                    <div class="form-group"><label>Email Address</label><input type="email" name="email" placeholder="Enter email" required></div>
                    <div class="form-group"><label>Password</label><input type="password" name="password" placeholder="Create password" required></div>
                    <button class="btn btn-primary" type="submit">Create Account</button>
                </form>
                <div class="link-row"><span class="muted">Already registered?</span><a class="btn btn-secondary" href="/login" style="width:auto;">Back to Login</a></div>
            </div>
        </section>
    </div>
</div>
</body>
</html>
