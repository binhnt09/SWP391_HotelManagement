/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.payment;

import dao.InvoiceDao;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "DownloadInvoice", urlPatterns = {"/downloadinvoice"})
public class DownloadInvoice extends HttpServlet {

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
            out.println("<title>Servlet DownloadInvoice</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DownloadInvoice at " + request.getContextPath() + "</h1>");
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
    private static final int BUFFER_SIZE = 4096; // 4KB

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paymentIdStr = request.getParameter("paymentId");

        if (paymentIdStr == null || paymentIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing paymentId");
            return;
        }

        int paymentId = validation.Validation.parseStringToInt(paymentIdStr);

        Integer invoiceId = new InvoiceDao().getInvoiceIdByPaymentId(paymentId);
        if (invoiceId == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy hóa đơn cho paymentId = " + paymentId);
            return;
        }

        // Đường dẫn đến thư mục chứa hóa đơn
        String invoiceDir = getServletContext().getRealPath("/invoices");
        File invoiceFile = new File(invoiceDir, invoiceId + ".pdf");

        if (!invoiceFile.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invoice not found.");
            return;
        }

        String downloadFileName = "PalatinInvoice_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "_" + invoiceFile.getName();

        // Thiết lập header để tải về file
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFileName + "\"");
        response.setContentLength((int) invoiceFile.length());

        // Ghi nội dung file ra response output stream
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(invoiceFile)); BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream())) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush(); // đảm bảo dữ liệu được ghi hết
        }
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
