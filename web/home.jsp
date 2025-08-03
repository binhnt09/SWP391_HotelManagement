<%-- 
    Document   : home
    Created on : May 25, 2025, 12:14:11 PM
    Author     : ASUS
--%>

<%@ page import="java.util.*, java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.HotelDAO" %>
<%@ page import="entity.Hotel" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html>
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
        <jsp:include page="common/header.jsp"></jsp:include>
            <!-- ##### Header Area End ##### -->

            <!-- ##### Hero Area Start ##### -->
            <section >

                <div class="single-hero-slide d-flex align-items-center justify-content-center">
                    <!-- Slide Img -->
                    <div class="slide-img bg-img" style="background-image: url(img/bg-img/bg-1.jpg);"></div>
                    <div class="container">
                        <div class="row justify-content-center">
                            <div class="col-12 col-lg-9">
                                <!-- Slide Content -->
                                <div class="hero-slides-content"  >
                                    <div class="line" ></div>
                                    <h2 data-animation="fadeInUp">The Palatin Hotel </h2>
                                    <p class=" palatin-btn mt-50" data-animation="fadeInUp" data-delay="900ms">Hãy đặt phòng với tôi</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <!-- ##### Hero Area End ##### -->

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


                                            <div class="col-4 col-lg-4">
                                                <label style="font-weight: 600;">Nhập check-in:</label>
                                                <input type="date" id="checkInDate" value="${checkin}" name="checkin" required=""  placeholder="Chọn ngày đến" class="form-control time-input" />

                                        </div>
                                        <div class="col-4 col-lg-4">
                                            <label style="font-weight: 600;">Nhập check-out:</label>
                                            <input type="date" id="checkOutDate" value="${checkout}" name="checkout" required=""  placeholder="Chọn ngày đến" class="form-control time-input"  />
                                        </div>
                                        <div class="col-4 col-lg-4">
                                            <label style="font-weight: 600;">Giá mỗi đêm (VND):</label>
                                            <div class="d-flex align-items-center gap-2">
                                                <input type="number" id="priceFrom"  name="pricefrom" value="${from}" step="100" min="0" placeholder="From" class="form-control" style="max-width: 200px;">
                                                <span>&nbsp;&mdash;&nbsp;</span>
                                                <input type="number" id="priceTo" name="priceto" value="${to}" step="100" min="0" placeholder="To" class="form-control" style="max-width: 200px;">
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
        <section class="about-us-area">
            <div class="container">
                <div class="row align-items-center">

                    <div class="col-12 col-lg-6">
                        <div class="about-text text-center mb-100">
                            <div class="section-heading text-center">
                                <div class="line-"></div>
                                <h2>A place to remember</h2>
                            </div>
                            <%
                                HotelDAO dao = new HotelDAO();               
                                Hotel hotel = dao.getHotelByID(1);           
                                request.setAttribute("hotel", hotel);        
                            %>
                            <p>${hotel.description}.</p>
                            <div class="about-key-text">
                                <h6><span class="fa fa-check"></span> Không gian tiện nghi, hiện đại và ấm cúng</h6>
                            </div>
                            <!--                            <a href="#" class="btn palatin-btn mt-50">Read More</a>-->
                        </div>
                    </div>

                    <div class="col-12 col-lg-6">
                        <div class="about-thumbnail homepage mb-100">
                            <div class="first-img wow fadeInUp" data-wow-delay="100ms">
                                <img src="img/bg-img/5.jpg" alt="">
                            </div>
                            <div class="second-img wow fadeInUp" data-wow-delay="300ms">
                                <img src="img/bg-img/6.jpg" alt="">
                            </div>
                            <div class="third-img wow fadeInUp" data-wow-delay="500ms">
                                <img src="img/bg-img/7.jpg" alt="">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- ##### Rooms Area End ##### -->

        <!-- ##### Contact Area Start ##### -->
        <section class="contact-area d-flex flex-wrap align-items-center">
            <div class="home-map-area">
                <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1862.2472218121568!2d105.52364820648101!3d21.012893195137924!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3135abc60e7d3f19%3A0x2be9d7d0b5abcbf4!2zVHLGsOG7nW5nIMSQ4bqhaSBo4buNYyBGUFQgSMOgIE7hu5lp!5e0!3m2!1svi!2s!4v1754225321883!5m2!1svi!2s" width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>            </div>
            <!-- Contact Info -->
            <div class="contact-info">
                <div class="section-heading wow fadeInUp" data-wow-delay="100ms">
                    <div class="line-"></div>
                    <h2>Contact Info</h2>
                </div>
                <h4 class="wow fadeInUp" data-wow-delay="300ms">Group 3 - SWP391</h4>
                <h5 class="wow fadeInUp" data-wow-delay="400ms">+84 32 863 3494</h5>
                <h5 class="wow fadeInUp" data-wow-delay="500ms">dominhdangcap2@gmail.com</h5>
                <!-- Social Info -->
                <div class="social-info mt-50 wow fadeInUp" data-wow-delay="600ms">
                    <a href="#"><i class="fa fa-pinterest" aria-hidden="true"></i></a>
                    <a href="#"><i class="fa fa-facebook" aria-hidden="true"></i></a>
                    <a href="#"><i class="fa fa-twitter" aria-hidden="true"></i></a>
                    <a href="#"><i class="fa fa-dribbble" aria-hidden="true"></i></a>
                    <a href="#"><i class="fa fa-behance" aria-hidden="true"></i></a>
                    <a href="#"><i class="fa fa-linkedin" aria-hidden="true"></i></a>
                </div>
            </div>
        </section>
        <!-- ##### Contact Area End ##### -->

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
        <script src="js/active.js"></script>

        <script>
        </script>
        <!-- Script khởi tạo -->
        <script>
            $(document).ready(function () {
                $('.single-rooms-area').hover(
                        function () {
                            $(this).css({
                                'transform': 'scale(1.03)',
                                'transition': 'transform 0.3s ease'
                            });
                        },
                        function () {
                            $(this).css('transform', 'scale(1)');
                        }
                );
            });

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

                // Cấu hình ngày tối thiểu và tối đa cho ngày check-in
                const today = new Date();
                const todayStr = today.toISOString().split('T')[0];
                checkInInput.min = todayStr;

                const maxCheckIn = new Date(today);
                maxCheckIn.setMonth(maxCheckIn.getMonth() + 6);
                checkInInput.max = maxCheckIn.toISOString().split('T')[0];

                // Hàm hỗ trợ cộng thêm ngày
                function getNextDay(dateStr, offset = 1) {
                    const date = new Date(dateStr);
                    date.setDate(date.getDate() + offset);
                    return date.toISOString().split('T')[0];
                }

                // Hàm kiểm tra hợp lệ ngày
                function validateDateRange() {
                    const checkInValue = checkInInput.value;
                    const checkOutValue = checkOutInput.value;

                    // Nếu chưa nhập ngày check-in thì không cần kiểm tra gì cả
                    if (!checkInValue)
                        return;

                    const checkInDate = new Date(checkInValue);
                    const checkOutDate = checkOutValue ? new Date(checkOutValue) : null;

                    // Giới hạn ngày check-in
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

        </script>
    </body>
</html>