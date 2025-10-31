<%-- 
    Document   : history
    Created on : Oct 30, 2025, 2:54:22 PM
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.VolunteerApplications" %>
<%@ page import="entity.Event" %>

<%
    List<VolunteerApplications> applications = (List<VolunteerApplications>) request.getAttribute("applications");
    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Volunteer History</title>
    <style>
        body {
            font-family: "Poppins", sans-serif;
            background-color: #f5f7fa;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 1000px;
            margin: 50px auto;
            background: #fff;
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
        }
        h2 {
            text-align: center;
            color: #001F60;
            font-size: 26px;
            margin-bottom: 30px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 14px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #001F60;
            color: white;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .status {
            font-weight: 600;
            padding: 5px 10px;
            border-radius: 12px;
        }
        .status.Pending { background: #ffeeba; color: #856404; }
        .status.Approved { background: #d4edda; color: #155724; }
        .status.Rejected { background: #f8d7da; color: #721c24; }
        .back-btn {
            display: inline-block;
            background-color: #001F60;
            color: white;
            padding: 10px 20px;
            border-radius: 25px;
            text-decoration: none;
            margin-top: 25px;
            transition: 0.3s;
        }
        .back-btn:hover {
            background-color: #003399;
        }
        .message-error {
            color: #721c24;
            background-color: #f8d7da;
            padding: 10px;
            border-radius: 10px;
            border: 1px solid #f5c6cb;
            text-align: center;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Volunteer History</h2>

    <% if (error != null) { %>
        <div class="message-error"><%= error %></div>
    <% } else if (applications == null || applications.isEmpty()) { %>
        <p style="text-align:center;">You have not applied for any events yet.</p>
    <% } else { %>
        <table>
            <thead>
                <tr>
                    <th>Event Name</th>
                    <th>Location</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Status</th>
                    <th>Applied On</th>
                </tr>
            </thead>
            <tbody>
                <% for (VolunteerApplications app : applications) { 
                       Event e = app.getEvent(); %>
                    <tr>
                        <td><%= e.getEventName() %></td>
                        <td><%= e.getLocation() %></td>
                        <td><%= e.getStartDate() != null ? e.getStartDate().toLocalDate() : "" %></td>
                        <td><%= e.getEndDate() != null ? e.getEndDate().toLocalDate() : "" %></td>
                        <td><span class="status <%= app.getStatus() %>"><%= app.getStatus() %></span></td>
                        <td><%= app.getApplicationDate() != null ? app.getApplicationDate().toLocalDate() : "" %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    <% } %>

    <div style="text-align:center;">
        <a href="../ProfileController" class="back-btn">Back to profile</a>
    </div>
</div>

</body>
</html>

