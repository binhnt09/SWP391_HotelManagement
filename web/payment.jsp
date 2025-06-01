<%-- 
    Document   : payment
    Created on : Jun 1, 2025, 10:18:57 AM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Traveloka - Thanh toán</title>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <div class="bird-icon"></div>
                <div class="logo">traveloka</div>
                <div style="margin-left: auto; font-weight: normal;">
                    Đừng lo lắng, giá vẫn giữ nguyên. Hoàn tất thanh toán của bạn bằng <span style="color: #FFD700;">00:54:29</span> ⏰
                </div>
            </div>

            <div class="main-content">
                <div class="payment-section">
                    <h2 class="section-title">Bạn muốn thanh toán thế nào?</h2>

                    <div class="payment-method selected" onclick="selectPayment(this)">
                        <div class="payment-method-header">
                            <div class="radio-btn checked"></div>
                            <div>
                                <div style="font-weight: bold;">VietQR</div>
                                <div style="font-size: 12px; color: #666;">Ưu đãi dành giá | Thanh toán dễ dàng bằng ứng dụng ngân hàng điện tử bằng cách quét mã QR</div>
                            </div>
                            <div class="payment-icons">
                                <div class="payment-icon" style="background: #E31E24; color: white; font-size: 8px;">VietQR</div>
                            </div>
                        </div>
                        <div class="qr-info">
                            <ul>
                                <li>Đảm bảo kết nối wifi ổn định hoặc dùng dung lượng hẵng di động để tra thanh toán bằng VietQR.</li>
                                <li>Mở QR và xuất hiện giao khi tiến hành việc mua: 'Thanh toán'. Chỉ cần lưu hoặc chụp màn hình mã QR để hoàn tất thanh toán của bạn trong thời hạn tức chi-banking của bạn.</li>
                                <li>Vui lòng sử dụng mã QR mới nhất được cung cấp để hoàn tất thanh toán của bạn.</li>
                            </ul>
                        </div>
                    </div>

                    <div class="payment-method" onclick="selectPayment(this)">
                        <div class="payment-method-header">
                            <div class="radio-btn"></div>
                            <div style="font-weight: bold;">E-Wallet</div>
                            <div class="payment-icons">
                                <div class="payment-icon zalopay">Zalo</div>
                                <div class="payment-icon shopee">SP</div>
                                <div class="payment-icon momo">MP</div>
                            </div>
                        </div>
                    </div>

                    <div class="payment-method" onclick="selectPayment(this)">
                        <div class="payment-method-header">
                            <div class="radio-btn"></div>
                            <div style="font-weight: bold;">ATM Cards/Mobile Banking</div>
                            <div class="payment-icons">
                                <div class="payment-icon" style="background: #004C97;">TP</div>
                                <div class="payment-icon" style="background: #1E3A8A;">IB</div>
                            </div>
                        </div>
                    </div>

                    <div class="payment-method" onclick="selectPayment(this)">
                        <div class="payment-method-header">
                            <div class="radio-btn"></div>
                            <div style="font-weight: bold;">Thẻ thanh toán</div>
                            <div class="payment-icons">
                                <div class="payment-icon visa">VISA</div>
                                <div class="payment-icon mastercard">MC</div>
                                <div class="payment-icon jcb">JCB</div>
                                <div class="payment-icon amex">AMEX</div>
                            </div>
                        </div>
                    </div>

                    <div class="payment-method" onclick="selectPayment(this)">
                        <div class="payment-method-header">
                            <div class="radio-btn"></div>
                            <div style="font-weight: bold;">Tại cửa hàng</div>
                            <div class="payment-icons">
                                <div class="payment-icon" style="background: #FF6600;">FPT</div>
                                <div class="payment-icon" style="background: #D50000;">Circle K</div>
                                <div class="payment-icon" style="background: #4CAF50;">Payoo</div>
                                <div class="payment-icon" style="background: #9C27B0;">VinMart</div>
                                <div style="font-size: 10px; color: #666;">+15 more</div>
                            </div>
                        </div>
                    </div>

                    <div class="payment-method" onclick="selectPayment(this)">
                        <div class="payment-method-header">
                            <div class="radio-btn"></div>
                            <div style="font-weight: bold;">Chuyển tiền qua VietinBank</div>
                            <div class="payment-icons">
                                <div class="payment-icon" style="background: #006A4E;">VTB</div>
                            </div>
                        </div>
                        <div style="font-size: 12px; color: #666; margin-top: 10px;">
                            Chỉ có vào 02:00 - 21:50
                        </div>
                    </div>

                    <div class="payment-method" onclick="selectPayment(this)">
                        <div class="payment-method-header">
                            <div class="radio-btn"></div>
                            <div style="font-weight: bold;">Trả góp thẻ tín dụng</div>
                            <div class="payment-icons">
                                <div class="payment-icon" style="background: #666;">⚡</div>
                            </div>
                        </div>
                        <div style="font-size: 12px; color: #666; margin-top: 10px;">
                            Đưa mức lão tháo
                        </div>
                    </div>

                    <div class="coupon-section">
                        <span style="color: #0770CD; font-size: 18px;">🎫</span>
                        <input type="text" class="coupon-input" placeholder="Thêm mã giảm (Enter coupon code or select available coupons)">
                        <button class="coupon-btn">Thêm mã</button>
                    </div>

                    <div class="price-summary">
                        <div class="total-price">1.087.121 VND</div>
                    </div>

                    <button class="payment-btn" onclick="processPayment()">
                        Thanh toán & Hiện thị mã QR
                    </button>

                    <div class="terms-text">
                        Bằng cách tiếp tục thanh toán, bạn đã đồng ý <a href="#" class="link">Điều khoản & Điều kiện</a> và <a href="#" class="link">Chính sách quyền riêng tư</a>.
                    </div>

                    <div class="points-info">
                        🎁 Earn total 3.805 Traveloka Points > 💎 Kiếm 1.304.545 Sao Priority
                    </div>
                </div>

                <div class="booking-summary">
                    <h3 style="font-size: 18px; margin-bottom: 15px;">Tóm tắt khách sạn</h3>
                    <div style="font-size: 12px; color: #666; margin-bottom: 15px;">Mã đặt chỗ: 1255776672</div>

                    <div class="hotel-info">
                        <div class="hotel-name">Muong Thanh Grand Da Nang Hotel</div>

                        <div class="date-info">
                            <div class="date-item">
                                <div class="date-label">Nhận phòng</div>
                                <div class="date-value">Th 5, 30 tháng 5<br>2025</div>
                                <div style="font-size: 11px; color: #666;">Từ 14:00</div>
                            </div>
                            <div class="date-item">
                                <div class="date-label">Trả phòng</div>
                                <div class="date-value">Th 7, 31 tháng 5<br>2025</div>
                                <div style="font-size: 11px; color: #666;">Trước 12:00</div>
                            </div>
                        </div>

                        <div style="text-align: center; margin-bottom: 15px;">
                            <span style="background: #f0f0f0; padding: 5px 10px; border-radius: 12px; font-size: 12px;">1 đêm</span>
                        </div>
                    </div>

                    <div class="room-info">
                        <div class="room-title">(1x) Deluxe King Room</div>
                        <div class="room-details">🏠 2 khách</div>
                        <div class="room-details">🍽️ Gồm bữa sáng</div>
                        <div class="room-details">📶 Without Wifi</div>
                        <div style="color: #d32f2f; font-size: 12px; margin-top: 8px;">Yêu cầu đặc biệt (nếu có)</div>
                    </div>

                    <div class="guest-info">
                        <div style="font-weight: bold; margin-bottom: 10px;">Tên khách</div>
                        <div style="margin-bottom: 10px;">Tokuda Toku</div>

                        <div class="status-badge no-refund">⚠ Không hoàn tiền</div><br>
                        <div class="status-badge no-reschedule">⚠ Không đổi lịch</div>
                    </div>

                    <div style="margin: 20px 0;">
                        <div style="font-weight: bold; margin-bottom: 10px;">Chi tiết người liên lạc</div>
                        <div style="margin-bottom: 5px;">Tokuda Toku</div>
                        <div style="font-size: 14px; color: #666;">📞 +6259638459</div>
                        <div style="font-size: 14px; color: #666;">✉️ tokutokuda123@gmail.com</div>
                    </div>

                    <div class="contact-note">
                        Sự lựa chọn tuyệt vời cho kỳ nghỉ của bạn!
                    </div>
                </div>
            </div>
        </div>

        <script>
            function selectPayment(element) {
                // Remove selected class from all payment methods
                const allMethods = document.querySelectorAll('.payment-method');
                allMethods.forEach(method => {
                    method.classList.remove('selected');
                    const radioBtn = method.querySelector('.radio-btn');
                    radioBtn.classList.remove('checked');
                });

                // Add selected class to clicked element
                element.classList.add('selected');
                const radioBtn = element.querySelector('.radio-btn');
                radioBtn.classList.add('checked');
            }

            function processPayment() {
                const selectedMethod = document.querySelector('.payment-method.selected');
                if (selectedMethod) {
                    const methodName = selectedMethod.querySelector('div[style*="font-weight: bold"]').textContent;
                    alert(`Đang xử lý thanh toán bằng: ${methodName}`);

                    // Simulate payment processing
                    setTimeout(() => {
                        if (methodName === 'VietQR') {
                            alert('Mã QR đã được tạo! Vui lòng quét mã để hoàn tất thanh toán.');
                        } else {
                            alert('Chuyển hướng đến trang thanh toán...');
                        }
                    }, 1000);
                } else {
                    alert('Vui lòng chọn phương thức thanh toán!');
                }
            }

            // Countdown timer simulation
            let timeLeft = 54 * 60 + 29; // 54 minutes 29 seconds

            function updateTimer() {
                const minutes = Math.floor(timeLeft / 60);
                const seconds = timeLeft % 60;
                const timerElement = document.querySelector('.header span[style*="color: #FFD700"]');
                if (timerElement) {
                    timerElement.textContent = `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
                            }

                            if (timeLeft > 0) {
                                timeLeft--;
                                setTimeout(updateTimer, 1000);
                            } else {
                                alert('Thời gian giữ giá đã hết! Vui lòng thực hiện lại giao dịch.');
                            }
                        }

                        // Start timer when page loads
                        document.addEventListener('DOMContentLoaded', function () {
                            updateTimer();
                        });

                        // Add coupon functionality
                        document.querySelector('.coupon-input').addEventListener('keypress', function (e) {
                            if (e.key === 'Enter') {
                                const couponCode = this.value.trim();
                                if (couponCode) {
                                    alert(`Đang áp dụng mã giảm giá: ${couponCode}`);
                                    // Simulate coupon application
                                    setTimeout(() => {
                                        alert('Mã giảm giá không hợp lệ hoặc đã hết hạn!');
                                    }, 1000);
                                }
                            }
                        });
        </script>
    </body>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            background-color: #f5f7fa;
            color: #333;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }

        .header {
            background: linear-gradient(135deg, #0770CD, #1E88E5);
            color: white;
            padding: 15px 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .logo {
            font-size: 24px;
            font-weight: bold;
        }

        .bird-icon {
            width: 30px;
            height: 30px;
            background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="white"><path d="M23 4.6c0-.8-.6-1.4-1.4-1.4H18c-.8 0-1.4.6-1.4 1.4v3.8L12 4.6c-.8-.8-2-.8-2.8 0L4.6 9.2c-.8.8-.8 2 0 2.8l4.6 4.6v3.8c0 .8.6 1.4 1.4 1.4h11.6c.8 0 1.4-.6 1.4-1.4V4.6z"/></svg>') no-repeat center;
            background-size: contain;
        }

        .main-content {
            display: grid;
            grid-template-columns: 1fr 350px;
            gap: 20px;
        }

        .payment-section {
            background: white;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .section-title {
            font-size: 20px;
            font-weight: bold;
            margin-bottom: 20px;
            color: #1a1a1a;
        }

        .payment-method {
            border: 2px solid #e0e6ed;
            border-radius: 8px;
            margin-bottom: 15px;
            padding: 15px;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .payment-method:hover {
            border-color: #0770CD;
        }

        .payment-method.selected {
            border-color: #0770CD;
            background-color: #f8fbff;
        }

        .payment-method-header {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .radio-btn {
            width: 20px;
            height: 20px;
            border: 2px solid #ddd;
            border-radius: 50%;
            position: relative;
        }

        .radio-btn.checked {
            border-color: #0770CD;
        }

        .radio-btn.checked::after {
            content: '';
            width: 10px;
            height: 10px;
            background: #0770CD;
            border-radius: 50%;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
        }

        .payment-icons {
            display: flex;
            gap: 8px;
            margin-left: auto;
        }

        .payment-icon {
            width: 32px;
            height: 20px;
            border-radius: 4px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 10px;
            font-weight: bold;
            color: white;
        }

        .zalopay {
            background: #0068FF;
        }
        .shopee {
            background: #EE4D2D;
        }
        .momo {
            background: #A50064;
        }
        .visa {
            background: #1A1F71;
        }
        .mastercard {
            background: #EB001B;
        }
        .jcb {
            background: #005B9A;
        }
        .amex {
            background: #006FCF;
        }

        .qr-info {
            background: #e8f4fd;
            border-left: 4px solid #0770CD;
            padding: 15px;
            margin-top: 15px;
            border-radius: 4px;
        }

        .qr-info ul {
            margin-left: 20px;
            color: #666;
        }

        .qr-info li {
            margin-bottom: 8px;
            line-height: 1.4;
        }

        .booking-summary {
            background: white;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            height: fit-content;
        }

        .hotel-info {
            margin-bottom: 20px;
        }

        .hotel-name {
            font-size: 16px;
            font-weight: bold;
            margin-bottom: 10px;
        }

        .date-info {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 10px;
            background: #f8f9fa;
            padding: 15px;
            border-radius: 6px;
            margin-bottom: 15px;
        }

        .date-item {
            text-align: center;
        }

        .date-label {
            font-size: 12px;
            color: #666;
            margin-bottom: 5px;
        }

        .date-value {
            font-weight: bold;
            color: #333;
        }

        .room-info {
            margin-bottom: 20px;
        }

        .room-title {
            font-weight: bold;
            margin-bottom: 10px;
        }

        .room-details {
            color: #666;
            font-size: 14px;
            margin-bottom: 5px;
        }

        .guest-info {
            margin-bottom: 20px;
        }

        .guest-name {
            font-weight: bold;
            margin-bottom: 10px;
        }

        .status-badge {
            display: inline-block;
            padding: 4px 8px;
            border-radius: 12px;
            font-size: 12px;
            margin-bottom: 5px;
        }

        .no-refund {
            background: #ffebee;
            color: #d32f2f;
        }

        .no-reschedule {
            background: #fff3e0;
            color: #f57c00;
        }

        .contact-note {
            background: #e8f5e8;
            color: #2e7d32;
            padding: 10px;
            border-radius: 6px;
            font-size: 14px;
            text-align: center;
        }

        .coupon-section {
            margin: 20px 0;
            padding: 15px;
            border: 2px dashed #ddd;
            border-radius: 8px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .coupon-input {
            flex: 1;
            border: none;
            outline: none;
            font-size: 14px;
        }

        .coupon-btn {
            color: #0770CD;
            background: none;
            border: none;
            cursor: pointer;
            font-weight: bold;
        }

        .price-summary {
            border-top: 2px solid #eee;
            padding-top: 20px;
            margin-top: 20px;
        }

        .total-price {
            font-size: 24px;
            font-weight: bold;
            color: #0770CD;
            text-align: right;
        }

        .payment-btn {
            width: 100%;
            background: linear-gradient(135deg, #FF6B35, #F7931E);
            color: white;
            border: none;
            padding: 15px;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            margin-top: 20px;
            transition: transform 0.2s ease;
        }

        .payment-btn:hover {
            transform: translateY(-2px);
        }

        .terms-text {
            font-size: 12px;
            color: #666;
            text-align: center;
            margin-top: 15px;
            line-height: 1.4;
        }

        .link {
            color: #0770CD;
            text-decoration: none;
        }

        .points-info {
            background: #fff9c4;
            padding: 10px 15px;
            border-radius: 6px;
            font-size: 12px;
            color: #795548;
            margin-top: 15px;
        }

        @media (max-width: 768px) {
            .main-content {
                grid-template-columns: 1fr;
            }

            .container {
                padding: 10px;
            }
        }
    </style>
</html>
