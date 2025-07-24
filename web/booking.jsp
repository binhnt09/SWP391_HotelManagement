
<%@ page import="java.text.SimpleDateFormat, java.util.*, java.time.*, java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Palatin - Đặt Phòng Khách Sạn</title>
        <link rel="icon" href="img/core-img/favicon.ico">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <style>
            .logo {
                color: #0194f3;
                font-weight: bold;
            }
            .nav-pill-custom {
                background-color: #e8f4fd;
                color: #0194f3;
            }
            .nav-pill-custom.active {
                background-color: #0194f3;
                color: white;
            }
            .promo-banner {
                background: linear-gradient(135deg, #ff6b35, #f7931e);
            }
            .price-highlight {
                color: #e74c3c;
            }
            .benefit-text {
                color: #27ae60;
            }
            .hotel-image-placeholder {
                height: 200px;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                display: flex;
                align-items: center;
                justify-content: center;
                color: white;
                font-size: 18px;
            }
            .main{
                padding-top: 150px
            }
        </style>
    </head>
    <body class="bg-light">
        <!-- Header -->
        <div class="preloader d-flex align-items-center justify-content-center">
            <div class="cssload-container">
                <div class="cssload-loading"><i></i><i></i><i></i><i></i></div>
            </div>
        </div>

        <!-- ##### Header Area Start ##### -->
        <%--<jsp:include page="common/header.jsp"></jsp:include>--%>
        <jsp:include page="common/header.jsp"></jsp:include>
            <!-- ##### Header Area End ##### -->

            <!-- ##### Breadcumb Area Start ##### -->


            <div class="container my-4 main">
                <div class="row">
                    <!-- Left Column - Booking Form -->
                    <div class="col-lg-8">
                        <div class="card shadow-sm">
                            <div class="card-body p-4">
                                <h2 class="card-title fw-bold mb-2">Đặt phòng của bạn</h2>

                                <!-- Promo Banner -->
                                <div class="alert promo-banner text-white d-flex align-items-center mb-4">
                                    <i class="fas fa-gift fs-3 me-3"></i>
                                    <div>
                                        <strong>Nhận ưu đãi độc quyền và tận hưởng nhiều lợi ích hơn khi bạn đăng nhập.</strong>
                                        <a href="#" class="text-white text-decoration-underline">Đăng nhập hoặc Đăng ký</a>
                                    </div>
                                </div>

                                <!-- Contact Information -->
                                <div class="mb-4">
                                    <h5 class="fw-bold mb-3">Thông tin liên hệ (dùng với E-voucher)</h5>
                                    <p class="text-muted small mb-3">Hãy điền chính xác tất cả thông tin để đảm bảo bạn nhận được Phiếu xác nhận đặt phòng (E-voucher) qua email của mình.</p>

                                    <div class="mb-3">
                                        <label class="form-label fw-semibold">Tên đầy đủ (theo Hộ chiếu/Thẻ căn cước công dân)</label>
                                        <input type="text" class="form-control" value="${sessionScope.authLocal.user.firstName} ${sessionScope.authLocal.user.lastName}" placeholder="Ví dụ: Nguyễn Văn A">
                                    <div class="form-text">Vui lòng chỉ dùng chữ cái (A-Z), không có chức danh, ký tự đặc biệt và dấu câu.</div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label fw-semibold">E-mail</label>
                                        <input type="email" name="email" value="${sessionScope.authLocal.user.email}" class="form-control" placeholder="email@example.com">
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label fw-semibold">Số điện thoại</label>
                                        <div class="input-group">
                                            <!--                                            <select class="form-select" style="max-width: 80px;">
                                                                                            <option>+84</option>
                                                                                        </select>-->
                                            <input type="tel" value="${sessionScope.authLocal.user.phone}" class="form-control">
                                        </div>
                                    </div>
                                </div>

                                <p class="form-text">Chúng tôi sẽ gửi e-voucher tới email này. ${sessionScope.authLocal.user.email} Số điện thoại (${sessionScope.authLocal.user.phone}).</p>
                            </div>

                            <h3>Dịch vụ đi kèm</h3>
                            <div id="service-container" style="display: flex; gap: 30px;">
                                <div id="left-column" style="flex: 1;"></div>
                                <div id="right-column" style="flex: 1;"></div>
                            </div>

                            <!-- Price Details -->
                            <div class="mb-4">
                                <h5 class="fw-bold mb-3">Chi tiết giá</h5>
                                <div class="card">
                                    <div class="card-body">
                                        <div class="d-flex justify-content-between mb-2">
                                            <span>Giá dịch vụ</span>
                                            <span id="service-price">0 VND</span>
                                        </div>
                                        <div class="text-muted small mb-2">${room.getRoomNumber()}-${room.getRoomType().getTypeName()} (${numberNight} đêm)</div>
                                        <div class="d-flex justify-content-between mb-2">
                                            <span>Giá phòng</span>
                                            <span id="room-price">${totalPrice} VND</span>
                                        </div>
                                        <div class="d-flex justify-content-between mb-3">

                                        </div>
                                        <hr>
                                        <div class="d-flex justify-content-between fw-bold fs-5 price-highlight">
                                            <span>Tổng giá</span>
                                            <span id="final-price">${totalPrice} VND</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="text-center mb-3">
                                <small class="text-muted"><i class="fas fa-mobile-alt"></i> Bản chưa bỏ trợ đình!</small>
                            </div>

                            <button class="btn btn-danger btn-lg w-100 fw-bold">
                                <a href="payment" class="fw-bold white-color">Tiếp tục thanh toán</a>
                            </button>

                            <p class="text-center small text-muted mt-3">
                                Bằng việc chấp nhận thanh toán, bạn đã đồng ý với 
                                <a href="#" class="text-primary">Điều khoản & Điều kiện</a>, 
                                <a href="#" class="text-primary">Chính sách quyền riêng tư</a> và 
                                <a href="#" class="text-primary">Quy trình hoàn tiền</a> của Traveloka.
                            </p>
                        </div>
                    </div>
                </div>

                <!-- Right Column - Hotel Info -->
                <div class="col-lg-4">
                    <div class="card shadow-sm">
                        <div class="hotel-image-placeholder">
                            <i class="fas fa-hotel fs-1"></i>
                        </div>
                        <div class="card-body">
                            <div class="alert alert-primary py-2 px-3 mb-3">
                                <small><i class="fas fa-bolt"></i> Đang khuyến mãi chỉ còn 2 giây! Chỉ còn 5 phòng có giá tốt nhất này!</small>
                            </div>

                            <h5 class="card-title fw-bold">${room.getHotel().getName()}</h5>
                            <div class="d-flex align-items-center mb-3">
                                <span class="text-warning me-2">
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star"></i>
                                </span>
                                <span class="badge bg-primary">8.9 (246)</span>
                            </div>

                            <div class="row text-center mb-3">
                                <div class="col-5">
                                    <strong>${checkin}</strong>
                                    <div class="small text-muted">Từ 14:00</div>
                                </div>
                                <div class="col-2">
                                    <div class="small text-muted">${numberNight} đêm</div>
                                </div>
                                <div class="col-5">
                                    <strong>${checkout}</strong>
                                    <div class="small text-muted">Trước 12:00</div>
                                </div>
                            </div>

                            <div class="fw-bold mb-1">${room.getRoomNumber()}-${room.getRoomType().getTypeName()}</div>

                            <div class="row small text-muted mb-3">
                                <div class="col-6"><i class="fas fa-users"></i> ${room.roomDetail.maxGuest} khách</div>
                                <div class="col-6"><i class="fas fa-bed"></i> ${room.roomDetail.bedType}</div>
                                <div class="col-6"><i class="fa-duotone fa-solid fa-chart-area"></i>  ${room.roomDetail.area} m²</div>
                                <div class="col-6"><i class="fas fa-wifi"></i> WiFi</div>
                            </div>
                            <div class="small text-muted mb-3">
                                <i class="fas fa-coffee"></i> ${room.roomDetail.description}
                            </div>

                            <hr>
                            <div class="d-flex justify-content-between align-items-center mb-2">
                                <strong>Tổng Giá Phòng</strong>
                                <div class="text-end">
                                    <div class="text-decoration-line-through text-muted small">9.866.665 VND</div>

                                    <div class="fw-bold price-highlight fs-5">${totalPrice} VND</div>
                                </div>
                            </div>
                            <div class="small text-muted mb-3">1 phòng, ${numberNight} đêm</div>

                            <div class="mb-3">
                                <div class="benefit-text small mb-1">

                                    <%
                                        String checkoutStr = request.getParameter("checkout"); 
                                        String cancelDeadline = "";

                                        if (checkoutStr != null && !checkoutStr.isEmpty()) {
                                            LocalDate checkout = LocalDate.parse(checkoutStr);
                                            LocalDate cancelDate = checkout.minusDays(7);

                                            // Định dạng kiểu: 19 thg 6 2025
                                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'thg' M yyyy", new Locale("vi", "VN"));
                                            cancelDeadline = cancelDate.format(formatter);
                                        }

                                        request.setAttribute("cancelDeadline", cancelDeadline);
                                    %>
                                    <c:choose>
                                        <c:when test="${nowTocheckin >= 7}">
                                            <i class="fas fa-check-circle"></i> Miễn phí hủy phòng trước ${cancelDeadline}</div>
                                        <div class="benefit-text small mb-2">
                                            <i class="fas fa-check-circle"></i> Có thể đổi lịch
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <i class="fas fa-check-circle"></i> <span class="text-danger">Không thể hủy phòng</span></div>
                                    </c:otherwise>
                                </c:choose>


                            <a href="#" class="text-primary small">Xem chi tiết</a>
                        </div>

                        <div class="mt-3">
                            <div class="fw-bold small mb-2">
                                <i class="fas fa-clipboard-list"></i> Chính sách lưu trú
                            </div>
                            <div class="fw-bold small mb-2">
                                <i class="fas fa-utensils"></i> Bữa sáng
                            </div>
                            <div class="small text-muted mb-3">
                                Bữa sáng tại cơ sở lưu trú được phục vụ từ 06:00 đến 10:30.
                            </div>

                            <div class="fw-bold small mb-2">
                                <i class="fas fa-clipboard-list"></i> Chính Sách Bổ Sung
                            </div>
                            <div class="small text-muted mb-2">
                                Infant 0-5 year(s) Stay for free if using existing bedding. Children 6-12 year(s) Must use an extra bed...
                            </div>
                            <a href="#" class="text-primary small">Xem chi tiết</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="js/active.js"></script>
    <script src="js/bootstrap/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
    <!-- Đảm bảo bạn đã truyền biến totalPrice từ server -->
<c:set var="totalPrice" value="${totalPrice}" />
<script>
    const baseUrl = '${pageContext.request.contextPath}';
    const roomPrice = ${totalPrice}; 
    let servicePrice = 0;

    function formatCurrency(value) {
        return value.toLocaleString('vi-VN') + ' VND';
    }

    function sendNewTotalToSession(newTotal) {
        fetch(baseUrl + "/bookingroom", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: "action=updateTotalPrice&newTotal=" + newTotal
        });
    }

    function updateTotal(servicePrice) {
        const total = roomPrice + servicePrice;
        document.getElementById('service-price').innerText = formatCurrency(servicePrice);
        document.getElementById('final-price').innerText = formatCurrency(total);
        sendNewTotalToSession(total);
    }

    fetch(baseUrl + "/bookingroom", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'action=addService'
    })
    .then(response => response.json())
    .then(data => {
        const leftColumn = document.getElementById('left-column');
        const rightColumn = document.getElementById('right-column');
        let isLeft = true;

        data.forEach(service => {
            const column = isLeft ? leftColumn : rightColumn;

            const row = document.createElement('div');
            row.style.marginBottom = '10px';

            const checkbox = document.createElement('input');
            checkbox.type = 'checkbox';
            checkbox.name = 'services';
            checkbox.value = service.serviceId;
            checkbox.dataset.price = service.price;

            checkbox.addEventListener('change', function () {
                const price = parseFloat(this.dataset.price);
                if (this.checked) {
                    servicePrice += price;
                } else {
                    servicePrice -= price;
                }
                updateTotal(servicePrice);
            });

            const label = document.createElement('label');
            label.appendChild(checkbox);
            label.append(' ' + service.name + ' (' + formatCurrency(service.price) + ')');

            row.appendChild(label);
            column.appendChild(row);
            isLeft = !isLeft;
        });

        // Gọi lần đầu để hiển thị ban đầu
        updateTotal(servicePrice);
    });
</script>



</body>
</html>