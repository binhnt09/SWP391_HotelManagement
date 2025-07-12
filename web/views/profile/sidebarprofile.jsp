<%-- 
    Document   : sidebarprofile
    Created on : Jun 15, 2025, 5:03:19 PM
    Author     : ASUS
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="col-lg-3 col-md-4 mb-4">
    <div class="card sidebar-card">
        <div class="profile-section">
            <c:if test="${sessionScope.authLocal != null}">
                <div class="profile-avatar">${fn:substring(sessionScope.authLocal.user.email, 0, 1)}</div>
                <h6 class="mb-2">${sessionScope.authLocal.user.lastName}</h6>
            </c:if>
            <c:if test="${sessionScope.levelUser.levelId == 1}">
                <div class="profile-status profile-bronze">Bạn là thành viên Bronze Priority 🥉</div>
            </c:if>
            <c:if test="${sessionScope.levelUser.levelId == 2}">
                <div class="profile-status profile-silver">Bạn là thành viên Silver Priority 🥈</div>
            </c:if>
            <c:if test="${sessionScope.levelUser.levelId == 3}">
                <div class="profile-status profile-gold">Bạn là thành viên Gold Priority 🥇</div>
            </c:if>
        </div>
        <div class="list-group list-group-flush sidebar-menu">
            <div class="list-group-item ${pageContext.request.requestURI.endsWith('myvoucher.jsp') ? 'active' : ''}">
                <a class="dropdown-item" href="${pageContext.request.contextPath}/voucherforcustomer">
                    <i class="fas fa-bed"></i> Điểm
                </a>
            </div>

            <div class="list-group-item ${pageContext.request.requestURI.endsWith('historybooking.jsp') ? 'active' : ''}">
                <a class="dropdown-item" href="${pageContext.request.contextPath}/bookingroomcustomer">
                    <i class="fas fa-bed"></i> Đặt chỗ của tôi
                </a>
            </div>
            <div class="list-group-item ${pageContext.request.requestURI.endsWith('historypayment.jsp') ? 'active' : ''}">
                <a class="dropdown-item" href="${pageContext.request.contextPath}/paymenthistory">
                    <i class="fas fa-list"></i> Danh sách giao dịch
                </a>
            </div>
            <div class="list-group-item">
                <a class="dropdown-item" href="#">
                    <i class="fas fa-money-bill-wave"></i> Refunds
                </a>
            </div>
            <div class="list-group-item">
                <a class="dropdown-item" href="#">
                    <i class="fas fa-users"></i> Thông tin khách đã lưu
                </a>
            </div>
            <div class="list-group-item">
                <a class="dropdown-item" href="#">
                    <i class="fas fa-envelope"></i> Cài đặt thông báo
                </a>
            </div>
            <div class="list-group-item ${pageContext.request.requestURI.endsWith('profile.jsp') ? 'active' : ''}">
                <a class="dropdown-item" href="${pageContext.request.contextPath}/updateprofile">
                    <i class="fas fa-cog"></i> Tài khoản
                </a>
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
