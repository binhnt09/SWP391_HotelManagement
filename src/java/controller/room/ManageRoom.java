/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.room;

import dao.RoomDAO;
import entity.Room;
import entity.RoomType;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ManageRoom", urlPatterns = {"/manageroom"})
public class ManageRoom extends HttpServlet {

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
            out.println("<title>Servlet ManageRoom</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageRoom at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet requets
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pageParam = request.getParameter("page");
        String keyword = request.getParameter("keyWord");  
        String sortBy = request.getParameter("sortBy");
        String roomType = request.getParameter("roomType");
        String sort = request.getParameter("sort");
        boolean check = "asc".equalsIgnoreCase(sort);
        String presentDeleted = request.getParameter("presentDeleted");

        Boolean isDeleted = true;
        if ("1".equalsIgnoreCase(presentDeleted)) {
            isDeleted = null;
        } else {
            isDeleted = false;
        }
        
        int typeId = validation.Validation.parseStringToInt(roomType);
        int pageIndex = 1;
        if (pageParam != null) {
            try {
                pageIndex = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                pageIndex = 1;
            }
        }

        int pageSize = 10;
        RoomDAO dao = new RoomDAO();

        int totalRooms = 0;
        List<Room> listRoom = null;

            listRoom = dao.searchRoomsByPage(keyword, typeId, sortBy, check, isDeleted, (pageIndex - 1) * pageSize, pageSize);
            totalRooms = dao.countSearchRooms(keyword, typeId, isDeleted);

        int totalPages = (int) Math.ceil((double) totalRooms / pageSize);
        
        request.setAttribute("listRoom", listRoom);
        request.setAttribute("currentPage", pageIndex);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("keyWord", keyword != null ? keyword : "");
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("roomType", roomType);
        request.setAttribute("sort", sort);
        request.setAttribute("presentDeleted", presentDeleted);
        request.setAttribute("numberRoom", totalRooms);
        request.setAttribute("listRoomType", new dao.RoomTypeDAO().getListRoomType());
        request.getRequestDispatcher("manageroom.jsp").forward(request, response);

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
