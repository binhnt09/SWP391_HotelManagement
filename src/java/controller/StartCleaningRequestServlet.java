/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CleaningRequestDAO;
import entity.Authentication;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


/**
 *
 * @author viet7
 */
@WebServlet(name = "StartCleaningRequestServlet", urlPatterns = {"/startCleaningRequest"})
public class StartCleaningRequestServlet extends HttpServlet {

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
            out.println("<title>Servlet StartCleaningRequestServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StartCleaningRequestServlet at " + request.getContextPath() + "</h1>");
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
        try {
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            int requestId = Integer.parseInt(request.getParameter("requestId"));

            HttpSession session = request.getSession();
            Authentication auth = (Authentication) session.getAttribute("authLocal");
            User cleaner = auth.getUser();

            if (cleaner == null || cleaner.getUserId() == 0) {
                response.sendRedirect("loadtohome#login-modal");
                return;
            }

            CleaningRequestDAO cleaningDAO = new CleaningRequestDAO();
            boolean success = cleaningDAO.startCleaning(roomId, cleaner.getUserId(), requestId);

            if (success) { 
                session.setAttribute("successMessage", "Bắt đầu dọn phòng thành công.");
            } else {
                session.setAttribute("errorMessage", "Không thể bắt đầu dọn phòng. Vui lòng thử lại.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Đã có lỗi xảy ra: " + e.getMessage());
        }

        response.sendRedirect("cleaningList");
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
