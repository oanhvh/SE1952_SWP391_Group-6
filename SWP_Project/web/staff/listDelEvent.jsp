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
        <link rel="stylesheet" href="../css/listDelEvent.css" />
    </head>
    <body>
        <jsp:include page="includes/header.jsp" />

        <div class="news_section layout_padding">
            <div class="container">
                <div class="row mb-4 align-items-center">
                    <div class="col-md-8">
                        <h1 class="news_taital mb-0">INACTIVE EVENTS</h1>
                        <p class="news_text mb-0">List of events that have been removed. You can restore or delete them below.</p>
                    </div>
                    <div class="col-md-4 text-right">
                        <div class="input-group">
                            <input type="text" id="searchEvent" class="form-control rounded-pill" placeholder="🔍 Search events...">
                        </div>
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
                                        <a href="${pageContext.request.contextPath}/staff/delEvent?action=detail&id=${event.eventID}" class="btn btn-primary btn-sm flex-fill">
                                            <i class="fa fa-eye"></i> View Details
                                        </a>
                                        <%--<a href="${pageContext.request.contextPath}/staff/delEvent?action=edit&id=${event.eventID}" 
                                           class="btn btn-warning btn-sm">
                                            <i class="fa fa-edit"></i> Edit
                                        </a>--%>
                                        <a href="${pageContext.request.contextPath}/staff/delEvent?action=restore&id=${event.eventID}" class="btn btn-success btn-sm"
                                           onclick="return confirm('Are you sure you want to restore this event?');">
                                            <i class="fa fa-undo"></i> Restore
                                        </a>
                                        <a href="${pageContext.request.contextPath}/staff/delEvent?action=delete&id=${event.eventID}" 
                                           class="btn btn-danger btn-sm"
                                           onclick="return confirm('Are you sure you want to delete this event completely?');">
                                            <i class="fa fa-trash"></i> Delete
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

        <script src="../js/jquery.min.js"></script>
        <script src="../js/popper.min.js"></script>
        <script src="../js/bootstrap.bundle.min.js"></script>
        <script src="../js/jquery-3.0.0.min.js"></script>
        <script src="../js/plugin.js"></script>
        <script src="../js/role.js?v=2"></script>
        <script src="../js/owl.carousel.js"></script>
        <script src="https:cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.js"></script>
        <script src="../js/login.js"></script>
        <script src="../js/jquery.mCustomScrollbar.concat.min.js"></script>
        <script src="../js/custom.js"></script>
        <script src="../js/owl.carousel.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.js"></script>
        <script>
                                                   document.getElementById('searchEvent').addEventListener('keyup', function () {
                                                       let keyword = this.value.toLowerCase();
                                                       let cards = document.querySelectorAll('.card');
                                                       cards.forEach(card => {
                                                           let name = card.querySelector('.card-title').textContent.toLowerCase();
                                                           card.parentElement.style.display = name.includes(keyword) ? '' : 'none';
                                                       });
                                                   });
        </script>
    </body>
</html>