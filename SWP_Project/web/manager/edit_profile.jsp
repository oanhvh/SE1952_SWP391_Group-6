<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entity.Users" %>
<%
    Users user = (Users) request.getAttribute("user");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Edit Profile</title>
        <link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="../css/style.css" />
        <link rel="stylesheet" href="../css/responsive.css" />
        <link rel="icon" href="../images/fevicon.png" type="image/gif" />
        <link rel="stylesheet" href="../css/jquery.mCustomScrollbar.min.css" />
        <link rel="stylesheet" href="../css/sidebar.css" />
        <link rel="stylesheet" href="../css/edit_profile.css" />
    </head>
    <body>
        <jsp:include page="includes/header.jsp" />

        <div class="edit-container">
            <h2>Edit My Profile</h2>

            <% if (error != null) { %>
            <div class="message"><%= error %></div>
            <% } %>

            <form action="<%= request.getContextPath() %>/volunteer/profile" method="post">
                <label>Full Name</label>
                <input type="text" name="fullName" value="<%= user.getFullName() != null ? user.getFullName() : "" %>">

                <label>Email</label>
                <input type="email" name="email" value="<%= user.getEmail() != null ? user.getEmail() : "" %>">

                <label>Phone</label>
                <input type="text" name="phone" value="<%= user.getPhone() != null ? user.getPhone() : "" %>">

                <label>Date of Birth</label>
                <input type="date" name="dateOfBirth" value="<%= user.getDateOfBirth() != null ? user.getDateOfBirth() : "" %>">

                <label>Avatar URL</label>
                <input type="text" name="avatar" value="<%= user.getAvatar() != null ? user.getAvatar() : "" %>">

                <div class="buttons">
                    <button type="submit">Save Changes</button>
                    <a href="<%= request.getContextPath() %>/volunteer/profile" class="button">Cancel</a>
                </div>
            </form>
        </div>

        <jsp:include page="includes/footer.jsp" />

        <script src="../js/jquery.min.js"></script>
        <script src="../js/popper.min.js"></script>
        <script src="../js/bootstrap.bundle.min.js"></script>
        <script src="../js/role.js?v=2"></script>

    </body>
</html>
