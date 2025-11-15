<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Change password</title>
        <link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="../css/style.css" />
        <link rel="stylesheet" href="../css/responsive.css" />
        <link rel="icon" href="../images/fevicon.png" type="image/gif" />
        <link rel="stylesheet" href="../css/jquery.mCustomScrollbar.min.css" />
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" />
        <link rel="stylesheet" href="../css/owl.carousel.min.css" />
        <link rel="stylesheet" href="../css/owl.theme.default.min.css" />
        <link rel="stylesheet" href="../css/sidebar.css" />
        <link rel="stylesheet" href="../css/change_password.css" />
    </head>
    <body class="change-password-page">
        <jsp:include page="includes/header.jsp" />
        <div class="container py-5">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <h3 class="mb-4">Change password</h3>

                            <% String error = (String) request.getAttribute("error"); %>
                            <% String success = (String) request.getAttribute("success"); %>
                            <% if (error != null) { %>
                            <div class="alert alert-danger" role="alert"><%= error %></div>
                            <% } %>
                            <% if (success != null) { %>
                            <div class="alert alert-success" role="alert"><%= success %></div>
                            <% } %>

                            <%
                                String forwardPathJakarta = (String) request.getAttribute("jakarta.servlet.forward.servlet_path");
                                String forwardPathJavax = (String) request.getAttribute("javax.servlet.forward.servlet_path");
                                String actionPath = forwardPathJakarta != null ? forwardPathJakarta : forwardPathJavax;
                                if (actionPath == null) {
                                    String sp = request.getServletPath(); 
                                    if (sp != null && sp.endsWith("/change_password.jsp")) {
                                        actionPath = sp.substring(0, sp.length() - ".jsp".length())
                                                         .replace("_password", "-password");
                                    } else {
                                        actionPath = sp != null ? sp : "/volunteer/change-password";
                                    }
                                }
                            %>
                            <form method="post" action="<%= request.getContextPath() + actionPath %>">
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
                                <div class="d-flex justify-content-center gap-3">
                                    <button type="submit" class="btn btn-primary">Change password</button>
                                    <a class="btn btn-outline-secondary" href="<%= request.getContextPath() %>/volunteer/index_1.jsp">Home page</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="includes/footer.jsp" />
        <script src="../js/jquery.min.js"></script>
        <script src="../js/popper.min.js"></script>
        <script src="../js/bootstrap.bundle.min.js"></script>
        <script src="../js/jquery-3.0.0.min.js"></script>
        <script src="../js/plugin.js"></script>
        <script src="../js/role.js?v=2"></script>
        <script src="../js/owl.carousel.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.js"></script>
        <script src="../js/login.js"></script>
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
