<%-- 
    Document   : changePassword
    Created on : Jun 12, 2025, 11:48:42 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
                    <div class="form-item">
                        <input type="email" value="${sessionScope.authLocal.email}" readonly>
                    </div>
                    <div class="form-item password-toggle">
                        <input type="password" name="currentPassword" placeholder="Current Password" id="passCurrent" required 
                               pattern ="(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}" 
                               title="Mật khẩu phải có ít nhất 6 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.">
                        <i class="material-icons toggle-icon toggle-password" toggle="#passCurrent">remove_red_eye</i>
                    </div>
                    <div class="form-item password-toggle">
                        <input type="password" placeholder="New Password" name="pass" id="newpassChange" required 
                               pattern ="(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}" 
                               title="Mật khẩu phải có ít nhất 6 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.">
                        <i class="material-icons toggle-icon toggle-password" toggle="#newpassChange">remove_red_eye</i>
                        <small id="newpass-error" style="color: yellow; margin-bottom: 5px; display: none"></small>
                    </div>
                    <div class="form-item password-toggle">
                        <input type="password" placeholder="Confirm New Password" name="repass" id="repassChange" required>
                        <i class="material-icons toggle-icon toggle-password" toggle="#repassChange">remove_red_eye</i>
                        <small id="repassch-error" style="color: red; display: none;">Mật khẩu nhập lại không khớp.</small>
                    </div>
                    <button type="submit" class="login-btn">Update Password</button>
                </form>
            </div>

            <div class="bottom-text white-color">
                <a href="#login-modal" class="switch-modal white-color">← Cancel</a>
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
