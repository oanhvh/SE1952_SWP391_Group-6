<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Account List</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

    <style>
        body {
            background-color: #f9f9f9;
        }
        .container {
            margin-top: 40px;
        }
        .search-section {
            background-color: #fff;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        table {
            background-color: #fff;
            border-radius: 10px;
            overflow: hidden;
        }
        .btn-view {
            background-color: #0d6efd;
            color: #fff;
        }
        .btn-view:hover {
            background-color: #0b5ed7;
        }
    </style>
</head>
<body>

<div class="container">
    <h2 class="text-center mb-4">User Account List</h2>

    <!-- Search Section -->
    <form action="ListAccount" method="get" class="search-section row g-3">
        <div class="col-md-4">
            <label for="searchName" class="form-label">Name</label>
            <input type="text" id="searchName" name="name" class="form-control" placeholder="Enter user name" value="${param.name}">
        </div>
        <div class="col-md-4">
            <label for="searchRole" class="form-label">Role</label>
            <input type="text" id="searchRole" name="role" class="form-control" placeholder="Enter role" value="${param.role}">
        </div>
        <div class="col-md-4">
            <label for="searchPhone" class="form-label">Phone</label>
            <input type="text" id="searchPhone" name="phone" class="form-control" placeholder="Enter phone number" value="${param.phone}">
        </div>
        <div class="col-12 text-end">
            <button type="submit" class="btn btn-primary mt-3">Search</button>
        </div>
    </form>

    <!-- Table Section -->
    <table class="table table-hover table-bordered align-middle">
        <thead class="table-primary text-center">
        <tr>
            <th>Email</th>
            <th>Phone</th>
            <th>Role</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody class="text-center">
        <c:choose>
            <c:when test="${not empty userList}">
                <c:forEach var="u" items="${userList}">
                    <tr>
                        <td>${u.email}</td>
                        <td>${u.phone}</td>
                        <td>${u.role}</td>
                        <td>
                        <td>
                            <c:choose>
                                <c:when test="${u.status eq 'active'}">
                                    <span class="badge bg-success">Active</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-secondary">${u.status}</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <form action="ViewAccountDetailController" method="get" class="m-0">
                                <input type="hidden" name="userId" value="${u.userID}">
                                <button type="submit" class="btn btn-view btn-sm">View Detail</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="5" class="text-muted text-center">No users found.</td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>

</body>
</html>
