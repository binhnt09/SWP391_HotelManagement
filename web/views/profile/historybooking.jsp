<%-- 
    Document   : profile
    Created on : Jun 12, 2025, 10:57:54 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.concurrent.TimeUnit" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="validation.Validation" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>The Palatin - Đặt chỗ của tôi</title>
        <link rel="icon" href="${pageContext.request.contextPath}/img/core-img/favicon.ico">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">



        <style>
            .star-rating {
                direction: rtl;
                display: inline-block;
                cursor: pointer;
            }

            .star-rating input {
                display: none;
            }

            .star-rating label {
                color: #ddd;
                font-size: 24px;
                padding: 0 2px;
                cursor: pointer;
                transition: all 0.2s ease;
            }

            .star-rating label:hover,
            .star-rating label:hover~label,
            .star-rating input:checked~label {
                color: #ffc107;
            }
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
        <jsp:include page="/views/profile/headerprofile.jsp"></jsp:include>

            <div class="container-fluid mt-4">
                <div class="row">
                    <!-- Sidebar -->
                <jsp:include page="/views/profile/sidebarprofile.jsp"></jsp:include>

                    <!-- Main Content -->
                    <div class="col-lg-9 col-md-8 ">
                    <c:if test="${not empty sessionScope.successMessage}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            ${sessionScope.successMessage}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <c:remove var="successMessage" scope="session" />
                    </c:if> 

                    <c:if test="${not empty sessionScope.errorMessage}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            ${sessionScope.errorMessage}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <c:remove var="errorMessage" scope="session" />
                    </c:if>
                    <div class="main-content">
                        <div class="col-lg-9 col-md-8 ">
                            <h2 class="mb-3 fw-bold">Lịch sử giao dịch</h2>
                        </div>
                    </div>
                    <input type="hidden" value="${sessionScope.authLocal.user.userId}" name="currentUserId">
                    <c:forEach items="${listBooking}" var="i">     
                        <%
                            entity.Booking booking = (entity.Booking) pageContext.getAttribute("i");
                            entity.BookingDetails bookingDetail = new dao.BookingDetailDAO().getBookingDetailByBookingId(booking.getBookingId());
                            
                            entity.Room room = (entity.Room) bookingDetail.getRoom();
                            
                            Date checkin = booking.getCheckInDate();
                            Date checkout = booking.getCheckOutDate();
                            long diffInMillies = checkout.getTime() - checkin.getTime();
                            long diffDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                            
                            int rate = (request.getAttribute("rating_"+booking.getBookingId()) != null) ? (int) request.getAttribute("rating_" + booking.getBookingId()) : 0;
                            pageContext.setAttribute("room", room);
                            pageContext.setAttribute("numberNight", diffDays);
                            pageContext.setAttribute("bookingDetail", bookingDetail);
                        %>
                        <div class="container my-4">
                            <div class="row justify-content-center">
                                <div class="col-12 col-xl-10">
                                    <div class="card booking-card h-100">
                                        <!-- Status Badge -->
                                        <span class="badge bg-success status-badge position-absolute top-0 end-0 m-3">
                                            <i class="fas fa-check-circle me-1"></i> ${i.status}
                                        </span>

                                        <div class="row g-0 h-100 align-items-stretch">
                                            <!-- Left Column - Room Info -->
                                            <div class="col-md-4 d-flex">
                                                <div class="card-header w-100 d-flex flex-column justify-content-center text-white font-price"
                                                     style="background: linear-gradient(to bottom right, #5B69E2, #D45CFF); border-top-left-radius: .5rem; border-bottom-left-radius: .5rem;">
                                                    <h5 class="mb-2">Phòng ${room.roomNumber}</h5>

                                                    <!-- Tổng chi phí + vote sao -->
                                                    <!-- Tổng chi phí -->
                                                    <div class="price-highlight mt-3 bg-danger text-white p-3 rounded text-center">
                                                        <div class="info-label text-white-50 mb-1">Tổng chi phí</div>
                                                        <h5 class="mb-0">${i.totalAmount} VNĐ</h5>
                                                        <div class="rating-card p-4" data-roomid="${room.roomID}" data-userid="${sessionScope.authLocal.user.userId}">
                                                            <div class="star-rating animated-stars">
                                                                <input type="radio" id="star5_${i.bookingId}" name="rating_${i.bookingId}" value="5" <%= (rate == 5) ? "checked" : "" %>>
                                                                <label for="star5_${i.bookingId}" class="bi bi-star-fill"></label>

                                                                <input type="radio" id="star4_${i.bookingId}" name="rating_${i.bookingId}" value="4" <%= (rate == 4) ? "checked" : "" %>>
                                                                <label for="star4_${i.bookingId}" class="bi bi-star-fill"></label>

                                                                <input type="radio" id="star3_${i.bookingId}" name="rating_${i.bookingId}" value="3" <%= (rate == 3) ? "checked" : "" %>>
                                                                <label for="star3_${i.bookingId}" class="bi bi-star-fill"></label>

                                                                <input type="radio" id="star2_${i.bookingId}" name="rating_${i.bookingId}" value="2" <%= (rate == 2) ? "checked" : "" %>>
                                                                <label for="star2_${i.bookingId}" class="bi bi-star-fill"></label>

                                                                <input type="radio" id="star1_${i.bookingId}" name="rating_${i.bookingId}" value="1" <%= (rate == 1) ? "checked" : "" %>>
                                                                <label for="star1_${i.bookingId}" class="bi bi-star-fill"></label>
                                                            </div>
                                                        </div>

                                                    </div>
                                                </div>
                                            </div>
                                            <!-- Right Column - Booking Details -->
                                            <div class="col-md-8">
                                                <div class="card-body h-100 d-flex flex-column justify-content-between">
                                                    <!-- Guest Information -->
                                                    <div class="row">
                                                        <div class="col-md-6 mb-3">
                                                            <div class="info-row d-flex align-items-center">
                                                                <div class="icon-circle me-3">
                                                                    <i class="fas fa-user"></i>
                                                                </div>
                                                                <div>
                                                                    <div class="info-label">Customer</div>
                                                                    <div class="info-value">${sessionScope.authLocal.user.firstName} ${sessionScope.authLocal.user.lastName}</div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 mb-3">
                                                            <div class="info-row d-flex align-items-center">
                                                                <div class="icon-circle me-3">
                                                                    <i class="fas fa-users"></i>
                                                                </div>
                                                                <div>
                                                                    <div class="info-label">Số khách</div>
                                                                    <div class="info-value">${room.roomDetail.maxGuest}</div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <!-- Check-in & Check-out -->
                                                    <div class="row">
                                                        <div class="col-md-6 mb-3">
                                                            <div class="info-row d-flex align-items-center">
                                                                <div class="icon-circle me-3">
                                                                    <i class="fas fa-calendar-plus"></i>
                                                                </div>
                                                                <div>
                                                                    <div class="info-label">Ngày nhận phòng</div>
                                                                    <div class="info-value">${i.checkInDate} - 14:00</div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 mb-3">
                                                            <div class="info-row d-flex align-items-center">
                                                                <div class="icon-circle me-3">
                                                                    <i class="fas fa-calendar-minus"></i>
                                                                </div>
                                                                <div>
                                                                    <div class="info-label">Ngày trả phòng</div>
                                                                    <div class="info-value">${i.checkOutDate} - 12:00</div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <!-- Duration & Booking ID -->
                                                    <div class="row">
                                                        <div class="col-md-6 mb-3">
                                                            <div class="info-row d-flex align-items-center">
                                                                <div class="icon-circle me-3">
                                                                    <i class="fas fa-clock"></i>
                                                                </div>
                                                                <div>
                                                                    <div class="info-label">Thời gian lưu trú</div>
                                                                    <div class="info-value">
                                                                        ${numberNight} ngày 
                                                                        <c:choose>
                                                                            <c:when test="${numberNight < 2}">
                                                                                1 đêm
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                ${numberNight - 1} đêm
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6 mb-3">
                                                            <div class="info-row d-flex align-items-center">
                                                                <div>
                                                                    <div class="info-label">Mã đặt phòng</div>
                                                                    <div class="info-value"><i class="fas fa-hashtag"></i>${i.bookingId}</div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <!-- Special Requests & Buttons -->
                                                    <div class="row mt-2 align-items-end">
                                                        <div class="col-md-6">
                                                            <!--                                                            <div class="info-label mb-2">
                                                                                                                            <i class="fas fa-comment-dots me-2"></i>Yêu cầu đặc biệt
                                                                                                                        </div>
                                                                                                                        <div class="info-value">
                                                                                                                            <small class="text-muted">Phòng tầng cao, view biển. Chuẩn bị bánh sinh nhật cho trẻ em.</small>
                                                                                                                        </div>-->
                                                        </div>
                                                        <%
                                                            long soNgay = diffDays;
                                                            String soDem = (soNgay <= 1) ? "1 đêm" : (soNgay - 1) + " đêm";
                                                            String textTongThoiGian = soNgay + " ngày - " + soDem;
                                                            pageContext.setAttribute("numberNightSoon", textTongThoiGian);
                                                        %>
                                                        <div class="col-md-6 d-flex justify-content-end gap-2 mt-3 mt-md-0">
                                                            <button class="btn btn-primary"
                                                                    data-bs-toggle="modal"
                                                                    data-bs-target="#editbooking"
                                                                    data-roomID="${room.roomID}"
                                                                    data-bookingID="${i.bookingId}"
                                                                    data-roomDetail="${room.roomDetail.roomDetailID}"
                                                                    data-roomNumber="${room.roomNumber}"
                                                                    data-status="${i.status}"
                                                                    data-pricePerNight="${i.totalAmount}"
                                                                    data-checkin="${i.checkInDate}"
                                                                    data-checkout="${i.checkOutDate}"
                                                                    data-numberNight="${numberNight}"
                                                                    data-textNumberNight="${numberNightSoon}"
                                                                    data-maxGuest="${room.roomDetail.maxGuest}"
                                                                    title="Edit Room">
                                                                <i class="fas fa-phone"></i>Chỉnh sửa
                                                            </button>
                                                            <button class="btn btn-outline-secondary">
                                                                <i class="fas fa-phone"></i>
                                                            </button>
                                                            <button class="btn btn-outline-danger btn-cancel-booking" data-booking-id="${i.bookingId}">
                                                                <i class="fas fa-times"></i>
                                                            </button>
                                                            <button class="btn btn-outline-secondary" data-bs-toggle="modal" data-bs-target="#modal-${i.bookingId}">
                                                                Dịch vụ
                                                            </button>

                                                            <!-- Modal tương ứng -->
                                                            <div class="modal fade" id="modal-${i.bookingId}" tabindex="-1">
                                                                <div class="modal-dialog modal-lg modal-dialog-scrollable">
                                                                    <div class="modal-content shadow-lg rounded-4">
                                                                        <div class="modal-header bg-primary text-white">
                                                                            <h5 class="modal-title">Chi tiết dịch vụ đã đặt</h5>
                                                                            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                                                                        </div>

                                                                        <div class="modal-body">
                                                                            <!-- Danh sách dịch vụ đã đặt -->
                                                                            <c:choose>
                                                                                <c:when test="${empty i.bookingServices}">
                                                                                    <div class="alert alert-info">Chưa có dịch vụ nào được đặt.</div>
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <table class="table table-bordered align-middle text-center">
                                                                                        <thead class="table-light">
                                                                                            <tr>
                                                                                                <th>Tên dịch vụ</th>
                                                                                                <th>Ngày đặt</th>
                                                                                                <th>Số lượng</th>
                                                                                                <th>Đơn giá</th>
                                                                                                <th>Thành tiền</th>
                                                                                            </tr>
                                                                                        </thead>
                                                                                        <tbody>
                                                                                            <c:set var="totalAmount" value="0" />
                                                                                            <c:forEach var="bs" items="${i.bookingServices}">
                                                                                                <tr>
                                                                                                    <td>${bs.service.name}</td>
                                                                                                    <td>${bs.createdAt}</td>
                                                                                                    <td>${bs.quantity}</td>
                                                                                                    <td>
                                                                                                        <fmt:formatNumber value="${bs.priceAtUse}" type="currency" currencySymbol="₫" groupingUsed="true" maxFractionDigits="0"/>
                                                                                                    </td>
                                                                                                    <td>
                                                                                                        <fmt:formatNumber value="${bs.quantity * bs.priceAtUse}" type="currency" currencySymbol="₫" groupingUsed="true" maxFractionDigits="0"/>
                                                                                                        <c:set var="totalAmount" value="${totalAmount + (bs.quantity * bs.priceAtUse)}" />
                                                                                                    </td>
                                                                                                </tr>
                                                                                            </c:forEach>
                                                                                        </tbody>
                                                                                        <tfoot>
                                                                                            <tr>
                                                                                                <th colspan="4" class="text-end">Tổng cộng:</th>
                                                                                                <th>
                                                                                                    <fmt:formatNumber value="${totalAmount}" type="currency" currencySymbol="₫" groupingUsed="true" maxFractionDigits="0"/>
                                                                                                </th>
                                                                                            </tr>
                                                                                        </tfoot>
                                                                                    </table>
                                                                                </c:otherwise>
                                                                            </c:choose>

                                                                            <!-- Form thêm dịch vụ -->
                                                                            <hr class="my-4" />
                                                                            <h6 class="fw-bold mb-3">Thêm dịch vụ mới</h6>
                                                                            <form action="addBookingService" method="post"
                                                                                  onsubmit="return confirm('Bạn có chắc chắn muốn thêm dịch vụ này? Chi phí sẽ được tính vào hóa đơn khi trả phòng.');">

                                                                                <input type="hidden" name="bookingId" value="${i.bookingId}" />

                                                                                <div class="row mb-3">
                                                                                    <div class="col-md-8">
                                                                                        <label for="serviceId-${i.bookingId}" class="form-label">Chọn dịch vụ:</label>
                                                                                        <select class="form-select" name="serviceId" id="serviceId-${i.bookingId}" required>
                                                                                            <c:forEach var="s" items="${serviceList}">
                                                                                                <c:set var="formattedPrice">
                                                                                                    <fmt:formatNumber value="${s.price}" type="currency" currencySymbol="₫" groupingUsed="true" maxFractionDigits="0"/>
                                                                                                </c:set>
                                                                                                <option value="${s.serviceId}">
                                                                                                    ${s.name} - ${formattedPrice}
                                                                                                </option>
                                                                                            </c:forEach>
                                                                                        </select>
                                                                                    </div>
                                                                                    <div class="col-md-4">
                                                                                        <label class="form-label">Số lượng:</label>
                                                                                        <input type="number" name="quantity" value="1" min="1" class="form-control" required />
                                                                                    </div>
                                                                                </div>

                                                                                <div class="d-grid gap-2">
                                                                                    <button type="submit" class="btn btn-success btn-lg">
                                                                                        <i class="fas fa-plus-circle"></i> Thêm dịch vụ
                                                                                    </button>
                                                                                </div>
                                                                            </form>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div> <!-- end right col -->
                                        </div> <!-- end row -->
                                    </div> <!-- end card -->
                                </div>
                            </div>
                        </div>
                    </c:forEach>   
                </div>
            </div>
        </div>

        <style>
            .custom-modal-width {
                max-width: 75%; /* hoặc 1200px */
            }

        </style>
        <div class="modal fade" id="editbooking" tabindex="-1">
            <div class="modal-dialog custom-modal-width">
                <form id="editRoomForm" method="get" action="bookingroomcustomer"  enctype="multipart/form-data">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Edit Room</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>

                        <div class="modal-body">
                            <input type="hidden" name="action" value="editBook">
                            <input type="hidden" id="bookRoomId" name="roomId">
                            <input type="hidden" id="bookingId" name="bookingId">
                            <input type="hidden" id="bookRoomDetail" name="bookRoomDetail">
                            <input type="hidden" id="bookNumberNight" name="bookNumberNight">

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label>Room Number</label>
                                    <input type="text" id="bookRoomNumber" name="bookRoomNumber" class="form-control" readonly="">
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label>Status</label>
                                    <input type="text" id="bookStatus" name="bookStatus" class="form-control" readonly="">
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label>Total Price</label>
                                    <input type="number" id="bookPricePerNight" name="bookPricePerNight" class="form-control">
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label>Number people</label>
                                    <input type="number" id="bookMaxGuest" name="bookMaxGuest" class="form-control">
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label>Check-in</label>
                                    <input type="date" id="bookCheckin" name="bookCheckin" class="form-control">
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label>Check-out</label>
                                    <input type="date" id="bookCheckout" name="bookCheckout" class="form-control">
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label>Number Night</label>
                                    <input type="text" id="bookTextNumberNight" class="form-control" readonly="">
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                <button type="submit" class="btn btn-primary">Update Room</button>
                            </div>
                        </div>  
                </form>
            </div>
        </div>
        <!-- Footer -->
        <jsp:include page="/views/profile/footerprofile.jsp"></jsp:include>

            <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
            <!-- jQuery (vì Bootstrap 4 phụ thuộc) -->

            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>


            <!-- Popper.js -->
            <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

            <!-- Bootstrap 4 JS -->
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

            <script src="${pageContext.request.contextPath}/js/profile.js"></script>
        <script src="${pageContext.request.contextPath}/js/authentication.js"></script>
        <script>

            $(document).on('click', '[data-bs-toggle="modal"]', function () {
                const button = $(this);
                $('#bookRoomId').val(button.data('roomid'));
                $('#bookingId').val(button.data('bookingid'));
                $('#bookRoomDetail').val(button.data('roomdetail'));
                $('#bookRoomNumber').val(button.data('roomnumber'));
                $('#bookStatus').val(button.data('status'));
                $('#bookPricePerNight').val(button.data('pricepernight'));
                $('#bookMaxGuest').val(button.data('maxguest'));
                $('#bookCheckin').val(button.data('checkin'));
                $('#bookCheckout').val(button.data('checkout'));
                $('#bookNumberNight').val(button.data('numbernight'));
                $('#bookTextNumberNight').val(button.data('textnumbernight'));
            });
            document.querySelectorAll('.star-rating:not(.readonly) label').forEach(star => {
                star.addEventListener('click', function () {
                    this.style.transform = 'scale(1.2)';
                    setTimeout(() => {
                        this.style.transform = 'scale(1)';
                    }, 200);
                });
            });
            document.addEventListener("DOMContentLoaded", () => {
                document.querySelectorAll(".star-rating input[type='radio']").forEach(input => {
                    input.addEventListener("change", function () {
                        const rating = this.value;
                        const bookingId = this.name.split("_")[1];
                        const roomId = this.closest(".rating-card").dataset.roomid;
                        const userId = this.closest(".rating-card").dataset.userid;
                        console.log(rating);
                        console.log(bookingId);
                        console.log(roomId);
                        console.log(userId);
                        const baseUrl = '${pageContext.request.contextPath}';
                        fetch(baseUrl + "/roomreview?roomId=" + roomId + "&userId=" + userId + "&rating=" + rating + "&bookingId=" + bookingId, {
                            method: "POST"
                        })
                                .then(res => res.json())
                                .then(data => {
                                    if (data.success) {
                                        alert("Cảm ơn bạn đã đánh giá!");
                                    } else {
                                        alert("Không thể gửi đánh giá: " + data.message);
                                    }
                                });
                    });
                });
            });
            $(document).on('click', '.btn-cancel-booking', function () {
                const bookingId = $(this).data('booking-id');
                const baseUrl = '${pageContext.request.contextPath}';

                fetch(baseUrl + "/bookingroomcustomer?action=checkCancel&bookingId=" + bookingId)
                        .then(res => res.json())
                        .then(data => {
                            if (data.allowCancel) {
                                const confirmCancel = confirm("Bạn có chắc chắn muốn hủy phòng?");
                                if (confirmCancel) {
                                    fetch(baseUrl + "/bookingroomcustomer?action=cancelBooking&bookingId=" + bookingId, {
                                        method: 'GET'
                                    }).then(() => {
                                        alert("Đã hủy thành công");
                                        location.reload();
                                    });
                                }
                            } else {
                                alert("Bạn không thể hủy vì đã quá hạn cho phép (trước ngày check-in 7 ngày).");
                            }
                        });
            });
        </script>


    </body>
</html>