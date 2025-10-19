<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<div class="d-flex flex-column flex-shrink-0 p-3 bg-light" style="width: 250px; height: 100vh; border-right: 1px solid #ddd;">
    <a href="#" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-dark text-decoration-none">
        <span class="fs-4 fw-bold">Admin Panel</span>
    </a>
    <hr>

    <ul class="nav nav-pills flex-column mb-auto">
        <!-- Account Manager Dropdown -->
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle text-dark" href="#" id="accountDropdown" role="button"
               data-bs-toggle="dropdown" aria-expanded="false">
                <i class="bi bi-people"></i> Account Manager
            </a>
            <ul class="dropdown-menu" aria-labelledby="accountDropdown">
                <li><a class="dropdown-item" href="ListAccount">View Account List</a></li>
                <li><a class="dropdown-item" href="AddAccountController">Add New Account</a></li>
            </ul>
        </li>

        <hr>

        <!-- Example of another menu section -->
        <li>
            <a href="dashboard.jsp" class="nav-link link-dark">
                <i class="bi bi-house"></i> Dashboard
            </a>
        </li>
    </ul>
</div>
