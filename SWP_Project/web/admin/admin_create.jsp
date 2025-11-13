<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map,java.util.HashMap" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    
    
    Map<String,String> errors = (Map<String,String>) request.getAttribute("errors");
    if (errors == null) errors = new java.util.HashMap<>();
    Map<String,String> form = (Map<String,String>) request.getAttribute("form");
    if (form == null) form = new java.util.HashMap<>();

    String success = request.getParameter("success");
    String errorMsg = request.getParameter("error");
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Create Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/header_admin_create.css" />
    </head>
    <body class="login-page">
        <div class="topbar d-flex align-items-center">
            <div class="container d-flex justify-content-between align-items-center">
                <a class="brand text-decoration-none" href="<%=request.getContextPath()%>/admin/listAccount.jsp">
                    <img src="<%=request.getContextPath()%>/images/logo.png" alt="logo"/>
                    <span class="text-white">Create Admin</span>
                </a>
            </div>
        </div>

        <div class="login-wrap">
            <div class="login-card container-xxl">
                <div class="row g-4 justify-content-center">
                    <div class="col-12 col-lg-6 col-xl-5 d-flex justify-content-center">
                        <div class="card card-login w-100">
                            <div class="card-header"><strong>Create Admin</strong></div>
                            <div class="card-body">
                                <c:if test="${not empty error}"><div class="alert alert-danger mb-3">${error}</div></c:if>
                                <c:if test="${not empty message}"><div class="alert alert-info mb-3">${message}</div></c:if>
                                <c:if test="${param.success == '1'}"><div class="alert alert-success mb-3">Admin account created successfully.</div></c:if>
                                <c:if test="${not empty param.error}"><div class="alert alert-danger mb-3">${fn:escapeXml(param.error)}</div></c:if>

                                    <form action="<%=request.getContextPath()%>/admin/admin_create" method="post">
                                    <input type="hidden" name="role" value="Admin" />

                                    <div class="mb-3">
                                        <label for="username" class="form-label">Username</label>
                                        <input type="text" class="form-control <%= errors.containsKey("username") ? "is-invalid" : "" %>" id="username" name="username" value="<%= form.getOrDefault("username", "") %>" required>
                                        <div class="invalid-feedback"><%= errors.getOrDefault("username", "") %></div>
                                    </div>
                                    <div class="mb-3 password-wrapper">
                                        <label for="password" class="form-label">Password</label>
                                        <div class="position-relative">
                                            <input type="password" class="form-control <%= errors.containsKey("password") ? "is-invalid" : "" %>" id="password" name="password" required>
                                            <i class="fa fa-eye-slash toggle-password" id="togglePassword" aria-label="Show password"></i>
                                        </div>
                                        <div class="invalid-feedback d-block"><%= errors.getOrDefault("password", "") %></div>
                                        <div class="mt-2">
                                            <div class="progress" style="height:6px;"><div id="pwdStrengthBar" class="progress-bar bg-danger" style="width:0%"></div></div>
                                            <small id="pwdStrengthText" class="text-muted">Weak</small>
                                        </div>
                                    </div>

                                    <div class="mb-3">
                                        <label for="fullName" class="form-label">Full Name</label>
                                        <input type="text" class="form-control <%= errors.containsKey("fullName") ? "is-invalid" : "" %>" id="fullName" name="fullName" value="<%= form.getOrDefault("fullName", "") %>">
                                        <div class="invalid-feedback"><%= errors.getOrDefault("fullName", "") %></div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="email" class="form-label">Email</label>
                                        <input type="email" class="form-control <%= errors.containsKey("email") ? "is-invalid" : "" %>" id="email" name="email" value="<%= form.getOrDefault("email", "") %>">
                                        <div class="invalid-feedback"><%= errors.getOrDefault("email", "") %></div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="phone" class="form-label">Phone</label>
                                        <input type="text" class="form-control <%= errors.containsKey("phone") ? "is-invalid" : "" %>" id="phone" name="phone" value="<%= form.getOrDefault("phone", "") %>">
                                        <div class="invalid-feedback"><%= errors.getOrDefault("phone", "") %></div>
                                    </div>
                                    <div class="d-grid"><button type="submit" class="btn btn-primary">Create Admin</button></div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            (function () {
                var pwd = document.getElementById('password');
                var toggle = document.getElementById('togglePassword');
                var bar = document.getElementById('pwdStrengthBar');
                var text = document.getElementById('pwdStrengthText');
                function score(p) {
                    var s = 0;
                    if (!p)
                        return 0;
                    if (p.length >= 8)
                        s++;
                    if (/[A-Z]/.test(p))
                        s++;
                    if (/[a-z]/.test(p))
                        s++;
                    if (/[0-9]/.test(p))
                        s++;
                    if (/[^A-Za-z0-9]/.test(p))
                        s++;
                    return Math.min(s, 5);
                }
                function update(p) {
                    var s = score(p), pct = [0, 20, 40, 60, 80, 100][s], cls = 'bg-danger', label = 'Weak';
                    if (s >= 3) {
                        cls = 'bg-warning';
                        label = 'Medium'
                    }
                    ;
                    if (s >= 5) {
                        cls = 'bg-success';
                        label = 'Strong'
                    }
                    ;
                    if (bar) {
                        bar.style.width = pct + '%';
                        bar.className = 'progress-bar ' + cls;
                    }
                    if (text) {
                        text.textContent = label;
                    }
                }
                if (pwd) {
                    update(pwd.value || '');
                    pwd.addEventListener('input', function () {
                        update(pwd.value)
                    });
                }
                if (toggle && pwd) {
                    toggle.addEventListener('click', function () {
                        var show = pwd.type === 'password';
                        pwd.type = show ? 'text' : 'password';
                        toggle.classList.toggle('fa-eye', show);
                        toggle.classList.toggle('fa-eye-slash', !show);
                    });
                }
            })();
        </script>
    </body>
</html>
