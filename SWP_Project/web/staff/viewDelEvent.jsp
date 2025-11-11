<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="entity.Event"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>${event.eventName} - Event Details</title>
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
        <style>
            body {
                background: #f5f6fa;
                font-family: 'Segoe UI', sans-serif;
            }
            .event-container {
                max-width: 800px;
                margin: 50px auto;
                background: #fff;
                border-radius: 12px;
                box-shadow: 0 0 15px rgba(0,0,0,0.1);
                overflow: hidden;
            }
            .event-header img {
                width: 100%;
                max-height: 350px;
                object-fit: cover;
            }
            .event-body {
                padding: 30px;
            }
            .event-body h2 {
                font-weight: 700;
                margin-bottom: 20px;
            }
            .event-info {
                margin-bottom: 10px;
                font-size: 1rem;
            }
            .event-info span {
                font-weight: 600;
                color: #555;
                display: inline-block;
                width: 140px;
            }
            .status-badge {
                padding: 6px 14px;
                border-radius: 20px;
                font-weight: 600;
                font-size: 0.9rem;
            }
            .status-Pending {
                background: #ffc107;
                color: #000;
            }
            .status-Active {
                background: #28a745;
                color: #fff;
            }
            .status-Completed {
                background: #6c757d;
                color: #fff;
            }
            .no-image {
                background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
                height: 300px;
                display: flex;
                align-items: center;
                justify-content: center;
                color: #fff;
                font-size: 2rem;
            }
            .action-buttons {
                display: flex;
                justify-content: center;
                flex-wrap: wrap;
                gap: 10px;
                margin-top: 25px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="includes/header.jsp" />

        <div class="event-container">
            <div class="event-header">
                <c:choose>
                    <c:when test="${not empty event.image}">
                        <img src="${pageContext.request.contextPath}/${event.image}" 
                             alt="${event.eventName}" 
                             onerror="this.src='${pageContext.request.contextPath}/images/default-event.jpg'">
                    </c:when>
                    <c:otherwise>
                        <div class="no-image"><i class="fa fa-calendar"></i></div>
                        </c:otherwise>
                    </c:choose>
            </div>

            <div class="event-body">
                <h2>${event.eventName}</h2>
                <p style="text-align: justify;">${event.description}</p>
                <hr>

                <div class="event-info"><span><i class="fa fa-map-marker"></i> Location:</span>${event.location}</div>
                <div class="event-info"><span><i class="fa fa-calendar"></i> Start:</span>${event.startDate}</div>
                <div class="event-info"><span><i class="fa fa-calendar-check-o"></i> End:</span>${event.endDate}</div>
                <div class="event-info"><span><i class="fa fa-users"></i> Capacity:</span>${event.capacity} participants</div>
                <div class="event-info"><span><i class="fa fa-tag"></i> Category:</span>${categoryName}</div>
                <div class="event-info">
                    <span><i class="fa fa-flag"></i> Status:</span>
                    <span class="status-badge status-${event.status}">${event.status}</span>
                </div>

                <c:if test="${not empty sessionScope.role && (sessionScope.role == 'Staff' || sessionScope.role == 'Manager')}">
                    <hr>
                    <div class="event-info"><span>Created By:</span>${staffName}</div>
                    <c:if test="${not empty event.managerID}">
                        <div class="event-info"><span>Manager ID:</span>${managerName}</div>
                    </c:if>
                    <div class="event-info"><span>Created At:</span>${event.createdAt}</div>
                </c:if>
                

                <div class="action-buttons">
                    <a href="${pageContext.request.contextPath}/staff/delEvent?action=list" class="btn btn-secondary">
                        <i class="fa fa-arrow-left"></i> Back
                    </a>
                    <c:if test="${sessionScope.role == 'Staff' || sessionScope.role == 'Manager'}">
                        <%--<a href="${pageContext.request.contextPath}/staff/event?action=edit&id=${event.eventID}" class="btn btn-warning">
                            <i class="fa fa-edit"></i> Edit
                        </a>
                        <a href="${pageContext.request.contextPath}/staff/event?action=delete&id=${event.eventID}" 
                           class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this event?');">
                            <i class="fa fa-trash"></i> Delete
                        </a>--%>
                        <a href="${pageContext.request.contextPath}/staff/delEvent?action=restore&id=${event.eventID}" 
                           class="btn btn-danger" onclick="return confirm('Are you sure you want to restore this event?');">
                            <i class="fa fa-undo"></i> Restore
                        </a>
                    </c:if>
                </div>
            </div>
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
