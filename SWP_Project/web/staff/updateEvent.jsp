<%-- 
    Document   : updateEvent
    Created on : Nov 4, 2025, 5:15:43 AM
    Author     : DucNM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Update Event</title>
        <link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="../css/style.css" />
        <link rel="stylesheet" href="../css/responsive.css" />
        <link rel="icon" href="../images/fevicon.png" type="image/gif" />
        <link rel="stylesheet" href="../css/jquery.mCustomScrollbar.min.css" />
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" />
        <link rel="stylesheet" href="../css/owl.carousel.min.css" />
        <link rel="stylesheet" href="../css/owl.theme.default.min.css" />
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
            .current-image {
                margin-top: 10px;
                max-width: 200px;
                max-height: 200px;
            }
            .current-image img {
                width: 100%;
                height: auto;
                border: 1px solid #ddd;
                border-radius: 4px;
                padding: 5px;
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
                border: 1px solid #28a745;
                border-radius: 4px;
                padding: 5px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="includes/header.jsp" />
        <div class="form-container">
            <h3 class="text-center mb-4">Update Event</h3>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/staff/event" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="eventID" value="${event.eventID}">
                <input type="hidden" name="oldImage" value="${event.image}">

                <%--<c:if test="${not empty event.createdAt}">
                    <input type="hidden" name="createdAt" value="<fmt:formatDate value='${event.createdAt}' pattern='dd-MM-yyyy HH:mm' />">
                </c:if>--%>
                <c:if test="${not empty event.createdAt}">
                    <input type="hidden" name="createdAt" value="${formattedCreatedAt}">
                </c:if>

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
                    <label>Event Image</label>

                    <c:if test="${not empty event.image}">
                        <div class="current-image">
                            <p class="mb-1"><strong>Current Image:</strong></p>
                            <img src="${pageContext.request.contextPath}/${event.image}" alt="Current Event Image">
                        </div>
                    </c:if>

                    <input type="file" class="form-control mt-2" name="image" id="imageFile" accept="image/*" onchange="previewImage(this)">
                    <small class="form-text text-muted">Leave empty to keep current image. Supported formats: JPG, PNG, GIF (Max 10MB)</small>

                    <div class="image-preview" id="imagePreview">
                        <p class="mb-1"><strong>New Image Preview:</strong></p>
                        <img id="preview" src="" alt="New Image Preview">
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
                        <option value="Active" <c:if test="${event.status eq 'Active'}">selected</c:if>>Active</option>
                        <option value="Inactive" <c:if test="${event.status eq 'Inactive'}">selected</c:if>>Inactive</option>
                    </select>
                </div>
                
                <%--<div class="form-group mb-3">
                    <label>Status <span class="text-danger">*</span></label>
                    <select class="form-control" name="status" required>
                        <option value="Pending" ${event.status == 'Pending' ? 'selected' : ''}>Pending</option>
                        <option value="Active" ${event.status == 'Active' ? 'selected' : ''}>Active</option>
                        <option value="Completed" ${event.status == 'Completed' ? 'selected' : ''}>Completed</option>
                    </select>
                </div>--%>

                <div class="text-center">
                    <button type="submit" class="btn btn-primary px-4">Update Event</button>
                    <a href="${pageContext.request.contextPath}/staff/event?action=list" class="btn btn-secondary px-4 ms-2">Cancel</a>
                </div>
            </form>
        </div>

        <script>
            // Load existing dates into datetime-local inputs
            window.onload = function () {
                // Parse dates from server (assuming they're available)
                const startDateStr = '${event.startDate}'; // Format: yyyy-MM-ddTHH:mm:ss
                const endDateStr = '${event.endDate}';

                if (startDateStr && startDateStr !== '') {
                    // Convert from LocalDateTime format to datetime-local format
                    const startDate = new Date(startDateStr);
                    document.getElementById('startDate').value = formatDateTimeLocal(startDate);
                }

                if (endDateStr && endDateStr !== '') {
                    const endDate = new Date(endDateStr);
                    document.getElementById('endDate').value = formatDateTimeLocal(endDate);
                }
            };

            // Format Date to datetime-local input format (yyyy-MM-ddTHH:mm)
            function formatDateTimeLocal(date) {
                const pad = (n) => n < 10 ? '0' + n : n;
                return date.getFullYear() + '-' +
                        pad(date.getMonth() + 1) + '-' +
                        pad(date.getDate()) + 'T' +
                        pad(date.getHours()) + ':' +
                        pad(date.getMinutes());
            }

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
        <script src="../js/jquery.min.js"></script>
        <script src="../js/popper.min.js"></script>
        <script src="../js/bootstrap.bundle.min.js"></script>
        <script src="../js/jquery-3.0.0.min.js"></script>
        <script src="../js/plugin.js"></script>
        <script src="../js/role.js?v=2"></script>
        <script src="../js/owl.carousel.js"></script>
        <script src="https:cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.js"></script>
        <script src="../js/login.js"></script>
    </body>
</html>
