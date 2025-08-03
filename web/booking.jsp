
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

                                <div class="mb-4">
                                    <h5 class="fw-bold mb-3">Thông tin liên hệ </h5>
                                    <p class="text-muted small mb-3">Hãy điền chính xác tất cả thông tin để đảm bảo bạn nhận được Phiếu xác nhận đặt phòng (E-voucher) qua email của mình.</p>

                                    <div class="mb-3">
                                        <label class="form-label fw-semibold">Tên đầy đủ (theo Hộ chiếu/Thẻ căn cước công dân)</label>
                                        <input type="text" id="fullName" name="fullName"
                                               class="form-control"
                                               value="${sessionScope.authLocal != null ? sessionScope.authLocal.user.firstName : ''} ${sessionScope.authLocal != null ? sessionScope.authLocal.user.lastName : ''}"
                                        placeholder="Ví dụ: Nguyễn Văn A"
                                        onblur="normalizeFullName()">
                                    <div class="form-text">Vui lòng chỉ dùng chữ cái (A-Z), không có chức danh, ký tự đặc biệt và dấu câu.</div>
                                    <div id="nameError" class="text-danger small mt-1"></div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label fw-semibold">E-mail</label>
                                    <input type="email"
                                           id="email"
                                           name="email"
                                           value="${sessionScope.authLocal != null ? sessionScope.authLocal.user.email : ''}"
                                           class="form-control"
                                           placeholder="email@example.com"
                                           oninput="validateEmail()"
                                           <c:if test="${sessionScope.authLocal != null}">readonly</c:if>>
                                           <small id="emailError" class="text-danger"></small>
                                    </div>

                                    <!-- PHONE -->
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label fw-semibold">Số điện thoại</label>
                                        <div class="input-group">
                                            <input type="tel"
                                                   id="phone"
                                                   name="phone"
                                                   value="${sessionScope.authLocal != null ? sessionScope.authLocal.user.phone : ''}"
                                            class="form-control"
                                            oninput="validatePhone()">
                                    </div>
                                    <small id="phoneError" class="text-danger"></small>
                                </div>
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
                            <input type="hidden" id="room-ida" value="${room.roomID}" />
                            <c:choose>
                                <c:when test="${sessionScope.authLocal != null}">
                                    <button class="btn btn-danger btn-lg w-100 fw-bold">
                                        <a id="payment-link" class="fw-bold white-color text-decoration-none" href="#">Tiếp tục thanh toán</a>
                                        <script>
                                            document.getElementById('payment-link').addEventListener('click', function (e) {
                                                e.preventDefault();
                                                const roomId = document.getElementById('room-ida').value;

                                                const selectedServices = Array.from(document.querySelectorAll('input[name="services"]:checked'))
                                                        .map(cb => cb.value);
                                                const contextPath = '${pageContext.request.contextPath}';

                                                const url = contextPath + '/payment?roomId=' + roomId + '&services=' + encodeURIComponent(selectedServices.join(','));

                                                window.location.href = url;
                                            });
                                        </script>
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button class="btn btn-danger btn-lg w-100 fw-bold" onclick="requireLogin()">
                                        <span class="fw-bold white-color">Tiếp tục thanh toán</span>
                                    </button>
                                </c:otherwise>
                            </c:choose>
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
                            <div class="d-flex align-items-center mb-3">
                                <span class="text-warning me-2">
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star"></i>
                                </span>
                                <span class="badge bg-primary">${starRoom} (${numberPeopleVote})</span>
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
                                    <div class="fw-bold price-highlight fs-5" id="total-price-description">${totalPrice} VND</div>
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


                        </div>


                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="js/active.js"></script>
    <script src="js/bootstrap/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
    <c:set var="totalPrice" value="${totalPrice}" />
    <script>
                                        function requireLogin() {
                                            alert("Vui lòng đăng nhập để tiếp tục thanh toán.");
                                        }
                                        function validateEmail() {
                                            const emailInput = document.getElementById("email");
                                            const emailError = document.getElementById("emailError");
                                            const email = emailInput.value.trim();

                                            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

                                            if (email === "") {
                                                emailError.textContent = "Vui lòng nhập email!";
                                                emailInput.classList.add("is-invalid");
                                                return false;
                                            }

                                            if (!emailRegex.test(email)) {
                                                emailError.textContent = "Email không hợp lệ!";
                                                emailInput.classList.add("is-invalid");
                                                return false;
                                            }

                                            emailError.textContent = "";
                                            emailInput.classList.remove("is-invalid");
                                            return true;
                                        }

                                        function validatePhone() {
                                            const phoneInput = document.getElementById("phone");
                                            const phoneError = document.getElementById("phoneError");
                                            const phone = phoneInput.value.trim();

                                            const phoneRegex = /^0\d{9}$/;

                                            if (phone === "") {
                                                phoneError.textContent = "Vui lòng nhập số điện thoại!";
                                                phoneInput.classList.add("is-invalid");
                                                return false;
                                            }

                                            if (!phoneRegex.test(phone)) {
                                                phoneError.textContent = "Số điện thoại không hợp lệ (phải có 10 chữ số và bắt đầu bằng 0)!";
                                                phoneInput.classList.add("is-invalid");
                                                return false;
                                            }

                                            phoneError.textContent = "";
                                            phoneInput.classList.remove("is-invalid");
                                            return true;
                                        }
                                        function normalizeFullName() {
                                            const fullNameInput = document.getElementById("fullName");
                                            let fullName = fullNameInput.value.trim();

                                            fullName = fullName.replace(/\s+/g, ' ');

                                            fullName = fullName.split(' ')
                                                    .map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
                                                    .join(' ');

                                            fullNameInput.value = fullName;

                                            validateFullName();
                                        }

                                        function validateFullName() {
                                            const fullNameInput = document.getElementById("fullName");
                                            const nameError = document.getElementById("nameError");
                                            const fullName = fullNameInput.value;
                                            const nameRegex = /^[A-Za-zÀ-ỹà-ỹ\s]+$/;
                                            if (fullName === "") {
                                                nameError.textContent = "Vui lòng nhập họ tên.";
                                                fullNameInput.classList.add("is-invalid");
                                                return false;
                                            }

                                            if (!nameRegex.test(fullName)) {
                                                nameError.textContent = "Tên chỉ được chứa chữ cái và khoảng trắng (không chứa số hoặc ký tự đặc biệt).";
                                                fullNameInput.classList.add("is-invalid");
                                                return false;
                                            }
                                            nameError.textContent = "";
                                            fullNameInput.classList.remove("is-invalid");
                                            return true;
                                        }
    </script>
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
            document.getElementById('total-price-description').innerText = formatCurrency(total);
            sendNewTotalToSession(total);
        }
        const roomId = ${room.roomID};
        fetch(baseUrl + "/bookingroom", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: 'action=addService&roomId='+roomId
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
                });
    </script>



</body>
</html>