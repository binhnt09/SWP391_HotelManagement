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
import java.math.BigDecimal;
import java.util.stream.Collectors;
import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
@WebServlet(name="VietQrSepaycallback", urlPatterns={"/sepay-callback"})
public class VietQrSepaycallback extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<h1>Servlet VietQrSepaycallback at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doPost(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String json = request.getReader().lines().collect(Collectors.joining());
        JSONObject obj = new JSONObject(json);

        String note = obj.getString("note");
        BigDecimal amount = obj.getBigDecimal("amount");
        String transactionCode = obj.getString("transaction_code");
        String bank = obj.getString("sender_bank");

        int bookingId = Integer.parseInt(note.replace("BOOK", ""));

        BookingDao bookingDao = new BookingDao();
        Booking booking = bookingDao.getBookingById(bookingId);
        if (booking != null && booking.getTotalAmount().compareTo(amount) == 0) {
            bookingDao.updateStatus(bookingId, "PAID");

            Payment payment = new Payment();
            payment.setBookingID(bookingId);
            payment.setAmount(amount);
            payment.setMethod("VietQR-SePay");
            payment.setStatus("Paid");
            payment.setTransactionCode(transactionCode);
            payment.setBankCode(bank);
            payment.setGatewayResponse("Webhook Confirmed");

            new PaymentDao().insertPayment(payment);
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
