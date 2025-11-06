<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.VolunteerApplications" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Applications Review</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body class="p-3">
<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>Danh sách đơn ứng tuyển (<span>${filterStatus}</span>)</h3>
        <form class="form-inline" method="get" action="${pageContext.request.contextPath}/staff/applications">
            <label class="mr-2">Trạng thái:</label>
            <select name="status" class="form-control mr-2" onchange="this.form.submit()">
                <option value="Pending" ${filterStatus == 'Pending' ? 'selected' : ''}>Pending</option>
                <option value="Approved" ${filterStatus == 'Approved' ? 'selected' : ''}>Approved</option>
                <option value="Rejected" ${filterStatus == 'Rejected' ? 'selected' : ''}>Rejected</option>
            </select>
            <noscript><button class="btn btn-primary" type="submit">Lọc</button></noscript>
        </form>
    </div>

    <c:choose>
        <c:when test="${empty applications}">
            <div class="alert alert-info">Không có đơn nào.</div>
        </c:when>
        <c:otherwise>
            <div class="table-responsive">
                <table class="table table-bordered table-sm" id="appsTable">
                    <thead class="thead-light">
                    <tr>
                        <th>ID</th>
                        <th>VolunteerID</th>
                        <th>EventID</th>
                        <th>Status</th>
                        <th>Application Date</th>
                        <th>Approve/Reject</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="a" items="${applications}">
                        <tr>
                            <td>${a.applicationID}</td>
                            <td>${a.volunteerID}</td>
                            <td>${a.eventID}</td>
                            <td class="status" data-id="${a.applicationID}">${a.status}</td>
                            <td>${a.applicationDate}</td>
                            <td>
                                <div class="d-flex">
                                    <form method="post" action="${pageContext.request.contextPath}/staff/applications/review" class="mr-2">
                                        <input type="hidden" name="applicationId" value="${a.applicationID}">
                                        <input type="hidden" name="action" value="approve">
                                        <input type="text" class="form-control form-control-sm mb-1" name="staffComment" placeholder="Ghi chú (tuỳ chọn)">
                                        <button type="submit" class="btn btn-success btn-sm" onclick="return confirm('Xác nhận duyệt đơn này?');">Approve</button>
                                    </form>
                                    <form method="post" action="${pageContext.request.contextPath}/staff/applications/review">
                                        <input type="hidden" name="applicationId" value="${a.applicationID}">
                                        <input type="hidden" name="action" value="reject">
                                        <input type="text" class="form-control form-control-sm mb-1" name="staffComment" placeholder="Lý do từ chối (khuyên nhập)">
                                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Xác nhận từ chối đơn này?');">Reject</button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
