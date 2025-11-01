<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách Quản lý</title>
    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    
    <style>
        /* Định nghĩa các biến màu để dễ dàng thay đổi */
        :root {
            --primary-color: #6a5af9; /* Tím hiện đại */
            --primary-hover: #5a4ae3;
            --secondary-color: #20c997; /* Xanh lá cây */
            --secondary-hover: #1ba980;
            --danger-color: #dc3545;
            --warning-color: #ffc107;
            --info-color: #0dcaf0;
            --light-bg: #f4f7f6;
            --white-color: #ffffff;
            --text-color: #495057;
            --text-light: #888;
            --border-color: #dee2e6;
            --shadow: 0 6px 12px rgba(0,0,0,0.05);
            --border-radius: 10px;
        }

        body {
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
            background-color: var(--light-bg);
            margin: 0;
            padding: 30px;
            color: var(--text-color);
            line-height: 1.6;
        }

        /* Thẻ bao bọc chính */
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: var(--white-color);
            border-radius: var(--border-radius);
            box-shadow: var(--shadow);
            padding: 30px;
        }

        h2 {
            text-align: center;
            color: #333;
            font-weight: 600;
            margin-bottom: 30px;
        }
        
        h2 .fas { /* Icon cho tiêu đề */
            color: var(--primary-color);
            margin-right: 10px;
            font-size: 0.9em;
        }

        /* Thanh công cụ (Tìm kiếm & Nút Thêm) */
        .toolbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-wrap: wrap; /* Cho phép xuống hàng trên di động */
            gap: 20px;
            margin-bottom: 30px;
        }

        .search-form {
            display: flex;
            gap: 10px;
        }
        
        /* Nút chung */
        .btn {
            display: inline-flex;
            align-items: center;
            gap: 8px; /* Khoảng cách giữa icon và chữ */
            padding: 10px 18px;
            border: none;
            border-radius: 8px;
            color: var(--white-color);
            cursor: pointer;
            font-size: 14px;
            font-weight: 500;
            text-decoration: none;
            transition: all 0.3s ease;
        }
        
        .btn:hover {
            transform: translateY(-2px);
            opacity: 0.9;
        }

        .btn-primary { background-color: var(--primary-color); }
        .btn-primary:hover { background-color: var(--primary-hover); }
        
        .btn-success { background-color: var(--secondary-color); }
        .btn-success:hover { background-color: var(--secondary-hover); }
        
        /* Ô tìm kiếm */
        input[type="text"] {
            padding: 10px 15px;
            border-radius: 8px;
            border: 1px solid var(--border-color);
            width: 280px;
            font-size: 14px;
            font-family: 'Inter', sans-serif;
            transition: border-color 0.3s, box-shadow 0.3s;
        }

        input[type="text"]:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 3px rgba(106, 90, 249, 0.1);
            outline: none;
        }

        /* Bảng dữ liệu */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #f0f0f0;
            vertical-align: middle;
        }

        th {
            background-color: #f8f9fa;
            color: #555;
            font-weight: 600;
            text-transform: uppercase;
            font-size: 12px;
            letter-spacing: 0.5px;
        }
        
        th .fas { /* Icon cho tiêu đề bảng */
            font-size: 13px;
            margin-right: 6px;
            color: var(--text-light);
        }

        tbody tr:hover {
            background-color: #fcfcfc;
        }
        
        tbody tr:last-child td {
            border-bottom: none;
        }

        /* Nút hành động trong bảng */
        .actions {
            white-space: nowrap; /* Ngăn các nút bị vỡ xuống hàng */
        }
        
        .btn-action {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            text-decoration: none;
            margin-right: 5px;
            padding: 6px 12px;
            border-radius: 6px;
            color: var(--white-color);
            font-size: 13px;
            font-weight: 500;
            gap: 5px;
            transition: opacity 0.3s ease;
        }
        .btn-action:hover {
            opacity: 0.8;
            color: var(--white-color);
        }
        
        .btn-view { background-color: var(--info-color); }
        .btn-edit { background-color: var(--warning-color); }
        .btn-delete { background-color: var(--danger-color); }

        /* Phân trang */
        .pagination {
            text-align: center;
            margin-top: 30px;
        }

        .pagination a {
            display: inline-block;
            padding: 8px 14px;
            margin: 0 4px;
            border-radius: 6px;
            text-decoration: none;
            background-color: #eee;
            color: #555;
            font-weight: 500;
            transition: background-color 0.3s, color 0.3s;
        }

        .pagination a.active {
            background-color: var(--primary-color);
            color: var(--white-color);
        }

        .pagination a:hover:not(.active) {
            background-color: #ddd;
        }

        /* Khi không có dữ liệu */
        .no-data {
            text-align: center;
            color: var(--text-light);
            padding: 40px 0;
            font-size: 18px;
            font-weight: 500;
        }
        .no-data .fas {
            font-size: 24px;
            display: block;
            margin-bottom: 15px;
            color: var(--text-light);
        }
    </style>
</head>
<body>

<div class="container">
    <h2><i class="fas fa-users-cog"></i> Danh sách Quản lý tổ chức</h2>

    <div class="toolbar">
        <form action="manager" method="get" class="search-form">
            <input type="hidden" name="action" value="search">
            <input type="text" name="keyword" value="${keyword}" placeholder="Nhập tên quản lý...">
            <button type="submit" class="btn btn-primary">
                <i class="fas fa-search"></i> Tìm kiếm
            </button>
        </form>
        <a href="manager?action=add" class="btn btn-success">
            <i class="fas fa-plus"></i> Thêm Quản lý mới
        </a>
    </div>

    <c:choose>
        <c:when test="${not empty listManagers}">
            <table>
                <thead>
                    <tr>
                        <th><i class="fas fa-id-badge"></i> ID</th>
                        <th><i class="fas fa-user"></i> Tên Quản lý</th>
                        <th><i class="fas fa-phone-alt"></i> Liên hệ</th>
                        <th><i class="fas fa-map-marker-alt"></i> Địa chỉ</th>
                        <th><i class="fas fa-calendar-alt"></i> Ngày đăng ký</th>
                        <th><i class="fas fa-bolt"></i> Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="m" items="${listManagers}">
                        <tr>
                            <td>${m.managerId}</td>
                            <td>${m.managerName}</td>
                            <td>${m.contactInfo}</td>
                            <td>${m.address}</td>
                            <td>${m.registrationDate}</td>
                            <td class="actions">
                                <a href="manager?action=detail&id=${m.managerId}" class="btn-action btn-view">
                                    <i class="fas fa-eye"></i> Xem
                                </a>
                                <a href="manager?action=edit&id=${m.managerId}" class="btn-action btn-edit">
                                    <i class="fas fa-pencil-alt"></i> Sửa
                                </a>
                                <a href="manager?action=delete&id=${m.managerId}" class="btn-action btn-delete"
                                   onclick="return confirm('Bạn có chắc muốn xóa quản lý này không?');">
                                   <i class="fas fa-trash-alt"></i> Xóa
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="pagination">
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <c:choose>
                        <c:when test="${isSearch}">
                            <a href="manager?action=search&keyword=${keyword}&page=${i}"
                               class="${i == currentPage ? 'active' : ''}">${i}</a>
                        </c:when>
                        <c:otherwise>
                            <a href="manager?action=list&page=${i}"
                               class="${i == currentPage ? 'active' : ''}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>

        </c:when>
        <c:otherwise>
            <p class="no-data">
                <i class="fas fa-info-circle"></i>
                Không tìm thấy dữ liệu phù hợp.
            </p>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>