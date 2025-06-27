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
        <title>The palatin - Payment</title>
        <link rel="icon" href="${pageContext.request.contextPath}/img/core-img/favicon.ico">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/payment.css">
        <!--sbvhfvhf-->
        <!--bvshvh-->
    </head>
    <body>
        <div class="container">
            <div class="header">
                <div class="bird-icon"></div>
                <div class="logo"><a href="loadtohome" style="text-decoration: none; color: white">The Palatin</a></div>
                <div style="margin-left: auto; font-weight: normal;">
                    Đừng lo lắng, giá vẫn giữ nguyên. Hoàn tất thanh toán của bạn bằng <span style="color: #FFD700;">00:01:00</span> ⏰
                </div>
            </div>

            <div class="main-content">

                <form id="paymentForm" action="${pageContext.request.contextPath}/payment" method="post">
                    <div class="payment-section">
                        <input type="hidden" name="bookingId" value="1" />
                        
                        <input type="hidden" id="transactionCode" name="transactionCode" />
                        <input type="hidden" id="bankCode" name="bankCode" />
                        <input type="hidden" id="gatewayResponse" name="gatewayResponse" />
                        <input type="hidden" id="qrRef" name="qrRef" />
                        <input type="hidden" id="nights" name="nights" value="${sessionScope.numberNight}" />
                        <input type="hidden" name="roomId" value="${sessionScope.roomId}">
                        <input type="hidden" name="checkin" value="${sessionScope.checkin}">
                        <input type="hidden" name="checkout" value="${sessionScope.checkout}">

                        <h2 class="section-title">Bạn muốn thanh toán thế nào?</h2>

                        <div class="payment-method selected" onclick="selectPayment(this)" data-method="vietqr">
                            <!--<input type="hidden" name="method" value="Vnpay" />-->
                            <input type="hidden" id="paymentMethod" name="method" value="vietqr" />
                            <div class="payment-method-header">
                                <div class="radio-btn checked"></div>
                                <div>
                                    <!--<div class="payment-badge">Ưu đãi giảm giá</div>-->
                                    <div style="font-weight: bold;">VietQR</div>
                                    <div style="font-size: 12px; color: #666;">Ưu đãi dành giá | Thanh toán dễ dàng bằng ứng dụng ngân hàng điện tử bằng cách quét mã QR</div>
                                </div>
                                <div class="payment-icons">
                                    <div class="payment-icon" style="background: #E31E24; color: white; font-size: 8px;">VietQR</div>
                                </div>
                            </div>
                            <div class="qr-info active" id="vietqr-details">
                                <ul>
                                    <li>Đảm bảo kết nối wifi ổn định hoặc dùng dung lượng hẵng di động để tra thanh toán bằng VietQR.</li>
                                    <li>Mở QR và xuất hiện giao khi tiến hành việc mua: 'Thanh toán'. Chỉ cần lưu hoặc chụp màn hình mã QR để hoàn tất thanh toán của bạn trong thời hạn tức chi-banking của bạn.</li>
                                    <li>Vui lòng sử dụng mã QR mới nhất được cung cấp để hoàn tất thanh toán của bạn.</li>
                                </ul>
                            </div>
                        </div>

                        <div class="payment-method" onclick="selectPayment(this)" data-method="Vnpay">
                            <input type="hidden" name="method" value="Vnpay" />
                            <div class="payment-method-header">
                                <div class="radio-btn"></div>
                                <div style="font-weight: bold;">E-Wallet</div>
                                <div class="payment-icons">
                                    <div class="vnpay-logo">VN<span>Pay</span></div>
                                    <div class="payment-icon momo">MOMO</div>
                                </div>
                            </div>
<!--                            <div class="qr-info" id="Vnpay-details">
                                <ul>
                                    <label><input type="radio" name="wallet" value="vnpay"> VNPay</label><br>
                                    <label><input type="radio" name="wallet" value="momo"> MOMO</label><br>

                                    <div id="qr-display" style="margin-top: 12px; display: none;">
                                        <p>Quét mã QR để thanh toán:</p>
                                        <img id="qr-image" src="" alt="QR Code" style="width: 150px; height: 150px;">
                                    </div>
                                </ul>
                            </div>-->
                        </div>

                        <div class="payment-method" onclick="selectPayment(this)" data-method="banktransfer">
                            <div class="payment-method-header">
                                <div class="radio-btn"></div>
                                <div style="font-weight: bold;">ATM Cards/Mobile Banking</div>
                                <div class="payment-icons">
                                    <div class="payment-icon" style="background: #004C97;">TP</div>
                                    <div class="payment-icon" style="background: #1E3A8A;">IB</div>
                                </div>
                            </div>
