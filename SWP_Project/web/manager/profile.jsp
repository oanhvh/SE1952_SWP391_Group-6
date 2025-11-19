<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entity.Users" %>
<%
    Users user = (Users) request.getAttribute("user");
    String success = (String) request.getAttribute("success");
    String error = (String) request.getAttribute("error");

    // Lấy role và scope
    String role = (String) session.getAttribute("role");
    String scope = (String) request.getAttribute("scope");
    if(scope == null) {
        scope = "volunteer"; // default
        if("Staff".equalsIgnoreCase(role)) scope = "staff";
        else if("Manager".equalsIgnoreCase(role)) scope = "manager";
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>My Profile</title>
        <link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="../css/style.css" />
        <link rel="stylesheet" href="../css/responsive.css" />
        <link rel="icon" href="../images/fevicon.png" type="image/gif" />
        <link rel="stylesheet" href="../css/jquery.mCustomScrollbar.min.css" />
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" />
        <link rel="stylesheet" href="../css/owl.carousel.min.css" />
        <link rel="stylesheet" href="../css/owl.theme.default.min.css" />
        <link rel="stylesheet" href="../css/sidebar.css" />
        <link rel="stylesheet" href="../css/profile.css" />
    </head>
    <body>
        <jsp:include page="includes/header.jsp" />

        <div class="profile-container">
            <h2>My Profile</h2>

            <% if (success != null) { %>
            <div class="message success"><%= success %></div>
            <% } else if (error != null) { %>
            <div class="message error"><%= error %></div>
            <% } %>

            <div class="profile-header">
                <img src="<%= (user.getAvatar() != null && !user.getAvatar().isEmpty()) 
                            ? user.getAvatar() 
                            : "https://via.placeholder.com/140" %>" alt="Avatar">
            </div>

            <div class="info">
                <p><strong>Full Name:</strong> <%= user.getFullName() %></p>
                <p><strong>Email:</strong> <%= user.getEmail() %></p>
                <p><strong>Phone:</strong> <%= user.getPhone() %></p>
                <p><strong>Date of Birth:</strong> <%= user.getDateOfBirth() != null ? user.getDateOfBirth() : "" %></p>
            </div>

            <div class="buttons">
                <a href="<%= request.getContextPath() %>/<%= scope %>/profile?action=edit" class="button">Edit Profile</a>
                <a href="<%= request.getContextPath() %>/<%= scope %>/index_1.jsp" class="button back-btn">Back to Homepage</a>
            </div>
        </div>
        <jsp:include page="includes/footer.jsp" />

        <script src="../js/role.js?v=2"></script>
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
