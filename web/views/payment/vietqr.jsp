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
        <title>The palatin - QR</title>
        <link rel="icon" href="${pageContext.request.contextPath}/img/core-img/favicon.ico">

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/sepayQR.css">
    </head>
    <body>
        <div class="payment-container">
            <div class="countdown-box">
                <span class="countdown-time" id="countdown">15:00</span> ⏰
            </div>
            <div class="payment-header">
                <h2>✅ Đặt hàng thành công</h2>
                <p>Mã đơn hàng: <strong><%= session.getAttribute("description") %></strong></p>
                <p>Số tiền: <strong><%= session.getAttribute("amount") %></strong> VND</p>
            </div>

            <div class="payment-body">
                <!-- LEFT: QR Code -->
                <div class="qr-container">
                    <h3>Cách 1: Quét mã QR</h3>
                    <div id="checkout_box">
                        <img class="qr-image" src="<%= session.getAttribute("qrUrl") %>" alt="QR Code thanh toán" />
                        <div class="info">
                            Trạng thái: <em>Chờ thanh toán...</em>
                        </div>
                        <button onclick="downloadQR()" class="download-btn">Tải ảnh QR</button>
                    </div>
                    <div class="success-message" id="success_box" style="display:none;">
                        ✅ Thanh toán thành công! Bạn có thể kiểm tra booking
                        <a href="${pageContext.request.contextPath}/paymenthistory" class="success-link">tại đây </a>
                    </div>
                </div>

                <!-- RIGHT: Bank Info -->
                <div class="info-container">
                    <h3>Cách 2: Chuyển khoản thủ công</h3>
                    <img src="${pageContext.request.contextPath}/img/Logo_MB.png" alt="MB Bank" class="bank-logo" />
                    <h3 style="text-align: center">Ngân hàng MB</h3>
                    <div class="info">
                        <strong>Chủ tài khoản:</strong> <%= session.getAttribute("accountName") %>
                    </div>
                    <hr/>
                    <div class="info">
                        <strong>Số tài khoản:</strong> <%= session.getAttribute("stk") %>
                    </div>
                    <hr/>
                    <div class="info">
                        <strong>Số tiền:</strong> <%= session.getAttribute("amount") %> VND
                    </div>
                    <hr/>
                    <div class="info">
                        <strong>Nội dung chuyển khoản:</strong> <%= session.getAttribute("description") %>
                    </div>
                    <hr/>
                    <div class="info">
                        <small>🔁 Vui lòng giữ nguyên nội dung chuyển khoản để hệ thống tự động xác nhận thanh toán.</small>
                    </div>
                </div>
            </div>

            <div class="payment-footer">
                <a href="${pageContext.request.contextPath}/loadtohome">← Quay lại trang chủ</a>
            </div>
        </div>

        <script>
            var contextPath = "<%= request.getContextPath() %>"; // lấy đúng context path
            var bookingId = "<%= session.getAttribute("bookingId") %>";

            const intervalId = setInterval(() => {
                $.post(contextPath + '/check-payment-status', {bookingId: bookingId}, function (data) {
                    if (data.payment_status === "Confirmed") {
                        $('#checkout_box').hide();
                        $('#success_box').show();
                        clearInterval(intervalId);
                    }
                }, 'json');
            }, 2000);
            window.addEventListener('beforeunload', () => {
                clearInterval(intervalId);
            });
        </script>

        <!--download qr-->
        <script>
            function downloadQR() {
                fetch('<%= session.getAttribute("qrUrl") %>').then(res => res.blob()).then(blob => {
                    const url = URL.createObjectURL(blob);
                    const a = document.createElement('a');
                    a.href = url;
                    a.download = 'vietqr.png';
                    document.body.appendChild(a);
                    a.click();
                    a.remove();
                    URL.revokeObjectURL(url);
                });
            }
        </script>

        <script>
            const countdown = document.getElementById("countdown");

            if (countdown) {
                let timeLeft = 15 * 60;

                const timer = setInterval(() => {
                    const minutes = String(Math.floor(timeLeft / 60)).padStart(2, '0');
                    const seconds = String(timeLeft % 60).padStart(2, '0');

                    countdown.textContent = `\${minutes}:\${seconds}`;
                    timeLeft--;

                    if (timeLeft < 0) {
                        clearInterval(timer);
                        Swal.fire({
                            icon: "error",
                            title: "Giao dịch hết hạn!",
                            text: "Bạn chưa thanh toán trong thời gian quy định. Đặt phòng đã bị hủy.",
                            confirmButtonText: "Quay lại trang thanh toán"
                        }).then(() => {
                            window.location.href = "<%= request.getContextPath() %>/payment"; // hoặc loadtohome
                        });
                    }
                }, 1000);
            }
        </script>

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </body>
</html>