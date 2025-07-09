<%-- 
    Document   : customer-report
    Created on : Jun 16, 2025, 2:00:09 PM
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
                        <form method="get" action="customerReport">
                            <input type="hidden" name="bookingRange" value="${param.bookingRange}" />
                            <input type="hidden" name="spentRange" value="${param.spentRange}" />
                            <input type="hidden" name="sort" value="${param.sort}" />
                            <input type="hidden" name="order" value="${param.order}" />
                            <input type="hidden" name="registerStartDate" value="${param.registerStartDate}" />
                            <input type="hidden" name="registerEndDate" value="${param.registerEndDate}" />
                            <input type="hidden" name="bookingStartDate" value="${param.bookingStartDate}" />
                            <input type="hidden" name="bookingEndDate" value="${param.bookingEndDate}" />
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
        </div>
    </div>

    <div class="main-content"> 
        <div class="row">
            <div class="col-md-12">

                <!-- Filter Section -->
                <div class="card mb-3">
                    <div class="card-header">
                        <h6 class="mb-0">Filter Customer Report</h6>
                    </div>
                    <div class="card-body py-2">
                        <form method="get" action="customerReport">
                            <div class="form-row align-items-end">
                                <input type="hidden" name="keyword" value="${param.keyword}" />

                                <div class="form-group col-md-2 mb-1">
                                    <label class="small mb-1">Bookings</label>
                                    <select name="bookingRange" class="form-control form-control-sm">
                                        <option value="" ${empty bookingRange ? 'selected' : ''}>All</option>
                                        <option value="0" ${bookingRange == '0' ? 'selected' : ''}>0</option>
                                        <option value="1-5" ${bookingRange == '1-5' ? 'selected' : ''}>1–5</option>
                                        <option value="6-10" ${bookingRange == '6-10' ? 'selected' : ''}>6–10</option>
                                        <option value="11-30" ${bookingRange == '11-30' ? 'selected' : ''}>11–30</option>
                                        <option value="31-50" ${bookingRange == '31-50' ? 'selected' : ''}>31–50</option>
                                        <option value="50+" ${bookingRange == '50+' ? 'selected' : ''}>>50</option>
                                    </select>
                                </div>

                                <div class="form-group col-md-2 mb-1">    
                                    <label class="small mb-1">Spent</label>
                                    <select name="spentRange" class="form-control form-control-sm">
                                        <option value="" ${empty spentRange ? 'selected' : ''}>All</option>
                                        <option value="bronze" ${spentRange == 'bronze' ? 'selected' : ''}>Bronze (0 – 4.9M)</option>
                                        <option value="silver" ${spentRange == 'silver' ? 'selected' : ''}>Silver (5M – 19.9M)</option>
                                        <option value="gold" ${spentRange == 'gold' ? 'selected' : ''}>Gold (20M – 49.9M)</option>
                                        <option value="platinum" ${spentRange == 'platinum' ? 'selected' : ''}>Platinum (≥ 50M)</option>
                                    </select>
                                </div>

                                <div class="form-group col-md-2 mb-1">
                                    <label class="small mb-1">Sort</label>
                                    <select name="sort" class="form-control form-control-sm">
                                        <option value="" ${empty sort ? 'selected' : ''}>None</option>
                                        <option value="totalSpent" ${sort == 'totalSpent' ? 'selected' : ''}>Total Spent</option>
                                        <option value="totalBookings" ${sort == 'totalBookings' ? 'selected' : ''}>Bookings</option>
                                        <option value="registerDate" ${sort == 'registerDate' ? 'selected' : ''}>Register Date</option>
                                    </select>
                                </div>

                                <div class="form-group col-md-1 mb-1">
                                    <label class="small mb-1">Order</label>
                                    <select name="order" class="form-control form-control-sm">
                                        <option value="asc" ${order == 'asc' ? 'selected' : ''}>Asc</option>
                                        <option value="desc" ${order == 'desc' ? 'selected' : ''}>Desc</option>
                                    </select>
                                </div>

                                <div class="form-group col-md-1 mb-1">
                                    <label class="small mb-1 d-block">&nbsp;</label>
                                    <button type="submit" class="btn btn-primary btn-sm w-100">Apply</button>
                                </div>
                            </div>

                            <div class="form-row align-items-end mt-2">
                                <div class="form-group col-md-3 mb-1">
                                    <label class="small mb-1">Register Date</label>
                                    <div class="d-flex gap-1">
                                        <input type="date" name="registerStartDate" class="form-control form-control-sm mr-1"
                                               value="${registerStartDate}">
                                        <input type="date" name="registerEndDate" class="form-control form-control-sm"
                                               value="${registerEndDate}">
                                    </div>
                                </div>

                                <div class="form-group col-md-3 mb-1">
                                    <label class="small mb-1">Last Booking</label>
                                    <div class="d-flex gap-1">
                                        <input type="date" name="bookingStartDate" class="form-control form-control-sm mr-1"
                                               value="${bookingStartDate}">
                                        <input type="date" name="bookingEndDate" class="form-control form-control-sm"
                                               value="${bookingEndDate}">
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="table-wrapper">
                    <div class="table-title">
                        <div class="row">
                            <div class="col-sm-2">
                                <h2 class="mb-0">Customer Report</h2>
                            </div>
                            <div class="col-sm-10">
                                <form action="exportExcel" method="post" target="_blank">
                                    <input type="hidden" name="bookingRange" value="${param.bookingRange}" />
                                    <input type="hidden" name="spentRange" value="${param.spentRange}" />
                                    <input type="hidden" name="sort" value="${param.sort}" />
                                    <input type="hidden" name="order" value="${param.order}" />
                                    <input type="hidden" name="registerStartDate" value="${param.registerStartDate}" />
                                    <input type="hidden" name="registerEndDate" value="${param.registerEndDate}" />
                                    <input type="hidden" name="bookingStartDate" value="${param.bookingStartDate}" />
                                    <input type="hidden" name="bookingEndDate" value="${param.bookingEndDate}" />
                                    <input type="hidden" name="keyword" value="${param.keyword}" />

                                    <button type="submit" class="btn btn-success"> <i class="fa fa-file-excel-o"></i>Export Excel</button>
                                </form>
                            </div>

                        </div>
                    </div>
                    <table class="table table-striped table-hover table-bordered">
                        <thead>
                            <tr>
                                <th>User ID</th>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Email</th>
                                <th>Total Bookings</th>
                                <th>Total Spent(VND)</th>
                                <th>Last Booking</th>
                                <th>Register Date</th>
                                <th>Tier</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty customerReport}">
                                    <c:forEach var="c" items="${customerReport}">
                                        <tr>
                                            <td>${c.userId}</td>
                                            <td>${c.firstName}</td>
                                            <td>${c.lastName}</td>
                                            <td>${c.email}</td>
                                            <td>${c.totalBookings}</td>
                                            <td><fmt:formatNumber value="${c.totalSpent}" pattern="#,##0" /></td>
                                            <td><fmt:formatDate value="${c.lastBookingDate}" pattern="dd-MM-yyyy HH:mm" /></td>
                                            <td><fmt:formatDate value="${c.registerDate}" pattern="dd-MM-yyyy HH:mm" /></td>
                                            <td>${c.tier}</td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="9" style="text-align:center;">Không có ai.</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>


                    <c:set var="queryParams" value="" />

                    <c:if test="${not empty param.keyword}">
                        <c:set var="queryParams" value="${queryParams}&keyword=${fn:trim(param.keyword)}" />
                    </c:if>
                    <c:if test="${not empty param.bookingRange}">
                        <c:set var="queryParams" value="${queryParams}&bookingRange=${fn:trim(param.bookingRange)}" />
                    </c:if>
                    <c:if test="${not empty param.spentRange}">
                        <c:set var="queryParams" value="${queryParams}&spentRange=${fn:trim(param.spentRange)}" />
                    </c:if>
                    <c:if test="${not empty param.sort}">
                        <c:set var="queryParams" value="${queryParams}&sort=${fn:trim(param.sort)}" />
                    </c:if>
                    <c:if test="${not empty param.order}">
                        <c:set var="queryParams" value="${queryParams}&order=${fn:trim(param.order)}" />
                    </c:if>
                    <c:if test="${not empty param.registerStartDate}">
                        <c:set var="queryParams" value="${queryParams}&registerStartDate=${fn:trim(param.registerStartDate)}" />
                    </c:if>
                    <c:if test="${not empty param.registerEndDate}">
                        <c:set var="queryParams" value="${queryParams}&registerEndDate=${fn:trim(param.registerEndDate)}" />
                    </c:if>
                    <c:if test="${not empty param.bookingStartDate}">
                        <c:set var="queryParams" value="${queryParams}&bookingStartDate=${fn:trim(param.bookingStartDate)}" />
                    </c:if>
                    <c:if test="${not empty param.bookingEndDate}">
                        <c:set var="queryParams" value="${queryParams}&bookingEndDate=${fn:trim(param.bookingEndDate)}" />
                    </c:if>


                    <div class="clearfix">
                        <div class="hint-text">
                            Showing <b>${customerReport.size()}</b> out of <b>${totalCustomer}</b>
                        </div>
                        <ul class="pagination">
                            <c:choose>
                                <c:when test="${currentPage > 1}">
                                    <li class="page-item">
                                        <a class="page-link" href="customerReport?page=${currentPage - 1}${queryParams}">Previous</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
                                    </c:otherwise>
                                </c:choose>

                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="customerReport?page=${i}${queryParams}">${i}</a>
                                </li>
                            </c:forEach>

                            <c:choose>
                                <c:when test="${currentPage < totalPages}">
                                    <li class="page-item">
                                        <a class="page-link" href="customerReport?page=${currentPage + 1}${queryParams}">Next</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item disabled"><a class="page-link" href="#">Next</a></li>
                                    </c:otherwise>
                                </c:choose>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        // dùng cho Register Date
        const registerStart = document.querySelector('input[name="registerStartDate"]');
        const registerEnd = document.querySelector('input[name="registerEndDate"]');

        registerStart.addEventListener('change', () => {
            if (registerEnd) {
                registerEnd.min = registerStart.value;
            }
        });

        registerEnd.addEventListener('change', () => {
            if (registerStart) {
                registerStart.max = registerEnd.value;
            }
        });

        // dùng cho Last Booking
        const bookingStart = document.querySelector('input[name="bookingStartDate"]');
        const bookingEnd = document.querySelector('input[name="bookingEndDate"]');

        bookingStart.addEventListener('change', () => {
            if (bookingEnd) {
                bookingEnd.min = bookingStart.value;
            }
        });

        bookingEnd.addEventListener('change', () => {
            if (bookingStart) {
                bookingStart.max = bookingEnd.value;
            }
        });
    </script>

    <%@ include file="/dashboard-layout/footer.jsp" %>