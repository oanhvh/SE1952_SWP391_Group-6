<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Đổi mật khẩu</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <jsp:include page="includes/header.jsp" />
        <div class="container py-5">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card shadow-sm">
                        <div class="card-body p-4">
                            <h3 class="mb-3">Đổi mật khẩu</h3>

                            <% String error = (String) request.getAttribute("error"); %>
                            <% String success = (String) request.getAttribute("success"); %>
                            <% if (error != null) { %>
                            <div class="alert alert-danger" role="alert"><%= error %></div>
                            <% } %>
                            <% if (success != null) { %>
                            <div class="alert alert-success" role="alert"><%= success %></div>
                            <% } %>

                            <form method="post" action="<%= request.getContextPath() + request.getServletPath() %>">
                                <div class="mb-3">
                                    <label for="currentPassword" class="form-label">Mật khẩu hiện tại</label>
                                    <input type="password" class="form-control" id="currentPassword" name="currentPassword" required>
                                </div>
                                <div class="mb-3">
                                    <label for="newPassword" class="form-label">Mật khẩu mới</label>
                                    <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                                    <div class="form-text">Tối thiểu 8 ký tự, gồm chữ hoa, chữ thường và số.</div>
                                </div>
                                <div class="mb-4">
                                    <label for="confirmPassword" class="form-label">Xác nhận mật khẩu mới</label>
                                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                                </div>
                                <div class="d-flex gap-2">
                                    <button type="submit" class="btn btn-primary">Đổi mật khẩu</button>
                                    <a class="btn btn-outline-secondary" href="<%= request.getContextPath() %>/manager/index_1.jsp">Trang quản lý</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="includes/footer.jsp" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
