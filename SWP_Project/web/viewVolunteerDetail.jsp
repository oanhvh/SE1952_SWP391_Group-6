<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Volunteer Detail</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body>
<div class="d-flex">
    <%@ include file="sidebar.jsp" %>

    <div class="container mt-4" style="flex: 1;">
        <div class="container mt-5">
            <h2>Volunteer Detail</h2>

            <c:if test="${not empty volunteer}">
                <div class="row">
                    <div class="col-md-9">
                        <table class="table table-bordered">
                            <tr><th>ID</th><td>${volunteer.volunteerID}</td></tr>
                            <tr><th>Full Name</th><td>${volunteer.user.fullName}</td></tr>
                            <tr><th>Email</th><td>${volunteer.user.email}</td></tr>
                            <tr><th>Phone</th><td>${volunteer.user.phone}</td></tr>
                            <tr><th>Status</th><td>${volunteer.status}</td></tr>
                            <tr><th>Availability</th><td>${volunteer.availability}</td></tr>
                            <tr><th>Is Sponsor</th>
                                <td>
                                    <c:choose>
                                        <c:when test="${volunteer.sponsor}">Yes</c:when>
                                        <c:otherwise>No</c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr><th>Age</th><td>${volunteer.age}</td></tr>
                            <tr><th>Join Date</th><td>${volunteer.joinDate}</td></tr>
                        </table>
                        <a href="ListVolunteer" class="btn btn-secondary">Back to List</a>
                    </div>

                    <div class="col-md-3 text-center">
                        <c:choose>
                            <c:when test="${not empty volunteer.user.avatar}">
                                <img src="${volunteer.user.avatar}" alt="Avatar"
                                     class="img-thumbnail" style="max-width:150px;">
                            </c:when>
                            <c:otherwise>
                                <img src="images/default-avatar.png" alt="No Avatar"
                                     class="img-thumbnail" style="max-width:150px;">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:if>

            <c:if test="${empty volunteer}">
                <div class="alert alert-warning">Volunteer not found.</div>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
