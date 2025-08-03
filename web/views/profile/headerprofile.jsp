<%-- 
    Document   : headerprofile
    Created on : Jun 15, 2025, 11:03:35 AM
    Author     : ASUS
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html> 

<header class="palatin-header">
    <div class="container-fluid">
        <div class="row align-items-center py-2">
            <div class="col-md-3">
                <a href="${pageContext.request.contextPath}/loadtohome" class="palatin-logo">Palatin</a>
            </div>
            <div class="col-md-4">
                <nav class="navbar-nav d-flex flex-row">
                    <a class="nav-link" href="#"></a>
                    <a class="nav-link" href="#"></a>
                </nav>
            </div>
            <div class="col-md-5">
                <div class="d-flex align-items-center justify-content-end">
                    <span class="me-3"><img src="${pageContext.request.contextPath}/img/vietnam.svg.webp" alt="Google logo" width="20"> VI | VND</span>
                    <a class="nav-link" href="${pageContext.request.contextPath}/voucher"><i class="fa fa-gift mr-2 text-primary"></i> Khuyến mãi</a>


                    <div class="user-avatar dropdown">
                        <a class="nav-link" style="color: white" href="#" id="userDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            ${fn:substring(sessionScope.authLocal.user.email, 0, 1)}
                            <!--<i class="fa fa-user"></i>-->
                        </a>
                        <div class="dropdown-menu dropdown-menu-right shadow border-0" aria-labelledby="userDropdown" style="min-width: 250px; margin-top: 18px">
                            <div class="px-3 py-2 border-bottom">
                                <c:if test="${sessionScope.levelUser.levelId == 1}">
                                    <div class="font-weight-bold text-dark">🥉 Bronze Priority</div>
                                </c:if>
                                <c:if test="${sessionScope.levelUser.levelId == 2}">
                                    <div class="font-weight-bold text-dark">🥈 Silver Priority</div>
                                </c:if>
                                <c:if test="${sessionScope.levelUser.levelId == 3}">
                                    <div class="font-weight-bold text-dark">🥇 Gold Priority</div>
                                </c:if>
                                <div class="text-muted small">
                                    <fmt:formatNumber value="${sessionScope.totalPaidAmount / 1000}" type="number" maxFractionDigits="0" /> điểm
                                </div>
                            </div>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/voucherforcustomer">
                                <i class="fas fa-money-bill-wave mr-2 text-primary"></i> Điểm
                            </a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/updateprofile">
                                <i class="fa fa-user-edit mr-2 text-primary"></i> Cập nhật hồ sơ
                            </a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/bookingroomcustomer">
                                <i class="fas fa-bed mr-2 text-primary"></i> Đặt chỗ của tôi
                            </a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/paymenthistory">
                                <i class="fas fa-list mr-2 text-primary"></i> Danh sách giao dịch
                            </a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/voucher">
                                <i class="fa fa-credit-card mr-2 text-primary"></i> Khuyến mãi
                            </a>

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