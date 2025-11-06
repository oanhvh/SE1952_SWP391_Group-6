<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Volunteer Detail</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<%@ include file="AdminHeader.jsp" %>
<div class="d-flex">
    <%@ include file="sidebar.jsp" %>

    <div class="container mt-5">
        <div class="detail-container">
            <h4 class="mb-4 fw-bold">Volunteer Detail</h4>
            <hr>
            <c:if test="${not empty message}">
                <div class="alert alert-success">${message}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <form action="UpdateVolunteerStatus" method="post">
                <!-- Hidden volunteer ID -->
                <input type="hidden" name="volunteerID" value="${volunteer.volunteerID}">

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="fullName" class="form-label">Full name:</label>
                        <input type="text" id="fullName" class="form-control" readonly disabled
                               value="${empty volunteer.user.fullName ? 'N/A' : volunteer.user.fullName}">
                    </div>
                    <div class="col-md-6">
                    </div>
                </div>

                <div class="mb-3">
                    <label for="email" class="form-label">Email:</label>
                    <input type="text" id="email" class="form-control" readonly disabled
                           value="${empty volunteer.user.email ? 'N/A' : volunteer.user.email}">
                </div>

                <div class="mb-3">
                    <label for="phone" class="form-label">Phone:</label>
                    <input type="text" id="phone" class="form-control" readonly disabled
                           value="${empty volunteer.user.phone ? 'N/A' : volunteer.user.phone}">
                </div>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="status" class="form-label">Status:</label>
                        <select id="status" name="status" class="form-select">
                            <option value="Active" ${volunteer.status eq 'Active' ? 'selected' : ''}>Active</option>
                            <option value="InActive" ${volunteer.status eq 'InActive' ? 'selected' : ''}>InActive</option>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <label for="availability" class="form-label">Availability:</label>
                        <input type="text" id="availability" class="form-control" readonly disabled
                               value="${empty volunteer.availability ? 'N/A' : volunteer.availability}">
                    </div>
                </div>

                <div class="row mb-4">
                    <div class="col-md-6">
                        <label for="sponsor" class="form-label">Sponsor:</label>
                        <input type="text" id="sponsor" class="form-control" readonly disabled
                               value="${empty volunteer.sponsor ? 'N/A' : volunteer.sponsor}">
                    </div>
                    <div class="col-md-6">
                        <label for="joinDate" class="form-label">Join Date:</label>
                        <input type="text" id="joinDate" class="form-control" readonly disabled
                               value="${empty volunteer.joinDate ? 'N/A' : volunteer.joinDate}">
                    </div>
                </div>

                <div class="text-start">
                    <a href="ListVolunteer" class="btn btn-secondary">Back to List</a>
                    <button type="submit" class="btn btn-primary">Save Changes</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
