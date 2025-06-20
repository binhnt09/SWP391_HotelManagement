/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.payment;

import constant.Config;
import dao.BookingDao;
import dao.PaymentDao;
import entity.Payment;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "VnpayReturn", urlPatterns = {"/vnpayReturn"})
public class VnpayReturn extends HttpServlet {

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
            Map<String, String> fields = new HashMap<>();
            for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
                String paramName = params.nextElement();
                String paramValue = request.getParameter(paramName);
                if (paramValue != null && !paramValue.isEmpty()) {
                    fields.put(paramName, paramValue);
                }
            }

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            fields.remove("vnp_SecureHashType");
            fields.remove("vnp_SecureHash");

            String signValue = Config.hashAllFields(fields);
            boolean isValidSignature = signValue.equals(vnp_SecureHash);

            if (isValidSignature) {
                String txnRef = request.getParameter("vnp_TxnRef");
                int bookingId = Integer.parseInt(txnRef.split("_")[0].replace("BOOK", ""));

                String paymentCode = request.getParameter("vnp_TransactionNo");
                String bankCode = request.getParameter("vnp_BankCode");
                BigDecimal amount = new BigDecimal(request.getParameter("vnp_Amount")).divide(BigDecimal.valueOf(100));

                boolean transSuccess = false;
                if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                    transSuccess = true;

                    // Update booking status
                    BookingDao bookingDao = new BookingDao();
                    bookingDao.updateStatus(bookingId, "PAID");

                    // Insert payment
                    Payment payment = new Payment();
                    payment.setBookingID(bookingId);
                    payment.setAmount(amount);
                    payment.setMethod("VNPay");
                    payment.setStatus("Paid");
                    payment.setTransactionCode(paymentCode);
                    payment.setBankCode(bankCode);
                    payment.setGatewayResponse("Success");

                    PaymentDao paymentDao = new PaymentDao();
                    paymentDao.insertPayment(payment);
                }

                request.setAttribute("transResult", transSuccess);
                request.getRequestDispatcher("/payment/paymentResult.jsp").forward(request, response);
            } else {
                System.out.println("Giao dịch không hợp lệ (invalid signature)");
                request.setAttribute("transResult", false);
                request.getRequestDispatcher("/payment/paymentResult.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("transResult", false);
            request.getRequestDispatcher("/payment/paymentResult.jsp").forward(request, response);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
