<%-- 
    Document   : user-list
    Created on : Jul 24, 2025, 12:02:38 PM
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
                <div class="col-md-5 col-lg-3 order-3 order-md-2">
                    <div class="xp-searchbar">
                        <form method="get" action="userList">
                            <div class="input-group">
                                <input type="hidden" name="role" value="${param.role}">
                                <input type="search" class="form-control" name="keyword"
                                       value="${param.keyword}"
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
                <li class="breadcrumb-item active" aria-current="page">User Info</li>
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

                    <div class="table-title">
                        <div class="row align-items-center">
                            <div class="col-md-6 d-flex justify-content-start">
                                <h2 class="ml-lg-2">Manage User</h2>
                            </div>
                            <div class="col-md-6 d-flex justify-content-end align-items-center">
                                <form method="get" action="userList" class="form-inline mb-2">
                                    <label>Filter by role: </label>
                                    <select name="role" onchange="this.form.submit()" class="form-control ml-2">
                                        <option value="" ${empty role ? 'selected' : ''}>All Roles</option>
                                        <option value="1" ${role == 1 ? 'selected' : ''}>SystemAdmin</option>
                                        <option value="2" ${role == 2 ? 'selected' : ''}>Manager</option>
                                        <option value="3" ${role == 3 ? 'selected' : ''}>Receptionist</option>
                                        <option value="4" ${role == 4 ? 'selected' : ''}>Cleaner</option>
                                        <option value="5" ${role == 5 ? 'selected' : ''}>Customer</option>
                                        <option value="6" ${role == 6 ? 'selected' : ''}>Guest</option>

                                    </select>
                                    <input type="hidden" name="keyword" value="${keyword}"/>
                                </form>
