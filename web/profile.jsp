<%-- 
    Document   : profile
    Created on : Jun 12, 2025, 10:57:54 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>Cài đặt tài khoản - The Palatin</title>
        <link rel="icon" href="img/core-img/favicon.ico">

        <link rel="stylesheet" href="css/profile.css">

        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
        <script src="https://www.google.com/recaptcha/api.js?onload=onloadCallback&render=explicit" async defer></script>

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">

        <!-- Bootstrap JS (include jQuery + Popper nếu dùng Bootstrap 4) -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
    </head>

    <body>
        <!-- header -->
        <header class="palatin-header">
            <div class="container-fluid">
                <div class="row align-items-center py-2">
                    <div class="col-md-2">
                        <a href="loadtohome" class="palatin-logo">Palatin</a>
                    </div>
                    <div class="col-md-7">
                        <nav class="navbar-nav d-flex flex-row">
                            <a class="nav-link" href="#"></a>
                            <a class="nav-link" href="#"></a>
                        </nav>
                    </div>
                    <div class="col-md-3">
                        <div class="d-flex align-items-center justify-content-end">
                            <span class="me-3"><img src="${pageContext.request.contextPath}/img/vietnam.svg.webp" alt="Google logo" width="20"> VI | VND</span>
                            <span class="me-3">Khuyến mãi</span>
                            <span class="me-3">Hỗ trợ</span>
                            <div class="user-avatar dropdown">
                                <a class="nav-link" href="#" id="userDropdown" role="button"
                                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <!--<i class="fa fa-user"></i>-->
                                </a>
                                <div class="dropdown-menu dropdown-menu-right shadow border-0" aria-labelledby="userDropdown" style="min-width: 250px; margin-top: 18px">
                                    <div class="px-3 py-2 border-bottom">
                                        <div class="font-weight-bold text-dark">🥉 Bronze Priority</div>
                                        <div class="text-muted small">0 Điểm</div>
                                    </div>
                                    <a class="dropdown-item" href="profile.jsp">
                                        <i class="fa fa-user mr-2 text-primary"></i> Tài khoản của tôi
                                    </a>
                                    <a class="dropdown-item" href="updateprofile">
                                        <i class="fa fa-user-edit mr-2 text-primary"></i> Cập nhật hồ sơ
                                    </a>
                                    <a class="dropdown-item" href="payment.jsp">
                                        <i class="fa fa-credit-card mr-2 text-primary"></i> Payment
                                    </a>

                                    <c:if test="${not empty sessionScope.authLocal}">
                                        <a href="#changePassword-modal" class="dropdown-item switch-modal">
                                            <i class="fa fa-lock mr-2 text-primary"></i> Change your password
                                        </a>
                                    </c:if>

                                    <div class="dropdown-divider"></div>

                                    <form action="logingoogle" method="post" style="margin: 0;">
                                        <button type="submit" name="logout" value="true" class="dropdown-item">
                                            <i class="fa fa-sign-out-alt mr-2"></i> Đăng xuất
                                        </button>
                                    </form>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </header>
                            <jsp:include page="common/changePassword.jsp"></jsp:include>

        <div class="container-fluid mt-4">
            <div class="row">
                <!-- Sidebar -->
                <div class="col-lg-3 col-md-4 mb-4">
                    <div class="card sidebar-card">
                        <div class="profile-section">
                            <div class="profile-avatar">N</div>
                            <h6 class="mb-2">ntb</h6>
                            <div class="profile-status">Bạn là thành viên Bronze Priority</div>
                        </div>
                        <div class="list-group list-group-flush sidebar-menu">
                            <div class="list-group-item">
                                <i class="fas fa-bed"></i> Đặt chỗ của tôi
                            </div>
                            <div class="list-group-item">
                                <i class="fas fa-list"></i> Danh sách giao dịch
                            </div>
                            <div class="list-group-item">
                                <i class="fas fa-money-bill-wave"></i> Refunds
                            </div>
                            <div class="list-group-item">
                                <i class="fas fa-users"></i> Thông tin hành khách đã lưu
                            </div>
                            <div class="list-group-item">
                                <i class="fas fa-envelope"></i> Cài đặt thông báo
                            </div>
                            <div class="list-group-item active">
                                <i class="fas fa-cog"></i> Tài khoản
                            </div>
                            <!--<i class="fas fa-sign-out-alt"></i> Đăng xuất-->
                            <form action="logingoogle" method="post" style="display:inline;">
                                <div class="list-group-item">
                                    <button type="submit" name="logout" value="true" class="dropdown-item">
                                        <i class="fa fa-sign-out-alt mr-2"></i> Đăng xuất
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- Main Content -->
                <div class="col-lg-9 col-md-8">
                    <div class="card main-content-card">
                        <div class="content-header">
                            <h2 class="mb-3">Cài đặt</h2>
                            <ul class="nav nav-tabs" id="accountTabs" role="tablist">
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link active" id="account-info-tab" data-bs-toggle="tab"
                                            data-bs-target="#account-info" type="button" role="tab">
                                        Thông tin tài khoản
                                    </button>
                                </li>
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link" id="password-security-tab" data-bs-toggle="tab"
                                            data-bs-target="#password-security" type="button" role="tab">
                                        Mật khẩu & Bảo mật
                                    </button>
                                </li>
                            </ul>
                        </div>

                        <div class="tab-content p-4" id="accountTabsContent">
                            <!-- Account Info Tab -->
                            <div class="tab-pane fade show active" id="account-info" role="tabpanel">
                                <form id="account-form">
                                    <!-- Personal Data Section -->
                                    <div class="mb-5">
                                        <h5 class="mb-3">Dữ liệu cá nhân</h5>

                                        <div class="row mb-3">
                                            <div class="col-12">
                                                <label for="fullName" class="form-label">Tên đầy đủ</label>
                                                <input type="text" class="form-control" id="fullName" value="ntb" required>
                                                <div class="form-text">Tên trong hộ sơ được rút ngắn từ họ tên của bạn.</div>
                                            </div>
                                        </div>

                                        <div class="row mb-3">
                                            <div class="col-md-4">
                                                <label for="gender" class="form-label">Giới tính</label>
                                                <select class="form-select" id="gender">
                                                    <option value="">Chọn giới tính</option>
                                                    <option value="male">Nam</option>
                                                    <option value="female">Nữ</option>
                                                    <option value="other">Khác</option>
                                                </select>
                                            </div>
                                            <div class="col-md-3">
                                                <label for="birthDay" class="form-label">Ngày sinh</label>
                                                <select class="form-select" id="birthDay">
                                                    <option value="">Ngày</option>
                                                </select>
                                            </div>
                                            <div class="col-md-3">
                                                <label for="birthMonth" class="form-label">Tháng</label>
                                                <select class="form-select" id="birthMonth">
                                                    <option value="">Tháng</option>
                                                </select>
                                            </div>
                                            <div class="col-md-2">
                                                <label for="birthYear" class="form-label">Năm</label>
                                                <select class="form-select" id="birthYear">
                                                    <option value="">Năm</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="city" class="form-label">Thành phố cư trú</label>
                                                <input type="text" class="form-control" id="city"
                                                       placeholder="Thành phố cư trú">
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Email Section -->
                                    <div class="mb-5">
                                        <h5 class="mb-2">Email</h5>
                                        <div id="email-list" class="mb-3">
                                            <div class="contact-item">
                                                <div>
                                                    <c:if test="${sessionScope.authGoogle != null}">
                                                        <span>${sessionScope.authGoogle.email}</span>
                                                    </c:if>
                                                    <c:if test="${sessionScope.authLocal != null}">
                                                        <span>${sessionScope.authLocal.email}</span>
                                                    </c:if>
                                                    <span class="verified-badge">Đã kích hoạt</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Phone Section -->
                                    <div class="mb-5">
                                        <h5 class="mb-2">
                                            <label for="phone" class="form-label">Phone number</label>
                                        </h5>

                                        <div id="phone-list" class="mb-3">
                                            <!-- Phone numbers will be added here -->
                                        </div>
                                        <input style="" type="text" class="form-control" id="phone">
                                        <button type="button" class="btn add-btn" id="add-phone-btn">
                                            <i class="fas fa-plus me-2"></i> Thêm số di động
                                        </button>
                                    </div>

                                    <!-- Linked Accounts Section -->
                                    <c:if test="${sessionScope.authGoogle != null}">
                                        <div class="mb-4">
                                            <h5 class="mb-2">Tài khoản đã Liên kết</h5>
                                            <p class="text-muted mb-3">Liên kết tài khoản mạng xã hội của bạn để đăng nhập Palatin dễ dàng</p>
                                            <div class="social-account-item">
                                                <div class="d-flex align-items-center">
                                                    <div class="social-icon">
                                                        <img src="${pageContext.request.contextPath}/img/logo.svg.webp" alt="Google logo" width="20">
                                                    </div>
                                                    <span>Google</span>
                                                </div>
                                                <button class="btn link-btn linked" data-account="google">Đã liên kết</button>
                                            </div>
                                        </div>
                                    </c:if>

                                    <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                                </form>
                            </div>

                            <!-- Password & Security Tab -->
                            <div class="tab-pane fade" id="password-security" role="tabpanel">
                                <form class="password-form">
                                    <div class="mb-4">
                                        <h5 class="mb-3">Đổi mật khẩu</h5>

                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="mb-3">
                                                    <label for="currentPassword" class="form-label">Mật khẩu hiện tại</label>
                                                    <input type="password" class="form-control" id="currentPassword" required>
                                                </div>

                                                <div class="mb-3">
                                                    <label for="newPassword" class="form-label">Mật khẩu mới</label>
                                                    <input type="password" class="form-control" id="newPassword" required>
                                                </div>

                                                <div class="mb-3">
                                                    <label for="confirmPassword" class="form-label">Xác nhận mật khẩu mới</label>
                                                    <input type="password" class="form-control" id="confirmPassword" required>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <button type="submit" class="btn btn-primary">Cập nhật mật khẩu</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Footer -->
        <footer class="palatin-footer">
            <div class="container">
                <div class="row">
                    <div class="col-md-3 footer-section">
                        <h6>Về Palatin</h6>
                        <ul>
                            <li><a href="#">Cách đặt chỗ</a></li>
                            <li><a href="#">Liên hệ chúng tôi</a></li>
                            <li><a href="#">Trợ giúp</a></li>
                            <li><a href="#">Về chúng tôi</a></li>
                        </ul>
                    </div>

                    <div class="col-md-3 footer-section">
                        <h6>Sản phẩm</h6>
                        <ul>
                            <li><a href="#">Khách sạn</a></li>
                        </ul>
                    </div>

                    <div class="col-md-3 footer-section">
                        <h6>Khác</h6>
                        <ul>
                            <li><a href="#">Palatin Blog</a></li>
                            <li><a href="#">Chính Sách Quyền Riêng</a></li>
                            <li><a href="#">Điều khoản & Điều kiện</a></li>
                            <li><a href="#">Đăng ký nội dung của bạn</a></li>
                        </ul>
                    </div>

                    <div class="col-md-3 footer-section">
                        <h6>Theo dõi chúng tôi</h6>
                        <div class="social-links mb-3">
                            <a href="#"><i class="fab fa-facebook"></i></a>
                            <a href="#"><i class="fab fa-instagram"></i></a>
                            <a href="#"><i class="fab fa-tiktok"></i></a>
                            <a href="#"><i class="fab fa-youtube"></i></a>
                            <a href="#"><i class="fas fa-envelope"></i></a>
                        </div>

                        <h6>Phương thức thanh toán</h6>
                        <div class="payment-methods">
                            <div class="payment-logo">Mastercard</div>
                            <div class="payment-logo">VNpay</div>
                        </div>
                    </div>
                </div>

                <div class="footer-bottom mt-4">
                    <p>Công ty TNHH Palatin Việt Nam.</p>
                    <p>Copyright © 2025 The Palatin. All rights reserved</p>
                </div>
            </div>
        </footer>
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
        <script src="js/authentication.js"></script>
            
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
        <!-- jQuery (vì Bootstrap 4 phụ thuộc) -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

        <!-- Popper.js -->
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

        <!-- Bootstrap 4 JS -->
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

        <script src="js/profile.js"></script>
    </body>
</html>
