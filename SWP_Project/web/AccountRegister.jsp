<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Register Account</title>
        <!-- Bootstrap 5 CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            body {
                background-color: #f8f9fa;
            }
            .register-container {
                max-width: 600px;
                margin: 50px auto;
            }
            .card {
                border-radius: 12px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            }
            .hidden {
                display:none;
            }
        </style>
        <script>
            function onRoleChange(sel) {
                var v = sel.value;
                var row = document.getElementById('employeeCodeRow');
                if (row)
                    row.classList.toggle('hidden', v !== 'Staff');
            }
            document.addEventListener('DOMContentLoaded', function () {
                var sel = document.getElementById('role');
                if (sel)
                    onRoleChange(sel);
            });
        </script>
    </head>
    <body>
        <div class="container register-container">
            <div class="card">
                <div class="card-header text-center bg-primary text-white">
                    <h4>Create New Account</h4>
                </div>
                <div class="card-body">
                    <!-- Display success/error messages -->
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <c:if test="${not empty message}">
                        <div class="alert alert-info">${message}</div>
                    </c:if>

                    <form action="register" method="post">
                        <!-- Role -->
                        <div class="mb-3">
                            <label for="role" class="form-label">Role</label>
                            <select class="form-select" id="role" name="role" onchange="onRoleChange(this)" required>
                                <option value="">-- Select Role --</option>
                                <option value="Volunteer" ${param.role == 'Volunteer' ? 'selected' : ''}>Volunteer</option>
                                <option value="Staff" ${param.role == 'Staff' ? 'selected' : ''}>Staff</option>
                            </select>
                        </div>

                        <!-- Employee Code (for Staff) -->
                        <div class="mb-3 hidden" id="employeeCodeRow">
                            <label for="employeeCode" class="form-label">Employee Code (Staff)</label>
                            <input type="text" class="form-control" id="employeeCode" name="employeeCode" value="${param.employeeCode}">
                        </div>

                        <!-- Username -->
                        <div class="mb-3">
                            <label for="username" class="form-label">Username</label>
                            <input type="text" class="form-control" id="username" name="username"
                                   value="${param.username}" required>
                        </div>

                        <!-- Password -->
                        <div class="mb-3">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="password" name="password" required>
                        </div>

                        <!-- Full Name -->
                        <div class="mb-3">
                            <label for="fullName" class="form-label">Full Name</label>
                            <input type="text" class="form-control" id="fullName" name="fullName"
                                   value="${param.fullName}">
                        </div>

                        <!-- Email -->
                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="email" name="email"
                                   value="${param.email}">
                        </div>

                        <!-- Phone -->
                        <div class="mb-3">
                            <label for="phone" class="form-label">Phone</label>
                            <input type="text" class="form-control" id="phone" name="phone"
                                   value="${param.phone}">
                        </div>

                        <!-- Date of Birth -->
                        <div class="mb-3">
                            <label for="dateOfBirth" class="form-label">Date of Birth</label>
                            <input type="date" class="form-control" id="dateOfBirth" name="dateOfBirth"
                                   value="${param.dateOfBirth}">
                        </div>

                        <!-- Submit Button -->
                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">Register</button>
                        </div>
                    </form>
                </div>

                <div class="card-footer text-center">
                    <p class="mb-0">Already have an account?
                        <a href="login.jsp" class="text-decoration-none">Login here</a>.
                    </p>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
