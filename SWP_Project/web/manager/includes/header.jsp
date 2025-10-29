<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entity.Users" %>
<%
    Users user = (Users) session.getAttribute("authUser");
    String role = (String) session.getAttribute("role");
    String displayName = (user != null && user.getFullName() != null && !user.getFullName().isEmpty()) ? user.getFullName() : (user != null ? user.getUsername() : "");
    if (displayName == null) displayName = "";
    if (role == null) role = "";
%>
<!-- header section start -->
<div class="header_section"></div>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="index_1.jsp"><img src="../images/logo.png"></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active"><a class="nav-link" href="index_1.jsp">Home</a></li>
            <li class="nav-item"><a class="nav-link" href="about.jsp">About</a></li>
            <li class="nav-item"><a class="nav-link" href="donate.jsp">Donate</a></li>
            <li class="nav-item"><a class="nav-link" href="news.jsp">News</a></li>
            <li class="nav-item"><a class="nav-link" href="contact.jsp">Contact Us</a></li>
            <li class="nav-item"><a class="nav-link" href="mission.jsp">Our Mission</a></li>
        </ul>
        <div class="ml-auto">
            <div class="dropdown">
                <button class="btn btn-light dropdown-toggle" type="button" id="userMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span style="font-weight:600;"><%= displayName %></span>
                    <span class="text-muted">(<%= role %>)</span>
                </button>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userMenu">
                    <a class="dropdown-item" href="profile.jsp">My account</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="<%= request.getContextPath() %>/ProfileController">My account</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="<%= request.getContextPath() %>/logout">Sign out</a>
                </div>
            </div>
        </div>
    </div>
</nav>

<button id="sidebarToggle" class="sidebar-toggle">☰</button>
<div class="sidebar" id="managerSidebar">
    <ul>
        <li><a href="activation.jsp">Create activation code</a></li>
    </ul>
</div>
