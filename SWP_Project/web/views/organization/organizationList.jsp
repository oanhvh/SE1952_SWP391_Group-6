<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Danh Sách Tổ Chức</title>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

        <style>
            :root {
                --primary-color: #007bff;
                --primary-hover: #0056b3;
                --success-color: #28a745;
                --background-color: #f4f7f9;
                --text-color: #333;
                --border-color: #dee2e6;
            }

            body {
                font-family: 'Roboto', sans-serif;
                margin: 0;
                padding: 20px;
                background: var(--background-color);
                color: var(--text-color);
            }

            .container {
                max-width: 1200px;
                margin: 0 auto;
                background: #fff;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
            }

            h2 {
                color: var(--text-color);
                font-weight: 700;
                margin-bottom: 0;
            }

            .top-bar {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
            }

            .btn {
                background: var(--primary-color);
                color: #fff !important;
                padding: 8px 15px;
                text-decoration: none;
                border-radius: 4px;
                border: none;
                cursor: pointer;
                transition: background 0.2s;
                display: inline-flex;
                align-items: center;
                font-weight: 500;
            }
            .btn i {
                margin-right: 5px;
            }
            .btn:hover {
                background: var(--primary-hover);
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
            }
            .btn-sm {
                padding: 4px 8px;
                font-size: 0.85em;
            }

            .search-form {
                display: flex;
                gap: 10px;
                margin-bottom: 20px;
            }
            .search-form input[type=text] {
                padding: 8px 12px;
                border: 1px solid var(--border-color);
                border-radius: 4px;
                flex-grow: 1;
                max-width: 400px;
            }
            .search-form input[type=submit] {
                background: var(--success-color);
                color: #fff;
                border: none;
                border-radius: 4px;
                padding: 8px 15px;
                cursor: pointer;
            }
            .search-form input[type=submit]:hover {
                background: #1e7e34;
            }

            table {
                width: 100%;
                border-collapse: separate;
                border-spacing: 0;
                margin-top: 10px;
            }

            th, td {
                padding: 12px 15px;
                border: none;
                border-bottom: 1px solid var(--border-color);
                text-align: left;
            }

            th {
                background: #e9ecef;
                color: #555;
                font-weight: 700;
                text-transform: uppercase;
                font-size: 0.9em;
            }

            tr:nth-child(even) {
                background-color: #f8f9fa;
            }
            tr:hover {
                background-color: #e2f2ff;
            }

            .pagination {
                margin-top: 25px;
                display: flex;
                justify-content: center;
                gap: 5px;
            }
            .pagination a {
                text-decoration: none;
                color: var(--primary-color);
                padding: 6px 12px;
                border: 1px solid var(--border-color);
                border-radius: 4px;
                transition: all 0.2s;
            }
            .pagination a:hover {
                background: var(--primary-color);
                color: #fff !important;
            }
            .pagination a.active {
                font-weight: bold;
                background: var(--primary-color);
                color: #fff !important;
                border-color: var(--primary-color);
            }

            .no-results {
                margin-top: 30px;
                padding: 20px;
                border: 1px solid #ffecb5;
                background: #fff3cd;
                color: #856404;
                border-radius: 4px;
                text-align: center;
            }
        </style>
    </head>
    <body>

        <div class="container">

            <div class="top-bar">
                <h2><i class="fas fa-sitemap" style="color: var(--primary-color);"></i> Danh Sách Tổ Chức</h2>
                <a href="organization?action=add" class="btn">
                    <i class="fas fa-plus"></i> Thêm Tổ Chức Mới
                </a>
            </div>

            <form action="organization" method="get" class="search-form" style="margin-bottom: 10px;">
                <input type="hidden" name="action" value="search">
                <input type="text" name="keyword" value="${keyword}" 
                       placeholder="🔍 Tìm theo Tên Tổ Chức, Mô tả, Địa chỉ hoặc Liên hệ..." 
                       style="border-radius: 20px; padding-left: 15px; flex-grow: 1;">
                <input type="submit" value="Tìm kiếm" class="btn" style="background: var(--primary-color);">
            </form>

            <form action="organization" method="get" class="search-form" style="margin-bottom: 20px;">
                <input type="hidden" name="action" value="search">
                <select name="categoryFilter" style="padding: 8px 12px; border-radius: 4px; border: 1px solid #ccc;">
                    <option value="">-- Lọc theo Danh mục --</option>
                    <option value="môi trường" ${categoryFilter == 'môi trường' ? 'selected' : ''}>Tổ chức Môi trường</option>
                    <option value="thiện nguyện" ${categoryFilter == 'thiện nguyện' ? 'selected' : ''}>Nhóm Thiện nguyện</option>
                    <option value="sức khỏe" ${categoryFilter == 'sức khỏe' ? 'selected' : ''}>Tổ chức Sức khỏe</option>
                    <option value="giáo dục" ${categoryFilter == 'giáo dục' ? 'selected' : ''}>Tổ chức Giáo dục</option>
                    <option value="doanh nghiệp" ${categoryFilter == 'doanh nghiệp' ? 'selected' : ''}>Doanh nghiệp xã hội</option>
                </select>
                <input type="submit" value="Lọc" class="btn" style="background: var(--success-color);">
            </form>


            <c:choose>
                <c:when test="${not empty orgList}">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Tên Tổ Chức</th>
                                <th>Danh Mục</th>
                                <th>Thông Tin Liên Hệ</th>
                                <th>Địa Chỉ</th>
                                <th>Người Tạo</th>
                                <th style="width: 100px; text-align: center;">Hành Động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="o" items="${orgList}">
                                <tr>
                                    <td>${o.orgID}</td>
                                    <td>${o.orgName}</td>
                                    <td><c:out value="${o.category}" default="-" /></td>
                                    <td>${o.contactInfo}</td>
                                    <td>${o.address}</td>
                                    <td><c:out value="${o.createdByUser.fullName}" default="N/A"/></td>
                                    <td style="text-align: center;">
                                        <a href="organization?action=details&id=${o.orgID}" class="btn btn-sm" style="background: var(--primary-color);">
                                            <i class="fas fa-eye"></i> Xem
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="no-results">
                        <i class="fas fa-exclamation-triangle"></i> 
                        Không tìm thấy tổ chức nào. Vui lòng thử từ khóa khác hoặc 
                        <a href="organization?action=add" style="color: var(--primary-color);">Thêm tổ chức mới</a>.
                    </div>
                </c:otherwise>
            </c:choose>

            <c:if test="${totalPages > 1}">
                <div class="pagination">
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <a href="organization?action=list&page=${i}" class="${i == currentPage ? 'active' : ''}">
                            ${i}
                        </a>
                    </c:forEach>
                </div>
            </c:if>

        </div>

    </body>
</html>
