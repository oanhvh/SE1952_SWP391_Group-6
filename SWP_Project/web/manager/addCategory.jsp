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
        <link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="../css/style.css" />
        <link rel="stylesheet" href="../css/responsive.css" />
        <link rel="icon" href="../images/fevicon.png" type="image/gif" />
        <link rel="stylesheet" href="../css/jquery.mCustomScrollbar.min.css" />
        <link rel="stylesheet" href="../css/sidebar.css" />
        <link rel="stylesheet" href="../css/addCategory.css" />
    </head>
    <body>
        <jsp:include page="includes/header.jsp" />
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
        <jsp:include page="includes/footer.jsp" />

        <script src="../js/jquery.min.js"></script>
        <script src="../js/popper.min.js"></script>
        <script src="../js/bootstrap.bundle.min.js"></script>
        <script src="../js/role.js?v=2"></script>
    </body>
</html>
