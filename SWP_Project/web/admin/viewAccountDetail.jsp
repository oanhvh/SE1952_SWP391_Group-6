<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Account Detail</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Account Detail</h2>
    <c:if test="${not empty user}">
        <table class="table table-bordered">
            <tr><th>Username</th><td>${user.username}</td></tr>
            <tr><th>Full Name</th><td>${user.fullName}</td></tr>
            <tr><th>Email</th><td>${user.email}</td></tr>
            <tr><th>Phone</th><td>${user.phone}</td></tr>
            <tr><th>Role</th><td>${user.role}</td></tr>
            <tr><th>Status</th><td>${user.status}</td></tr>
            <tr><th>Avatar</th>
                <td>
                    <c:if test="${not empty user.avatar}">
                        <img src="${user.avatar}" alt="Avatar" style="max-width:100px;"/>
                    </c:if>
                </td>
            </tr>
            <tr><th>Created At</th><td>${user.createdAt}</td></tr>
            <tr><th>Updated At</th><td>${user.updatedAt}</td></tr>
        </table>
    </c:if>
    <c:if test="${empty user}">
        <div class="alert alert-warning">User not found.</div>
    </c:if>
    <a href="ListAccount" class="btn btn-secondary">Back to List</a>
</div>
</body>
</html>