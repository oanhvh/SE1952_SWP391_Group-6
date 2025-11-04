<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, entity.VolunteerApplications, entity.Event" %>

<%
    List<VolunteerApplications> todayEvents = (List<VolunteerApplications>) request.getAttribute("todayEvents");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Sự kiện hôm nay | DONI Charity</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <style>
        body {
            background-color: #f9fbfd;
            font-family: "Poppins", "Segoe UI", sans-serif;
            color: #333;
        }

        .header-section {
            text-align: center;
            padding: 80px 20px 50px 20px;
            background: #001f54;
            color: #fff;
        }

        .header-section h2 {
            font-size: 38px;
            font-weight: 700;
        }

        .header-section p {
            font-size: 18px;
            opacity: 0.9;
        }

        .container {
            max-width: 900px;
            margin: -40px auto 60px;
        }

        .event-card {
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.08);
            padding: 25px;
            margin-bottom: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            transition: transform 0.2s ease;
        }

        .event-card:hover {
            transform: translateY(-4px);
        }

        .event-info {
            flex: 1;
        }

        .event-info h5 {
            color: #001f54;
            font-weight: 600;
            margin-bottom: 10px;
        }

        .event-info p {
            margin: 3px 0;
            font-size: 15px;
        }

        .status {
            margin-top: 8px;
        }

        .badge {
            font-size: 13px;
            padding: 6px 10px;
            border-radius: 10px;
        }

        .btn-complete {
            background-color: #28a745;
            color: white;
            border: none;
            font-weight: 600;
            border-radius: 6px;
            padding: 8px 14px;
            transition: 0.3s;
        }

        .btn-complete:hover {
            background-color: #1e7e34;
        }

        .alert-info {
            background-color: #eaf4ff;
            color: #004085;
            border: none;
            font-weight: 500;
            border-radius: 10px;
            padding: 20px;
        }

        .btn-back {
            display: inline-block;
            background-color: #001f54;
            color: white;
            padding: 10px 24px;
            border-radius: 6px;
            font-weight: 600;
            text-decoration: none;
            transition: 0.3s;
            margin-top: 20px;
        }

        .btn-back:hover {
            background-color: #003080;
            color: white;
        }
    </style>
</head>
<body>

    <!-- Header -->
    <div class="header-section">
        <h2>🌞 Volunteer Today</h2>
        <p>Thank you for joining DONI Charity in spreading kindness and hope today!</p>
    </div>

    <!-- Main Content -->
    <div class="container">
        <% if (todayEvents == null || todayEvents.isEmpty()) { %>
            <div class="alert alert-info text-center">
                Hôm nay bạn không có sự kiện nào.
            </div>
        <% } else { %>
            <% for (VolunteerApplications va : todayEvents) { 
                   Event e = va.getEvent(); %>
            <div class="event-card">
                <div class="event-info">
                    <h5><%= e.getEventName() %></h5>
                    <p>📍 <strong>Địa điểm:</strong> <%= e.getLocation() %></p>
                    <p>🕒 <strong>Thời gian:</strong> <%= e.getStartDate() %> - <%= e.getEndDate() %></p>
                    <div class="status">
                        <% if ("Completed".equalsIgnoreCase(va.getStatus())) { %>
                            <span class="badge bg-success">Hoàn thành</span>
                        <% } else if ("Pending".equalsIgnoreCase(va.getStatus())) { %>
                            <span class="badge bg-warning text-dark">Đang chờ</span>
                        <% } else if ("Approved".equalsIgnoreCase(va.getStatus())) { %>
                            <span class="badge bg-primary">Đã duyệt</span>
                        <% } else { %>
                            <span class="badge bg-secondary"><%= va.getStatus() %></span>
                        <% } %>
                    </div>
                </div>

                <div>
                    <% if (!"Completed".equalsIgnoreCase(va.getStatus())) { %>
                        <form action="<%= request.getContextPath() %>/CompleteEventController" method="post" style="display:inline;">
                            <input type="hidden" name="applicationId" value="<%= va.getApplicationID() %>">
                            <button type="submit" class="btn-complete btn-sm">Đã hoàn thành</button>
                        </form>
                    <% } else { %>
                        <span class="text-success fw-bold fs-5">✔</span>
                    <% } %>
                </div>
            </div>
            <% } %>
        <% } %>

        <div class="text-center">
            <a href="<%= request.getContextPath() %>/volunteer/index_1.jsp" class="btn-back">← Trở về trang chủ</a>
        </div>
    </div>

</body>
</html>
