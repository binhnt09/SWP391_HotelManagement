/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.InvoiceDao;
import entity.Invoice;
import entity.InvoiceServiceDetail;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author ASUS
 */
@WebServlet(name="ViewInvoiceByPayment", urlPatterns={"/viewinvoicebypayment"})
public class ViewInvoiceByPayment extends HttpServlet {
   
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
            out.println("<title>Servlet ViewInvoiceByPayment</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewInvoiceByPayment at " + request.getContextPath () + "</h1>");
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
        String paymentIdParam = request.getParameter("paymentId");
        if (paymentIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing paymentId");
            return;
        }

        int paymentId = Integer.parseInt(paymentIdParam);
        InvoiceDao invoiceDAO = new InvoiceDao();
        Invoice invoice = invoiceDAO.getInvoiceByPaymentId(paymentId);
        
        if (invoice == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invoice not found.");
            return;
        }
        
        List<InvoiceServiceDetail> serviceList = invoiceDAO.getByInvoiceId(invoice.getInvoiceId());
        invoice.setListService(serviceList);

        // Trả về JSON
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getWriter(), invoice);
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
        processRequest(request, response);
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
