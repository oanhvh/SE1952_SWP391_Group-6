<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Access Denied</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .card {
            max-width: 420px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }
        .error-icon {
            font-size: 60px;
            color: #dc3545;
        }
    </style>
</head>
<body>

<div class="card text-center p-4">
    <div class="mb-3">
        <i class="bi bi-exclamation-triangle-fill error-icon"></i>
    </div>
    <h3 class="text-danger">Access Denied</h3>
    <p class="text-muted mt-2 mb-4">
        You don’t have permission to access this page.<br>
        Please contact the administrator if you believe this is a mistake.
    </p>
    <div>
        <a href="<%= request.getContextPath() %>/login.jsp" class="btn btn-primary px-4">Back to Login</a>
    </div>
</div>

<!-- Bootstrap icons -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

</body>
</html>
