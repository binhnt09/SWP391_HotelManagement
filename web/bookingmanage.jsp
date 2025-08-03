<%-- 
    Document   : room-list
    Created on : Jun 8, 2025, 9:56:55 PM
    Author     : viet7
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/dashboard-layout/header.jsp" %>
<%@ include file="/dashboard-layout/sidebar.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div id="content">

    <!----------------top-navbar---------------->
    <div class="top-navbar">
        <div class="xp-topbar">
            <div class="row"> 
                <div class="col-2 col-md-1 col-lg-1 order-2 order-md-1 align-self-center">
                    <div class="xp-menubar">
                        <span class="material-icons text-white">signal_cellular_alt
                        </span>
                    </div>
                </div> 
                <div class="col-md-5 col-lg-3 order-3 order-md-2">
                    <div class="xp-searchbar">
                        <form method="get" action="bookingcrud">
                            <div class="input-group">
                                <input type="search" class="form-control" name="keyword"
                                       value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>"
                                       placeholder="Search">
                                <div class="input-group-append">
                                    <button class="btn" type="submit" id="button-addon2">GO</button>
                                </div>
                            </div>

                        </form>
                    </div>
                </div>
            </div> 
        </div>
        <div class="xp-breadcrumbbar text-center">
            <h4 class="page-title">Dashboard</h4>  
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="#">Dashboard</a></li>
                <li class="breadcrumb-item active" aria-current="page">Booking</li>
            </ol>                
        </div>

    </div>
    <!------main-content-start----------->


    <div class="main-content"> 
        <div class="row">
            <div class="col-md-12">
                <div class="table-wrapper">
                    <c:if test="${param.success == 'deleted'}">
                        <div class="alert alert-success" role="alert">
                            Xóa thành công!
                        </div>
                    </c:if>
                    <div class="row align-items-end justify-content-between" style="background-color: #F7F8FA">
                        <div class="col-md-9 ms-auto">
                            <div class="row g-2">

                                <div class="col-md-3">
                                    <label class="form-label">Chọn để sắp xếp:</label>
                                    <select class="form-select" id="sortByFilter" onchange="applyFilters()">
                                        <option value="" disabled="">-- Chọn tiêu chí --</option>
                                        <option value="price" ${sortBy == 'b.TotalAmount' ? 'selected' : ''}>Giá phòng</option>
                                        <option value="status" ${sortBy == 'b.BookingDate' ? 'selected' : ''}>Booking date</option>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">Tăng giảm dần:</label>
                                    <select class="form-select" id="sortOrderFilter" onchange="applyFilters()">
                                        <option value="" disabled="">-- Chọn tiêu chí --</option>
                                        <option value="asc" ${isAsc == 'asc' ? 'selected' : ''}>Tăng dần</option>
                                        <option value="desc" ${isAsc == 'desc' ? 'selected' : ''}>Giảm dần</option>
                                    </select>
                                </div>


                                <!-- Phòng -->
                                <div class="col-md-2">
                                    <label class="form-label">Phòng:</label>
                                    <select class="form-select" id="roomFilter" onchange="applyFilters()">
                                        <option value="-1">Tất cả phòng</option>
                                        <c:forEach items="${listRoomBooked}" var="room" >
                                            <option value="${room.roomID}" ${room.roomID == roomId ? 'selected' : ''}>${room.roomNumber}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- Tên khách hàng -->
                                <div class="col-md-4">
                                    <label class="form-label">Tên khách hàng:</label>
                                    <input type="text" value="${keyword}" class="form-control" id="keywordFilter" oninput="applyFilters()" placeholder="Tìm theo tên khách">
                                </div>

                                <!--                                 Ngày check-in 
                                                                <div class="col-md-3">
                                                                    <label class="form-label">Ngày Check-In:</label>
                                                                    <input type="date" class="form-control" id="checkinFilter" onchange="applyFilters()">
                                                                </div>-->

                            </div>
                        </div>

                    </div>
                    <script>
                        function applyFilters() {
                            const roomId = document.getElementById("roomFilter")?.value || "";
                            const keyword = document.getElementById("keywordFilter")?.value || "";
                            const sortBy = document.getElementById("sortByFilter")?.value || "";
                            const sort = document.getElementById("sortOrderFilter")?.value || "";


                            const query = "&roomId=" + encodeURIComponent(roomId) +
                                    "&keyword=" + encodeURIComponent(keyword) +
                                    "&sortBy=" + encodeURIComponent(sortBy) +
                                    "&isAsc=" + encodeURIComponent(sort);
                            console.log(query);
                            window.location.href = "bookingcrud?" + query;

                        }
                    </script>
                    <div class="table-title">
                        <div class="col-md-3 d-flex align-items-center">
                            <h4 class="mb-0">Manage Booking</h4>
                        </div>
                    </div>
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th><input type="checkbox" id="selectAll"></th>
                                <th>BookingId</th>
                                <th>UserId</th>
                                <th>Room</th>
                                <th>BookingDate</th>
                                <th>Check-in</th>
                                <th>Check-out</th>
                                <th>Total amount</th>
                                <th colspan="2">Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty listBooking}">
                                    <c:forEach var="b" items="${listBooking}" varStatus="status">
                                        <%
                                            entity.Booking booking = (entity.Booking) pageContext.getAttribute("b");
                                            entity.BookingDetails detail = new dao.BookingDetailDAO().getBookingDetailByBookingId(booking.getBookingId());

                                            entity.Room room = (entity.Room) detail.getRoom();
                                            entity.User user = new dao.BookingDao().getUserByBookingId(booking.getBookingId());
                                            pageContext.setAttribute("room", room);
                                            pageContext.setAttribute("detail", detail);
                                            pageContext.setAttribute("userInfo", user);
                                        %>
                                        <tr>
                                            <td>
                                                <input type="checkbox" name="roomCheckbox" value="${b.bookingId}">
                                            </td>
                                            <td>${b.bookingId}</td>
                                            <td>${userInfo.firstName} ${userInfo.lastName}</td>
                                            <td>${room.roomNumber}</td>
                                            <td>${b.bookingDate}</td>
                                            <td>${b.checkInDate}</td>
                                            <td>${b.checkOutDate}</td>
                                            <td>${b.totalAmount}</td>

                                            <td>
                                                <c:choose>
                                                    <c:when test="${fn:toLowerCase(b.status) == 'pending'}">
                                                        <button class="btn btn-primary btn-sm" disabled>Pending</button>
                                                    </c:when>

                                                    <c:when test="${fn:toLowerCase(b.status) == 'confirmed'}">
                                                        <button class="btn btn-success btn-sm" disabled>Check-In</button>
                                                    </c:when>

                                                    <c:when test="${fn:toLowerCase(b.status) == 'occupied'}">
                                                        <button class="btn btn-warning btn-sm" disabled>Occupied</button>
                                                        <button class="btn btn-success btn-sm" disabled>Check-Out</button>
                                                    </c:when>

                                                    <c:when test="${fn:toLowerCase(b.status) == 'checkedout'}">
                                                        <button class="btn btn-info btn-sm" disabled>Cleaning</button>
                                                    </c:when>

                                                    <c:when test="${fn:toLowerCase(b.status) == 'bookingdone'}">
                                                        <button class="btn btn-secondary btn-sm" disabled>Cancelled</button>
                                                    </c:when>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="10" style="text-align:center;">Không có booking nào.</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>

                        </tbody>
                    </table>
                    <div class="clearfix">
                        <div class="hint-text">
                            Showing <b>${listBooking.size()}</b> of <b>${totalPages * 5}</b> bookings
                        </div>
                        <ul class="pagination">
                            <c:choose>
                                <c:when test="${currentPage > 1}">
                                    <li class="page-item">
                                        <a href="bookingcrud?page=${currentPage - 1}&keyword=${keyword}" class="page-link">Previous</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item disabled"><span class="page-link">Previous</span></li>
                                    </c:otherwise>
                                </c:choose>

                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="bookingcrud?page=${i}&keyword=${keyword}">${i}</a>
                                </li>
                            </c:forEach>

                            <c:choose>
                                <c:when test="${currentPage < totalPages}">
                                    <li class="page-item">
                                        <a href="bookingcrud?page=${currentPage + 1}&keyword=${keyword}" class="page-link">Next</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item disabled"><span class="page-link">Next</span></li>
                                    </c:otherwise>
                                </c:choose>
                        </ul>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="editbooking" tabindex="-1">
        <div class="modal-dialog">
            <form id="editRoomForm" method="post" action="roomcrud"  enctype="multipart/form-data">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Edit Room</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" name="action" value="edit">
                        <input type="hidden" id="edit-roomID" name="roomID">
                        <input type="hidden" id="edit-roomDetail" name="roomDetail">
                        <div class="mb-3">
                            <label>Room Number</label>
                            <input type="text" id="edit-roomNumber" name="roomNumber" class="form-control">
                        </div>
                        <div class="mb-3">
                            <label>Room Type</label>
                            <input type="text" id="edit-roomType" name="roomTypeID" class="form-control">
                        </div>
                        <div class="mb-3">
                            <label>Bed Type</label>
                            <input type="text" id="edit-bedType" name="bedType" class="form-control">
                        </div>
                        <div class="mb-3">
                            <label>Status</label>
                            <input type="text" id="edit-status" name="status" class="form-control">
                        </div>
                        <div class="mb-3">
                            <label for="photos">Select Img:</label>
                            <input type="file" name="photos" class="form-control" multiple accept="image/*">
                        </div>
                        <div class="mb-3">
                            <label>Description</label>
                            <textarea id="edit-description" name="description" class="form-control"></textarea>
                        </div>
                        <div class="mb-3">
                            <label>Price</label>
                            <input type="number" id="edit-price" name="price" class="form-control">
                        </div>
                        <div class="mb-3">
                            <label>Number Guest</label>
                            <input type="number" id="edit-maxGuest" name="maxGuest" class="form-control">
                        </div>
                        <div class="mb-3">
                            <label>Area</label>
                            <input type="number" step="any" id="edit-area" name="area" class="form-control">
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
    <%@ include file="/dashboard-layout/footer.jsp" %>