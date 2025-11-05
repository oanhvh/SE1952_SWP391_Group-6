<%-- 
    Document   : applied_events
    Created on : Nov 1, 2025, 1:06:40 PM
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.VolunteerApplications" %>
<%@ page import="entity.Event" %>

<%
    List<VolunteerApplications> appliedEvents = (List<VolunteerApplications>) request.getAttribute("appliedEvents");
    String cancelMsg = (String) session.getAttribute("cancelMsg");
    session.removeAttribute("cancelMsg");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Applied Events | DONI Charity</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background-color: #f9fafc;
            margin: 0;
            padding: 0;
        }
        header {
            background-color: #fff;
            box-shadow: 0 2px 6px rgba(0,0,0,0.05);
            padding: 15px 60px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        header img.logo {
            height: 50px;
        }
        nav a {
            text-decoration: none;
            color: #333;
            margin: 0 15px;
            font-weight: 500;
            transition: color 0.2s;
        }
        nav a:hover {
            color: #1a237e;
        }
        .user-info {
            font-weight: 600;
            color: #1a237e;
        }

        .banner {
            background: url('<%= request.getContextPath() %>/assets/images/applied_banner.jpg') center/cover no-repeat;
            height: 260px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #0048b3;
            text-align: center;
            flex-direction: column;
        }
        .banner h1 {
            font-size: 40px;
            font-weight: 700;
            margin-bottom: 8px;
        }
        .banner p {
            font-size: 17px;
            opacity: 0.9;
        }

        .container {
            max-width: 1000px;
            margin: 50px auto;
            padding: 0 20px;
            background: white;
            border-radius: 12px;
            box-shadow: 0 3px 8px rgba(0,0,0,0.08);
            padding: 30px;
        }
        h3 {
            color: #1a237e;
            margin-bottom: 20px;
            text-align: center;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 15px;
        }
        thead {
            background-color: #1a237e;
            color: white;
        }
        th, td {
            padding: 12px 10px;
            border-bottom: 1px solid #eee;
            text-align: left;
        }
        tr:hover {
            background-color: #f4f6fb;
        }

        .btn-cancel {
            background-color: #c62828;
            color: white;
            border: none;
            padding: 6px 12px;
            border-radius: 6px;
            cursor: pointer;
            transition: background 0.3s;
        }
        .btn-cancel:hover {
            background-color: #8e1b1b;
        }

        .msg {
            font-weight: bold;
            color: #2e7d32;
            background: #e8f5e9;
            padding: 10px 15px;
            border-radius: 6px;
            margin-bottom: 20px;
            text-align: center;
        }

        .no-event {
            text-align: center;
            color: #666;
            margin-top: 20px;
        }

        .back-home {
            display: inline-block;
            margin-top: 25px;
            text-decoration: none;
            background: #1a237e;
            color: white;
            padding: 10px 16px;
            border-radius: 6px;
            transition: background 0.3s;
        }
        .back-home:hover {
            background: #0d1445;
        }

        footer {
            background: #1a237e;
            color: white;
            text-align: center;
            padding: 20px;
            margin-top: 60px;
        }
    </style>
</head>
<body>
<% if (cancelMsg != null) { %>
<script>
    window.onload = function() {
        alert("<%= cancelMsg.replace("\"", "\\\"") %>");
    }
</script>
<% } %>

<section class="banner">
    <h1>MY APPLIED EVENTS</h1>
    <p>See all the volunteer programs you’ve joined with Doni Charity.</p>
</section>

<div class="container">
    <% if (cancelMsg != null) { %>
        <div class="msg"><%= cancelMsg %></div>
    <% } %>

    <% if (appliedEvents == null || appliedEvents.isEmpty()) { %>
        <p class="no-event">Bạn chưa đăng ký sự kiện nào.</p>
    <% } else { %>
        <table>
            <thead>
                <tr>
                    <th>Tên sự kiện</th>
                    <th>Địa điểm</th>
                    <th>Thời gian</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <% for (VolunteerApplications va : appliedEvents) { 
                       Event e = va.getEvent(); %>
                <tr>
                    <td><%= e.getEventName() %></td>
                    <td><%= e.getLocation() %></td>
                    <td><%= e.getStartDate() %> — <%= e.getEndDate() %></td>
                    <td><%= va.getStatus() %></td>
                    <td>
                        <% if ("Pending".equalsIgnoreCase(va.getStatus())) { %>
                            <form action="<%= request.getContextPath() %>/CancelApplyController" method="post" style="display:inline;">
                                <input type="hidden" name="applicationId" value="<%= va.getApplicationID() %>">
                                <button type="submit" class="btn-cancel" onclick="return confirm('Bạn có chắc muốn hủy apply sự kiện này không?');">
                                    Hủy Apply
                                </button>
                            </form>
                        <% } else { %>
                            <span style="color: #888;">Không thể hủy</span>
                        <% } %>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
    <% } %>

    <div style="text-align:center;">
        <a href="<%= request.getContextPath() %>/volunteer/index_1.jsp" class="back-home">← Back to Homepage</a>
    </div>
</div>

<footer>
    <p>&copy; 2025 DONI Charity | Together We Make a Difference</p>
</footer>

</body>
</html>
