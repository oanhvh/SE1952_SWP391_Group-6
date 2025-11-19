<%-- 
    Document   : createEvent
    Created on : Nov 4, 2025, 4:28:11 AM
    Author     : DucNM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Create Event</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/responsive.css" />
        <link rel="icon" href="${pageContext.request.contextPath}/images/fevicon.png" type="image/gif" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.mCustomScrollbar.min.css" />
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.theme.default.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/createEvent.css" />
    </head>
    <body>
        <jsp:include page="includes/header.jsp" />

        <div class="form-container">
            <h3 class="text-center mb-4">Create New Event</h3>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/staff/event" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
                <input type="hidden" name="action" value="add">

                <div class="form-group mb-3">
                    <label>Event Name <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" name="eventName" value="${event.eventName}" required>
                </div>

                <div class="form-group mb-3">
                    <label>Description <span class="text-danger">*</span></label>
                    <textarea class="form-control" name="description" rows="3" required>${event.description}</textarea>
                </div>

                <div class="form-group mb-3">
                    <label>Location <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" name="location" value="${event.location}" required>
                </div>

                <div class="form-group mb-3">
                    <label>Start Date & Time <span class="text-danger">*</span></label>
                    <input type="datetime-local" class="form-control" name="startDate" id="startDate" required>
                </div>

                <div class="form-group mb-3">
                    <label>End Date & Time <span class="text-danger">*</span></label>
                    <input type="datetime-local" class="form-control" name="endDate" id="endDate" required>
                </div>

                <div class="form-group mb-3">
                    <label>Capacity <span class="text-danger">*</span></label>
                    <input type="number" class="form-control" name="capacity" value="${event.capacity}" min="1" required>
                </div>

                <div class="form-group mb-3">
                    <label>Event Image <span class="text-danger">*</span></label>
                    <input type="file" class="form-control" name="image" id="imageFile" accept="image/*" required onchange="previewImage(this)">
                    <small class="form-text text-muted">Supported formats: JPG, PNG, GIF (Max 10MB)</small>
                    <div class="image-preview" id="imagePreview">
                        <img id="preview" src="" alt="Image Preview">
                    </div>
                </div>

                <%--<div class="form-group mb-3">
                    <label>Category ID <span class="text-danger">*</span></label>
                    <input type="number" class="form-control" name="categoryID" value="${event.categoryID}" min="1" required>
                </div>--%>
                <div class="form-group mb-3">
                    <label>Category <span class="text-danger">*</span></label>
                    <select class="form-control" name="categoryID" required>
                        <option value="">-- Select Category --</option>
                        <c:forEach var="cat" items="${categoryList}">
                            <option value="${cat.categoryID}" 
                                    <c:if test="${event != null && event.categoryID == cat.categoryID}">selected</c:if>>
                                ${cat.categoryName}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group mb-3">
                    <label>Status <span class="text-danger">*</span></label>
                    <select class="form-control" name="status" required>
                        <option value="Pending" ${event.status == 'Pending' ? 'selected' : ''}>Pending</option>
                        <option value="Active" ${event.status == 'Active' ? 'selected' : ''}>Active</option>
                        <option value="Completed" ${event.status == 'Completed' ? 'selected' : ''}>Completed</option>
                    </select>
                </div>

                <div class="text-center">
                    <button type="submit" class="btn btn-primary px-4">Create Event</button>
                    <a href="${pageContext.request.contextPath}/staff/event?action=list" class="btn btn-secondary px-4 ms-2">Cancel</a>
                </div>
            </form>
        </div>


        <script>
            // Preview image before upload
            function previewImage(input) {
                const preview = document.getElementById('preview');
                const previewDiv = document.getElementById('imagePreview');

                if (input.files && input.files[0]) {
                    const file = input.files[0];

                    // Validate file size (10MB)
                    if (file.size > 10 * 1024 * 1024) {
                        alert('File size must be less than 10MB');
                        input.value = '';
                        previewDiv.style.display = 'none';
                        return;
                    }

                    // Validate file type
                    if (!file.type.match('image.*')) {
                        alert('Please select an image file');
                        input.value = '';
                        previewDiv.style.display = 'none';
                        return;
                    }

                    const reader = new FileReader();
                    reader.onload = function (e) {
                        preview.src = e.target.result;
                        previewDiv.style.display = 'block';
                    };
                    reader.readAsDataURL(file);
                } else {
                    previewDiv.style.display = 'none';
                }
            }

            // Format datetime-local -> dd-MM-yyyy HH:mm before submit
            function validateForm() {
                const startEl = document.getElementById("startDate");
                const endEl = document.getElementById("endDate");
                const imageFile = document.getElementById("imageFile");
                const startVal = startEl.value;
                const endVal = endEl.value;

                // Validate dates
                if (!startVal || !endVal) {
                    alert("Please fill out both start and end date.");
                    return false;
                }

                const start = new Date(startVal);
                const end = new Date(endVal);

                if (start >= end) {
                    alert("End date must be after start date.");
                    return false;
                }

                // Validate current date
                const now = new Date();
                if (start < now) {
                    if (!confirm("Start date is in the past. Do you want to continue?")) {
                        return false;
                    }
                }

                // Validate image file
                if (!imageFile.files || imageFile.files.length === 0) {
                    alert("Please select an image file.");
                    return false;
                }

                // Do not convert values; submit ISO from datetime-local for backend parsing
                return true;
            }
        </script>
        <jsp:include page="includes/footer.jsp" />

        <script src="../js/role.js?v=2"></script>
        <script src="../js/jquery.min.js"></script>
        <script src="../js/popper.min.js"></script>
        <script src="../js/bootstrap.bundle.min.js"></script>
        <script src="../js/jquery-3.0.0.min.js"></script>
        <script src="../js/plugin.js"></script>
        <script src="../js/jquery.mCustomScrollbar.concat.min.js"></script>
        <script src="../js/custom.js"></script>
        <script src="../js/owl.carousel.js"></script>
        <script src="https:cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.js"></script>
    </body>
</html>
