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
        <title>Event Details</title>
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
                <div class="text-center mb-5">
                    <h1 class="news_taital">${event.eventName}</h1>
                    <p class="news_text">Event details and full description below.</p>
                </div>

                <div class="row justify-content-center">
                    <div class="col-md-10">
                        <div class="card border-0 shadow-lg">
                            <img src="${event.image}" class="card-img-top" alt="Event Image">
                            <div class="card-body">
                                <h4 class="card-title mb-3">${event.eventName}</h4>
                                <p class="card-text">${event.description}</p>
                                <hr>
                                <p><strong>Location:</strong> ${event.location}</p>
                                <p><strong>Start Date:</strong> 
                                    <fmt:formatDate value="${event.startDate}" pattern="dd-MM-yyyy HH:mm"/>
                                </p>
                                <p><strong>End Date:</strong> 
                                    <fmt:formatDate value="${event.endDate}" pattern="dd-MM-yyyy HH:mm"/>
                                </p>
                                <p><strong>Capacity:</strong> ${event.capacity}</p>
                                <p><strong>Category ID:</strong> ${event.categoryID}</p>
                                <p><strong>Status:</strong> ${event.status}</p>
                                <p><strong>Created By (Staff ID):</strong> ${event.createdByStaffID}</p>
                                <p><strong>Created At:</strong> 
                                    <fmt:formatDate value="${event.createdAt}" pattern="dd-MM-yyyy HH:mm"/>
                                </p>
                                <div class="text-end mt-3">
                                    <a href="event?action=list" class="btn btn-secondary">Back to List</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
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
