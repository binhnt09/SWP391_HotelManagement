<%-- 
    Document   : rooms
    Created on : May 25, 2025, 12:16:13 PM
    Author     : ASUS
--%>
<%@ page import="java.util.*, java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="description" content="">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- The above 4 meta tags *must* come first in the head; any other head content must come *after* these tags -->

        <!-- Title -->
        <title>The Palatin - Hotel &amp; Resort Template</title>

        <!-- Favicon -->
        <link rel="icon" href="img/core-img/favicon.ico">

        <!-- Core Stylesheet -->
        <link rel="stylesheet" href="style.css">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">


        <script src="${pageContext.request.contextPath}/assets/js/jquery.magnific-popup.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/magnific-popup.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">

    </head>

    <body>
        <!-- Preloader -->
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
            <section class="breadcumb-area bg-img d-flex align-items-center justify-content-center" style="background-image: url(img/bg-img/bg-6.jpg);">
                <div class="bradcumbContent">
                    <h2>Rooms</h2>
                </div>
            </section>

            <!-- ##### Book Now Area Start ##### -->
            <style>
                body {
                    font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
                }
                .booking-frame {
                    background-color: #ffffff;
                    border-radius: 12px;
                    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                    padding: 30px;
                    width: 100%;
                    max-width: 1000px;
                }
                #avgRatingStars i {
                    font-size: 1.2rem;
                    color: gold;
                }
            </style>
            <div class="book-now-area">
                <div class="container">
                    <div class="row justify-content-center" >
                        <div class="col-12 col-lg-10" >
                            <div class="book-now-form" >
                                <form action="searchroom">
                                    <div class="booking-frame">
                                        <div class="row">


                                            <div class="col-4 col-lg-4">
                                                <label style="font-weight: 600;">Nhập check-in:</label>
                                                <input type="date" id="checkInDate" value="${checkin}" name="checkin" required=""  placeholder="Chọn ngày đến" class="form-control time-input" />

                                        </div>
                                        <div class="col-4 col-lg-4">
                                            <label style="font-weight: 600;">Nhập check-out:</label>
                                            <input type="date" id="checkOutDate" value="${checkout}" name="checkout" required=""  placeholder="Chọn ngày đến" class="form-control time-input"  />
                                        </div>
                                        <div class="col-4 col-lg-4">
                                            <label style="font-weight: 600;">Giá mỗi đêm:</label>
                                            <div class="d-flex align-items-center gap-2">
                                                <input type="number" id="priceFrom"  name="pricefrom" value="${from}" step="100" min="0" placeholder="$From" class="form-control" style="max-width: 200px;">
                                                <span>&nbsp;&mdash;&nbsp;</span>
                                                <input type="number" id="priceTo" name="priceto" value="${to}" step="100" min="0" placeholder="$To" class="form-control" style="max-width: 200px;">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">

                                        <div class="col-4 col-lg-4">
                                            <label style="font-weight: 600; font-size: initial; margin-top: 20px">Số lượng thành viên:</label>
                                            <input type="number" name="numberpeople" value="${numberPeople}"  placeholder="Nhập số thành viên" class="form-control" />
                                        </div>
                                        <div class="col-4 col-lg-4">
                                            <label style="font-weight: 600; font-size: initial; margin-top: 20px">Loại phòng:</label>
                                            <select name="roomType" class="form-select"   >
                                                <option value="-1" >Select room type </option>
                                                <c:forEach items="${listRoomType}" var="tmp">
                                                    <option value="${tmp.roomTypeID}" <c:if test="${tmp.roomTypeID == type}">selected</c:if>>${tmp.typeName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-2 col-lg-2 d-flex align-items-end">
                                            <input type="submit" value="Tìm kiếm" class="btn btn-primary w-100" />
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- ##### Book Now Area End ##### -->
        <!-- ##### Rooms Area Start ##### -->
        <section class="rooms-area section-padding-0-100">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-12 col-lg-6">
                        <div class="section-heading text-center">
                            <div class="line-"></div>
                            <h2>Choose a room</h2>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <!-- Bộ lọc -->
                    <div class="filter-bar d-flex justify-content-end align-items-center gap-2 mb-3">
                        <select id="sortBySelect">
                            <option disabled selected>Select Sort By</option>
                            <option value="roomnumber">Name</option>
                            <option value="price">Price</option>
                        </select>

                        <select id="sortOrderSelect">
                            <option value="asc">Ascending</option>
                            <option value="desc">Descending</option>
                        </select>
                    </div>
                </div>


                <div class="row" id="roomListContainer"  style="margin-top: 30px;">
                    <c:forEach items="${listRoom}" var="i">
                        <%
                            entity.Room room = (entity.Room) pageContext.getAttribute("i");
                            int roomDetailId = room.getRoomDetail().getRoomDetailID();
                            List<entity.RoomImage> roomImgList = new dao.RoomImageDAO().getListRoomImgByDetailID(roomDetailId);
                            pageContext.setAttribute("roomImgList", roomImgList);
                            pageContext.setAttribute("roomId", room.getRoomID());
                            pageContext.setAttribute("listService", new dao.RoomTypeDAO().getServicesByRoomTypeId(room.getRoomType().getRoomTypeID()));
                        %>
                        <div class="col-12 col-md-6 col-lg-4 mb-5 d-flex align-items-stretch room-card"
                             data-roomid="${i.getRoomID()}"
                             data-roomnumber="${i.getRoomNumber()}"
                             data-price="${i.getPrice()}"
                             data-roomtype="${i.getRoomType().getRoomTypeID()}">
                            <div class="single-rooms-area card shadow w-100 h-85" style="border: none;">
                                <a href="#" 
                                   onclick="showRoomDetail(this, event)" 
                                   data-roomid="${i.roomID}" 
                                   data-checkin="${checkin}" 
                                   data-checkout="${checkout}"
                                   title="Click để xem chi tiết">
                                    <img src="${roomImgList[0].imageURL}" class="card-img-top img-fluid"
                                         style="height: 200px; object-fit: cover;" alt="Room Image">
                                </a>
                                <div class="card-body d-flex flex-column" style="height: 50px">
                                    <p class="price-from text-start mb-2" style="font-weight: bold; color: white;">
                                        From <fmt:formatNumber value="${i.price}" type="number" groupingUsed="true" />VND/night
                                    </p>
                                    <h5 class="card-title">${i.getRoomNumber()} - Tầng ${i.getFloorID()} - ${i.getRoomType().getTypeName()}<div id="avgRatingStars_${i.getRoomID()}" class="mb-2 text-warning d-inline-block"></div></h5>
                                    <p class="card-text">${i.getRoomDetail().getDescription()}</p>
                                    <ul class="list-unstyled text-muted fs-6 mt-2 row">
                                        <c:choose>
                                            <c:when test="${not empty listService}">
                                                <c:forEach var="service" items="${listService}">
                                                    <li class="d-flex align-items-center mb-1 col-6"> 
                                                        <i class="bi bi-check-circle-fill text-success me-2"></i> ${service.name}
                                                    </li>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <li class="col-12">
                                                    <i class="bi bi-x-circle text-danger me-2"></i> Không có dịch vụ đi kèm
                                                </li>
                                            </c:otherwise>
                                        </c:choose>
                                    </ul>


                                    <a href="bookingroom?roomID=${i.getRoomID()}&checkin=${checkin}&checkout=${checkout}" class="btn palatin-btn mt-auto">Book Room</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </section>
        <script>
            function filterAndSortRooms() {
                const sortBy = document.getElementById('sortBySelect').value || 'price';
                const sortOrder = document.getElementById('sortOrderSelect').value;

                const rooms = Array.from(document.querySelectorAll('.room-card'));

                rooms.forEach(room => {
                    room.style.display = '';
                });

                const visibleRooms = rooms.filter(room => room.style.display !== 'none');

                visibleRooms.sort((a, b) => {
                    let valA = a.dataset[sortBy];
                    let valB = b.dataset[sortBy];

                    if (sortBy === 'price' || sortBy === 'roomnumber') {
                        valA = parseFloat(valA);
                        valB = parseFloat(valB);
                    }

                    if (valA < valB)
                        return sortOrder === 'asc' ? -1 : 1;
                    if (valA > valB)
                        return sortOrder === 'asc' ? 1 : -1;
                    return 0;
                });

                const container = document.getElementById('roomListContainer');
                visibleRooms.forEach(room => container.appendChild(room));
            }

            document.getElementById('sortBySelect').addEventListener('change', filterAndSortRooms);
            document.getElementById('sortOrderSelect').addEventListener('change', filterAndSortRooms);
        </script>



        <!-- ##### Rooms Area End ##### -->

        <!-- ##### Footer Area Start ##### -->
        <footer class="footer-area">
            <div class="container">
                <div class="row">

                    <!-- Footer Widget Area -->
                    <div class="col-12 col-lg-5">
                        <div class="footer-widget-area mt-50">
                            <a href="#" class="d-block mb-5"><img src="img/core-img/logo.png" alt=""></a>
                        </div>
                    </div>                    
                </div>
            </div>
        </footer>

        <style>
            * {
                font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            }

            body {
                font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
                font-size: 14px;
                line-height: 1.6;
                color: #1a1a1a;
                -webkit-font-smoothing: antialiased;
                -moz-osx-font-smoothing: grayscale;
            }


            .modal-dialog {
                max-width: 1200px;
                height: 90vh;
            }

            .modal-content {
                height: 100%;
            }

            .modal-header {
                border-bottom: none;
                padding: 1.5rem 1.5rem 0;
            }

            .modal-title {
                font-size: 1.5rem;
                font-weight: 600;
                color: #1a1a1a;
            }

            .custom-modal-width {
                max-width: 70%;
                height: 95vh;
            }

            .modal-body {
                padding: 0;
                height: 100%; /* cần chiều cao cố định để chia layout */
                overflow: hidden; /* ẩn scroll tổng thể */
                display: flex;
                width: 100%
            }

            .row.g-0 {
                width: 110%;
                display: flex;
                flex-direction: row;
            }

            .col-md-4 {
                display: flex;
                flex-direction: column;
                height: 100%;
            }

            .room-features {
                flex: 1;
                overflow-y: auto;
                min-height: 0;
                padding-right: 0;
            }

            .carousel-item img {
                width: 100%;
                height: auto;
                object-fit: contain;
                border-radius: 8px;
                display: block;
            }

            .carousel-control-prev,
            .carousel-control-next {
                width: 50px;
                height: 50px;
                background-color: rgba(255, 255, 255, 0.9);
                border-radius: 50%;
                top: 50%;
                transform: translateY(-50%);
            }

            .carousel-control-prev-icon,
            .carousel-control-next-icon {
                filter: invert(1);
            }

            .room-thumbnails {
                display: flex;
                overflow-x: auto;
                scroll-behavior: smooth;
                gap: 8px;
            }

            .room-thumbnails::-webkit-scrollbar {
                display: none;
            }

            .room-thumbnails img {
                width: 80px;
                height: 60px;
                object-fit: cover;
                border: 2px solid transparent;
                cursor: pointer;
                border-radius: 4px;
                flex-shrink: 0;
            }

            .room-thumbnails img.active {
                border: 2px solid #007bff;
            }

            .thumb-nav {
                background-color: rgba(0, 0, 0, 0.3);
                border: none;
                color: white;
                font-size: 20px;
                padding: 4px 10px;
                cursor: pointer;
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                z-index: 10;
            }

            .thumb-nav.left {
                left: -10px;
            }

            .thumb-nav.right {
                right: -10px;
            }



            .feature-grid {
                display: grid;
                grid-template-columns: 1fr 1fr;
                gap: 1rem;
                margin-bottom: 2rem;
            }

            .feature-list {
                list-style: none;
                padding-left: 0;
                margin: 0;
            }

            .feature-list li {
                font-size: 0.95rem;
                color: #2b2b2b;
                padding-left: 1.25rem;
                position: relative;
                margin-bottom: 0.3rem;
            }

            .feature-list li::before {
                content: "•";
                position: absolute;
                left: 0;
                color: #666;
            }

            .room-title {
                font-size: 1.1rem;
                font-weight: 600;
                color: #1a1a1a;
                margin-bottom: 1rem;
            }

            .price {
                font-size: 1.5rem;
                font-weight: 700;
                color: #ff6b35;
                margin-bottom: 0.25rem;
            }

            .btn-book {
                background-color: #0ea5e9;
                border: none;
                padding: 0.75rem 2rem;
                font-size: 1rem;
                font-weight: 600;
                border-radius: 8px;
                width: 100%;
                margin-top: 1rem;
            }

            .btn-book:hover {
                background-color: #0284c7;
            }
            .custom-modal-width {
                max-width: 70%;
                height: 95vh; /* Tăng từ 90% lên 95% chiều cao màn hình */
            }

            .thumbnail-wrapper {
                overflow: hidden;
                width:100%
            }


            .room-thumbnails {
                display: flex;
                transition: transform 0.3s ease;
                gap: 8px;
            }

            .room-thumbnails img {
                height: 60px;
                width: 18%;
                cursor: pointer;
                border: 2px solid transparent;
                border-radius: 4px;
                flex-shrink: 0;
            }

            .room-thumbnails img.active {
                border-color: #007bff;
            }
            .modal-footer {
                padding: 0.5rem 1rem;
                min-height: 100px;
                max-height: 180px;
                overflow-y: auto;
                background-color: #fff;
                border-top: 1px solid #dee2e6;
            }

        </style>

        <!--CHI TIẾT PHÒNG-->
        <div class="modal fade" id="roomDetailModal" tabindex="-1" aria-labelledby="roomDetailLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg custom-modal-width">
                <div class="modal-content">
                    <div class="modal-header">
                        <div class="d-flex align-items-center gap-2">
                            <h5 class="modal-title mb-0" id="roomModalLabel"></h5>
                            <div id="avgRatingStars" class="text-warning mb-0"></div>
                        </div>
                        <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body p-0">
                        <div class="row g-0">
                            <div class="col-md-8">
                                <div id="roomCarousel" class="carousel slide mb-3" data-bs-ride="carousel">
                                    <div class="carousel-inner">
                                    </div>
                                    <button class="carousel-control-prev" type="button" data-bs-target="#roomCarousel" data-bs-slide="prev">
                                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                        <span class="visually-hidden">Previous</span>
                                    </button>
                                    <button class="carousel-control-next" type="button" data-bs-target="#roomCarousel" data-bs-slide="next">
                                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                        <span class="visually-hidden">Next</span>
                                    </button>
                                </div>
                            </div>
                            <!-- Room Information -->
                            <div class="col-md-4">
                                <div class="room-features">
                                    <div class="">
                                        <div class="feature-section">
                                            <input type="hidden" id="selectedRoomId" >
                                            <h6 id="area"></h6>
                                            <h6 id="numberPeople"></h6>
                                        </div>
                                    </div>
                                    <hr style="border-top: 1px solid black">

                                    <div class="feature-section" id="service-container">
                                        <div class="feature-grid"></div>
                                    </div>
                                    <div class="feature-section" id="amenity-container">
                                        <div class="feature-grid" >

                                        </div>
                                    </div>
                                    <div class="room-info">
                                        <div class="room-title">Về phòng này</div>
                                        <div class="mb-3" id="roomDetailDescription"></div>
                                        <div id="avgRatingStars" class="text-warning"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer justify-content-center p-2">
                        <div class="row w-100">
                            <div class="col-md-8 d-flex align-items-center">
                                <button class="thumb-nav left btn btn-outline-secondary me-2">&lt;</button>
                                <div class="thumbnail-wrapper flex-grow-1">
                                    <div class="room-thumbnails" id="roomThumbnails">
                                    </div>
                                    <div id="testImageContainer" style="display: flex; flex-wrap: wrap; gap: 10px;"></div>

                                </div>
                                <button class="thumb-nav right btn btn-outline-secondary ms-2">&gt;</button>
                            </div>
                            <div class="col-md-4 text-center mt-2 mt-md-0">
                                <div class="price-section">
                                    <div class="price-label">Khởi điểm từ:</div>
                                    <div class="" style="color: red" id="detailPrice"></div>
                                    <a id="bookingLink" href="#" class="btn btn-primary btn-book">
                                        Booking now
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- ##### Footer Area End ##### -->

        <!-- ##### All Javascript Script ##### -->
        <!-- jQuery-2.2.4 js -->
        <!-- jQuery của bạn -->
        <script src="js/jquery/jquery-2.2.4.min.js"></script>

        <!-- Thêm Moment + Daterangepicker -->
        <script src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />

        <!-- Popper + Bootstrap + Plugin + Active -->
        <script src="js/bootstrap/popper.min.js"></script>
        <script src="js/bootstrap/bootstrap.min.js"></script>
        <script src="js/plugins/plugins.js"></script>
        <!--<script src="js/active.js"></script>-->


        <script>
                                        document.addEventListener("DOMContentLoaded", function () {
                                            const priceFrom = document.getElementById("priceFrom");
                                            const priceTo = document.getElementById("priceTo");

                                            function validatePriceRange() {
                                                const from = parseInt(priceFrom.value);
                                                const to = parseInt(priceTo.value);

                                                if (!isNaN(from) && !isNaN(to)) {
                                                    if (from < 0)
                                                        priceFrom.value = 0;
                                                    if (to < 0)
                                                        priceTo.value = 0;

                                                    if (from >= to) {
                                                        alert("Giá 'From' phải nhỏ hơn giá 'To'. Vui lòng điều chỉnh.");
                                                        priceTo.value = from + 100;
                                                    }
                                                }
                                            }

                                            priceFrom.addEventListener("change", validatePriceRange);
                                            priceTo.addEventListener("change", validatePriceRange);

                                            const checkInInput = document.getElementById('checkInDate');
                                            const checkOutInput = document.getElementById('checkOutDate');

                                            const today = new Date();
                                            const todayStr = today.toISOString().split('T')[0];
                                            checkInInput.min = todayStr;

                                            const maxCheckIn = new Date(today);
                                            maxCheckIn.setMonth(maxCheckIn.getMonth() + 6);
                                            checkInInput.max = maxCheckIn.toISOString().split('T')[0];

                                            function getNextDay(dateStr, offset = 1) {
                                                const date = new Date(dateStr);
                                                date.setDate(date.getDate() + offset);
                                                return date.toISOString().split('T')[0];
                                            }

                                            function validateDateRange() {
                                                const checkInValue = checkInInput.value;
                                                const checkOutValue = checkOutInput.value;

                                                if (!checkInValue)
                                                    return;

                                                const checkInDate = new Date(checkInValue);
                                                const checkOutDate = checkOutValue ? new Date(checkOutValue) : null;

                                                const maxCheckIn = new Date(today);
                                                maxCheckIn.setMonth(maxCheckIn.getMonth() + 6);

                                                if (checkInDate > maxCheckIn) {
                                                    alert("Bạn không thể đặt phòng cách ngày hôm nay hơn 6 tháng!");
                                                    checkInInput.value = maxCheckIn.toISOString().split('T')[0];
                                                    return;
                                                }

                                                if (checkInDate < today) {
                                                    alert("Ngày nhận phòng không thể trước hôm nay!");
                                                    checkInInput.value = getNextDay(todayStr);
                                                    return;
                                                }

                                                const minCheckOut = getNextDay(checkInValue, 1);
                                                const maxCheckOut = new Date(checkInDate);
                                                maxCheckOut.setMonth(maxCheckOut.getMonth() + 1);

                                                checkOutInput.min = minCheckOut;
                                                checkOutInput.max = maxCheckOut.toISOString().split('T')[0];

                                                if (checkOutDate) {
                                                    if (checkOutDate <= checkInDate) {
                                                        alert("Ngày trả phòng phải sau ngày nhận phòng!");
                                                        checkOutInput.value = null;
                                                    } else if (checkOutDate > maxCheckOut) {
                                                        alert("Ngày trả phòng không được quá 1 tháng sau ngày nhận phòng!");
                                                        checkOutInput.value = null;
                                                    }
                                                }
                                            }

                                            checkInInput.addEventListener('blur', validateDateRange);
                                            checkOutInput.addEventListener('blur', validateDateRange);

                                            if (checkInInput.value) {
                                                validateDateRange();
                                            }
                                        });
                                        let currentIndex = 0;
                                        const visibleCount = 5;
                                        const thumbWidth = 60;
                                        let totalThumbs = 0;
                                        function updateThumbnailScroll() {
                                            const scrollDistance = currentIndex * thumbWidth;
                                            document.getElementById('roomThumbnails').style.transform = "translateX(-" + scrollDistance + "px)";
                                        }
                                        function showRoomDetail(element, event) {
                                            if (event)
                                                event.preventDefault();
                                            const baseUrl = '${pageContext.request.contextPath}';
                                            const roomId = element.dataset.roomid;
                                            fetch(baseUrl + "/showroomdetail?roomId=" + roomId)
                                                    .then(data => data.json())
                                                    .then(data => {
                                                        const room = data.room;
                                                        const detail = data.roomdetail;
                                                        const type = data.roomtype;
                                                        const img = data.roomimg;
                                                        const listAmenity = data.roomamenities;
                                                        const listService = data.listService;
                                                        const star = data.roomstar || 0;
                                                        totalThumbs = img.length;

                                                        document.getElementById("selectedRoomId").value = roomId;
                                                        document.getElementById("area").innerHTML = '<i class="fa-solid fa-chart-area"></i> Diện tích: ' + detail.area;
                                                        document.getElementById("numberPeople").innerHTML = '<i class="fa-solid fa-people-group"></i> Số thành viên: ' + detail.maxGuest;
                                                        document.getElementById("roomDetailDescription").innerText = detail.description;
                                                        document.getElementById("detailPrice").innerText = room.price + "VNĐ/Đêm";
                                                        document.getElementById("roomModalLabel").innerText = room.roomNumber+" - Tầng"+room.floorID + " - " + type.typeName;
                                                        

                                                        const checkin = element.getAttribute('data-checkin');
                                                        const checkout = element.getAttribute('data-checkout');

                                                        const bookingLink = document.getElementById("bookingLink");
                                                        bookingLink.href = "bookingroom?roomID=" + roomId + "&checkin=" + checkin + "&checkout=" + checkout;
                                                        renderStars("avgRatingStars", star);

                                                        const container = document.getElementById("amenity-container");
                                                        container.innerHTML = "";

                                                        Object.entries(listAmenity).forEach(([category, amenities], index) => {
                                                            if (amenities && amenities.length > 0) {

                                                                const title = document.createElement("div");
                                                                title.textContent = category;
                                                                title.style.fontWeight = "bold";
                                                                title.style.fontSize = "16px";
                                                                title.style.marginBottom = "8px";
                                                                container.appendChild(title);

                                                                const grid = document.createElement("div");
                                                                grid.style.display = "grid";
                                                                grid.style.gridTemplateColumns = "1fr 1fr";
                                                                grid.style.gap = "6px 20px";
                                                                grid.style.fontSize = "14px";

                                                                amenities.forEach(item => {
                                                                    const amenityItem = document.createElement("div");
                                                                    amenityItem.textContent = "• " + item.name;
                                                                    grid.appendChild(amenityItem);
                                                                });


                                                                container.appendChild(grid);
                                                                if (container.childElementCount > 0) {
                                                                    const hr = document.createElement("hr");
                                                                    hr.style.border = "0";
                                                                    hr.style.borderTop = "1px solid black";
                                                                    hr.style.margin = "16px 0";
                                                                    container.appendChild(hr);
                                                                }
                                                        }
                                                        });
                                                        const serviceContainer = document.getElementById("service-container");
                                                        serviceContainer.innerHTML = "";

                                                        if (listService && listService.length > 0) {
                                                            const serviceTitle = document.createElement("div");
                                                            serviceTitle.textContent = "Dịch vụ đi kèm";
                                                            serviceTitle.style.fontWeight = "bold";
                                                            serviceTitle.style.fontSize = "16px";
                                                            serviceTitle.style.marginBottom = "8px";
                                                            serviceContainer.appendChild(serviceTitle);

                                                            const serviceGrid = document.createElement("div");
                                                            serviceGrid.style.display = "grid";
                                                            serviceGrid.style.gridTemplateColumns = "1fr 1fr";
                                                            serviceGrid.style.gap = "6px 20px";
                                                            serviceGrid.style.fontSize = "14px";

                                                            listService.forEach(service => {
                                                                const serviceItem = document.createElement("div");
                                                                serviceItem.textContent = "• " + service.name;
                                                                serviceGrid.appendChild(serviceItem);
                                                            });

                                                            serviceContainer.appendChild(serviceGrid);

                                                            const hr = document.createElement("hr");
                                                            hr.style.border = "0";
                                                            hr.style.borderTop = "1px solid black";
                                                            hr.style.margin = "16px 0";
                                                            serviceContainer.appendChild(hr);
                                                        }

                                                        const carouselInner = document.querySelector('#roomCarousel .carousel-inner');
                                                        const thumbnailContainer = document.getElementById('roomThumbnails');
                                                        carouselInner.innerHTML = '';
                                                        thumbnailContainer.innerHTML = '';


                                                        if (img.length > 0) {
                                                            currentIndex = 0;
                                                            img.forEach((image, index) => {
                                                                const itemDiv = document.createElement('div');
                                                                itemDiv.className = 'carousel-item' + (index === 0 ? ' active' : '');

                                                                const imgTag = document.createElement('img');
                                                                imgTag.src = image.imageURL;
                                                                imgTag.className = 'd-block w-100';
                                                                imgTag.alt = "Room Image" + (index + 1);
                                                                itemDiv.appendChild(imgTag);
                                                                carouselInner.appendChild(itemDiv);

                                                                const thumbImg = document.createElement('img');
                                                                thumbImg.src = image.imageURL;
                                                                thumbImg.alt = "Thumbnail " + (index + 1);

                                                                thumbnailContainer.appendChild(thumbImg);

                                                            });


                                                            document.querySelector('.thumb-nav.left').onclick = () => {
                                                                if (currentIndex > 0) {
                                                                    currentIndex--;
                                                                    updateThumbnailScroll();
                                                                }
                                                            };

                                                            document.querySelector('.thumb-nav.right').onclick = () => {
                                                                if (currentIndex <= totalThumbs - visibleCount) {
                                                                    currentIndex++;
                                                                    updateThumbnailScroll();
                                                                }
                                                            };
                                                            updateThumbnailScroll();
                                                        }
                                                    });
                                            const modal = new bootstrap.Modal(document.getElementById('roomDetailModal'));
                                            modal.show();
                                        }


                                        $(document).ready(function () {
                                            $('.single-rooms-area').hover(
                                                    function () {
                                                        $(this).css({
                                                            'transform': 'scale(1.04)',
                                                            'transition': 'transform 0.4s ease'
                                                        });
                                                    },
                                                    function () {
                                                        $(this).css('transform', 'scale(1)');
                                                    }
                                            );
                                        });

                                        $(document).ready(function () {
                                            document.querySelectorAll(".room-card").forEach(card => {
                                                const roomId = card.dataset.roomid;
                                                const baseUrl = '${pageContext.request.contextPath}';
                                                fetch(baseUrl + "/showroomdetail?roomId=" + roomId)
                                                        .then(response => response.json())
                                                        .then(data => {
                                                            const rating = data.roomstar || 0;
                                                            renderStars("avgRatingStars_" + roomId, rating);
                                                        });
                                            });
                                        });
                                        function renderStars(containerId, star) {
                                            const starContainer = document.getElementById(containerId);
                                            if (!starContainer)
                                                return;
                                            starContainer.innerHTML = "";
                                            const fullStars = Math.floor(star);
                                            const halfStar = (star - fullStars >= 0.5);
                                            const emptyStars = 5 - fullStars - (halfStar ? 1 : 0);

                                            for (let i = 0; i < fullStars; i++) {
                                                const starIcon = document.createElement("i");
                                                starIcon.className = "bi bi-star-fill";
                                                starContainer.appendChild(starIcon);
                                            }
                                            if (halfStar) {
                                                const starIcon = document.createElement("i");
                                                starIcon.className = "bi bi-star-half";
                                                starContainer.appendChild(starIcon);
                                            }

                                            for (let i = 0; i < emptyStars; i++) {
                                                const starIcon = document.createElement("i");
                                                starIcon.className = "bi bi-star";
                                                starContainer.appendChild(starIcon);
                                            }
                                            const scoreText = document.createElement("span");
                                            scoreText.className = "ms-2 text-dark";
                                            scoreText.innerText = "(" + star.toFixed(1) + "/5)";
                                            starContainer.appendChild(scoreText);
                                        }

        </script>

    </body>
</html>