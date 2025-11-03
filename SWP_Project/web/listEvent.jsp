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

        <div class="news_section layout_padding">
            <div class="container">
                <div class="row">
                    <div class="col-sm-12">
                        <h1 class="news_taital">EVENT LIST</h1>
                        <p class="news_text">Explore our upcoming and ongoing events below.</p>
                    </div>
                </div>

                <div class="row">
                <c:forEach var="event" items="${eventList}">
                    <div class="col-md-6 mb-4">
                        <div class="card shadow-sm border-0">
                            <img src="${event.image}" class="card-img-top" alt="Event Image">
                            <div class="card-body">
                                <h4 class="card-title">${event.eventName}</h4>
                                <p class="card-text text-muted">${event.description}</p>
                                <p><strong>Location:</strong> ${event.location}</p>
                                <p><strong>Date:</strong>
                                    <fmt:formatDate value="${event.startDate}" pattern="dd-MM-yyyy HH:mm"/> -
                                    <fmt:formatDate value="${event.endDate}" pattern="dd-MM-yyyy HH:mm"/>
                                </p>
                                <p><strong>Status:</strong> ${event.status}</p>
                                <a href="event?action=detail&id=${event.eventID}" class="btn btn-outline-primary btn-sm">View Details</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

<c:if test="${empty eventList}">
    <p class="text-center mt-4">No events available at the moment.</p>
</c:if>
<!-- End Event List Section -->


<jsp:include page="includes/footer.jsp" />

<script src="../js/jquery.min.js"></script>
<script src="../js/popper.min.js"></script>
<script src="../js/bootstrap.bundle.min.js"></script>
<script src="../js/jquery-3.0.0.min.js"></script>
<script src="../js/plugin.js"></script>
<script src="../js/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="../js/custom.js"></script>
<script src="../js/owl.carousel.js"></script>
<script src="https:cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.js"></script>
</body>
</html>
