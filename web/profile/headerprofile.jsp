<%-- 
    Document   : headerprofile
    Created on : Jun 15, 2025, 11:03:35 AM
    Author     : ASUS
--%>
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
                    <a class="nav-link" href="#"><i class="fa fa-gift mr-2 text-primary"></i> Khuyến mãi</a>

                    <div class="nav-item dropdown menu-btn user-info ">
                        <a class="nav-link" style="color: white" href="#" id="userDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="fa fa-headset mr-2 text-primary"></i> Hỗ trợ
                        </a>
                        <div class="dropdown-menu dropdown-menu-right shadow border-0" aria-labelledby="userDropdown" style="min-width: 250px; margin-top: 18px">
                            <a class="dropdown-item" href="#">
                                <i class="fa fa-question-circle mr-2 text-primary"></i> Trợ giúp
                            </a>
                            <a class="dropdown-item" href="#">
                                <i class="fa fa-envelope mr-2 text-primary"></i> Liên hệ chúng tôi
                            </a>
                            <a class="dropdown-item" href="#">
                                <i class="fa fa-inbox mr-2 text-primary"></i> Hộp thư của tôi
                            </a>
                        </div>
                    </div>

                    <div class="user-avatar dropdown">
                        <a class="nav-link" style="color: white" href="#" id="userDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            ${fn:substring(sessionScope.authLocal.user.email, 0, 1)}
                            <!--<i class="fa fa-user"></i>-->
                        </a>
                        <div class="dropdown-menu dropdown-menu-right shadow border-0" aria-labelledby="userDropdown" style="min-width: 250px; margin-top: 18px">
                            <div class="px-3 py-2 border-bottom">
                                <div class="font-weight-bold text-dark">🥉 Bronze Priority</div>
                                <div class="text-muted small">0 Điểm</div>
                            </div>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/profile/profile.jsp">
                                <i class="fa fa-user mr-2 text-primary"></i> Tài khoản của tôi
                            </a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/updateprofile">
                                <i class="fa fa-user-edit mr-2 text-primary"></i> Cập nhật hồ sơ
                            </a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/bookingcrud">
                                <i class="fas fa-bed mr-2 text-primary"></i> Đặt chỗ của tôi
                            </a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/profile/historypayment.jsp">
                                <i class="fas fa-list mr-2 text-primary"></i> Danh sách giao dịch
                            </a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/payment/payment.jsp">
                                <i class="fa fa-credit-card mr-2 text-primary"></i> Payment
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
