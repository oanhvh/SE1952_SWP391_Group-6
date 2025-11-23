<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Notifications" %>
<%
    @SuppressWarnings("unchecked")
    List<Notifications> notifications = (List<Notifications>) request.getAttribute("notifications");
    Integer unread = (Integer) request.getAttribute("unread");
    if (notifications == null) {
        notifications = java.util.Collections.emptyList();
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Notifications</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/responsive.css" />
        <link rel="icon" href="${pageContext.request.contextPath}/images/fevicon.png" type="image/gif" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.mCustomScrollbar.min.css" />
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.theme.default.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css" />
    </head>
    <body>
        <jsp:include page="includes/header.jsp" />

        <div class="about_section layout_padding">
            <div class="container">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h2 class="mb-0" style="color:#ffffff; font-weight:700;">Notifications <% if (unread != null && unread > 0) { %><span class="badge badge-danger ml-2"><%= unread %> unread</span><% } %></h2>
                    <div class="btn-group">
                        <a class="btn btn-outline-primary" href="<%= request.getContextPath() %>/notifications/mark-all">Mark all as read</a>
                    </div>
                </div>

                <div class="list-group">
                    <% if (notifications.isEmpty()) { %>
                    <div class="alert alert-info">No notifications.</div>
                    <% } %>
                    <% for (Notifications n : notifications) { %>
                    <div class="list-group-item d-flex justify-content-between align-items-center <%= n.read ? "" : "list-group-item-warning" %>">
                        <div>
                            <div class="font-weight-bold"><%= n.title %></div>
                            <div class="text-muted" style="white-space: pre-wrap;"><%= n.message %></div>
                            <div class="small text-secondary mt-1"><%= n.createdAt != null ? n.createdAt : "" %></div>
                        </div>
                        <div class="ml-3">
                            <% if (!n.read) { %>
                            <a class="btn btn-sm btn-outline-success" href="<%= request.getContextPath() %>/notifications/mark?id=<%= n.notificationId %>">Mark read</a>
                            <% } %>
                        </div>
                    </div>
                    <% } %>
                </div>

            </div>
        </div>
        <jsp:include page="includes/footer.jsp" />

        <script src="${pageContext.request.contextPath}/js/role.js?v=2"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery-3.0.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/plugin.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.mCustomScrollbar.concat.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/custom.js"></script>
        <script src="${pageContext.request.contextPath}/js/owl.carousel.js"></script>
        <script src="https:cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.js"></script>
    </body>
</html>
