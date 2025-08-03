<%-- 
    Document   : room-list
    Created on : Jun 8, 2025, 9:56:55 PM
    Author     : viet7
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/dashboard-layout/header.jsp" %>
<%@ include file="/dashboard-layout/sidebar.jsp" %>

<style>
    :root {
        --header-bg: #c41e3a;
        --occupied-color: #dc3545;
        --available-color: #28a745;
        --maintenance-color: #6c757d;
        --booking-color: #007bff;
        --checkout-color: #ffc107;
    }

    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
        background-color: #f8f9fa;
    }

    .header {
        background-color: var(--header-bg);
        color: white;
        padding: 8px 15px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }

    .header-title {
        display: flex;
        align-items: center;
        gap: 10px;
    }

    .header-title i {
        font-size: 20px;
    }

    .header-controls {
        display: flex;
        gap: 10px;
    }

    .header-controls .btn {
        padding: 4px 8px;
        font-size: 12px;
    }

    .stats-bar {
        background-color: #343a40;
        color: white;
        padding: 10px 15px;
        display: flex;
        gap: 20px;
        align-items: center;
        font-size: 14px;
    }

    .stat-item {
        display: flex;
        align-items: center;
        gap: 8px;
        padding: 5px 10px;
        border-radius: 4px;
    }

    .stat-item.tong {
        background-color: #4caf50; /* Xanh lá - giống room-card.available */
        color: white;
    }

    .stat-item.dang-o {
        background-color: #e53935; /* Đỏ đậm - giống room-card.occupied */
        color: white;
    }

    .stat-item.kich-hoat {
        background-color: #42a5f5; /* Xanh dương sáng - giống room-card.reserved */
        color: white;
    }

    .stat-item.den-han {
        background-color: #ffc107; /* 🟡 Vàng - Đến hạn trả phòng */
        color: #000; /* Chữ đen dễ đọc trên nền vàng */
    }

    .stat-item.qua-han {
        background-color: #8e24aa; /* 🟣 Tím đậm - Quá hạn chưa trả phòng */
        color: white;
    }

    .stat-item.cho-khach {
        background-color: #0d47a1; /* Xanh dương sáng - giống room-card.reserved */
        color: white;
    }

    .stat-item.chua-don {
        background-color: #fb8c00; /* Cam đậm - giống room-card.cleaning */
        color: white;
    }

    .stat-item.khong-kha-dung {
        background-color: #616161; /* Xám đen - giống room-card.non-available */
        color: white;
    }

    .action-bar {
        background-color: #e9ecef;
        padding: 10px 15px;
        display: flex;
        gap: 10px;
        align-items: center;
    }

    .action-bar .btn {
        padding: 8px 15px;
        font-size: 14px;
    }

    .room-grid {
        padding: 20px;
        background-color: white;
        margin: 0 15px;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }

    .floor-container {
        margin-bottom: 20px;
    }

    .floor-label {
        background-color: #495057;
        color: white;
        padding: 10px 15px;
        font-weight: bold;
        border-radius: 4px 4px 0 0;
        margin-bottom: 0;
    }

    .rooms-row {
        display: flex;
        gap: 2px;
        margin-bottom: 2px;
    }

    .room-card {
        width: 200px;
        height: 100px;
        border: 2px solid #dee2e6;
        border-radius: 8px;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        position: relative;
        cursor: pointer;
        transition: all 0.3s ease;
        font-weight: bold;
    }

    .room-card:hover {
        transform: translateY(-2px);
        box-shadow: 0 4px 8px rgba(0,0,0,0.2);
    }

    .room-card.available {
        background-color: #4caf50; /* Xanh lá - Trống */
        color: white;
    }

    .room-card.occupied {
        background-color: #e53935; /* Đỏ đậm - Có khách */
        color: white;
    }

    .room-card.reserved {
        background-color: #42a5f5; /* Xanh dương sáng - Đặt trước */
        color: white;
    }

    .room-card.cleaning {
        background-color: #fb8c00; /* Cam đậm - Dọn phòng */
        color: white;
    }

    .room-card.non-available {
        background-color: #616161; /* Xám đen - Không khả dụng */
        color: white;
    }

    .room-card.checkout {
        background-color: #ba68c8; /* Tím nhạt - Trả phòng hôm nay */
        color: white;
    }

    .room-card.overdue {
        background-color:  #8e24aa; /* Tím - Quá hạn */
        color: white;
    }

    .rooms-row {
        display: flex;
        overflow-x: auto;
        padding-bottom: 10px;
        scroll-behavior: smooth;
    }

    .room-number {
        font-size: 24px;
        font-weight: bold;
        margin-bottom: 5px;
    }

    .room-info {
        font-size: 11px;
        text-align: center;
        line-height: 1.2;
    }

    .room-status {
        position: absolute;
        top: 5px;
        right: 5px;
        font-size: 10px;
        padding: 2px 6px;
        border-radius: 10px;
        background-color: rgba(255,255,255,0.9);
        color: #000;
    }



    .room-date {
        position: absolute;
        top: 5px;
        left: 5px;
        font-size: 9px;
        padding: 2px 4px;
        border-radius: 4px;
        background-color: rgba(255,255,255,0.9);
        color: #000;
    }

    .booking-source {
        position: absolute;
        bottom: 5px;
        left: 5px;
        font-size: 9px;
        padding: 2px 4px;
        border-radius: 4px;
        background-color: rgba(255,255,255,0.9);
        color: #000;
    }

    .cleaning-icon {
        position: absolute;
        bottom: 5px;
        right: 5px;
        color: #ffc107;
        font-size: 12px;
    }

    .custom-modal-lg {
        max-width: 90% !important;
        width: 70%;
        max-height: 90% !important;
        height: 70%;
    }

    .chat-widget {
        position: fixed;
        bottom: 20px;
        right: 20px;
        background-color: #007bff;
        color: white;
        padding: 10px 15px;
        border-radius: 25px;
        cursor: pointer;
        box-shadow: 0 4px 8px rgba(0,0,0,0.2);
    }

    .skyhotel-support {
        position: fixed;
        bottom: 70px;
        right: 20px;
        background-color: white;
        padding: 8px 12px;
        border-radius: 20px;
        box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        font-size: 12px;
    }

    @media (max-width: 1200px) {
        .room-card {
            width: 180px;
            height: 90px;
        }
    }

    @media (max-width: 768px) {
        .room-card {
            width: 150px;
            height: 80px;
        }

        .room-number {
            font-size: 20px;
        }

        .stats-bar {
            flex-wrap: wrap;
            gap: 10px;
        }
    }
