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
        <link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="../css/style.css" />
        <link rel="stylesheet" href="../css/responsive.css" />
        <link rel="stylesheet" href="../css/sidebar.css" />
        <style>
            .form-container {
                max-width: 700px;
                margin: 50px auto;
                background: #fff;
                padding: 35px;
                border-radius: 12px;
                box-shadow: 0 0 15px rgba(0,0,0,0.1);
            }
            .error-msg {
                color: red;
                font-size: 0.9rem;
                margin-top: 5px;
            }
            .form-group label {
                font-weight: 600;
            }
            .image-preview {
                margin-top: 10px;
                max-width: 200px;
                max-height: 200px;
                display: none;
            }
            .image-preview img {
                width: 100%;
                height: auto;
                border: 1px solid #ddd;
                border-radius: 4px;
                padding: 5px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="includes/header.jsp" />
        <div class="form-container">
            <h3 class="text-center mb-4">Create New Event</h3>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/event" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
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
                    <a href="${pageContext.request.contextPath}/event?action=list" class="btn btn-secondary px-4 ms-2">Cancel</a>
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

                // Convert format to dd-MM-yyyy HH:mm
                const formatDate = (d) => {
                    const pad = (n) => n < 10 ? '0' + n : n;
                    return pad(d.getDate()) + '-' + pad(d.getMonth() + 1) + '-' + d.getFullYear() + ' ' + pad(d.getHours()) + ':' + pad(d.getMinutes());
                };

                startEl.value = formatDate(start);
                endEl.value = formatDate(end);
                return true;
            }
        </script>
    </body>
</html>
