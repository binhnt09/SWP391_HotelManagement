<%-- 
    Document   : profile
    Created on : Jun 12, 2025, 10:57:54 PM
    Author     : ASUS
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>The Palatin - Giao dịch của tôi</title>
        <link rel="icon" href="${pageContext.request.contextPath}/img/core-img/favicon.ico">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

        <style>
            .custom-date-form {
                background-color: #ffffff;
                border-radius: 12px;
                border: 1px solid #dee2e6;
                box-shadow: 0 8px 16px rgba(0, 0, 0, 0.05);
                transition: all 0.3s ease;
            }

            .input-group-text {
                background-color: #f1f3f5;
                border: 1px solid #ced4da;
                border-right: none;
                border-radius: 0.5rem 0 0 0.5rem;
            }

            .form-control {
                border-left: none;
                border: 1px solid #ced4da;
                border-radius: 0 0.5rem 0.5rem 0;
                cursor: pointer;
            }

            .btn-primary {
                border-radius: 0.5rem;
                background-color: #007bff;
                border: none;
            }

            .btn-primary:hover {
                background-color: #0056b3;
            }

            .form-label {
                font-size: 0.9rem;
                color: #6c757d;
            }

            .bi {
                font-size: 1.2rem;
            }
        </style>

    </head>

    <body>
        <!-- header -->
        <jsp:include page="/views/profile/headerprofile.jsp"></jsp:include>

            <div class="container-fluid mt-4">
                <div class="row">
                    <!-- Sidebar -->
                <jsp:include page="/views/profile/sidebarprofile.jsp"></jsp:include>

                    <!-- Main Content -->
                    <div class="col-lg-9 col-md-8">
                        <div class="card main-content-card">

                            <div class="container py-5">
                                <h2 class="mb-4">Lịch sử giao dịch</h2>

                                <!-- Filter Bar -->
                                <div class="card p-3 mb-4">
                                    <div class="d-flex flex-wrap gap-2 align-items-center">
                                        <button class="btn btn-outline-primary filter-btn active" data-range="90days">90 ngày qua</button>
                                        <button class="btn btn-outline-primary filter-btn" data-range="6-2025">Tháng 6 2025</button>
                                        <button class="btn btn-outline-primary filter-btn" data-range="5-2025">Tháng 5 2025</button>
                                        <button class="btn btn-outline-primary" onclick="toggleCustomDate()">Ngày tùy chọn</button>
                                    </div>

                                    <div id="customDateForm" class="custom-date-form d-none mt-4 p-4">
                                        <div class="row gy-3 align-items-end">
                                            <div class="col-md-6">
                                                <label for="fromDate" class="form-label fw-semibold text-secondary">Từ ngày</label>
                                                <div class="input-group shadow-sm">
                                                    <span class="input-group-text bg-white"><i class="bi bi-calendar-event-fill text-primary"></i></span>
                                                    <input type="date" id="fromDate" class="form-control border-start-0" onchange="filterByCustomDate()" />
                                                </div>
                                            </div>

                                            <div class="col-md-6">
                                                <label for="toDate" class="form-label fw-semibold text-secondary">Đến ngày</label>
                                                <div class="input-group shadow-sm">
                                                    <span class="input-group-text bg-white"><i class="bi bi-calendar-check-fill text-primary"></i></span>
                                                    <input type="date" id="toDate" class="form-control border-start-0" onchange="filterByCustomDate()" />
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>

                                <!-- No transaction -->
                                <div id="no-transaction" class="alert alert-light text-center border d-none">
                                    <img src="images/no-transaction.png" alt="No Transaction" style="height: 100px">
                                    <h5 class="mt-3">Không tìm thấy giao dịch</h5>
                                    <p>Không có giao dịch nào trong khoảng thời gian bạn chọn.</p>
                                    <a href="payment-history" class="btn btn-outline-primary">Đặt lại bộ lọc</a>
                                </div>

                                <!-- Table transaction -->
                                <div class="table-responsive">
                                    <table class="table table-bordered text-center" id="paymentTable">
                                        <thead class="table-light">
                                            <tr>
                                                <th>#</th>
                                                <th>Payment ID</th>
                                                <th>Amount</th>
                                                <th>Method</th>
                                                <th>Bank</th>
                                                <th>Status</th>
                                                <th>Date</th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="p" items="${listPayments}" varStatus="i">
                                            <tr data-date="${p.createdAt}">
                                                <td>${i.index + 1}</td>
                                                <td>${p.paymentId}</td>
                                                <td>${p.amount} VND</td>
                                                <td>${p.method}</td>
                                                <td>${p.bankCode}</td>
                                                <td>
                                                    <span class="badge ${p.status == 'Paid' ? 'bg-success' : 'bg-secondary'}">${p.status}</span>
                                                </td>
                                                <td><fmt:formatDate value="${p.createdAt}" pattern="yyyy-MM-dd"/></td>
                                                <td>
                                                    <a href="view-invoice?paymentId=${p.paymentId}" class="btn btn-sm btn-outline-primary">Xem</a>
                                                    <a href="download-invoice?paymentId=${p.paymentId}" class="btn btn-sm btn-outline-success">Tải</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <!-- Pagination -->
                            <nav class="mt-4">
                                <ul class="pagination justify-content-center">
                                    <c:forEach begin="1" end="${totalPages}" var="page">
                                        <li class="page-item ${page == pageIndex ? 'active' : ''}">
                                            <a class="page-link" href="?page=${page}">${page}</a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </nav>
                        </div>


                    </div>
                </div>
            </div>
        </div>

        <!-- Footer -->
        <jsp:include page="/views/profile/footerprofile.jsp"></jsp:include>

            <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
            <!-- jQuery (vì Bootstrap 4 phụ thuộc) -->
            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

            <!-- Popper.js -->
            <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

            <!-- Bootstrap 4 JS -->
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

            <script src="${pageContext.request.contextPath}/js/profile.js"></script>

        <script src="${pageContext.request.contextPath}/js/payment/paymenthistory.js"></script>
        <script src="${pageContext.request.contextPath}/js/authentication.js"></script>

    </body>
</html>

