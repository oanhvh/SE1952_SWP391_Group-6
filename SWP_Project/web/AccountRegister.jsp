<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Register Account</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
        <link rel="stylesheet" href="css/register.css?v=20251029" />
        <meta name="keywords" content="">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/style.css">
    </head>
    <body class="login-page">
        <!-- Topbar (reuse from login) -->
        <div class="topbar d-flex align-items-center">
            <div class="container d-flex justify-content-between align-items-center">
                <a class="brand text-decoration-none" href="index.html">
                    <img src="images/logo.png" alt="logo"/>
                    <span class="text-white">Register</span>
                </a>
            </div>
        </div>

        <!-- Content -->
        <div class="register-wrap">
            <div class="login-card container-xxl">
                <div class="row g-4 justify-content-center">
                    <div class="col-12 col-lg-6 col-xl-5 d-flex justify-content-center">
                        <div class="card card-login w-100">
                            <div class="card-header">
                                <div class="d-flex justify-content-between">
                                    <strong>Register</strong>
                                </div>
                            </div>
                            <div class="card-body">
                                <!-- Alerts -->
                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger">${error}</div>
                                </c:if>
                                <c:if test="${not empty message}">
                                    <div class="alert alert-info">${message}</div>
                                </c:if>

                                <!-- Form -->
                                <form action="register" method="post">
                                    <!-- Role -->
                                    <div class="mb-3">
                                        <label for="role" class="form-label">Role</label>
                                        <select class="form-select" id="role" name="role" onchange="onRoleChange(this)" required>
                                            <option value="" disabled ${empty param.role ? 'selected' : ''}>-- Select Role --</option>
                                            <option value="Volunteer" ${param.role == 'Volunteer' ? 'selected' : ''}>Volunteer</option>
                                            <option value="Staff" ${param.role == 'Staff' ? 'selected' : ''}>Staff</option>
                                        </select>
                                    </div>

                                    <!-- Employee Code (for Staff) -->
                                    <div class="mb-3 hidden" id="employeeCodeRow">
                                        <label for="employeeCode" class="form-label">Employee Code (Staff)</label>
                                        <input type="text" class="form-control" id="employeeCode" name="employeeCode" value="${param.employeeCode}">
                                    </div>

                                    <!-- Username -->
                                    <div class="mb-3">
                                        <label for="username" class="form-label">Username</label>
                                        <input type="text" class="form-control" id="username" name="username" value="${param.username}" required>
                                    </div>

                                    <!-- Password -->
                                    <div class="mb-3 password-wrapper">
                                        <label for="password" class="form-label">Password</label>
                                        <div class="position-relative">
                                            <input type="password" class="form-control" id="password" name="password" required>
                                            <i class="fa fa-eye-slash toggle-password" id="togglePassword" aria-label="Show password"></i>
                                        </div>
                                        <div class="mt-2">
                                            <div class="progress" style="height:6px;">
                                                <div id="pwdStrengthBar" class="progress-bar bg-danger" style="width:0%"></div>
                                            </div>
                                            <small id="pwdStrengthText" class="text-muted">Weak</small>
                                        </div>
                                    </div>

                                    <!-- Full Name -->
                                    <div class="mb-3">
                                        <label for="fullName" class="form-label">Full Name</label>
                                        <input type="text" class="form-control" id="fullName" name="fullName" value="${param.fullName}">
                                    </div>

                                    <!-- Email -->
                                    <div class="mb-3">
                                        <label for="email" class="form-label">Email</label>
                                        <input type="email" class="form-control" id="email" name="email" value="${param.email}">
                                    </div>

                                    <!-- Phone -->
                                    <div class="mb-3">
                                        <label for="phone" class="form-label">Phone</label>
                                        <input type="text" class="form-control" id="phone" name="phone" value="${param.phone}">
                                    </div>

                                    <!-- Date of Birth -->
                                    <div class="mb-3">
                                        <label for="dateOfBirth" class="form-label">Date of Birth</label>
                                        <input type="date" class="form-control" id="dateOfBirth" name="dateOfBirth" value="${param.dateOfBirth}">
                                    </div>

                                    <!-- Submit Button -->
                                    <div class="d-grid">
                                        <button type="submit" class="btn btn-primary">Create account</button>
                                    </div>
                                </form>

                                <div class="text-center mt-3">
                                    <span class="small">Already have an account? </span>
                                    <a href="login.jsp" class="small">Login here</a>
                                </div>

                                <!-- Google Register Button -->
                                <div class="d-grid gap-2 mt-3">
                                    <a href="<%= request.getContextPath() %>/auth/google" class="btn btn-outline-danger d-flex align-items-center justify-content-center" style="text-decoration: none;">
                                        <img src="https://www.gstatic.com/images/branding/product/1x/gsa_ios_64dp.png" alt="Google logo" style="width: 20px; height: 20px; margin-right: 8px;">
                                        <span>Đăng ký với Google</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            function onRoleChange(sel) {
                var v = sel.value;
                var row = document.getElementById('employeeCodeRow');
                if (row)
                    row.classList.toggle('hidden', v !== 'Staff');
            }
            document.addEventListener('DOMContentLoaded', function () {
                var sel = document.getElementById('role');
                if (sel)
                    onRoleChange(sel);
                var pwd = document.getElementById('password');
                var toggle = document.getElementById('togglePassword');
                var bar = document.getElementById('pwdStrengthBar');
                var text = document.getElementById('pwdStrengthText');
                function scorePassword(p) {
                    var s = 0;
                    if (!p)
                        return 0;
                    if (p.length >= 8)
                        s++;
                    if (/[A-Z]/.test(p))
                        s++;
                    if (/[a-z]/.test(p))
                        s++;
                    if (/[0-9]/.test(p))
                        s++;
                    if (/[^A-Za-z0-9]/.test(p))
                        s++;
                    return Math.min(s, 5);
                }
                function updateStrength(p) {
                    var s = scorePassword(p);
                    var pct = [0, 20, 40, 60, 80, 100][s];
                    var cls = 'bg-danger';
                    var label = 'Weak';
                    if (s >= 3) {
                        cls = 'bg-warning';
                        label = 'Medium';
                    }
                    if (s >= 5) {
                        cls = 'bg-success';
                        label = 'Strong';
                    }
                    if (bar) {
                        bar.style.width = pct + '%';
                        bar.className = 'progress-bar ' + cls;
                    }
                    if (text) {
                        text.textContent = label;
                    }
                }
                if (pwd) {
                    updateStrength(pwd.value || '');
                    pwd.addEventListener('input', function () {
                        updateStrength(pwd.value);
                    });
                }
                if(toggle && pwd){
                    toggle.addEventListener('click', function(){
                        var show = pwd.type === 'password';
                        pwd.type = show ? 'text' : 'password';
                        toggle.classList.toggle('fa-eye', show);
                        toggle.classList.toggle('fa-eye-slash', !show);
                        toggle.setAttribute('aria-label', show ? 'Hide password' : 'Show password');
                    });
                }
            });
        </script>
        <div class="footer_section layout_padding">
            <div class="container">
                <div class="row">
                    <div class="col-sm-6 col-md-6 col-lg-3">
                        <div class="footer_logo"><img src="images/footer-logo.png"></div>
                    </div>
                    <div class="col-sm-6 col-md-6 col-lg-3">
                        <h4 class="footer_taital">NAVIGATION</h4>
                        <div class="footer_menu_main">
                            <div class="footer_menu_left">
                                <div class="footer_menu">
                                    <ul>
                                        <li><a href="index_1.html">Home</a></li>
                                        <li><a href="donate.html">Donate</a></li>
                                        <li><a href="contact.html">Contact us</a></li>
                                    </ul>
                                </div>
                            </div>
                            <div class="footer_menu_right">
                                <div class="footer_menu">
                                    <ul>
                                        <li><a href="about.html">About</a></li>
                                        <li><a href="news.html">News</a></li>
                                        <li><a href="mission.html">Our Mission</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-md-6 col-lg-3">
                        <h4 class="footer_taital">NEWS</h4>
                        <p class="footer_text">Generators on the Internet</p>
                        <p class="footer_text">Tend to repeat predefined</p>
                        <p class="footer_text">Chunks as necessary, making</p>
                    </div>
                    <div class="col-sm-6 col-md-6 col-lg-3">
                        <h4 class="footer_taital">address</h4>
                        <p class="footer_text">Ave NW, Washington</p>
                        <p class="footer_text">+01 1234567890</p>
                        <p class="footer_text">demo@gmail.com</p>
                    </div>
                </div>
                <div class="footer_section_2">
                    <div class="row">
                        <div class="col-sm-4 col-md-4 col-lg-3">
                            <div class="social_icon">
                                <ul>
                                    <li><a href="#"><img src="images/fb-icon.png"></a></li>
                                    <li><a href="#"><img src="images/twitter-icon.png"></a></li>
                                    <li><a href="#"><img src="images/linkedin-icon.png"></a></li>
                                    <li><a href="#"><img src="images/instagram-icon.png"></a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-sm-8 col-md-8 col-lg-9">
                            <input type="text" class="address_text" placeholder="Enter your Enail" name="text">
                            <button type="button" class="get_bt">SUBSCRIBE</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="copyright_section">
            <div class="container">
                <p class="copyright_text">2020 All Rights Reserved. Design by <a href="https://html.design">Free html  Templates</a></p>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
<<<<<<< HEAD
</html>
=======
</html>
>>>>>>> origin/mainV1
