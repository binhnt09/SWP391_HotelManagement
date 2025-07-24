/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.RoomDAO;
import dao.RoomTypeDAO;
import entity.Authentication;
import entity.RoomInfo;
import entity.RoomStats;
import entity.RoomType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 *
 * @author viet7
 */
@WebServlet(name = "ReceptionistPageServlet", urlPatterns = {"/receptionistPage"})
public class ReceptionistPageServlet extends HttpServlet {

    RoomDAO roomDAO = new RoomDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ReceptionistPageServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReceptionistPageServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); 
        Authentication auth = (session != null) ? (Authentication) session.getAttribute("authLocal") : null;

        if (auth == null || auth.getUser().getUserRoleId() >3) {
            response.sendRedirect("loadtohome#login-modal");
            return;
        }
        
        RoomStats stats = roomDAO.getRoomStats();
        request.setAttribute("roomStats", stats);
        
        List<RoomType> roomTypes = new RoomTypeDAO().getListRoomType();
        request.setAttribute("roomTypes", roomTypes);
        
        String status = request.getParameter("status");
        String roomType = request.getParameter("roomType");
        String keyword = request.getParameter("keyword");
        
        Map<Integer, List<RoomInfo>> roomsByFloor = roomDAO.getRoomsGroupedByFilter(keyword, status, roomType);
        request.setAttribute("roomsByFloor", roomsByFloor);
        
        request.getRequestDispatcher("roommap.jsp").forward(request, response);
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
