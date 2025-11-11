<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.NotificationsDAO.NotificationItem" %>
<%
    @SuppressWarnings("unchecked")
    List<NotificationItem> notifications = (List<NotificationItem>) request.getAttribute("notifications");
    Integer unread = (Integer) request.getAttribute("unread");
    if (notifications == null) {
        notifications = java.util.Collections.emptyList();
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Notifications</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
</head>
<body>
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h4>Notifications <% if (unread != null && unread > 0) { %><span class="badge badge-danger"><%= unread %> unread</span><% } %></h4>
        <div class="btn-group">
            <button type="button" class="btn btn-outline-secondary" onclick="if (history.length > 1) { history.back(); } else { window.location.href='<%= request.getContextPath() %>/volunteer/index_1.jsp'; }">Back</button>
            <a class="btn btn-outline-primary" href="<%= request.getContextPath() %>/notifications/mark-all">Mark all as read</a>
        </div>
    </div>

    <div class="list-group">
        <% if (notifications.isEmpty()) { %>
            <div class="alert alert-info">No notifications.</div>
        <% } %>
        <% for (NotificationItem n : notifications) { %>
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

    <div class="mt-3">
        <a href="<%= request.getContextPath() %>/volunteer/index_1.jsp" class="btn btn-link">Back</a>
    </div>
</div>
</body>
</html>
