<%-- 
    Document   : addCategory
    Created on : Nov 2, 2025, 6:14:46 PM
    Author     : DucNM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Category</title>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
        <style>
            body {
                font-family: "Poppins", sans-serif;
                background-color: #f7f8fa;
                height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
            }
            .form-container {
                background-color: #fff;
                padding: 40px 45px;
                border-radius: 14px;
                box-shadow: 0 5px 18px rgba(0,0,0,0.1);
                width: 420px;
            }
            h2 {
                text-align: center;
                margin-bottom: 25px;
                font-weight: 600;
                color: #2c3e50;
                font-size: 26px;
            }
            label {
                display: block;
                margin-bottom: 8px;
                font-weight: 500;
                color: #555;
            }
            input[type="text"] {
                width: 100%;
                padding: 10px 14px;
                border: 1px solid #ccc;
                border-radius: 8px;
                transition: border-color 0.2s;
                font-size: 15px;
            }
            input[type="text"]:focus {
                border-color: #007bff;
                outline: none;
            }
            .error {
                color: #dc3545;
                font-size: 13px;
                margin-top: 5px;
            }
            .btn-container {
                display: flex;
                justify-content: space-between;
                margin-top: 25px;
            }
            .btn {
                padding: 10px 18px;
                border: none;
                border-radius: 8px;
                cursor: pointer;
                font-size: 15px;
                font-weight: 500;
                transition: background-color 0.2s;
            }
            .btn-submit {
                background-color: #28a745;
                color: white;
            }
            .btn-submit:hover {
                background-color: #218838;
            }
            .btn-cancel {
                background-color: #ccc;
                color: #333;
                text-decoration: none;
                padding: 10px 18px;
                text-align: center;
                border-radius: 8px;
            }
            .btn-cancel:hover {
                background-color: #b5b5b5;
            }
        </style>
    </head>

    <body>
        <div class="form-container">
            <h2>Add Category</h2>
            <form action="${pageContext.request.contextPath}/manager/category" method="post">
                <input type="hidden" name="action" value="add">

                <label for="categoryName">Category Name</label>
                <input type="text" id="categoryName" name="categoryName"
                       value="${categoryName != null ? categoryName : ''}" required>
                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>

                <div class="btn-container">
                    <button type="submit" class="btn btn-submit">Add</button>
                    <a href="${pageContext.request.contextPath}/manager/category?action=list" class="btn btn-cancel">Cancel</a>
                </div>
            </form>
        </div>
    </body>
</html>
