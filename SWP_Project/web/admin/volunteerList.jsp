<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Volunteer List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<%@ include file="AdminHeader.jsp" %>
<div class="d-flex">
    <%@ include file="sidebar.jsp" %>

    <div class="container mt-4" style="flex: 1;">
        <div class="container">
            <h2 class="text-center mb-4">Volunteer List</h2>

            <!-- Search Section -->
            <form action="ListVolunteer" method="get" class="search-section row g-3">
                <div class="col-md-3">
                    <label for="searchName" class="form-label">Name</label>
                    <input type="text" id="searchName" name="name" class="form-control"
                           placeholder="Enter name" value="${param.name}">
                </div>
                <div class="col-md-3">
                    <label for="searchPhone" class="form-label">Phone</label>
                    <input type="text" id="searchPhone" name="phone" class="form-control"
                           placeholder="Enter phone" value="${param.phone}">
                </div>
                <div class="col-md-3">
                    <label for="searchEmail" class="form-label">Email</label>
                    <input type="text" id="searchEmail" name="email" class="form-control"
                           placeholder="Enter email" value="${param.email}">
                </div>
                <div class="col-md-3">
                    <label for="searchStatus" class="form-label">Status</label>
                    <select id="searchStatus" name="status" class="form-select">
                        <option value="">All</option>
                        <option value="Active" ${param.status == 'Active' ? 'selected' : ''}>Active</option>
                        <option value="InActive" ${param.status == 'InActive' ? 'selected' : ''}>In Active</option>
                    </select>
                </div>
                <div class="col-12 text-end">
                    <button type="submit" class="btn btn-primary mt-3">Search</button>
                </div>
            </form>

            <!-- Table Section -->
            <table class="table table-hover table-bordered align-middle mt-4">
                <thead class="table-primary text-center">
                <tr>
                    <th>Full Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody class="text-center">
                <c:choose>
                    <c:when test="${not empty volunteerList}">
                        <c:forEach var="v" items="${volunteerList}">
                            <tr>
                                <td>${v.user.fullName}</td>
                                <td>${v.user.email}</td>
                                <td>${v.user.phone}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${v.status eq 'Active'}">
                                            <span class="badge bg-success">Active</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-secondary">${v.status}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <form action="viewVolunteerDetail" method="post" class="m-0">
                                        <input type="hidden" name="volunteerID" value="${v.volunteerID}">
                                        <button type="submit" class="btn btn-sm btn-primary">View Detail</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr><td colspan="5" class="text-muted">No volunteers found.</td></tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
