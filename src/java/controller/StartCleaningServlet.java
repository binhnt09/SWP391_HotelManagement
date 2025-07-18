/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CleaningHistoryDAO;
import dao.RoomDAO;
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
@WebServlet(name = "StartCleaningServlet", urlPatterns = {"/startCleaning"})
public class StartCleaningServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StartCleaningServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StartCleaningServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int roomId = Integer.parseInt(request.getParameter("roomId"));

            HttpSession session = request.getSession();
            Authentication auth = (Authentication) session.getAttribute("authLocal");
            User cleaner = auth.getUser();

            if (cleaner == null || cleaner.getUserId() == 0) {
                response.sendRedirect("loadtohome#login-modal");
                return;
            }

            CleaningHistoryDAO cleaningDAO = new CleaningHistoryDAO();
            boolean success = cleaningDAO.startCleaning(roomId, cleaner.getUserId());

            if (success) {
                cleaningDAO.updateRoomStatus(roomId, "Cleaning");
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
