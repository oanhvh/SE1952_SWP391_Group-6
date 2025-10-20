<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chi Tiết Tổ Chức</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    
    <style>
        :root {
            --primary-color: #007bff;
            --primary-hover: #0056b3;
            --secondary-color: #6c757d;
            --secondary-hover: #5a6268;
            --warning-color: #ffc107;
            --background-color: #f4f7f9; 
            --text-color: #333;
            --border-color: #e9ecef;
        }

        body { 
            font-family: 'Roboto', sans-serif; 
            margin: 0; 
            padding: 40px 20px;
            background: var(--background-color); 
            color: var(--text-color);
        }
        
        .detail-container {
            max-width: 800px;
            margin: 0 auto;
        }

        h2 { 
            color: var(--text-color); 
            font-weight: 700;
            margin-bottom: 25px;
            border-bottom: 3px solid var(--primary-color);
            padding-bottom: 10px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .info-card {
            background: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
            margin-bottom: 30px;
        }

        .data-grid {
            display: grid;
            grid-template-columns: 200px 1fr;
            gap: 15px;
        }
        
        .data-grid div {
            padding: 8px 0;
            border-bottom: 1px dashed var(--border-color);
        }
        
        .data-grid div:nth-child(even) {
            color: #555;
            font-weight: 500;
        }

        .data-grid div:nth-child(odd) {
            font-weight: bold;
            color: var(--primary-color);
            padding-left: 10px;
        }
        
        .data-grid div:nth-last-child(1),
        .data-grid div:nth-last-child(2) {
            border-bottom: none;
        }

        .action-group {
            margin-top: 20px;
            display: flex;
            gap: 10px;
        }
        
        a.btn-action {
            padding: 10px 20px; 
            text-decoration: none; 
            border: none;
            border-radius: 4px; 
            cursor: pointer; 
            display: inline-flex;
            align-items: center;
            font-weight: 500;
            transition: background 0.2s;
            color: #fff;
        }
        .btn-action i {
            margin-right: 8px;
        }

        .btn-back {
            background: var(--secondary-color);
        }
        .btn-back:hover { 
            background: var(--secondary-hover); 
        }

        .btn-edit {
            background: var(--warning-color); 
            color: #333;
        }
        .btn-edit:hover { 
            background: #e0a800; 
        }
        
        .not-found {
            padding: 20px;
            border: 1px solid #f5c6cb;
            background-color: #f8d7da;
            color: #721c24;
            border-radius: 4px;
            text-align: center;
        }
    </style>
</head>
<body>

<div class="detail-container">

    <h2><i class="fas fa-search"></i> Chi Tiết Tổ Chức</h2>

    <c:choose>
        <c:when test="${org != null}">
            <div class="info-card">
                <div class="data-grid">
                    <div><i class="fas fa-id-badge"></i> ID:</div>
                    <div>${org.orgID}</div>
                    
                    <div><i class="fas fa-building"></i> Tên Tổ Chức:</div>
                    <div>${org.orgName}</div>
                    
                    <div><i class="fas fa-tags"></i> Danh Mục:</div>
                    <div><c:out value="${org.category}" default="Không xác định" /></div>

                    <div><i class="fas fa-info-circle"></i> Mô Tả:</div>
                    <div>${org.description}</div>
                    
                    <div><i class="fas fa-phone"></i> Thông Tin Liên Hệ:</div>
                    <div>${org.contactInfo}</div>
                    
                    <div><i class="fas fa-map-marker-alt"></i> Địa Chỉ:</div>
                    <div>${org.address}</div>
                    
                    <div><i class="fas fa-calendar-alt"></i> Ngày Đăng Ký:</div>
                    <div>${org.registrationDate}</div>
                    
                    <div><i class="fas fa-user-tie"></i> Người Tạo:</div>
                    <div><c:out value="${org.createdByUser.fullName}" default="N/A"/></div>
                </div>
            </div>

            <div class="action-group">
                <a href="organization?action=edit&id=${org.orgID}" class="btn-action btn-edit">
                    <i class="fas fa-edit"></i> Chỉnh Sửa
                </a>
                
                <a href="organization?action=list" class="btn-action btn-back">
                    <i class="fas fa-arrow-left"></i> Quay Lại Danh Sách
                </a>
            </div>
            
        </c:when>
        <c:otherwise>
            <div class="not-found">
                <i class="fas fa-exclamation-triangle"></i> Rất tiếc, không tìm thấy tổ chức này.
            </div>
            
            <a href="organization?action=list" class="btn-action btn-back" style="margin-top: 20px;">
                <i class="fas fa-arrow-left"></i> Quay Lại Danh Sách
            </a>
        </c:otherwise>
    </c:choose>

</div>

</body>
</html>
