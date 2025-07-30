<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Trạng thái giao dịch</title>
        <link rel="icon" href="${pageContext.request.contextPath}/img/core-img/favicon.ico">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            body {
                background-color: #f4f4f4;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                margin: 0;
                padding: 0;
            }
            .container {
                max-width: 600px;
                margin: 60px auto;
                background-color: #fff;
                border-radius: 12px;
                padding: 40px 30px;
                text-align: center;
                box-shadow: 0 8px 16px rgba(0,0,0,0.1);
            }
            .status-icon {
                width: 100px;
                margin-bottom: 25px;
            }
            h3 {
                font-size: 24px;
                margin-bottom: 15px;
            }
            p {
                font-size: 17px;
                margin: 8px 0;
            }
            strong.phone {
                color: #e63946;
                font-size: 22px;
            }
            .btn-home {
                margin-top: 30px;
                padding: 12px 28px;
                background-color: #28a745;
                color: #fff;
                font-weight: bold;
                font-size: 16px;
                border: none;
                border-radius: 8px;
                cursor: pointer;
                transition: background-color 0.2s ease-in-out;
            }
            .btn-home:hover {
                background-color: #218838;
            }
        </style>
    </head>
    <body>

        <div class="container">
            <img src="https://cdn2.cellphones.com.vn/insecure/rs:fill:150:0/q:90/plain/https://cellphones.com.vn/media/wysiwyg/Review-empty.png"
                 alt="Trạng thái giao dịch" class="status-icon"/>

            <!-- Giao dịch thành công -->
            <c:if test="${sessionScope.transResult}">
                <h3 style="color: #28a745;">
                    <i class="fas fa-check-circle"></i> Giao dịch thành công!
                </h3>
                <p>Vui lòng để ý số điện thoại của nhân viên tư vấn:</p>
                <strong class="phone">0328633xxx</strong>
            </c:if>

            <!-- Giao dịch thất bại -->
            <c:if test="${sessionScope.transResult == false}">
                <h3 style="color: #dc3545;">
                    <i class="fas fa-times-circle"></i> Giao dịch thất bại!
                </h3>
                <p>Cảm ơn quý khách đã sử dụng dịch vụ.</p>
                <p>Liên hệ tổng đài để được tư vấn:</p>
                <strong class="phone">0328633xxx</strong>
            </c:if>

            <!-- Đang xử lý -->
            <c:if test="${empty sessionScope.transResult}">
                <h3 style="color: #ffc107;">
                    <i class="fas fa-clock"></i> Đang xử lý đơn hàng!
                </h3>
                <p>Chúng tôi đã tiếp nhận thông tin, vui lòng chờ xử lý.</p>
                <p>Liên hệ nếu cần hỗ trợ thêm:</p>
                <strong class="phone">0328633xxx</strong>
                <c:remove var="transResult" scope="session" />
            </c:if>

            <!-- Nút về trang chủ -->
            <br/>
            <div class="success-message" id="success_box" style="display:none;">
                ✅ Thanh toán thành công! Bạn có thể kiểm tra booking
                <a href="${pageContext.request.contextPath}/paymenthistory" class="success-link">tại đây </a>
            </div>
            <button class="btn-home"
                    onclick="window.location.href = '${pageContext.request.contextPath}/loadtohome'">
                <i class="fas fa-home"></i> Về trang chủ
            </button>
        </div>

    </body>
</html>

