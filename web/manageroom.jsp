<%-- 
    Document   : rmanageroom
    Created on : Jun 8, 2025, 8:46:42 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

                                <!-- Search input -->
                                <div class="col-auto">
                                    <input type="hidden" name="action" value="filterRoom">
                                    <input type="text" id="searchInput" name="keyWorld" value="${keyWorld}" 
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
                                        <option value="asc" ${sort.equals("asc") ? "selected" : ""}>↑ Giá</option>
                                        <option value="desc" ${sort.equals("desc") ? "selected" : ""}>↓ Giá</option>
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
                                        <td>${i.roomDetail.area}</td>
                                        <td class="text-success fw-bold">$${i.price}</td>
                                        <td>${i.roomDetail.maxGuest}</td>
                                        <td>${i.roomDetail.description} </td>                                            
                                        <td>
                                            <a href="#editroom"
                                               data-bs-toggle="modal"
                                               data-roomID="${i.roomID}"
                                               data-roomDetail="${i.roomDetail.roomDetailID}"
                                               data-name="${i.roomNumber}"
                                               data-status="${i.status}"
                                               data-roomType="${i.roomType.roomTypeID}"
                                               data-bedType="${i.roomDetail.bedType}"
                                               data-description="${i.roomDetail.description}"
                                               data-pricePerNight="${i.price}"
                                               data-capacity="${i.roomDetail.maxGuest}"
                                               data-area="${i.roomDetail.area}"
                                               style="color: blue;" title="Edit Room"><i class="fa-solid fa-pen-to-square"></i></a>
                                            <a href="#" onclick="doDelete('${i.roomID}', '${i.roomNumber}');return false;" 
                                               class="delete" 
                                               style="color: red;margin-left: 5px" 
                                               title="Delete Room" 
                                               data-toggle="modal"><i class="fa-solid fa-delete-left"></i></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
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
                                    <c:forEach items="${listRoomType}" var="type">
                                        <tr>
                                            <td>${type.roomTypeID}</td>
                                            <td>${type.typeName}</td>
                                            <td>${type.description}</td>
                                            <td>${type.numberPeople}</td>
                                            <td>${type.amenity}</td>
                                            <td>
                                                <button class="btn btn-sm btn-primary"
                                                        data-bs-toggle="modal"
                                                        data-bs-target="#roomtypeModal"
                                                        onclick="openRoomTypeModal('edit', '${type.roomTypeID}', '${type.typeName}', '${type.description}', '${type.numberPeople}', '${type.amenity}')">
                                                    Edit
                                                </button>

                                                <button class="btn btn-sm btn-danger">Delete</button>
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


        <!--Modal add room-->
        <div class="modal fade" id="addroom" tabindex="-1">
            <div class="modal-dialog">
                <form id="addRoomForm" method="post" action="roomcrud" enctype="multipart/form-data">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Add Room</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="action" value="add">
                            <div class="mb-3">
                                <label>Room Number</label>
                                <input type="text" id="edit-roomNumber" name="roomNumber" class="form-control">
                            </div>
                            <div class="mb-3">
                                <label>Room Type</label>
                                <select name="roomTypeID" class="form-select"   >
                                    <option value="-1" >Select room type </option>
                                    <c:forEach items="${listRoomType}" var="tmp">
                                        <option value="${tmp.roomTypeID}">${tmp.typeName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label>Bed Type</label>
                                <input type="text" id="edit-bedType" name="bedType" class="form-control">
                            </div>
                            <div class="mb-3">
                                <label>Status</label>
                                <select name="status" class="form-select"   >
                                    <option value="-1" >Select room status </option>
                                    <option value="Available">Available</option>
                                    <option value="Occupied">Occupied</option>
                                    <option value="Reserved">Reserved</option>
                                    <option value="Cleaning">Cleaning</option>
                                    <option value="Non-available">Non-available</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label>Hotel</label>
                                <input type="text" id="edit-status" name="hotel" class="form-control">
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
                                <input type="number" name="numberPeople" id="numberPeople" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label>Amenity</label>
                                <input type="text" name="amenity" id="amenity" class="form-control">
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
            function doDelete(id, name) {
                if (confirm("Are you sure to delete roomName :" + name)) {
                    window.location = "roomcrud?action=delete&roomId=" + id;//deletelesson là tên của link servlet để nó nhận doGet
                }
            }
            $(document).on('click', '[data-bs-toggle="modal"]', function () {
                console.log($(this).data());

                $('#edit-roomID').val($(this).data('roomid'));
                $('#edit-roomDetail').val($(this).data('roomdetail'));
                $('#edit-roomNumber').val($(this).data('name'));
                $('#edit-status').val($(this).data('status'));
                $('#edit-roomType').val($(this).data('roomtype'));
                $('#edit-bedType').val($(this).data('bedtype'));
                $('#edit-description').val($(this).data('description'));
                $('#edit-price').val($(this).data('pricepernight'));
                $('#edit-maxGuest').val($(this).data('capacity'));
                $('#edit-area').val($(this).data('area'));
            });

            function confirmDeleteSelected() {
                const selected = document.querySelectorAll('.room-checkbox:checked');
                if (selected.length === 0) {
                    alert("Please select at least one room to delete.");
                    return;
                }

                const ids = Array.from(selected).map(cb => cb.value);
                console.log("Room IDs to delete:", ids); // Gỡ lỗi

                if (!confirm(`Are you sure to delete ${ids.length} room(s)?`)) {
                    return;
                }

                let queryString = '';
                for (let i = 0; i < ids.length; i++) {
                    queryString += 'roomIds=' + ids[i];
                    if (i < ids.length - 1) {
                        queryString += '&';
                        console.log(queryString);
                    }
                }
                window.location = "roomcrud?action=deleteMultiple&" + queryString;
            }

            // Select all functionality
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

            // Individual checkbox functionality
            document.querySelectorAll('tbody input[type="checkbox"]').forEach(checkbox => {
                checkbox.addEventListener('change', function () {
                    if (this.checked) {
                        this.closest('tr').classList.add('table-primary');
                    } else {
                        this.closest('tr').classList.remove('table-primary');
                    }
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

        </script>
    </body>
</html>