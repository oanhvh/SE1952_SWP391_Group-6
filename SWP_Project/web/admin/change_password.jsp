<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Change Password</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/header_admin_create.css" />
    <style>
      .input-icon .field { position: relative; }
      .input-icon .field .left-icon {
        position: absolute; left: 12px; top: 50%; transform: translateY(-50%);
        color: #6c757d; pointer-events: none;
      }
      .input-icon .field .toggle-eye {
        position: absolute; right: 12px; top: 50%; transform: translateY(-50%);
        color: #6c757d; cursor: pointer;
      }
      .input-icon .field input.form-control { padding-left: 2rem; padding-right: 2rem; }
      .password-strength { height: 6px; background: #e9ecef; border-radius: 4px; }
      .password-strength .progress-bar { transition: width .2s ease; }
    </style>
</head>
<body class="login-page">
    <div class="topbar d-flex align-items-center">
        <div class="container d-flex justify-content-between align-items-center">
            <a class="brand text-decoration-none" href="<%= request.getContextPath() %>/ListAccount">
                <img src="<%=request.getContextPath()%>/images/logo.png" alt="logo"/>
                <span class="text-white">Change Password</span>
            </a>
        </div>
    </div>

    <div class="container my-5">
      <div class="row justify-content-center">
        <div class="col-12 col-md-8 col-lg-5">
          <div class="card shadow-sm border-0">
            <div class="card-body p-4 p-md-5">
              <h3 class="mb-4 text-center">Change Password</h3>
              <% String error = (String) request.getAttribute("error"); %>
              <% String success = (String) request.getAttribute("success"); %>
              <% if (error != null) { %>
              <div class="alert alert-danger" role="alert"><%= error %></div>
              <% } %>
              <% if (success != null) { %>
              <div class="alert alert-success" role="alert"><%= success %></div>
              <% } %>

              <form method="post" action="<%= request.getContextPath() %>/admin/change-password">
        <div class="mb-3 input-icon">
            <label for="currentPassword" class="form-label">Current password</label>
            <div class="field">
                <i class="fa fa-lock left-icon"></i>
                <input type="password" class="form-control" id="currentPassword" name="currentPassword" placeholder="Enter current password" required>
                <i class="fa fa-eye toggle-eye" data-target="#currentPassword"></i>
            </div>
        </div>
        <div class="mb-3 input-icon">
            <label for="newPassword" class="form-label">New password</label>
            <div class="field">
                <i class="fa fa-key left-icon"></i>
                <input type="password" class="form-control" id="newPassword" name="newPassword" placeholder="Enter new password" required>
                <i class="fa fa-eye toggle-eye" data-target="#newPassword"></i>
            </div>
            <div class="form-text">Minimum 8 characters, including uppercase letters, lowercase letters, and numbers</div>
            <div class="progress password-strength mt-2"><div id="pwdStrength" class="progress-bar" style="width:0%"></div></div>
            <small id="pwdStrengthText" class="text-muted"></small>
        </div>
        <div class="mb-4 input-icon">
            <label for="confirmPassword" class="form-label">Confirm password</label>
            <div class="field">
                <i class="fa fa-check left-icon"></i>
                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Re-enter new password" required>
                <i class="fa fa-eye toggle-eye" data-target="#confirmPassword"></i>
            </div>
            <small id="confirmMsg" class="d-block mt-1"></small>
        </div>
        <div class="d-flex justify-content-center gap-3 flex-column flex-sm-row">
            <button type="submit" class="btn btn-primary btn-lg px-4">Change password</button>
            <a class="btn btn-outline-secondary btn-lg px-4" href="<%= request.getContextPath() %>/ListAccount">Home page</a>
        </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
<script>
    (function () {
        function scorePassword(p) {
            if (!p)
                return 0;
            let s = 0;
            var r = [/[a-z]/, /[A-Z]/, /\d/, /[^A-Za-z0-9]/];
            r.forEach(x => {
                if (x.test(p))
                    s++;
            });
            if (p.length >= 12)
                s++;
            else if (p.length >= 8)
                s += 0.5;
            return Math.min(5, s);
        }
        function renderStrength(p) {
            var bar = document.getElementById('pwdStrength');
            var txt = document.getElementById('pwdStrengthText');
            var sc = scorePassword(p);
            var widths = {0: 0, 1: 20, 2: 40, 3: 60, 4: 80, 5: 100};
            bar.style.width = widths[sc] + "%";
            bar.className = 'progress-bar';
            var label = 'Weak', cls = 'bg-danger';
            if (sc >= 4) {
                label = 'Strong';
                cls = 'bg-success';
            } else if (sc >= 3) {
                label = 'Medium';
                cls = 'bg-warning';
            }
            bar.classList.add(cls);
            txt.textContent = p ? (label) : '';
        }
        document.querySelectorAll('.toggle-eye').forEach(function (el) {
            el.addEventListener('click', function () {
                var t = document.querySelector(this.getAttribute('data-target'));
                if (!t)
                    return;
                var isPwd = t.type === 'password';
                t.type = isPwd ? 'text' : 'password';
                this.classList.toggle('fa-eye');
                this.classList.toggle('fa-eye-slash');
            });
        });
        var np = document.getElementById('newPassword');
        if (np) {
            np.addEventListener('input', function (e) {
                renderStrength(e.target.value);
            });
            renderStrength('');
        }
        var cp = document.getElementById('confirmPassword');
        var msg = document.getElementById('confirmMsg');
        function upd() {
            if (!cp.value) {
                msg.textContent = '';
                msg.className = '';
                return;
            }
            if (cp.value === np.value) {
                msg.textContent = 'The confirmation password matches';
                msg.className = 'text-success';
            } else {
                msg.textContent = 'The confirmation password does not match';
                msg.className = 'text-danger';
            }
        }
        if (np && cp) {
            np.addEventListener('input', upd);
            cp.addEventListener('input', upd);
        }
    })();
</script>
</body>
</html>
