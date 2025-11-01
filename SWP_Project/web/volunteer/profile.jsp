<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entity.Users" %>
<%
    Users user = (Users) request.getAttribute("user");
    String success = (String) request.getAttribute("success");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <title>My Profile</title>
    <style>
        body {
            font-family: "Poppins", sans-serif;
            background-color: #f5f7fa;
            margin: 0;
            padding: 0;
        }

        .profile-container {
            max-width: 900px;
            margin: 60px auto;
            background: #fff;
            border-radius: 20px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
            padding: 40px 60px;
        }

        h2 {
            text-align: center;
            color: #001F60;
            font-size: 28px;
            margin-bottom: 30px;
        }

        .profile-header {
            text-align: center;
            margin-bottom: 25px;
        }

        .profile-header img {
            width: 140px;
            height: 140px;
            border-radius: 50%;
            object-fit: cover;
            border: 4px solid #001F60;
        }

        .message {
            margin: 15px 0;
            padding: 12px;
            border-radius: 10px;
            font-weight: 500;
        }

        .message.success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .message.error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }

        .info p {
            font-size: 16px;
            color: #333;
            margin: 10px 0;
        }

        .info strong {
            color: #001F60;
        }

        .buttons {
            text-align: center;
            margin-top: 35px;
        }

        .button {
            display: inline-block;
            background-color: #e74c3c;
            color: white;
            padding: 10px 20px;
            border-radius: 25px;
            text-decoration: none;
            margin: 5px 10px;
            transition: 0.3s;
            font-weight: 500;
        }

        .button:hover {
            background-color: #c0392b;
        }

        .back-btn {
            background-color: #001F60;
        }

        .back-btn:hover {
            background-color: #003399;
        }

        @media (max-width: 768px) {
            .profile-container {
                padding: 30px 20px;
            }

            h2 {
                font-size: 22px;
            }
        }
    </style>
</head>
<body>

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
        <a href="<%= request.getContextPath() %>/volunteer/profile?action=edit" class="button">Edit Profile</a>
        <a href="<%= request.getContextPath() %>/volunteer/history.jsp" class="button">View Volunteer History</a>
        <a href="<%= request.getContextPath() %>/volunteer/index_1.jsp" class="button back-btn">Back to Homepage</a>
    </div>
</div>

</body>
</html>
