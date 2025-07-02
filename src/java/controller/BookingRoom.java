/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Room;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
import validation.Validation;

/**
 *
 * @author Admin
 */
@WebServlet(name = "BookingRoom", urlPatterns = {"/bookingroom"})
public class BookingRoom extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BookingRoom</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BookingRoom at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String roomID_raw = request.getParameter("roomID");
        String checkin_raw = request.getParameter("checkin");
        String checkout_raw = request.getParameter("checkout");
        int roomId = Validation.parseStringToInt(roomID_raw);

        //numbre night
        Date checkin = Validation.parseStringToSqlDate(checkin_raw, "yyyy-MM-dd");
        Date checkout = Validation.parseStringToSqlDate(checkout_raw, "yyyy-MM-dd");
//        Timestamp checkin = Validation.parseStringToSqlTimestamp(checkin_raw, "yyyy-MM-dd HH:mm:ss");
//        Timestamp checkout = Validation.parseStringToSqlTimestamp(checkout_raw, "yyyy-MM-dd HH:mm:ss");
        long diffInMillies = checkout.getTime() - checkin.getTime();
        long diffDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        //khoảng thời gian từ hiện tại đến checkin
        Date currentDateOnly = java.sql.Date.valueOf(LocalDate.now());
        long nowTocheckinMillies = checkin.getTime() - currentDateOnly.getTime();
        long nowTocheckinDays = TimeUnit.DAYS.convert(nowTocheckinMillies, TimeUnit.MILLISECONDS);

        Room room = new dao.RoomDAO().getRoomByRoomID(roomId);
        double totalPrice = diffDays * room.getPrice();

        request.getSession().setAttribute("room", room);
        request.getSession().setAttribute("roomIdBooking", roomId);
//        request.getSession().setAttribute("checkin", checkin_raw);
//        request.getSession().setAttribute("checkout", checkout_raw);
        request.getSession().setAttribute("checkin", checkin);
        request.getSession().setAttribute("checkout", checkout);
        request.getSession().setAttribute("numberNight", diffDays);
        request.setAttribute("nowTocheckin", nowTocheckinDays);
        request.getSession().setAttribute("totalPrice", totalPrice);
        
        request.getRequestDispatcher("booking.jsp").forward(request, response);
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
