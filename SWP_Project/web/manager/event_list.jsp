<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Event Management</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/responsive.css" />
        <link rel="icon" href="${pageContext.request.contextPath}/images/fevicon.png" type="image/gif" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css" />
        <style>
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

        <div class="container-fluid py-4 px-4">
            <div class="d-flex align-items-center justify-content-between mb-2">
                <h3 class="mb-0">Event Management</h3>
                <button type="button" id="btnReloadEvents" class="btn btn-outline-secondary">Reload</button>
            </div>

            <div id="alertContainer"></div>

            <div class="card">
                <div class="card-header">
                    <form action="${pageContext.request.contextPath}/manager/events" method="get" class="row g-3 align-items-center">
                        <div class="col-md-4">
                            <input type="text" class="form-control" name="name" value="${fn:escapeXml(filterName)}" placeholder="Search by event name" />
                        </div>
                        <div class="col-md-2">
                            <input type="date" class="form-control" name="startFrom" value="${filterStartFrom}" placeholder="Start from" />
                        </div>
                        <div class="col-md-2">
                            <input type="date" class="form-control" name="startTo" value="${filterStartTo}" placeholder="Start to" />
                        </div>
                        <div class="col-md-2">
                            <select class="form-select" name="status" onchange="this.form.submit()">
                                <option value="">All Status</option>
                                <option value="Pending" ${filterStatus == 'Pending' ? 'selected' : ''}>Pending</option>
                                <option value="Active" ${filterStatus == 'Active' ? 'selected' : ''}>Active</option>
                                <option value="Inactive" ${filterStatus == 'Inactive' ? 'selected' : ''}>Inactive</option>
                                <option value="Cancelled" ${filterStatus == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
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
                                    <th>Event Name</th>
                                    <th>Category</th>
                                    <th>Location</th>
                                    <th>Start</th>
                                    <th>End</th>
                                    <th>Status</th>
                                    <th>Created By</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty events}">
                                        <c:forEach var="e" items="${events}">
                                            <tr>
                                                <td>${fn:escapeXml(e.eventName)}</td>
                                                <td>${fn:escapeXml(e.categoryName)}</td>
                                                <td>${fn:escapeXml(e.location)}</td>
                                                <td><c:out value="${e.startDate}" /></td>
                                                <td><c:out value="${e.endDate}" /></td>
                                                <td>${fn:escapeXml(e.status)}</td>
                                                <td>${fn:escapeXml(e.createdByName)}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="7" class="text-center text-muted py-5">No events found.</td>
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
                                    $('#btnReloadEvents').click(function () {
                                        window.location = '${pageContext.request.contextPath}/manager/events';
                                    });
                                });
        </script>
    </body>
</html>
