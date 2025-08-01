/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookingDao;
import dao.ServiceDAO;
import entity.Authentication;
import entity.Booking;
import entity.BookingServices;
import entity.Service;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        List<Service> allServices = new ServiceDAO().getAllServices();
        request.setAttribute("serviceList", allServices);
        
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

        for (Booking booking : list) {
            List<BookingServices> services = new BookingDao().getBookingServicesByBookingId(booking.getBookingId());
            booking.setBookingServices(services);
        }
        request.setAttribute("listBooking", list);
        if (action != null) {
            switch (action.toLowerCase()) {
                case "checkcancel":
                    checkCancel(request, response);
                    break;
                case "cancelbooking":
                    cancelBooking(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        request.getRequestDispatcher("/views/profile/historybooking.jsp").forward(request, response);
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

    private void checkCancel(HttpServletRequest request, HttpServletResponse response) {
        int bookingId = validation.Validation.parseStringToInt(request.getParameter("bookingId"));
        response.setContentType("application/json");

        try (PrintWriter out = response.getWriter()) {
            LocalDate checkIn = new dao.BookingDao().getCheckInDateByBookingId(bookingId);
            if (checkIn == null) {
                out.write("{\"error\":\"Check-in date not found\"}");
                return;
            }
            long days = ChronoUnit.DAYS.between(LocalDate.now(), checkIn);
            boolean allowCancel = days >= 7;
            Authentication auth = (Authentication) request.getSession().getAttribute("authLocal");
            if(!allowCancel){
                new dao.NotificationDao().addNotifications(auth.getUser().getUserId(), "Thời gian từ hiện tại đến ngày checkin dưới 7 ngày!!!", "Thông báo");
            }
            out.write("{\"allowCancel\":" + allowCancel + "}");
        } catch (Exception ex) {
            Logger.getLogger(BookingRoomCustomer.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    private void cancelBooking(HttpServletRequest request, HttpServletResponse response) {
        String bookingIdStr = request.getParameter("bookingId");
        int bookingId = validation.Validation.parseStringToInt(bookingIdStr);
        BookingDao dao = new BookingDao();

        boolean success = dao.cancelBooking(bookingId);

        response.setContentType("application/json");
        Authentication auth = (Authentication) request.getSession().getAttribute("authLocal");
        try {
            new dao.NotificationDao().addNotifications(auth.getUser().getUserId(), "Bạn đã hủy phòng thành công", "Sucsess");
            response.getWriter().write("{\"message\": \"Booking cancelled successfully\"}");
        } catch (IOException ex) {
            new dao.NotificationDao().addNotifications(auth.getUser().getUserId(), "Không thể hủy khi thời hạn dưới 7 ngày", "Error");
            Logger.getLogger(BookingRoomCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
