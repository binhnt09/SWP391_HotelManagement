<%-- 
    Document   : managevoucher
    Created on : Jul 12, 2025, 9:42:30 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">

        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">

        <link rel="icon" href="${pageContext.request.contextPath}/img/core-img/favicon.ico">
        <title>Palatin Hotel - ManageVoucher</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/managevoucher/custom.css">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/managevoucher/bootstrap.min.css">

        <!--google fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">

        <!--google material icon-->
        <link href="https://fonts.googleapis.com/css2?family=Material+Icons"rel="stylesheet">
    </head>
    <body>

        <div class="wrapper">

            <div class="body-overlay"></div>
            <!-------------------------sidebar------------>
            <!-- Brand and toggle get grouped for better mobile display -->
            <!-- Sidebar  -->
            <%@ include file="/dashboard-layout/sidebar.jsp" %>

            <!--------page-content---------------->

            <div id="content">
                <!--top--navbar----design--------->

                <div class="top-navbar">
                    <div class="xp-topbar">

                        <!-- Start XP Row -->
                        <div class="row"> 
                            <!-- Start XP Col -->
                            <div class="col-2 col-md-1 col-lg-1 order-2 order-md-1 align-self-center">
                                <div class="xp-menubar">
                                    <span class="material-icons text-white">signal_cellular_alt</span>
                                </div>
                            </div> 
                            <!-- End XP Col -->

                            <!-- Start XP Col -->
                            <div class="col-md-5 col-lg-3 order-3 order-md-2">
                                <div class="xp-searchbar">
                                    <div class="input-group">
                                        <input type="search" name="searchAccount" value="${searchVoucher}" class="form-control" placeholder="Search">
                                        <div class="input-group-append">
                                            <button class="btn" type="submit" id="button-addon2">GO</button>
                                        </div>
                                    </div>
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
                                                    <li><a href="#">You have 5 new messages</a></li>
                                                    <li><a href="#">You're now friend with Mike</a></li>
                                                    <li><a href="#">Wish Mary on her birthday!</a></li>
                                                    <li><a href="#">5 warnings in Server Console</a></li>
                                                </ul>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link" href="#">
                                                    <span class="material-icons">question_answer</span>
                                                </a>
                                            </li>
                                            <li class="nav-item dropdown">
                                                <a class="nav-link" href="#" data-toggle="dropdown">
                                                    <img src="img/admin2.jpg" style="width:35px; border-radius:50%;"/>
                                                    <span class="xp-user-live"></span>
                                                </a>
                                                <ul class="dropdown-menu small-menu">
                                                    <li>
                                                        <a href="#"><span class="material-icons">settings</span>Settings</a>
                                                    </li>
                                                    <li>
                                                        <a href="login.jsp"><span class="material-icons">logout</span>Logout</a>
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
                        <ul class="breadcrumb" style="background: none;">
                            <li class="breadcrumb-item"><a href="#">Booster</a></li>
                            <li class="breadcrumb-item active" aria-current="page">Dashboard</li>
                        </ul>                
                    </div>
                </div>


                <!--------main-content------------->

                <div class="main-content">
                    <div class="row" id="tableStaff_main">

                        <div id="staffTable" class="col-md-12">
                            <div id="customertable" class="table-wrapper">
                                <div class="table-title">
                                    <div class="row">
                                        <div class="col-sm-6 p-0 d-flex justify-content-lg-start justify-content-center">
                                            <h2 class="ml-lg-2">Manage Voucher</h2>
                                            <!--<h2 class="ml-lg-4">Sort</h2>-->
                                        </div>
                                        <div class="col-sm-6 p-0 d-flex justify-content-lg-start justify-content-center">
                                            <ul class="nav navbar-nav menu_nav ml-auto">
                                                <li class="nav-item submenu dropdown">
                                                    <a href="#" class="nav-link custom1-dropdown1" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                                        <span>Sort</span> <i class="material-icons">arrow_drop_down</i></a>
                                                    <ul class="dropdown-menu custom2-dropdown1">
                                                        <li class="nav-item submenu dropdown custom2-dropdown2">
                                                            <a href="#" class="nav-link" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                                                Asscending</a>
                                                            <ul class=" custom2-dropdown3">

                                                            </ul>
                                                        </li>
                                                        <li class="nav-item submenu dropdown custom2-dropdown2">
                                                            <a href="#" class="nav-link" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                                                Decreasing</a>
                                                            <ul class=" custom2-dropdown3">

                                                            </ul>
                                                        </li>
                                                    </ul>
                                                </li>
                                            </ul>
                                            <a href="#addEmployeeModal" class="btn btn-success" data-toggle="modal">
                                                <i class="material-icons">&#xE147;</i> <span>Add New Voucher</span></a>
                                            <a href="#deleteEmployeeModal" class="btn btn-danger" data-toggle="modal">
                                                <i class="material-icons">&#xE15C;</i> <span>Delete</span></a>
                                        </div>
                                    </div>
                                </div>
                                <!--<form action="staff">-->
                                <table class="table table-striped table-hover">
                                    <thead>
                                        <tr>
                                            <th>
                                                <span class="custom-checkbox">
                                                    <input type="checkbox" id="selectAll">
                                                    <label for="selectAll"></label>
                                                </span>
                                            </th>
                                            <th style="white-space: nowrap;">Code</th>
                                            <th style="white-space: nowrap;">Discount(%)</th>
                                            <th>Valid From</th>
                                            <th>Valid To</th>
                                            <th>Create At</th>
                                            <th>Update At</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${listVoucher}" var="voucher">
                                            <tr>
                                                <td>
                                                    <span class="custom-checkbox">
                                                        <input type="checkbox" class="select-item" id="checkbox1" name="options[]" value=""/>
                                                        <label for="checkbox1"></label>
                                                    </span>
                                                </td>
                                                <td style="white-space: nowrap;">${voucher.code}</td>
                                                <td style="white-space: nowrap;">${voucher.discountPercentage} %</td>
                                                <td style="white-space: nowrap;">${voucher.validFrom}</td>
                                                <td style="white-space: nowrap;">${voucher.validTo}</td>
                                                <td style="white-space: nowrap;">${voucher.createdAt}</td>
                                                <td style="white-space: nowrap;">${voucher.updatedAt}</td>
                                                <td>
                                                    <a href="#editEmployeeModal" class="edit" data-toggle="modal">
                                                        <i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
                                                    <a href="#" class="delete" data-toggle="modal" data-target="#deleteEmployeeModal" data-id="">
                                                        <i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
                                                </td> 
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                                <div class="clearfix">
                                    <div class="hint-text">Showing <b>${count<5?count:'5'}</b> out of <b>${count}</b> entries</div>
                                    <ul class="pagination">
                                        <c:if test="${tag > 1}">
                                            <li class="page-item disabled"><a href="vouchermanage?sortby=${not empty sortby ? sortby : 'default'}&orderSort=${not empty orderSort ? orderSort : 'default'}&index=${tag - 1}&searchVoucher=${not empty searchVoucher ? searchVoucher : ''}">Previous</a>
                                            </li>
                                        </c:if>
                                        <c:forEach begin="1" end="${endPage}" var="i">
                                            <li class="page-item  ${tag == i?"active":""}"><a href="vouchermanage?sortby=${not empty sortby ? sortby : 'default'}&orderSort=${not empty orderSort ? orderSort : 'default'}&index=${i}&searchVoucher=${not empty searchVoucher ? searchVoucher : ''}" class="page-link">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <c:if test="${tag < endPage}">
                                            <li class="page-item">
                                                <a href="vouchermanage?sortby=${not empty sortby ? sortby : 'default'}&orderSort=${not empty orderSort ? orderSort : 'default'}&index=${tag + 1}&searchVoucher=${not empty searchVoucher ? searchVoucher : ''}" class="page-link">Next</a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </div>
                            </div>
                        </div>

                        <!-- Add Modal HTML -->
                        <div id="addEmployeeModal" class="modal fade">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <form action="${pageContext.request.contextPath}/vouchermanage" method="post">
                                        <input type="hidden" name="action" value="insertVoucher"/>
                                        <div class="modal-header">
                                            <h4 class="modal-title">Create Voucher</h4>
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="form-group" style="display: flex; gap: 10px">
                                                <div style="flex: 2;">
                                                    <label>Voucher Code</label>
                                                    <input type="text" value="" name="voucherCode" class="form-control" required>
                                                </div>
                                                <div style="flex: 1;">
                                                    <label>DiscountPercentage</label>
                                                    <input type="number" value="" name="Discout" class="form-control" min="0" max="100" step="1" required>
                                                </div>
                                            </div>

                                            <div class="form-group" style="display: flex; gap: 10px">
                                                <div style="flex: 1">
                                                    <label>Valid From</label>
                                                    <input type="date" id="validfrom" value="" name="validfrom" class="form-control" ">
                                                </div>
                                                <div style="flex: 1">
                                                    <label>Valid To</label>
                                                    <input type="date" id="validto" value="" name="validto" class="form-control" ">
                                                </div>
                                            </div>

                                            <div class="form-group" style="display: flex; gap: 10px">
                                                <div style="flex: 1">
                                                    <label class="form-label">Member level</label>
                                                    <ul class="nav navbar-nav menu_nav">
                                                        <li class="nav-item submenu dropdown">
                                                            <a href="#" id="memberDropdown" class="nav-link dropdown-toggle" style="background: #cccccc; color: black" 
                                                               data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                                                <span>Select Level</span>
                                                            </a>
                                                            <ul class="dropdown-menu" style="width: 100%; text-align: left">
                                                                <c:forEach var="memberShip" items="${listMemberShip}">
                                                                    <li class="nav-item submenu dropdown">
                                                                        <label class="dropdown-item" style="cursor: pointer;">
                                                                            <input type="checkbox" style="width: 18px; height: 18px; margin: 5px" 
                                                                                   class="member-checkbox" value="${memberShip.levelId}" data-name="${memberShip.levelName}">
                                                                            ${memberShip.levelName}
                                                                        </label>
                                                                    </li>
                                                                </c:forEach>
                                                            </ul>
                                                        </li>
                                                    </ul>
                                                    <input type="hidden" id="memberShipId" name="memberShipId">
                                                </div>

                                                <div style="flex: 1">
                                                    <label>Phone</label>
                                                    <input type="text" value="" name="phone" class="form-control">
                                                    <p id="phoneError" style="color: red;"></p>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                                                <input type="submit" class="btn btn-info" value="Save">
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <!-- Edit Modal HTML -->
                        <div id="editEmployeeModal" class="modal fade">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <form action="updateasg" method="post">
                                        <input type="hidden" name="update" value="updateBooking"/>
                                        <input type="hidden" name="bookingID" value="" id="bookIDud"/>
                                        <div class="modal-header">
                                            <h4 class="modal-title">Edit Booking</h4>
                                            <button type="button" class="close" data-dismiss="modal" 
                                                    aria-hidden="true">&times;</button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="form-group" style="display: flex; gap: 10px">
                                                <div style="flex: 1;">
                                                    <label>First Name</label>
                                                    <input type="text" name="fnameud" id="fnameud" class="form-control" required>
                                                </div>
                                                <div style="flex: 1;">
                                                    <label>Last Name</label>
                                                    <input type="text" name="lnameud" id="lnameud" class="form-control" required>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="form-label">Room Type</label>
                                                <select id="roomIDsud" name="bookingIDud" class="form-control" onchange="updateTotalPriceud()">
                                                    <option>Select Rooms</option>
                                                </select>
                                                <input type="hidden" id="roomIDsud" name="roomIDs">
                                            </div>

                                            <div class="form-group" style="display: flex; gap: 10px">
                                                <div style="flex: 1">
                                                    <label>CheckinDate</label>
                                                    <input type="date" id="checkInud" name="checkinDate" class="form-control" onchange="updateTotalPriceud()">
                                                </div>
                                                <div style="flex: 1">
                                                    <label>CheckoutDate</label>
                                                    <input type="date" id="checkOutud" name="checkoutDate" class="form-control" onchange="updateTotalPriceud()">
                                                </div>
                                            </div>

                                            <div class="form-group" style="display: flex; gap: 10px">
                                                <div style="flex: 1">
                                                    <label>Phone</label>
                                                    <input type="text" id="phoneud" name="phone" class="form-control" required>
                                                    <p id="phoneError" style="color: red;">${phoneError}</p>
                                                </div>
                                                <div style="flex: 1">
                                                    <label>Email</label>
                                                    <input type="email" id="emailud" name="email" class="form-control" required>
                                                    <p id="emailError" style="color: red;">${emailError}</p>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>TotalPrice</label>
                                                <input type="text" id="totalPriceud" name="TotalPrice" class="form-control" >
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                                            <input type="submit" class="btn btn-info" value="Save">
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>


                        <!-- Delete Modal HTML -->
                        <div id="deleteEmployeeModal" class="modal fade">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <form action="deletebooking" method="post">
                                        <div class="modal-header">
                                            <h4 class="modal-title">Delete Booking</h4>
                                            <button type="button" class="close" data-dismiss="modal" 
                                                    aria-hidden="true">&times;</button>
                                        </div>
                                        <div class="modal-body">
                                            <p>Are you sure you want to delete these Records?</p>
                                            <p class="text-warning"><small>This action cannot be undone.</small></p>
                                        </div>
                                        <div class="modal-footer">
                                            <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                                            <!--<input id="deleteStaffBtn" type="submit" class="btn btn-danger" value="Delete">-->
                                            <button id="deleteStaffBtn" class="btn btn-danger">Delete</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                    </div>

                    <!---footer---->

                </div>

                <footer class="footer">
                    <div class="container-fluid">
                        <div class="footer-in">
                            <p class="mb-0">&copy 2025 Palatin Hotel - Manage voucher.</p>
                        </div>
                    </div>
                </footer>
            </div>
        </div>

        <!-- Optional JavaScript -->
        <!-- Thư viện SweetAlert2 -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <!-- jQuery và Bootstrap -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="${pageContext.request.contextPath}/js/managevoucher/jquery-3.3.1.slim.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/managevoucher/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/managevoucher/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/managevoucher/jquery-3.3.1.min.js"></script>
        
        <script src="${pageContext.request.contextPath}/js/payment/voucher.js"></script>

        <script>
        </script>
        <script type="text/javascript">
            $(document).ready(function () {
                $(".xp-menubar").on('click', function () {
                    $('#sidebar').toggleClass('active');
                    $('#content').toggleClass('active');
                });

                $(".xp-menubar,.body-overlay").on('click', function () {
                    $('#sidebar,.body-overlay').toggleClass('show-nav');
                });
            });
        </script>
    </body>
</html>





