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
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <link rel="stylesheet" href="css/responsive.css" />
        <link rel="icon" href="images/fevicon.png" type="image/gif" />
        <link rel="stylesheet" href="css/jquery.mCustomScrollbar.min.css" />
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" />
        <link rel="stylesheet" href="css/owl.carousel.min.css" />
        <link rel="stylesheet" href="css/owl.theme.default.min.css" />
        <link rel="stylesheet" href="css/sidebar.css" />
        <style>
            .event-detail-image {
                width: 100%;
                max-height: 500px;
                object-fit: cover;
                border-radius: 8px 8px 0 0;
            }
            .detail-card {
                border-radius: 8px;
                overflow: hidden;
            }
            .info-row {
                padding: 15px 0;
                border-bottom: 1px solid #eee;
            }
            .info-row:last-child {
                border-bottom: none;
            }
            .info-label {
                font-weight: 600;
                color: #555;
                min-width: 150px;
                display: inline-block;
            }
            .info-value {
                color: #333;
            }
            .status-badge {
                display: inline-block;
                padding: 8px 16px;
                border-radius: 20px;
                font-size: 0.9rem;
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
            .action-buttons {
                display: flex;
                gap: 10px;
                flex-wrap: wrap;
            }
            .no-image-detail {
                width: 100%;
                height: 400px;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                display: flex;
                align-items: center;
                justify-content: center;
                color: white;
                font-size: 3rem;
            }
        </style>
    </head>
    <body>
        <jsp:include page="includes/header.jsp" />

        <div class="news_section layout_padding">
            <div class="container">
                <div class="text-center mb-4">
                    <h1 class="news_taital">${event.eventName}</h1>
                    <p class="news_text">Complete event information and details</p>
                </div>

                <div class="row justify-content-center">
                    <div class="col-md-10">
                        <div class="card border-0 shadow-lg detail-card">
                            <!-- Event Image -->
                            <c:choose>
                                <c:when test="${not empty event.image}">
                                    <img src="${pageContext.request.contextPath}/${event.image}" 
                                         class="event-detail-image" 
                                         alt="${event.eventName}"
                                         onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/default-event.jpg';">
                                </c:when>
                                <c:otherwise>
                                    <div class="no-image-detail">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            
                            <div class="card-body p-4">
                                <!-- Event Name -->
                                <h3 class="card-title mb-4">${event.eventName}</h3>
                                
                                <!-- Description -->
                                <div class="mb-4">
                                    <h5 class="text-primary"><i class="fa fa-align-left"></i> Description</h5>
                                    <p class="card-text" style="text-align: justify; line-height: 1.8;">
                                        ${event.description}
                                    </p>
                                </div>
                                
                                <hr class="my-4">
                                
                                <!-- Event Information -->
                                <h5 class="text-primary mb-3"><i class="fa fa-info-circle"></i> Event Information</h5>
                                
                                <div class="info-row">
                                    <span class="info-label"><i class="fa fa-map-marker"></i> Location:</span>
                                    <span class="info-value">${event.location}</span>
                                </div>
                                
                                <div class="info-row">
                                    <span class="info-label"><i class="fa fa-calendar"></i> Start Date:</span>
                                    <span class="info-value">
                                        <fmt:formatDate value="${event.startDate}" pattern="dd/MM/yyyy HH:mm"/>
                                    </span>
                                </div>
                                
                                <div class="info-row">
                                    <span class="info-label"><i class="fa fa-calendar-check-o"></i> End Date:</span>
                                    <span class="info-value">
                                        <fmt:formatDate value="${event.endDate}" pattern="dd/MM/yyyy HH:mm"/>
                                    </span>
                                </div>
                                
                                <div class="info-row">
                                    <span class="info-label"><i class="fa fa-users"></i> Capacity:</span>
                                    <span class="info-value">${event.capacity} participants</span>
                                </div>
                                
                                <div class="info-row">
                                    <span class="info-label"><i class="fa fa-tag"></i> Category ID:</span>
                                    <span class="info-value">${event.categoryID}</span>
                                </div>
                                
                                <div class="info-row">
                                    <span class="info-label"><i class="fa fa-flag"></i> Status:</span>
                                    <span class="status-badge status-${event.status}">${event.status}</span>
                                </div>
                                
                                <!-- Additional Info (only for Staff/Manager) -->
                                <c:if test="${not empty sessionScope.role && (sessionScope.role == 'Staff' || sessionScope.role == 'Manager')}">
                                    <hr class="my-4">
                                    <h5 class="text-primary mb-3"><i class="fa fa-cog"></i> Management Information</h5>
                                    
                                    <div class="info-row">
                                        <span class="info-label"><i class="fa fa-user"></i> Created By (Staff ID):</span>
                                        <span class="info-value">${event.createdByStaffID}</span>
                                    </div>
                                    
                                    <c:if test="${not empty event.managerID}">
                                        <div class="info-row">
                                            <span class="info-label"><i class="fa fa-user-circle"></i> Manager ID:</span>
                                            <span class="info-value">${event.managerID}</span>
                                        </div>
                                    </c:if>
                                    
                                    <div class="info-row">
                                        <span class="info-label"><i class="fa fa-clock-o"></i> Created At:</span>
                                        <span class="info-value">
                                            <fmt:formatDate value="${event.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                                        </span>
                                    </div>
                                </c:if>
                                
                                <!-- Action Buttons -->
                                <div class="action-buttons mt-4">
                                    <a href="${pageContext.request.contextPath}/event?action=list" class="btn btn-secondary">
                                        <i class="fa fa-arrow-left"></i> Back to List
                                    </a>
                                    
                                    <c:if test="${not empty sessionScope.role && (sessionScope.role == 'Staff' || sessionScope.role == 'Manager')}">
                                        <a href="${pageContext.request.contextPath}/event?action=edit&id=${event.eventID}" 
                                           class="btn btn-warning">
                                            <i class="fa fa-edit"></i> Edit Event
                                        </a>
                                        
                                        <a href="${pageContext.request.contextPath}/event?action=delete&id=${event.eventID}" 
                                           class="btn btn-danger"
                                           onclick="return confirm('Are you sure you want to delete this event?');">
                                            <i class="fa fa-trash"></i> Delete Event
                                        </a>
                                    </c:if>
                                    
                                    <!-- Register button for Volunteers -->
                                    <c:if test="${not empty sessionScope.role && sessionScope.role == 'Volunteer' && event.status == 'Active'}">
                                        <a href="${pageContext.request.contextPath}/registration?eventId=${event.eventID}" 
                                           class="btn btn-success">
                                            <i class="fa fa-check-circle"></i> Register for Event
                                        </a>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="includes/footer.jsp" />

        <script src="js/jquery.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.bundle.min.js"></script>
        <script src="js/jquery-3.0.0.min.js"></script>
        <script src="js/plugin.js"></script>
        <script src="js/jquery.mCustomScrollbar.concat.min.js"></script>
        <script src="js/custom.js"></script>
        <script src="js/owl.carousel.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.js"></script>
    </body>
</html>