</style>
<div id="content">

    <!----------------top-navbar---------------->
    <div class="top-navbar">
        <div class="xp-topbar">

            <!-- Start XP Row -->
            <div class="row"> 
                <!-- Start XP Col -->
                <div class="col-2 col-md-1 col-lg-1 order-2 order-md-1 align-self-center">
                    <div class="xp-menubar">
                        <span class="material-icons text-white">signal_cellular_alt
                        </span>
                    </div>
                </div> 
                <!-- End XP Col -->

                <!-- Start XP Col -->
                <div class="col-md-5 col-lg-3 order-3 order-md-2">
                    <div class="xp-searchbar">
                        <form method="get" action="receptionistPage">
                            <div class="input-group">
                                <input type="search" class="form-control" name="keyword"
                                       value="${param.keyword}"
                                       placeholder="Search">
                                <div class="input-group-append">
                                    <button class="btn" type="submit" id="button-addon2">GO</button>
                                </div>
                            </div>
<!--                            <input type="hidden" name="role" value="${role}"/>-->

                        </form>
                    </div>
                </div>

                <!-- End XP Col -->

                <!-- Start XP Col -->
                <div class="col-10 col-md-6 col-lg-8 order-1 order-md-3">
                    <div class="xp-profilebar text-right">
                        <nav class="navbar p-0">
                            <ul class="nav navbar-nav flex-row ml-auto">   
                                <li class="dropdown nav-item active">
                                    <a href="#" class="nav-link" data-toggle="dropdown">
                                        <span class="material-icons">notifications</span>
                                        <span class="notification">4</span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a href="#">You have 5 new messages</a>
                                        </li>
                                        <li>
                                            <a href="#">You're now friend with Mike</a>
                                        </li>
                                        <li>
                                            <a href="#">Wish Mary on her birthday!</a>
                                        </li>
                                        <li>
                                            <a href="#">5 warnings in Server Console</a>
                                        </li>

                                    </ul>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">
                                        <span class="material-icons">question_answer</span>

                                    </a>
                                </li>
                                <li class="nav-item dropdown">
                                    <a class="nav-link" href="#" data-toggle="dropdown">
                                        <img src="img/user.jpg" style="width:40px; border-radius:50%;"/>
                                        <span class="xp-user-live"></span>
                                    </a>
                                    <ul class="dropdown-menu small-menu">
                                        <li>
                                            <a href="#">
                                                <span class="material-icons">
                                                    person_outline
                                                </span>Profile

                                            </a>
                                        </li>
                                        <li>
                                            <a href="#"><span class="material-icons">
                                                    settings
                                                </span>Settings</a>
                                        </li>
                                        <li>
                                            <a href="#"><span class="material-icons">
                                                    logout</span>Logout</a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>


                        </nav>

                    </div>
                </div>
                <!-- End XP Col -->

            </div> 
            <!-- End XP Row -->

        </div>
        <div class="xp-breadcrumbbar text-center">
            <h4 class="page-title">Dashboard</h4>  
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="#">Dashboard</a></li>
                <li class="breadcrumb-item active" aria-current="page">Dashboard</li>
            </ol>                
        </div>

    </div>
    <!------main-content-start----------->
    <div class="main-content"> 
        <div class="row">
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
            <div class="col-md-12">
                <div class="table-wrapper">                    
                    <!-- Stats Bar -->
                    <c:set var="stats" value="${roomStats}" />

                    <div class="stats-bar">
                        <div class="stat-item tong">
                            <i class="fas fa-door-open"></i>
                            <span>${stats.availableCount} Trống</span>
                        </div>
                        <div class="stat-item dang-o">
                            <i class="fas fa-user"></i>
                            <span>${stats.occupiedCount} Đang ở</span>
                        </div>
                        <div class="stat-item kich-hoat">
                            <i class="fas fa-check"></i>
                            <span>${stats.reservedCount} Đặt trước</span>
                        </div>
                        <div class="stat-item den-han">
                            <i class="fas fa-clock"></i>
                            <span>${stats.dueTodayCount} Đến hạn trả phòng</span>
                        </div>
                        <div class="stat-item qua-han">
                            <i class="fas fa-exclamation-triangle"></i>
                            <span>${stats.overdueCount} Quá hạn trả phòng</span>
                        </div>
                        <div class="stat-item cho-khach">
                            <i class="fas fa-hourglass-half"></i>
                            <span>${stats.waitingGuestCount} Chờ khách đến</span>
                        </div>
                        <div class="stat-item chua-don">
                            <i class="fas fa-broom"></i>
                            <span>${stats.checkoutCount} Chưa dọn</span>
                        </div>
                        <div class="stat-item khong-kha-dung">
                            <i class="fas fa-tools"></i>
                            <span>${stats.nonAvailableCount} Đang sửa</span>
                        </div>
                        <div class="stat-item">
                            <i class="fas fa-home"></i>
                            <span>${stats.total} Tổng </span>
                        </div>
                    </div>

                    <!-- Action Bar -->
                    <div class="action-bar">
                        <form action="receptionistPage" method="get" class="action-bar">
                            <input type="text" name="keyword" placeholder="Tìm theo tên phòng / tên khách" value="${param.keyword}" />

                            <select name="status">
                                <option value="">-- Trạng thái --</option>
                                <option value="Available" ${param.status == 'Available' ? 'selected' : ''}>Còn trống</option>
                                <option value="Occupied" ${param.status == 'Occupied' ? 'selected' : ''}>Đang ở</option>
                                <option value="Reserved" ${param.status == 'Reserved' ? 'selected' : ''}>Đã đặt</option>
                                <option value="Checkout" ${param.status == 'Checkout' ? 'selected' : ''}>Đã đi</option>
                                <option value="Cleaning" ${param.status == 'Cleaning' ? 'selected' : ''}>Đang dọn</option>
                                <option value="Non-available" ${param.status == 'Non-available' ? 'selected' : ''}>Đang bảo trì</option>
                            </select>

                            <select name="roomType">
                                <option value="">-- Loại phòng --</option>
                                <c:forEach var="type" items="${roomTypes}">
                                    <option value="${type.typeName}" ${param.roomType == type.typeName ? 'selected' : ''}>${type.typeName}</option>
                                </c:forEach>
                            </select>

                            <button type="submit" class="btn btn-primary">Lọc</button>
                        </form>
                    </div>

                    <!-- Room Grid -->
                    <div class="room-grid">
                        <c:forEach var="entry" items="${roomsByFloor}">
                            <div class="floor-container">
                                <div class="floor-label">Lầu ${entry.key}</div>
                                <div class="rooms-row">
                                    <c:forEach var="room" items="${entry.value}">
                                        <div class="room-card ${room.displayStatus.toLowerCase()}" onclick="showRoomDetails(${room.roomID})">
                                            <c:choose>
                                                <c:when test="${not empty room.guestName}">
                                                    <div class="room-date">
                                                        <fmt:formatDate value="${room.checkInDate}" pattern="dd/MM HH:mm" />
                                                        -
                                                        <fmt:formatDate value="${room.checkOutDate}" pattern="dd/MM HH:mm" />
                                                    </div>
                                                    <div class="room-number">${room.roomNumber}</div>
                                                    <div class="room-type text-muted" ">${room.roomType}</div>
                                                    <div class="room-info">${room.guestName}</div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="room-number">${room.roomNumber}</div>
                                                    <div class="room-type text-muted" style="font-size: 0.85em;">${room.roomType}</div>
                                                    <div class="room-info">Phòng trống</div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <!-- Room Details Modal -->
                    <div class="modal fade" id="roomModal" tabindex="-1" role="dialog" aria-labelledby="roomModalLabel" aria-hidden="true">
                        <div class="modal-dialog custom-modal-lg" role="document">
                            <div class="modal-content">

                                <!-- Header -->
                                <div class="modal-header">
                                    <h5 class="modal-title" id="roomModalLabel">Chi tiết phòng</h5>
                                    <!-- Nút đóng đúng chuẩn Bootstrap 4 -->
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Đóng">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>

                                <!-- Body -->
                                <div class="modal-body" >
                                    <div id="roomDetailsContent">
                                        <!-- Nội dung chi tiết phòng sẽ được load ở đây -->
                                    </div>
                                </div>

                                <!-- Footer -->
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                                    <button type="button" class="btn btn-primary">Cập nhật</button>
                                </div>

                            </div>
                        </div>
                    </div>

                    <script>
                        function showRoomDetails(roomNumber) {
                            fetch('roomDetail?roomId=' + roomNumber)
                                    .then(res => res.text())
                                    .then(html => {
                                        document.getElementById('roomDetailsContent').innerHTML = html;
                                        const modal = new bootstrap.Modal(document.getElementById('roomModal'));
                                        modal.show();
                                    });
                        }


                        function openQuickCheckIn(roomId) {
                            document.getElementById("quickCheckInRoomId").value = roomId;
                            const modal = new bootstrap.Modal(document.getElementById("quickCheckInModal"));
                            modal.show();
                        }

                        function confirmCheckOut(roomId, bookingId, checkOutDateStr) {
                            const checkOutDate = new Date(checkOutDateStr);
                            const today = new Date();

                            checkOutDate.setHours(0, 0, 0, 0);
                            today.setHours(0, 0, 0, 0);

                            if (today < checkOutDate) {
                                const confirmEarly = confirm(
                                        "Khách vẫn còn thời gian lưu trú đến " + checkOutDateStr + ".\nBạn có chắc chắn muốn check-out sớm không?"
                                        );
                                if (!confirmEarly)
                                    return;
                            }

                            document.getElementById('checkoutRoomId').value = roomId;
                            document.getElementById('checkoutBookingId').value = bookingId;
                            const modal = new bootstrap.Modal(document.getElementById('confirmCheckoutModal'));
                            modal.show();
                        }

                        function openCheckInModal(roomId, bookingId, guestName, guestPhone, roomNumber, checkInDate, checkOutDate, totalAmount) {
                            console.log('Opening check-in modal with data:', {roomId, bookingId, guestName});
                            document.getElementById('checkInRoomId').value = roomId;
                            document.getElementById('checkInBookingId').value = bookingId;
                            document.getElementById('guestName').textContent = guestName;
                            document.getElementById('guestPhone').textContent = guestPhone;
                            document.getElementById('roomNumber').textContent = roomNumber;
                            document.getElementById('checkInDate').textContent = checkInDate;
                            document.getElementById('checkOutDate').textContent = checkOutDate;
                            document.getElementById('totalAmount').textContent = totalAmount;

                            const modal = new bootstrap.Modal(document.getElementById('confirmCheckInModal'));
                            modal.show();
                        }

                        function confirmCancel(roomId, bookingId) {
                            document.getElementById('cancelRoomId').value = roomId;
                            document.getElementById('cancelBookingId').value = bookingId;
                            const modal = new bootstrap.Modal(document.getElementById('confirmCancelModal'));
                            modal.show();
                        }

                        function confirmSetMaintenance(roomId) {
                            document.getElementById('maintenanceRoomId').value = roomId;
                            const modal = new bootstrap.Modal(document.getElementById('confirmMaintenanceModal'));
                            modal.show();
                        }

                        function confirmFinishMaintenance(roomId) {
                            document.getElementById('finishMaintenanceRoomId').value = roomId;
                            const modal = new bootstrap.Modal(document.getElementById('confirmFinishMaintenanceModal'));
                            modal.show();
                        }

                        function openRequestCleaningModal(roomId, bookingId) {
                            document.getElementById('cleaningRoomId').value = roomId;
                            document.getElementById('cleaningBookingId').value = bookingId;

                            const modal = new bootstrap.Modal(document.getElementById('requestCleaningModal'));
                            modal.show();
                        }

                        setInterval(function () {
                            location.reload();
                        }, 300000);

                        document.addEventListener('keydown', function (e) {
                            if (e.ctrlKey && e.key === 'r') {
                                e.preventDefault();
                                location.reload();
                            }
                        });

                        function updateRoomStatus(roomNumber, newStatus) {
                            const roomCard = document.querySelector(`[onclick="showRoomDetails(${roomNumber})"]`);
                            if (roomCard) {
                                roomCard.className = `room-card ${newStatus}`;
                            }
                        }

                        document.addEventListener('DOMContentLoaded', function () {
                            const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
                            const tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                                return new bootstrap.Tooltip(tooltipTriggerEl);
                            });
                        });

                    </script>

                </div>
            </div>
        </div>
    </div>
    <%@ include file="/dashboard-layout/footer.jsp" %>