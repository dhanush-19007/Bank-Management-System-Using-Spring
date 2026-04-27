<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.bankmanagement.model.Account" %>
<%@ page import="com.example.bankmanagement.model.Transaction" %>
<%
    List<Account> accounts = (List<Account>) request.getAttribute("accounts");
    Account selectedAccount = (Account) request.getAttribute("selectedAccount");
    List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");
    com.example.bankmanagement.model.User user = (com.example.bankmanagement.model.User) request.getAttribute("user");
    String message = (String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bank Dashboard</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
<div class="container dashboard-shell">
    <div class="dashboard-grid">
        <aside class="sidebar">
            <div class="logo"><div class="logo-mark">B</div><div>BluePeak Bank</div></div>
            <div style="margin-top:24px;"><div class="small">Logged in as</div><h2 style="margin:8px 0 6px;"><%= user.getFullName() %></h2><div class="muted"><%= user.getEmail() %></div></div>
            <div class="account-list">
                <% for (Account account : accounts) { %>
                <a class="account-item <%= (selectedAccount != null && selectedAccount.getId().equals(account.getId())) ? "active" : "" %>" href="/dashboard?accountId=<%= account.getId() %>">
                    <strong><%= account.getAccountType() %> Account</strong><br>
                    <span class="account-number"><%= account.getAccountNumber() %></span><br><br>
                    <span>₹ <%= account.getBalance() %></span><br>
                    <span class="transaction-meta">Status: <%= account.getStatus() %></span>
                </a>
                <% } %>
            </div>
        </aside>
        <main class="main-panel">
            <div class="topbar"><div><div class="small">Dashboard overview</div><h1 style="margin-top:6px;">Bank Management System</h1></div><div class="header-actions"><a href="/logout">Logout</a></div></div>
            <% if (message != null && !message.isBlank()) { %><div class="alert success"><%= message %></div><% } %>
            <% if (selectedAccount != null) { %>
            <section class="kpi-grid">
                <div class="card"><div class="small">Current Balance</div><div class="kpi-value">₹ <%= selectedAccount.getBalance() %></div><div class="muted">Available funds in selected account.</div></div>
                <div class="card"><div class="small">Account Number</div><div class="kpi-value" style="font-size:1.3rem;"><%= selectedAccount.getAccountNumber() %></div><div class="muted">Status: <%= selectedAccount.getStatus() %></div></div>
                <div class="card"><div class="small">Transactions</div><div class="kpi-value"><%= transactions.size() %></div><div class="muted">Recorded entries for this account.</div></div>
            </section>
            <section class="action-grid">
                <div class="card">
                    <h3 style="margin-bottom:14px;">Deposit Money</h3>
                    <form class="inline-form" method="post" action="/deposit">
                        <input type="hidden" name="accountId" value="<%= selectedAccount.getId() %>">
                        <div><label>Amount</label><input type="number" step="0.01" min="1" name="amount" placeholder="Enter deposit amount" required></div>
                        <div><label>Note</label><input type="text" name="note" placeholder="Reference note"></div>
                        <button class="btn btn-primary" type="submit">Deposit</button>
                    </form>
                </div>
                <div class="card">
                    <h3 style="margin-bottom:14px;">Withdraw Money</h3>
                    <form class="inline-form" method="post" action="/withdraw">
                        <input type="hidden" name="accountId" value="<%= selectedAccount.getId() %>">
                        <div><label>Amount</label><input type="number" step="0.01" min="1" name="amount" placeholder="Enter withdrawal amount" required></div>
                        <div><label>Note</label><input type="text" name="note" placeholder="Reference note"></div>
                        <button class="btn btn-danger" type="submit">Withdraw</button>
                    </form>
                </div>
            </section>
            <section class="card">
                <div class="topbar" style="margin-bottom:10px;"><div><h3 style="margin:0;">Transaction Details</h3><div class="muted">Detailed transaction records for the selected account.</div></div></div>
                <div class="table-wrap">
                    <table>
                        <thead><tr><th>ID</th><th>Type</th><th>Amount</th><th>Reference</th><th>Date & Time</th></tr></thead>
                        <tbody>
                        <% for (Transaction tx : transactions) { %>
                        <tr>
                            <td><%= tx.getId() %></td>
                            <td><span class="badge <%= "DEPOSIT".equals(tx.getTransactionType()) ? "deposit" : "withdraw" %>"><%= tx.getTransactionType() %></span></td>
                            <td>₹ <%= tx.getAmount() %></td>
                            <td><%= tx.getReferenceNote() %></td>
                            <td><%= tx.getTransactionTime() %></td>
                        </tr>
                        <% } %>
                        </tbody>
                    </table>
                </div>
            </section>
            <% } else { %>
            <section class="card"><h3>No account found</h3><p class="muted">Register a new user or verify account records in PostgreSQL.</p></section>
            <% } %>
        </main>
    </div>
</div>
</body>
</html>
