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
        <link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="../css/style.css" />
        <link rel="stylesheet" href="../css/responsive.css" />
        <link rel="icon" href="../images/fevicon.png" type="image/gif" />
        <link rel="stylesheet" href="../css/jquery.mCustomScrollbar.min.css" />
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" />
        <link rel="stylesheet" href="../css/owl.carousel.min.css" />
        <link rel="stylesheet" href="../css/owl.theme.default.min.css" />
        <link rel="stylesheet" href="../css/sidebar.css" />
    </head>
    <body>
        <jsp:include page="includes/header.jsp" />
        <div class="container-fluid">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3>Applications list (<span>${filterStatus}</span>)</h3>
                <form class="form-inline" method="get" action="${pageContext.request.contextPath}/staff/applications">
                    <label class="mr-2">Status:</label>
                    <select name="status" class="form-control mr-2" onchange="this.form.submit()">
                        <option value="Pending" ${filterStatus == 'Pending' ? 'selected' : ''}>Pending</option>
                        <option value="Approved" ${filterStatus == 'Approved' ? 'selected' : ''}>Approved</option>
                        <option value="Rejected" ${filterStatus == 'Rejected' ? 'selected' : ''}>Rejected</option>
                    </select>
                    <noscript><button class="btn btn-primary" type="submit">Filter</button></noscript>
                </form>
            </div>

            <c:choose>
                <c:when test="${empty rows}">
                    <div class="alert alert-info">No registrations.</div>
                </c:when>
                <c:otherwise>
                    <div class="table-responsive">
                        <table class="table table-bordered table-sm" id="appsTable">
                            <thead class="thead-light">
                                <tr>
                                    <th>ID</th>
                                    <th>Volunteer</th>
                                    <th>Event</th>
                                    <th>Status</th>
                                    <th>Application Date</th>
                                    <th>Details</th>
                                    <th>Approve/Reject</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="r" items="${rows}">
                                    <tr>
                                        <td>${r.applicationID}</td>
                                        <td>${r.volunteerFullName}</td>
                                        <td>${r.eventName}</td>
                                        <td class="status" data-id="${r.applicationID}">${r.status}</td>
                                        <td>${r.applicationDate}</td>
                                        <td>
                                            <button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#appDetail_${r.applicationID}">View more</button>
                                            <div class="modal fade" id="appDetail_${r.applicationID}" tabindex="-1" role="dialog" aria-labelledby="detailLabel_${r.applicationID}" aria-hidden="true">
                                                <div class="modal-dialog modal-lg" role="document">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title" id="detailLabel_${r.applicationID}">Application #${r.applicationID} details</h5>
                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                <span aria-hidden="true">&times;</span>
                                                            </button>
                                                        </div>
                                                        <div class="modal-body">
                                                            <div class="mb-3">
                                                                <strong>Volunteer:</strong> ${r.volunteerFullName}
                                                            </div>
                                                            <div class="mb-3">
                                                                <strong>Event:</strong> ${r.eventName}
                                                            </div>
                                                            <div class="mb-3">
                                                                <strong>Motivation:</strong>
                                                                <pre style="white-space: pre-wrap;">${empty r.motivation ? 'N/A' : r.motivation}</pre>
                                                            </div>
                                                            <div class="mb-3">
                                                                <strong>Experience:</strong>
                                                                <pre style="white-space: pre-wrap;">${empty r.experience ? 'N/A' : r.experience}</pre>
                                                            </div>
                                                            <div class="mb-3">
                                                                <strong>Skills:</strong>
                                                                <pre style="white-space: pre-wrap;">${empty r.skills ? 'N/A' : r.skills}</pre>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="d-flex">
                                                <form method="post" action="${pageContext.request.contextPath}/staff/applications/review" class="mr-2">
                                                    <input type="hidden" name="applicationId" value="${r.applicationID}">
                                                    <input type="hidden" name="action" value="approve">
                                                    <button type="submit" class="btn btn-success btn-sm" onclick="return confirm('Confirm approve this application?');">Approve</button>
                                                    <input type="text" class="form-control form-control-sm mb-1" name="staffComment" placeholder="Note">
                                                </form>
                                                <form method="post" action="${pageContext.request.contextPath}/staff/applications/review">
                                                    <input type="hidden" name="applicationId" value="${r.applicationID}">
                                                    <input type="hidden" name="action" value="reject">                              
                                                   <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Confirm rejection of this application?');">Reject</button>
                                                    <input type="text" class="form-control form-control-sm mb-1" name="staffComment" placeholder="Reason for declining">
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
        <jsp:include page="includes/footer.jsp" />

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
