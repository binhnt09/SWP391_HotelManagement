<%-- 
    Document   : room-list
    Created on : Jun 8, 2025, 9:56:55 PM
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
                        <form method="get" action="roomList">
                            <div class="input-group">
                                <input type="search" class="form-control" name="keyword"
                                       value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>"
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
            <div class="col-md-12">
                <div class="table-wrapper">
                    <c:if test="${param.success == 'deleted'}">
                        <div class="alert alert-success" role="alert">
                            Xóa thành công!
                        </div>
                    </c:if>

                    <div class="table-title">
                        <div class="row">
                            <div class="col-sm-6">
                                <h2 class="ml-lg-2">Manage Rooms</h2>
                            </div>
                            <div class="col-sm-6 text-right">
                                <a href="#addRoomModal" class="btn btn-success" data-toggle="modal">
                                    <i class="material-icons">&#xE147;</i> <span>Add New Room</span>
                                </a>
                                <a href="#deleteRoomModal" class="btn btn-danger" data-toggle="modal">
                                    <i class="material-icons">&#xE15C;</i> <span>Delete</span>
                                </a>
                            </div>
                        </div>
                    </div>
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th><input type="checkbox" id="selectAll"></th>
                                <th>Room Number</th>
                                <th>Type</th>
                                <th>Bed Type</th>
                                <th>Area (m²)</th>
                                <th>Max Guest</th>
                                <th>Price</th>
                                <th>Status</th>
                                <th>Created At</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty roomList}">
                                    <c:forEach var="r" items="${roomList}" varStatus="status">
                                        <tr>
                                            <td>
                                                <input type="checkbox" name="roomCheckbox" value="${r.roomID}">
                                            </td>
                                            <td>${r.roomNumber}</td>
                                            <td>${r.roomTypeID.typeName}</td>
                                            <td>${r.roomDetail.bedType}</td>
                                            <td>${r.roomDetail.area}</td>
                                            <td>${r.roomDetail.maxGuest}</td>
                                            <td>${r.price}</td>
                                            <td>${r.status}</td>
                                            <td>${r.createdAt}</td>
                                            <td>
                                                <a href="room-edit?id=${r.roomID}" class="edit" title="Edit"><i class="material-icons">&#xE254;</i></a>
                                                <a href="roomDelete?id=${r.roomID}" class="delete" title="Delete" onclick="return confirm('Xác nhận xóa phòng này?');"><i class="material-icons">&#xE872;</i></a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="9" style="text-align:center;">Không có phòng nào.</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                    <div class="clearfix">
                        <div class="hint-text">
                            Showing <b>${roomList.size()}</b> of <b>${totalRoom}</b> rooms
                        </div>
                        <ul class="pagination">
                            <c:choose>
                                <c:when test="${currentPage > 1}">
                                    <li class="page-item">
                                        <a href="roomList?page=${currentPage - 1}&keyword=${keyword}" class="page-link">Previous</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item disabled">
                                        <a href="#" class="page-link">Previous</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>

                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a href="roomList?page=${i}&keyword=${keyword}" class="page-link">${i}</a>
                                </li>
                            </c:forEach>

                            <c:choose>
                                <c:when test="${currentPage < totalPages}">
                                    <li class="page-item">
                                        <a href="roomList?page=${currentPage + 1}&keyword=${keyword}" class="page-link">Next</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item disabled">
                                        <a href="#" class="page-link">Next</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/dashboard-layout/footer.jsp" %>