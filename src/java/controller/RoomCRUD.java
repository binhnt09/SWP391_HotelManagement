/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Room;
import entity.RoomDetail;
import entity.RoomType;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import validation.Validation;

/**
 *
 * @author Admin
 */
@WebServlet(name = "RoomCRUD", urlPatterns = {"/roomcrud"})
public class RoomCRUD extends HttpServlet {

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
            out.println("<title>Servlet RoomCRUD</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RoomCRUD at " + request.getContextPath() + "</h1>");
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
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "edit":
                    editRoom(request, response);
                    break;
                case "delete":
                    deleteRoom(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
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

    private void editRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String roomIdRaw = request.getParameter("roomID");
        String roomDetailRaw = request.getParameter("roomDetail");
        String roomNumber = request.getParameter("roomNumber");
        String roomTypeIdRaw = request.getParameter("roomTypeID");
        String status = request.getParameter("status");
        String priceRawRaw = request.getParameter("price");
        String bedType = request.getParameter("bedType");
        String description = request.getParameter("description");
        String maxGuestRaw = request.getParameter("maxGuest");
        String areaRaw = request.getParameter("area");

        RoomDetail roomD = new RoomDetail(Validation.parseStringToInt(roomDetailRaw),
                bedType,
                Validation.parseStringToDouble(areaRaw),
                Validation.parseStringToInt(maxGuestRaw),
                description);
        RoomType roomT = new dao.RoomTypeDAO().getRoomTypeById(Validation.parseStringToInt(roomTypeIdRaw));
        Room room = new Room(Validation.parseStringToInt(roomIdRaw),
                roomNumber, roomD,
                roomT,
                status, Validation.parseStringToDouble(priceRawRaw));
        boolean checkUpdate = new dao.RoomDAO().updateRoom(room);
        request.setAttribute("listRoom", new dao.RoomDAO().getAllRoom());
        request.setAttribute("numberRoom", new dao.RoomDAO().getAllRoom().size());
        //CẦN SỬA KHI KHÔNG UPDATE ĐƯỢC
        if (checkUpdate) {
            request.getRequestDispatcher("manageroom.jsp").forward(request, response);
        }
    }

    private void updateRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy dữ liệu từ form (roomNumber, price,...)
        // Gọi DAO update
        // Redirect hoặc forward lại trang quản lý phòng
    }

    private void deleteRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xử lý xóa phòng theo ID
    }

    private void addRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xử lý thêm mới phòng
    }

}
