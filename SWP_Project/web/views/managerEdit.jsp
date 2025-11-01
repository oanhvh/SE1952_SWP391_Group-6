<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa Quản lý</title>
    <style>
        body {
            font-family: "Segoe UI", sans-serif;
            background-color: #f9fafb;
            margin: 0;
            padding: 40px;
        }
        h2 {
            text-align: center;
            color: #ffb703;
        }
        form {
            max-width: 600px;
            margin: 0 auto;
            background-color: white;
            padding: 25px 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        label {
            display: block;
            margin-top: 15px;
            font-weight: 600;
        }
        input[type=text],
        textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            margin-top: 5px;
            box-sizing: border-box;
        }
        button {
            margin-top: 25px;
            padding: 10px 18px;
            border: none;
            border-radius: 6px;
            background-color: #ffb703;
            color: black;
            font-size: 15px;
            cursor: pointer;
        }
        button:hover {
            background-color: #fca311;
        }
        .back-link {
            display: inline-block;
            margin-top: 20px;
            color: #0078D7;
            text-decoration: none;
        }
    </style>
</head>
<body>

    <h2>✏️ Chỉnh sửa Quản lý</h2>

    <form action="manager?action=update" method="post">
        <input type="hidden" name="managerId" value="${manager.managerId}">

        <label>Tên Quản lý:</label>
        <input type="text" name="managerName" value="${manager.managerName}" required>

        <label>Liên hệ:</label>
        <input type="text" name="contactInfo" value="${manager.contactInfo}">

        <label>Địa chỉ:</label>
        <textarea name="address" rows="3">${manager.address}</textarea>

        <button type="submit">💾 Cập nhật</button>
        <a href="manager?action=list" class="back-link">⬅ Quay lại danh sách</a>
    </form>

</body>
</html>
