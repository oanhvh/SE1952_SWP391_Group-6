<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Manager Account List</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<%@ include file="AdminHeader.jsp" %>

<div class="d-flex">
    <%@ include file="sidebar.jsp" %>

    <div class="container mt-4" style="flex: 1;">
        <h2 class="text-center mb-4">Manager Account List</h2>

        <!-- 🔍 Search Form -->
        <form action="ListManagerAccount" method="get" class="row g-3 mb-4">
            <div class="col-md-4">
                <input type="text" name="managerName" value="${param.managerName}" class="form-control"
                       placeholder="Search by Manager Name">
            </div>
            <div class="col-md-4">
                <input type="text" name="phone" value="${param.phone}" class="form-control"
                       placeholder="Search by Phone">
            </div>
            <div class="col-md-3">
                <select name="status" class="form-select">
                    <option value="">All Status</option>
                    <option value="active" ${param.status == 'active' ? 'selected' : ''}>Active</option>
                    <option value="inactive" ${param.status == 'inactive' ? 'selected' : ''}>Inactive</option>
                </select>
            </div>
            <div class="col-md-1">
                <button type="submit" class="btn btn-primary w-100">
                    <i class="bi bi-search"></i>
                </button>
            </div>
        </form>

        <!-- 🧾 Table -->
        <table class="table table-hover table-bordered align-middle">
            <thead class="table-primary text-center">
            <tr>
                <th>Username</th>
                <th>Manager Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody class="text-center">
            <c:choose>
                <c:when test="${not empty managerList}">
                    <c:forEach var="m" items="${managerList}">
                        <tr>
                            <td>${m.user.username}</td>
                            <td>${m.managerName}</td>
                            <td>${m.user.email}</td>
                            <td>${m.user.phone}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${m.user.status eq 'Active'}">
                                        <span class="badge bg-success">Active</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-secondary">${m.user.status}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <form action="ViewManagerDetail" method="post" class="m-0">
                                    <input type="hidden" name="userId" value="${m.user.userID}">
                                    <button type="submit" class="btn btn-sm btn-primary">
                                        <i class="bi bi-eye"></i> View Detail
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="6" class="text-muted text-center">No managers found.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
