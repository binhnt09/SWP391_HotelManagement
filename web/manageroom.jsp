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

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <script src="${pageContext.request.contextPath}/assets/js/jquery.magnific-popup.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/magnific-popup.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">

    </head>
    <body>
        <div class="container">
            <div class="table-wrapper">
                <form action="deleteroomtype" method="post" id="deleteSelect">

                    <!--                    <div class="table-title" style="margin-top: 10px">
                                            <div class="row">
                                                <div class="col-sm-6">
                                                    <h2><a href="crudASG.jsp"><i class="material-icons">home</i></a> Manage <b>Product</b></h2>
                                                </div>
                                                <div class="col-sm-6">
                                                    <a href="#addnewroomtype" class="btn btn-success" data-bs-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Add New Product</span></a>
                                                    <a href="#deleteEmployeeModal" class="btn btn-danger" data-toggle="modal"><i class="material-icons">&#xE15C;</i> <span>Delete</span></a>					
                                                    <button type="submit" onclick="deleteListType()"  class="btn btn-danger">DELETE</button>					
                                                </div>
                                            </div>
                                        </div>-->
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <div>
                            <button class="btn btn-success me-2">
                                <i class="fas fa-plus me-1"></i>Add New Product
                            </button>
                            <button class="btn btn-danger">
                                <i class="fas fa-trash me-1"></i>DELETE
                            </button>
                        </div>
                        <div class="text-muted">
                            <small>Tổng cộng: ${numberRoom} phòng</small>
                        </div>
                    </div>
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
                                <th>Image</th>
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
                                            <input type="checkbox"  >
                                        </span>
                                    </td>
                                    <td>${index.index+1}</td>
                                    <td><strong>${i.roomNumber}</strong></td>
                                    <td>
                                        <div class="bg-primary text-white rounded text-center d-inline-block px-2 py-1">
                                            <small>IMG</small>
                                        </div>
                                    </td>
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
                                    <!--                                <tr>
                                                                        <td>
                                                                            <span class="custom-checkbox">
                                                                                <input type="checkbox" id="checkbox1" name="options" value="${o.typeID}">
                                                                                <label for="checkbox1"></label>
                                                                            </span>  
                                                                        </td>
                                                                        <td>${index.index+1}</td>
                                                                        <td>${i.roomID}</td>
                                                                        <td>${i.roomNumber}</td>
                                                                        <td>
                                                                            <img src="${o.imageURL}">IMG
                                                                        </td>
                                                                        <td>${i.status} </td>
                                                                        <td>${i.roomDetail.area} </td>
                                                                        <td>${i.price} </td>
                                                                        <td>${i.roomDetail.maxGuest} </td>
                                    -->                                                 
                                    <td>
                                        <a href="#editroomtype"  style="color: blue;" title="Edit Room"><i class="fa-solid fa-pen-to-square"></i></a>
                                        <a href="#" class="delete" style="color: red;margin-left: 5px" title="Delete Room" data-toggle="modal"><i class="fa-solid fa-delete-left"></i></a>
                                        
                                    </td>
                                </tr>

                            </c:forEach>

                        </tbody>
                    </table>
                </form>

                <!--                <div class="clearfix">
                                    <div class="hint-text">Showing <b>${currentpage}</b> out of <b>${endP}</b> entries</div>
                                    <ul class="pagination">
                                        <li class="page-item ${currentpage == 1 ? 'disabled' : ''}">
                                            <a href="roomtypecontrol?page=${currentpage == 1 ? 1 : currentpage-1}" class="page-link">Previous</a>
                                        </li>
                <c:forEach begin="1" end="${endP}" var="i">
                    <li class="page-item ${i == currentpage ? 'active':''}">
                        <a href="roomtypecontrol?page=${i}" class="page-link">${i}</a>
                    </li>
                </c:forEach>
                <li class="page-item ${currentpage == endP ? 'disabled' : ''}">
                    <a href="roomtypecontrol?page=${currentpage == endP ? endP : currentpage+1}" class="page-link">Next</a>
                </li>
            </ul>
        </div>-->
            </div>
            <div>
                <a href="#"><button type="button" class="btn btn-primary" style="margin: 20px 40px; padding: 10px">Back to home</button></a>
            </div>
        </div>
        <script>
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
        </script>
    </body>
</html>
