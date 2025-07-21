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
                                <a href="#" onclick="showRoomDetail(this, event)" data-roomid="${i.roomID}"
                                   title="Click để xem chi tiết" >
                                    <img src="${roomImgList[0].imageURL}" class="card-img-top img-fluid"
                                         style="height: 200px; object-fit: cover;" alt="Room Image">
                                </a>
                                <div class="card-body d-flex flex-column" style="height: 50px">
                                    <p class="price-from text-end mb-2" style="font-weight: bold; color: white;">
                                        From $${i.getPrice()}/night
                                    </p>
                                    <h5 class="card-title">${i.getRoomNumber()} - ${i.getRoomType().getTypeName()}<div id="avgRatingStars_${i.getRoomID()}" class="mb-2 text-warning d-inline-block"></div></h5>
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
                font-family: 'Inter', sans-serif;
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

            .modal-body {
                overflow: hidden;
                padding: 0;
            }

            .carousel-item img {
                width: 100%;
                height: 400px;
                object-fit: cover;
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

            .room-features {
                max-height: 350px; /* hoặc 100% nếu bạn muốn nó co theo modal */
                overflow-y: auto;
                padding-right: 10px; /* tránh tràn chữ che thanh cuộn */
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
                color: #4a4a4a;
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
                width: 100%;
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
                            <!-- Image Carousel -->
                            <div class="col-md-8">
                                <!-- Carousel chính -->
                                <div id="roomCarousel" class="carousel slide mb-3" data-bs-ride="carousel">
                                    <div class="carousel-inner">
                                        <!-- JS sẽ đẩy ảnh chính vào đây -->
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
                                </div>
                                <button class="thumb-nav right btn btn-outline-secondary ms-2">&gt;</button>
                            </div>
                            <div class="col-md-4 text-center mt-2 mt-md-0">
                                <div class="price-section">
                                    <div class="price-label">Khởi điểm từ:</div>
                                    <div class="" style="color: red" id="detailPrice"></div>
                                    <a href="bookingroom?roomID=${roomId}&checkin=${checkin}&checkout=${checkout}" class="btn btn-primary btn-book">
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

//                                        document.querySelector('.thumb-nav.left').onclick = () => {
//                                            document.getElementById('roomThumbnails').scrollBy({left: -100, behavior: 'smooth'});
//                                        };
//
//                                        document.querySelector('.thumb-nav.right').onclick = () => {
//                                            document.getElementById('roomThumbnails').scrollBy({left: 100, behavior: 'smooth'});
//                                        };

                                        let currentIndex = 0;
                                        const visibleCount = 5;
                                        const thumbWidth = 70;
                                        let totalThumbs = 0;

                                        document.querySelector('.thumb-nav.left').onclick = () => {
                                            if (currentIndex > 0) {
                                                currentIndex--;
                                                updateThumbnailScroll();
                                            }
                                        };

                                        document.querySelector('.thumb-nav.right').onclick = () => {
                                            if (currentIndex < totalThumbs - visibleCount) {
                                                currentIndex++;
                                                updateThumbnailScroll();
                                            }
                                        };

                                        function updateThumbnailScroll() {
                                            const scrollDistance = currentIndex * thumbWidth;
                                            document.getElementById('roomThumbnails').style.transform = `translateX(-${scrollDistance}px)`;
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
                                                        const star = data.roomstar || 0;
                                                        totalThumbs = img.length;


                                                        console.log(room);
                                                        console.log(detail);
                                                        console.log(type);
                                                        console.log(img);
                                                        console.log(star);
                                                        document.getElementById("selectedRoomId").value = roomId;
                                                        document.getElementById("area").innerText = "Diện tích: " + detail.area;
                                                        document.getElementById("numberPeople").innerText = "Số thành viên: " + detail.maxGuest;
                                                        document.getElementById("roomDetailDescription").innerText = detail.description;
                                                        document.getElementById("detailPrice").innerText = room.price + "VNĐ/Giờ/Ngày    ";
                                                        document.getElementById("roomModalLabel").innerText = room.roomNumber + "-" + type.typeName;

                                                        renderStars("avgRatingStars", star);

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

                                                        const carouselInner = document.querySelector('#roomCarousel .carousel-inner');
                                                        const thumbnailContainer = document.getElementById('roomThumbnails');
                                                        carouselInner.innerHTML = '';
                                                        thumbnailContainer.innerHTML = '';

                                                        if (img.length > 0) {
                                                            img.forEach((image, index) => {
                                                                // Carousel item
                                                                const itemDiv = document.createElement('div');
                                                                itemDiv.className = 'carousel-item' + (index === 0 ? ' active' : '');

                                                                const imgTag = document.createElement('img');
                                                                imgTag.src = image.imageURL;
                                                                imgTag.className = 'd-block w-100';
                                                                imgTag.alt = `Room Image ${index + 1}`;
                                                                itemDiv.appendChild(imgTag);
                                                                carouselInner.appendChild(itemDiv);

                                                                // Thumbnail
                                                                const thumbImg = document.createElement('img');
                                                                thumbImg.src = image.imageURL;
                                                                thumbImg.alt = `Thumbnail ${index + 1}`;
                                                                if (index === 0)
                                                                    thumbImg.classList.add('active');
                                                                thumbImg.addEventListener('click', () => {
                                                                    const carousel = bootstrap.Carousel.getOrCreateInstance(document.getElementById('roomCarousel'));
                                                                    carousel.to(index);

                                                                    // Highlight thumbnail
                                                                    document.querySelectorAll('#roomThumbnails img').forEach(img => img.classList.remove('active'));
                                                                    thumbImg.classList.add('active');

                                                                    // Nếu thumbnail này ngoài tầm nhìn thì tự cuộn đến
                                                                    if (index < currentIndex) {
                                                                        currentIndex = index;
                                                                        updateThumbnailScroll();
                                                                    } else if (index >= currentIndex + visibleCount) {
                                                                        currentIndex = index - visibleCount + 1;
                                                                        updateThumbnailScroll();
                                                                    }
                                                                });

                                                                thumbnailContainer.appendChild(thumbImg);
                                                            });
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

                                            starContainer.innerHTML = ""; // Xóa cũ

                                            const fullStars = Math.floor(star);
                                            const halfStar = (star - fullStars >= 0.5);
                                            const emptyStars = 5 - fullStars - (halfStar ? 1 : 0);

                                            for (let i = 0; i < fullStars; i++) {
                                                const starIcon = document.createElement("i");
                                                starIcon.className = "bi bi-star-fill"; // Sao đầy
                                                starContainer.appendChild(starIcon);
                                            }

                                            if (halfStar) {
                                                const starIcon = document.createElement("i");
                                                starIcon.className = "bi bi-star-half"; // Sao nửa
                                                starContainer.appendChild(starIcon);
                                            }

                                            for (let i = 0; i < emptyStars; i++) {
                                                const starIcon = document.createElement("i");
                                                starIcon.className = "bi bi-star"; // Sao rỗng
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