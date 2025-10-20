<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Account Detail</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body>
<div class="d-flex">
    <%@ include file="sidebar.jsp" %>

    <div class="container mt-4" style="flex: 1;">
        <div class="container mt-5">
            <h2>Account Detail</h2>
            <c:if test="${not empty user}">
                <div class="row">
                    <div class="col-md-9">
                        <table class="table table-bordered">
                            <tr><th>Username</th><td>${user.username}</td></tr>
                            <tr><th>Full Name</th><td>${user.fullName}</td></tr>
                            <tr><th>Email</th><td>${user.email}</td></tr>
                            <tr><th>Phone</th><td>${user.phone}</td></tr>
                            <tr><th>Role</th><td>${user.role}</td></tr>
                            <tr><th>Status</th><td>${user.status}</td></tr>
                            <tr><th>Created At</th><td>${user.createdAt}</td></tr>
                            <tr><th>Updated At</th><td>${user.updatedAt}</td></tr>
                        </table>
                        <a href="ListAccount" class="btn btn-secondary">Back to List</a>
                        <a href="EditProfileController?username=${user.username}" class="btn btn-primary">Edit Profile</a>
                    </div>

                    <div class="col-md-3 text-center">
                        <c:choose>
                            <c:when test="${not empty user.avatar}">
                                <img src="${user.avatar}" alt="Avatar" class="img-thumbnail" style="max-width:150px;">
                            </c:when>
                            <c:otherwise>
                                <img src="images/default-avatar.png" alt="No Avatar" class="img-thumbnail" style="max-width:150px;">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:if>

            <c:if test="${empty user}">
                <div class="alert alert-warning">User not found.</div>
            </c:if>
        </div>
    </div>
</div>


</body>
</html>
