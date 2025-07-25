<%-- 
    Document   : rmanageroom
    Created on : Jun 8, 2025, 8:46:42 PM
    Author     : Admin
--%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <meta charset="UTF-8">
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- The above 4 meta tags *must* come first in the head; any other head content must come *after* these tags -->

        <!-- Title -->
        <title>The Palatin - Hotel &amp; Resort Template</title>

        <link rel="icon" href="img/core-img/favicon.ico">

        <!-- Core Stylesheet -->
        <link rel="stylesheet" href="style.css">
        <!--        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>-->

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">

        <script src="${pageContext.request.contextPath}/assets/js/jquery.magnific-popup.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/magnific-popup.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
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
            /* Áp dụng font và màu cho toàn bộ modal content */
            .modal-content {
                font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif !important;
                font-size: 14px !important;
                color: #1a1a1a !important;
            }


            /* Áp dụng cho button trong modal nếu cần */
            .modal-footer .btn {
                font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif !important;
            }

        </style>
    </head>
    <body>
        <jsp:include page="common/header.jsp"></jsp:include>
            <div class="container" style="margin-top: 200px">
                <div class="table-wrapper">
                    <div class="content-header mb-30">
                        <ul class="nav nav-tabs" id="accountTabs" role="tablist">
                            <li class="nav-item" role="presentation">
                                <button class="nav-link ${empty openTab || openTab == '#managerRoom' ? 'active' : ''}"
                                    id="managerRoom-tab" data-bs-toggle="tab"
                                    data-bs-target="#managerRoom" type="button" role="tab">
                                Room 
                            </button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link ${openTab == '#managerRoomType' ? 'active' : ''}" 
                                    id="managerRoomType-tab" data-bs-toggle="tab"
                                    data-bs-target="#managerRoomType" type="button" role="tab">
                                RoomType
                            </button>
                        </li>
                    </ul>
                </div>
                <div class="tab-content" id="accountTabsContent">

                    <div class="tab-pane fade ${empty openTab || openTab == '#managerRoom' ? 'show active' : ''}" 
                         id="managerRoom" role="tabpanel" aria-labelledby="managerRoom-tab">
                        <form action="roomcrud" method="get" id="deleteSelect">
                            <div class="row align-items-center mb-3 g-2">

                                <!-- Add / Delete Buttons -->
                                <div class="col-auto">
                                    <a href="#addroom" data-bs-toggle="modal" class="btn btn-success">
                                        <i class="fas fa-plus me-1"></i> Add New Room
                                    </a>
                                </div>
                                <div class="col-auto">
                                    <button type="button" onclick="return confirmDeleteSelected();" class="btn btn-danger">
                                        <i class="fas fa-trash me-1"></i> DELETE
                                    </button>
                                </div>
                                <div class="col-auto">
                                    <button type="button" onclick="return confirmRestoreSelected();" class="btn btn-success">
                                        <i class="fas fa-undo me-1"></i> RESTORE
                                    </button>
                                </div>
                            </div>
                        </form>
                        <form action="manageroom" method="get" >
                            <div class="row align-items-center mb-3 g-2">
                                <!-- Search input -->
                                <div class="col-auto">
                                    <input type="hidden" name="action" value="filterRoom">
                                    <input type="hidden" name="page" value="${current}">
                                    <input type="text" id="searchInput" name="keyWord" value="${keyWord}" 
                                           placeholder="Search Room" class="form-control form-control-sm" style="width: 150px;">
                                </div>
                                <!-- Sort By -->
                                <div class="col-auto">
                                    <select name="sortBy" class="form-select form-select-sm">
                                        <option disabled ${sortBy == null ? "selected" : ""}>Sort by</option>
                                        <option value="RoomNumber" ${sortBy.equals("RoomNumber") ? "selected" : ""}>Name</option>
                                        <option value="Price" ${sortBy.equals("Price") ? "selected" : ""}>Price</option>
                                    </select>
                                </div>

                                <!-- Room Type -->
                                <div class="col-auto">
                                    <select name="roomType" class="form-select form-select-sm">
                                        <option value="all" ${roomType == "all" ? "selected" : ""}>All</option>
                                        <c:forEach items="${listRoomType}" var="i">
                                            <option value="${i.roomTypeID}" ${roomType != null && roomType == String.valueOf(i.roomTypeID) ? "selected" : ""}>
                                                ${i.typeName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <!-- Sort Order -->
                                <div class="col-auto">
                                    <select name="sort" class="form-select form-select-sm">
                                        <option value="asc" ${sort.equals("asc") ? "selected" : ""}>↑ Tăng Dần</option>
                                        <option value="desc" ${sort.equals("desc") ? "selected" : ""}>↓ Giảm dần</option>
                                    </select>
                                </div>

                                <!-- View Deleted -->
                                <div class="col-auto d-flex align-items-center">
                                    <input type="checkbox" class="form-check-input me-1" id="viewDeleted" name="presentDeleted"
                                           ${presentDeleted.equals("1") ? "checked" : ""} value="1">
                                    <label class="form-check-label small" for="viewDeleted">Deleted?</label>
                                </div>

                                <!-- Submit -->
                                <div class="col-auto">
                                    <input type="submit" value="Submit" class="btn btn-outline-secondary btn-sm">
                                </div>

                                <!-- Tổng số phòng -->
                                <div class="col-auto ms-auto text-muted">
                                    <small>Tổng cộng: ${numberRoom} phòng</small>
                                </div>
                            </div>
                        </form>


                        <table class="table table-striped table-hover">
                            <thead class="table-dark">
                                <tr>
                                    <th>
                                        <span class="custom-checkbox">
                                            <input type="checkbox" id="selectAll" name="selectAll">
                                            <label for="selectAll"></label>
                                        </span>
                                    </th>
                                    <th>STT</th>
                                    <th>Name</th>
                                    <th>RoomType</th>
                                    <th>Status</th>
                                    <th>Area(m²)</th>
                                    <th>Price</th>
                                    <th>Amount</th>
                                    <th>Description</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>

                                <c:forEach items="${listRoom}" var="i" varStatus="index">

                                    <tr>

                                        <td>
                                            <span class="custom-checkbox">
                                                <input type="checkbox" name="roomIds" class="room-checkbox" value="${i.roomID}">
                                            </span>
                                        </td>

                                        <td>${index.index+1}</td>
                                        <td>
                                            <strong class=" <c:choose>
                                                        <c:when test="${i.isDeleted}">
                                                            text-primary
                                                        </c:when>
                                                        <c:otherwise>
                                                            text-dark
                                                        </c:otherwise>
                                                    </c:choose>">
                                                ${i.roomNumber}

                                            </strong>
                                        </td>
                                        <td>${i.roomType.roomTypeID}</td>
                                        <td><span class="
                                                  <c:choose>
                                                      <c:when test="${i.status == 'Available'}">
                                                          badge bg-success
                                                      </c:when>
                                                      <c:when test="${i.status == 'Occupied'}">
                                                          badge bg-danger
                                                      </c:when>
                                                      <c:when test="${i.status == 'Reserved'}">
                                                          badge bg-warning text-dark
                                                      </c:when>
                                                      <c:when test="${i.status == 'Cleaning'}">
                                                          badge bg-info
                                                      </c:when>
                                                      <c:otherwise>
                                                          badge bg-secondary
                                                      </c:otherwise>
                                                  </c:choose>
                                                  ">${i.status}</span></td>
                                        <td>  <fmt:formatNumber value="${i.roomDetail.area}" type="number" groupingUsed="true" maxFractionDigits="2" /> m&sup2;</td>
                                        <td class="text-success fw-bold"> <fmt:formatNumber value="${i.price}" type="number" groupingUsed="true" maxFractionDigits="2" />đ</td>
                                        <td>${i.roomDetail.maxGuest}</td>
                                        <td>${i.roomDetail.description} </td>                                            
                                        <td>
                                            <a href="#editroom"
                                               onclick="openEditRoomModal(this)"
                                               data-bs-toggle="modal"
                                               data-bs-target="#editroom"
                                               data-roomid="${i.roomID}"
                                               style="color: blue;" 
                                               title="Edit Room">
                                                <i class="fa-solid fa-pen-to-square"></i>
                                            </a>
                                            <a href="#" onclick="doDelete('${i.roomID}', '${i.roomNumber}'); return false;" 
                                               class="delete" 
                                               style="color: red;margin-left: 5px" 
                                               title="Delete Room" 
                                               data-toggle="modal"><i class="fa-solid fa-delete-left"></i></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div class=" d-flex justify-content-between align-items-center mt-3">
                            <div class="hint-text">
                                Showing <b>${listRoom.size()}</b> of <b>${totalPages * pageSize}</b> rooms
                            </div>
                            <ul class="pagination mb-0">
                                <!-- Nút Previous -->
                                <script>
                                    const currentPage = ${currentPage};
                                </script>
                                <c:choose>
                                    <c:when test="${currentPage > 1}">
                                        <li class="page-item">
                                            <a href="manageroom?page=${currentPage - 1}&keyword=${keyword}&presentDeleted=${presentDeleted}&roomType=${roomType}&sort=${sort}&sortBy=${sortBy}" class="page-link">Previous</a>
                                        </li>

                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item disabled"><span class="page-link">Previous</span></li>
                                        </c:otherwise>
                                    </c:choose>

                                <!-- Số trang -->
                                <c:forEach begin="1" end="${totalPages}" var="i">
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <a class="page-link" href="manageroom?page=${i}&keyword=${keyword}&presentDeleted=${presentDeleted}&roomType=${roomType}&sort=${sort}&sortBy=${sortBy}">${i}</a>
                                    </li>
                                </c:forEach>

                                <!-- Nút Next -->
                                <c:choose>
                                    <c:when test="${currentPage < totalPages}">
                                        <li class="page-item">
                                            <a href="manageroom?page=${currentPage + 1}&keyword=${keyword}&presentDeleted=${presentDeleted}&roomType=${roomType}&sort=${sort}&sortBy=${sortBy}" class="page-link">Next</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item disabled"><span class="page-link">Next</span></li>
                                        </c:otherwise>
                                    </c:choose>
                            </ul>
                        </div>
                    </div>



                    <div class="tab-pane fade ${openTab == '#managerRoomType' ? 'show active' : ''}" 
                         id="managerRoomType" role="tabpanel" aria-labelledby="managerRoomType-tab">
                        <!-- 🔽 PHẦN QUẢN LÝ ROOM TYPE -->
                        <div class="mt-4">
                            <div>
                                <h3>Room Type Management</h3>
                                <a href="#" class="btn btn-success"
                                   data-bs-toggle="modal"
                                   data-bs-target="#roomtypeModal"
                                   onclick="openRoomTypeModal('add')">
                                    <i class="fas fa-plus me-1"></i> Add New Room Type
                                </a>

                            </div>
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Type Name</th>
                                        <th>Description</th>
                                        <th>numberPeople</th>
                                        <th>amenity</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listRoomType}" var="type" varStatus="index">
                                        <tr>
                                            <td>${index.index+1}</td>
                                            <td>${type.typeName}</td>
                                            <td>${type.description}</td>
                                            <td>${type.numberPeople}</td>
                                            <td><c:out value="${fn:replace(type.amenity, '+', '<br>+')}" escapeXml="false" /></td>
                                            <td>
                                                <div class="d-flex gap-2">
                                                    <button class="btn btn-sm btn-primary edit-btn"
                                                            data-id="${type.roomTypeID}"
                                                            data-name="${type.typeName}"
                                                            data-desc="${type.description}"
                                                            data-num="${type.numberPeople}"
                                                            data-amenity="${fn:escapeXml(type.amenity)}"
                                                            data-bs-toggle="modal"
                                                            data-bs-target="#roomtypeModal">
                                                        Edit
                                                    </button>

                                                    <button type="button"
                                                            class="btn btn-sm btn-danger delete-btn"
                                                            data-id="${type.roomTypeID}">
                                                        Delete
                                                    </button>
                                                </div>

                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!-- 🔼 KẾT THÚC ROOM TYPE -->

                    </div>                                
                </div>
            </div>
            <div>
                <a href="searchroom"><button type="button" class="btn btn-primary" style="margin: 20px 40px; padding: 10px">Back to home</button></a>
            </div>
        </div>

        <div class="modal fade" id="editroom" tabindex="-1">
            <div class="modal-dialog custom-modal-width"> <!-- rộng hơn -->
                <form id="editRoomForm" method="post" action="roomcrud" enctype="multipart/form-data">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Edit Room</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" id="edit-roomID" name="roomID">
                            <input type="hidden" id="edit-roomDetail" name="roomDetail">
                            <input type="hidden" id="page" name="currentPage" value="${currentPage}">
                            <div class="row g-3">
                                <!-- Cột trái -->
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label>Room Number</label>
                                        <input type="text" id="edit-roomNumber" name="roomNumber"
                                               class="form-control"
                                               onkeypress="preventSpace(event)"
                                               onblur="checkDuplicateRoomNumberEdit()">
                                        <!--<input type="hidden" id="edit-roomId11" value="${room.id}">-->
                                        <small id="editRoomNumberError" class="text-danger d-none">Tên phòng đã tồn tại.</small>
                                    </div>

                                    <div class="mb-3">
                                        <label>Room Type</label>
                                        <select id="edit-roomType" name="roomTypeID" class="form-control">
                                            <option value="-1" disabled>Chọn loại phòng</option>
                                            <c:forEach items="${listRoomType}" var="type">
                                                <option value="${type.roomTypeID}">${type.typeName}</option>
                                            </c:forEach>
                                        </select>
                                        <small id="editRoomTypeError" class="text-danger d-none">Vui lòng chọn loại phòng.</small>
                                    </div>

                                    <div class="mb-3">
                                        <label>Status</label>
                                        <select name="status" id="edit-status" class="form-select">
                                            <option value="-1" disabled>Chọn trạng thái phòng</option>
                                            <option value="Available">Available</option>
                                            <option value="Occupied">Occupied</option>
                                            <option value="Reserved">Reserved</option>
                                            <option value="Cleaning">Cleaning</option>
                                            <option value="Non-available">Non-available</option>
                                        </select>
                                        <small id="editStatusError" class="text-danger d-none">Vui lòng chọn trạng thái hợp lệ.</small>
                                    </div>

                                    <div class="mb-3">
                                        <label>Current Images:</label>
                                        <div id="edit-room-images" style="display: flex; flex-wrap: wrap; gap: 10px;">
                                            <!-- ảnh hiện tại được render tại đây -->
                                        </div>
                                        <small class="text-muted">Chọn ảnh để xóa</small>
                                    </div>
                                </div>

                                <!-- Cột phải -->
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="photos">Chọn ảnh mới:</label>
                                        <input type="file" name="photos" class="form-control" multiple accept="image/*">
                                    </div>

                                    <div class="mb-3">
                                        <label>Description</label>
                                        <textarea id="edit-description" name="description" class="form-control"></textarea>
                                    </div>

                                    <div class="mb-3">
                                        <label>Price</label>
                                        <input type="number" id="edit-price" name="price" class="form-control" onblur="validateEditPrice()">
                                        <small id="editPriceError" class="text-danger d-none">Giá phải là số dương và không được để trống.</small>
                                    </div>

                                    <div class="mb-3">
                                        <label>Area</label>
                                        <input type="number" id="edit-area" name="area" class="form-control" onblur="validateEditArea()">
                                        <small id="editAreaError" class="text-danger d-none">Diện tích phải là số dương và không được để trống.</small>
                                    </div>
                                </div>

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


        <style>
            .custom-modal-width {
                max-width: 75%; /* hoặc 1200px */
            }

        </style>
        <!--Modal add room-->
        <div class="modal fade" id="addroom" tabindex="-1">
            <div class="modal-dialog custom-modal-width">
                <form id="addRoomForm" method="post" action="roomcrud" enctype="multipart/form-data">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Add Room</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="totalRooms" value="${numberRoom}">
                            <div class="row">
                                <!-- Cột bên trái -->
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label>Room Number</label>
                                        <input type="text" id="add-roomNumber" name="roomNumber" onkeydown="preventSpace(event)" onblur="checkRoomNumber()" class="form-control">
                                        <small id="roomNumberError" class="text-danger d-none">Room number already exists.</small>
                                    </div>
                                    <div class="mb-3">
                                        <label>Room Type</label>
                                        <select name="roomTypeID" id="roomTypeID" class="form-select">
                                            <option value="-1"  >Select room type</option>
                                            <c:forEach items="${listRoomType}" var="tmp">
                                                <option value="${tmp.roomTypeID}">${tmp.typeName}</option>
                                            </c:forEach>
                                        </select>
                                        <small id="roomTypeError" class="text-danger d-none">Please select a room type.</small>

                                    </div>
                                    <div class="mb-3">
                                        <label>Status</label>
                                        <select name="status" id="roomStatus" class="form-select">
                                            <option value="-1" >Select room status</option>
                                            <option value="Available">Available</option>
                                            <option value="Occupied">Occupied</option>
                                            <option value="Reserved">Reserved</option>
                                            <option value="Cleaning">Cleaning</option>
                                            <option value="Non-available">Non-available</option>
                                        </select>
                                        <small id="roomStatusError" class="text-danger d-none">Please select a room status.</small>

                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="photos">Select Img:</label>
                                        <input type="file" name="photos" class="form-control" multiple accept="image/*">
                                    </div>
                                    <div class="mb-3">
                                        <label>Price</label>
                                        <input type="number" id="add-price" name="price" class="form-control" onblur="validateAddPrice()">
                                        <small id="addPriceError" class="text-danger d-none"></small>
                                    </div>

                                    <div class="mb-3">
                                        <label>Area</label>
                                        <input type="number"  id="add-area" name="area" class="form-control"  onblur="validateAddArea()">
                                        <small id="addAreaError" class="text-danger d-none"></small>
                                    </div>
                                    <div class="mb-3">
                                        <label>Description</label>
                                        <textarea id="edit-description" name="description" class="form-control"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-primary">Add Room</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!--Modal add-edit roomtype-->
        <div class="modal fade" id="roomtypeModal" tabindex="-1">
            <div class="modal-dialog">
                <form id="roomtypeForm" method="post" action="roomcrud">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalTitle">Add Room Type</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="action" id="modal-action" value="addRoomType">
                            <input type="hidden" name="roomTypeID" id="roomTypeID">
                            <div class="mb-3">
                                <label>Type Name</label>
                                <input type="text" name="typeName" id="typeName" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label>Description</label>
                                <textarea name="description" id="description" class="form-control" rows="2"></textarea>
                            </div>
                            <div class="mb-3">
                                <label>Number of People</label>
                                <input type="number" name="numberPeople" id="numberPeople" min="1" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label>Amenities</label>
                                <div id="amenity-container" class="border p-2 rounded" style="max-height: 300px; overflow-y: auto;"></div>
                                <input type="hidden" name="amenityIds" id="amenityIds">
                            </div>
                            <div class="mb-3">
                                <label>Services</label>
                                <div id="service-container" class="border p-2 rounded" style="max-height: 300px; overflow-y: auto;"></div>
                                <input type="hidden" name="serviceIds" id="serviceIds">
                            </div>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-primary" id="modalSubmitBtn">Save</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>


        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const container = document.getElementById("amenity-container");
                container.innerHTML = "";
                const baseUrl = '${pageContext.request.contextPath}';

                fetch(baseUrl + "/roomcrud?action=getAllAmenity")
                        .then(response => response.json())
                        .then(data => {
                            for (const category in data) {
                                const groupDiv = document.createElement("div");
                                groupDiv.classList.add("mb-3");

                                const title = document.createElement("strong");
                                title.textContent = category;
                                groupDiv.appendChild(title);

                                const rowDiv = document.createElement("div");
                                rowDiv.classList.add("row", "mt-2");

                                data[category].forEach((item, index) => {
                                    const colDiv = document.createElement("div");
                                    colDiv.classList.add("col-md-6");

                                    const checkboxDiv = document.createElement("div");
                                    checkboxDiv.classList.add("form-check");

                                    const checkbox = document.createElement("input");
                                    checkbox.type = "checkbox";
                                    checkbox.className = "form-check-input amenity-checkbox";
                                    checkbox.id = "amenity-" + item.amenityId;
                                    checkbox.value = item.amenityId;
                                    checkbox.dataset.name = item.name;

                                    const label = document.createElement("label");
                                    label.className = "form-check-label";
                                    label.htmlFor = checkbox.id;
                                    label.textContent = item.name;

                                    checkboxDiv.appendChild(checkbox);
                                    checkboxDiv.appendChild(label);
                                    colDiv.appendChild(checkboxDiv);
                                    rowDiv.appendChild(colDiv);
                                });

                                groupDiv.appendChild(rowDiv);
                                container.appendChild(groupDiv);
                            }
                            container.addEventListener("change", () => {
                                const checkedBoxes = container.querySelectorAll(".amenity-checkbox:checked");
                                const selectedIds = Array.from(checkedBoxes).map(cb => cb.value);
                                document.getElementById("amenityIds").value = selectedIds.join(",");
                            });
                        });
                fetch(baseUrl + "/roomcrud?action=getAllService")
                        .then(res => res.json())
                        .then(data => {
                            console.log("Dữ liệu trả về:", data);

                            const container = document.getElementById("service-container");
                            container.innerHTML = "";

                            const groupDiv = document.createElement("div");
                            groupDiv.classList.add("mb-3");

                            const title = document.createElement("strong");
                            title.textContent = "Available Services";
                            groupDiv.appendChild(title);

                            const rowDiv = document.createElement("div");
                            rowDiv.classList.add("row", "mt-2");

                            data.forEach(service => {
                                const colDiv = document.createElement("div");
                                colDiv.classList.add("col-md-6");

                                const checkboxDiv = document.createElement("div");
                                checkboxDiv.classList.add("form-check");

                                const checkbox = document.createElement("input");
                                checkbox.type = "checkbox";
                                checkbox.className = "form-check-input service-checkbox";
                                checkbox.id = "service-" + service.serviceId;
                                checkbox.value = service.serviceId;
                                checkbox.dataset.name = service.name;

                                const label = document.createElement("label");
                                label.className = "form-check-label";
                                label.htmlFor = checkbox.id;
                                label.textContent = service.name;

                                checkboxDiv.appendChild(checkbox);
                                checkboxDiv.appendChild(label);
                                colDiv.appendChild(checkboxDiv);
                                rowDiv.appendChild(colDiv);
                            });

                            groupDiv.appendChild(rowDiv);
                            container.appendChild(groupDiv);

                            container.addEventListener("change", () => {
                                const checkedBoxes = container.querySelectorAll(".service-checkbox:checked");
                                const selectedIds = Array.from(checkedBoxes).map(cb => cb.value);
                                document.getElementById("serviceIds").value = selectedIds.join(",");
                            });
                        })
                        .catch(error => {
                            console.error("Lỗi khi tải danh sách dịch vụ:", error);
                        });
            });
        </script>




        <div id="imageModal" style="
             display: none;
             position: fixed;
             z-index: 9999;
             left: 0;
             top: 0;
             width: 100%;
             height: 100%;
             overflow: auto;
             background-color: rgba(0,0,0,0.8);
             align-items: center;
             justify-content: center;
             ">
            <span id="closeModal" style="
                  position: absolute;
                  top: 20px;
                  right: 30px;
                  color: white;
                  font-size: 30px;
                  font-weight: bold;
                  cursor: pointer;
                  ">&times;</span>

            <img id="modalImage" class="img-fluid" style="max-width: 100%; max-height: 90vh; object-fit: contain; margin: auto; display: block;">
        </div>

        <c:if test="${not empty openTab}">
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    const el = document.querySelector(`button[data-bs-target='${openTab}']`);
                    if (el)
                        new bootstrap.Tab(el).show();
                });
            </script>
        </c:if>

        <script>
            document.querySelectorAll('.delete-btn').forEach(btn => {
                btn.addEventListener('click', function () {
                    const roomTypeID = this.getAttribute('data-id');
                    console.log("Clicked ID:", roomTypeID);
                    if (confirm('Bạn có chắc chắn xóa loại phòng này?')) {
                        const baseUrl = '${pageContext.request.contextPath}';
                        fetch(baseUrl + "/roomcrud?action=deleteRoomType&roomTypeID=" + roomTypeID)
                                .then(response => response.json())
                                .then(result => {
                                    if (result.success) {
                                        alert(result.message);
                                        window.location.reload();
                                    } else {
                                        alert(result.message);
                                    }
                                })
                                .catch(error => {
                                    console.error('Error:', error);
                                    alert('Error deleting room type');
                                });
                    }
                });
            });


            function doDelete(id, name) {
                if (!confirm("Are you sure to delete roomName: " + name))
                    return;
                fetch("roomcrud?action=delete&roomId=" + id)
                        .then(res => res.json())
                        .then(data => {
                            if (data.status === "success") {
                                alert("Bạn đã xóa thành công phòng " + name);
                                window.location.reload();
                            } else if (data.status === "booked") {
                                alert("Phòng" + name + " hiện tại đang được đặt nên không thể xóa!.");
                            } else {
                                alert(data.status);
                            }
                        })
                        .catch(err => {
                            console.error("Delete error:", err);
                            alert("Something went wrong.");
                        });
            }

            function preventSpace(event) {
                if (event.key === " ") {
                    event.preventDefault();
                }
            }


