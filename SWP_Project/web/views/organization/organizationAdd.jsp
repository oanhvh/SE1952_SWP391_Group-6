<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thêm Tổ Chức Mới</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <style>
        :root {
            --primary-color: #007bff;
            --primary-hover: #0056b3;
            --secondary-color: #6c757d;
            --secondary-hover: #5a6268;
            --background-color: #f4f7f9;
            --text-color: #333;
            --border-color: #ced4da;
            --error-color: #dc3545;
        }

        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            padding: 40px 20px;
            background: var(--background-color);
            color: var(--text-color);
        }

        .form-container {
            background: #fff;
            padding: 30px;
            border-radius: 8px;
            max-width: 600px;
            margin: auto;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        h2 {
            color: var(--primary-color);
            font-weight: 700;
            text-align: center;
            margin-bottom: 25px;
            border-bottom: 2px solid var(--primary-color);
            padding-bottom: 10px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: 500;
            color: #555;
        }

        input[type=text], textarea {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid var(--border-color);
            border-radius: 4px;
            box-sizing: border-box;
            transition: border-color 0.2s, box-shadow 0.2s;
        }

        input[type=text]:focus, textarea:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
            outline: none;
        }

        .button-group {
            margin-top: 25px;
            display: flex;
            justify-content: flex-end;
            gap: 10px;
        }

        input[type=submit], .btn-action {
            padding: 10px 20px;
            text-decoration: none;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            display: inline-flex;
            align-items: center;
            font-weight: 500;
            transition: background 0.2s;
        }

        input[type=submit] {
            background: var(--primary-color);
            color: #fff;
        }
        input[type=submit]:hover {
            background: var(--primary-hover);
        }

        .btn-cancel {
            background: var(--secondary-color);
            color: #fff;
        }
        .btn-cancel:hover {
            background: var(--secondary-hover);
        }

        .btn-action i {
            margin-right: 5px;
        }

        .error {
            color: var(--error-color);
            padding: 10px;
            margin-top: 15px;
            border: 1px solid #f5c6cb;
            background-color: #f8d7da;
            border-radius: 4px;
            font-weight: 500;
        }
    </style>
</head>
<body>

<div class="form-container">

    <h2><i class="fas fa-sitemap"></i> Thêm Tổ Chức Mới</h2>

    <form action="organization" method="post">
        <input type="hidden" name="action" value="add">

        <div class="form-group">
            <label for="orgName"><i class="fas fa-building"></i> Tên Tổ Chức (*):</label>
            <input type="text" id="orgName" name="orgName" value="${orgName}" required placeholder="Nhập tên tổ chức...">
        </div>

        <div class="form-group">
            <label for="category"><i class="fas fa-tags"></i> Danh Mục:</label>
            <input type="text" id="category" name="category" value="${category}" placeholder="Ví dụ: Tổ chức môi trường, Nhóm thiện nguyện...">
        </div>

        <div class="form-group">
            <label for="description"><i class="fas fa-info-circle"></i> Mô Tả:</label>
            <textarea id="description" name="description" rows="4" placeholder="Mô tả về tổ chức...">${description}</textarea>
        </div>

        <div class="form-group">
            <label for="contactInfo"><i class="fas fa-phone"></i> Thông Tin Liên Hệ:</label>
            <input type="text" id="contactInfo" name="contactInfo" value="${contactInfo}" placeholder="Email hoặc số điện thoại...">
        </div>

        <div class="form-group">
            <label for="address"><i class="fas fa-map-marker-alt"></i> Địa Chỉ:</label>
            <input type="text" id="address" name="address" value="${address}" placeholder="Địa chỉ trụ sở chính...">
        </div>

        <c:if test="${not empty error}">
            <p class="error"><i class="fas fa-times-circle"></i> ${error}</p>
        </c:if>

        <div class="button-group">
            <a href="organization?action=list" class="btn-action btn-cancel">
                <i class="fas fa-times"></i> Hủy Bỏ
            </a>
            <input type="submit" value="Thêm Tổ Chức" class="btn-action">
        </div>
    </form>

</div>

</body>
</html>
