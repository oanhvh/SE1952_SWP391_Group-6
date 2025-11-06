<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Add New Manager Account</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<%@ include file="AdminHeader.jsp" %>
<div class="d-flex">
    <%@ include file="sidebar.jsp" %>

    <div class="container mt-4" style="flex: 1;">
        <h2 class="mb-4">Add New Manager Account</h2>

        <!-- Display success/error messages -->
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>

        <form action="AddAccountController" method="post" class="border p-4 rounded bg-light shadow-sm">
            <!-- User Info -->
            <h5 class="mb-3">User Information</h5>

            <div class="mb-3">
                <label for="username" class="form-label">Username:</label>
                <input type="text" id="username" name="username" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Password:</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="fullName" class="form-label">Full Name:</label>
                <input type="text" id="fullName" name="fullName" class="form-control" required>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label for="email" class="form-label">Email:</label>
                    <input type="email" id="email" name="email" class="form-control" required>
                </div>
                <div class="col-md-6">
                    <label for="phone" class="form-label">Phone:</label>
                    <input type="text" id="phone" name="phone" class="form-control" required>
                </div>
            </div>

            <div class="mb-3">
                <label for="dateOfBirth" class="form-label">Date of Birth:</label>
                <input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control">
            </div>

            <div class="mb-3">
                <label for="status" class="form-label">Status:</label>
                <select id="status" name="status" class="form-select" required>
                    <option value="Active">Active</option>
                    <option value="Inactive">Inactive</option>
                </select>
            </div>

            <div class="mb-3">
                <label for="role" class="form-label">Role:</label>
                <input type="text" id="roleDisplay" class="form-control" value="Manager" disabled>
                <input type="hidden" id="role" name="role" value="Manager">
            </div>

            <!-- Manager Info -->
            <h5 class="mb-3 mt-4">Manager Information</h5>

            <div class="mb-3">
                <label for="managerName" class="form-label">Manager Name:</label>
                <input type="text" id="managerName" name="managerName" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="contactInfo" class="form-label">Contact Info:</label>
                <input type="text" id="contactInfo" name="contactInfo" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="address" class="form-label">Address:</label>
                <input type="text" id="address" name="address" class="form-control" required>
            </div>

            <button type="submit" class="btn btn-primary">
                <i class="bi bi-person-plus"></i> Create Account
            </button>
        </form>
    </div>
</div>
</body>
</html>
