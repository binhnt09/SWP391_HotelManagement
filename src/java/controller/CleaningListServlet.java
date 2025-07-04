/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CleaningHistoryDAO;
import entity.Authentication;
import entity.CleaningHistory;
import entity.Room;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author viet7
 */
@WebServlet(name = "CleaningListServlet", urlPatterns = {"/cleaningList"})
public class CleaningListServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CleaningListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CleaningListServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); 
        Authentication auth = (session != null) ? (Authentication) session.getAttribute("authLocal") : null;

        if (auth == null) {
            response.sendRedirect("loadtohome#login-modal");
            return;
        }

        User cleaner = auth.getUser();

        try {
            CleaningHistoryDAO dao = new CleaningHistoryDAO();

            List<Room> pendingRooms = dao.getListRoomForCleaner();
            List<CleaningHistory> inProgressTasks = dao.getCleaningHistoryByCleanerInProgress(cleaner.getUserId());

            request.setAttribute("pendingRooms", pendingRooms);
            request.setAttribute("inProgressTasks", inProgressTasks);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi tải danh sách phòng: " + e.getMessage());
        }

        request.getRequestDispatcher("cleaning-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
