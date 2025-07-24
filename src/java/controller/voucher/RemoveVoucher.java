/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.voucher;

import dao.VoucherDao;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "RemoveVoucher", urlPatterns = {"/removevoucher"})
public class RemoveVoucher extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RemoveVoucher</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RemoveVoucher at " + request.getContextPath() + "</h1>");
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
        VoucherDao dao = new VoucherDao();
        String[] voucherIdRe_Raw = request.getParameterValues("voucherId");

        System.out.println("VoucherId: " + Arrays.toString(voucherIdRe_Raw));
        int voucherId = 0;
        boolean remove = true;
        if (voucherIdRe_Raw != null) {
            for (String id : voucherIdRe_Raw) {
                try {
                    voucherId = Integer.parseInt(id);
                    System.out.println("Deleting voucherId: " + voucherId);
                    remove = dao.updateIsdeletedVoucher(voucherId);
//                    if (!result) remove = false;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid bookingID: " + id);
                    System.out.println(e);
                }
            }
        }
        String index_raw = request.getParameter("index");
        request.getSession().setAttribute("index", index_raw);
        if (remove) {
            request.getSession().setAttribute("success", "Remove Voucher successfully");
        response.sendRedirect(request.getContextPath() + "/vouchermanage");
        } else {
            request.getSession().setAttribute("errorMessageSes", "Remove faled!");
        response.sendRedirect(request.getContextPath() + "/vouchermanage");
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
