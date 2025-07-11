/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookingDao;
import dao.RoomDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

/**
 *
 * @author viet7
 */
@WebServlet(name = "CheckInServlet", urlPatterns = {"/checkIn"})
public class CheckInServlet extends HttpServlet {

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
            out.println("<title>Servlet CheckInServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckInServlet at " + request.getContextPath() + "</h1>");
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

        BookingDao bookingDAO = new BookingDao();
        RoomDAO roomDAO = new RoomDAO();

        int roomId = Integer.parseInt(request.getParameter("roomId"));
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        
        if (LocalDate.now().isBefore(bookingDAO.getBookingById(bookingId).getCheckInDate().toLocalDate())) {
            request.getSession().setAttribute("errorMessage", "không được nhận phòng trước ngày Check-in!");
            response.sendRedirect("receptionistPage");
            return;
        }

        boolean successBooking = bookingDAO.updateStatus(bookingId, "Checked-in");
        boolean successRoom = roomDAO.updateRoomStatus(roomId, "Occupied");

        if (successBooking && successRoom) {
            bookingDAO.updateActualCheckin(bookingId);
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
