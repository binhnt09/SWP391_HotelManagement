/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.room;

import controller.*;
import dao.RoomDAO;
import entity.Room;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author viet7
 */
@WebServlet(name = "RoomListServlet", urlPatterns = {"/roomList"})
public class RoomListServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RoomListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RoomListServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = 1;
        int pageSize = 5;

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        int totalRoom;
        List<Room> roomList;
        RoomDAO dao = new RoomDAO();
        String keyword = request.getParameter("keyword");
        if (keyword != null && !keyword.trim().isEmpty()) {
            roomList = dao.searchRoomsByPage(keyword, (page - 1) * pageSize, pageSize);
            totalRoom = dao.countSearchRooms(keyword);
        } else {
            roomList = dao.getRoomsByPage((page - 1) * pageSize, pageSize);
            totalRoom = dao.countAllRooms();
        }
        int totalPages = (int) Math.ceil((double) totalRoom / pageSize);

        request.setAttribute("roomList", new dao.RoomDAO().getAllRoom());
        request.setAttribute("totalRoom", totalRoom);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("keyword", keyword);

        request.getRequestDispatcher("room-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
