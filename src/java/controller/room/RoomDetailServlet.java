/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.room;

import controller.*;
import dao.BookingDao;
import dao.RoomDAO;
import dao.ServiceDAO;
import entity.BookingInfo;
import entity.BookingServices;
import entity.RoomInfo;
import entity.Service;
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
@WebServlet(name = "RoomDetailServlet", urlPatterns = {"/roomDetail"})
public class RoomDetailServlet extends HttpServlet {

    RoomDAO roomDAO = new RoomDAO();
    BookingDao bookingDAO = new BookingDao();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RoomDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RoomDetailServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int roomId = Integer.parseInt(request.getParameter("roomId"));

        RoomInfo room = roomDAO.getRoomInfo(roomId);
        BookingInfo currentStay = bookingDAO.getCurrentStay(roomId);
        if (currentStay == null) {
            currentStay = bookingDAO.getClosestReservedBooking(roomId);
        }
        if (currentStay != null) {
            List<BookingServices> bookingServices = new BookingDao().getBookingServicesByBookingId(currentStay.getBookingID());
            List<Service> serviceList = new ServiceDAO().getAllServices();

            request.setAttribute("serviceList", serviceList);
            request.setAttribute("bookingServices", bookingServices);
        }
        List<BookingInfo> futureBookings = bookingDAO.getFutureBookings(roomId);
        
        request.setAttribute("room", room);
        request.setAttribute("currentStay", currentStay);
        request.setAttribute("futureBookings", futureBookings);
        request.getRequestDispatcher("room-detail-fragment.jsp").forward(request, response);
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
