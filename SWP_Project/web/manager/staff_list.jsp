<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Staff Management</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="../css/style.css" />
        <link rel="stylesheet" href="../css/responsive.css" />
        <link rel="icon" href="../images/fevicon.png" type="image/gif" />
        <link rel="stylesheet" href="../css/jquery.mCustomScrollbar.min.css" />
        <link rel="stylesheet" href="../css/sidebar.css" />
        <style>
            .status-text {
                min-width: 70px;
                display: inline-block;
            }
            .status-active {
                color: #198754;
                font-weight: 600;
            }
            .status-inactive {
                color: #6c757d;
                font-weight: 600;
            }
            .form-switch .form-check-input {
                width: 3em;
                height: 1.5em;
            }
            #alertContainer {
                position: fixed;
                top: 90px;
                right: 16px;
                z-index: 1080;
                width: 360px;
                pointer-events: none;
            }
            #alertContainer .alert {
                pointer-events: auto;
            }
        </style>
    </head>
    <body>
        <jsp:include page="includes/header.jsp" />

        <div class="container py-4">
            <div class="d-flex align-items-center justify-content-between mb-3">
                <h3 class="mb-0">Staff Management</h3>
                <div>
                    <a href="${pageContext.request.contextPath}/manager/activation.jsp" class="btn btn-primary">Add New Staff</a>
                    <button id="btnReload" class="btn btn-outline-secondary ms-2">Reload</button>
                </div>
            </div>

            <!-- Alerts (fixed, no layout shift) -->
            <div id="alertContainer"></div>

            <div class="card">
                <div class="card-header">
                    <form action="${pageContext.request.contextPath}/manager/staffs" method="get" class="row g-3 align-items-center">
                        <div class="col-md-4">
                            <input type="text" class="form-control" name="name" value="${fn:escapeXml(param.name)}" placeholder="Search by name" />
                        </div>
                        <div class="col-md-3">
                            <input type="text" class="form-control" name="phone" value="${fn:escapeXml(param.phone)}" placeholder="Search by phone" />
                        </div>
                        <div class="col-md-3">
                            <select class="form-select" name="status" onchange="this.form.submit()">
                                <option value="">All Status</option>
                                <option value="Active" ${param.status == 'Active' ? 'selected' : ''}>Active</option>
                                <option value="Inactive" ${param.status == 'Inactive' ? 'selected' : ''}>Inactive</option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <button class="btn btn-primary w-100" type="submit">Search</button>
                        </div>
                    </form>
                </div>

                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-hover mb-0">
                            <thead class="table-light">
                                <tr>
                                    <th>Full Name</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                    <th class="text-center">Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty staffs}">
                                        <c:forEach var="u" items="${staffs}">
                                            <tr>
                                                <td>${fn:escapeXml(u.fullName)}</td>
                                                <td>${fn:escapeXml(u.email)}</td>
                                                <td>${fn:escapeXml(u.phone)}</td>
                                                <td class="text-center">
                                                    <select class="form-select d-inline-block w-auto status-select" data-user-id="${u.userID}">
                                                        <option value="Active" ${u.status == 'Active' ? 'selected' : ''}>Active</option>
                                                        <option value="Inactive" ${u.status == 'Inactive' ? 'selected' : ''}>Inactive</option>
                                                    </select>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="4" class="text-center text-muted py-5">No staff found.</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="includes/footer.jsp" />

        <script src="../js/jquery.min.js"></script>
        <script src="../js/popper.min.js"></script>
        <script src="../js/bootstrap.bundle.min.js"></script>
        <script src="../js/role.js?v=2"></script>
        <script>
                                $(document).ready(function () {
                                    $('#btnReload').click(() => {
                                        window.location = '${pageContext.request.contextPath}/manager/staffs';
                                    });

                                    $('.status-select').on('change', function () {
                                        const $select = $(this);
                                        const userId = $select.data('user-id');
                                        const newStatus = $select.val();
                                        const prevStatus = newStatus === 'Active' ? 'Inactive' : 'Active';

                                        if (!confirm('Are you sure you want to set this account to ' + newStatus + '?')) {
                                            $select.val(prevStatus);
                                            return;
                                        }

                                        $.ajax({
                                            url: '${pageContext.request.contextPath}/manager/staff/status',
                                            type: 'POST',
                                            data: {userId: userId, status: newStatus},
                                            success: function (resp) {
                                                if (resp && resp.success) {
                                                    showAlert('success', 'Status updated successfully.');
                                                } else {
                                                    $select.val(prevStatus);
                                                    showAlert('danger', (resp && resp.message) || 'Failed to update status.');
                                                }
                                            },
                                            error: function () {
                                                $select.val(prevStatus);
                                                showAlert('danger', 'Server error. Please try again.');
                                            }
                                        });
                                    });

                                    function showAlert(type, message) {
                                        const $alert = $(`<div class="alert alert-${type} alert-dismissible fade show mb-2" role="alert">${message}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>`);
                                        $('#alertContainer').append($alert);
                                        setTimeout(() => {
                                            $alert.alert('close');
                                        }, 3000);
                                    }
                                });
        </script>
    </body>
</html>