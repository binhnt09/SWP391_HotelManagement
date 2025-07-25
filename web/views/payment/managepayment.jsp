<%-- 
    Document   : managepayment
    Created on : Jul 25, 2025, 9:33:24 AM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">

        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">

        <link rel="icon" href="${pageContext.request.contextPath}/img/core-img/favicon.ico">
        <title>Palatin Hotel - ManagePayment</title>

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
                                    <form action="managepayment" method="get" id="searchFormPayment">
                                        <div class="input-group">
                                            <input type="search" name="searchPayment" value="${searchPayment}" class="form-control" placeholder="Search payment ..."
                                                   onchange="document.getElementById('searchFormPayment').submit()">
                                            <div class="input-group-append">
                                                <button class="btn" type="submit" >GO</button>
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
                                                    <li class="nav-item dropdown menu-btn user-info">
                                                        <a class="nav-link" href="#" id="userDropdown" role="button"
                                                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                            <i class="fa fa-user"></i>
                                                        </a>
                                                        <div class="dropdown-menu dropdown-menu-right shadow border-0" aria-labelledby="userDropdown" style="min-width: 250px;">
                                                            <a class="dropdown-item" href="${pageContext.request.contextPath}/updateprofile" style="color: black"><i class="fa fa-user mr-2 text-primary"></i> Chỉnh sửa hồ sơ</a>
                                                            <a class="dropdown-item" href="${pageContext.request.contextPath}/voucher" style="color: black"><i class="fa fa-credit-card mr-2 text-primary"></i> Khuyến mãi</a>
                                                            <div class="dropdown-divider"></div>
                                                            <form action="logingoogle" method="post" style="display:inline;">
                                                                <button type="submit" name="logout" value="true" class="dropdown-item">
                                                                    <i class="fa fa-sign-out-alt mr-2"></i> Đăng xuất
                                                                </button>
                                                            </form>
                                                        </div>
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
                                        <div class="col-sm-3 p-0 d-flex justify-content-lg-start justify-content-center">
                                            <h2 class="ml-lg-2">Manage Payment</h2>
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
                                                                <li class="nav-item">
                                                                    <a class="nav-link" href="managepayment?sortby=Amount&orderSort=asc&searchPayment=${not empty searchPayment ? searchPayment : ''}">Amount</a>
                                                                </li>
                                                                <li class="nav-item">
                                                                    <a class="nav-link" href="managepayment?sortby=BankCode&orderSort=asc&searchPayment=${not empty searchPayment ? searchPayment : ''}">Bank Code</a>
                                                                </li>
                                                                <li class="nav-item">
                                                                    <a class="nav-link" href="managepayment?sortby=createdAt&orderSort=asc&searchPayment=${not empty searchPayment ? searchPayment : ''}">Create Date</a>
                                                                </li>
                                                            </ul>
                                                        </li>
                                                        <li class="nav-item submenu dropdown custom2-dropdown2">
                                                            <a href="#" class="nav-link" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                                                Decreasing</a>
                                                            <ul class=" custom2-dropdown3">
                                                                <li class="nav-item">
                                                                    <a class="nav-link" href="managepayment?sortby=Amount&orderSort=desc&searchPayment=${not empty searchPayment ? searchPayment : ''}">Amount</a>
                                                                </li>
                                                                <li class="nav-item">
                                                                    <a class="nav-link" href="managepayment?sortby=BankCode&orderSort=desc&searchPayment=${not empty searchPayment ? searchPayment : ''}">Bank code</a>
                                                                </li>
                                                                <li class="nav-item">
                                                                    <a class="nav-link" href="managepayment?sortby=createdAt&orderSort=desc&searchPayment=${not empty searchPayment ? searchPayment : ''}">Create Date</a>
                                                                </li>
                                                            </ul>
                                                        </li>
                                                    </ul>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <!--<form action="staff">-->
                                <table class="table table-striped table-hover">
                                    <thead>
                                        <tr>
                                            <th>PaymentId</th>
                                            <th>Amount</th>
                                            <th>Method</th>
                                            <th style="white-space: nowrap;">bank Code</th>
                                            <th>status</th>
                                            <th>Create At</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${listPayment}" var="payment">
                                        <input type="hidden" name="paymentId" value="${payment.paymentId}"/>
                                        <tr>
                                            <td style="white-space: nowrap;">${payment.paymentId}</td>
                                            <td style="white-space: nowrap;">${payment.amount}</td>
                                            <td style="white-space: nowrap;">${payment.method}</td>
                                            <td style="white-space: nowrap;">${payment.bankCode}</td>
                                            <td style="white-space: nowrap;">${payment.status}</td>
                                            <td style="white-space: nowrap; font-size: 14px">${payment.createdAt}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>

                                <div class="clearfix">
                                    <div class="hint-text">Showing <b>${count<6?count:'6'}</b> out of <b>${count}</b> entries</div>
                                    <ul class="pagination">
                                        <c:if test="${tag > 1}">
                                            <li class="page-item disabled">
                                                <a href="managepayment?sortby=${not empty sortby ? sortby : 'default'}&orderSort=${not empty orderSort ? orderSort : 'default'}&index=${tag - 1}&searchPayment=${not empty searchPayment ? searchPayment : ''}">Previous</a>
                                            </li>
                                        </c:if>
                                        <c:forEach begin="1" end="${endPage}" var="i">
                                            <li class="page-item  ${tag == i?"active":""}">
                                                <a href="managepayment?sortby=${not empty sortby ? sortby : 'default'}&orderSort=${not empty orderSort ? orderSort : 'default'}&index=${i}&searchPayment=${not empty searchPayment ? searchPayment : ''}" class="page-link">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <c:if test="${tag < endPage}">
                                            <li class="page-item">
                                                <a href="managepayment?sortby=${not empty sortby ? sortby : 'default'}&orderSort=${not empty orderSort ? orderSort : 'default'}&index=${tag + 1}&searchPayment=${not empty searchPayment ? searchPayment : ''}" class="page-link">Next</a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </div>
                            </div>
                        </div>

                        <!-- Delete Modal HTML -->
                        <div id="deleteVoucherModal" class="modal fade">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <form action="" method="post">
                                        <input type="hidden" name="action" value="removeVoucher"/>
                                        <input type="hidden" name="voucherId" value="" id="voucherIdRe"/>
                                        <input type="hidden" name="index" value="" /> 

                                        <div class="modal-header">
                                            <h4 class="modal-title">Delete Voucher</h4>
                                            <button type="button" class="close" data-dismiss="modal" 
                                                    aria-hidden="true">&times;</button>
                                        </div>
                                        <div class="modal-body">
                                            <p>Are you sure you want to delete these Records?</p>
                                            <p class="text-warning"><small>This action cannot be undone.</small></p>
                                        </div>
                                        <div class="modal-footer">
                                            <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                                            <button id="deleteVoucherBtn" class="btn btn-danger">Delete</button>
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
                            <p class="mb-0">&copy 2025 Palatin Hotel - Manage Payment.</p>
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
        <script>
            const contextPath = '<%= request.getContextPath() %>';
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






