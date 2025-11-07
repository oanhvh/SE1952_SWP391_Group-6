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
        <title>Event List</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/responsive.css" />
        <link rel="icon" href="${pageContext.request.contextPath}/images/fevicon.png" type="image/gif" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.mCustomScrollbar.min.css" />
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.theme.default.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css" />
        <style>
            .event-image {
                width: 100%;
                height: 250px;
                object-fit: cover;
            }
            .card {
                transition: transform 0.3s ease;
                height: 100%;
            }
            .card:hover {
                transform: translateY(-5px);
                box-shadow: 0 8px 16px rgba(0,0,0,0.2) !important;
            }
            .status-badge {
                display: inline-block;
                padding: 5px 12px;
                border-radius: 20px;
                font-size: 0.85rem;
                font-weight: 600;
            }
            .status-Pending {
                background-color: #ffc107;
                color: #000;
            }
            .status-Active {
                background-color: #28a745;
                color: #fff;
            }
            .status-Completed {
                background-color: #6c757d;
                color: #fff;
            }
            .card-text {
                display: -webkit-box;
                -webkit-line-clamp: 3;
                -webkit-box-orient: vertical;
                overflow: hidden;
                text-overflow: ellipsis;
                min-height: 4.5em;
            }
            .btn-actions {
                display: flex;
                gap: 10px;
                flex-wrap: wrap;
            }
            .no-image {
                width: 100%;
                height: 250px;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                display: flex;
                align-items: center;
                justify-content: center;
                color: white;
                font-size: 1.5rem;
            }
        </style>
    </head>
    <body>
        <jsp:include page="includes/header.jsp" />
        
        <div class="news_section layout_padding">
            <div class="container">
                <div class="row mb-4">
                    <div class="col-sm-12">
                        <h1 class="news_taital">EVENT LIST</h1>
                        <p class="news_text">Explore our upcoming and ongoing events below.</p>
                    </div>
                </div>
                
                <c:if test="${not empty sessionScope.role && (sessionScope.role == 'Staff' || sessionScope.role == 'Manager')}">
                    <div class="row mb-3">
                        <div class="col-sm-12 text-end">
                            <a href="${pageContext.request.contextPath}/event?action=create" class="btn btn-success">
                                <i class="fa fa-plus"></i> Create New Event
                            </a>
                        </div>
                    </div>
                </c:if>
                
                <div class="row">
                    <c:forEach var="event" items="${eventList}">
                        <div class="col-md-6 col-lg-4 mb-4">
                            <div class="card shadow-sm border-0">
                                <c:choose>
                                    <c:when test="${not empty event.image}">
                                        <img src="${pageContext.request.contextPath}/${event.image}" 
                                             class="card-img-top event-image" 
                                             alt="${event.eventName}"
                                             onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/default-event.jpg';">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="no-image">
                                            <i class="fa fa-calendar"></i>
                                        </div>
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
                                        <span class="status-badge status-${event.status}">${event.status}</span>
                                    </div>
                                    
                                    <div class="btn-actions">
                                        <a href="${pageContext.request.contextPath}/event?action=detail&id=${event.eventID}" 
                                           class="btn btn-primary btn-sm flex-fill">
                                            <i class="fa fa-eye"></i> View Details
                                        </a>
                                        
                                        <c:if test="${not empty sessionScope.role && (sessionScope.role == 'Staff' || sessionScope.role == 'Manager')}">
                                            <a href="${pageContext.request.contextPath}/event?action=edit&id=${event.eventID}" 
                                               class="btn btn-warning btn-sm">
                                                <i class="fa fa-edit"></i> Edit
                                            </a>
                                            <a href="${pageContext.request.contextPath}/event?action=delete&id=${event.eventID}" 
                                               class="btn btn-danger btn-sm"
                                               onclick="return confirm('Are you sure you want to delete this event?');">
                                                <i class="fa fa-trash"></i> Delete
                                            </a>
                                        </c:if>
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
                                <h4>No events available at the moment.</h4>
                                <c:if test="${not empty sessionScope.role && (sessionScope.role == 'Staff' || sessionScope.role == 'Manager')}">
                                    <p class="mt-3">
                                        <a href="${pageContext.request.contextPath}/event?action=create" class="btn btn-success">
                                            Create Your First Event
                                        </a>
                                    </p>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
        
        <jsp:include page="includes/footer.jsp" />
        
        <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery-3.0.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/plugin.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.mCustomScrollbar.concat.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/custom.js"></script>
        <script src="${pageContext.request.contextPath}/js/owl.carousel.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.js"></script>
    </body>
</html>
