<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>My Profile</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container mt-5">
    <div class="detail-container">
        <h4 class="mb-4 fw-bold text-center">My Profile</h4>

        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <!-- html -->
        <!-- File: `web/admin/admin_profile.jsp` (updated form inputs) -->
        <form action="AdminProfileController" method="post" enctype="multipart/form-data">
            <input type="hidden" name="username" value="${user.username}"/>
            <div class="mb-3">
                <label class="form-label">Avatar</label><br>
                <c:if test="${not empty user.avatar}">
                    <img src="${user.avatar}" alt="Avatar" style="max-width:200px; display:block; margin-bottom:10px;"/>
                </c:if>
                <c:if test="${empty user.avatar}">
                    <img src="images/default-avatar.png" alt="No Avatar"
                         style="max-width:200px; display:block; margin-bottom:10px;"/>
                </c:if>
                <div class="mb-3">
                    <label for="avatar" class="form-label">Upload new avatar:</label>
                    <input type="file" id="avatar" name="avatar" class="form-control" accept="image/*">
                </div>
            </div>
            <hr>
            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="username" class="form-label">Username:</label>
                    <input type="text" id="username" name="username" class="form-control" readonly disabled
                           value="${empty user.username ? 'N/A' : user.username}">
                </div>
                <div class="col-md-6">
                    <label for="email" class="form-label">Email:</label>
                    <input type="text" id="email" name="email" class="form-control" readonly disabled
                           value="${empty user.email ? 'N/A' : user.email}">
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="fullName" class="form-label">Full name:</label>
                    <input type="text" id="fullName" name="fullName" class="form-control"
                           value="${empty user.fullName ? 'N/A' : user.fullName}">
                </div>
                <div class="col-md-6">
                    <label for="dateOfBirth" class="form-label">Date of birth:</label>
                    <input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control"
                           value="${empty user.dateOfBirth ? '' : user.dateOfBirth}">
                </div>
            </div>

            <div class="mb-3">
                <label for="phone" class="form-label">Phone:</label>
                <input type="tel" id="phone" name="phone" class="form-control"
                       value="${empty user.phone ? 'N/A' : user.phone}">
            </div>

            <div class="mb-3">
                <label for="facebookID" class="form-label">facebook link:</label>
                <input type="url" id="facebookID" name="facebookID" class="form-control"
                       value="${empty user.facebookID ? 'N/A' : user.facebookID}">
            </div>

            <div class="mb-3">
                <label for="googleID" class="form-label">google ID:</label>
                <input type="text" id="googleID" name="googleID" class="form-control"
                       value="${empty user.googleID ? 'N/A' : user.googleID}">
            </div>

            <div class="text-start">
                <a href="ListAccount" class="btn btn-secondary">Back to List</a>
                <button type="submit" class="btn btn-primary">Save Changes</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
