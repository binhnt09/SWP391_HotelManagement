/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.voucher;

import dao.VoucherDao;
import entity.Authentication;
import entity.Voucher;
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
@WebServlet(name = "VoucherServlet", urlPatterns = {"/voucher"})
public class VoucherServlet extends HttpServlet {

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
            out.println("<title>Servlet VoucherServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VoucherServlet at " + request.getContextPath() + "</h1>");
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
        Authentication auth = (Authentication) request.getSession().getAttribute("authLocal");

        VoucherDao voucherDAO = new VoucherDao();
        List<Voucher> vouchers;
        if (auth != null) {
            int userId = auth.getUser().getUserId();
            vouchers = voucherDAO.getAvailableVouchersForUser(userId);
        } else {
            vouchers = null;
        }

        request.setAttribute("vouchers", vouchers);
        request.getRequestDispatcher("/views/voucher/voucher.jsp").forward(request, response);
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
        Authentication auth = (Authentication) request.getSession().getAttribute("authLocal");
        int userId = auth.getUser().getUserId();

        String voucherIdStr = request.getParameter("voucherId");
        VoucherDao voucherDAO = new VoucherDao();

        int voucherId = validation.Validation.parseStringToInt(voucherIdStr);
        if (!voucherDAO.hasClaimed(userId, voucherId)) {
            voucherDAO.insert(userId, voucherId);
            request.getSession().setAttribute("success", "Bạn đã nhận voucher thành công!");
        } else {
            request.getSession().setAttribute("info", "Bạn đã nhận voucher này rồi.");
        }
        response.sendRedirect(request.getContextPath() + "/voucher");
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
