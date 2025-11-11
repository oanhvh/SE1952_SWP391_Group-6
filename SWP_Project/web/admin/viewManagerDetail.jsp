<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Manager Account Detail</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<%@ include file="AdminHeader.jsp" %>

<div class="d-flex">
    <%@ include file="sidebar.jsp" %>

    <div class="container mt-4" style="flex: 1;">
        <h2 class="mb-4 text-center">Manager Account Detail</h2>

        <!-- ✅ Display Messages (Optional) -->
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">${successMessage}</div>
        </c:if>

        <!-- ✅ User Information -->
        <div class="border p-4 rounded bg-light shadow-sm mb-4">
            <h5 class="mb-3">User Information</h5>

            <div class="mb-3">
                <label class="form-label fw-bold">Username:</label>
                <div class="form-control-plaintext">
                    ${manager.user.username != null ? manager.user.username : 'N/A'}
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">Full Name:</label>
                <div class="form-control-plaintext">
                    ${manager.user.fullName != null ? manager.user.fullName : 'N/A'}
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label fw-bold">Email:</label>
                    <div class="form-control-plaintext">
                        ${manager.user.email != null ? manager.user.email : 'N/A'}
                    </div>
                </div>
                <div class="col-md-6">
                    <label class="form-label fw-bold">Phone:</label>
                    <div class="form-control-plaintext">
                        ${manager.user.phone != null ? manager.user.phone : 'N/A'}
                    </div>
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">Date of Birth:</label>
                <div class="form-control-plaintext">
                    ${manager.user.dateOfBirth != null ? manager.user.dateOfBirth : 'N/A'}
                </div>
            </div>


        </div>

        <!-- ✅ Manager Information -->
        <div class="border p-4 rounded bg-light shadow-sm">
            <h5 class="mb-3">Manager Information</h5>

            <div class="mb-3">
                <label class="form-label fw-bold">Manager Name:</label>
                <div class="form-control-plaintext">
                    ${manager.managerName != null ? manager.managerName : 'N/A'}
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">Contact Info:</label>
                <div class="form-control-plaintext">
                    ${manager.contactInfo != null ? manager.contactInfo : 'N/A'}
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">Address:</label>
                <div class="form-control-plaintext">
                    ${manager.address != null ? manager.address : 'N/A'}
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">Registration Date:</label>
                <div class="form-control-plaintext">
                    ${manager.registrationDate != null ? manager.registrationDate : 'N/A'}
                </div>
            </div>
        </div>

        <!-- 🔙 Back Button -->
        <div class="text-center mt-4">
            <a href="ListManagerAccount" class="btn btn-secondary">
                <i class="bi bi-arrow-left"></i> Back to Manager List
            </a>
        </div>
    </div>
</div>
</body>
</html>
