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
                        <form method="get" action="serviceList">
                            <!-- Giữ lại filter đang có -->
                            <input type="hidden" name="category" value="${param.category}" />
                            <input type="hidden" name="status" value="${param.status}" />
                            <input type="hidden" name="sortField" value="${param.sortField}" />
                            <input type="hidden" name="sortOrder" value="${param.sortOrder}" />
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
