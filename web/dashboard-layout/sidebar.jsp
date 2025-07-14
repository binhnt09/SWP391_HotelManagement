<%-- 
    Document   : sidebar
    Created on : Jun 7, 2025, 5:49:14 PM
    Author     : viet7
--%>

<nav id="sidebar">
    <div class="sidebar-header">
        <h3>
            <a href="loadtohome">
                <img src="image/Logo.png" class="img-fluid"/>
                <b>Royal Hotel</b>
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
        
        <!-- Receptionist page -->
        <li>
            <a href="receptionistPage">
                <i class="material-icons">square</i>Room Map
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
            <a href="${pageContext.request.contextPath}/vouchermanage">
                <i class="material-icons">local_offer</i>Promotion
            </a>
        </li>

        <!-- Booking -->
        <li>
            <a href="bookingcrud">
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


