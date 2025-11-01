<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registration Successful</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
    <link rel="stylesheet" href="css/login.css?v=20251029">
    <link rel="stylesheet" href="css/register_success.css?v=20251029">
  </head>
  <body class="login-page">
    <!-- Topbar -->
    <div class="topbar d-flex align-items-center">
      <div class="container d-flex justify-content-between align-items-center">
        <a class="brand text-decoration-none" href="index.html">
          <img src="images/logo.png" alt="logo"/>
          <span class="text-white">Register</span>
        </a>
      </div>
    </div>

    <!-- Success content -->
    <div class="login-wrap">
      <div class="container-xxl">
        <div class="row justify-content-center">
          <div class="col-12 col-lg-6 col-xl-5">
            <div class="card success-card">
              <div class="card-body p-4 p-md-5 text-center">
                <div class="success-icon mx-auto mb-3">
                  <i class="fa fa-check"></i>
                </div>
                <h3 class="mb-2">Registration Successful</h3>
                <p class="text-muted mb-4">
                  ${success != null ? success : 'Your account has been created successfully.'}
                </p>
                <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                  <a class="btn btn-primary px-4" href="<%= request.getContextPath() %>/login">Go to Login</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="site-footer py-4">
      <div class="container text-center small text-muted">© 2025 Your Organization. All rights reserved.</div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
