<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Event Participation List</title>
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body {
            font-family:'Inter',sans-serif;
            background:linear-gradient(135deg,#eef2ff 0%,#d4d9f0 100%);
            min-height:100vh; padding:20px; color:#2d3748;
        }
        .container{
            max-width:1300px; margin:0 auto; background:#fff;
            border-radius:20px; box-shadow:0 15px 35px rgba(0,0,0,.08);
            overflow:hidden;
        }
        h2{
            text-align:center; padding:28px 20px; font-size:26px; font-weight:700;
            color:#fff; background:linear-gradient(90deg,#6366f1 0%,#8b5cf6 100%);
            margin:0; border-radius:20px 20px 0 0; letter-spacing:.5px;
        }
        /* ==== SEARCH FORM ==== */
        .search-form{
            padding:25px; background:#f8fafc; border-bottom:1px solid #e2e8f0;
            display:flex; flex-wrap:wrap; gap:12px; justify-content:center; align-items:center;
        }
        .form-group{
            display:flex; flex-direction:column; min-width:180px;
        }
        .form-group label{
            font-weight:600; color:#4a5568; margin-bottom:6px; font-size:14px;
        }
        .form-group input,
        .form-group select{
            padding:10px 12px; border:1.5px solid #cbd5e0; border-radius:10px;
            font-size:14px; transition:all .3s ease; background:#fff;
        }
        .form-group input:focus,
        .form-group select:focus{
            outline:none; border-color:#6366f1; box-shadow:0 0 0 3px rgba(99,102,241,.2);
        }
        .form-group input::placeholder{color:#a0aec0;}
        .btn-search{
            background:linear-gradient(90deg,#6366f1 0%,#8b5cf6 100%);
            color:#fff; border:none; padding:10px 20px; border-radius:10px;
            font-weight:600; cursor:pointer; transition:all .3s ease;
            display:flex; align-items:center; gap:8px; margin-left:10px; font-size:14px;
        }
        .btn-search:hover{
            transform:translateY(-2px); box-shadow:0 8px 20px rgba(99,102,241,.3);
        }
        /* ==== TABLE ==== */
        .table-container{overflow-x:auto; padding:0 25px;}
        table{
            width:100%; border-collapse:separate; border-spacing:0;
            margin:20px 0; font-size:15px;
        }
        th{
            background:linear-gradient(90deg,#6366f1 0%,#8b5cf6 100%);
            color:#fff; padding:16px 12px; text-align:center;
            font-weight:600; text-transform:uppercase; font-size:13px;
            letter-spacing:.8px; position:sticky; top:0; z-index:10;
        }
        td{
            padding:14px 12px; text-align:center;
            border-bottom:1px solid #e2e8f0; transition:background .2s ease;
        }
        tr:hover td{background:#f1f5f9;}
        tr:nth-child(even) td{background:#fafbfe;}
        tr:last-child td{border-bottom:none;}
        /* Status color */
        td:nth-child(3){font-weight:600;}
        td:contains('Pending'){color:#dd6b20;}
        td:contains('Approved'){color:#2f855a;}
        td:contains('Rejected'){color:#e53e3e;}
        /* ==== PAGINATION ==== */
        .pagination{
            text-align:center; padding:25px; background:#f8fafc;
            border-top:1px solid #e2e8f0;
        }
        .pagination a,.pagination span{
            display:inline-block; min-width:44px; height:44px; margin:0 6px;
            border-radius:14px; line-height:44px; text-decoration:none;
            font-weight:500; color:#4a5568; transition:all .3s ease;
            border:1.5px solid transparent;
        }
        .pagination a:hover{
            background:#6366f1; color:#fff; border-color:#6366f1;
            transform:translateY(-3px); box-shadow:0 6px 15px rgba(99,102,241,.3);
        }
        .pagination .active{
            background:linear-gradient(90deg,#6366f1 0%,#8b5cf6 100%);
            color:#fff; font-weight:600; border:none;
            box-shadow:0 6px 18px rgba(99,102,241,.4);
        }
        /* ==== RESPONSIVE ==== */
        @media (max-width:992px){
            .search-form{flex-direction:column; align-items:stretch;}
            .form-group{min-width:100%;}
            .btn-search{margin-left:0; margin-top:10px;}
        }
        @media (max-width:600px){
            h2{font-size:22px; padding:20px 15px;}
            th,td{font-size:13px; padding:10px 6px;}
            .pagination a,.pagination span{min-width:38px; height:38px; line-height:38px; font-size:14px;}
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Event Participation List</h2>

    <!-- Search Form -->
    <form method="get" action="${pageContext.request.contextPath}/participations" class="search-form">
        <div class="form-group">
            <label>Volunteer Name</label>
            <input type="text" name="volunteerName" placeholder="Enter name..." value="${param.volunteerName}">
        </div>

        <div class="form-group">
            <label>Event</label>
            <select name="eventName">
                <option value="">-- Select Event --</option>
                <c:forEach var="e" items="${eventNames}">
                    <option value="${e}" ${param.eventName == e ? 'selected' : ''}>${e}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label>Status</label>
            <select name="status">
                <option value="">-- Status --</option>
                <c:forEach var="s" items="${statuses}">
                    <option value="${s}" ${param.status == s ? 'selected' : ''}>${s}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label>Approver</label>
            <select name="approverName">
                <option value="">-- Approver --</option>
                <c:forEach var="a" items="${approverNames}">
                    <option value="${a}" ${param.approverName == a ? 'selected' : ''}>${a}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label>From Date</label>
            <input type="date" name="fromDate" value="${param.fromDate}">
        </div>

        <div class="form-group">
            <label>To Date</label>
            <input type="date" name="toDate" value="${param.toDate}">
        </div>

        <button type="submit" class="btn-search">
            Search
        </button>
    </form>

    <!-- Data Table -->
    <div class="table-container">
        <table>
            <tr>
                <th>Volunteer</th>
                <th>Event</th>
                <th>Status</th>
                <th>Submission Date</th>
                <th>Approver</th>
            </tr>
            <c:forEach var="p" items="${participations}">
                <tr>
                    <td>${p.fullName}</td>
                    <td>${p.eventName}</td>
                    <td>${p.status}</td>
                    <td>${p.applicationDate}</td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty p.approvedByStaffName}">
                                ${p.approvedByStaffName}
                            </c:when>
                            <c:otherwise>
                                <span style="color:#e53e3e; font-style:italic;">Not approved yet</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <!-- Pagination (preserves all search params) -->
    <div class="pagination">
        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <span class="active">${i}</span>
                </c:when>
                <c:otherwise>
                    <a href="?page=${i}
                        &volunteerName=${param.volunteerName}
                        &eventName=${param.eventName}
                        &status=${param.status}
                        &approverName=${param.approverName}
                        &fromDate=${param.fromDate}
                        &toDate=${param.toDate}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>
</div>

</body>
</html>