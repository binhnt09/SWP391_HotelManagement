<%-- 
    Document   : myvoucher
    Created on : Jul 10, 2025, 10:45:22 PM
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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/myvoucher.css">

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
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

                        <div class="content-point">
                            <div class="point-summary">
                                <div class="point-title">Tổng chi tiêu của bạn</div>
                                <div class="point-value">₫${sessionScope.totalPaidAmount}</div>
                            <a href="#" class="btn-info">Thông tin chi tiết</a>
                        </div>

                        <!-- Nút tìm hiểu -->
                        <div class="learn-more-box">
                            <p><strong>Tìm hiểu thêm về lợi ích khách hàng</strong><br>
                                Nhiều quyền lợi hơn khi bạn chi tiêu nhiều hơn!</p>
                            <a href="#" class="btn-learn">Tìm hiểu ngay</a>
                        </div>
                    </div>

                    <div class="card main-content-card">
                        <div class="container py-5">

                            <div class="reward-wrapper">
                                <!-- Tổng số tiền đã thanh toán -->

                                <!-- Tabs: Lịch sử tích điểm -->
                                <div class="tabs">
                                    <ul class="nav nav-tabs" role="tablist">
                                        <li class="nav-item" role="presentation">
                                            <button class="nav-link ${empty openTab || openTab == '#voucher-valid' ? 'active' : ''}"
                                                    id="voucher-valid-tab" data-bs-toggle="tab"
                                                    data-bs-target="#voucher-valid" type="button" role="tab">
                                                Voucher khả dụng
                                            </button>
                                        </li>
                                        <li class="nav-item" role="presentation">
                                            <button class="nav-link ${openTab == '#voucher-expired' ? 'active' : ''}"
                                                    id="voucher-expired-tab" data-bs-toggle="tab"
                                                    data-bs-target="#voucher-expired" type="button" role="tab">
                                                Đã hết hạn
                                            </button>
                                        </li>
                                        <li class="nav-item" role="presentation">
                                            <button class="nav-link ${openTab == '#voucher-used' ? 'active' : ''}"
                                                    id="voucher-used-tab" data-bs-toggle="tab"
                                                    data-bs-target="#voucher-used" type="button" role="tab">
                                                Đã sử dụng
                                            </button>
                                        </li>
                                    </ul>
                                </div>

                                <!-- Danh sách voucher -->
                                <div class="tab-content p-4">
                                    <div class="voucher-container tab-pane fade ${empty openTab || openTab == '#voucher-valid' ? 'show active' : ''}" id="voucher-valid" role="tabpanel">
                                        <c:forEach var="v" items="${vouchers}">
                                            <div class="voucher-card">
                                                <div class="voucher-title">${v.getCode()} - Giảm ${v.getDiscountPercentage()}%</div>
                                                <div class="voucher-date">
                                                    Áp dụng:
                                                    <c:choose>
                                                        <c:when test="${v.validFrom != null}">
                                                            <fmt:formatDate value="${v.validFrom}" pattern="dd-MM-yyyy" />
                                                        </c:when>
                                                        <c:otherwise>Không giới hạn</c:otherwise>
                                                    </c:choose>
                                                    →
                                                    <c:choose>
                                                        <c:when test="${v.validTo != null}">
                                                            <fmt:formatDate value="${v.validTo}" pattern="dd-MM-yyyy" />
                                                        </c:when>
                                                        <c:otherwise>Không giới hạn</c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="voucher-footer">
                                                    <button class="apply-btn">
                                                        <a href="${pageContext.request.contextPath}/searchroom" style="text-decoration: none">Sử dụng ngay</a>
                                                    </button>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>


                                    <!-- Danh sách voucher đã sử dụng -->
                                    <div class="voucher-container tab-pane fade ${openTab == '#voucher-expired' ? 'show active' : ''}" id="voucher-expired" role="tabpanel">
                                        <c:forEach var="expired" items="${vouchersExpired}">
                                            <div class="voucher-card">
                                                <div class="voucher-title">${expired.getCode()} - Giảm ${expired.getDiscountPercentage()}%</div>
                                                <div class="voucher-date">
                                                    Áp dụng:
                                                    <c:choose>
                                                        <c:when test="${expired.validFrom != null}">
                                                            <fmt:formatDate value="${expired.validFrom}" pattern="dd-MM-yyyy" />
                                                        </c:when>
                                                        <c:otherwise>Không giới hạn</c:otherwise>
                                                    </c:choose>
                                                    →
                                                    <c:choose>
                                                        <c:when test="${expired.validTo != null}">
                                                            <fmt:formatDate value="${expired.validTo}" pattern="dd-MM-yyyy" />
                                                        </c:when>
                                                        <c:otherwise>Không giới hạn</c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="voucher-footer">
                                                    <button class="btn btn-danger" >Đã hết hạn</button>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                        
                                        
                                    <!-- Danh sách voucher đã sử dụng -->
                                    <div class="voucher-container tab-pane fade ${openTab == '#voucher-used' ? 'show active' : ''}" id="voucher-used" role="tabpanel">
                                        <c:forEach var="used" items="${vouchersIused}">
                                            <div class="voucher-card">
                                                <div class="voucher-title">${used.getCode()} - Giảm ${used.getDiscountPercentage()}%</div>
                                                <div class="voucher-date">
                                                    Áp dụng:
                                                    <c:choose>
                                                        <c:when test="${used.validFrom != null}">
                                                            <fmt:formatDate value="${used.validFrom}" pattern="dd-MM-yyyy" />
                                                        </c:when>
                                                        <c:otherwise>Không giới hạn</c:otherwise>
                                                    </c:choose>
                                                    →
                                                    <c:choose>
                                                        <c:when test="${used.validTo != null}">
                                                            <fmt:formatDate value="${used.validTo}" pattern="dd-MM-yyyy" />
                                                        </c:when>
                                                        <c:otherwise>Không giới hạn</c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="voucher-footer">
                                                    <button class="btn btn-secondary" >Đã sử dụng</button>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Footer -->
        <jsp:include page="/views/profile/footerprofile.jsp"></jsp:include>

        <c:if test="${not empty openTab}">
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    const el = document.querySelector(`button[data-bs-target='${openTab}']`);
                    if (el)
                        new bootstrap.Tab(el).show();
                });
            </script>
        </c:if>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
        <!-- jQuery (vì Bootstrap 4 phụ thuộc) -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

        <!-- Popper.js -->
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

        <!-- Bootstrap 4 JS -->
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

        <script src="${pageContext.request.contextPath}/js/profile.js"></script>
    </body>
</html>


