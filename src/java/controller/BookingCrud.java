/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookingDao;
import entity.Booking;
import entity.Room;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet(name = "BookingCrud", urlPatterns = {"/bookingcrud"})
public class BookingCrud extends HttpServlet {

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
            out.println("<title>Servlet BookingCrud</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BookingCrud at " + request.getContextPath() + "</h1>");
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
    
    int pageIndex = 0;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String status = request.getParameter("status");
        String roomIdRaw = request.getParameter("roomId");
        String keyword = request.getParameter("keyword");
        String checkinRaw = request.getParameter("checkin");
        
        

        int roomId = validation.Validation.parseStringToInt(roomIdRaw);
        Date checkin = validation.Validation.parseStringToSqlDate(checkinRaw, "yyyy-MM-dd");
        
        int pageSize = 5;

        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            pageIndex = Integer.parseInt(pageParam) - 1; // Vì page bắt đầu từ 1
        }


        int totalRecords = new BookingDao().countBookings(keyword, status,roomId); // bạn cần tạo hàm này
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        List<Booking> list = new BookingDao().getBookings(
                0, // userRoleId
                -1, // currentUserId (nếu không lọc theo user)
                roomId, 
                status,
                "b.BookingID", // sortBy
                keyword, // sortBy
                true, // isAsc
                pageIndex,
                pageSize,
                false // isDeleted
        );
        
        List<Room> listRoomBooked = new ArrayList<>();
        List<Integer> listRoomId = new dao.BookingDetailDAO().getAllRoomIdByBookingDetail();
        for (Integer id : listRoomId) {
            listRoomBooked.add(new dao.RoomDAO().getRoomByRoomID(id));
            System.out.println(id);
        }

        
        request.setAttribute("status", status);
        request.setAttribute("roomId", roomIdRaw);
        request.setAttribute("keyword", keyword);
        request.setAttribute("checkin", checkinRaw);
        request.setAttribute("listRoomBooked", listRoomBooked);
        request.setAttribute("listBooking", list);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", pageIndex + 1);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("bookingmanage.jsp").forward(request, response);

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
        String bookingIdRaw = request.getParameter("bookingID");
        String status = request.getParameter("statusbooking");
        boolean check = new dao.BookingDao().updateStatus(validation.Validation.parseStringToInt(bookingIdRaw), status);
        if(check){
            pageIndex =0;
            doGet(request, response);
        }else{
            System.out.println("asghkfyasgfsagfaushgfuo");
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
