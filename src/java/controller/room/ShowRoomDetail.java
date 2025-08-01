/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.room;

import com.google.gson.Gson;
import entity.Amenity;
import entity.Room;
import entity.RoomImage;
import entity.RoomType;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import validation.Validation;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ShowRoomDetail", urlPatterns = {"/showroomdetail"})
public class ShowRoomDetail extends HttpServlet {

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
            out.println("<title>Servlet ShowRoomDetail</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ShowRoomDetail at " + request.getContextPath() + "</h1>");
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
        String roomId = request.getParameter("roomId");
        int id = Validation.parseStringToInt(roomId);

        Room room = new dao.RoomDAO().getRoomByRoomID(id);
        Map<String, Object> map = new HashMap<>();
        map.put("room", room);
        map.put("roomdetail", room.getRoomDetail());
        List<RoomImage> tmp = new dao.RoomImageDAO().getListRoomImgByDetailID(room.getRoomDetail().getRoomDetailID());
        if (tmp != null) {
            map.put("roomimg", tmp);
        }
        map.put("roomtype", room.getRoomType());
        map.put("roomstar", new dao.RoomReviewDAO().getAverageRatingByRoomId(id));
        Map<String, List<Amenity>> groupedAmenities = new dao.RoomTypeDAO().getAmenitiesGroupedByCategoryByRoomType(room.getRoomType().getRoomTypeID());
        map.put("roomamenities", groupedAmenities);
        map.put("listService", new dao.RoomTypeDAO().getServicesByRoomTypeId(room.getRoomType().getRoomTypeID()));
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(map));
    }
    

    public static void main(String[] args) {
        int id = 23;

        Room room = new dao.RoomDAO().getRoomByRoomID(id);
        Map<String, Object> map = new HashMap<>();
        map.put("room", room);
        map.put("roomdetail", room.getRoomDetail());
        List<RoomImage> tmp = new dao.RoomImageDAO().getListRoomImgByDetailID(room.getRoomDetail().getRoomDetailID());
        if (tmp != null) {
            map.put("roomimg", tmp);
        }
        map.put("roomtype", room.getRoomType());
        map.put("roomstar", new dao.RoomReviewDAO().getAverageRatingByRoomId(id));
        Map<String, List<Amenity>> groupedAmenities = new dao.RoomTypeDAO().getAmenitiesGroupedByCategoryByRoomType(room.getRoomType().getRoomTypeID());
        map.put("roomamenities", groupedAmenities);
         System.out.println("== ROOM ==");
        System.out.println(room);

        System.out.println("== ROOM DETAIL ==");
        System.out.println(room.getRoomDetail());

        System.out.println("== ROOM IMAGES ==");
        if (tmp != null) {
            for (RoomImage img : tmp) {
                System.out.println(img);
            }
        }

        System.out.println("== ROOM TYPE ==");
        System.out.println(room.getRoomType());

        System.out.println("== ROOM STAR ==");
        System.out.println(map.get("roomstar"));

        System.out.println("== GROUPED AMENITIES ==");
        for (Map.Entry<String, List<Amenity>> entry : groupedAmenities.entrySet()) {
            System.out.println("Category: " + entry.getKey());
            for (Amenity amenity : entry.getValue()) {
                System.out.println(" - " + amenity);
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

}