<!--                            <div class="qr-info" id="banktransfer-details">
                                <ul>
                                    <li>VNPay</li>
                                    <li>MOMO</li>
                                </ul>
                            </div>-->
                        </div>
                        
                        <div class="payment-method" onclick="selectPayment(this)" data-method="cash">
                            <div class="payment-method-header">
                                <div class="radio-btn"></div>
                                <div style="font-weight: bold;">Cash</div>
                            </div>
                        </div>

                        <!--<input type="hidden" class="coupon-input">-->
                        <div class="coupon-section">
                            <div class="coupon-header" id="toggle-coupon" style="gap: 10px;">
                                <input type="hidden" name="voucherId" id="coupon-checkbox">
                                <span style="color: #0770CD; font-size: 18px;">🎫</span>
                                <label for="coupon-checkbox" class="coupon-label">Thêm mã giảm</label>
                                <span class="toggle-button">Thêm mã</span>
                            </div>
                            <div class="coupon-input-container" id="coupon-container" style="display: none;flex-direction: column; gap: 16px; margin-top: 12px;">
                                <input type="text" placeholder="Enter coupon code or select available coupon(s)" id="coupon-input" style="padding: 10px; width: 100%; box-sizing: border-box; border-radius: 6px;">
                                <div class="voucher-list">
                                    <p>Hoặc chọn một mã từ danh sách:</p>
                                    <label><input type="radio" name="voucher" value="TRAVEL10"> TRAVEL10 - Giảm 10%</label><br>
                                    <label><input type="radio" name="voucher" value="WELCOME15"> WELCOME15 - Giảm 15%</label><br>
                                    <label><input type="radio" name="voucher" value="SUMMER25"> SUMMER25 - Giảm 25%</label>
                                </div>
                                <button id="apply-coupon" style="padding: 10px 20px; width: fit-content;">Áp dụng</button><br/>
                            </div>
                        </div>

                        <div class="price-summary">
                            <input type="hidden" name="totalbill" value="${sessionScope.totalPrice}" />
                            <div class="total-price">${totalPrice} VND</div>
                        </div>

                        <button class="payment-btn" onclick="processPayment(event)">
                            Thanh toán & Hiện thị mã QR
                        </button>

                        <div class="terms-text">
                            Bằng cách tiếp tục thanh toán, bạn đã đồng ý <a href="#" class="link">Điều khoản & Điều kiện</a> và <a href="#" class="link">Chính sách quyền riêng tư</a>.
                        </div>
                    </div>
                </form>

                <div class="booking-summary">
                    <h3 style="font-size: 18px; margin-bottom: 15px;">Tóm tắt khách sạn</h3>
                    <div style="font-size: 12px; color: #666; margin-bottom: 15px;">Mã đặt chỗ: 1255776672</div>

                    <div class="hotel-info">
                        <div class="hotel-name">The Palatin Hotel</div>

                        <div class="date-info">
                            <div class="date-item">
                                <div class="date-label">Nhận phòng</div>
                                <strong>${checkin}</strong>
                                <div style="font-size: 11px; color: #666;">Từ 14:00</div>
                            </div>
                            <div class="date-item">
                                <div class="date-label">Trả phòng</div>
                                <strong>${checkout}</strong>
                                <div style="font-size: 11px; color: #666;">Trước 12:00</div>
                            </div>
                        </div>

                        <div style="text-align: center; margin-bottom: 15px;">
                            <span style="background: #f0f0f0; padding: 5px 10px; border-radius: 12px; font-size: 12px;">${numberNight} đêm</span>
                        </div>
                    </div>

                    <div class="room-info">
                        <div class="room-title">${room.getRoomNumber()}-${room.getRoomTypeID().getTypeName()}</div>
                        <div class="room-details">🏠 ${room.roomDetail.maxGuest} khách</div>
                        <div class="room-details">🍽️ Gồm bữa sáng</div>
                        <div class="room-details">📶 Without Wifi</div>
                        <div style="color: #d32f2f; font-size: 12px; margin-top: 8px;">Yêu cầu đặc biệt (nếu có)</div>
                    </div>

                    <div class="guest-info">
                        <div style="font-weight: bold; margin-bottom: 10px;">Tên khách</div>
                        <div style="margin-bottom: 10px;">${sessionScope.authLocal.user.firstName} ${sessionScope.authLocal.user.lastName}</div>

                        <div class="status-badge no-refund">⚠ Không hoàn tiền</div><br>
                        <div class="status-badge no-reschedule">⚠ Không đổi lịch</div>
                    </div>

                    <div style="margin: 20px 0;">
                        <div style="font-weight: bold; margin-bottom: 10px;">Chi tiết người liên lạc</div>
                        <div style="margin-bottom: 5px;">${sessionScope.authLocal.user.firstName} ${sessionScope.authLocal.user.lastName}</div>
                        <div style="font-size: 14px; color: #666;">📞 ${sessionScope.authLocal.user.phone}</div>
                        <div style="font-size: 14px; color: #666;">✉ ️${sessionScope.authLocal.user.email}</div>
                    </div>

                    <div class="contact-note">
                        Sự lựa chọn tuyệt vời cho kỳ nghỉ của bạn!
                    </div>
                </div>
            </div>
        </div>

        <script src="${pageContext.request.contextPath}/js/payment.js"></script>
    </body>
</html>
