<%-- 
    Document   : test
    Created on : Jun 7, 2025, 5:52:01 PM
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
            </div> 
        </div>
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

                    <div class="table-title">
                        <div class="row">
                            <div class="col-sm-6 p-0 flex justify-content-lg-start justify-content-center">
                                <h2 class="ml-lg-2">Manage customer</h2>
                            </div>
                            <div class="col-sm-6 p-0 flex justify-content-lg-end justify-content-center">
                                <!--                                <a href="#addEmployeeModal" class="btn btn-success" data-toggle="modal">
                                                                    <i class="material-icons">&#xE147;</i>
                                                                    <span>Add New customer</span>
                                                                </a>-->
                                <!--                                <a href="#deleteEmployeeModal" class="btn btn-danger" data-toggle="modal">
                                                                    <i class="material-icons">&#xE15C;</i>
                                                                    <span>Delete</span>
                                                                </a>-->
                            </div>
                        </div>
                    </div>

                    <table class="table table-striped table-hover table-bordered">
                        <thead>
                            <tr>
                                <th>
                                </th>
                                <th>User ID</th>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Email</th>  
                                <th>Phone</th>
                                <th>Address</th>
                                <th>Create At</th>
                                <th>Action</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:choose>
                                <c:when test="${not empty customers}">
                                    <c:forEach var="c" items="${customers}" varStatus="status">
                                        <tr>
                                            <td>
                                                <span class="custom-checkbox">
                                                    <input type="checkbox" id="checkbox${status.index}" name="option[]" value="${c.userId}">
                                                    <label for="checkbox${status.index}"></label>
                                                </span>
                                            </td>
                                            <td>${c.userId}</td>
                                            <td>${c.firstName}</td>
                                            <td>${c.lastName}</td>
                                            <td>${c.email}</td>
                                            <td>${c.phone}</td>
                                            <td>${c.address}</td>
                                            <td>${c.createdAt}</td>
                                            <td>
                                                <a href="#editEmployeeModal"
                                                   class="edit"
                                                   data-toggle="modal"
                                                   data-id="${c.userId}"
                                                   data-firstname="${c.firstName}"
                                                   data-lastname="${c.lastName}"
                                                   data-email="${c.email}"
                                                   data-phone="${c.phone}"
                                                   data-address="${c.address}">
                                                    <i class="material-icons">&#xE254;</i>
                                                </a>
                                                <a href="customerDelete?id=${c.userId}" class="delete" data-toggle="tooltip" title="Delete"
                                                   onclick="return confirm('Xác nhận xóa khách hàng này?');">
                                                    <i class="material-icons">&#xE872;</i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="9" style="text-align:center;">Không có khách hàng nào.</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>

                    <div class="clearfix">
                        <div class="hint-text">showing <b>${customers.size()}</b> out of <b>${totalCustomer}</b></div>
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
                                <h5 class="modal-title">Add Customer</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <label>First Name</label>
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
                                    <input type="text" class="form-control" name="phone" pattern="^\d{9,11}$" title="Vui lòng nhập số điện thoại từ 9 đến 11 chữ số, chỉ nhận chữ số." required>
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
                                    <input type="text" class="form-control" id="editFirstName" name="firstName" required pattern="^(?!\s*$)[A-Za-zÀ-ỹ0-9\s]+$" 
                                           title="Không được để trống, không chỉ toàn khoảng trắng và không chứa ký tự đặc biệt">
                                </div>
                                <div class="form-group">
                                    <label>Last Name</label>
                                    <input type="text" class="form-control" id="editLastName" name="lastName" required pattern="^(?!\s*$)[A-Za-zÀ-ỹ0-9\s]+$" 
                                           title="Không được để trống, không chỉ toàn khoảng trắng và không chứa ký tự đặc biệt">
                                </div>
                                <div class="form-group">
                                    <label>Email</label>
                                    <input type="email" class="form-control" id="editEmail" name="email" readonly>
                                </div>
                                <div class="form-group">
                                    <label>Address</label>
                                    <textarea class="form-control" id="editAddress" name="address" required pattern="^(?!\s*$)[A-Za-zÀ-ỹ0-9\s]+$" 
                                              title="Không được để trống, không chỉ toàn khoảng trắng và không chứa ký tự đặc biệt"></textarea>
                                </div>
                                <div class="form-group">
                                    <label>Phone</label>
                                    <input type="text" class="form-control" id="editPhone" name="phone" pattern="^\d{9,11}$" title="Vui lòng nhập số điện thoại từ 9 đến 11 chữ số, chỉ nhận chữ số." required>
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

