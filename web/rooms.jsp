<%-- 
    Document   : rooms
    Created on : May 25, 2025, 12:16:13 PM
    Author     : ASUS
--%>
<%@ page import="java.util.*, java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

                .booking-frame {
                    background-color: #ffffff;
                    border-radius: 12px;
                    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                    padding: 30px;
                    width: 100%;
                    max-width: 1000px;
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
                                        <%
                                            String checkin = request.getParameter("checkin");
                                            if (checkin == null || checkin.trim().isEmpty()) {
                                                Calendar calendar = Calendar.getInstance();
                                                calendar.add(Calendar.DAY_OF_YEAR, 1); // ngày mai
                                                Date tomorrow = calendar.getTime();
                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                checkin = sdf.format(tomorrow);
                                            }
                                            String checkout = request.getParameter("checkout");
                                            if (checkout == null || checkout.trim().isEmpty()) {
                                                Calendar calendar = Calendar.getInstance();
                                                calendar.add(Calendar.DAY_OF_YEAR, 2); // ngày kia
                                                Date afterTomorrow = calendar.getTime();
                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                checkout = sdf.format(afterTomorrow);
                                            }
                                            request.setAttribute("checkin", checkin);
                                            request.setAttribute("checkout", checkout);
                                        %>

                                        <div class="col-4 col-lg-4">
                                            <label style="font-weight: 600;">Nhập check-in:</label>
                                            <input type="date" id="checkInDate" value="${checkin}" name="checkin" required=""  placeholder="Chọn ngày đến" class="form-control time-input" />

                                        </div>
                                        <div class="col-4 col-lg-4">
                                            <label style="font-weight: 600;">Nhập check-out:</label>
                                            <input type="date" id="checkOutDate" value="${checkout}" name="checkout" required=""  placeholder="Chọn ngày đến" class="form-control time-input"  />
                                        </div>
                                        <div class="col-3 col-lg-3">
                                            <label style="font-weight: 600;">Giá mỗi đêm:</label>
                                            <div class="d-flex align-items-center gap-2">
                                                <input type="number" name="pricefrom" value="${from}" step="100" placeholder="$From" class="form-control" style="max-width: 150px;">
                                                <span>&nbsp;&mdash;&nbsp;</span>
                                                <input type="number" name="priceto" value="${to}" step="100" placeholder="$To" class="form-control" style="max-width: 150px;">
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
                            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec malesuada lorem maximus mauris sceleri sque, at rutrum nulla dictum. Ut ac ligula sapien.</p>
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
                        %>
                        <div class="col-12 col-md-6 col-lg-4 mb-5 d-flex align-items-stretch room-card"
                             data-roomid="${i.getRoomID()}"
                             data-roomnumber="${i.getRoomNumber()}"
                             data-price="${i.getPrice()}"
                             data-roomtype="${i.getRoomType().getRoomTypeID()}">
                            <div class="single-rooms-area card shadow w-100 h-85" style="border: none;">
                                <a href="#" onclick="showRoomDetail(this)" data-roomid="${i.roomID}"
                                   title="Click để xem chi tiết" >
                                    <img src="${roomImgList[0].imageURL}" class="card-img-top img-fluid"
                                         style="height: 200px; object-fit: cover;" alt="Room Image">
                                </a>
                                <div class="card-body d-flex flex-column" style="height: 50px">
                                    <p class="price-from text-end mb-2" style="font-weight: bold; color: white;">
                                        From $${i.getPrice()}/night
                                    </p>
                                    <h5 class="card-title">${i.getRoomNumber()} - ${i.getRoomType().getTypeName()}</h5>
                                    <p class="card-text flex-grow-1">${i.getRoomDetail().getDescription()}</p>
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

                // Bỏ lọc, tất cả hiển thị
                rooms.forEach(room => {
                    room.style.display = '';
                });

                // Sắp xếp lại các phòng đang hiển thị
                const visibleRooms = rooms.filter(room => room.style.display !== 'none');

                visibleRooms.sort((a, b) => {
                    let valA = a.dataset[sortBy];
                    let valB = b.dataset[sortBy];

                    // Nếu là số thì parseFloat
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

                // Gắn lại vào container
                const container = document.getElementById('roomListContainer');
                visibleRooms.forEach(room => container.appendChild(room));
            }

            // Gắn sự kiện (không còn searchInput)
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
                            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec malesuada lorem maximus mauris sceleri sque, at rutrum nulla dictum. Ut ac ligula sapien. Suspendisse cursus faucibus finibus. </p>
                        </div>
                    </div>

                    <!-- Footer Widget Area -->
                    <div class="col-12 col-md-6 col-lg-4">
                        <div class="footer-widget-area mt-50">
                            <h6 class="widget-title mb-5">Find us on the map</h6>
                            <img src="img/bg-img/footer-map.png" alt="">
                        </div>
                    </div>
                    <!-- Footer Widget Area -->
                    <div class="col-12 col-md-6 col-lg-3">
                        <div class="footer-widget-area mt-50">
                            <h6 class="widget-title mb-5">Subscribe to our newsletter</h6>
                            <form action="#" method="post" class="subscribe-form">
                                <input type="email" name="subscribe-email" id="subscribeemail" placeholder="Your E-mail">
                                <button type="submit">Subscribe</button>
                            </form>
                        </div>
                    </div>
                    <!-- Copywrite Text -->
                    <div class="col-12">
                        <div class="copywrite-text mt-30">
                            <p><a href="#"><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                                    Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="fa fa-heart-o" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
                                    <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. --></p>
                        </div>
                    </div>
                </div>
            </div>
        </footer>

        <style>

            * {
                font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
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
                flex-shrink: 0;
            }
            .modal-title {
                font-size: 1.5rem;
                font-weight: 600;
                color: #1a1a1a;
            }
            .btn-close {
                font-size: 1.2rem;
                opacity: 0.7;
            }
            .modal-body {
                flex: 1;
                overflow: hidden;
            }

            .carousel-item img {
                width: 100%;              /* Chiếm toàn bộ chiều ngang */
                height: 400px;            /* Cố định chiều cao bạn muốn */
                object-fit: cover;        /* Cắt ảnh để vừa khung mà không bị méo */
                border-radius: 8px;       /* Tuỳ chọn: bo góc đẹp hơn */
            }

            .carousel-inner {
                height: 100%;
            }
            .carousel {
                height: 100%;
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
            .carousel-control-prev {
                left: 20px;
            }
            .carousel-control-next {
                right: 20px;
            }
            .carousel-control-prev-icon,
            .carousel-control-next-icon {
                filter: invert(1);
            }
            .carousel-indicators {
                position: absolute;
                bottom: 20px;
                left: 20px;
                right: auto;
                margin: 0;
                justify-content: flex-start;
            }
            .carousel-indicators [data-bs-target] {
                width: 80px;
                height: 60px;
                border-radius: 8px;
                margin: 0 5px;
                background-size: cover;
                background-position: center;
                opacity: 0.6;
            }
            .carousel-indicators .active {
                opacity: 1;
                border: 2px solid white;
            }

            .room-features {
                padding-right: 1.5rem;
                height: 100%;
                overflow-y: auto;
                max-height: calc(90vh - 120px);
            }
            .room-features::-webkit-scrollbar {
                width: 6px;
            }
            .room-features::-webkit-scrollbar-track {
                background: #f1f1f1;
                border-radius: 10px;
            }
            .room-features::-webkit-scrollbar-thumb {
                background: #c1c1c1;
                border-radius: 10px;
            }
            .room-features::-webkit-scrollbar-thumb:hover {
                background: #a8a8a8;
            }
            .feature-grid {
                display: grid;
                grid-template-columns: 1fr 1fr;
                gap: 1rem;
                margin-bottom: 2rem;
            }
            .feature-section h6 {
                font-weight: 600;
                color: #1a1a1a;
                margin-bottom: 0.75rem;
                font-size: 1rem;
            }
            .feature-list {
                list-style: none;
                padding: 0;
                margin: 0;
            }
            .feature-list li {
                padding: 0.25rem 0;
                color: #4a4a4a;
                font-size: 0.95rem;
                position: relative;
                padding-left: 1.5rem;
            }
            .feature-list li:before {
                content: "•";
                position: absolute;
                left: 0;
                color: #666;
            }
            .room-info {
                border-top: 1px solid #e0e0e0;
                padding-top: 1.5rem;
            }
            .room-title {
                font-size: 1.1rem;
                font-weight: 600;
                color: #1a1a1a;
                margin-bottom: 1rem;
            }
            .price-section {
                margin-top: 1.5rem;
            }
            .price-label {
                font-size: 0.9rem;
                color: #666;
                margin-bottom: 0.5rem;
            }
            .price {
                font-size: 1.5rem;
                font-weight: 700;
                color: #ff6b35;
                margin-bottom: 0.25rem;
            }
            .price-unit {
                font-size: 0.9rem;
                color: #666;
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
            .show-more {
                color: #0ea5e9;
                text-decoration: none;
                font-size: 0.9rem;
                font-weight: 500;
            }
            .show-more:hover {
                color: #0284c7;
            }
            .carousel-caption {
                position: absolute;
                bottom: 20px;
                left: 20px;
                right: auto;
                text-align: left;
                background-color: rgba(0, 0, 0, 0.7);
                padding: 0.5rem 1rem;
                border-radius: 8px;
                color: white;
                font-size: 0.9rem;
            }
            .custom-modal-width {
                max-width: 70%;
                min-height: 90%;
            }

        </style>
        <!--CHI TIẾT PHÒNG-->
        <div class="modal fade" id="roomDetailModal" tabindex="-1" aria-labelledby="roomDetailLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg custom-modal-width">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="roomModalLabel"></h5>
                        <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body p-0">
                        <div class="row g-0">
                            <!-- Image Carousel -->
                            <div class="col-md-8">
                                <div id="roomCarousel" class="carousel slide" data-bs-ride="carousel">
                                    <div class="carousel-inner">
                                        <div class="carousel-item active">
                                            <img src="https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80" class="d-block w-100" alt="Bathroom">
                                        </div>
                                        <div class="carousel-item">
                                            <img src="https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80" class="d-block w-100" alt="Bedroom">
                                        </div>
                                        <div class="carousel-item">
                                            <img src="https://images.unsplash.com/photo-1586023492125-27b2c045efd7?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2058&q=80" class="d-block w-100" alt="Living Area">
                                        </div>
                                        <div class="carousel-item">
                                            <img src="https://images.unsplash.com/photo-1590490360182-c33d57733427?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2074&q=80" class="d-block w-100" alt="Room View">
                                        </div>
                                    </div>

                                    <!-- Navigation arrows -->
                                    <button class="carousel-control-prev" type="button" data-bs-target="#roomCarousel" data-bs-slide="prev">
                                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                        <span class="visually-hidden">Previous</span>
                                    </button>
                                    <button class="carousel-control-next" type="button" data-bs-target="#roomCarousel" data-bs-slide="next">
                                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                        <span class="visually-hidden">Next</span>
                                    </button>

                                    <!-- Indicators with thumbnails -->


                                    <!-- Caption -->
                                    <div class="carousel-caption">
                                        <span>Bathroom</span>
                                        <span class="ms-2">3/4</span>
                                    </div>
                                </div>
                                <div class="carousel-indicators">
                                    <button type="button" data-bs-target="#roomCarousel" data-bs-slide-to="0" class="active" 
                                            style="background-image: url('https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=200&q=80')"></button>
                                    <button type="button" data-bs-target="#roomCarousel" data-bs-slide-to="1"
                                            style="background-image: url('https://images.unsplash.com/photo-1631049307264-da0ec9d70304?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=200&q=80')"></button>
                                    <button type="button" data-bs-target="#roomCarousel" data-bs-slide-to="2"
                                            style="background-image: url('https://images.unsplash.com/photo-1586023492125-27b2c045efd7?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=200&q=80')"></button>
                                    <button type="button" data-bs-target="#roomCarousel" data-bs-slide-to="3"
                                            style="background-image: url('https://images.unsplash.com/photo-1590490360182-c33d57733427?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=200&q=80')"></button>
                                </div>
                            </div>

                            <!-- Room Information -->
                            <div class="col-md-4">
                                <div class="room-features">
                                    <div class="feature-grid">
                                        <div class="feature-section">
                                            <input type="hidden" id="selectedRoomId" >
                                            <h6 id="area">Diện tích: </h6>
                                            <h6 id="numberPeople">Số thành viên: </h6>
                                        </div>
                                        <div class="feature-section">
                                            <ul class="feature-list">
                                                <li>WiFi miễn phí</li>
                                                <li>Dịch vụ phòng</li>
                                            </ul>
                                        </div>
                                        <div class="feature-section">
                                            <ul class="feature-list">
                                                <li>Dọn phòng</li>
                                                <li>Điện thoại</li>
                                            </ul>
                                        </div>
                                    </div>
                                    <hr>
                                    <div class="feature-section">
                                        <h6>Tiện nghi phòng</h6>
                                        <div class="feature-grid">
                                            <div>
                                                <ul id="amenity-left" class="feature-list"></ul>
                                            </div>
                                            <div>
                                                <ul id="amenity-right" class="feature-list"></ul>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="feature-section mt-4">
                                        <h6>Tiện nghi phòng tắm</h6>
                                        <div class="feature-grid">
                                            <div>
                                                <ul class="feature-list">
                                                    <li>Phòng tắm mở bàn phần</li>
                                                    <li>Khăn tắm</li>
                                                    <li>Áo choàng tắm</li>
                                                </ul>
                                            </div>
                                            <div>
                                                <ul class="feature-list">
                                                    <li>Bộ vệ sinh cá nhân</li>
                                                    <li>Máy sấy tóc</li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="room-info">
                                        <div class="room-title">Về phòng này</div>
                                        <div class="mb-3" id="roomDetailDescription"></div>

                                        <div class="price-section">
                                            <div class="price-label">Khởi điểm từ:</div>
                                            <div class="price" id="detailPrice"></div>
                                            <a href="bookingroom?roomID=${i.getRoomID()}&checkin=${checkin}&checkout=${checkout}" class="btn btn-primary btn-book">
                                                Booking now
                                            </a>
                                        </div>
                                    </div>
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

                                                    function showRoomDetail(element) {
                                                        const baseUrl = '${pageContext.request.contextPath}';
                                                        const roomId = element.dataset.roomid;
                                                        currentId = roomId;
                                                        fetch(baseUrl + "/showroomdetail?roomId=" + roomId)
                                                                .then(data => data.json())
                                                                .then(data => {
                                                                    const room = data.room;
                                                                    const detail = data.roomdetail;
                                                                    const type = data.roomtype;


                                                                    console.log(room);
                                                                    console.log(detail);
                                                                    console.log(type);
                                                                    document.getElementById("selectedRoomId").value = roomId;
                                                                    document.getElementById("area").innerText = "Diện tích: " + detail.area;
                                                                    document.getElementById("numberPeople").innerText = "Số thành viên: " + detail.maxGuest;
                                                                    document.getElementById("roomDetailDescription").innerText = detail.description;
                                                                    document.getElementById("detailPrice").innerText = room.price +"VNĐ/Giờ/Ngày    ";
                                                                    document.getElementById("roomModalLabel").innerText = room.roomNumber + "-" + type.typeName;


                                                                    const amenities = type.amenity.split(",").map(a => a.trim());
                                                                    const half = Math.ceil(amenities.length / 2);
                                                                    const left = amenities.slice(0, half);
                                                                    const right = amenities.slice(half);

                                                                    const leftList = document.getElementById("amenity-left");
                                                                    const rightList = document.getElementById("amenity-right");

                                                                    leftList.innerHTML = "";
                                                                    rightList.innerHTML = "";

                                                                    left.forEach(item => {
                                                                        const li = document.createElement("li");
                                                                        li.textContent = item;
                                                                        leftList.appendChild(li);
                                                                    });

                                                                    right.forEach(item => {
                                                                        const li = document.createElement("li");
                                                                        li.textContent = item;
                                                                        rightList.appendChild(li);
                                                                    });
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
        </script>
    </body>
</html>
