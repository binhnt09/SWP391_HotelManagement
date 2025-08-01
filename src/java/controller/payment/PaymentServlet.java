/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.payment;

import constant.Config;
import dao.BookingDao;
import dao.PaymentDao;
import dao.VoucherDao;
import entity.Authentication;
import entity.Booking;
import entity.BookingDetails;
import entity.Voucher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import validation.Validation;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "PaymentServlet", urlPatterns = {"/payment"})
public class PaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Authentication auth = (Authentication) request.getSession().getAttribute("authLocal");
        int userId = auth.getUser().getUserId();
        
        VoucherDao voucherDAO = new VoucherDao();

        List<Voucher> vouchers = voucherDAO.getClaimedVouchers(userId);

        request.setAttribute("vouchers", vouchers);
        request.getRequestDispatcher("/views/payment/payment.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getParameter("method");

        if ("Vnpay".equals(method)) {
            paymentWithVnpay(request, response);
        } else if ("vietqr".equals(method)) {
            paymentWithVietQr(request, response);
        }
    }

    public void paymentWithVietQr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Authentication userIdStr = (Authentication) request.getSession().getAttribute("authLocal");

        String bookingIdStr = request.getParameter("bookingId");
        String totalBillStr = request.getParameter("totalbill");
        String nightsStr = request.getParameter("nights");
        String voucherIdStr = request.getParameter("voucherId");

        Date checkin = (Date) request.getSession().getAttribute("checkin");
        Date checkout = (Date) request.getSession().getAttribute("checkout");
        int roomId = (int) request.getSession().getAttribute("roomIdBooking");

        if (totalBillStr == null || bookingIdStr == null) {
            throw new IllegalArgumentException("Số tiền không hợp lệ: rỗng");
        }

        // Làm sạch dữ liệu số tiền
        totalBillStr = totalBillStr.replaceAll("[^\\d.]", "");

        int userId = userIdStr.getUser().getUserId();
        int nights = Validation.getInt(nightsStr, 1, 365);
        int bookingId = Validation.getInt(bookingIdStr, 0, Integer.MAX_VALUE);
        Integer voucherId = (voucherIdStr != null && !voucherIdStr.isEmpty()) ? Integer.valueOf(voucherIdStr) : null;
        BigDecimal amountDouble = Validation.validateBigDecimal(totalBillStr, BigDecimal.ONE, new BigDecimal("999999999"));

        PaymentDao PaymentDao = new PaymentDao();
        BigDecimal discountedTotal = PaymentDao.calculateDiscountedTotal(amountDouble, voucherId);
        
        // Tạo booking
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setVoucherId(voucherId);
        booking.setTotalAmount(discountedTotal);
        booking.setStatus("PENDING");
        booking.setCheckInDate(checkin);
        booking.setCheckOutDate(checkout);

        BookingDao bookingDAO = new BookingDao();
        bookingId = bookingDAO.insertBooking(booking);

        if (bookingId < 1) {
            response.sendRedirect("booking.jsp");
            return;
        }

        // Chi tiết booking
        BookingDetails detail = new BookingDetails();
        detail.setBookingId(bookingId);
        detail.setRoomId(roomId);
        detail.setPrice(amountDouble);
        detail.setNights(nights);
        bookingDAO.insertBookingDetail(detail);

        // Tạo QR code để hiển thị
        String bank = "MB";
        String account = "0328633494";
        String accountName = "NGO THANH BINH";
        String template = "compact";
        String download = "0";

        // Phải để đúng format để webhook tách được
        String description = "BOOK" + bookingId;
        String encodedDescription = URLEncoder.encode(description, StandardCharsets.UTF_8);

        String qrUrl = "https://qr.sepay.vn/img?"
                + "acc=" + account
                + "&bank=" + bank
                + "&amount=" + amountDouble
                + "&des=" + encodedDescription
                + "&template=" + template
                + "&download=" + download;

        // Gửi dữ liệu sang JSP để hiển thị mã QR
        request.getSession().setAttribute("bookingId", bookingId);
        request.getSession().setAttribute("accountName", accountName);
        request.getSession().setAttribute("stk", account);
        request.getSession().setAttribute("amount", amountDouble);
        request.getSession().setAttribute("description", description);
        request.getSession().setAttribute("qrUrl", qrUrl);
        response.sendRedirect(request.getContextPath() + "/views/payment/vietqr.jsp");
    }

    public void paymentWithVnpay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Authentication userIdStr = (Authentication) request.getSession().getAttribute("authLocal");

        String bankCode = request.getParameter("bankCode");
        String bookingIdStr = request.getParameter("bookingId");
        String totalBillStr = request.getParameter("totalbill");
        String nightsStr = request.getParameter("nights");
        String voucherIdStr = request.getParameter("voucherId");

        Date checkin = (Date) request.getSession().getAttribute("checkin");
        Date checkout = (Date) request.getSession().getAttribute("checkout");
        int roomId = (int) request.getSession().getAttribute("roomIdBooking");

        if (totalBillStr == null || bookingIdStr == null) {
            throw new IllegalArgumentException("Số tiền không hợp lệ: rỗng abax");
        }

        totalBillStr = totalBillStr.replaceAll("[^\\d.]", "");

        int userId = userIdStr.getUser().getUserId();
        int nights = Validation.getInt(nightsStr, 1, 365);

        int bookingId = Validation.getInt(bookingIdStr, 0, Integer.MAX_VALUE);
        Integer voucherId = (voucherIdStr != null && !voucherIdStr.isEmpty()) ? Integer.valueOf(voucherIdStr) : null;
        BigDecimal amountDouble = Validation.validateBigDecimal(totalBillStr, BigDecimal.ONE, new BigDecimal("999999999"));

        PaymentDao PaymentDao = new PaymentDao();
        BigDecimal discountedTotal = PaymentDao.calculateDiscountedTotal(amountDouble, voucherId);
        
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setVoucherId(voucherId);
        booking.setTotalAmount(discountedTotal);
        booking.setStatus("PENDING");
        booking.setCheckInDate(checkin);
        booking.setCheckOutDate(checkout);

        BookingDao bookingDAO = new BookingDao();
        bookingId = bookingDAO.insertBooking(booking);
        if (bookingId < 1) {
            response.sendRedirect("booking.jsp");
            return;
        }

        BookingDetails detail = new BookingDetails();
        detail.setBookingId(bookingId);
        detail.setRoomId(roomId);
        detail.setPrice(amountDouble);
        detail.setNights(nights);
        bookingDAO.insertBookingDetail(detail);

        long amount = amountDouble
                .multiply(BigDecimal.valueOf(100))
                .setScale(0, RoundingMode.DOWN) // đảm bảo không thập phân
                .longValue();

        System.out.println("Amount gửi VNPay: " + amount);

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";

        String txnRef = "BOOK" + bookingId + "_" + System.currentTimeMillis();
        String ipAddr = Config.getIpAddress(request);

        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", txnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + ipAddr);
        vnp_Params.put("vnp_OrderType", "other");

        String locale = request.getParameter("language");
        vnp_Params.put("vnp_Locale", (locale != null && !locale.isEmpty()) ? locale : "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", ipAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        response.sendRedirect(paymentUrl);
    }
}