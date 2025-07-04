/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CleaningHistoryDAO;
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
@WebServlet(name = "FinishCleaningServlet", urlPatterns = {"/finishCleaning"})
public class FinishCleaningServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FinishCleaningServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FinishCleaningServlet at " + request.getContextPath() + "</h1>");
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
            int cleaningId = Integer.parseInt(request.getParameter("cleaningId"));
            String note = request.getParameter("note");

            HttpSession session = request.getSession(false);
            Authentication auth = (session != null) ? (Authentication) session.getAttribute("authLocal") : null;

            if (auth == null) {
                response.sendRedirect("loadtohome#login-modal");
                return;
            }

            User cleaner = auth.getUser();

            CleaningHistoryDAO cleaningDAO = new CleaningHistoryDAO();
            boolean success = cleaningDAO.finishCleaning(roomId, cleaningId, note);

            if (success) {
                cleaningDAO.updateRoomStatus(roomId, "Available");
                session.setAttribute("successMessage", "Đã hoàn tất dọn phòng.");
            } else {
                session.setAttribute("errorMessage", "Không thể hoàn tất. Vui lòng thử lại.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }

        response.sendRedirect("cleaningList");

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
