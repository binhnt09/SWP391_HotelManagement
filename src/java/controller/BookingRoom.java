/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dao.ServiceDAO;
import entity.Room;
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
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        request.getSession().setAttribute("checkin", checkin);
        request.getSession().setAttribute("checkout", checkout);
        request.getSession().setAttribute("numberNight", diffDays);
        request.setAttribute("nowTocheckin", nowTocheckinDays);
        request.getSession().setAttribute("totalPrice", totalPrice);
        request.setAttribute("starRoom", new dao.RoomReviewDAO().getAverageRatingByRoomId(roomId));
        request.setAttribute("numberPeopleVote", new dao.RoomReviewDAO().countNumberPeopleRateStar(roomId));
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
        String action = request.getParameter("action");
        if (action != null && action.equalsIgnoreCase("addService")) {
            addService(request, response);
        } else if (action != null && action.equalsIgnoreCase("updateTotalPrice")) {
            updateTotalPrice(request, response);
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

    private void addService(HttpServletRequest request, HttpServletResponse response) {
        List<Service> services = new ServiceDAO().getListService();
        Gson gson = new Gson();
        String json = gson.toJson(services);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(json);
        } catch (IOException ex) {
            Logger.getLogger(BookingRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateTotalPrice(HttpServletRequest request, HttpServletResponse response) {

        String totalStr = request.getParameter("newTotal");

        double newTotal = Validation.parseStringToDouble(totalStr);

        request.getSession().setAttribute("totalPrice", newTotal);

    }
}
