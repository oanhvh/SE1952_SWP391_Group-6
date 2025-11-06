<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entity.Users" %>
<%@ page import="dao.NotificationsDAO" %>
<%
    Users user = (Users) session.getAttribute("authUser");
    String role = (String) session.getAttribute("role");
    String displayName = "";

    if (user != null) {
        displayName = (user.getFullName() != null && !user.getFullName().trim().isEmpty())
                      ? user.getFullName()
                      : user.getUsername();
    } else {
        // Nếu chưa đăng nhập, chuyển hướng sang login
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    if (role == null) role = "";
    int unreadNoti = 0;
    if (user != null) {
        try {
            NotificationsDAO ndao = new NotificationsDAO();
            Integer vid = ndao.getVolunteerIdByUserId(user.getUserID());
            if (vid != null) unreadNoti = ndao.getUnreadCountForVolunteer(vid);
        } catch (Exception ignore) {}
    }
%>

<!-- ========================= -->
<!-- 🔹 Header section start -->
<!-- ========================= -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="<%= request.getContextPath() %>/volunteer/index_1.jsp">
        <img src="<%= request.getContextPath() %>/images/logo.png" alt="Logo">
    </a>

    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <!-- Menu chính -->
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active"><a class="nav-link" href="<%= request.getContextPath() %>/volunteer/index_1.jsp">Home</a></li>
            <li class="nav-item"><a class="nav-link" href="<%= request.getContextPath() %>/volunteer/about.jsp">About</a></li>
            <li class="nav-item"><a class="nav-link" href="<%= request.getContextPath() %>/volunteer/donate.jsp">Donate</a></li>
            <li class="nav-item"><a class="nav-link" href="<%= request.getContextPath() %>/volunteer/news.jsp">News</a></li>
            <li class="nav-item"><a class="nav-link" href="<%= request.getContextPath() %>/volunteer/contact.jsp">Contact Us</a></li>
            <li class="nav-item"><a class="nav-link" href="<%= request.getContextPath() %>/volunteer/mission.jsp">Our Mission</a></li>
        </ul>

        <!-- Menu người dùng -->
        <div class="ml-auto d-flex align-items-center">
            <a href="<%= request.getContextPath() %>/notifications" class="btn btn-light position-relative mr-3" title="Thông báo" style="margin-right:12px;">
                <span style="font-size:18px;">🔔</span>
                <% if (unreadNoti > 0) { %>
                    <span class="badge badge-danger" style="position:absolute; top:-5px; right:-5px;"><%= unreadNoti %></span>
                <% } %>
            </a>
            <div class="dropdown">
                <button class="btn btn-light dropdown-toggle" type="button" id="userMenu"
                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span style="font-weight:600;"><%= displayName %></span>
                    <span class="text-muted">(<%= role %>)</span>
                </button>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userMenu">
                    <a class="dropdown-item" href="<%= request.getContextPath() %>/ProfileController">My account</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="<%= request.getContextPath() %>/volunteer/change_password.jsp">Change password</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="<%= request.getContextPath() %>/logout" onclick="return confirm('Would you like to log out?');">Sign out</a>
                </div>
            </div>
        </div>
    </div>
</nav>

<!-- Sidebar Toggle Button -->
<button id="sidebarToggle" class="sidebar-toggle">☰</button>

<!-- Volunteer Sidebar -->
<div class="sidebar" id="volunteerSidebar">
    <ul>
        <!-- 🔹 Xem danh sách tất cả sự kiện -->
        <li><a href="<%= request.getContextPath() %>/volunteer/events">📅 View Events</a></li>
        
        <!-- 🔹 Xem các sự kiện đã apply -->
        <li><a href="<%= request.getContextPath() %>/ApplyEventController">📝 My Applied Events</a></li>
        
        <!-- 🔹 Sự kiện hôm nay -->
        <li><a href="<%= request.getContextPath() %>/VolunteerTodayController">🌞 Volunteer Today</a></li>
        
        <!-- 🔹 Trang hồ sơ -->
        <li><a href="<%= request.getContextPath() %>/ProfileController">👤 Profile</a></li>
    </ul>
</div>
<!-- ========================= -->
<!-- 🔹 Header section end -->
<!-- ========================= -->
