<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Admin Dashboard</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <div class="container py-4">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3 class="mb-0">Admin Dashboard</h3>
                <a class="btn btn-outline-secondary" href="<%= request.getContextPath() %>/logout">Sign out</a>
            </div>

            <div class="row g-3">
                <div class="col-md-4">
                    <div class="card h-100 shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Tạo tài khoản</h5>
                            <p class="card-text text-muted">Tạo Admin.</p>
                            <a href="<%= request.getContextPath() %>/admin/admin_create.jsp" class="btn btn-primary">Tạo Admin</a>
                        </div>
                    </div>
                </div>

                <div class="row g-3">
                    <div class="col-md-4">
                        <div class="card h-100 shadow-sm">
                            <div class="card-body">
                                <h5 class="card-title">Tạo tài khoản</h5>
                                <p class="card-text text-muted">Tạo Manager.</p>
                                <a href="<%= request.getContextPath() %>/admin/manager_create.jsp" class="btn btn-primary">Tạo Manager</a>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="card h-100 shadow-sm">
                            <div class="card-body">
                                <h5 class="card-title">Quản lý Skills</h5>
                                <p class="card-text text-muted">Tạo, sửa, xóa kỹ năng.</p>
                                <a href="<%= request.getContextPath() %>/admin/skills_list.jsp" class="btn btn-primary">Mở trang Skills</a>
                            </div>
                        </div>
                    </div>

                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
                    </body>
                    </html>
