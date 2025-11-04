<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="vi">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Log in</title>
        <meta name="keywords" content="">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link rel="stylesheet" href="css/responsive.css">
        <link rel="icon" href="images/fevicon.png" type="image/gif" />
        <link rel="stylesheet" href="css/jquery.mCustomScrollbar.min.css">
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css">
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700|Poppins:400,700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="css/owl.carousel.min.css">
        <link rel="stylesheet" href="css/owl.theme.default.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.css" media="screen">
        <link rel="stylesheet" href="css/login.css?v=20251029" />
    </head>
    <body class="login-page">
        <div class="topbar d-flex align-items-center">
            <div class="container d-flex justify-content-between align-items-center">
                <a class="brand text-decoration-none" href="index.html">
                    <img src="images/logo.png" alt="logo"/>
                    <span class="text-white">Login</span>
                </a>
            </div>
        </div>

        <div class="login-wrap">
            <div class="login-card container-xxl">
                <div class="row g-4 justify-content-end">
                    <div class="col-12 col-lg-6 col-xl-5 d-flex">
                        <div class="card card-login">
                            <div class="card-header">
                                <div class="d-flex justify-content-between">
                                    <strong>Login</strong>
                                </div>
                            </div>
                            <div class="card-body">
                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger">${error}</div>
                                </c:if>
                                <form method="post" action="<%= request.getContextPath() %>/login">
                                    <div class="mb-3">
                                        <label for="username" class="form-label">Username</label>                                     
                                        <input type="text" id="username" name="username" class="form-control" placeholder="Please enter your Username" required>
                                    </div>
                                    <div class="mb-3 password-wrapper">
                                        <label for="password" class="form-label">Password</label>
                                        <div class="position-relative">
                                            <input type="password" id="password" name="password" class="form-control" placeholder="Please enter your password" required>
                                            <i class="fa fa-eye-slash toggle-password" id="togglePassword"></i>
                                            <a href="#" class="forgot-link">Forgot password?</a>
                                        </div>
                                    </div>
                                    <div class="d-grid gap-2">
                                        <button type="submit" class="btn btn-primary">LOGIN</button>
                                    </div>
                                </form>                              
                                <div class="signup-section">
                                    <p class="signup-text">
                                        Don't have an account? 
                                        <a href="AccountRegister.jsp" class="signup-link">Sign up</a>
                                    </p>

                                    <div class="d-grid gap-2 mt-3">
                                        <a href="<%= request.getContextPath() %>/auth/google" class="btn btn-outline-danger d-flex align-items-center justify-content-center" style="text-decoration: none;">
                                            <img src="https://www.gstatic.com/images/branding/product/1x/gsa_ios_64dp.png" alt="Google logo" style="width: 20px; height: 20px; margin-right: 8px;">
                                            <span>Sign in with Google</span>
                                        </a>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
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
                                        <li><a href="index.html">Home</a></li>
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
        <script src="js/jquery.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.bundle.min.js"></script>
        <script src="js/jquery-3.0.0.min.js"></script>
        <script src="js/plugin.js"></script>
        <!-- sidebar -->
        <script src="js/jquery.mCustomScrollbar.concat.min.js"></script>
        <script src="js/custom.js"></script>
        <!-- javascript --> 
        <script src="js/owl.carousel.js"></script>
        <script src="https:cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.js"></script>  
    </body>
    <script>
        (function () {
            var pwd = document.getElementById('password');
            var toggle = document.getElementById('togglePassword');
            if (toggle && pwd) {
                toggle.addEventListener('click', function () {
                    var show = pwd.type === 'password';
                    pwd.type = show ? 'text' : 'password';
                    toggle.classList.toggle('fa-eye', show);
                    toggle.classList.toggle('fa-eye-slash', !show);
                    toggle.setAttribute('aria-label', show ? 'Hide password' : 'Show password');
                });
            }
        })();
    </script>
</html>
