<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit Profile</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<%@ include file="AdminHeader.jsp" %>
<div class="d-flex">
    <%@ include file="sidebar.jsp" %>
    <div class="container mt-5">
        <h2>Edit Profile</h2>

        <!-- Alert messages -->
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <c:if test="${not empty user}">
            <form action="EditProfileController" method="post" enctype="multipart/form-data">
                <input type="hidden" name="userId" value="${user.userID}"/>

                <div class="mb-3">
                    <label class="form-label">Avatar</label><br>
                    <c:if test="${not empty user.avatar}">
                        <img src="${user.avatar}" alt="Avatar" style="max-width:100px; display:block; margin-bottom:10px;"/>
                    </c:if>
                    <c:if test="${empty user.avatar}">
                        <img src="images/default-avatar.png" alt="No Avatar" style="max-width:100px; display:block; margin-bottom:10px;"/>
                    </c:if>

                    <!-- Upload new image -->
                    <input type="file" name="avatarFile" accept="image/*" class="form-control"/>
                </div>

                <div class="mb-3">
                    <label class="form-label">Full Name</label>
                    <input type="text" name="fullName" class="form-control" value="${user.fullName}"/>
                </div>

                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" name="email" class="form-control" value="${user.email}"/>
                </div>

                <div class="mb-3">
                    <label class="form-label">Phone</label>
                    <input type="text" name="phone" class="form-control" value="${user.phone}"/>
                </div>

                <button type="submit" class="btn btn-primary">Save Changes</button>
                <a href="viewAccountDetail?userId=${user.userID}" class="btn btn-secondary">Cancel</a>
            </form>

        </c:if>

        <c:if test="${empty user}">
            <div class="alert alert-warning">User not found.</div>
        </c:if>
    </div>
</div>
</body>
</html>
