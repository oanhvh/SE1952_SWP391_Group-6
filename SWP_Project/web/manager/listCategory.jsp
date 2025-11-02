<%-- 
    Document   : listCategory
    Created on : Nov 2, 2025, 6:15:19 PM
    Author     : DucNM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="entity.Category" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Category List</title>
        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
        <style>
            body {
                font-family: "Poppins", sans-serif;
                background-color: #f7f8fa;
                margin: 40px;
                color: #333;
            }
            h1 {
                font-size: 32px;
                text-align: center;
                margin-bottom: 25px;
                font-weight: 600;
                letter-spacing: 0.5px;
                color: #222;
            }
            .toolbar {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
                flex-wrap: wrap;
            }
            .toolbar .add-btn {
                background-color: #28a745;
                color: white;
                border: none;
                padding: 10px 18px;
                border-radius: 6px;
                font-size: 15px;
                cursor: pointer;
                transition: background-color 0.2s;
            }
            .toolbar .add-btn:hover {
                background-color: #218838;
            }
            #searchBox {
                padding: 8px 12px;
                width: 240px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 14px;
            }
            table.dataTable {
                border-collapse: collapse !important;
                border-radius: 8px;
                overflow: hidden;
                background-color: white;
                box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            }
            table.dataTable th {
                background-color: #007bff;
                color: white;
            }
            table.dataTable tbody tr:hover {
                background-color: #f1f1f1;
            }
            .delete-btn {
                color: white;
                background-color: #dc3545;
                border: none;
                padding: 6px 10px;
                border-radius: 5px;
                cursor: pointer;
            }
            .delete-btn:hover {
                background-color: #c82333;
            }
        </style>
    </head>

    <body>
        <h1>Category List</h1>

        <div class="toolbar">
            <input type="text" id="searchBox" placeholder="Search category name...">
            <button class="add-btn" onclick="window.location.href = '${pageContext.request.contextPath}/category?action=add'">
                + Add Category
            </button>
        </div>

        <table id="categoryTable" class="display">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Category Name</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="cat" items="${categories}">
                    <tr>
                        <td>${cat.categoryID}</td>
                        <td>${cat.categoryName}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/category" method="post" class="delete-form" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="categoryID" value="${cat.categoryID}">
                                <button type="submit" class="delete-btn">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <script>
            $(document).ready(function () {
                const table = $('#categoryTable').DataTable({
                    paging: true,
                    info: false,
                    searching: false,
                    lengthChange: false
                });

                $('#searchBox').on('keyup', function () {
                    table.column(1).search(this.value).draw();
                });

                $('.delete-form').on('submit', function (e) {
                    const confirmed = confirm('Are you sure you want to delete this category?');
                    if (!confirmed)
                        e.preventDefault();
                });
            });
        </script>
    </body>
</html>
