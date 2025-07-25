<%-- 
    Document   : sidebar
    Created on : Jun 7, 2025, 5:49:14 PM
    Author     : viet7
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav id="sidebar">
    <div class="sidebar-header">
        <h3>
            <a href="loadtohome">
                <img src="${pageContext.request.contextPath}/img/core-img/favicon.ico" class="img-fluid"/>
                <b>Palatin Hotel</b>
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
        <c:if test="${sessionScope.authLocal.user.roleName == 'Manager' || sessionScope.authLocal.user.roleName == 'Receptionist'}"> 
            <li class="dropdown">
                <a href="#customerMenu" data-toggle="collapse" class="dropdown-toggle" aria-expanded="false">
                    <i class="material-icons">person</i>Customer
                </a>
                <ul class="collapse list-unstyled menu" id="customerMenu">
                    <li><a href="customerList"><i class="material-icons">account_circle</i>Customer Info</a></li>
                </ul>
            </li>
        </c:if>
        <!-- Staff Management -->
        <c:if test="${sessionScope.authLocal.user.roleName == 'Manager'}">
            <li class="dropdown">
                <a href="#staffMenu" data-toggle="collapse" class="dropdown-toggle" aria-expanded="false">
                    <i class="material-icons">people</i>Staff
                </a>
                <ul class="collapse list-unstyled menu" id="staffMenu">
                    <li><a href="staffList"><i class="material-icons">account_circle</i>Staff Info</a></li>
                </ul>
            </li>
        </c:if>
        <!--         Room Management 
                <li>
                    <a href="roomList">
                        <i class="material-icons">domain</i>Room
                    </a>
                </li>-->

        <!-- Room Management hieu -->
        <c:if test="${sessionScope.authLocal.user.roleName == 'Manager' || sessionScope.authLocal.user.roleName == 'Receptionist'}"> 
            <li>
                <a href="manageroom">
                    <i class="material-icons">domain</i>Room
                </a>
            </li>
        </c:if>
        <!-- Service Management  -->
        <c:if test="${sessionScope.authLocal.user.roleName == 'Manager' || sessionScope.authLocal.user.roleName == 'Receptionist'}"> 
            <li>
                <a href="serviceList">
                    <i class="material-icons">room_service</i>Service
                </a>
            </li>
        </c:if>
        <!-- Review Management -->
        

        <!-- Receptionist page -->
        <c:if test="${sessionScope.authLocal.user.roleName == 'Manager' || sessionScope.authLocal.user.roleName == 'Receptionist'}"> 
            <li class="${pageContext.request.requestURI.endsWith('roommap.jsp') ? 'active' : ''}">
                <a href="receptionistPage">
                    <i class="material-icons">square</i>Room Map
                </a>
            </li>
        </c:if>
        <!-- Cleaning List -->
        <c:if test="${sessionScope.authLocal.user.roleName == 'Cleaner'}">
            <li class="${pageContext.request.requestURI.endsWith('cleaning-list.jsp') ? 'active' : ''}">
                <a href="cleaningList">
                    <i class="material-icons">square</i>Cleaner
                </a>
            </li>
        </c:if>
            <!-- Promotion Management -->
        <c:if test="${sessionScope.authLocal.user.userRoleId == 2}">
        <li class="${pageContext.request.requestURI.endsWith('managevoucher.jsp') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/vouchermanage">
                <i class="material-icons">local_offer</i>Promotion
            </a>
        </li>
        </c:if>
        <!-- Booking -->
        <c:if test="${sessionScope.authLocal.user.roleName == 'Manager' || sessionScope.authLocal.user.roleName == 'Receptionist'}"> 
            <li>
                <a href="bookingcrud">
                    <i class="material-icons">touch_app</i>Booking
                </a>
            </li>
        </c:if>

        <!-- Payment -->
        <c:if test="${sessionScope.authLocal.user.roleName == 'Manager' || sessionScope.authLocal.user.roleName == 'Receptionist'}">            
            <li class="${pageContext.request.requestURI.endsWith('managepayment.jsp') ? 'active' : ''}">
                <a href="${pageContext.request.contextPath}/managepayment">
                    <i class="material-icons">payment</i>Payment
                </a>
            </li>
        </c:if>

        <!-- User -->
        <c:if test="${sessionScope.authLocal.user.roleName == 'Admin System'}">
            <li>
                <a href="userList">
                    <i class="material-icons">people</i>User
                </a>
            </li>
        </c:if>
            
        <!-- Account -->
        <c:if test="${sessionScope.authLocal.user.roleName == 'Admin System'}">
            <li>
                <a href="authenticationList">
                    <i class="material-icons">circle</i>Account
                </a>
            </li>
        </c:if>

        <!-- Reports -->
        <c:if test="${sessionScope.authLocal.user.roleName == 'Manager' || sessionScope.authLocal.user.roleName == 'SystemAdmin'}">
            <li class="dropdown">
                <a href="#reportMenu" data-toggle="collapse" class="dropdown-toggle" aria-expanded="false">
                    <i class="material-icons">insights</i>Reports
                </a>
                <ul class="collapse list-unstyled menu" id="reportMenu">
                    <li><a href="customerReport"><i class="material-icons">bar_chart</i>Customer Report</a></li>
                    <!--                    <li><a href="bookingReport"><i class="material-icons">pie_chart</i>Booking Rate</a></li>-->
                </ul>
            </li>
        </c:if>
    </ul>
</nav>


