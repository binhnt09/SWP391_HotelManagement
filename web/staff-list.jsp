<%-- 
   Document   : staff-list
   Created on : Jun 8, 2025, 10:30:05 AM
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
                        <form method="get" action="staffList">
                            <div class="input-group">
                                <input type="search" class="form-control" name="keyword"
                                       value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>"
                                       placeholder="Search">
                                <div class="input-group-append">
                                    <button class="btn" type="submit" id="button-addon2">GO</button>
                                </div>
                            </div>
                            <input type="hidden" name="role" value="${role}"/>

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
                <li class="breadcrumb-item active" aria-current="page">Staff Info</li>
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
                                <h2 class="ml-lg-2">Manage Staff</h2>
                            </div>
                            <div class="col-md-6 d-flex justify-content-end align-items-center">
                                <form method="get" action="staffList" class="form-inline mb-2">
                                    <label>Filter by role: </label>
                                    <select name="role" onchange="this.form.submit()" class="form-control ml-2">
                                        <option value="3" ${role == 3 ? 'selected' : ''}>Receptionist</option>
                                        <option value="4" ${role == 4 ? 'selected' : ''}>Cleaner</option>
                                    </select>
                                    <input type="hidden" name="keyword" value="${keyword}"/>
                                </form>
                                <a href="#addEmployeeModal" class="btn btn-success" data-toggle="modal">
                                    <i class="material-icons">&#xE147;</i>
                                    <span>Add New Staff</span>
                                </a>
                                <!--                                <a href="#deleteStaffModal" class="btn btn-danger" data-toggle="modal">
                                                                    <i class="material-icons">&#xE15C;</i>
                                                                    <span>Delete</span>
                                                                </a>-->
                            </div>
                        </div>
                    </div>

                    <!-- Staff Table -->
                    <table class="table table-striped table-hover table-bordered">
                        <thead>
                            <tr>
                                <th><span class="custom-checkbox">
                                        <input type="checkbox" id="selectAll">
                                        <label for="selectAll"></label>
                                    </span></th>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Address</th>
                                <th>Role</th>
                                <th>Create At</th>
                                <th>Actions</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:choose>
                                <c:when test="${not empty staffList}">
                                    <c:forEach var="s" items="${staffList}" varStatus="status">
                                        <tr>
                                            <td>
                                                <span class="custom-checkbox">
                                                    <input type="checkbox" id="checkbox${status.index}" name="option[]" value="${s.userId}">
                                                    <label for="checkbox${status.index}"></label>
                                                </span>
                                            </td>
                                            <td>${s.firstName}</td>
                                            <td>${s.lastName}</td>
                                            <td>${s.email}</td>
                                            <td>${s.phone}</td>
                                            <td>${s.address}</td>
                                            <td>${s.roleName}</td>
                                            <td>${s.createdAt}</td>
                                            <td>
                                                <a href="#editEmployeeModal"
                                                   class="edit"
                                                   data-toggle="modal"
                                                   data-id="${s.userId}"
                                                   data-firstname="${s.firstName}"
                                                   data-lastname="${s.lastName}"
                                                   data-email="${s.email}"
                                                   data-phone="${s.phone}"
                                                   data-address="${s.address}">
                                                    <i class="material-icons">&#xE254;</i>
                                                </a>
                                                <a href="staffDelete?id=${s.userId}" class="delete" data-toggle="tooltip" title="Delete"
                                                   onclick="return confirm('Xác nhận xóa nhân viên này?');">
                                                    <i class="material-icons">&#xE872;</i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="9" style="text-align:center;">Không có nhân viên nào.</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>

                    <!-- Pagination -->
                    <div class="clearfix">
                        <div class="hint-text">showing <b>${staffList.size()}</b> out of <b>${totalStaff}</b></div>
                        <ul class="pagination">
                            <c:choose>
                                <c:when test="${currentPage > 1}">
                                    <li class="page-item"><a href="staffList?page=${currentPage - 1}&role=${role}&keyword=${keyword}" class="page-link">Previous</a></li>
                                    </c:when>
                                    <c:otherwise>
                                    <li class="page-item disabled"><a href="#" class="page-link">Previous</a></li>
                                    </c:otherwise>
                                </c:choose>

                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a href="staffList?page=${i}&role=${role}&keyword=${keyword}" class="page-link">${i}</a>
                                </li>
                            </c:forEach>

                            <c:choose>
                                <c:when test="${currentPage < totalPages}">
                                    <li class="page-item"><a href="staffList?page=${currentPage + 1}&role=${role}&keyword=${keyword}" class="page-link">Next</a></li>
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
                    <form action="staffUpdate" method="POST">
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
                                    <input type="text" class="form-control" id="firstName" name="firstName" required pattern="^(?!\s*$)[A-Za-zÀ-ỹ0-9\s]+$" 
                                           title="Không được để trống, không chỉ toàn khoảng trắng và không chứa ký tự đặc biệt">
                                    <div class="invalid-feedback" id="firstNameError"></div>
                                </div>

                                <div class="form-group">
                                    <label>Last Name</label>
                                    <input type="text" class="form-control" id="lastName" name="lastName" required pattern="^(?!\s*$)[A-Za-zÀ-ỹ0-9\s]+$" 
                                           title="Không được để trống, không chỉ toàn khoảng trắng và không chứa ký tự đặc biệt">
                                    <div class="invalid-feedback" id="lastNameError"></div>
                                </div>

                                <div class="form-group">
                                    <label>Email</label>
                                    <input type="email" class="form-control" id="email" name="email" required>
                                    <div class="invalid-feedback" id="emailError"></div>
                                </div>

                                <div class="form-group">
                                    <label>Address</label>
                                    <textarea class="form-control" id="address" name="address" required pattern="^(?!\s*$)[A-Za-zÀ-ỹ0-9\s]+$" 
                                              title="Không được để trống, không chỉ toàn khoảng trắng và không chứa ký tự đặc biệt"></textarea>
                                    <div class="invalid-feedback" id="addressError"></div>
                                </div>

                                <div class="form-group">
                                    <label>Phone</label>
                                    <input type="text" class="form-control" id="phone" name="phone" required pattern="0\d{9}" title="Số điện thoại không hợp lệ (phải có 10 chữ số và bắt đầu bằng 0)!" required>
                                    <div class="invalid-feedback" id="phoneError"></div>
                                </div>
                                <div class="form-group">
                                    <label>Position</label>
                                    <select name="roleID" class="form-control">
                                        <option value="3" ${role == 3 ? 'selected' : ''}>Receptionist</option>
                                        <option value="4" ${role == 4 ? 'selected' : ''}>Cleaner</option>
                                    </select>
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
                    <form id="editEmployeeForm" action="staffUpdate" method="POST">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Edit employee</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <label>First Name</label>
                                    <input type="text" class="form-control" id="editFirstName" name="firstName" required pattern="^(?!\s*$)[A-Za-zÀ-ỹ0-9\s]+$" 
                                           title="Không được để trống, không chỉ toàn khoảng trắng và không chứa ký tự đặc biệt">
                                    <small class="text-danger d-none" id="errorEditFirstName"></small>
                                </div>

                                <div class="form-group">
                                    <label>Last Name</label>
                                    <input type="text" class="form-control" id="editLastName" name="lastName" required pattern="^(?!\s*$)[A-Za-zÀ-ỹ0-9\s]+$" 
                                           title="Không được để trống, không chỉ toàn khoảng trắng và không chứa ký tự đặc biệt">
                                    <small class="text-danger d-none" id="errorEditLastName"></small>
                                </div>

                                <div class="form-group">
                                    <label>Email</label>
                                    <input type="email" class="form-control" id="editEmail" name="email" readonly>
                                </div>

                                <div class="form-group">
                                    <label>Address</label>
                                    <textarea class="form-control" id="editAddress" name="address" required pattern="^(?!\s*$)[A-Za-zÀ-ỹ0-9\s]+$" 
                                              title="Không được để trống, không chỉ toàn khoảng trắng và không chứa ký tự đặc biệt"></textarea>
                                    <small class="text-danger d-none" id="errorEditAddress"></small>
                                </div>

                                <div class="form-group">
                                    <label>Phone</label>
                                    <input type="text" class="form-control" id="editPhone" name="phone" pattern="0\d{9}" title="Số điện thoại không hợp lệ (phải có 10 chữ số và bắt đầu bằng 0)!" required>
                                    <small class="text-danger d-none" id="errorEditPhone"></small>
                                </div>

                                <div class="form-group">
                                    <label>Position</label>
                                    <select name="roleID" class="form-control">
                                        <option value="3" ${role == 3 ? 'selected' : ''}>Receptionist</option>
                                        <option value="4" ${role == 4 ? 'selected' : ''}>Cleaner</option>
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

        document.querySelector("#addEmployeeModal form").addEventListener("submit", function (e) {
            e.preventDefault();

            const fields = ["firstName", "lastName", "email", "address", "phone"];
            fields.forEach(field => {
                document.getElementById(field).classList.remove("is-invalid");
                document.getElementById(field + "Error").textContent = "";
            });

            const firstName = document.getElementById("firstName").value.trim();
            const lastName = document.getElementById("lastName").value.trim();
            const email = document.getElementById("email").value.trim();
            const address = document.getElementById("address").value.trim();
            const phone = document.getElementById("phone").value.trim();

            const nameRegex = /^[A-Za-zÀ-ỹà-ỵ\s'-]+$/; // Cho phép tên tiếng Việt, dấu, khoảng trắng, dấu nháy đơn
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            const phoneRegex = /^0\d{9}$/;

            let isValid = true;

            if (!firstName) {
                showError("firstName", "First name is required.");
                isValid = false;
            } else if (!nameRegex.test(firstName)) {
                showError("firstName", "First name contains invalid characters.");
                isValid = false;
            }

            if (!lastName) {
                showError("lastName", "Last name is required.");
                isValid = false;
            } else if (!nameRegex.test(lastName)) {
                showError("lastName", "Last name contains invalid characters.");
                isValid = false;
            }

            if (!email) {
                showError("email", "Email is required.");
                isValid = false;
            } else if (!emailRegex.test(email)) {
                showError("email", "Email format is invalid.");
                isValid = false;
            }

            if (!address) {
                showError("address", "Address is required.");
                isValid = false;
            }

            if (!phone) {
                showError("phone", "Phone number is required.");
                isValid = false;
            } else if (!phoneRegex.test(phone)) {
                showError("phone", "Phone must start with 0 and contain exactly 10 digits.");
                isValid = false;
            }

            if (isValid) {
                this.submit();
            }

            function showError(fieldId, message) {
                const input = document.getElementById(fieldId);
                const errorDiv = document.getElementById(fieldId + "Error");
                input.classList.add("is-invalid");
                errorDiv.textContent = message;
            }
        });

        document.getElementById('editEmployeeForm').addEventListener('submit', function (e) {
            let isValid = true;

            const firstName = document.getElementById('editFirstName');
            const lastName = document.getElementById('editLastName');
            const address = document.getElementById('editAddress');
            const phone = document.getElementById('editPhone');

            const errorFirstName = document.getElementById('errorEditFirstName');
            const errorLastName = document.getElementById('errorEditLastName');
            const errorAddress = document.getElementById('errorEditAddress');
            const errorPhone = document.getElementById('errorEditPhone');

            const nameRegex = /^[a-zA-ZÀ-ỹ\s'-]+$/;
            const phoneRegex = /^(0\d{9,10})$/;

            [errorFirstName, errorLastName, errorAddress, errorPhone].forEach(el => el.classList.add('d-none'));

            if (!firstName.value.trim() || !nameRegex.test(firstName.value.trim())) {
                errorFirstName.textContent = "First name is required and must not contain special characters.";
                errorFirstName.classList.remove('d-none');
                isValid = false;
            }
            if (!lastName.value.trim() || !nameRegex.test(lastName.value.trim())) {
                errorLastName.textContent = "Last name is required and must not contain special characters.";
                errorLastName.classList.remove('d-none');
                isValid = false;
            }

            if (!address.value.trim()) {
                errorAddress.textContent = "Address is required.";
                errorAddress.classList.remove('d-none');
                isValid = false;
            }

            if (!phone.value.trim() || !phoneRegex.test(phone.value.trim())) {
                errorPhone.textContent = "Phone must be 10–11 digits and start with 0.";
                errorPhone.classList.remove('d-none');
                isValid = false;
            }

            if (!isValid) {
                e.preventDefault();
            }
        });
    </script>
</script>

<!------main-content-end----------->

<%@ include file="/dashboard-layout/footer.jsp" %>
