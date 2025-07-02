<%-- 
    Document   : profile
    Created on : Jun 12, 2025, 10:57:54 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>The Palatin - Giao dịch của tôi</title>
        <link rel="icon" href="${pageContext.request.contextPath}/img/core-img/favicon.ico">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    </head>

    <body>
        <!-- header -->
        <jsp:include page="/views/profile/headerprofile.jsp"></jsp:include>

            <div class="container-fluid mt-4">
                <div class="row">
                    <!-- Sidebar -->
                <jsp:include page="/views/profile/sidebarprofile.jsp"></jsp:include>

                    <!-- Main Content -->
                    <div class="col-lg-9 col-md-8">
                        <div class="card main-content-card">
                            <div class="content-header">
                                <h2 class="mb-3">Cài đặt</h2>
                                <ul class="nav nav-tabs" id="accountTabs" role="tablist">
                                    <li class="nav-item" role="presentation">
                                        <button class="nav-link ${empty openTab || openTab == '#account-info' ? 'active' : ''}"
                                            id="account-info-tab" data-bs-toggle="tab"
                                            data-bs-target="#account-info" type="button" role="tab">
                                        Thông tin tài khoản
                                    </button>
                                </li>
                                <li class="nav-item" role="presentation">
                                    <button class="nav-link ${openTab == '#password-security' ? 'active' : ''}" 
                                            id="password-security-tab" data-bs-toggle="tab"
                                            data-bs-target="#password-security" type="button" role="tab">
                                        Mật khẩu & Bảo mật
                                    </button>
                                </li>
                            </ul>
                        </div>

                        <div class="tab-content p-4" id="accountTabsContent">
                            <!-- Account Info Tab -->
                            <div class="tab-pane fade ${empty openTab || openTab == '#account-info' ? 'show active' : ''}" id="account-info" role="tabpanel">
                                <form action="updateprofile" method="post" id="profileForm">
                                    <input type="hidden" name="action" value="updateProfile"/>
                                    <!-- Personal Data Section -->
                                    <div class="mb-5">
                                        <c:if test="${not empty successUpProfile}">
                                            <div id="notification" class="alert alert-success d-flex align-items-center" style="margin-bottom: 0px">
                                                <i class="fa fa-check" aria-hidden="true" style="margin-right: 8px;"></i>
                                                ${successUpProfile}
                                            </div><br/>
                                            <c:remove var="successUpProfile" scope="session" />
                                        </c:if>
                                        <c:if test="${not empty errorUpProfile}">
                                            <div class="alert alert-danger" style="margin-bottom: 10px">
                                                <i class="fa fa-exclamation-triangle" style="margin-right: 8px;"></i>
                                                ${errorUpProfile}
                                            </div><br/>
                                        </c:if>
                                        <h5 class="mb-3">Dữ liệu cá nhân</h5>

                                        <div class="row mb-3">
                                            <div class="col-6">
                                                <label for="" class="form-label">Họ</label>
                                                <input type="text" name="firstName" class="form-control" value="${sessionScope.authLocal.user.firstName}" required>
                                            </div>
                                            <div class="col-6">
                                                <label for="" class="form-label">Tên</label>
                                                <input type="text" name="lastName" class="form-control" value="${sessionScope.authLocal.user.lastName}" required>
                                            </div>
                                        </div>

                                        <div class="row mb-3">
                                            <div class="col-md-4">
                                                <label for="gender" class="form-label">Giới tính</label>
                                                <select name="gender" class="form-select" id="gender">
                                                    <option value="">Chọn giới tính</option>
                                                    <option value="male" ${sessionScope.authLocal.user.sex=='male'?'selected':''}>Nam</option>
                                                    <option value="female" ${sessionScope.authLocal.user.sex=='female'?'selected':''}>Nữ</option>
                                                    <option value="other" ${sessionScope.authLocal.user.sex=='other'?'selected':''}>Khác</option>
                                                </select>
                                            </div>
                                            <script>
                                                // Lấy giá trị từ phía server
                                                var birthYear = ${fn:substring(sessionScope.authLocal.user.birthDay, 0, 4)};
                                                var birthMonth = ${fn:substring(sessionScope.authLocal.user.birthDay, 5, 7)};
                                                var birthDay = ${fn:substring(sessionScope.authLocal.user.birthDay, 8, 10)};
                                            </script>
                                            <div class="col-md-3">
                                                <label for="birthDay" class="form-label">Ngày sinh</label>
                                                <select name="birthDay" class="form-select" id="birthDay">
                                                    <option value="">Ngày</option>
                                                </select>
                                            </div>
                                            <div class="col-md-3">
                                                <label for="birthMonth" class="form-label">Tháng</label>
                                                <select name="birthMonth" class="form-select" id="birthMonth">
                                                    <option value="">Tháng</option>
                                                </select>
                                            </div>
                                            <div class="col-md-2">
                                                <label for="birthYear" class="form-label">Năm</label>
                                                <select name="birthYear" class="form-select" id="birthYear">
                                                    <option value="">Năm</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="row mb-3">
                                            <div class="col-md-6">
                                                <label for="city" class="form-label">Thành phố cư trú</label>
                                                <input type="text" name="city" value="${sessionScope.authLocal.user.address}" class="form-control" id="city" placeholder="Thành phố cư trú">
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Email Section -->
                                    <div class="mb-5">
                                        <h5 class="mb-2">Email</h5>
                                        <div id="email-list" class="mb-3">
                                            <div class="contact-item">
                                                <div>
                                                    <span>${sessionScope.authLocal.user.email}</span>
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
                                        <div class="contact-item" style="width: 50%">
                                            <input type="text" value="${sessionScope.authLocal.user.phone}" name="phone" class="form-control" id="phone" style="width: 80%">
                                            <c:if test="${not empty sessionScope.authLocal.user.phone}">
                                                <span class="verified-badge">Đã kích hoạt</span>
                                            </c:if>
                                        </div>
                                    </div>

                                    <!-- Linked Accounts Section -->
                                    <c:if test="${sessionScope.authLocal.authType eq 'google'}">
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

                                    <button type="submit" id="cancelProfile" class="btn btn-secondary" disabled>Để sau</button>
                                    <button type="submit" id="submitProfile" class="btn btn-primary" disabled>Lưu thay đổi</button>
                                </form>
                            </div>

                            <!-- Password & Security Tab -->
                            <div class="tab-pane fade ${openTab == '#password-security' ? 'show active' : ''}" id="password-security" role="tabpanel">
                                <c:if test="${sessionScope.authLocal.authType eq 'local'}">
                                    <form id="formChange-password-profile" action="changeassword" method="post" class="password-form">
                                        <input type="hidden" name="action" value="changePasswordInProfile"/>
                                        <div class="mb-4">
                                            <h5 class="mb-3">Đổi mật khẩu</h5>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <label for="passCurrent" class="form-label">Mật khẩu hiện tại</label>
                                                    <div class="mb-3 password-toggle">
                                                        <input type="password" class="form-control" name="currentPassword" value="${param.currentPassword}" placeholder="Current Password" id="passCurrent" required 
                                                               pattern ="(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}" 
                                                               title="Mật khẩu phải có ít nhất 6 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.">
                                                        <i class="material-icons toggle-icon toggle-password" toggle="#passCurrent">remove_red_eye</i>
                                                    </div>

                                                    <label for="newpassChange" class="form-label">Mật khẩu mới</label>
                                                    <div class="mb-3 password-toggle">
                                                        <input type="password" class="form-control" placeholder="New Password" name="pass" value="${param.pass}" id="newpassChange" required 
                                                               pattern ="(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}" 
                                                               title="Mật khẩu phải có ít nhất 6 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.">
                                                        <i class="material-icons toggle-icon toggle-password" toggle="#newpassChange">remove_red_eye</i>
                                                        <small id="newpass-error" style="color: red; margin-top: 10px; display: none"></small>
                                                    </div>

                                                    <label for="repassChange" class="form-label">Xác nhận mật khẩu mới</label>
                                                    <div class="mb-3 password-toggle">
                                                        <input type="password" class="form-control" placeholder="Confirm New Password" name="repass" value="${param.repass}" id="repassChange" required>
                                                        <i class="material-icons toggle-icon toggle-password" toggle="#repassChange">remove_red_eye</i>
                                                        <small id="repassch-error-profile" style="color: red; display: none;">Mật khẩu nhập lại không khớp.</small>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <button type="submit" class="btn btn-primary">Cập nhật mật khẩu</button>
                                    </form>
                                </c:if>

                                <c:if test="${not empty successUpProfile}">
                                    <div class="alert alert-success d-flex align-items-center" style="margin-bottom: 0px">
                                        <i class="fa fa-check" aria-hidden="true" style="margin-right: 8px;"></i>
                                        ${success}
                                    </div>
                                </c:if>
                                <c:if test="${not empty errorUpProfile}">
                                    <div class="alert alert-danger" style="margin-bottom: 10px">
                                        <i class="fa fa-exclamation-triangle" style="margin-right: 8px;"></i>
                                        ${error}
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${not empty openTab}">
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    const el = document.querySelector(`button[data-bs-target='${openTab}']`);
                    if (el)
                        new bootstrap.Tab(el).show();
                });
            </script>
        </c:if>

        <!-- Footer -->
        <jsp:include page="/views/profile/footerprofile.jsp"></jsp:include>

            <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
            <!-- jQuery (vì Bootstrap 4 phụ thuộc) -->
            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

            <!-- Popper.js -->
            <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

            <!-- Bootstrap 4 JS -->
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

            <script src="${pageContext.request.contextPath}/js/profile.js"></script>
        <script src="${pageContext.request.contextPath}/js/authentication.js"></script>

    </body>
</html>

