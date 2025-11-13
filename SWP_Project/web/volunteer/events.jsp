<%-- 
    Document   : events
    Created on : Nov 1, 2025, 11:57:24 AM
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, entity.Event" %>

<%
    List<Event> events = (List<Event>) request.getAttribute("eventList");
    String error = (String) request.getAttribute("error");
    String applyError = (String) session.getAttribute("applyError");
    String applySuccess = (String) session.getAttribute("applySuccess");

    //  Xóa session message sau khi hiển thị
    session.removeAttribute("applyError");
    session.removeAttribute("applySuccess");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Volunteer Events | DONI Charity</title>
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
            background: url('<%= request.getContextPath() %>/assets/images/events_banner.jpg') center/cover no-repeat;
            height: 300px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #004085;
            text-align: center;
            flex-direction: column;
        }
        .banner h1 {
            font-size: 42px;
            letter-spacing: 1px;
            font-weight: 700;
            margin-bottom: 10px;
        }
        .banner p {
            font-size: 18px;
            opacity: 0.9;
        }

        .container {
            max-width: 1000px;
            margin: 50px auto;
            padding: 0 20px;
        }

        .event-card {
            display: flex;
            background: white;
            border-radius: 12px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.08);
            margin-bottom: 25px;
            overflow: hidden;
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .event-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 6px 12px rgba(0,0,0,0.12);
        }
        .event-card img {
            width: 250px;
            height: 180px;
            object-fit: cover;
        }
        .event-details {
            padding: 20px;
            flex: 1;
        }
        .event-details h3 {
            margin: 0;
            color: #1a237e;
            font-size: 22px;
        }
        .event-details p {
            margin: 6px 0;
            color: #555;
        }
        .apply-btn {
            background-color: #1a237e;
            color: white;
            padding: 10px 18px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            margin-top: 10px;
            transition: background 0.3s;
        }
        .apply-btn:hover {
            background-color: #0d1445;
        }

        .msg-success {
            color: #2e7d32;
            font-weight: bold;
            background: #e8f5e9;
            padding: 10px 15px;
            border-radius: 6px;
            margin-bottom: 20px;
        }
        .msg-error {
            color: #c62828;
            font-weight: bold;
            background: #ffebee;
            padding: 10px 15px;
            border-radius: 6px;
            margin-bottom: 20px;
        }

        footer {
            background: #1a237e;
            color: white;
            text-align: center;
            padding: 20px;
            margin-top: 50px;
        }
        .back-home {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            background: #1a237e;
            color: white;
            padding: 10px 16px;
            border-radius: 6px;
        }
        .back-home:hover {
            background: #0d1445;
        }
      /* Popup overlay */
.modal {
  display: none; 
  position: fixed; 
  z-index: 1000; 
  left: 0;
  top: 0;
  width: 100%; 
  height: 100%; 
  overflow: auto;
  background-color: rgba(0,0,0,0.5); 
}

/* Form nội dung */
.modal-content {
  background-color: #fff;
  margin: 8% auto;
  padding: 20px 30px;
  border-radius: 12px;
  width: 90%;
  max-width: 500px;
  box-shadow: 0 5px 15px rgba(0,0,0,0.3);
  animation: fadeIn 0.3s ease;
}

.modal-content textarea {
  width: 100%;
  border: 1px solid #ccc;
  border-radius: 6px;
  padding: 8px;
  font-family: 'Poppins', sans-serif;
  resize: vertical;
}

/* Nút đóng (x) */
.close {
  color: #aaa;
  float: right;
  font-size: 24px;
  font-weight: bold;
  cursor: pointer;
}
.close:hover {
  color: #000;
}

@keyframes fadeIn {
  from {opacity: 0; transform: scale(0.9);}
  to {opacity: 1; transform: scale(1);}
}


    </style>
</head>
<body>
<section class="banner">
    <h1>VOLUNTEER EVENTS</h1>
    <p>Join us in making a difference through kindness and community.</p>
</section>

<div class="container">

    <% if (error != null) { %>
        <div class="msg-error"><%= error %></div>
    <% } %>
    <% if (applyError != null) { %>
        <div class="msg-error"><%= applyError %></div>
    <% } %>
    <% if (applySuccess != null) { %>
        <div class="msg-success"><%= applySuccess %></div>
    <% } %>

    <% if (events != null && !events.isEmpty()) {
            
           for (Event e : events) { %>
        <div class="event-card">
            <img src="<%= request.getContextPath() %>/<%= e.getImage() %>" alt="Event Image">
            <div class="event-details">
                <h3><%= e.getEventName() %></h3>
                <p><b>📍 Location:</b> <%= e.getLocation() %></p>
                <p><b>🕒 Start:</b> <%= e.getStartDate() %> — <b>End:</b> <%= e.getEndDate() %></p>
                <p><%= e.getDescription() %></p>

        <!-- Nút Apply mở popup -->
<button type="button" class="apply-btn" onclick="openApplyForm(<%= e.getEventID() %>, '<%= e.getEventName().replace("'", "\\'") %>')">
    Apply
</button>

            </div>
        </div>
    <% } } else { %>
        <p style="text-align:center; color:#666;">No events available at the moment.</p>
    <% } %>

    <div style="text-align:center;">
        <a href="<%= request.getContextPath() %>/volunteer/index_1.jsp" class="back-home">← Back to Homepage</a>
    </div>
</div>

<footer>
    <p>&copy; 2025 DONI Charity | Together We Make a Difference</p>
</footer>
    <!-- Form Apply Popup -->
<div id="applyModal" class="modal">
  <div class="modal-content">
    <span class="close" onclick="closeApplyForm()">&times;</span>
    <h2>Apply for Event</h2>
    <p id="eventNameDisplay" style="font-weight:bold; color:#1a237e; margin-bottom:10px;"></p>

    <form id="applyForm" action="<%= request.getContextPath() %>/ApplyEventController" method="post">
      <input type="hidden" name="eventId" id="eventIdField">

      <label><b>✨ Why do you want to join?</b></label><br>
      <textarea name="motivation" rows="3" placeholder="Write your motivation..." required></textarea><br><br>

      <label><b>💼 Your previous experience:</b></label><br>
      <textarea name="experience" rows="3" placeholder="Describe your experience..." required></textarea><br>

      <div style="text-align:right; margin-top:10px;">
        <button type="button" class="apply-btn" style="background:#9e9e9e;" onclick="closeApplyForm()">Cancel</button>
        <button type="submit" class="apply-btn" style="background:#2e7d32;">Submit</button>
      </div>
    </form>
  </div>
</div>

<script>
function openApplyForm(eventId, eventName) {
  document.getElementById('applyModal').style.display = 'block';
  document.getElementById('eventIdField').value = eventId;
  document.getElementById('eventNameDisplay').innerText = eventName;
}

function closeApplyForm() {
  document.getElementById('applyModal').style.display = 'none';
}
window.onclick = function(event) {
  const modal = document.getElementById('applyModal');
  if (event.target == modal) {
    modal.style.display = "none";
  }
}
</script>


</body>
</html>
