<%-- 
    Document   : voucher
    Created on : Jul 11, 2025, 4:23:09 PM
    Author     : ASUS
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Traveloka - Voucher</title>
        <link rel="icon" href="${pageContext.request.contextPath}/img/core-img/favicon.ico">

        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/voucher.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/myvoucher.css">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    </head>

    <body>
        <!-- Header -->
        <div class="preloader d-flex align-items-center justify-content-center">
            <div class="cssload-container">
                <div class="cssload-loading"><i></i><i></i><i></i><i></i></div>
            </div>
        </div>

        <!-- ##### Header Area Start ##### -->
        <jsp:include page="/common/header.jsp"></jsp:include>
            <!-- ##### Header Area End ##### -->

            <!-- Hero Section -->
            <section class="hero-section">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-12">
                            <h1 class="hero-title text-center">Voucher Palatin</h1>
                        </div>
                    </div>
                </div>
            </section>

            <!-- Hotels Section -->
            <section class="container my-5">
            <c:if test="${sessionScope.authLocal != null}">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2 class="section-title fa fa-gift"> Voucher</h2>
                </div>
                <div>
                    <div class="tour-container">
                        <c:forEach var="v" items="${vouchers}">
                            <div class="tour-card">
                                <div class="tour-content">
                                    <!--<img src="" alt="Hotel Hanoi" class="tour-image">-->
                                    <h3 class="tour-title">${v.getCode()} - Giảm ${v.getDiscountPercentage()}%</h3>
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
                                    <form action="${pageContext.request.contextPath}/voucher" method="post">
                                        <input type="hidden" name="voucherId" value="${v.voucherId}" />
                                        <button type="submit" class="btn-book">Nhận Voucher</button>
                                    </form>
                                    <c:if test="${not empty success}">
                                        <script>
                                            Swal.fire({
                                                icon: 'success',
                                                title: 'Thành công',
                                                text: 'Bạn đã nhận voucher thành công!',
                                                confirmButtonText: 'Đóng'
                                            });
                                        </script>
                                    </c:if>
                                    <c:if test="${not empty info}">
                                        <script>
                                            Swal.fire({
                                                icon: 'error',
                                                title: 'Lỗi',
                                                text: '${info}',
                                                confirmButtonText: 'Đóng'
                                            });
                                        </script>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
            <c:if test="${sessionScope.authLocal == null}">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2 class="section-title">🏨 Login để nhận nhiều voucher hấp dẫn</h2>
                </div>
            </c:if>
        </section>

        <!-- Why Choose Us -->
        <section class="why-choose">
            <div class="container">
                <h2 class="section-title text-center">Tại sao chọn Palatin?</h2>
                <div class="row">
                    <div class="col-lg-4 col-md-6">
                        <div class="feature-item">
                            <i class="fas fa-shield-alt feature-icon"></i>
                            <h3 class="feature-title">Đảm bảo an toàn</h3>
                            <p class="feature-desc">Thanh toán an toàn với nhiều phương thức khác nhau</p>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-6">
                        <div class="feature-item">
                            <i class="fas fa-tags feature-icon"></i>
                            <h3 class="feature-title">Giá tốt nhất</h3>
                            <p class="feature-desc">Cam kết giá tốt nhất với nhiều ưu đãi hấp dẫn</p>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-6">
                        <div class="feature-item">
                            <i class="fas fa-mobile-alt feature-icon"></i>
                            <h3 class="feature-title">Dễ dàng sử dụng</h3>
                            <p class="feature-desc">Giao diện thân thiện, dễ sử dụng</p>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- Footer -->

        <script src="${pageContext.request.contextPath}/js/payment/voucher.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/jquery/jquery-2.2.4.min.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

        <!-- Popper + Bootstrap + Plugin + Active -->
        <script src="${pageContext.request.contextPath}/js/bootstrap/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/plugins/plugins.js"></script>
        <script src="${pageContext.request.contextPath}/js/active.js"></script>
    </body>
</html>
