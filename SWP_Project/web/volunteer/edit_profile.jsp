<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entity.Users" %>
<%
    Users user = (Users) request.getAttribute("user");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Profile</title>
        <link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="../css/style.css" />
        <link rel="stylesheet" href="../css/responsive.css" />
        <link rel="icon" href="../images/fevicon.png" type="image/gif" />
        <link rel="stylesheet" href="../css/jquery.mCustomScrollbar.min.css" />
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" />
        <link rel="stylesheet" href="../css/owl.carousel.min.css" />
        <link rel="stylesheet" href="../css/owl.theme.default.min.css" />
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
                <!-- Thêm action để servlet nhận diện -->
                <input type="hidden" name="action" value="updateProfile">

                <label>Full Name</label>
                <input type="text" name="fullName" value="<%= user.getFullName() != null ? user.getFullName() : "" %>" required>

                <label>Email</label>
                <input type="email" name="email" value="<%= user.getEmail() != null ? user.getEmail() : "" %>">

                <label>Phone</label>
                <input type="text" 
                       name="phone" 
                       value="<%= user.getPhone() != null ? user.getPhone() : "" %>" 
                       required 
                       minlength="10"
                       pattern="[0-9]{10}" 
                       maxlength="10"

                       title="Phone must be 10 numbers">

                <%
              // Lấy ngày hiện tại
              java.time.LocalDate today = java.time.LocalDate.now();

              // Giới hạn ngày sinh tối thiểu: không quá 130 tuổi
              java.time.LocalDate minDate = today.minusYears(130);

              java.time.LocalDate dob = user.getDateOfBirth();
                %>

                <label>Date of Birth</label>
                <input type="date" 
                       name="dateOfBirth" 
                       value="<%= (dob != null) ? dob.toString() : "" %>" 
                       min="<%= minDate.toString() %>" 
                       max="<%= today.toString() %>" 
                       required>


                <label>Avatar URL</label>
                <input type="text" name="avatar" value="<%= user.getAvatar() != null ? user.getAvatar() : "" %>">

                <div class="buttons">
                    <button type="submit">Save Changes</button>
                    <a href="<%= request.getContextPath() %>/volunteer/profile" class="button">Cancel</a>
                </div>
            </form>
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
