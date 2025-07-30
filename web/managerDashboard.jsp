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
<!--                    <main id="main-content" class="container mt-4">
                        <h2 class="mb-4">Xin chào, Quản lý!</h2>

                         ======= STATS BARS ======= 
                        <div class="row text-center mb-4">
                            <div class="col-md-2">
                                <div class="card bg-primary text-white shadow">
                                    <div class="card-body">
                                        <h6>Tổng số phòng</h6>
                                        <h3>${roomStats.total}</h3>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="card bg-success text-white shadow">
                                    <div class="card-body">
                                        <h6>Phòng trống</h6>
                                        <h3>${roomStats.available}</h3>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="card bg-warning text-white shadow">
                                    <div class="card-body">
                                        <h6>Đang ở</h6>
                                        <h3>${roomStats.occupied}</h3>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="card bg-danger text-white shadow">
                                    <div class="card-body">
                                        <h6>Chưa dọn</h6>
                                        <h3>${roomStats.uncleaned}</h3>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="card bg-dark text-white shadow">
                                    <div class="card-body">
                                        <h6>Bảo trì</h6>
                                        <h3>${roomStats.maintenance}</h3>
                                    </div>
                                </div>
                            </div>
                        </div>

                         ======= CHARTS ======= 
                        <div class="row mb-4">
                            <div class="col-md-6">
                                <div class="card shadow">
                                    <div class="card-header bg-light">
                                        <strong>Doanh thu theo tuần / tháng</strong>
                                    </div>
                                    <div class="card-body">
                                        <canvas id="revenueChart"></canvas>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-6">
                                <div class="card shadow">
                                    <div class="card-header bg-light">
                                        <strong>Lượng khách theo tuần / tháng</strong>
                                    </div>
                                    <div class="card-body">
                                        <canvas id="guestChart"></canvas>
                                    </div>
                                </div>
                            </div>
                        </div>

                         ======= Chart.js ======= 
                        <canvas id="revenueChart"></canvas>

                        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                        <script>
                            fetch('${pageContext.request.contextPath}/revenue-chart?type=week')
                                    .then(res => res.json())
                                    .then(data => {
                                        const labels = data.map(item => item.label);
                                        const values = data.map(item => item.revenue);

                                        new Chart(document.getElementById('revenueChart'), {
                                            type: 'bar',
                                            data: {
                                                labels: labels,
                                                datasets: [{
                                                        label: 'Doanh thu (VND)',
                                                        data: values,
                                                        backgroundColor: '#007bff'
                                                    }]
                                            },
                                            options: {
                                                scales: {
                                                    y: {
                                                        beginAtZero: true
                                                    }
                                                }
                                            }
                                        });
                                    });
                        </script>


                        <canvas id="customerChart"></canvas>

                        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                        <script>
                                                    function loadCustomerChart(type = 'week') {
                                                        fetch('${pageContext.request.contextPath}/customer-stats?type=' + type)
                                                                .then(res => res.json())
                                                                .then(data => {
                                                                    const labels = data.map(item => item.label);
                                                                    const values = data.map(item => item.count);

                                                                    new Chart(document.getElementById('customerChart'), {
                                                                        type: 'line',
                                                                        data: {
                                                                            labels: labels,
                                                                            datasets: [{
                                                                                    label: 'Số lượng khách',
                                                                                    data: values,
                                                                                    fill: false,
                                                                                    borderColor: 'rgb(75, 192, 192)',
                                                                                    tension: 0.1
                                                                                }]
                                                                        },
                                                                        options: {
                                                                            responsive: true,
                                                                            scales: {
                                                                                y: {
                                                                                    beginAtZero: true,
                                                                                    ticks: {
                                                                                        precision: 0
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    });
                                                                });
                                                    }

                                                    loadCustomerChart(); // Mặc định là tuần
                        </script>
                         Quick Links 
                        <div class="row mt-4">
                            <div class="col-md-6">
                                <a href="receptionistPage" class="btn btn-outline-primary w-100">
                                    🔍 Xem trạng thái phòng
                                </a>
                            </div>
                            <div class="col-md-6">
                                <a href="customerReport" class="btn btn-outline-secondary w-100">
                                    📊 Xem báo cáo khách hàng
                                </a>
                            </div>
                        </div>
                    </main>-->
                </div>

                <!---footer---->
                <footer class="footer">
                    <div class="container-fluid">
                        <div class="footer-in">
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
