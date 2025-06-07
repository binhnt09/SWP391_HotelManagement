<%-- 
    Document   : home
    Created on : May 25, 2025, 12:14:11 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="description" content="">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!--The above 4 meta tags *must* come first in the head; any other head content must come *after* these tags--> 

        <!--remove cache-->



        <!--Title--> 
        <title>The Palatin - Hotel &amp; Resort Template</title>

        <!--Favicon--> 
        <link rel="icon" href="img/core-img/favicon.ico">

        <!--Core Stylesheet--> 
        <link rel="stylesheet" href="style.css">

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    </head>

    <body>
        <!-- Preloader -->
        <div class="preloader d-flex align-items-center justify-content-center">
            <div class="cssload-container">
                <div class="cssload-loading"><i></i><i></i><i></i><i></i></div>
            </div>
        </div>

        <!-- ##### Header Area Start ##### -->
        <header class="header-area">
            <!-- Navbar Area -->
            <div class="palatin-main-menu">
                <div class="classy-nav-container breakpoint-off">
                    <div class="container">
                        <!-- Menu -->
                        <nav class="classy-navbar justify-content-between" id="palatinNav">

                            <!-- Nav brand -->
                            <a href="home.jsp" class="nav-brand"><img src="img/core-img/logo.png" alt=""></a>

                            <!-- Navbar Toggler -->
                            <div class="classy-navbar-toggler">
                                <span class="navbarToggler"><span></span><span></span><span></span></span>
                            </div>

                            <!-- Menu -->
                            <div class="classy-menu">

                                <!-- close btn -->
                                <div class="classycloseIcon">
                                    <div class="cross-wrap"><span class="top"></span><span class="bottom"></span></div>
                                </div>

                                <!-- Nav Start -->
                                <div class="classynav">
                                    <ul>
                                        <li class="${pageContext.request.requestURI.endsWith('home.jsp') ? 'active' : ''}"><a href="home.jsp">Home</a></li>
                                        <li class="${pageContext.request.requestURI.endsWith('rooms.jsp') ? 'active' : ''}"><a href="searchroom">Room</a></li>
                                        <li><a href="#">More</a>
                                            <ul class="dropdown">
                                                <li><a href="home.jsp">Home</a></li>
                                                <li><a href="about-us.jsp">About Us</a></li>
                                                <li><a href="services.jsp">Services</a></li>
                                                <li><a href="searchroom">Rooms</a></li>
                                                <li><a href="blog.jsp">News</a></li>
                                                <li><a href="contact.jsp">Contact</a></li>
                                                <li><a href="elements.jsp">Elements</a></li>
                                                <li><a href="payment.jsp">Payment</a></li>
                                            </ul>
                                        </li>
                                        <li class="${pageContext.request.requestURI.endsWith('about-us.jsp') ? 'active' : ''}"><a href="about-us.jsp">About Us</a></li>
                                        <!--                                        <li><a href="#">Mega Menu</a>
                                                                                    <div class="megamenu">
                                                                                        <ul class="single-mega cn-col-4">
                                                                                            <li><a href="home.jsp">Home</a></li>
                                                                                            <li><a href="about-us.jsp">About Us</a></li>
                                                                                            <li><a href="services.jsp">Services</a></li>
                                                                                            <li><a href="rooms.jsp">Rooms</a></li>
                                                                                            <li class=""><a href="blog.jsp">News</a></li>
                                                                                            <li><a href="contact.jsp">Contact</a></li>
                                                                                            <li class=""><a href="elements.jsp">Elements</a></li>
                                                                                        </ul>
                                                                                    </div>
                                                                                </li>-->
                                        <li class="${pageContext.request.requestURI.endsWith('services.jsp') ? 'active' : ''}"><a href="services.jsp">Services</a></li>
                                        <li class="${pageContext.request.requestURI.endsWith('contact.jsp') ? 'active' : ''}"><a href="contact.jsp">Contact</a></li>
                                    </ul>

                                    <!-- Button -->

                                    <!-- Login -->
                                    <div class="menu-btn">
                                        <div class="user-search-btn-group ul-li clearfix">
                                            <ul>
                                                <c:if test="${sessionScope.acc == null}">
                                                    <li>
                                                        <a href="#login-modal" class="switch-modal">
                                                            <i class="fa fa-lock"> Login |</i>
                                                            <i class="fa fa-user"> Register</i>
                                                        </a>
                                                        <div id="login-modal" class="reglog-modal-wrapper mfp-hide clearfix" style="background-image: url('${pageContext.request.contextPath}/img/bg-img/bg-3.jpg');">
                                                            <div class="overlay-black clearfix">

                                                                <!-- leftside-content - start -->
                                                                <div class="leftside-content">
                                                                    <div class="site-logo-wrapper mb-80">
                                                                        <a href="#!" class="logo">
                                                                            <img src="${pageContext.request.contextPath}/img/core-img/logo.png" alt="logo_not_found">
                                                                        </a>
                                                                    </div>
                                                                    <div class="register-login-link mb-80">
                                                                        <ul>
                                                                            <li class="active"><a href="#login-modal">Login</a></li>
                                                                            <li><a href="#register-modal" class="switch-modal">Register</a></li>
                                                                        </ul>
                                                                    </div>
                                                                    <div class="copyright-text">
                                                                        <p class="m-0 yellow-color" >©2025 <a href="#!" class="yellow-color">Palatin.com</a> all right reserved, made with <i class="fa fa-heart"></i> by Themes Studio </p>
                                                                    </div>
                                                                </div>
                                                                <!-- leftside-content - end -->

                                                                <!-- rightside-content - start -->
                                                                <div class="rightside-content text-center">

                                                                    <div class="mb-30">
                                                                        <h2 class="form-title title-large white-color">Account <strong>Login</strong></h2>
                                                                        <span class="form-subtitle white-color">Login to our website, or <a href="#register-modal" class="switch-modal"><strong>REGISTER</strong></a></span>
                                                                    </div>

                                                                    <div class="google-login-btn mb-30">
                                                                        <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=http://localhost:8080/swp391_hotelmanagement/logingoogle&response_type=code&client_id=592439718897-nneimctagpcoattdv2r16b8ukod4u0cc.apps.googleusercontent.com&prompt=select_account%20consent">
                                                                            <span class="icon">
                                                                                <i class="fa fa-google"></i>
                                                                            </span>
                                                                            login with Google
                                                                        </a>
                                                                    </div>

                                                                    <div class="or-text mb-30">
                                                                        <a href="#register-modal" class="switch-modal">
                                                                            <span>or sign in</span>
                                                                        </a>
                                                                    </div>

                                                                    <div class="login-form text-center mb-50">
                                                                        <form action="loginaccount" method="post">
                                                                            <input type="hidden" name="action" value="login"/>
                                                                            <div class="form-item">
                                                                                <input type="email" name="user" placeholder="example@gmail.com">
                                                                            </div>
                                                                            <div class="form-item">
                                                                                <input type="password" name="pass" placeholder="Password">
                                                                            </div>
                                                                            <div class="row mb-4">
                                                                                <div class="col-md-6 d-flex justify-content-center">
                                                                                    <!-- Checkbox -->
                                                                                    <div class="form-check mb-3 mb-md-0">
                                                                                        <input class="form-check-input" type="checkbox" name="remember" value="" id="loginCheck" style="cursor: pointer" checked />
                                                                                        <label class="form-check-label" for="loginCheck" style="cursor: pointer"> Remember me </label>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <button type="submit" class="login-btn">login now</button>
                                                                        </form>
                                                                    </div>
                                                                    <div class="mt-3">
                                                                        <a href="#change-password-modal" class="switch-modal white-color" style="font-size: 14px;">Change your password? | </a>
                                                                        <a href="#forgot-password-modal" class="switch-modal white-color" style="font-size: 14px;">Forgot your password?</a>
                                                                    </div>

                                                                    <div class="bottom-text white-color">
                                                                        <p class="m-0">

                                                                        </p>
                                                                        <p class="m-0"></p>
                                                                    </div>

                                                                </div>
                                                                <!-- rightside-content - end -->

                                                                <a class="popup-modal-dismiss" href="#">
                                                                    <i class="fa fa-times"><button title="Close (Esc)" type="button" class="mfp-close"></button></i>
                                                                </a>
                                                            </div>
                                                        </div>
                                                    </li>
                                                </c:if>
                                                <c:if test="${sessionScope.acc != null}">
                                                    <li class="menu-btn">
                                                        <form action="logingoogle" method="" style="display:inline;">
                                                            <button type="submit" name="logout" value="true" class="btn btn-link nav-link">
                                                                <a><i class="fa fa-user"> Logout</i></a>
                                                            </button>
                                                        </form>
                                                    </li>
                                                </c:if>

                                                <!--                                                <li>
                                                                                                    <a href="#register-modal" class="switch-modal">
                                                                                                        <i class="fa fa-lock"> Login |</i>
                                                                                                        <i class="fa fa-user"> Register</i>
                                                                                                    </a>-->
                                                <div id="register-modal" class="reglog-modal-wrapper register-modal mfp-hide clearfix" style="background-image: url('${pageContext.request.contextPath}/img/bg-img/bg-3.jpg');">
                                                    <div class="overlay-black clearfix">

                                                        <!-- leftside-content - start -->
                                                        <div class="leftside-content">
                                                            <div class="site-logo-wrapper mb-80">
                                                                <a href="#!" class="logo">
                                                                    <img src="${pageContext.request.contextPath}/img/core-img/logo.png" alt="logo_not_found">
                                                                </a>
                                                            </div>
                                                            <div class="register-login-link mb-80">
                                                                <ul>
                                                                    <li><a href="#login-modal" class="switch-modal">Login</a></li>
                                                                    <li class="active"><a href="#register-modal">Register</a></li>
                                                                </ul>
                                                            </div>
                                                            <div class="copyright-text">
                                                                <p class="m-0 yellow-color">©2025 <a href="#!">Palatin.com</a> all right reserved, made with <i class="fa fa-heart"></i> by Themes Studio </p>
                                                            </div>
                                                        </div>
                                                        <!-- leftside-content - end -->

                                                        <!-- rightside-content - start -->
                                                        <div class="rightside-content text-center">

                                                            <div class="mb-30">
                                                                <h2 class="form-title title-large white-color">Account <strong>Register</strong></h2>
                                                                <span class="form-subtitle white-color">Have an account? <a href="#login-modal" class="switch-modal"><strong>LOGIN NOW</strong></a></span>
                                                            </div>

                                                            <div class="login-form text-center mb-50">
                                                                <form action="home.jsp" id="formRegister">
                                                                    <div class="form-item">
                                                                        <input type="email" placeholder="User Name" required autofocus pattern ="">
                                                                    </div>
                                                                    <div class="form-item">
                                                                        <input type="password" placeholder="Password" required pattern ="" title="">
                                                                    </div>
                                                                    <div class="form-item">
                                                                        <input type="email" placeholder="Repeat Password" required pattern ="">
                                                                    </div>
                                                                    <div class="form-item">
                                                                        <input type="password" placeholder="Email Address">
                                                                    </div>
                                                                    <!--recaptcha-->
                                                                    <div style="margin: 10px;" class="g-recaptcha" data-sitekey="6LcbvVYrAAAAAHNkpvJXFD1U2lNK--fDNfhtM1Q7"></div>
                                                                    <div id="errorRegister" style="color: white; font-style: italic"></div>
                                                                    <button type="submit" class="login-btn">Register</button>
                                                                </form>
                                                            </div>

                                                            <div class="bottom-text white-color">
                                                                <p class="m-0">

                                                                </p>
                                                                <p class="m-0"></p>
                                                            </div>

                                                        </div>
                                                        <!-- rightside-content - end -->

                                                        <a class="popup-modal-dismiss" href="#">
                                                            <i class="fa fa-times"><button title="Close (Esc)" type="button" class="mfp-close"></button></i>
                                                        </a>
                                                    </div>
                                                </div>

                                                <!-- Forgot Password Modal -->
                                                <div id="forgot-password-modal" class="reglog-modal-wrapper mfp-hide clearfix" style="background-image: url('${pageContext.request.contextPath}/img/bg-img/bg-3.jpg');">
                                                    <div class="overlay-black clearfix">
                                                        <!-- Left side - reuse -->
                                                        <div class="leftside-content">
                                                            <div class="site-logo-wrapper mb-80">
                                                                <a href="#!" class="logo">
                                                                    <img src="${pageContext.request.contextPath}/img/core-img/logo.png" alt="logo_not_found">
                                                                </a>
                                                            </div>
                                                            <div class="register-login-link mb-80">
                                                                <ul>
                                                                    <li><a href="#login-modal" class="switch-modal">Login</a></li>
                                                                    <li><a href="#register-modal" class="switch-modal">Register</a></li>
                                                                </ul>
                                                            </div>
                                                            <div class="copyright-text">
                                                                <p class="m-0 yellow-color">©2025 <a href="#!" class="yellow-color">Palatin.com</a> all right reserved</p>
                                                            </div>
                                                        </div>
                                                        <!-- Right side - Forgot password -->
                                                        <div class="rightside-content text-center">
                                                            <div class="mb-30">
                                                                <h2 class="form-title title-large white-color">Forgot <strong>Password</strong></h2>
                                                                <span class="form-subtitle white-color">Enter your email to reset password</span>
                                                            </div>

                                                            <div class="login-form text-center mb-50">
                                                                <form action="" method="">
                                                                    <div class="form-item">
                                                                        <input type="email" name="email" placeholder="Your registered email" required>
                                                                    </div>
                                                                    <button type="submit" class="login-btn">Send Reset Link</button>
                                                                </form>
                                                            </div>
                                                            <div class="bottom-text white-color">
                                                                <a href="#login-modal" class="switch-modal black-color">← Back to Login</a>
                                                            </div>
                                                        </div>
                                                        <a class="popup-modal-dismiss" href="#">
                                                            <i class="fa fa-times"><button title="Close (Esc)" type="button" class="mfp-close"></button></i>
                                                        </a>
                                                    </div>
                                                </div>

                                                <!-- Change Password Modal -->
                                                <div id="change-password-modal" class="reglog-modal-wrapper mfp-hide clearfix" style="background-image: url('${pageContext.request.contextPath}/img/bg-img/bg-3.jpg');">
                                                    <div class="overlay-black clearfix">

                                                        <div class="leftside-content">
                                                            <div class="site-logo-wrapper mb-80">
                                                                <a href="#!" class="logo">
                                                                    <img src="${pageContext.request.contextPath}/img/core-img/logo.png" alt="logo_not_found">
                                                                </a>
                                                            </div>
                                                            <div class="register-login-link mb-80">
                                                                <ul>
                                                                    <li><a href="#login-modal" class="switch-modal">Login</a></li>
                                                                    <li><a href="#register-modal" class="switch-modal">Register</a></li>
                                                                </ul>
                                                            </div>
                                                            <div class="copyright-text">
                                                                <p class="m-0 yellow-color">©2025 <a href="#!" class="yellow-color">Palatin.com</a> all right reserved</p>
                                                            </div>
                                                        </div>

                                                        <div class="rightside-content text-center">
                                                            <div class="mb-30">
                                                                <h2 class="form-title title-large white-color">Change <strong>Password</strong></h2>
                                                                <span class="form-subtitle white-color">Update your password securely</span>
                                                            </div>

                                                            <div class="login-form text-center mb-50">
                                                                <form action="changepassword" method="post">
                                                                    <div class="form-item">
                                                                        <input type="password" name="currentPassword" placeholder="Current Password" required>
                                                                    </div>
                                                                    <div class="form-item">
                                                                        <input type="password" name="newPassword" placeholder="New Password" required>
                                                                    </div>
                                                                    <div class="form-item">
                                                                        <input type="password" name="confirmPassword" placeholder="Confirm New Password" required>
                                                                    </div>
                                                                    <button type="submit" class="login-btn">Update Password</button>
                                                                </form>
                                                            </div>

                                                            <div class="bottom-text white-color">
                                                                <a href="#login-modal" class="switch-modal black-color">← Cancel</a>
                                                            </div>
                                                        </div>

                                                        <a class="popup-modal-dismiss" href="#">
                                                            <i class="fa fa-times"><button title="Close (Esc)" type="button" class="mfp-close"></button></i>
                                                        </a>
                                                    </div>
                                                </div>
                                                <!--</li>-->
                                            </ul>
                                        </div>
                                    </div>
                                    <!-- Login | Register End -->                               
                                </div>
                                <!-- Nav End -->
                            </div>
                        </nav>
                    </div>
                </div>
            </div>
        </header>
        <!-- ##### Header Area End ##### -->

        <!-- ##### All Javascript Script ##### -->
        <!--jQuery-2.2.4 js--> 
        <script src="js/jquery/jquery-2.2.4.min.js"></script>
        <!--Popper js--> 
        <script src="js/bootstrap/popper.min.js"></script>
        <!--Bootstrap js--> 
        <script src="js/bootstrap/bootstrap.min.js"></script>
        <!--All Plugins js--> 
        <script src="js/plugins/plugins.js"></script>
        <!--Active js--> 
        <script src="js/active.js"></script>
        <!----login js---->
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <script>
            window.onload = function () {
                let isValid = false;
                const form = document.getElementById("formRegister");
                const error = document.getElementById("errorRegister");

                form.addEventListener("submit", function (event) {
                    event.preventDefault();
                    const response = grecaptcha.getResponse();

                    if (response) {
                        form.submit();
                    } else {
                        error.innerHTML = "Please choose captcha!";
                    }
                });
            };
        </script>
        <!--remove cache-->


    </body>

    <script>
        $(document).ready(function () {
            // Khởi tạo popup cho nút mở login/register
            $('.login-modal-btn, .register-modal-btn').magnificPopup({
                type: 'inline',
                midClick: true
            });

            // Xử lý chuyển đổi giữa login <-> register
            $(document).on('click', '.switch-modal', function (e) {
                e.preventDefault();
                const target = $(this).attr('href');

                // Cập nhật URL hash mà không reload
                history.pushState(null, '', target);

                // Đóng popup hiện tại rồi mở cái mới ngay lập tức
                $.magnificPopup.close();

                // Mở popup mới ngay lập tức (không delay, không hiệu ứng)
                $.magnificPopup.open({
                    items: {
                        src: target,
                        type: 'inline'
                    },
                    midClick: true
                });
            });

            // Khi người dùng nhấn nút back (quay lại)
            window.addEventListener('popstate', function () {
                const hash = window.location.hash;
                $.magnificPopup.close();

                if ($(hash).length) {
                    $.magnificPopup.open({
                        items: {
                            src: hash,
                            type: 'inline'
                        },
                        midClick: true
                    });
                }
            });

            // Khi người dùng load trang kèm theo #login-modal hoặc #register-modal
            const initialHash = window.location.hash;
            if ($(initialHash).length) {
                $.magnificPopup.open({
                    items: {
                        src: initialHash,
                        type: 'inline'
                    },
                    midClick: true
                });
            }
        });
    </script>
</html>
