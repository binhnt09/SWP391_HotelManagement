<%-- 
    Document   : account-list
    Created on : Jun 24, 2025, 2:49:50 PM
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
                        <form method="get" action="customerList">
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

        </div>
        <!-- End XP Row -->
        <div class="xp-breadcrumbbar text-center">
            <h4 class="page-title">Dashboard</h4>  
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="#">Dashboard</a></li>
                <li class="breadcrumb-item active" aria-current="page">Customer Info</li>
            </ol>                
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

                    <div class="table-title mb-3">
                        <div class="row">
                            <div class="col-sm-6 p-0 flex justify-content-lg-start justify-content-center">
                                <h2 class="ml-lg-2">Manage Authentication</h2>
                            </div>
                            <div class="col-sm-6 p-0 flex justify-content-lg-end justify-content-center">
                                <a href="#addAuthModal" class="btn btn-success" data-toggle="modal">
                                    <i class="material-icons">&#xE147;</i>
                                    <span>Add New Authentication</span>
                                </a>
                                <a href="#deleteAuthModal" class="btn btn-danger" data-toggle="modal">
                                    <i class="material-icons">&#xE15C;</i>
                                    <span>Delete</span>
                                </a>
                            </div>
                        </div>
                    </div>

                    <table class="table table-striped table-hover table-bordered">
                        <thead>
                            <tr>
                                <th>
                                    <span class="custom-checkbox">
                                        <input type="checkbox" id="selectAll">
                                        <label for="selectAll"></label>
                                    </span>
                                </th>
                                <th>User ID</th>
                                <th>Email</th>
                                <th>User Key</th>
                                <th>Auth Type</th>
                                <th>Created At</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:choose>
                                <c:when test="${not empty authList}">
                                    <c:forEach var="a" items="${authList}" varStatus="status">
                                        <tr>
                                            <td>
                                                <span class="custom-checkbox">
                                                    <input type="checkbox" id="checkbox${status.index}" name="option[]" value="${a.authenticationID}">
                                                    <label for="checkbox${status.index}"></label>
                                                </span>
                                            </td>
                                            <td>${a.user.userId}</td>
                                            <td>${a.user.email}</td>
                                            <td>${a.userKey}</td>
                                            <td>${a.authType}</td>
                                            <td>
                                                <fmt:formatDate value="${a.createdAt}" pattern="yyyy-MM-dd HH:mm" />
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${a.isDeleted}">
                                                        <span class="badge badge-danger">Deleted</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge badge-success">Active</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <!--                                            <td>
                                                                                            <a href="#editAuthModal"
                                                                                               class="edit"
                                                                                               data-toggle="modal"
                                                                                               data-id="${a.authenticationID}"
                                                                                               data-userkey="${a.userKey}"
                                                                                               data-authtype="${a.authType}">
                                                                                                <i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i>
                                                                                            </a>
                                                                                            <a href="authDelete?id=${a.authenticationID}" class="delete" data-toggle="tooltip" title="Delete"
                                                                                               onclick="return confirm('Xác nhận xóa bản ghi này?');">
                                                                                                <i class="material-icons">&#xE872;</i>
                                                                                            </a>
                                                                                        </td>-->

                                            <td class="text-center">
                                                <c:choose>
                                                    <c:when test="${a.isDeleted}">
                                                        <a href="authRestore?id=${a.authenticationID}" class="btn btn-warning btn-sm" data-toggle="tooltip" title="Khôi phục">
                                                            Khôi phục
                                                        </a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="d-flex justify-content-start gap-2">
                                                            <a href="#editAuthModal"
                                                               class="btn btn-info btn-sm me-2"
                                                               data-toggle="modal"
                                                               data-id="${a.authenticationID}"
                                                               data-userkey="${a.userKey}"
                                                               data-authtype="${a.authType}">
                                                                Sửa
                                                            </a>

                                                            <a href="authDelete?id=${a.authenticationID}"
                                                               class="btn btn-danger btn-sm"
                                                               data-toggle="tooltip"
                                                               title="Xóa"
                                                               onclick="return confirm('Xác nhận xóa bản ghi này?');">
                                                                Xóa
                                                            </a>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="7" class="text-center">Không có bản ghi nào.</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>

                    <div class="clearfix">
                        <div class="hint-text">showing <b>${authList.size()}</b> out of <b>${totalAuth}</b></div>
                        <ul class="pagination">
                            <c:choose>
                                <c:when test="${currentPage > 1}">
                                    <li class="page-item">
                                        <a href="customerList?page=${currentPage - 1}&keyword=${param.keyword}" class="page-link">Previous</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item disabled"><a href="#" class="page-link">Previous</a></li>
                                    </c:otherwise>
                                </c:choose>

                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a href="customerList?page=${i}&keyword=${param.keyword}" class="page-link">${i}</a>
                                </li>
                            </c:forEach>

                            <c:choose>
                                <c:when test="${currentPage < totalPages}">
                                    <li class="page-item">
                                        <a href="customerList?page=${currentPage + 1}&keyword=${param.keyword}" class="page-link">Next</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item disabled"><a href="#" class="page-link">Next</a></li>
                                    </c:otherwise>
                                </c:choose>
                        </ul>
                    </div>
                </div>
            </div>


            <!----add-modal start--------->
            <div class="modal fade" tabindex="-1" id="addEmployeeModal" role="dialog">
                <div class="modal-dialog" role="document">
                    <form action="customerUpdate" method="POST">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Add Auth</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <label>User ID</label>
                                    <input type="text" class="form-control" name="firstName" required>
                                </div>
                                <div class="form-group">
                                    <label>Last Name</label>
                                    <input type="text" class="form-control" name="lastName" required>
                                </div>
                                <div class="form-group">
                                    <label>Email</label>
                                    <input type="email" class="form-control" name="email" required>
                                </div>
                                <div class="form-group">
                                    <label>Address</label>
                                    <textarea class="form-control" name="address" required></textarea>
                                </div>
                                <div class="form-group">
                                    <label>Phone</label>
                                    <input type="text" class="form-control" name="phone" required>
                                </div>
                                <input type="hidden" name="action" value="add">
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                <button type="submit" class="btn btn-success">Add</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <!----edit-modal end--------->





            <!----edit-modal start--------->
            <div class="modal fade" tabindex="-1" id="editEmployeeModal" role="dialog">
                <div class="modal-dialog" role="document">
                    <form action="customerUpdate" method="POST">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Edit Customer</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <label>First Name</label>
                                    <input type="text" class="form-control" id="editFirstName" name="firstName" required>
                                </div>
                                <div class="form-group">
                                    <label>Last Name</label>
                                    <input type="text" class="form-control" id="editLastName" name="lastName" required>
                                </div>
                                <div class="form-group">
                                    <label>Email</label>
                                    <input type="email" class="form-control" id="editEmail" name="email" required>
                                </div>
                                <div class="form-group">
                                    <label>Address</label>
                                    <textarea class="form-control" id="editAddress" name="address" required></textarea>
                                </div>
                                <div class="form-group">
                                    <label>Phone</label>
                                    <input type="text" class="form-control" id="editPhone" name="phone" required>
                                </div>
                                <input type="hidden" id="editUserId" name="userId">
                                <input type="hidden" name="action" value="update">
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                <button type="submit" class="btn btn-success">Save</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <!----edit-modal end--------->


            <!----delete-modal start--------->
            <div class="modal fade" tabindex="-1" id="deleteEmployeeModal" role="dialog">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Delete Employees</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <p>Are you sure you want to delete this Records</p>
                            <p class="text-warning"><small>this action Cannot be Undone,</small></p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                            <button type="button" class="btn btn-success">Delete</button>
                        </div>
                    </div>
                </div>
            </div>

            <!----edit-modal end--------->




        </div>
    </div>

    <script>
        $(document).ready(function () {
            $('.edit').on('click', function () {
                var id = $(this).data('id');
                var firstname = $(this).data('firstname');
                var lastname = $(this).data('lastname');
                var email = $(this).data('email');
                var phone = $(this).data('phone');
                var address = $(this).data('address');

                $('#editUserId').val(id);
                $('#editFirstName').val(firstname);
                $('#editLastName').val(lastname);
                $('#editEmail').val(email);
                $('#editPhone').val(phone);
                $('#editAddress').val(address);
                console.log("id = ", id);
                console.log("firstname = ", firstname);
            });
        });
    </script>


    <!------main-content-end----------->

    <%@ include file="/dashboard-layout/footer.jsp" %>
