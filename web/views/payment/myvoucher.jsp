<%-- 
    Document   : myvoucher
    Created on : Jul 10, 2025, 10:45:22 PM
    Author     : ASUS
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>The Palatin - Giao dịch của tôi</title>
        <link rel="icon" href="${pageContext.request.contextPath}/img/core-img/favicon.ico">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/myvoucher.css">

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

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

                        <div class="content-point">
                            <div class="point-summary">
                                <div class="point-title">Tổng chi tiêu của bạn</div>
                                <div class="point-value">₫${totalPaidAmount}</div>
                            <a href="#" class="btn-info">Thông tin chi tiết</a>
                        </div>

                        <!-- Nút tìm hiểu -->
                        <div class="learn-more-box">
                            <p><strong>Tìm hiểu thêm về lợi ích khách hàng</strong><br>
                                Nhiều quyền lợi hơn khi bạn chi tiêu nhiều hơn!</p>
                            <a href="#" class="btn-learn">Tìm hiểu ngay</a>
                        </div>
                    </div>

                    <div class="card main-content-card">
                        <div class="container py-5">
                            
                            <div class="reward-wrapper">
                                <!-- Tổng số tiền đã thanh toán -->

                                <!-- Tabs: Lịch sử tích điểm -->
                                <div class="tabs">
                                    <ul>
                                        <li class="active">Voucher khả dụng</li>
                                        <li>Đã hết hạn</li>
                                        <li>Đã sử dụng</li>
                                    </ul>
                                </div>

                                <!-- Danh sách voucher -->
                                <div class="voucher-container">
                                    <c:forEach var="v" items="${vouchers}">
                                        <div class="voucher-card">
                                            <div class="voucher-title">${v.getCode()} - Giảm ${v.getDiscountPercentage()}%</div>
                                            <div class="voucher-date">
                                                Áp dụng:
                                                ${v.getValidFrom() != null ? v.getValidFrom().toString() : "Không giới hạn"} →
                                                ${v.getValidTo() != null ? v.getValidTo().toString() : "Không giới hạn"}
                                            </div>
                                            <div class="voucher-footer">
                                                <button class="apply-btn" >Sử dụng ngay</button>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>

                            </div>

                        </div>

                    </div>

                </div>
            </div>
        </div>

        <!-- Footer -->
        <jsp:include page="/views/profile/footerprofile.jsp"></jsp:include>

            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

            <script src="${pageContext.request.contextPath}/js/profile.js"></script>
    </body>
</html>


