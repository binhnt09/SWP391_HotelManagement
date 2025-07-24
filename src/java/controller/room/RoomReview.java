/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.room;

import controller.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.RoomDAO;
import dao.RoomReviewDAO;
import entity.Authentication;
import entity.Room;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import org.json.JSONObject;

/**
 *
 * @author Admin
 */
@WebServlet(name = "RoomReview", urlPatterns = {"/roomreview"})
public class RoomReview extends HttpServlet {

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
            out.println("<title>Servlet RoomReview</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RoomReview at " + request.getContextPath() + "</h1>");
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
        try {
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            Authentication auth = (Authentication) request.getSession().getAttribute("authLocal");

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            if (auth == null || auth.getUser() == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\":\"Chưa đăng nhập\"}");
                return;
            }

            int userId = auth.getUser().getUserId();

            RoomReviewDAO reviewDAO = new RoomReviewDAO();
            entity.RoomReview review = reviewDAO.getReviewByUserAndRoom(userId, roomId);

            Gson gson = new Gson();
            response.getWriter().write(gson.toJson(review != null ? review : new RoomReview())); // tránh null
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"error\":\"Lỗi hệ thống\"}");
            e.printStackTrace();
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

        String roomIdRaw = request.getParameter("roomId");
        String userIdRaw = request.getParameter("userId");
        String ratingRaw = request.getParameter("rating");
        String bookingIdRaw = request.getParameter("bookingId");

        int roomId = validation.Validation.parseStringToInt(roomIdRaw);
        int userId = validation.Validation.parseStringToInt(userIdRaw);
        int rating = validation.Validation.parseStringToInt(ratingRaw);
        int bookingId = validation.Validation.parseStringToInt(bookingIdRaw);

        if (rating < 0) {
            rating = 0;
        }
        boolean check = new dao.RoomReviewDAO().insertRating(roomId, userId, rating);
        response.setContentType("application/json");
        response.getWriter().write(response.toString());
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
