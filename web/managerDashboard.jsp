<%-- 
    Document   : ManagerPage
    Created on : Jun 7, 2025, 2:44:45 PM
    Author     : viet7
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <title>CRUD dashboard</title>

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="css/bootstrap.min.css">

        <link rel="stylesheet" href="css/managerDashboardCustom.css">

        <!-- Google Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">

        <!-- Google Material Icons -->
        <link href="https://fonts.googleapis.com/css2?family=Material+Icons" rel="stylesheet">

    </head>

    <body>

        <div class="wrapper">

            <div class="body-overlay"></div>

            <!-------------------------sidebar------------------------->
            <nav id="sidebar">
                <div class="sidebar-header">
                    <h3>
                        <a href="loadtohome">
                            <img src="image/Logo.png" class="img-fluid"/>
                            <b>Paletin Hotel</b>
                        </a>
                    </h3>
                </div>

                <ul class="list-unstyled components"> 
                    <li class="nav-item">
                        <a href="managerDashboard.jsp" class="nav-link p-3 rounded bg-light d-flex flex-column align-items-start hover-shadow">
                            <span class="text-secondary small font-weight-bold text-uppercase mb-1">${sessionScope.authLocal.user.roleName}</span>
                            <span class="text-dark font-weight-bold h6 mb-0">${sessionScope.authLocal.user.firstName} ${sessionScope.authLocal.user.lastName}</span>
                        </a>
                    </li>

                    <!-- Customer Management -->
                    <li class="dropdown">
                        <a href="#customerMenu" data-toggle="collapse" class="dropdown-toggle" aria-expanded="false">
                            <i class="material-icons">person</i>Customer
                        </a>
                        <ul class="collapse list-unstyled menu" id="customerMenu">
                            <li><a href="customerList"><i class="material-icons">account_circle</i>Customer Info</a></li>
                            <li><a href="customerList"><i class="material-icons">account_box</i>Customer Account</a></li>
                        </ul>
                    </li>

                    <!-- Staff Management -->
                    <li class="dropdown">
                        <a href="#staffMenu" data-toggle="collapse" class="dropdown-toggle" aria-expanded="false">
                            <i class="material-icons">people</i>Staff
                        </a>
                        <ul class="collapse list-unstyled menu" id="staffMenu">
                            <li><a href="staffList"><i class="material-icons">account_circle</i>Staff Info</a></li>
                            <li><a href="accountstaff"><i class="material-icons">account_box</i>Staff Account</a></li>
                        </ul>
                    </li>

                    <!-- Room Management -->
                    <li>
                        <a href="roomList">
                            <i class="material-icons">domain</i>Room
                        </a>
                    </li>

                    <!-- Room Management hieu -->
                    <li>
                        <a href="manageroom">
                            <i class="material-icons">domain</i>Room
                        </a>
                    </li>

                    <!-- Service Management  -->
                    <li>
                        <a href="serviceList">
                            <i class="material-icons">room_service</i>Service
                        </a>
                    </li>

                    <!-- Review Management -->
                    <li>
                        <a href="reviewmanager">
                            <i class="material-icons">rate_review</i>Review
                        </a>
                    </li>
                    
                    <!-- Cleaning List -->
                    <li>
                        <a href="cleaningList">
                            <i class="material-icons">square</i>Cleaner
                        </a>
                    </li>

                    <!-- Promotion Management -->
                    <li>
                        <a href="promotionmanager">
                            <i class="material-icons">local_offer</i>Promotion
                        </a>
                    </li>

                    <!-- Booking -->
                    <li>
                        <a href="booking">
                            <i class="material-icons">touch_app</i>Booking
                        </a>
                    </li>


                    <!-- Payment -->
                    <li>
                        <a href="paymentaddmin">
                            <i class="material-icons">payment</i>Payment
                        </a>
                    </li>

                    <!-- Account -->
                    <li>
                        <a href="authenticationList">
                            <i class="material-icons">circle</i>Account
                        </a>
                    </li>

                    <!-- Reports -->
                    <li class="dropdown">
                        <a href="#reportMenu" data-toggle="collapse" class="dropdown-toggle" aria-expanded="false">
                            <i class="material-icons">insights</i>Reports
                        </a>
                        <ul class="collapse list-unstyled menu" id="reportMenu">
                            <li><a href="customerReport"><i class="material-icons">bar_chart</i>Revenue</a></li>
                            <li><a href="bookingReport"><i class="material-icons">pie_chart</i>Booking Rate</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>





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
                                    <span class="material-icons text-white">signal_cellular_alt
                                    </span>
                                </div>
                            </div> 
                            <!-- End XP Col -->

                            <!-- Start XP Col -->
                            <div class="col-md-5 col-lg-3 order-3 order-md-2">
                                <div class="xp-searchbar">
                                    <form>
                                        <div class="input-group">
                                            <input type="search" class="form-control" 
                                                   placeholder="Search">
                                            <div class="input-group-append">
                                                <button class="btn" type="submit" 
                                                        id="button-addon2">GO</button>
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
                    <div class="xp-breadcrumbbar text-center">
                        <h4 class="page-title">Dashboard</h4>  
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="#">Dashboard</a></li>
                            <li class="breadcrumb-item active" aria-current="page">Dashboard</li>
                        </ol>                
                    </div>

                </div>



                <!--------main-content------------->

                <div class="main-content">
                    <div class="row">
                        <div class="col-sm-4">
                            <div class="table-wrapper">
                                <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                                <canvas id="revenueChart"></canvas>
                            </div>
                        </div>

                        <script>
                            fetch('/swp391_hotelmanagement/api/revenue')
                                    .then(res => res.json())
                                    .then(json => {
                                        const ctx = document.getElementById('revenueChart').getContext('2d');
                                        new Chart(ctx, {
                                            type: 'bar',
                                            data: {
                                                labels: json.labels,
                                                datasets: [{
                                                        label: 'Doanh thu',
                                                        data: json.data,
                                                        backgroundColor: '#007bff'
                                                    }]
                                            },
                                            options: {
                                                responsive: true,
                                                scales: {
                                                    y: {
                                                        beginAtZero: true
                                                    }
                                                }
                                            }
                                        });
                                    })
                                    .catch(err => console.error('Lỗi lấy dữ liệu API:', err));
                        </script>

                    </div>
                </div>

                <!---footer---->
                <footer class="footer">
                    <div class="container-fluid">
                        <div class="footer-in">
                            <p class="mb-0">&copy <%= java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) %> Vishweb design - All Rights Reserved.</p>
                        </div>
                    </div>
                </footer>
            </div>
        </div>


        <!----------html code complete----------->









        <!-- Optional JavaScript -->
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="js/jquery-3.3.1.slim.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery-3.3.1.min.js"></script>


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
