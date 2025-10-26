<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Registration Successful</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
  <div class="container py-5">
    <div class="row justify-content-center">
      <div class="col-md-7">
        <div class="card shadow-sm">
          <div class="card-body p-4">
            <h3 class="card-title mb-3">Registration Successful</h3>
            <p class="mb-4">
              ${success != null ? success : 'Your account has been created successfully.'}
            </p>
            <a class="btn btn-primary" href="<%= request.getContextPath() %>/login">Go to Login</a>
          </div>
        </div>
      </div>
    </div>
  </div>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
