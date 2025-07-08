<%-- 
    Document   : room-detail-fragment
    Created on : Jul 8, 2025, 2:40:48 PM
    Author     : viet7
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="row g-4 d-4"> <!-- g-4 tạo khoảng cách giữa 2 cột -->

    <!-- Cột trái: Thông tin phòng + Khách đang ở -->
    <div class="col-md-12">
        <div class="border rounded p-4 shadow-sm bg-light h-100">
            <div class="row">

                <div class="col-md-6">
                    <h5 class="mb-3">
                        <i class="fas fa-door-open me-2 text-primary"></i>
                        Phòng <strong>${room.roomNumber}</strong> - <span class="text-muted">${room.roomType}</span>
                    </h5>

                    <p><i class="fas fa-layer-group text-secondary me-2"></i><strong>Tầng:</strong> ${room.floorNumber}</p>

                    <p>
                        <i class="fas fa-info-circle text-secondary me-2"></i><strong>Trạng thái:</strong>
                        <span class="badge bg-${room.status eq 'Available' ? 'success' : room.status eq 'Occupied' ? 'danger' : 'secondary'}">
                            ${room.status}
                        </span>
                    </p>

                    <p>
                        <i class="fas fa-dollar-sign text-secondary me-2"></i><strong>Giá:</strong>
                        <span class="text-success">
                            <fmt:formatNumber value="${room.price}" type="currency" currencySymbol="₫" />
                        </span>
                    </p>
                </div>

                <div class="col-md-6">
                    <c:if test="${room.status eq 'Occupied' && not empty currentStay}">
                        <div class="border rounded bg-white p-3 shadow-sm h-100">
                            <h6 class="mb-3">
                                <i class="fas fa-user me-1 text-primary"></i> Khách đang ở
                            </h6>
                            <p><strong>Họ tên:</strong> ${currentStay.guestName}</p>
                            <p><strong>Check-in:</strong> 
                                <fmt:formatDate value="${currentStay.checkInDate}" pattern="dd/MM/yyyy" />
                            </p>
                            <p><strong>Check-out:</strong> 
                                <fmt:formatDate value="${currentStay.checkOutDate}" pattern="dd/MM/yyyy" />
                            </p>
                        </div>
                    </c:if>
                </div>
            </div>

            <!-- Nút thao tác -->
            <div class="d-flex flex-wrap gap-2 mt-4">
                <c:choose>
                    <c:when test="${room.status eq 'Available'}">
                        <button class="btn btn-success" onclick="openQuickCheckIn(${room.roomID})">
                            <i class="fas fa-sign-in-alt me-1"></i> Check-in
                        </button>
                        <button class="btn btn-info" onclick="bookRoom(${room.roomID})">
                            <i class="fas fa-calendar-plus me-1"></i> Đặt phòng
                        </button>
                        <button class="btn btn-secondary" onclick="editRoom(${room.roomID})">
                            <i class="fas fa-edit me-1"></i> Sửa phòng
                        </button>
                    </c:when>

                    <c:when test="${room.status eq 'Occupied'}">
                        <button class="btn btn-warning" onclick="checkOut(${room.roomID})">
                            <i class="fas fa-sign-out-alt me-1"></i> Check-out
                        </button>
                        <button class="btn btn-primary" onclick="printInvoice(${room.roomID})">
                            <i class="fas fa-receipt me-1"></i> In hóa đơn
                        </button>
                        <button class="btn btn-secondary" onclick="editBooking(${room.roomID})">
                            <i class="fas fa-user-edit me-1"></i> Cập nhật thông tin khách
                        </button>
                        <button class="btn btn-danger" onclick="callCleaning(${room.roomID})">
                            <i class="fas fa-broom me-1"></i> Gọi dọn phòng
                        </button>
                    </c:when>

                    <c:when test="${room.status eq 'Reserved'}">
                        <button class="btn btn-success" onclick="checkIn(${room.roomID})">
                            <i class="fas fa-sign-in-alt me-1"></i> Nhận phòng
                        </button>
                        <button class="btn btn-danger" onclick="cancelBooking(${room.roomID})">
                            <i class="fas fa-times me-1"></i> Hủy đặt
                        </button>
                        <button class="btn btn-secondary" onclick="editBooking(${room.roomID})">
                            <i class="fas fa-edit me-1"></i> Cập nhật đặt phòng
                        </button>
                    </c:when>

                    <c:when test="${room.status eq 'Cleaning'}">
                        <button class="btn btn-success" onclick="markCleaned(${room.roomID})">
                            <i class="fas fa-check me-1"></i> Đánh dấu đã dọn
                        </button>
                        <button class="btn btn-secondary" disabled>
                            <i class="fas fa-sign-in-alt me-1"></i> Không thể nhận phòng
                        </button>
                    </c:when>

                    <c:when test="${room.status eq 'Non-available'}">
                        <button class="btn btn-success" onclick="endMaintenance(${room.roomID})">
                            <i class="fas fa-tools me-1"></i> Kết thúc bảo trì
                        </button>
                        <button class="btn btn-secondary" onclick="updateMaintenanceReason(${room.roomID})">
                            <i class="fas fa-edit me-1"></i> Lý do bảo trì
                        </button>
                    </c:when>

                    <c:otherwise>
                        <button class="btn btn-secondary" disabled>
                            <i class="fas fa-ban me-1"></i> Không khả dụng
                        </button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>


    <!-- Cột phải: Danh sách đặt phòng -->
    <div class="col-md-12">
        <div class="border rounded p-3 shadow-sm h-100">
            <h6 class="mb-3">
                <i class="fas fa-calendar-alt me-2 text-primary"></i>
                Danh sách đặt / lưu trú
            </h6>

            <c:if test="${not empty futureBookings}">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover table-sm align-middle">
                        <thead class="table-light">
                            <tr>
                                <th>Khách hàng</th>
                                <th>Check-in</th>
                                <th>Check-out</th>
                                <th>Trạng thái</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="b" items="${futureBookings}">
                                <tr>
                                    <td>${b.guestName}</td>
                                    <td><fmt:formatDate value="${b.checkInDate}" pattern="dd/MM/yyyy" /></td>
                                    <td><fmt:formatDate value="${b.checkOutDate}" pattern="dd/MM/yyyy" /></td>
                                    <td>
                                        <span class="badge bg-${b.status eq 'Occupied' ? 'danger' : b.status eq 'Reserved' ? 'warning' : 'secondary'}">
                                            ${b.status}
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>

            <c:if test="${empty futureBookings}">
                <p class="text-muted fst-italic">Chưa có lịch đặt phòng.</p>
            </c:if>
        </div>
    </div>
</div>

<!-- Modal Check-in -->
<div class="modal fade" id="quickCheckInModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <form action="quickCheckIn" method="post" class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Check-in Khách Lẻ</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Đóng">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <input type="hidden" name="roomId" id="quickCheckInRoomId" />

                <div class="mb-3">
                    <label for="firstName" class="form-label">Họ</label>
                    <input type="text" class="form-control" name="firstName" required>
                </div>
                <div class="mb-3">
                    <label for="lastName" class="form-label">Tên</label>
                    <input type="text" class="form-control" name="lastName" required>
                </div>
                <div class="mb-3">
                    <label for="phone" class="form-label">Số điện thoại (nếu có)</label>
                    <input type="text" class="form-control" name="phone">
                </div>
                <div class="mb-3">
                    <label for="nights" class="form-label">Số đêm</label>
                    <input type="number" class="form-control" name="nights" value="1" min="1" required>
                </div>
            </div>
            <div class="modal-footer">
                <button type="s ubmit" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                <button type="submit" class="btn btn-primary">Xác nhận Check-in</button>
            </div>
        </form>
    </div>
</div>

