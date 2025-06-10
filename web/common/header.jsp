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
        <meta http-equiv="Cache-Control" content="no-store, no-cache, must-revalidate">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="0">



        <!--Title--> 
        <title>The Palatin - Hotel &amp; Resort Template</title>

        <!--Favicon--> 
        <link rel="icon" href="img/core-img/favicon.ico">

        <!--Core Stylesheet--> 
        <link rel="stylesheet" href="style.css">

        <script src="https://www.google.com/recaptcha/api.js?onload=onloadCallback&render=explicit" async defer></script>


        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">

        <!-- Bootstrap JS (include jQuery + Popper nếu dùng Bootstrap 4) -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
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
                                        <li class="${pageContext.request.requestURI.endsWith('home.jsp') ? 'active' : ''}"><a href="loadtohome">Home</a></li>
                                        <li class="${pageContext.request.requestURI.endsWith('rooms.jsp') ? 'active' : ''}"><a href="searchroom">Room</a></li>
                                        <li><a href="#">More</a>
                                            <ul class="dropdown">
                                                <li><a href="loadtohome">Home</a></li>
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
                                                <c:if test="${sessionScope.auth == null && sessionScope.auth1 == null}">
                                                    <li>
                                                        <a href="#login-modal" class="switch-modal">
                                                            <i class="fa fa-lock"> Login |</i>
                                                            <i class="fa fa-user"> Register</i>
                                                        </a>

                                                        <!--Login-->
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
                                                                            <li><a href="#verifyEmail-modal" class="switch-modal">Register</a></li>
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
                                                                        <span class="form-subtitle white-color">Login to our website, or <a href="#verifyEmail-modal" class="switch-modal"><strong>REGISTER</strong></a></span>
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
                                                                        <a href="#verifyEmail-modal" class="switch-modal">
                                                                            <span>or sign in</span>
                                                                        </a>
                                                                    </div>

                                                                    <div class="login-form text-center mb-50">
                                                                        <form action="loginaccount" method="post">
                                                                            <input type="hidden" name="action" value="login"/>
                                                                            <div class="form-item">
                                                                                <input type="email" name="email" value="${param.email}" placeholder="example@gmail.com">
                                                                                <!--                                                                                       pattern="^[a-zA-Z0-9._%+-]+@(gmail\.com|fpt\.edu\.vn)$"
                                                                                                                                                                       title="Email phải có định dạng @gmail.com hoặc @fpt.edu.vn">-->
                                                                            </div>
                                                                            <c:if test="${not empty param.loginError}">
                                                                                <script>
                                                                                    window.addEventListener('DOMContentLoaded', () => {
                                                                                        document.querySelector("a[href='#login-modal']").click();
                                                                                    });
                                                                                </script>
                                                                                <div style="color: red; text-align: center; margin-bottom: 10px;">
                                                                                    <c:choose>
                                                                                        <c:when test="${param.loginError == 'EmailNotExist'}">Email not registered account</c:when>
                                                                                    </c:choose>
                                                                                </div>
                                                                            </c:if>
                                                                            <div class="form-item">
                                                                                <input type="password" name="pass" placeholder="Password"
                                                                                       required 
                                                                                       pattern ="(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}" 
                                                                                       title="Mật khẩu phải có ít nhất 6 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.">
                                                                            </div>
                                                                            <c:if test="${not empty param.loginError}">
                                                                                <script>
                                                                                    window.addEventListener('DOMContentLoaded', () => {
                                                                                        document.querySelector("a[href='#login-modal']").click();
                                                                                    });
                                                                                </script>
                                                                                <div style="color: red; text-align: center; margin-bottom: 10px;">
                                                                                    <c:choose>
                                                                                        <c:when test="${param.loginError == 'WrongPassword'}">Wrong password</c:when>
                                                                                    </c:choose>
                                                                                </div>
                                                                            </c:if>
                                                                            <div class="row mb-2">
                                                                                <div class="col-md-6 d-flex justify-content-center">
                                                                                    <!-- Checkbox -->
                                                                                    <div class="form-check mb-3 mb-md-0">
                                                                                        <input class="form-check-input" type="checkbox" name="remember" value="on" id="loginCheck"
                                                                                               style="cursor: pointer"
                                                                                               <c:if test="${param.remember == 'on'}">checked</c:if> />
                                                                                               <label class="form-check-label" for="loginCheck" style="cursor: pointer"> Remember me </label>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <button type="submit" class="login-btn">login now</button>
                                                                            </form>
                                                                        </div>
                                                                        <div class="mt-3">
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
                                                            <c:if test="${not empty success}">
                                                                <div class="alert alert-success d-flex align-items-center" style="margin-bottom: 0px">
                                                                    <i class="fa fa-check" aria-hidden="true" style="margin-right: 8px;"></i>
                                                                    ${success}
                                                                </div>
                                                            </c:if>
                                                        </div>
                                                    </li>
                                                </c:if>
                                                <c:if test="${sessionScope.auth != null || sessionScope.auth1 != null}">
                                                    <li class="nav-item dropdown menu-btn">
                                                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                                                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                            <i class="fa fa-user"></i>
                                                        </a>
                                                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
                                                            <a class="dropdown-item" href="account" style="color: black">Tài khoản của tôi</a>
                                                            <a class="dropdown-item" href="updateprofile" style="color: black">Cập nhật hồ sơ</a>
                                                            <!--<a href="#verifyEmailChange-modal" class="dropdown-item switch-modal" style="font-size: 14px; color: black">Change your password </a>-->
                                                            <a href="#changePassword-modal" class="dropdown-item switch-modal" style="font-size: 14px; color: black">Change your password </a>
                                                            <div class="dropdown-divider"></div>
                                                            <form action="logingoogle" method="post" style="display:inline;">
                                                                <button type="submit" name="logout" value="true" class="dropdown-item">
                                                                    Đăng xuất
                                                                </button>
                                                            </form>
                                                        </div>
                                                    </li>
                                                </c:if>


                                                <!---- verify Email ---->
                                                <div id="verifyEmail-modal" class="reglog-modal-wrapper mfp-hide clearfix" style="background-image: url('${pageContext.request.contextPath}/img/bg-img/bg-3.jpg');">
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
                                                                    <li class="active"><a href="#verifyEmail-modal">Register</a></li>
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

                                                            <c:if test="${not empty error}">
                                                                <div class="alert alert-danger" style="margin-bottom: 10px">
                                                                    <i class="fa fa-exclamation-triangle" style="margin-right: 8px;"></i>
                                                                    ${error}
                                                                </div>
                                                            </c:if>
                                                            <div class="login-form text-center mb-50">
                                                                <form id="formVerifyEmail" action="loginaccount" method="post">
                                                                    <input type="hidden" name="action" value="verifyEmail"/>
                                                                    <div class="form-item">
                                                                        <input type="email" name="emailvrf" value="" placeholder="Email address">
                                                                        <!--                                                                        pattern="^[a-zA-Z0-9._%+-]+@(gmail\.com|fpt\.edu\.vn)$"
                                                                                                                                                title="Email phải kết thúc bằng @gmail.com hoặc @fpt.edu.vn"-->
                                                                    </div>

                                                                    <!--recaptcha-->
                                                                    <div style="margin: 10px;" id="verifyEmail-captcha"></div>
                                                                    <div id="errorVerifyEmail" style="color: white; font-style: italic"></div>
                                                                    <button type="submit" class="login-btn">Register</button>
                                                                </form>
                                                            </div>
                                                            <div class="bottom-text white-color">
                                                                <a href="#login-modal" class="switch-modal white-color">← Back to Login</a>
                                                            </div>
                                                        </div>
                                                        <!-- rightside-content - end -->
                                                        <a class="popup-modal-dismiss" href="#">
                                                            <i class="fa fa-times"><button title="Close (Esc)" type="button" class="mfp-close"></button></i>
                                                        </a>
                                                    </div>

                                                    <!--chuyển hướng đến verification code sau khi submit-->
                                                    <c:if test="${not empty openModal}">
                                                        <script>
                                                            document.addEventListener("DOMContentLoaded", function () {
                                                                setTimeout(function () {
                                                                    $.magnificPopup.open({
                                                                        items: {
                                                                            src: '${openModal}'
                                                                        },
                                                                        type: 'inline'
                                                                    });
                                                                }, 10);
                                                            });
                                                        </script>
                                                    </c:if>
                                                </div>

                                                <!---- Verification code ---->
                                                <div id="enterVerifyCode-modal" class="reglog-modal-wrapper mfp-hide clearfix" style="background-image: url('${pageContext.request.contextPath}/img/bg-img/bg-3.jpg');">
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
                                                                    <li class="active"><a href="#enterVerifyCode-modal">Register</a></li>
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
                                                                <h2 class="form-title title-large white-color">Verification code <strong>Register</strong></h2>
                                                                <span class="form-subtitle white-color">Have an account? <a href="#login-modal" class="switch-modal"><strong>LOGIN NOW</strong></a></span>
                                                            </div>

                                                            <div class="login-form text-center mb-50">
                                                                <p style="color: #ff0; font-weight: bold; margin: 0" id="countdown-text">Mã sẽ hết hạn sau <span id="countdown">${sessionScope.expiredAt}</span> giây</p>
                                                                <form id="resendForm" action="loginaccount" method="post" style="display: none">
                                                                    <input type="hidden" name="action" value="resendCode"/>
                                                                    <button type="submit" id="resendButton" class="login-btn" style="background: none; border: none; color: yellow; cursor: pointer; padding: 0;">
                                                                        Gửi lại mã?
                                                                    </button>
                                                                </form>
                                                                <p style="color: #fff; font-style: italic; margin-bottom: 15px;">
                                                                    Mã xác minh đã được gửi tới địa chỉ email: <strong>${sessionScope.email_to_verify}</strong>
                                                                </p>
                                                                <form action="loginaccount" method="post">
                                                                    <input type="hidden" name="action" value="verifyCode"/>
                                                                    <div class="form-item">
                                                                        <input type="text" name="codevrf" value="" placeholder="Enter verification code">
                                                                    </div>
                                                                    <button type="submit" class="login-btn">Register</button>
                                                                </form>
                                                            </div>
                                                            <div class="bottom-text white-color">
                                                                <a href="#verifyEmail-modal" class="switch-modal white-color">← Back to previous step</a>
                                                            </div>
                                                        </div>
                                                        <!-- rightside-content - end -->
                                                        <a class="popup-modal-dismiss" href="#">
                                                            <i class="fa fa-times"><button title="Close (Esc)" type="button" class="mfp-close"></button></i>
                                                        </a>
                                                    </div>
                                                    <!--time đếm ngược-->
                                                    <c:if test="${not empty sessionScope.expiredAt}">
                                                        <script>
                                                            const expiredTime = new Date("${sessionScope.expiredAt}").getTime();
                                                            const now = new Date().getTime();
                                                            let timeLeft = Math.floor((expiredTime - now) / 1000);
                                                            var countdown = document.getElementById("countdown");
                                                            var countdownText = document.getElementById("countdown-text");
                                                            var resendForm = document.getElementById("resendForm");
                                                            if (timeLeft > 0) {
                                                                var timer = setInterval(function () {
                                                                    timeLeft--;
                                                                    countdown.innerText = timeLeft;
                                                                    if (timeLeft <= 0) {
                                                                        clearInterval(timer);
                                                                        resendForm.style.display = "block";
                                                                        countdownText.innerHTML = "<span style='color: red;'>Mã đã hết hạn.</span>";
                                                                    }
                                                                }, 1000);
                                                            } else {
                                                                resendForm.style.display = "block";
                                                                countdownText.innerHTML = "<span style='color: red;'>Mã đã hết hạn.</span>";
                                                            }
                                                        </script>
                                                    </c:if>

                                                    <!--chuyển hướng đến Register sau khi submit-->
                                                    <c:if test="${not empty openModalRegister}">
                                                        <script>
                                                            document.addEventListener("DOMContentLoaded", function () {
                                                                setTimeout(function () {
                                                                    $.magnificPopup.open({
                                                                        items: {
                                                                            src: '${openModalRegister}'
                                                                        },
                                                                        type: 'inline'
                                                                    });
                                                                }, 100);
                                                            });
                                                        </script>
                                                    </c:if>

                                                    <!--báo lỗi-->
                                                    <c:if test="${not empty error}">
                                                        <div class="alert alert-danger" style="margin-bottom: 0px">
                                                            <i class="fa fa-exclamation-triangle" style="margin-right: 8px;"></i>
                                                            ${error}
                                                        </div>
                                                    </c:if>
                                                </div>

                                                <!---- register ---->
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
                                                                <form id="formRegister" action="loginaccount" method="post">
                                                                    <input type="hidden" name="action" value="register"/>
                                                                    <div class="form-item">
                                                                        <input type="text" placeholder="First Name" name="firstName" required autofocus 
                                                                               pattern = "[A-Za-zÀ-ỹ\s]{2,30}" title="Tên chỉ được chứa chữ cái, không bao gồm số hoặc ký tự đặc biệt.">
                                                                        <input type="text" placeholder="Last Name" name="lastName" required autofocus 
                                                                               pattern = "[A-Za-zÀ-ỹ\s]{2,30}" title="Tên chỉ được chứa chữ cái, không bao gồm số hoặc ký tự đặc biệt.">
                                                                    </div>
                                                                    <div class="form-item">
                                                                        <input type="password" placeholder="Password" name="pass" required 
                                                                               pattern ="(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}" 
                                                                               title="Mật khẩu phải có ít nhất 6 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.">
                                                                    </div>
                                                                    <div class="form-item">
                                                                        <input type="password" placeholder="Repeat Password" name="repass" required>
                                                                        <small id="repass-error" style="color: red; display: none;">Mật khẩu nhập lại không khớp.</small>
                                                                    </div>
                                                                    <!--recaptcha-->
                                                                    <div style="margin: 10px;" id="register-captcha"></div>
                                                                    <div id="errorRegister" style="color: white; font-style: italic"></div>
                                                                    <button type="submit" class="login-btn">Register</button>
                                                                </form>
                                                            </div>
                                                        </div>
                                                        <!-- rightside-content - end -->
                                                        <a class="popup-modal-dismiss" href="#">
                                                            <i class="fa fa-times"><button title="Close (Esc)" type="button" class="mfp-close"></button></i>
                                                        </a>
                                                    </div>
                                                    <!--chuyển hướng đến Login sau khi submit-->
                                                    <c:if test="${not empty openModalLogin}">
                                                        <script>
                                                            document.addEventListener("DOMContentLoaded", function () {
                                                                setTimeout(function () {
                                                                    $.magnificPopup.open({
                                                                        items: {
                                                                            src: '${openModalLogin}'
                                                                        },
                                                                        type: 'inline'
                                                                    });
                                                                }, 100);
                                                            });
                                                        </script>
                                                    </c:if>
                                                </div>

                                                <!---- Verification code forgot ---->
                                                <div id="enterVerifyCodeForgot-modal" class="reglog-modal-wrapper mfp-hide clearfix" style="background-image: url('${pageContext.request.contextPath}/img/bg-img/bg-3.jpg');">
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
                                                                    <li class="active"><a href="#enterVerifyCodeForgot-modal">Register</a></li>
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
                                                                <h2 class="form-title title-large white-color">Verification code <strong>Forgot password</strong></h2>
                                                                <span class="form-subtitle white-color">Have an account? <a href="#login-modal" class="switch-modal"><strong>LOGIN NOW</strong></a></span>
                                                            </div>

                                                            <div class="login-form text-center mb-50">
                                                                <p style="color: #ff0; font-weight: bold; margin: 0" id="countdown-text-fg">Mã sẽ hết hạn sau <span id="countdown-fg">${sessionScope.expiredAt}</span> giây</p>
                                                                <form id="resendFormForgot" action="loginaccount" method="post" style="display: none">
                                                                    <input type="hidden" name="action" value="resendCodeFogot"/>
                                                                    <button type="submit" id="resendButton" class="login-btn" style="background: none; border: none; color: yellow; cursor: pointer; padding: 0;">
                                                                        Gửi lại mã?
                                                                    </button>
                                                                </form>
                                                                <p style="color: #fff; font-style: italic; margin-bottom: 15px;">
                                                                    Mã xác minh đã được gửi tới địa chỉ email: <strong>${sessionScope.emailforgot}</strong>
                                                                </p>
                                                                <form action="loginaccount" method="post">
                                                                    <input type="hidden" name="action" value="verifyCodeForgot"/>
                                                                    <div class="form-item">
                                                                        <input type="text" name="codefg" value="" placeholder="Enter verification code">
                                                                    </div>
                                                                    <button type="submit" class="login-btn">Send</button>
                                                                </form>
                                                            </div>
                                                            <div class="bottom-text white-color">
                                                                <a href="#forgot-password-modal" class="switch-modal white-color">← Back to previous step</a>
                                                            </div>
                                                        </div>
                                                        <!-- rightside-content - end -->
                                                        <a class="popup-modal-dismiss" href="#">
                                                            <i class="fa fa-times"><button title="Close (Esc)" type="button" class="mfp-close"></button></i>
                                                        </a>
                                                    </div>
                                                    <!--time đếm ngược-->
                                                    <c:if test="${not empty sessionScope.expiredAt}">
                                                        <script>
                                                            const expiredTimefg = new Date("${sessionScope.expiredAt}").getTime();
                                                            const nowfg = new Date().getTime();
                                                            let timeLeftfg = Math.floor((expiredTimefg - nowfg) / 1000);
                                                            var countdownfg = document.getElementById("countdown-fg");
                                                            var countdownTextfg = document.getElementById("countdown-text-fg");
                                                            var resendFormfg = document.getElementById("resendFormForgot");
                                                            if (timeLeftfg > 0) {
                                                                var timerfg = setInterval(function () {
                                                                    timeLeftfg--;
                                                                    countdownfg.innerText = timeLeftfg;
                                                                    if (timeLeftfg <= 0) {
                                                                        clearInterval(timerfg);
                                                                        resendFormfg.style.display = "block";
                                                                        countdownTextfg.innerHTML = "<span style='color: red;'>Mã đã hết hạn.</span>";
                                                                    }
                                                                }, 1000);
                                                            } else {
                                                                resendFormfg.style.display = "block";
                                                                countdownTextfg.innerHTML = "<span style='color: red;'>Mã đã hết hạn.</span>";
                                                            }
                                                        </script>
                                                    </c:if>
                                                    <!--báo lỗi-->
                                                    <c:if test="${not empty error}">
                                                        <div class="alert alert-danger" style="margin-bottom: 0px">
                                                            <i class="fa fa-exclamation-triangle" style="margin-right: 8px;"></i>
                                                            ${error}
                                                        </div>
                                                    </c:if>
                                                </div>

                                                <!-- Complete Forgot Password Modal -->
                                                <div id="completeForgotPassword-modal" class="reglog-modal-wrapper mfp-hide clearfix" style="background-image: url('${pageContext.request.contextPath}/img/bg-img/bg-3.jpg');">
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
                                                                    <li><a href="#verifyEmail-modal" class="switch-modal">Register</a></li>
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
                                                                <span class="form-subtitle white-color">Enter your new password</span>
                                                            </div>

                                                            <div class="login-form text-center mb-50">
                                                                <form id="formCompleteForgotPassword" action="loginaccount" method="post">
                                                                    <input type="hidden" name="action" value="completeForgotPassword"/>
                                                                    <div class="form-item">
                                                                        <input type="email" value="${sessionScope.emailforgot}" readonly>
                                                                    </div>
                                                                    <div class="form-item">
                                                                        <input type="password" placeholder="Password" name="pass" required 
                                                                               pattern ="(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}" 
                                                                               title="Mật khẩu phải có ít nhất 6 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.">
                                                                    </div>
                                                                    <div class="form-item">
                                                                        <input type="password" placeholder="Repeat Password" name="repass" required>
                                                                        <small id="repassfg-error" style="color: red; display: none;">Mật khẩu nhập lại không khớp.</small>
                                                                    </div>
                                                                    <button type="submit" class="login-btn">Reset password</button>
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
                                                                    <li><a href="#verifyEmail-modal" class="switch-modal">Register</a></li>
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

                                                            <c:if test="${not empty error}">
                                                                <div class="alert alert-danger" style="margin-bottom: 10px">
                                                                    <i class="fa fa-exclamation-triangle" style="margin-right: 8px;"></i>
                                                                    ${error}
                                                                </div>
                                                            </c:if>
                                                            <div class="login-form text-center mb-50">
                                                                <form id="formVerifyEmailForgotPassword" action="loginaccount" method="post">
                                                                    <input type="hidden" name="action" value="verifyEmailForgotPassword"/>
                                                                    <div class="form-item">
                                                                        <input type="email" name="emailfg" placeholder="Your registered email" required>
                                                                        <!--                                                                        pattern="^[a-zA-Z0-9._%+-]+@(gmail\.com|fpt\.edu\.vn)$"
                                                                                                                                                title="Email phải kết thúc bằng @gmail.com hoặc @fpt.edu.vn"-->
                                                                    </div>
                                                                    <!--recaptcha-->
                                                                    <div style="margin: 10px;" id="forgot-captcha"></div>
                                                                    <div id="errorForgot" style="color: white; font-style: italic"></div>
                                                                    <button type="submit" class="login-btn">Send</button>
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

                                                <!-- verify Email Change Password Modal -->


                                                <!---- Verification code change ---->


                                                <!-- Change Password Modal -->
                                                <div id="changePassword-modal" class="reglog-modal-wrapper mfp-hide clearfix" style="background-image: url('${pageContext.request.contextPath}/img/bg-img/bg-3.jpg');">
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
                                                                    <li><a href="#verifyEmail-modal" class="switch-modal">Register</a></li>
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
                                                                <form id="formChange-password" action="changeassword" method="post">
                                                                    <input type="hidden" name="action" value="changePassword"/>
                                                                    <c:if test="${not empty sessionScope.auth}">
                                                                        <div class="form-item">
                                                                            <input type="email" value="${sessionScope.auth.email}" readonly>
                                                                        </div>
                                                                    </c:if>
                                                                    <c:if test="${not empty sessionScope.auth1.email}">
                                                                        <div class="form-item">
                                                                            <input type="email" value="${sessionScope.auth1.email}" readonly>
                                                                        </div>
                                                                        <div class="form-item">
                                                                            <input type="password" name="currentPassword" placeholder="Current Password" required>
                                                                        </div>
                                                                    </c:if>
                                                                    <div class="form-item">
                                                                        <input type="password" placeholder="New Password" name="pass" required 
                                                                               pattern ="(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}" 
                                                                               title="Mật khẩu phải có ít nhất 6 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.">
                                                                    </div>
                                                                    <div class="form-item">
                                                                        <input type="password" placeholder="Confirm New Password" name="repass" required>
                                                                        <small id="repassch-error" style="color: red; display: none;">Mật khẩu nhập lại không khớp.</small>
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
                                                    <c:if test="${not empty success}">
                                                        <div class="alert alert-success d-flex align-items-center" style="margin-bottom: 0px">
                                                            <i class="fa fa-check" aria-hidden="true" style="margin-right: 8px;"></i>
                                                            ${success}
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${not empty error}">
                                                        <div class="alert alert-danger" style="margin-bottom: 10px">
                                                            <i class="fa fa-exclamation-triangle" style="margin-right: 8px;"></i>
                                                            ${error}
                                                        </div>
                                                    </c:if>
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
    </body>

    <script>
    </script>
    <script>
        let captchaWidgets = {};
        function onloadCallback() {
            captchaWidgets["formRegister"] = grecaptcha.render("register-captcha", {
                sitekey: "6LfzjVorAAAAAAJT5bnnmi2MrHjf7KjKK2sAwlxF"
            });
            captchaWidgets["formVerifyEmail"] = grecaptcha.render("verifyEmail-captcha", {
                sitekey: "6LfzjVorAAAAAAJT5bnnmi2MrHjf7KjKK2sAwlxF"
            });
            captchaWidgets["formVerifyEmailForgotPassword"] = grecaptcha.render("forgot-captcha", {
                sitekey: "6LfzjVorAAAAAAJT5bnnmi2MrHjf7KjKK2sAwlxF"
            });
        }

        window.onload = function () {
            setupFormValidation("formRegister", "errorRegister", "repass-error", true);
            setupFormValidation("formVerifyEmail", "errorVerifyEmail", null, false);
            setupFormValidation("formVerifyEmailForgotPassword", "errorForgot", null, false);
            setupFormValidation("formCompleteForgotPassword", null, "repassfg-error", true);
            setupFormValidation("formChange-password", null, "repassch-error", true);
        };
        function setupFormValidation(formId, captchaErrorId, repassErrorId, hasRepass) {
            const form = document.getElementById(formId);
            const errorCaptcha = captchaErrorId ? document.getElementById(captchaErrorId) : null;
            const errorRepass = repassErrorId ? document.getElementById(repassErrorId) : null;
            form.addEventListener("submit", function (e) {
                let hasError = false;
                // Re-password check
                if (hasRepass) {
                    const pass = form.querySelector('input[name="pass"]').value;
                    const repass = form.querySelector('input[name="repass"]').value;
                    if (pass !== repass) {
                        errorRepass.style.display = "block";
                        hasError = true;
                    } else {
                        errorRepass.style.display = "none";
                    }
                }
                // Captcha check
                if (errorCaptcha) {
                    const widgetId = captchaWidgets?.[formId];
                    const captchaResponse = grecaptcha?.getResponse(widgetId);
                    if (!captchaResponse) {
                        errorCaptcha.innerText = "Bạn cần xác minh captcha.";
                        hasError = true;
                    } else {
                        errorCaptcha.innerText = "";
                    }
                }
                if (hasError) {
                    e.preventDefault();
                }
            });
        }
    </script>

    <script>
        function togglePassword() {
            const passInput = document.getElementById("newPass");
            const icon = document.getElementById("togglePass");
            if (passInput.type === "password") {
                passInput.type = "text";
                icon.classList.remove("fa-eye");
                icon.classList.add("fa-eye-slash");
            } else {
                passInput.type = "password";
                icon.classList.remove("fa-eye-slash");
                icon.classList.add("fa-eye");
            }
        }
    </script>



    <!--ko đóng popup--> 
    <!--        <script>
                document.addEventListener("DOMContentLoaded", function () {
                    const hash = window.location.hash;
                    if (hash === "#login-modal") {
                        document.querySelector("a[href='#login-modal']").click();
                    }
                });
            </script>-->

    <%--<c:if test="${not empty openModal}">--%>
    <!--            <script>
                    window.addEventListener('DOMContentLoaded', (event) => {
                        if ($"{openModal}" === "login") {
                            // Tự động mở popup login bằng trigger
                            document.querySelector("a[href='#login-modal']").click();
                        }
                    });
                </script>-->
    <%--</c:if>--%>

    <!--remove cache-->
    <script>
        window.addEventListener("pageshow", function (event) {
            if (event.persisted || (window.performance && window.performance.navigation.type == 2)) {
                // Force reload if the page is loaded from cache
                window.location.reload();
            }
        });
    </script>

    <script>
        $(document).ready(function () {
            // Khởi tạo popup cho nút mở login/register
            $('.login-modal-btn, .register-modal-btn').magnificPopup({
                type: 'inline',
                midClick: true
            });
            // Xử lý chuyển đổi giữa login và register
            $(document).on('click', '.switch-modal', function (e) {
                e.preventDefault();
                const target = $(this).attr('href');
                // Cập nhật URL hash mà không reload
                history.pushState(null, '', target);
                // Đóng popup hiện tại rồi mở cái mới ngay lập tức
                $.magnificPopup.close();
                // Mở popup mới ngay lập tức 
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
