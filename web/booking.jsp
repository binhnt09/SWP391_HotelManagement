
<%@ page import="java.text.SimpleDateFormat, java.util.*, java.time.*, java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Traveloka - Đặt Phòng Khách Sạn</title>
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
                                        <input type="text" class="form-control" placeholder="Ví dụ: Nguyễn Văn A">
                                        <div class="form-text">Vui lòng chỉ dùng chữ cái (A-Z), không có chức danh, ký tự đặc biệt và dấu câu.</div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label fw-semibold">E-mail</label>
                                            <input type="email" name="email" class="form-control" placeholder="email@example.com">
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label fw-semibold">Số điện thoại</label>
                                            <div class="input-group">
                                                <select class="form-select" style="max-width: 80px;">
                                                    <option>+84</option>
                                                </select>
                                                <input type="tel" class="form-control">
                                            </div>
                                        </div>
                                    </div>

                                    <p class="form-text">Chúng tôi sẽ gửi e-voucher tới email này. +84 123456789 gồm Mã quốc gia (+84) và Số điện thoại (123456789).</p>

                                    <div class="row mt-3">
                                        <div class="col-6">
                                            <div class="card border-primary bg-light">
                                                <div class="card-body text-center p-3">
                                                    <i class="fas fa-phone me-2"></i>
                                                    <span class="small">Tôi là khách lưu trú</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-6">
                                            <div class="card">
                                                <div class="card-body text-center p-3">
                                                    <i class="fas fa-users me-2"></i>
                                                    <span class="small">Tôi đang đặt cho người khác</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Special Requests -->
                                <div class="mb-4">
                                    <h5 class="fw-bold mb-3">Bạn yêu cầu nào không?</h5>
                                    <p class="text-muted small mb-3">Khi nhận phòng, khách sạn sẽ thông báo liệu yêu cầu này có được đáp ứng hay không.</p>

                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-check mb-2">
                                                <input class="form-check-input" type="checkbox" id="nonsmoking">
                                                <label class="form-check-label" for="nonsmoking">Phòng không hút thuốc</label>
                                            </div>
                                            <div class="form-check mb-2">
                                                <input class="form-check-input" type="checkbox" id="highfloor">
                                                <label class="form-check-label" for="highfloor">Tầng lầu</label>
                                            </div>
                                            <div class="form-check mb-2">
                                                <input class="form-check-input" type="checkbox" id="earlycheck">
                                                <label class="form-check-label" for="earlycheck">Gửi nhận phòng</label>
                                            </div>
                                            <div class="form-check mb-2">
                                                <input class="form-check-input" type="checkbox" id="other">
                                                <label class="form-check-label" for="other">Khác</label>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-check mb-2">
                                                <input class="form-check-input" type="checkbox" id="connecting">
                                                <label class="form-check-label" for="connecting">Phòng liền thông</label>
                                            </div>
                                            <div class="form-check mb-2">
                                                <input class="form-check-input" type="checkbox" id="bed">
                                                <label class="form-check-label" for="bed">Loại giường</label>
                                            </div>
                                            <div class="form-check mb-2">
                                                <input class="form-check-input" type="checkbox" id="latecheck">
                                                <label class="form-check-label" for="latecheck">Gửi trả phòng</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                                <!-- Price Details -->
                                <div class="mb-4">
                                    <h5 class="fw-bold mb-3">Chi tiết giá</h5>
                                    <div class="card">
                                        <div class="card-body">
                                            <div class="d-flex justify-content-between mb-2">
                                                <span>Giá phòng</span>
                                                <span>${totalPrice} VND</span>
                                        </div>
                                        <div class="text-muted small mb-2">${room.getRoomNumber()}-${room.getRoomTypeID().getTypeName()} (${numberNight} đêm)</div>
                                        <div class="d-flex justify-content-between mb-3">
                                            <span>Thuế và phí</span>
                                            <span>993.074 VND</span>
                                        </div>
                                        <hr>
                                        <div class="d-flex justify-content-between fw-bold fs-5 price-highlight">
                                            <span>Tổng giá</span>
                                            <span>${totalPrice} VND</span>
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

                            <div class="fw-bold mb-1">${room.getRoomNumber()}-${room.getRoomTypeID().getTypeName()}</div>

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
                                        String checkoutStr = request.getParameter("checkout"); // ví dụ: "2025-06-25"
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

    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>