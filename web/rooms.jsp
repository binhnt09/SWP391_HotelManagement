<%-- 
    Document   : rooms
    Created on : May 25, 2025, 12:16:13 PM
    Author     : ASUS
--%>
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
        <!--        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>-->

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
            <!-- ##### Breadcumb Area End ##### -->

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
                                                <input type="date" name="checkin"  placeholder="Chọn ngày đến" class="form-control time-input" />

                                            </div>
                                            <div class="col-4 col-lg-4">
                                                <label style="font-weight: 600;">Nhập check-out:</label>
                                                <input type="date" name="checkout" placeholder="Chọn ngày đi" class="form-control time-input" />
                                            </div>
                                            <div class="col-3 col-lg-3">
                                                <label style="font-weight: 600;">Giá mỗi đêm:</label>
                                                <div class="d-flex align-items-center gap-2">
                                                    <input type="number" name="priceto" step="100" placeholder="$ To" class="form-control" style="max-width: 150px;">
                                                    <span>&nbsp;&mdash;&nbsp;</span>
                                                    <input type="number" name="pricefrom" step="100" placeholder="$ From" class="form-control" style="max-width: 150px;">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">

                                            <div class="col-4 col-lg-4">
                                                <label style="font-weight: 600; font-size: initial; margin-top: 20px">Số lượng thành viên:</label>
                                                <input type="number" name="numberpeople"   placeholder="Nhập số thành viên" class="form-control" />

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

                        <!-- Single Rooms Area -->
                    <c:forEach items="${listRoom}" var="i">
                        <%
                            entity.Room room = (entity.Room) pageContext.getAttribute("i"); 
                            
                            int roomDetailId = room.getRoomDetail().getRoomDetailID();
                            List<entity.RoomImage> roomImgList = new dao.RoomImageDAO().getListRoomImgByDetailID(roomDetailId);
                            pageContext.setAttribute("roomImgList",roomImgList);
                            
                        %>
                        <div class="col-12 col-md-6 col-lg-4">
                            <div class="single-rooms-area wow fadeInUp" data-wow-delay="100ms">
                                <!-- Thumbnail -->
                                <!--                                <a href="#" data-bs-toggle="modal" data-bs-target="#roomDetailModal">
                                                                    <div class="bg-thumbnail bg-img" >
                                                                        <img src="images/ten_anh.jpg" alt="Mô tả hình ảnh" width="200" height="150">
                                                                    </div></a>-->

                                <a href="#" onclick="showRoomDetail(this)" 
                                   data-bs-toggle="modal"
                                   data-bs-target="#roomDetailModal"
                                   data-roomID ="${i.getRoomID()}"
                                   data-roomNumber ="${i.getRoomNumber()}"
                                   data-roomPrice ="${i.price}"
                                   data-bedType ="${i.getRoomDetail().getBedType()}"
                                   data-area ="${i.getRoomDetail().getArea()}"
                                   data-maxGuest ="${i.getRoomDetail().getMaxGuest()}"
                                   data-description ="${i.getRoomDetail().getDescription()}"
                                   data-img="${not empty roomImgList and roomImgList.size() > 0 ? roomImgList[0].imageURL : ''}"
                                   data-img1="${not empty roomImgList and roomImgList.size() > 1 ? roomImgList[1].imageURL : ''}"
                                   data-img2="${not empty roomImgList and roomImgList.size() > 2 ? roomImgList[2].imageURL : ''}"
                                   data-img3="${not empty roomImgList and roomImgList.size() > 3 ? roomImgList[3].imageURL : ''}"

                                   >
                                    <div class="bg-thumbnail bg-img">
                                        <img src="${roomImgList[0].imageURL}" alt="Mô tả hình ảnh" width="200" height="150">
                                    </div>
                                </a>

                                <!-- Price -->
                                <p class="price-from">From $${i.getPrice()}/night</p>
                                <!-- Rooms Text -->
                                <div class="rooms-text">
                                    <div class="line"></div>
                                    <h4>Deluxe Room</h4>
                                    <p>${i.getRoomDetail().getDescription()}.</p>
                                </div>
                                <!-- Book Room -->
                                <a href="#" class="book-room-btn btn palatin-btn">Book Room</a>
                            </div>
                        </div>
                    </c:forEach>



                </div>
            </div>
        </section>
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


        <!--CHI TIẾT PHÒNG-->
        <div class="modal fade" id="roomDetailModal" tabindex="-1" aria-labelledby="roomDetailLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="roomNumber"></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="row modal-body mainbody">
                        <!-- Hình ảnh phòng -->
                        <div class="a col-6 ">
                            <div class="mainImg">
                                <img id="imgDetail"  class="img-fluid" alt="Room luxury" >
                            </div>
                            <div class="moreImg row">
                                <img id="imgDetail1" class="img-fluid" alt="Room luxury">
                                <img id="imgDetail2" class="img-fluid" alt="Room luxury">
                                <img id="imgDetail3" class="img-fluid" alt="Room luxury">
                            </div>
                        </div>

                        <!-- Thông tin chi tiết -->
                        <div class="mainInfo col-6">
                            <h3>Room Information</h3>
                            <input type="hidden" id="typeID">
                            <input type="hidden" id="roomID">

                            <span>
                                <i style="font-size: 28px;" class="fa-duotone fa-solid fa-chart-area"></i>
                            </span> 
                            <p id="area" style="margin-left: 10px;display: inline;"></p>
                            <br>
                            <i style="font-size: 25px;margin-top :8px;" class="fa-duotone fa-solid fa-people-group"></i>
                            <p id="maxGuest" style="margin-left: 8px;display: inline;"></p>
                            <hr style="width: 330px">

                            <h3>Customer Benefits</h3>

                            <div id="description" ></div>
                            <div id="bedType" style="margin-top: 10px"></div>

