<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.VolunteerApplications" %>
<%@ page import="entity.Event" %>

<%
    List<VolunteerApplications> historyList = (List<VolunteerApplications>) request.getAttribute("historyList");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Lịch sử tình nguyện | DONI Charity</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/responsive.css" />
        <link rel="icon" href="${pageContext.request.contextPath}/images/fevicon.png" type="image/gif" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.mCustomScrollbar.min.css" />
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.theme.default.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css" />   
    <style>
        body {
            background-color: #f9fbfd;
            font-family: 'Poppins', 'Segoe UI', sans-serif;
            color: #333;
        }

        .header-section {
            text-align: center;
            padding: 80px 20px 50px 20px;
            background: #001f54;
            color: #fff;
        }

        .header-section h2 {
            font-size: 40px;
            color: white;
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
            padding: 20px 25px;
            margin-bottom: 20px;
            display: flex;
            align-items: flex-start;
            gap: 20px;
            transition: transform 0.2s ease;
        }

        .event-card:hover {
            transform: translateY(-4px);
        }

        .event-details h5 {
            color: #001f54;
            font-weight: 600;
            margin-bottom: 8px;
        }

        .event-details p {
            margin: 3px 0;
            font-size: 15px;
        }

        .event-status {
            margin-top: 10px;
        }

        .badge {
            font-size: 13px;
            padding: 6px 10px;
            border-radius: 10px;
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
         
/* Container form */
.filter-form-inline {
    background: #fff;
    padding: 15px 20px;
    border-radius: 12px;
    box-shadow: 0 4px 10px rgba(0,0,0,0.08);
    margin-bottom: 30px;
    display: flex;
    flex-wrap: wrap;
    gap: 15px;
    align-items: flex-end;
}

/* Form group (input + label) */
.filter-form-inline .form-group {
    display: flex;
    flex-direction: column;
    flex: 1;
    min-width: 150px;
}

.filter-form-inline label {
    font-weight: 500;
    font-size: 14px;
    color: #001f54;
    margin-bottom: 5px;
}

.filter-form-inline input {
    padding: 8px 12px;
    border-radius: 6px;
    border: 1px solid #ccc;
    font-size: 14px;
    width: 100%;
}

/* Button */
.filter-form-inline button {
    background-color: #001f54;
    color: #fff;
    padding: 10px 24px;
    border-radius: 6px;
    font-weight: 600;
    border: none;
    cursor: pointer;
    transition: 0.3s;
    align-self: flex-end;
}

.filter-form-inline button:hover {
    background-color: #003080;
}

/* Responsive: khi màn hình nhỏ, các input xếp dọc */
@media (max-width: 767px) {
    .filter-form-inline {
        flex-direction: column;
    }

    .filter-form-inline .form-group,
    .filter-form-inline button {
        width: 100%;
    }

    .filter-form-inline button {
        align-self: stretch;
    }
}


    </style>
</head>
<body>
    <jsp:include page="includes/header.jsp" />
    <!-- Header -->
    <div class="header-section">
        <h2>📜 Volunteer History</h2>
        <p>Your completed and past volunteer experiences</p>
    </div>
      <div class="container">
    <form method="get" action="<%= request.getContextPath() %>/VolunteerHistory" class="filter-form-inline">
        <div class="form-group">
            <label for="eventName">Event Name</label>
            <input type="text" id="eventName" name="eventName"
                   value="<%= request.getParameter("eventName") != null ? request.getParameter("eventName") : "" %>"
                   placeholder="Enter event name">
        </div>
        <div class="form-group">
            <label for="location">Location</label>
            <input type="text" id="location" name="location"
                   value="<%= request.getParameter("location") != null ? request.getParameter("location") : "" %>"
                   placeholder="Enter location">
        </div>
        <div class="form-group">
            <label for="startDate">Start Date</label>
            <input type="date" id="startDate" name="startDate"
                   value="<%= request.getParameter("startDate") != null ? request.getParameter("startDate") : "" %>">
        </div>
        <div class="form-group">
            <label for="endDate">End Date</label>
            <input type="date" id="endDate" name="endDate"
                   value="<%= request.getParameter("endDate") != null ? request.getParameter("endDate") : "" %>">
        </div>
        <button type="submit">Filter</button>
    </form>
</div>

    <!-- Content -->
    <div class="container">
        <% if (historyList == null || historyList.isEmpty()) { %>
            <div class="alert alert-info text-center mt-4">
                Nothing
            </div>
        <% } else { %>
            <% for (VolunteerApplications va : historyList) {
                   Event e = va.getEvent(); %>
            <div class="event-card">
                <div class="event-details">
                    <h5><%= e.getEventName() %></h5>
                    <p>📍 <strong>Location:</strong> <%= e.getLocation() %></p>
                    <p>🕒 <strong>Time:</strong> <%= e.getStartDate() %> - <%= e.getEndDate() %></p>
                    <div class="event-status">
                        <% if ("Completed".equalsIgnoreCase(va.getStatus())) { %>
                            <span class="badge bg-success">Completed</span>
                        <% } else if ("Pending".equalsIgnoreCase(va.getStatus())) { %>
                            <span class="badge bg-warning text-dark">Pending</span>
                        <% } else if ("Rejected".equalsIgnoreCase(va.getStatus())) { %>
                            <span class="badge bg-danger">Rejected</span>
                        <% } else { %>
                            <span class="badge bg-secondary"><%= va.getStatus() %></span>
                        <% } %>
                    </div>
                </div>
            </div>
            <% } %>
        <% } %>

        <div class="text-center">
            <a href="<%= request.getContextPath() %>/volunteer/profile" class="btn-back">⬅ Back to profile</a>
        </div>
    </div>
    <jsp:include page="includes/footer.jsp" />
     <script src="../js/jquery.min.js"></script>
    <script src="../js/popper.min.js"></script>
    <script src="../js/bootstrap.bundle.min.js"></script>
    <script src="../js/jquery-3.0.0.min.js"></script>
    <script src="../js/plugin.js"></script>
    <script src="../js/role.js?v=2"></script>
    <script src="../js/owl.carousel.js"></script>
    <script src="https:cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.js"></script>
    <script src="../js/login.js"></script>
</body>
</html>
