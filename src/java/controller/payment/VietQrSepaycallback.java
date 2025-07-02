/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.payment;

import dao.BookingDao;
import dao.PaymentDao;
import entity.Booking;
import entity.Payment;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "VietQrSepaycallback", urlPatterns = {"/sepay-callback"})
public class VietQrSepaycallback extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet VietQrSepaycallback</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VietQrSepaycallback at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        doPost(request, response);
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
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String contentType = request.getContentType();
        System.out.println("Webhook Content-Type: " + contentType);

        String content = null;
        String amountStr = null;
        String transactionCode = null;
        String bankCode = null;

        try {
            if (contentType != null && contentType.contains("application/json")) {
                String rawJson = request.getReader().lines().collect(Collectors.joining());
                System.out.println("Webhook JSON Body: " + rawJson);

                JSONObject json = new JSONObject(rawJson);
                content = json.optString("content");
                amountStr = json.optString("transferAmount");
                if (json.isNull("code") || json.optString("code").isEmpty()) {
                    if (content != null && content.contains("-")) {
                        bankCode = content.split("-")[0];
                    }
                } else {
                    bankCode = json.optString("code");
                }
                transactionCode = json.optString("referenceCode");
            } else {
                System.out.println("Webhook Form Data: content=" + request.getParameter("content") + ", amount=" + request.getParameter("transferAmount"));
                content = request.getParameter("content");
                amountStr = request.getParameter("transferAmount");
            }
        } catch (IOException | JSONException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            out.write("{\"success\": false, \"error\": \"Error parsing webhook JSON: " + e.getMessage() + "\"}");
            return;
        }

        if (content == null || amountStr == null || content.isEmpty() || amountStr.isEmpty()) {
            out.write("{\"success\": false, \"error\": \"Missing content or amount\"}");
            return;
        }

        // Bóc mã booking ID từ content
        int bookingId = -1;
        Matcher matcher = Pattern.compile("BOOK(\\d+)").matcher(content);
        if (matcher.find()) {
            bookingId = Integer.parseInt(matcher.group(1));
        }

        if (bookingId == -1) {
            out.write("{\"success\": false, \"message\": \"Invalid booking ID format\"}");
            return;
        }

        // So khớp với booking
        BigDecimal amount = new BigDecimal(amountStr);
        BookingDao dao = new BookingDao();
        Booking booking = dao.getBookingById(bookingId);
        System.out.println("✔️ content = " + content);
        System.out.println("✔️ amount = " + amountStr);
        System.out.println("✔️ bookingId = " + bookingId);

        if (booking != null) {
            dao.updateStatus(bookingId, "PAID");

            // ✅ Ghi log hoặc insert vào bảng Payment nếu cần
            Payment payment = new Payment();
            payment.setBookingID(bookingId);
            payment.setAmount(amount);
            payment.setMethod("VietQR");
            payment.setStatus("Paid");
            payment.setTransactionCode(transactionCode);
            payment.setBankCode(bankCode);
            payment.setGatewayResponse("Sepay Confirmed");

            PaymentDao paymentDao = new PaymentDao();
            paymentDao.insertPayment(payment);
            
            Booking updateBooking = dao.getBookingById(bookingId);
            if(updateBooking != null && "PAID".equalsIgnoreCase(updateBooking.getStatus())){
                HttpSession session = request.getSession();
                session.removeAttribute("bookingId");
                session.removeAttribute("accountName");
                session.removeAttribute("stk");
                session.removeAttribute("amount");
                session.removeAttribute("description");
                session.removeAttribute("qrUrl");
            }
            System.out.println("✅ bookingId after clear: " + request.getSession().getAttribute("bookingId")); // Should be null
            out.write("{\"success\": true, \"message\": \"Booking updated to PAID\"}");
        } else {
            out.write("{\"success\": false, \"message\": \"Booking not matched or already PAID\"}");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
