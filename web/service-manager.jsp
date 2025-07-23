<%-- 
    Document   : service-manager
    Created on : Jun 21, 2025, 9:47:01 PM
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
                    <li class="breadcrumb-item active" aria-current="page">Service</li>
                </ol>                
            </div>

        </div>
    </div>
    <!------main-content-start----------->

    <div class="main-content"> 
        <div class="row">
            <div class="col-md-12">
                <div class="table-wrapper">

                    <!-- Filter Section -->
                    <div class="card mb-3">
                        <div class="card-header">
                            <h6 class="mb-0">Filter Services</h6>
                        </div>
                        <div class="card-body py-2">
                            <form method="get" action="serviceList">
                                <div class="form-row align-items-end">

                                    <!-- Keyword (ẩn) -->
                                    <input type="hidden" name="keyword" value="${param.keyword}" />

                                    <!-- Category Filter -->
                                    <div class="form-group col-md-2 mb-1">
                                        <label class="small mb-1">Category</label>
                                        <select name="category" class="form-control form-control-sm">
                                            <option value="" ${empty param.category ? 'selected' : ''}>All</option>
                                            <option value="Spa" ${param.category == 'Spa' ? 'selected' : ''}>Spa</option>
                                            <option value="Food" ${param.category == 'Food' ? 'selected' : ''}>Food</option>
                                            <option value="Transport" ${param.category == 'Transport' ? 'selected' : ''}>Transport</option>
                                            <!-- Thêm category khác nếu cần -->
                                        </select>
                                    </div>

                                    <!-- Status Filter -->
                                    <div class="form-group col-md-2 mb-1">
                                        <label class="small mb-1">Status</label>
                                        <select name="status" class="form-control form-control-sm">
                                            <option value="" ${empty param.status ? 'selected' : ''}>All</option>
                                            <option value="true" ${param.status == 'true' ? 'selected' : ''}>Active</option>
                                            <option value="false" ${param.status == 'false' ? 'selected' : ''}>Inactive</option>
                                        </select>
                                    </div>

                                    <!-- Sort Field -->
                                    <div class="form-group col-md-2 mb-1">
                                        <label class="small mb-1">Sort By</label>
                                        <select name="sortField" class="form-control form-control-sm">
                                            <option value="" ${empty param.sortField ? 'selected' : ''}>None</option>
                                            <option value="Price" ${param.sortField == 'Price' ? 'selected' : ''}>Price</option>
                                            <option value="Name" ${param.sortField == 'Name' ? 'selected' : ''}>Name</option>
                                        </select>
                                    </div>

                                    <!-- Sort Order -->
                                    <div class="form-group col-md-1 mb-1">
                                        <label class="small mb-1">Order</label>
                                        <select name="sortOrder" class="form-control form-control-sm">
                                            <option value="asc" ${param.sortOrder == 'asc' ? 'selected' : ''}>Asc</option>
                                            <option value="desc" ${param.sortOrder == 'desc' ? 'selected' : ''}>Desc</option>
                                        </select>
                                    </div>

                                    <!-- Submit -->
                                    <div class="form-group col-md-1 mb-1">
                                        <label class="small mb-1 d-block">&nbsp;</label>
                                        <button type="submit" class="btn btn-primary btn-sm w-100">Apply</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
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
                            <div class="col-sm-6">
                                <h2 class="ml-lg-2">Manage Services</h2>
                            </div>
                            <div class="col-sm-6 text-right">
                                <a href="#" class="btn btn-success" data-toggle="modal" data-target="#addServiceModal">
                                    <i class="material-icons">&#xE147;</i> <span>Add New Service</span>
                                </a>
                                <a href="#deleteServiceModal" class="btn btn-danger" data-toggle="modal">
                                    <i class="material-icons">&#xE15C;</i> <span>Delete</span>
                                </a>
                            </div>
                        </div>
                    </div>

                    <table class="table table-striped table-hover table-bordered">
                        <thead>
                            <tr>
                                <th><input type="checkbox" id="selectAll"></th>
                                <th>Image</th>
                                <th>Name</th>
                                <th>Category</th>
                                <th>Description</th>
                                <th>Price (VNĐ)</th>
                                <th>Status</th>
                                <th>Created At</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty serviceList}">
                                    <c:forEach var="s" items="${serviceList}">
                                        <tr>
                                            <td>
                                                <input type="checkbox" name="serviceCheckbox" value="${s.serviceId}">
                                            </td>
                                            <td>
                                                <img src="${s.imageUrl}" alt="${s.name}" style="width: 60px; height: 60px; object-fit: cover; border-radius: 6px;" />
                                            </td>
                                            <td>${s.name}</td>
                                            <td>${s.category}</td>
                                            <td>
                                                <c:out value="${fn:length(s.description) > 100 ? fn:substring(s.description, 0, 100) + '...' : s.description}" />
                                            </td>
                                            <td>
                                                <fmt:formatNumber value="${s.price}" type="currency" currencySymbol="₫" />
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${s.status}">
                                                        <span class="badge badge-success" style="font-size: 14px;" >Active</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge badge-danger" style="font-size: 14px;">Inactive</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <fmt:formatDate value="${s.createdAt}" pattern="dd/MM/yyyy HH:mm" />
                                            </td>
                                            <td>
                                                <a href="#" class="editServiceBtn" 
                                                   data-id="${s.serviceId}" 
                                                   data-name="${s.name}" 
                                                   data-category="${s.category}" 
                                                   data-description="${s.description}" 
                                                   data-price="${s.price}" 
                                                   data-image="${s.imageUrl}" 
                                                   data-status="${s.status}" 
                                                   data-toggle="modal" data-target="#editServiceModal">
                                                    <i class="material-icons">&#xE254;</i>
                                                </a>
                                                <a href="serviceDelete?id=${s.serviceId}" class="delete" title="Delete"
                                                   onclick="return confirm('Xác nhận xóa dịch vụ này?');">
                                                    <i class="material-icons">&#xE872;</i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="9" style="text-align:center;">Không có dịch vụ nào.</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>


                    <c:set var="queryParams" value="" />

                    <c:if test="${not empty param.keyword}">
                        <c:set var="queryParams" value="${queryParams}&keyword=${fn:trim(param.keyword)}" />
                    </c:if>
                    <c:if test="${not empty param.category}">
                        <c:set var="queryParams" value="${queryParams}&category=${fn:trim(param.category)}" />
                    </c:if>
                    <c:if test="${not empty param.status}">
                        <c:set var="queryParams" value="${queryParams}&status=${fn:trim(param.status)}" />
                    </c:if>
                    <c:if test="${not empty param.sortField}">
                        <c:set var="queryParams" value="${queryParams}&sortField=${fn:trim(param.sortField)}" />
                    </c:if>
                    <c:if test="${not empty param.sortOrder}">
                        <c:set var="queryParams" value="${queryParams}&sortOrder=${fn:trim(param.sortOrder)}" />
                    </c:if>


                    <div class="clearfix">
                        <div class="hint-text">
                            Showing <b>${serviceList.size()}</b> Out Of <b>${totalService}</b> 
                        </div>
                        <ul class="pagination">
                            <c:choose>
                                <c:when test="${currentPage > 1}">
                                    <li class="page-item">
                                        <a href="serviceList?page=${currentPage - 1}${queryParams}" class="page-link">Previous</a>
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
                                    <a href="serviceList?page=${i}${queryParams}" class="page-link">${i}</a>
                                </li>
                            </c:forEach>

                            <c:choose>
                                <c:when test="${currentPage < totalPages}">
                                    <li class="page-item">
                                        <a href="serviceList?page=${currentPage + 1}${queryParams}" class="page-link">Next</a>
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


                    <!-- Edit Service Modal -->
                    <div class="modal fade" tabindex="-1" id="editServiceModal" role="dialog">
                        <div class="modal-dialog" role="document">
                            <form action="serviceUpdate" method="POST" enctype="multipart/form-data" onsubmit="return validateEditService()">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Edit Service</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>

                                    <div class="modal-body">
                                        <!-- Name -->
                                        <div class="form-group">
                                            <label>Service Name</label>
                                            <input type="text" class="form-control" id="editName" name="name" required>
                                        </div>

                                        <!-- Category -->
                                        <div class="form-group">
                                            <label>Category</label>
                                            <input type="text" class="form-control" id="editCategory" name="category" required>
                                        </div>

                                        <!-- Description -->
                                        <div class="form-group">
                                            <label>Description</label>
                                            <textarea class="form-control" id="editDescription" name="description" rows="3" required></textarea>
                                        </div>

                                        <!-- Price -->
                                        <div class="form-group">
                                            <label>Price (VNĐ)</label>
                                            <input type="number" step="0.01" class="form-control" id="editPrice" name="price" required>
                                        </div>

                                        <!-- Image URL -->
                                        <div class="form-group">
                                            <label>Upload Image</label>
                                            <input type="file" class="form-control-file" name="imageFile" accept="image/*" >
                                        </div>

                                        <!-- Status -->
                                        <div class="form-group">
                                            <label>Status</label>
                                            <select class="form-control" id="editStatus" name="status">
                                                <option value="true">Active</option>
                                                <option value="false">Inactive</option>
                                            </select>
                                        </div>

                                        <!-- Hidden -->
                                        <input type="hidden" id="editServiceID" name="serviceID">
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

                    <!-- Add Service Modal -->
                    <div class="modal fade" tabindex="-1" id="addServiceModal" role="dialog">
                        <div class="modal-dialog" role="document">
                            <form action="serviceUpdate" method="POST" enctype="multipart/form-data" onsubmit="return validateAddService()">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Add Service</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <!-- Name -->
                                        <div class="form-group">
                                            <label>Service Name</label>
                                            <input type="text" class="form-control" name="name" required>
                                        </div>

                                        <!-- Category -->
                                        <div class="form-group">
                                            <label>Category</label>
                                            <input type="text" class="form-control" name="category" required>
                                        </div>

                                        <!-- Description -->
                                        <div class="form-group">
                                            <label>Description</label>
                                            <textarea class="form-control" name="description" rows="3" required></textarea>
                                        </div>

                                        <!-- Price -->
                                        <div class="form-group">
                                            <label>Price (VNĐ)</label>
                                            <input type="number" step="0.01" class="form-control" name="price" required>
                                        </div>

                                        <!-- Image URL -->
                                        <div class="form-group">
                                            <label>Upload Image</label>
                                            <input type="file" class="form-control-file" name="imageFile" accept="image/*" >
                                        </div>

                                        <!-- Status -->
                                        <div class="form-group">
                                            <label>Status</label>
                                            <select class="form-control" name="status">
                                                <option value="true">Active</option>
                                                <option value="false">Inactive</option>
                                            </select>
                                        </div>

                                        <!-- Hidden -->
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
                </div>
            </div>
        </div>
    </div>

    <script>
        function isValidNameCategory(value) {
            const regex = /^[\p{L}\p{N}\s\-]+$/u; // Unicode letters, numbers, space, hyphen
            return regex.test(value);
        }

        function isValidPrice(value) {
            const price = parseFloat(value);
            return !isNaN(price) && price > 0 && price < 1_000_000_000;
        }

        function isValidImage(file) {
            if (!file)
                return true; // Image is optional
            const allowedExtensions = /\.(jpg|jpeg|png)$/i;
            const maxSize = 2 * 1024 * 1024; // 2MB
            return allowedExtensions.test(file.name) && file.size <= maxSize;
        }

        function validateForm(modalId) {
            const modal = document.getElementById(modalId);
            const name = modal.querySelector("input[name='name']").value.trim();
            const category = modal.querySelector("input[name='category']").value.trim();
            const description = modal.querySelector("textarea[name='description']").value.trim();
            const price = modal.querySelector("input[name='price']").value.trim();
            const imageFile = modal.querySelector("input[name='imageFile']").files[0];
            const status = modal.querySelector("select[name='status']").value;

            // Required fields
            if (!name || !category || !description || !price) {
                alert("Please fill in all required fields.");
                return false;
            }

            // Name & Category validation
            if (!isValidNameCategory(name)) {
                alert("Service Name contains invalid characters. Only letters, numbers, spaces, and hyphens are allowed.");
                return false;
            }
            if (!isValidNameCategory(category)) {
                alert("Category contains invalid characters. Only letters, numbers, spaces, and hyphens are allowed.");
                return false;
            }

            // Description length
            if (description.length > 500) {
                alert("Description must be less than 500 characters.");
                return false;
            }

            // Price validation
            if (!isValidPrice(price)) {
                alert("Price must be a positive number less than 1,000,000,000.");
                return false;
            }

            // Image file validation
            if (!isValidImage(imageFile)) {
                alert("Invalid image. Only .jpg, .jpeg, .png files under 2MB are allowed.");
                return false;
            }


            return true;
        }

        function validateAddService() {
            return validateForm("addServiceModal");
        }

        function validateEditService() {
            return validateForm("editServiceModal");
        }

        $(document).on("click", ".editServiceBtn", function () {
            $("#editServiceID").val($(this).data("id"));
            $("#editName").val($(this).data("name"));
            $("#editCategory").val($(this).data("category"));
            $("#editDescription").val($(this).data("description"));
            $("#editPrice").val($(this).data("price"));
            $("#editImageURL").val($(this).data("image"));
            $("#editStatus").val($(this).data("status").toString());
        });
    </script>
    <%@ include file="/dashboard-layout/footer.jsp" %>
