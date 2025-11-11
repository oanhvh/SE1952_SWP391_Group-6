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
    <style>
        body {
            font-family: "Poppins", sans-serif;
            background-color: #f5f7fa;
            margin: 0;
            padding: 0;
        }

        .edit-container {
            max-width: 800px;
            margin: 60px auto;
            background: #fff;
            border-radius: 20px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
            padding: 40px 50px;
        }

        h2 {
            text-align: center;
            color: #001F60;
            font-size: 28px;
            margin-bottom: 25px;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 18px;
        }

        label {
            font-weight: 600;
            color: #001F60;
            margin-bottom: 4px;
        }

        input[type="text"],
        input[type="email"],
        input[type="date"] {
            width: 100%;
            padding: 10px 14px;
            font-size: 15px;
            border-radius: 10px;
            border: 1px solid #ccc;
            outline: none;
            transition: 0.3s;
        }

        input:focus {
            border-color: #001F60;
            box-shadow: 0 0 5px rgba(0,31,96,0.3);
        }

        .message {
            margin: 15px 0;
            padding: 12px;
            border-radius: 10px;
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
            text-align: center;
            font-weight: 500;
        }

        .buttons {
            display: flex;
            justify-content: center;
            gap: 15px;
            margin-top: 25px;
        }

        button,
        a.button {
            padding: 10px 25px;
            border: none;
            border-radius: 25px;
            font-size: 15px;
            font-weight: 500;
            color: #fff;
            cursor: pointer;
            text-decoration: none;
            transition: 0.3s;
        }

        button {
            background-color: #e74c3c;
        }

        button:hover {
            background-color: #c0392b;
        }

        a.button {
            background-color: #001F60;
        }

        a.button:hover {
            background-color: #003399;
        }

        @media (max-width: 768px) {
            .edit-container {
                padding: 25px 20px;
            }

            h2 {
                font-size: 22px;
            }
        }
    </style>
</head>
<body>

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

</body>
</html>