<!--                            <div style="display: flex; align-items: center; flex-wrap: wrap; margin-top: 10px">
                                <p class="benefit" style="margin: 0; font-weight: 600;">Other: </p>
                                <p id="other" style="margin: 0;">&nbsp;Breakfast, Gym, Pool, Laundry service...</p>
                            </div>-->

                            <p id="price" style="margin-top: 10px;"></p>

                        </div>
                    </div>
                    <div class="modal-footer">
                        <c:if test="${not empty sessionScope.acc && sessionScope.acc.role != null && sessionScope.acc.role == 'Admin'}">
                            <button type="button" class="btn btn-secondary" onclick="deleteRoom()" data-bs-dismiss="modal">Delete</button>

                            <button type="button" onclick="getRoomInfo()" class="btn btn-primary" 
                                    data-bs-toggle="modal"
                                    data-bs-target="#updateroominfo"
                                    >
                                Update
                            </button>
                        </c:if>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                        <a href="bookingguest" style="border-radius: 10px; background-color: #80bdff" class="btn theme_btn button_hover">Book Now</a>
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
        <script src="js/active.js"></script>

  
        <script>
            function showRoomDetail(info){
                let roomID = info.getAttribute("data-roomID");
                let roomNumber = info.getAttribute("data-roomNumber");
                let roomPrice = info.getAttribute("data-roomPrice");
                let bedType = info.getAttribute("data-bedType");
                let area = info.getAttribute("data-area");
                let maxGuest = info.getAttribute("data-maxGuest");
                let description = info.getAttribute("data-description");
                
                let imgSrc = info.getAttribute("data-img");
                let imgSrc1 = info.getAttribute("data-img1");
                let imgSrc2 = info.getAttribute("data-img2");
                let imgSrc3 = info.getAttribute("data-img3");
                
                
                document.getElementById("roomNumber").innerHTML = roomNumber;
                
                //IMG
                document.getElementById("imgDetail").src = imgSrc;
                document.getElementById("imgDetail").style.width = "400px";// Đổi chiều cao thành 200px

                document.getElementById("imgDetail1").src = imgSrc1;
                document.getElementById("imgDetail1").style.width = "130px";
                document.getElementById("imgDetail1").style.height = "90px";
                document.getElementById("imgDetail2").src = imgSrc2;
                document.getElementById("imgDetail2").style.width = "130px";
                document.getElementById("imgDetail2").style.height = "90px";
                document.getElementById("imgDetail3").src = imgSrc3;
                document.getElementById("imgDetail3").style.width = "130px";
                document.getElementById("imgDetail3").style.height = "90px";

                //Room Info
                document.getElementById("area").innerHTML = area;
                document.getElementById("maxGuest").innerHTML = maxGuest;
                document.getElementById("description").innerHTML = description;
                document.getElementById("bedType").innerHTML = bedType;
                document.getElementById("price").innerHTML = roomPrice;
            }
            
            
            $(document).ready(function () {
                // Khởi tạo popup cho nút mở login/register
                $('.login-modal-btn, .register-modal-btn').magnificPopup({
                    type: 'inline',
                    midClick: true
                });

                // Xử lý chuyển đổi giữa login <-> register
                $(document).on('click', '.switch-modal', function (e) {
                    e.preventDefault();
                    const target = $(this).attr('href');

                    // Cập nhật URL hash mà không reload
                    history.pushState(null, '', target);

                    // Đóng popup hiện tại rồi mở cái mới ngay lập tức
                    $.magnificPopup.close();

                    // Mở popup mới ngay lập tức (không delay, không hiệu ứng)
                    $.magnificPopup.open({
                        items: {
                            src: target,
                            type: 'inline'
                        },
                        midClick: true
                    });
                });

                // Khi người dùng nhấn nút back (quay lại)
                window.addEventListener('popstate', function () {
                    const hash = window.location.hash;
                    $.magnificPopup.close();

                    if ($(hash).length) {
                        $.magnificPopup.open({
                            items: {
                                src: hash,
                                type: 'inline'
                            },
                            midClick: true
                        });
                    }
                });

                // Khi người dùng load trang kèm theo #login-modal hoặc #register-modal
                const initialHash = window.location.hash;
                if ($(initialHash).length) {
                    $.magnificPopup.open({
                        items: {
                            src: initialHash,
                            type: 'inline'
                        },
                        midClick: true
                    });
                }
            });
        </script>
</html>
