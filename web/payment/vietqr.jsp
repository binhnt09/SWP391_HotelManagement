<%-- 
    Document   : vietqr
    Created on : Jun 27, 2025, 9:46:47 AM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                background-color: #f5f7fa;
                display: flex;
                flex-direction: column;
                align-items: center;
                padding: 40px;
            }
            .qr-container {
                background-color: white;
                border-radius: 16px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.1);
                padding: 32px;
                max-width: 500px;
                width: 100%;
                text-align: center;
            }
            .qr-container h2 {
                color: #0f294d;
            }
            .qr-image {
                margin: 20px auto;
                width: 300px;
            }
            .info {
                font-size: 16px;
                margin: 10px 0;
                color: #333;
            }
            .confirm-btn {
                margin-top: 20px;
                padding: 12px 24px;
                font-size: 16px;
                background-color: #0f63f5;
                color: white;
                border: none;
                border-radius: 8px;
                cursor: pointer;
            }
            .confirm-btn:hover {
                background-color: #084ed2;
            }
            .back-link {
                margin-top: 20px;
                display: inline-block;
                color: #0f63f5;
                text-decoration: none;
            }
            .back-link:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <div id="checkout_box">
            <div class="qr-container">
                <h2>Quét mã VietQR để thanh toán</h2>
                <div class="info">Số tiền: <strong><%= request.getAttribute("amount") %></strong> VND</div>
                <div class="info">Nội dung chuyển khoản:</div>
                <div class="info"><strong><%= request.getAttribute("description") %></strong></div>
                <img class="qr-image" src="${qrUrl}" alt="QR Code thanh toán" />

                <!-- Nút xác nhận thủ công -->
                <!--<form action="vietqrreturn" method="get">-->
                <input type="hidden" name="bookingId" value="${bookingId}" />
                <input type="hidden" name="amount" value="<%= request.getAttribute("amount") %>" />
                <input type="hidden" name="bankCode" value="Manual" />
                <label for="transactionCode">Mã giao dịch (nếu có):</label><br/>
                <input type="text" name="transactionCode" id="transactionCode" placeholder="Nhập mã chuyển khoản" style="margin-top:5px; padding:8px; width:80%;"/><br/>
                <button class="confirm-btn" type="submit">Tôi đã thanh toán</button>
                <!--</form>-->

                <a class="back-link" href="loadtohome">← Quay lại trang chủ</a>
            </div>
        </div>

        <div id="success_box" style="display:none;">
            <div class="qr-container">
                <h2 style="color: green;">✅ Thanh toán thành công!</h2>
                <p>Cảm ơn bạn đã thanh toán. Đơn hàng của bạn đang được xử lý.</p>
                <a class="back-link" href="loadtohome">← Quay lại trang chủ</a>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            var contextPath = "<%= request.getContextPath() %>"; // lấy đúng context path
            var bookingId = "<%= request.getAttribute("bookingId") %>";

            setInterval(() => {
                $.post(contextPath + '/check-payment-status', {bookingId: bookingId}, function (data) {
                    if (data.payment_status === "PAID") {
                        $('#checkout_box').hide();
                        $('#success_box').show();
                    }
                }, 'json');
            }, 2000);
        </script>




    </body>
</html>
