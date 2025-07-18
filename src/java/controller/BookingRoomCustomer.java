/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookingDao;
import entity.Authentication;
import entity.Booking;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet(name = "BookingRoomCustomer", urlPatterns = {"/bookingroomcustomer"})
public class BookingRoomCustomer extends HttpServlet {

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
            out.println("<title>Servlet BookingRoomCustomer</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BookingRoomCustomer at " + request.getContextPath() + "</h1>");
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
        String roomIdRaw = request.getParameter("roomId");
        String roomDetailId = request.getParameter("bookRoomDetail");
        Authentication auth = (Authentication) request.getSession().getAttribute("authLocal");
        List<Booking> list = new BookingDao().getBookings(
                5, // userRoleId
                auth.getUser().getUserId(), // currentUserId (nếu không lọc theo user)
                -1,
                null,
                "b.BookingID", // sortBy
                null, // sortBy
                true, // isAsc
                0,
                0,
                false // isDeleted
        );
        request.setAttribute("listBooking", list);

        for (Booking b : list) {
            int roomId = new dao.BookingDetailDAO().getBookingDetailByBookingId(b.getBookingId()).getRoom().getRoomID();
            request.setAttribute("rating_" + b.getBookingId(), new dao.RoomReviewDAO().getRatingByRoomId(roomId, b.getUserId()));
        }
        request.setAttribute("listBooking", list);

        if (action != null) {
            switch (action.toLowerCase()) {
                case "editbooking":
                    editBooking(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        request.getRequestDispatcher("/views/profile/historybooking.jsp").forward(request, response);
    }

    public static void main(String[] args) {
        List<Booking> list = new BookingDao().getBookings(
                5, // userRoleId
                52, // currentUserId (nếu không lọc theo user)
                -1,
                null,
                "b.BookingID", // sortBy
                null, // sortBy
                true, // isAsc
                0,
                0,
                false // isDeleted
        );
        for (Booking booking : list) {
            System.out.println(booking.getBookingId());
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

    private void editBooking(HttpServletRequest request, HttpServletResponse response) {
        String bookingIdRaw = request.getParameter("roomId");
        String roomIdRaw = request.getParameter("bookingId");
        String bookRoomDetail = request.getParameter("bookRoomDetail");

        String checkInraw = request.getParameter("bookCheckin");
        String checkOutRaw = request.getParameter("bookCheckout");

        Date checkInDate = validation.Validation.parseStringToSqlDate(checkInraw, "yyyy-MM-dd");
        Date checkOutDate = validation.Validation.parseStringToSqlDate(checkOutRaw, "yyyy-MM-dd");

    }

}
