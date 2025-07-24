/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookingDao;
import dao.CustomerDAO;
import dao.RoomDAO;
import entity.Booking;
import entity.BookingDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author viet7
 */
@WebServlet(name = "QuickCheckInServlet", urlPatterns = {"/quickCheckIn"})
public class QuickCheckInServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet QuickCheckInServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet QuickCheckInServlet at " + request.getContextPath() + "</h1>");
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
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");
        int nights = Integer.parseInt(request.getParameter("nights"));

        // Validate input
        if (firstName == null || firstName.isBlank() || lastName == null || phone == null || nights <= 0) {
            request.getSession().setAttribute("errorMessage", "Check-in thất bại!");
            response.sendRedirect("receptionistPage");
            return;
        }

        BookingDao bookingDAO = new BookingDao();
        RoomDAO roomDAO = new RoomDAO();
        CustomerDAO customerDAO = new CustomerDAO();

        // Tạo booking cho khách lẻ
        int userId = customerDAO.createGuestUser(firstName, lastName, phone);
        Booking booking = new Booking();

        // Convert LocalDate to Date
        LocalDate checkInLocalDate = LocalDate.now();
        LocalDate checkOutLocalDate = checkInLocalDate.plusDays(nights);
        java.sql.Date checkInDate = java.sql.Date.valueOf(checkInLocalDate);
        java.sql.Date checkOutDate = java.sql.Date.valueOf(checkOutLocalDate);
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);

        booking.setUserId(userId);
        BigDecimal price = roomDAO.getRoomPrice(roomId);
        BigDecimal dem = BigDecimal.valueOf(nights);
        booking.setTotalAmount(price.multiply(dem));
        booking.setStatus("Checked-in");

        int bookingId = bookingDAO.insertBookingForGuest(booking);

        if (bookingId > 0) {

            BookingDetails bd = new BookingDetails();
            bd.setBookingId(bookingId);
            bd.setRoomId(roomId);
            bd.setPrice(price);
            bd.setNights(nights);
            bookingDAO.insertBookingDetail(bd);

            roomDAO.updateRoomStatus(roomId, "Occupied");
            request.getSession().setAttribute("successMessage", "Check-in thành công!");
            response.sendRedirect("receptionistPage");
        } else {
            request.getSession().setAttribute("errorMessage", "Check-in thất bại!");
            response.sendRedirect("receptionistPage");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
