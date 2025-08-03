<%-- 
    Document   : cleaning-list
    Created on : Jun 30, 2025, 5:50:54 PM
    Author     : viet7
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/dashboard-layout/header.jsp" %>
<%@ include file="/dashboard-layout/sidebar.jsp" %>
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
            </div> 
            <div class="xp-breadcrumbbar text-center">
                <h4 class="page-title">Dashboard</h4>  
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="#">Dashboard</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Cleaner</li>
                </ol>                
            </div>
        </div>
    </div>

    <!------main-content-start----------->

    <div class="main-content"> 
        <div class="row">
            <div class="col-md-12">
                <div class="table-wrapper">
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

                    <!-- ========== DANH SÁCH PHÒNG CẦN DỌN ========== -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="mb-0">Danh sách phòng cần dọn</h5>
                        </div>
                        <div class="card-body">
                            <table class="table table-bordered table-hover text-center align-middle" style="vertical-align: middle;">
                                <thead class="thead-light">
                                    <tr>
                                        <th>Phòng</th>
                                        <th>Loại</th>
                                        <th>Trạng thái</th>
                                        <th>Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:choose>
                                        <c:when test="${not empty pendingRooms}">
                                            <c:forEach var="room" items="${pendingRooms}">
                                                <tr>
                                                    <td class="align-middle">${room.roomNumber}</td>
                                                    <td class="align-middle">${room.roomType.typeName}</td>
                                                    <td class="align-middle">
                                                        <c:choose>
                                                            <c:when test="${room.status == 'Checkout'}">
                                                                <span class="badge badge-warning" style="font-size: 14px; padding: 6px 12px">
                                                                    Cần dọn dẹp
                                                                </span>
                                                            </c:when>
                                                            <c:when test="${room.status == 'Cleaning'}">
                                                                <span class="badge badge-info" style="font-size: 14px; padding: 6px 12px">
                                                                    Đang dọn dẹp
                                                                </span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge badge-secondary" style="font-size: 14px; padding: 6px 12px">
                                                                    ${room.status}
                                                                </span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <c:if test="${room.status == 'Checkout'}">
                                                        <td class="align-middle">
                                                            <form method="post" action="startCleaning">
                                                                <input type="hidden" name="roomId" value="${room.roomID}" />
                                                                <button class="btn btn-sm btn-primary">Bắt đầu dọn</button>
                                                            </form>
                                                        </td>
                                                    </c:if>
                                                </tr>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                                <td colspan="4" class="text-center">Không có phòng nào cần dọn.</td>
                                            </tr>
                                        </c:otherwise>
                                    </c:choose>
                                </tbody>
                            </table>

                            <!-- PHÂN TRANG -->
                            <c:if test="${totalPendingRooms > pageSize}">
                                <div class="clearfix mt-3">
                                    <div class="hint-text">
                                        Hiển thị <b>${pendingRooms.size()}</b> trong tổng số <b>${totalPendingRooms}</b> phòng cần dọn
                                    </div>
                                    <ul class="pagination justify-content-center">
                                        <c:forEach var="i" begin="1" end="${totalPages}">
                                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                <a href="cleaningTaskList?page=${i}" class="page-link">${i}</a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>
                        </div>
                    </div>

                    <!-- ========== YÊU CẦU DỌN PHÒNG TỪ KHÁCH ========== -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="mb-0">Yêu cầu dọn phòng từ khách</h5>
                        </div>
                        <div class="card-body">
                            <table class="table table-bordered text-center align-middle" style="vertical-align: middle;">
                                <thead class="thead-light">
                                    <tr>
                                        <th>Phòng</th>
                                        <th>Loại</th>
                                        <th>Yêu cầu lúc</th>
                                        <th>Ghi chú</th>
                                        <th>Trạng thái</th>
                                        <th>Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:choose>
                                        <c:when test="${not empty cleaningRequests}">
                                            <c:forEach var="req" items="${cleaningRequests}">
                                                <tr>
                                                    <td class="align-middle">${req.roomNumber}</td>
                                                    <td class="align-middle">${req.roomTypeName}</td>
                                                    <td class="align-middle">
                                                        <fmt:formatDate value="${req.requestedAt}" pattern="HH:mm dd/MM/yyyy" />
                                                    </td>
                                                    <td class="align-middle">${req.note}</td>
                                                    <td class="align-middle">
                                                        <c:choose>
                                                            <c:when test="${req.status == 'Pending'}">
                                                                <span class="badge badge-warning" style="font-size: 14px; padding: 6px 12px">
                                                                    Cần dọn dẹp
                                                                </span>
                                                            </c:when>
                                                            <c:when test="${req.status == 'InProgress'}">
                                                                <span class="badge badge-info" style="font-size: 14px; padding: 6px 12px">
                                                                    Đang dọn dẹp
                                                                </span>
                                                            </c:when>
                                                            <c:when test="${req.status == 'Completed'}">
                                                                <span class="badge badge-success" style="font-size: 14px; padding: 6px 12px">
                                                                    Đã dọn xong
                                                                </span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge badge-secondary" style="font-size: 14px; padding: 6px 12px">
                                                                    ${req.status}
                                                                </span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td class="align-middle">
                                                        <c:if test="${req.status == 'Pending'}">
                                                            <form method="post" action="startCleaningRequest">
                                                                <input type="hidden" name="roomId" value="${req.roomID}" />
                                                                <input type="hidden" name="requestId" value="${req.requestID}" />
                                                                <button class="btn btn-sm btn-primary">Bắt đầu dọn</button>
                                                            </form>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                                <td colspan="6" class="text-center">Không có yêu cầu nào.</td>
                                            </tr>
                                        </c:otherwise>
                                    </c:choose>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!-- ========== DANH SÁCH PHÒNG ĐANG DỌN ========== -->
                    <div class="card">
                        <div class="card-header">
                            <h5 class="mb-0">Danh sách phòng đang dọn</h5>
                        </div>
                        <div class="card-body">
                            <table class="table table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th>Room</th>
                                        <th>Type</th>
                                        <th>Start Time</th>
                                        <th>Note</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:choose>
                                        <c:when test="${not empty inProgressTasks}">
                                            <c:forEach var="task" items="${inProgressTasks}">
                                                <tr>
                                                    <td>${task.room.roomNumber}</td>
                                                    <td>${task.room.roomType.typeName}</td>
                                                    <td>
                                                        <fmt:formatDate value="${task.startTime}" pattern="dd/MM/yyyy HH:mm" />
                                                    </td>
                                                    <td>
                                                        <form method="post" action="finishCleaning">
                                                            <input type="hidden" name="cleaningId" value="${task.cleaningID}" />
                                                            <input type="hidden" name="roomId" value="${task.room.roomID}" />
                                                            <input type="hidden" name="requestId" value="${task.requestID}" />
                                                            <textarea name="note" class="form-control form-control-sm" placeholder="Ghi chú nếu có"></textarea>
                                                    </td>
                                                    <td>
                                                        <button class="btn btn-sm btn-success mt-1">Hoàn tất</button>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                                <td colspan="5" class="text-center">Không có phòng nào đang dọn.</td>
                                            </tr>
                                        </c:otherwise>
                                    </c:choose>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="/dashboard-layout/footer.jsp" %>
