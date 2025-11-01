<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết Quản lý</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    
    <style>
        /* Biến màu đồng nhất với các trang khác */
        :root {
            --primary-color: #6a5af9;
            --primary-hover: #5a4ae3;
            --secondary-btn-bg: #f8f9fa;
            --secondary-btn-text: #555;
            --secondary-btn-hover: #e9ecef;
            --warning-color: #ffc107;
            --danger-color: #dc3545;
            --light-bg: #f4f7f6;
            --white-color: #ffffff;
            --text-color: #495057;
            --text-light: #888;
            --border-color: #dee2e6;
            --border-light: #f0f0f0;
            --shadow: 0 6px 12px rgba(0,0,0,0.05);
            --border-radius: 10px;
        }

        body {
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
            background-color: var(--light-bg);
            margin: 0;
            padding: 40px;
            color: var(--text-color);
            line-height: 1.6;
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
            box-sizing: border-box;
        }

        /* Thẻ bao bọc chi tiết */
        .detail-card {
            width: 100%;
            max-width: 700px;
            margin: 0 auto;
            background-color: var(--white-color);
            border-radius: var(--border-radius);
            box-shadow: var(--shadow);
            padding: 35px 40px;
        }

        h2 {
            text-align: center;
            color: #333;
            font-weight: 600;
            margin-top: 0;
            margin-bottom: 30px;
        }
        
        h2 .fas { /* Icon cho tiêu đề */
            color: var(--primary-color);
            margin-right: 10px;
        }

        /* Danh sách thông tin */
        .info-list {
            margin-bottom: 30px;
        }
        
        .info-item {
            display: flex;
            flex-wrap: wrap; /* Cho phép xuống hàng trên di động */
            justify-content: space-between;
            align-items: center;
            padding: 16px 0;
            border-bottom: 1px solid var(--border-light);
        }
        
        .info-item:last-child {
            border-bottom: none;
        }
        
        .info-item .label {
            font-weight: 600;
            color: #333;
            font-size: 15px;
            display: flex;
            align-items: center;
            gap: 10px; /* Khoảng cách giữa icon và chữ */
            margin-right: 20px;
        }
        
        .info-item .label .fas {
            color: var(--primary-color);
            font-size: 16px;
            width: 20px; /* Căn chỉnh icon thẳng hàng */
            text-align: center;
        }

        .info-item .value {
            font-size: 15px;
            color: #555;
            text-align: right;
            word-break: break-word; /* Tránh vỡ layout nếu nội dung quá dài */
        }

        /* Nút chung */
        .btn {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            padding: 10px 18px;
            border: none;
            border-radius: 8px;
            color: var(--white-color);
            cursor: pointer;
            font-size: 15px;
            font-weight: 500;
            text-decoration: none;
            transition: all 0.3s ease;
        }
        
        .btn:hover {
            transform: translateY(-2px);
            opacity: 0.9;
        }

        /* Nút quay lại (thứ cấp) */
        .btn-secondary {
            background-color: var(--secondary-btn-bg);
            color: var(--secondary-btn-text);
            border: 1px solid var(--border-color);
        }
        .btn-secondary:hover { 
            background-color: var(--secondary-btn-hover); 
            color: var(--secondary-btn-text);
        }

        .btn-warning { 
            background-color: var(--warning-color); 
            color: var(--white-color); /* Đổi màu chữ thành trắng cho dễ đọc */
        }
        .btn-warning:hover { opacity: 0.9; }
        
        .btn-danger { 
            background-color: var(--danger-color); 
            color: var(--white-color);
        }
        .btn-danger:hover { opacity: 0.9; }

        /* Khu vực nút hành động */
        .actions {
            display: flex;
            justify-content: center;
            gap: 15px;
            margin-top: 30px;
            flex-wrap: wrap; /* Cho phép xuống hàng trên di động */
        }
        
        /* Khi không tìm thấy dữ liệu */
        .error-card {
            text-align: center;
        }
        .error-card h2 .fas {
            color: var(--danger-color);
        }
        .error-message {
            font-size: 16px;
            color: var(--danger-color);
            font-weight: 500;
            margin: 20px 0 30px;
        }

    </style>
</head>
<body>

    <c:choose>
        <c:when test="${manager != null}">
            <div class="detail-card">
                <h2><i class="fas fa-id-card-alt"></i> Chi tiết Quản lý</h2>
                
                <div class="info-list">
                    <div class="info-item">
                        <div class="label"><i class="fas fa-id-badge"></i> Mã Quản lý (ID):</div>
                        <div class="value">${manager.managerId}</div>
                    </div>
    
                    <div class="info-item">
                        <div class="label"><i class="fas fa-user-tie"></i> Tên Quản lý:</div>
                        <div class="value">${manager.managerName}</div>
                    </div>
    
                    <div class="info-item">
                        <div class="label"><i class="fas fa-phone-alt"></i> Liên hệ:</div>
                        <div class="value">${manager.contactInfo}</div>
                    </div>
    
                    <div class="info-item">
                        <div class="label"><i class="fas fa-map-marker-alt"></i> Địa chỉ:</div>
                        <div class="value">${manager.address}</div>
                    </div>
    
                    <div class="info-item">
                        <div class="label"><i class="fas fa-calendar-check"></i> Ngày đăng ký:</div>
                        <div class="value">
                            <c:if test="${manager.registrationDate != null}">
                                ${manager.registrationDate}
                            </c:if>
                            <c:if test="${manager.registrationDate == null}">-</c:if>
                        </div>
                    </div>
    
                    <div class="info-item">
                        <div class="label"><i class="fas fa-user-plus"></i> Người tạo (UserID):</div>
                        <div class="value">
                            <c:if test="${manager.createdByUserId != null}">
                                ${manager.createdByUserId}
                            </c:if>
                            <c:if test="${manager.createdByUserId == null}">-</c:if>
                        </div>
                    </div>
                </div>

                <div class="actions">
                    <a href="manager?action=list" class="btn btn-secondary">
                        <i class="fas fa-arrow-left"></i> Quay lại
                    </a>
                    <a href="manager?action=edit&id=${manager.managerId}" class="btn btn-warning">
                        <i class="fas fa-pencil-alt"></i> Chỉnh sửa
                    </a>
                    <a href="manager?action=delete&id=${manager.managerId}" class="btn btn-danger"
                       onclick="return confirm('Bạn có chắc muốn xóa quản lý này không?');">
                       <i class="fas fa-trash-alt"></i> Xóa
                    </a>
                </div>
            </div>
        </c:when>

        <c:otherwise>
            <div class="detail-card error-card">
                <h2><i class="fas fa-exclamation-triangle"></i> Không tìm thấy</h2>
                <p class="error-message">Không tìm thấy thông tin người quản lý!</p>
                <div class="actions">
                    <a href="manager?action=list" class="btn btn-secondary">
                        <i class="fas fa-arrow-left"></i> Quay lại danh sách
                    </a>
                </div>
            </div>
        </c:otherwise>
    </c:choose>

</body>
</html>