<%-- 
    Document   : profile
    Created on : Jun 12, 2025, 10:57:54 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>The Palatin - Đặt chỗ của tôi</title>
        <link rel="icon" href="${pageContext.request.contextPath}/img/core-img/favicon.ico">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">


        <style>
            
            .booking-card {
                border-radius: 15px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                border: none;
                overflow: hidden;
                font-size: 13px
            }
            
            .card-header {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                border: none;
                padding: 20px;
            }
            .room-type {
                background-color: rgba(255,255,255,0.2);
                padding: 5px 15px;
                border-radius: 20px;
                font-size: 0.9rem;
                display: inline-block;
                margin-bottom: 10px;
            }

            .info-row {
                border-bottom: 1px solid #eee;
                padding: 12px 0;
            }
            .info-row:last-child {
                border-bottom: none;
            }
            .info-label {
                color: #6c757d;
                font-weight: 500;
                font-size: 0.9rem;
            }
            .booking-card {
                border-radius: 15px;
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
                border: none;
                overflow: hidden;
                width: 900px; /* hoặc 800px, tùy bạn */
                margin: 0 auto;
            }
            .info-value {
                color: #333;
                font-weight: 600;
            }
            .price-highlight {
                background: linear-gradient(135deg, #ff6b6b, #ee5a52);
                color: white;
                padding: 15px;
                border-radius: 10px;
                text-align: center;
                margin: 15px 0;
            }
            .status-badge {
                position: absolute;
                top: 15px;
                right: 15px;
                z-index: 10;
            }

        </style>
    </head>

    <body>
        <!-- header -->
        <jsp:include page="/profile/headerprofile.jsp"></jsp:include>

            <div class="container-fluid mt-4">
                <div class="row">
                    <!-- Sidebar -->
                <jsp:include page="/profile/sidebarprofile.jsp"></jsp:include>

                    <!-- Main Content -->
                    <div class="col-lg-9 col-md-8 ">
                        <div class="main-content">
                            <div class="col-lg-9 col-md-8 ">
                                <h2 class="mb-3 fw-bold">Lịch sử giao dịch</h2>
                            </div>
                        </div>
                    <c:forEach items="${sessionScope.listBooking}" var="i">     
                        <%
                            entity.Booking booking = (entity.Booking) pageContext.getAttribute("i");
                            entity.BookingDetails detail = new dao.BookingDetailDAO().getBookingDetailByBookingId(booking.getBookingID());
                            
                            entity.Room room = (entity.Room) detail.getRoom();
                            pageContext.setAttribute("room", room);
                            pageContext.setAttribute("detail", detail);
                        %>
                        <div class="row justify-content-center w-25">
                            <div class="col-12 col-xl-10">
                                <div class="card booking-card position-relative">
                                    <!-- Status Badge -->
                                    <span class="badge bg-success status-badge">
                                        <i class="fas fa-check-circle me-1"></i> Đã xác nhận
                                    </span>

                                    <div class="row g-0">
                                        <!-- Left Column - Room Info -->
                                        <div class="col-md-4">
                                            <div class="card-header h-100 d-flex flex-column justify-content-center font-price">
                                                <h5 class="mb-2">Phòng 203B-Superior</h4>

                                                <!-- Price in left column -->
                                                <div class="price-highlight mt-3">
                                                    <div class="info-label text-white-50 mb-1">Tổng chi phí</div>
                                                    <h5 class="mb-0">2.400.000 VNĐ</h3>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- Right Column - Booking Details -->
                                        <div class="col-md-8">
                                            <div class="card-body">
                                                <!-- Guest Information -->
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <div class="info-row d-flex align-items-center">
                                                            <div class="icon-circle me-3">
                                                                <i class="fas fa-user"></i>
                                                            </div>
                                                            <div class="flex-grow-1">
                                                                <div class="info-label">Khách hàng</div>
                                                                <div class="info-value">${sessionScope.authLocal.user.firstName} ${sessionScope.authLocal.user.lastName}</div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <div class="info-row d-flex align-items-center">
                                                            <div class="icon-circle me-3">
                                                                <i class="fas fa-users"></i>
                                                            </div>
                                                            <div class="flex-grow-1">
                                                                <div class="info-label">Số khách</div>
                                                                <div class="info-value">2 người lớn, 1 trẻ em</div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <!-- Check-in & Check-out -->
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <div class="info-row d-flex align-items-center">
                                                            <div class="icon-circle me-3">
                                                                <i class="fas fa-calendar-plus"></i>
                                                            </div>
                                                            <div class="flex-grow-1">
                                                                <div class="info-label">Ngày nhận phòng</div>
                                                                <div class="info-value">25/06/2025 - 14:00</div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <div class="info-row d-flex align-items-center">
                                                            <div class="icon-circle me-3">
                                                                <i class="fas fa-calendar-minus"></i>
                                                            </div>
                                                            <div class="flex-grow-1">
                                                                <div class="info-label">Ngày trả phòng</div>
                                                                <div class="info-value">28/06/2025 - 12:00</div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <!-- Duration & Booking ID -->
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <div class="info-row d-flex align-items-center">
                                                            <div class="icon-circle me-3">
                                                                <i class="fas fa-clock"></i>
                                                            </div>
                                                            <div class="flex-grow-1">
                                                                <div class="info-label">Thời gian lưu trú</div>
                                                                <div class="info-value">3 ngày 2 đêm</div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6">

                                                        <!-- Booking ID -->
                                                        <div class="info-row d-flex align-items-center">
                                                            <div class="icon-circle me-3">
                                                                <i class="fas fa-hashtag"></i>
                                                            </div>
                                                            <div class="flex-grow-1">
                                                                <div class="info-label">Mã đặt phòng</div>
                                                                <div class="info-value">#BK240625001</div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <!-- Special Requests -->
                                                    <div class="row">
                                                        <div class="col-md-6">
                                                            <div class="info-label mb-2">
                                                                <i class="fas fa-comment-dots me-2"></i>Yêu cầu đặc biệt
                                                            </div>
                                                            <div class="info-value">
                                                                <small class="text-muted">Phòng tầng cao, view biển. Chuẩn bị bánh sinh nhật cho trẻ em.</small>
                                                            </div>
                                                        </div>

                                                        <!-- Action Buttons -->
                                                        <div class="col-md-6" style="margin-top: 20px">
                                                            <button class="btn btn-primary btn-booking flex-grow-1">
                                                                <i class="fas fa-edit me-2"></i>Chỉnh sửa
                                                            </button>
                                                            <button class="btn btn-outline-secondary">
                                                                <i class="fas fa-phone"></i>
                                                            </button>
                                                            <button class="btn btn-outline-danger">
                                                                <i class="fas fa-times"></i>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>   
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <!-- Footer -->
        <jsp:include page="/profile/footerprofile.jsp"></jsp:include>

            <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
            <!-- jQuery (vì Bootstrap 4 phụ thuộc) -->
            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

            <!-- Popper.js -->
            <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

            <!-- Bootstrap 4 JS -->
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

            <script src="${pageContext.request.contextPath}/js/profile.js"></script>
        <script src="${pageContext.request.contextPath}/js/authentication.js"></script>

    </body>
</html>

