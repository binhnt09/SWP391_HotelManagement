<%-- 
    Document   : home
    Created on : May 25, 2025, 12:14:11 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="description" content="">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!--The above 4 meta tags *must* come first in the head; any other head content must come *after* these tags--> 

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
                                                <li><a href="rooms.jsp">Rooms</a></li>
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
                                                                    <a href="#!">
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
                                                                    <form action="#!">
                                                                        <div class="form-item">
                                                                            <input type="email" placeholder="example@gmail.com">
                                                                        </div>
                                                                        <div class="form-item">
                                                                            <input type="password" placeholder="Password">
                                                                        </div>
                                                                        <button type="submit" class="login-btn">login now</button>
                                                                    </form>
                                                                </div>

                                                                <div class="bottom-text white-color">
                                                                    <p class="m-0">
                                                                        * Denotes mandatory field.
                                                                    </p>
                                                                    <p class="m-0">** At least one telephone number is required.</p>
                                                                </div>

                                                            </div>
                                                            <!-- rightside-content - end -->

                                                            <a class="popup-modal-dismiss" href="#">
                                                                <i class="fa fa-times"><button title="Close (Esc)" type="button" class="mfp-close"></button></i>
                                                            </a>
                                                        </div>
                                                    </div>
                                                </li>

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
                                                                <form action="#!">
                                                                    <div class="form-item">
                                                                        <input type="email" placeholder="User Name">
                                                                    </div>
                                                                    <div class="form-item">
                                                                        <input type="password" placeholder="Password">
                                                                    </div>
                                                                    <div class="form-item">
                                                                        <input type="email" placeholder="Repeat Password">
                                                                    </div>
                                                                    <div class="form-item">
                                                                        <input type="password" placeholder="Email Address">
                                                                    </div>
                                                                    <div class="human-verification text-left">
                                                                        <input type="checkbox" id="imnotarobot">
                                                                        <label for="imnotarobot">I'm not a robot</label>
                                                                        <span class="verification-image">
                                                                            <img src="${pageContext.request.contextPath}/img/bg-img/iamnotrobot.png" alt="Image_not_found">
                                                                        </span>
                                                                    </div>
                                                                    <button type="submit" class="login-btn">login now</button>
                                                                </form>
                                                            </div>

                                                            <div class="bottom-text white-color">
                                                                <p class="m-0">
                                                                    * Denotes mandatory field.
                                                                </p>
                                                                <p class="m-0">** At least one telephone number is required.</p>
                                                            </div>

                                                        </div>
                                                        <!-- rightside-content - end -->

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
    </body>

    <script>

    </script>
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