//            function checkDuplicateRoomNumberEdit() {
//                const roomNumberInput = document.getElementById("edit-roomNumber");
//                const roomNumber = roomNumberInput.value.trim();
//                const roomId = document.getElementById("edit-roomId11").value;
//                const errorEl = document.getElementById("editRoomNumberError");
//
//                if (!roomNumber) {
//                    errorEl.textContent = "Tên phòng không được để trống.";
//                    errorEl.classList.remove("d-none");
//                    roomNumberInput.classList.add("is-invalid");
//                    return;
//                }
//
//                const baseUrl = `${pageContext.request.contextPath}`;
//                fetch(baseUrl+"/roomcrud?action=checkRoomNumberEdit&roomNumber="+roomNumber++"&roomId="roomId)
//                                .then(response => response.json())
//                                .then(data => {
//                                    if (data.exists) {
//                                        errorEl.textContent = "Tên phòng đã tồn tại.";
//                                        errorEl.classList.remove("d-none");
//                                        roomNumberInput.classList.add("is-invalid");
//                                    } else {
//                                        errorEl.classList.add("d-none");
//                                        roomNumberInput.classList.remove("is-invalid");
//                                    }
//                                })
//                                .catch(error => {
//                                    console.error("Lỗi khi kiểm tra tên phòng:", error);
//                                });
//                    }


            let tmpEditId = null;
            function openEditRoomModal(element) {
                const baseUrl = '${pageContext.request.contextPath}';
                const roomId = element.dataset.roomid;
                tmpEditId = roomId;
                fetch(baseUrl + "/showroomdetail?roomId=" + roomId)
                        .then(res => res.json())
                        .then(data => {
                            const room = data.room;
                            const detail = data.roomdetail;
                            const type = data.roomtype;

                            console.log(room);
                            console.log(detail);
                            console.log(type);

                            document.getElementById('edit-roomID').value = room.roomID;
                            document.getElementById('edit-roomDetail').value = detail.roomDetailID;
                            document.getElementById('edit-roomNumber').value = room.roomNumber;
                            document.getElementById('edit-roomType').value = type.roomTypeID;
                            document.getElementById('edit-status').value = room.status;
//                            document.getElementById('edit-bedType').value = detail.bedType;
                            document.getElementById('edit-description').value = detail.description;
                            document.getElementById('edit-price').value = room.price;
                            document.getElementById('edit-area').value = detail.area;

                            return fetch(baseUrl + "/roomcrud?action=getImages&roomDetailId=" + data.roomdetail.roomDetailID);
                        })
                        .then(res => res.json())
                        .then(images => {
                            const container = document.getElementById('edit-room-images');
                            container.innerHTML = '';
                            if (images.length === 0) {
                                container.innerHTML = "<p class='text-muted'>Không có ảnh nào.</p>";
                                return;
                            }

                            images.forEach(img => {
                                const div = document.createElement('div');
                                div.style.position = 'relative';
                                const image = document.createElement('img');
                                image.src = img.imageURL;
                                console.log(img.imageURL);
                                image.style.width = '100px';
                                image.style.height = '70px';
                                image.style.objectFit = 'cover';

                                image.addEventListener("click", () => {
                                    const modal = document.getElementById("imageModal");
                                    const modalImg = document.getElementById("modalImage");
                                    modalImg.src = image.src;
                                    modal.style.display = "flex";
                                });

                                const checkbox = document.createElement('input');
                                checkbox.type = 'checkbox';
                                checkbox.name = 'imagesToDelete';
                                checkbox.value = img.imageID;
                                checkbox.style.position = 'absolute';
                                checkbox.style.top = '0';
                                checkbox.style.right = '0';

                                div.appendChild(image);
                                div.appendChild(checkbox);
                                container.appendChild(div);
                            });
                        })
                        .catch(err => {
                            console.error("Lỗi khi tải dữ liệu phòng:", err);
                            document.getElementById('edit-room-images').innerHTML = "<p class='text-danger'>Lỗi khi tải ảnh.</p>";
                        });
            }


            async function confirmRestoreSelected() {
                const selected = document.querySelectorAll('.room-checkbox:checked');
                if (selected.length === 0) {
                    alert("Please select at least one room to restore.");
                    return;
                }

                const ids = Array.from(selected).map(cb => cb.value);
                const restoreQuery = ids.map(id => "roomIds=" + encodeURIComponent(id)).join("&");
                console.log(restoreQuery);
                if (!confirm(`Bạn có muốn khôi phục room(s) ?`)) {
                    return;
                }

                window.location = "roomcrud?currentPage=" + currentPage + "&action=restoreMultiple&" + restoreQuery;
            }

            async function confirmDeleteSelected() {
                const selected = document.querySelectorAll('.room-checkbox:checked');
                if (selected.length === 0) {
                    alert("Please select at least one room to delete.");
                    return;
                }

                const ids = Array.from(selected).map(cb => cb.value);
                const queryString = ids.map(id => "roomIds=" + encodeURIComponent(id)).join("&");

                try {
                    const response = await fetch("roomcrud?action=checkBookingStatus&" + queryString);
                    const result = await response.json();

                    if (result.status === "error") {
                        alert("Không thể xóa, vì những phòng này đang được đặt: " + result.bookedRoomIds.join(", "));
                        return;
                    }

                    if (!confirm(`Are you sure to delete ${ids.length} room(s)?`)) {
                        return;
                    }

                    const deleteQuery = ids.map(id => "roomIds=" + encodeURIComponent(id)).join("&");
                    window.location = "roomcrud?action=deleteMultiple&" + deleteQuery;

                } catch (error) {
                    console.error("Error checking booking status:", error);
                    alert("Error checking booking status. Please try again.");
                }
            }

            document.querySelector('thead input[type="checkbox"]').addEventListener('change', function () {
                const checkboxes = document.querySelectorAll('tbody input[type="checkbox"]');
                checkboxes.forEach(checkbox => {
                    checkbox.checked = this.checked;
                    if (this.checked) {
                        checkbox.closest('tr').classList.add('table-primary');
                    } else {
                        checkbox.closest('tr').classList.remove('table-primary');
                    }
                });
            });

            document.querySelectorAll('tbody input[type="checkbox"]').forEach(checkbox => {
                checkbox.addEventListener('change', function () {
                    if (this.checked) {
                        this.closest('tr').classList.add('table-primary');
                    } else {
                        this.closest('tr').classList.remove('table-primary');
                    }
                });
            });

            document.querySelectorAll('.edit-btn').forEach(btn => {
                btn.addEventListener('click', function () {
                    const id = this.dataset.id;
                    const name = this.dataset.name;
                    const desc = this.dataset.desc;
                    const number = this.dataset.num;
                    const amenity = this.dataset.amenity;

                    openRoomTypeModal('edit', id, name, desc, number, amenity);
                });
            });
            function openRoomTypeModal(mode, id = '', name = '', desc = '', number = '', amenity = '') {
                if (mode === 'add') {
                    $('#modalTitle').text('Add Room Type');
                    $('#modal-action').val('addRoomType');
                    $('#roomTypeID').val('');
                    $('#typeName').val('');
                    $('#description').val('');
                    $('#numberPeople').val('');
                    $('#amenity').val('');
                    $('#modalSubmitBtn').text('Add');
                } else {
                    $('#modalTitle').text('Edit Room Type');
                    $('#modal-action').val('editRoomType');
                    $('#roomTypeID').val(id);
                    $('#typeName').val(name);
                    $('#description').val(desc);
                    $('#numberPeople').val(number);
                    $('#amenity').val(amenity);
                    $('#modalSubmitBtn').text('Update');
            }
            }
            document.getElementById("closeModal").addEventListener("click", () => {
                document.getElementById("imageModal").style.display = "none";
            });

            document.getElementById("imageModal").addEventListener("click", (e) => {
                const modalImage = document.getElementById("modalImage");
                if (!modalImage.contains(e.target)) {
                    document.getElementById("imageModal").style.display = "none";
                }
            });


        </script>

        <script>
            document.addEventListener("DOMContentLoaded", () => {
                const addForm = document.getElementById("addRoomForm");
                const editForm = document.getElementById("editRoomForm");

                addForm.addEventListener("submit", async function (event) {
                    event.preventDefault();

                    const isRoomNumberValid = await checkRoomNumber();
                    const isPriceValid = validateAddPrice();
                    const isAreaValid = validateAddArea();
                    const isRoomTypeValid = validateRoomType();
                    const isRoomStatusValid = validateRoomStatus();

                    if (!isRoomNumberValid || !isPriceValid || !isAreaValid || !isRoomTypeValid || !isRoomStatusValid) {
                        alert("Nhập sai dữ liệu. Vui lòng kiểm tra lại!!");
                        return;
                    }
                    addForm.submit();
                });
                document.getElementById("roomTypeID").addEventListener("change", function () {
                    const roomTypeSelect = this;
                    const errorMsg = document.getElementById("roomTypeError");
                    if (roomTypeSelect.value !== "-1") {
                        errorMsg.classList.add("d-none");
                        roomTypeSelect.classList.remove("is-invalid");
                    }
                });

                document.getElementById("roomStatus").addEventListener("change", function () {
                    const roomStatusSelect = this;
                    const errorMsg = document.getElementById("roomStatusError");
                    if (roomStatusSelect.value !== "-1") {
                        errorMsg.classList.add("d-none");
                        roomStatusSelect.classList.remove("is-invalid");
                    }
                });
                editForm.addEventListener("submit", function (event) {
                    event.preventDefault();

//                    const isRoomNumberValid = await checkRoomNumber();
                    const isPriceValid = validateEditPrice();
                    const isAreaValid = validateEditArea();
                    const isStatusValid = validateEditStatus();
                    const isTypeValid = validateEditRoomType();
                    const isDuplicateRoomNumberEdit = checkDuplicateRoomNumberEdit();

                    if (!isDuplicateRoomNumberEdit || !isPriceValid || !isAreaValid || !isStatusValid || !isTypeValid) {
                        alert("Nhập sai dữ liệu. Vui lòng kiểm tra lại!!");
                        return;
                    }
                    editForm.submit();
                });

                document.getElementById("edit-roomType").addEventListener("change", function () {
                    const roomTypeSelect = this;
                    const errorMsg = document.getElementById("editRoomTypeError");
                    if (roomTypeSelect.value !== "-1") {
                        errorMsg.classList.add("d-none");
                        roomTypeSelect.classList.remove("is-invalid");
                    }
                });

                document.getElementById("edit-status").addEventListener("change", function () {
                    const roomStatusSelect = this;
                    const errorMsg = document.getElementById("editStatusError");
                    if (roomStatusSelect.value !== "-1") {
                        errorMsg.classList.add("d-none");
                        roomStatusSelect.classList.remove("is-invalid");
                    }
                });

            });

            async function checkRoomNumber() {
                const roomNumberInput = document.getElementById("add-roomNumber");
                const roomNumber = roomNumberInput.value.trim();
                const errorEl = document.getElementById("roomNumberError");
                const regex = /^[a-zA-Z0-9]+$/;

                errorEl.classList.add("d-none");
                roomNumberInput.classList.remove("is-invalid");

                if (!roomNumber) {
                    errorEl.textContent = "Tên phòng không được trống.";
                    errorEl.classList.remove("d-none");
                    roomNumberInput.classList.add("is-invalid");
                    return false;
                }

                if (!regex.test(roomNumber)) {
                    errorEl.textContent = "Tên phòng chỉ được chứa chữ cái và số.";
                    errorEl.classList.remove("d-none");
                    roomNumberInput.classList.add("is-invalid");
                    return false;
                }

                const baseUrl = '${pageContext.request.contextPath}';
                try {
                    const response = await fetch(baseUrl + "/roomcrud?action=checkRoomNumber&roomNumber=" + encodeURIComponent(roomNumber));
                    const data = await response.json();
                    if (data.exists) {
                        errorEl.textContent = "Tên phòng đã tồn tại!";
                        errorEl.classList.remove("d-none");
                        roomNumberInput.classList.add("is-invalid");
                        return false;
                    }
                } catch (error) {
                    console.error("Error:", error);
                    return false;
                }

                return true;
            }

            async function checkDuplicateRoomNumberEdit() {
                const roomNumberInput = document.getElementById("edit-roomNumber");
                const roomNumber = roomNumberInput.value.trim();
                const errorEl = document.getElementById("editRoomNumberError");
                const regex = /^[a-zA-Z0-9]+$/;

                errorEl.classList.add("d-none");
                roomNumberInput.classList.remove("is-invalid");

                if (!roomNumber) {
                    errorEl.textContent = "Tên phòng không được để trống.";
                    errorEl.classList.remove("d-none");
                    roomNumberInput.classList.add("is-invalid");
                    return false;
                }

                if (!regex.test(roomNumber)) {
                    errorEl.textContent = "Tên phòng chỉ được chứa chữ cái và số.";
                    errorEl.classList.remove("d-none");
                    roomNumberInput.classList.add("is-invalid");
                    return false;
                }

                const baseUrl = '${pageContext.request.contextPath}';
                try {
                    const response = await fetch(baseUrl + "/roomcrud?action=checkRoomNumberEdit&roomNumber=" + roomNumber +"&roomId="+tmpEditId);
                    const data = await response.json();

                    if (data.exists) {
                        errorEl.textContent = "Tên phòng đã tồn tại!";
                        errorEl.classList.remove("d-none");
                        roomNumberInput.classList.add("is-invalid");
                        return false;
                    }
                } catch (error) {
                    console.error("Error:", error);
                    return false;
                }

                return true;
            }


            function validateAddPrice() {
                const priceInput = document.getElementById("add-price");
                const priceError = document.getElementById("addPriceError");
                const price = parseFloat(priceInput.value.trim());

                priceError.classList.add("d-none");
                priceInput.classList.remove("is-invalid");

                if (isNaN(price) || price <= 0) {
                    priceError.textContent = "Giá phòng cần phải lớn hơn 0!!";
                    priceError.classList.remove("d-none");
                    priceInput.classList.add("is-invalid");
                    return false;
                }
                return true;
            }

            function validateAddArea() {
                const areaInput = document.getElementById("add-area");
                const areaError = document.getElementById("addAreaError");
                const area = parseFloat(areaInput.value.trim());

                areaError.classList.add("d-none");
                areaInput.classList.remove("is-invalid");

                if (isNaN(area) || area <= 0) {
                    areaError.textContent = "Diện tích lớn hơn 0!!!";
                    areaError.classList.remove("d-none");
                    areaInput.classList.add("is-invalid");
                    return false;
                }

                return true;
            }

            function validateRoomType() {
                const roomType = document.getElementById("roomTypeID");
                const roomTypeError = document.getElementById("roomTypeError");

                if (roomType.value === "-1") {
                    roomType.classList.add("is-invalid");
                    roomTypeError.classList.remove("d-none");
                    return false;
                }

                roomType.classList.remove("is-invalid");
                roomTypeError.classList.add("d-none");
                return true;
            }

            function validateRoomStatus() {
                const roomStatus = document.getElementById("roomStatus");
                const roomStatusError = document.getElementById("roomStatusError");

                if (roomStatus.value === "-1") {
                    roomStatus.classList.add("is-invalid");
                    roomStatusError.classList.remove("d-none");
                    return false;
                }

                roomStatus.classList.remove("is-invalid");
                roomStatusError.classList.add("d-none");
                return true;
            }

            function validateEditStatus() {
                const status = document.getElementById("edit-status");
                const error = document.getElementById("editStatusError");

                if (status.value === "-1") {
                    error.classList.remove("d-none");
                    status.classList.add("is-invalid");
                    return false;
                }

                error.classList.add("d-none");
                status.classList.remove("is-invalid");
                return true;
            }

            function validateEditPrice() {
                const priceInput = document.getElementById("edit-price");
                const error = document.getElementById("editPriceError");
                const value = priceInput.value.trim();

                if (value === "" || isNaN(value) || parseFloat(value) <= 0) {
                    error.classList.remove("d-none");
                    priceInput.classList.add("is-invalid");
                    return false;
                }

                error.classList.add("d-none");
                priceInput.classList.remove("is-invalid");
                return true;
            }

            function validateEditArea() {
                const areaInput = document.getElementById("edit-area");
                const error = document.getElementById("editAreaError");
                const value = areaInput.value.trim();

                if (value === "" || isNaN(value) || parseFloat(value) <= 0) {
                    error.classList.remove("d-none");
                    areaInput.classList.add("is-invalid");
                    return false;
                }

                error.classList.add("d-none");
                areaInput.classList.remove("is-invalid");
                return true;
            }



            function validateEditRoomType() {
                const type = document.getElementById("edit-roomType");
                const error = document.getElementById("editRoomTypeError");

                if (type.value === "-1") {
                    error.classList.remove("d-none");
                    type.classList.add("is-invalid");
                    return false;
                }

                error.classList.add("d-none");
                type.classList.remove("is-invalid");
                return true;
            }
        </script>




    </body>
</html>
