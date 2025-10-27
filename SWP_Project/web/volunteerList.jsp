<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Volunteer List</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body>
<div class="d-flex">
    <%@ include file="sidebar.jsp" %>
    <div class="container mt-4" style="flex: 1;">
        <div class="container">

            <h2 class="text-center mb-4">Volunteer List</h2>

            <!-- Search Section -->
            <form action="ListVolunteer" method="get" class="search-section row g-3">
                <div class="col-md-4">
                    <label for="searchName" class="form-label">Full Name</label>
                    <input type="text" id="searchName" name="name" class="form-control"
                           placeholder="Enter volunteer name" value="${param.name}">
                </div>
                <div class="col-md-4">
                    <label for="searchStatus" class="form-label">Status</label>
                    <input type="text" id="searchStatus" name="status" class="form-control"
                           placeholder="Enter status" value="${param.status}">
                </div>
                <div class="col-md-4">
                    <label for="searchAvailability" class="form-label">Availability</label>
                    <input type="text" id="searchAvailability" name="availability" class="form-control"
                           placeholder="Enter availability" value="${param.availability}">
                </div>
                <div class="col-12 text-end">
                    <button type="submit" class="btn btn-primary mt-3">Search</button>
                </div>
            </form>

            <!-- Table Section -->
            <table class="table table-hover table-bordered align-middle mt-4">
                <thead class="table-primary text-center">
                <tr>
                    <th>ID</th>
                    <th>Full Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Status</th>
                    <th>Availability</th>
                    <th>Is Sponsor</th>
                    <th>Join Date</th>
                    <th>Age</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody class="text-center">
                <c:choose>
                    <c:when test="${not empty volunteerList}">
                        <c:forEach var="v" items="${volunteerList}">
                            <tr>
                                <td>${v.volunteerID}</td>
                                <td>${v.user.fullName}</td>
                                <td>${v.user.email}</td>
                                <td>${v.user.phone}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${v.status eq 'Active'}">
                                            <span class="badge bg-success">ACTIVE</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-secondary">IN ACTIVE</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${v.availability}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${v.sponsor}">Yes</c:when>
                                        <c:otherwise>No</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${v.joinDate}</td>
                                <td>${v.age}</td>
                                <td>
                                    <form action="viewVolunteerDetail" method="post" class="m-0">
                                        <input type="hidden" name="action" value="viewDetail">
                                        <input type="hidden" name="volunteerID" value="${v.volunteerID}">
                                        <button type="submit" class="btn btn-sm btn-primary">View Detail</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="10" class="text-muted text-center">No volunteers found.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>
