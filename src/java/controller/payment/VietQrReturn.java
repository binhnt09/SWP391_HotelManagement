/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.payment;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import validation.Validation;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "VietQrReturn", urlPatterns = {"/vietqrreturn"})
public class VietQrReturn extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet VietQrReturn</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VietQrReturn at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private static final String TRANS_RESULT = "transResult";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookingIdStr = request.getParameter("bookingId");
        String bankCode = request.getParameter("bankCode");
        String transactionCode = request.getParameter("transactionCode");
        String amountStr = request.getParameter("amount");

        boolean transSuccess = false;

        try {
            int bookingId = Validation.getInt(bookingIdStr, 0, Integer.MAX_VALUE);
            BigDecimal amount = new BigDecimal(amountStr);

            BookingDao bookingDao = new BookingDao();
            bookingDao.updateStatus(bookingId, "PAID");

            Payment payment = new Payment();
            payment.setBookingId(bookingId);
            payment.setAmount(amount);
            payment.setMethod("VietQR");
            payment.setStatus("Paid");
            payment.setTransactionCode(transactionCode);
            payment.setBankCode(bankCode);
            payment.setGatewayResponse("User Confirmed");

            PaymentDao paymentDao = new PaymentDao();
            paymentDao.insertPayment(payment);

            transSuccess = true;
            
            request.setAttribute("bookingId", bookingId);
            
            request.getSession().setAttribute(TRANS_RESULT, transSuccess);
        } catch (Exception e) {
            Logger.getLogger(VietQrReturn.class.getName()).log(Level.SEVERE, null, e);
            request.getSession().setAttribute(TRANS_RESULT, false);
        }
        response.sendRedirect(request.getContextPath() + "/views/payment/paymentResult.jsp");
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
