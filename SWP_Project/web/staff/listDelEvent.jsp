<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="entity.Event"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Inactive Events</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/responsive.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css" />
    <style>
        .event-image { width: 100%; height: 250px; object-fit: cover; }
        .card { transition: transform 0.3s ease; height: 100%; }
        .card:hover { transform: translateY(-5px); box-shadow: 0 8px 16px rgba(0,0,0,0.2) !important; }
        .status-Inactive { background-color: #dc3545; color: #fff; border-radius: 20px; padding: 5px 12px; font-weight: 600; font-size: 0.85rem; }
        .no-image { width: 100%; height: 250px; background: linear-gradient(135deg, #ff416c, #ff4b2b); display: flex; align-items: center; justify-content: center; color: white; font-size: 1.5rem; }
        .btn-actions { display: flex; gap: 10px; flex-wrap: wrap; }
    </style>
</head>
<body>
    <jsp:include page="includes/header.jsp" />

    <div class="news_section layout_padding">
        <div class="container">
            <div class="row mb-4">
                <div class="col-sm-12">
                    <h1 class="news_taital">INACTIVE EVENTS</h1>
                    <p class="news_text">List of events that have been removed. You can review or restore them below.</p>
                </div>
            </div>

            <div class="row">
                <c:forEach var="event" items="${eventList}">
                    <div class="col-md-6 col-lg-4 mb-4">
                        <div class="card shadow-sm border-0">
                            <c:choose>
                                <c:when test="${not empty event.image}">
                                    <img src="${pageContext.request.contextPath}/${event.image}" class="card-img-top event-image" alt="${event.eventName}"
                                         onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/default-event.jpg';">
                                </c:when>
                                <c:otherwise>
                                    <div class="no-image"><i class="fa fa-calendar-times-o"></i></div>
                                </c:otherwise>
                            </c:choose>

                            <div class="card-body">
                                <h5 class="card-title">${event.eventName}</h5>
                                <p class="card-text text-muted">${event.description}</p>
                                <div class="mb-2">
                                    <small><strong><i class="fa fa-map-marker"></i> Location:</strong></small>
                                    <p class="mb-1">${event.location}</p>
                                </div>
                                <div class="mb-2">
                                    <small><strong><i class="fa fa-clock-o"></i> Date:</strong></small>
                                    <p class="mb-1">${event.startDate} - ${event.endDate}</p>
                                </div>
                                <div class="mb-3">
                                    <small><strong>Status:</strong></small><br>
                                    <span class="status-Inactive">${event.status}</span>
                                </div>
                                <div class="btn-actions">
                                    <a href="${pageContext.request.contextPath}/delEvent?action=detail&id=${event.eventID}" class="btn btn-primary btn-sm flex-fill">
                                        <i class="fa fa-eye"></i> View Details
                                    </a>
                                    <a href="${pageContext.request.contextPath}/delEvent?action=restore&id=${event.eventID}" class="btn btn-success btn-sm"
                                       onclick="return confirm('Restore this event?');">
                                        <i class="fa fa-undo"></i> Restore
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <c:if test="${empty eventList}">
                <div class="row">
                    <div class="col-12">
                        <div class="alert alert-info text-center" role="alert">
                            <i class="fa fa-info-circle fa-3x mb-3"></i>
                            <h4>No deleted events found.</h4>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
    </div>

    <jsp:include page="includes/footer.jsp" />
</body>
</html>