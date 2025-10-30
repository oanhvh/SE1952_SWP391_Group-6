<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm Quản lý mới</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    
    <style>
        /* Biến màu đồng nhất với trang danh sách */
        :root {
            --primary-color: #6a5af9; /* Tím hiện đại */
            --primary-hover: #5a4ae3;
            --secondary-btn-bg: #f8f9fa;
            --secondary-btn-text: #555;
            --secondary-btn-hover: #e9ecef;
            --light-bg: #f4f7f6;
            --white-color: #ffffff;
            --text-color: #495057;
            --text-light: #888;
            --border-color: #dee2e6;
            --shadow: 0 6px 12px rgba(0,0,0,0.05);
            --border-radius: 10px;
        }

        body {
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
            background-color: var(--light-bg);
            margin: 0;
            padding: 40px;
            color: var(--text-color);
            line-height: 1.6;
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
            box-sizing: border-box;
        }

        /* Thẻ bao bọc form */
        .form-container {
            width: 100%;
            max-width: 650px;
            margin: 0 auto;
            background-color: var(--white-color);
            border-radius: var(--border-radius);
            box-shadow: var(--shadow);
            padding: 35px 40px;
        }

        h2 {
            text-align: center;
            color: #333;
            font-weight: 600;
            margin-top: 0;
            margin-bottom: 30px;
        }
        
        h2 .fas { /* Icon cho tiêu đề */
            color: var(--primary-color);
            margin-right: 10px;
        }
        
        /* Nhóm một cặp label + input */
        .form-group {
            margin-bottom: 20px;
        }
        
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            font-size: 14px;
            color: #333;
        }

        /* Thiết kế chung cho input, textarea */
        .form-control {
            width: 100%;
            padding: 12px 15px;
            border-radius: 8px;
            border: 1px solid var(--border-color);
            font-size: 15px;
            font-family: 'Inter', sans-serif;
            transition: border-color 0.3s, box-shadow 0.3s;
            box-sizing: border-box; /* Rất quan trọng */
        }

        .form-control:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 3px rgba(106, 90, 249, 0.1);
            outline: none;
        }
        
        textarea.form-control {
            resize: vertical;
            min-height: 100px;
        }
        
        /* Nút chung */
        .btn {
            display: inline-flex;
            align-items: center;
            gap: 8px; /* Khoảng cách giữa icon và chữ */
            padding: 10px 18px;
            border: none;
            border-radius: 8px;
            color: var(--white-color);
            cursor: pointer;
            font-size: 15px;
            font-weight: 500;
            text-decoration: none;
            transition: all 0.3s ease;
        }
        
        .btn:hover {
            transform: translateY(-2px);
            opacity: 0.9;
        }

        .btn-primary { 
            background-color: var(--primary-color); 
            color: var(--white-color);
        }
        .btn-primary:hover { background-color: var(--primary-hover); }
        
        /* Nút quay lại (thứ cấp) */
        .btn-secondary {
            background-color: var(--secondary-btn-bg);
            color: var(--secondary-btn-text);
            border: 1px solid var(--border-color);
        }
        .btn-secondary:hover { 
            background-color: var(--secondary-btn-hover); 
            color: var(--secondary-btn-text); /* Đảm bảo chữ không bị trắng */
        }
        
        /* Khu vực chứa các nút (Lưu, Quay lại) */
        .form-actions {
            display: flex;
            justify-content: flex-start;
            gap: 15px;
            margin-top: 30px;
            flex-wrap: wrap; /* Cho phép xuống hàng trên di động */
        }
        
    </style>
</head>
<body>

    <div class="form-container">
        <h2><i class="fas fa-user-plus"></i> Thêm Quản lý Mới</h2>

        <form action="manager?action=insert" method="post">
            
            <div class="form-group">
                <label for="managerName">Tên Quản lý:</label>
                <input type="text" id="managerName" name="managerName" class="form-control" required>
            </div>
            
            <div class="form-group">
                <label for="contactInfo">Liên hệ (Email hoặc SĐT):</label>
                <input type="text" id="contactInfo" name="contactInfo" class="form-control" placeholder="VD: manager@gmail.com">
            </div>

            <div class="form-group">
                <label for="address">Địa chỉ:</label>
                <textarea id="address" name="address" rows="4" class="form-control" placeholder="Nhập địa chỉ tổ chức..."></textarea>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save"></i> Lưu
                </button>
                <a href="manager?action=list" class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i> Quay lại danh sách
                </a>
            </div>
            
        </form>
    </div>

</body>
</html>