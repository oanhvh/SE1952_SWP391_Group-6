<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>Admin - Tạo tài khoản</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h3 class="mb-0">Tạo tài khoản (Admin / Manager)</h3>
    <a class="btn btn-outline-secondary" href="<%= request.getContextPath() %>/admin/dashboard">Về Dashboard</a>
  </div>

  <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
  <% } %>
  <% if (request.getAttribute("success") != null) { %>
    <div class="alert alert-success"><%= request.getAttribute("success") %></div>
  <% } %>

  <div class="row g-3">
    <div class="col-md-6">
      <div class="card h-100">
        <div class="card-body">
          <h5 class="card-title">Tạo tài khoản Admin</h5>
          <form method="post" action="<%= request.getContextPath() %>/admin/users/new">
            <input type="hidden" name="type" value="Admin" />
            <div class="mb-3">
              <label class="form-label">Username</label>
              <input class="form-control" name="username" required />
            </div>
            <div class="mb-3">
              <label class="form-label">Password</label>
              <input type="password" class="form-control" name="password" required />
            </div>
            <div class="mb-3">
              <label class="form-label">Họ tên</label>
              <input class="form-control" name="fullName" />
            </div>
            <div class="mb-3">
              <label class="form-label">Email</label>
              <input type="email" class="form-control" name="email" />
            </div>
            <div class="mb-3">
              <label class="form-label">Điện thoại</label>
              <input class="form-control" name="phone" />
            </div>
            <button class="btn btn-primary" type="submit">Tạo Admin</button>
          </form>
        </div>
      </div>
    </div>

    <div class="col-md-6">
      <div class="card h-100">
        <div class="card-body">
          <h5 class="card-title">Tạo tài khoản Manager</h5>
          <form method="post" action="<%= request.getContextPath() %>/admin/users/new">
            <input type="hidden" name="type" value="Manager" />
            <div class="mb-3">
              <label class="form-label">Username</label>
              <input class="form-control" name="username" required />
            </div>
            <div class="mb-3">
              <label class="form-label">Password</label>
              <input type="password" class="form-control" name="password" required />
            </div>
            <div class="mb-3">
              <label class="form-label">Họ tên</label>
              <input class="form-control" name="fullName" />
            </div>
            <div class="mb-3">
              <label class="form-label">Email</label>
              <input type="email" class="form-control" name="email" />
            </div>
            <div class="mb-3">
              <label class="form-label">Điện thoại</label>
              <input class="form-control" name="phone" />
            </div>
            <hr />
            <div class="mb-3">
              <label class="form-label">Tên đơn vị/Manager</label>
              <input class="form-control" name="managerName" required />
            </div>
            <div class="mb-3">
              <label class="form-label">Thông tin liên hệ</label>
              <input class="form-control" name="managerContact" />
            </div>
            <div class="mb-3">
              <label class="form-label">Địa chỉ</label>
              <input class="form-control" name="managerAddress" />
            </div>
            <button class="btn btn-primary" type="submit">Tạo Manager</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