<!--                                <a href="#addEmployeeModal" class="btn btn-success" data-toggle="modal">
                                    <i class="material-icons">&#xE147;</i>
                                    <span>Add New User</span>
                                </a>-->
                            </div>
                        </div>
                    </div>

                    <!-- Staff Table -->
                    <table class="table table-striped table-hover table-bordered">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Address</th>
                                <th>Role</th>
                                <th>Create At</th>
                                <th>isDeleted</th>
                                <th>Has Account</th>
                                <th>Actions</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:choose>
                                <c:when test="${not empty userList}">
                                    <c:forEach var="s" items="${userList}" varStatus="status">
                                        <tr>
                                            <td>${s.userId}</td>
                                            <td>${s.firstName}</td>
                                            <td>${s.lastName}</td>
                                            <td>${s.email}</td>
                                            <td>${s.phone}</td>
                                            <td>${s.address}</td>
                                            <td>${s.roleName}</td>
                                            <td>${s.createdAt}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${s.isDeleted}">
                                                        <span class="badge badge-danger">Deleted (By ID: ${s.deletedBy})</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge badge-success">Active</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${s.hasAccount}">
                                                        <span class="text-success">Yes</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-danger">No</span>
                                                        <button type="button"
                                                                class="btn btn-outline-primary btn-sm ml-2"
                                                                data-toggle="modal"
                                                                data-target="#createAccountModal"
                                                                data-userid="${s.userId}"
                                                                data-useremail="${s.email}"                                                                
                                                                data-username="${s.firstName} ${s.lastName}"
                                                                <i class="fas fa-plus mr-1"></i> Create
                                                        </button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <a href="#editEmployeeModal"
                                                   class="edit"
                                                   data-toggle="modal"
                                                   data-id="${s.userId}"
                                                   data-firstname="${s.firstName}"
                                                   data-lastname="${s.lastName}"
                                                   data-email="${s.email}"
                                                   data-phone="${s.phone}"
                                                   data-address="${s.address}"
                                                   data-userroleid="${s.userRoleId}">
                                                    <i class="material-icons">&#xE254;</i>
                                                </a>
                                                <c:choose>
                                                    <c:when test="${s.isDeleted}">
                                                        <a href="userRestore?id=${s.userId}" class="btn btn-warning btn-sm" data-toggle="tooltip" title="Khôi phục">
                                                            Khôi phục
                                                        </a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="d-flex justify-content-start gap-2">
                                                            <a href="userDelete?id=${s.userId}"
                                                               class="btn btn-danger btn-sm"
                                                               data-toggle="tooltip"
                                                               title="Xóa"
                                                               onclick="return confirm('Xác nhận xóa bản ghi này?');">
                                                                Xóa
                                                            </a>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="10" style="text-align:center;">Không có người dùng nào.</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>

                    <!-- Pagination -->
                    <div class="clearfix">
                        <div class="hint-text">showing <b>${userList.size()}</b> out of <b>${totalUsers}</b></div>
                        <ul class="pagination">
                            <c:choose>
                                <c:when test="${currentPage > 1}">
                                    <li class="page-item"><a href="userList?page=${currentPage - 1}&role=${role}&keyword=${keyword}" class="page-link">Previous</a></li>
                                    </c:when>
                                    <c:otherwise>
                                    <li class="page-item disabled"><a href="#" class="page-link">Previous</a></li>
                                    </c:otherwise>
                                </c:choose>

                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a href="userList?page=${i}&role=${role}&keyword=${keyword}" class="page-link">${i}</a>
                                </li>
                            </c:forEach>

                            <c:choose>
                                <c:when test="${currentPage < totalPages}">
                                    <li class="page-item"><a href="userList?page=${currentPage + 1}&role=${role}&keyword=${keyword}" class="page-link">Next</a></li>
                                    </c:when>
                                    <c:otherwise>
                                    <li class="page-item disabled"><a href="#" class="page-link">Next</a></li>
                                    </c:otherwise>
                                </c:choose>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal tạo tài khoản -->
    <div class="modal fade" id="createAccountModal" tabindex="-1" role="dialog" aria-labelledby="createAccountModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <form action="createAccountForUser" method="post" class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Tạo tài khoản cho <span id="modalUsername" class="text-primary"></span></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Đóng">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-body">
                    <input type="hidden" name="userId" id="modalUserId" />

                    <div class="form-group">
                        <label>Username:</label>
                        <input type="text" id="modalEmail" name="username" class="form-control" value="" readonly />
                    </div>

                    <div class="form-group">
                        <label>Password:</label>
                        <div class="input-group">
                            <input type="password" class="form-control" id="password" name="password" value="P@ssword123">
                            <div class="input-group-append">
                                <span class="input-group-text">
                                    <a href="#" id="togglePassword" class="text-dark"><i class="fa fa-eye" aria-hidden="true"></i></a>
                                </span>
                            </div>
                        </div>
                    </div>

                    <!-- Confirm Password -->
                    <div class="form-group">
                        <label for="confirmPassword">Confirm Password</label>
                        <div class="input-group">
                            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" value="P@ssword123">
                            <div class="input-group-append">
                                <span class="input-group-text">
                                    <a href="#" class="toggle-password text-dark" data-target="#confirmPassword"><i class="fa fa-eye"></i></a>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button class="btn btn-primary" type="submit">Create Account</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                </div>
            </form>
        </div>
    </div>

    <!----edit-modal start--------->
    <div class="modal fade" tabindex="-1" id="editEmployeeModal" role="dialog">
        <div class="modal-dialog" role="document">
            <form id="editEmployeeForm" action="userUpdate" method="POST">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Edit User</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label>First Name</label>
                            <input type="text" class="form-control" id="editFirstName" name="firstName" readonly>
                        </div>
                        <div class="form-group">
                            <label>Last Name</label>
                            <input type="text" class="form-control" id="editLastName" name="lastName" readonly>
                        </div>
                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" class="form-control" id="editEmail" name="email" readonly>
                        </div>
                        <div class="form-group">
                            <label>Address</label>
                            <textarea class="form-control" id="editAddress" name="address" readonly></textarea>
                        </div>
                        <div class="form-group">
                            <label>Phone</label>
                            <input type="text" class="form-control" id="editPhone" name="phone" readonly>
                        </div>
                        <div class="form-group">
                            <label>Position</label>
                            <select name="roleID" id="editRoleId" class="form-control">
                                <option value="1" >SystemAdmin</option>
                                <option value="2" >Manager</option>
                                <option value="3" >Receptionist</option>
                                <option value="4" >Cleaner</option>
                                <option value="5" >Customer</option>
                            </select>
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

    <script>
        $('#createAccountModal').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget); // Button that triggered the modal
            var userId = button.data('userid');
            var username = button.data('username');
            var userEmail = button.data('useremail');

            var modal = $(this);
            modal.find('#modalUserId').val(userId);
            modal.find('#modalEmail').val(userEmail);
            modal.find('#modalUsername').text(username);
        });


        $('#togglePassword').on('click', function (e) {
            e.preventDefault();
            var input = $('#password');
            var icon = $(this).find('i');

            if (input.attr('type') === 'password') {
                input.attr('type', 'text');
                icon.removeClass('fa-eye').addClass('fa-eye-slash');
            } else {
                input.attr('type', 'password');
                icon.removeClass('fa-eye-slash').addClass('fa-eye');
            }
        });

        $(document).on('click', '.toggle-password', function (e) {
            e.preventDefault();
            var input = $($(this).data('target'));
            var icon = $(this).find('i');

            if (input.attr('type') === 'password') {
                input.attr('type', 'text');
                icon.removeClass('fa-eye').addClass('fa-eye-slash');
            } else {
                input.attr('type', 'password');
                icon.removeClass('fa-eye-slash').addClass('fa-eye');
            }
        });

        $(document).ready(function () {
            $('.edit').on('click', function () {
                var id = $(this).data('id');
                var firstname = $(this).data('firstname');
                var lastname = $(this).data('lastname');
                var email = $(this).data('email');
                var phone = $(this).data('phone');
                var address = $(this).data('address');
                var roleid = $(this).data('userroleid');

                $('#editUserId').val(id);
                $('#editFirstName').val(firstname);
                $('#editLastName').val(lastname);
                $('#editEmail').val(email);
                $('#editPhone').val(phone);
                $('#editAddress').val(address);
                $('#editRoleId').val(roleid);
                console.log("id = ", id);
                console.log("firstname = ", firstname);
            });
        });

    </script>

    <!------main-content-end----------->

    <%@ include file="/dashboard-layout/footer.jsp" %>