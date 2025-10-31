<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<!-- ✅ Header -->
<nav class="navbar navbar-expand-lg navbar-light bg-white border-bottom shadow-sm px-3">
    <div class="container-fluid">
        <!-- Left side (brand or toggle button if needed) -->
        <button class="btn btn-outline-secondary d-lg-none" type="button" data-bs-toggle="offcanvas"
                data-bs-target="#sidebar" aria-controls="sidebar">
            <i class="bi bi-list"></i>
        </button>

        <!-- Right side -->
        <div class="dropdown ms-auto">
            <span class="me-3 fw-semibold">
                Hello, <c:out value="${sessionScope.user.fullName}" default="User"/>
            </span>

            <button class="btn btn-light border rounded-circle" type="button" id="userMenuButton"
                    data-bs-toggle="dropdown" aria-expanded="false">
                <i class="bi bi-person-circle fs-5"></i>
            </button>

            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userMenuButton">
                <li><a class="dropdown-item" href="ProfileController">Profile</a></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item text-danger" href="LogoutController">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>
