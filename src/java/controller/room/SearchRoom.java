/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.room;

import entity.Room;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;
import validation.Validation;

/**
 *
 * @author Admin
 */
public class SearchRoom extends HttpServlet {

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
            out.println("<title>Servlet SearchRoom</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchRoom at " + request.getContextPath() + "</h1>");
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
        String checkin_raw = request.getParameter("checkin");
        String checkout_raw = request.getParameter("checkout");
        String priceFrom_raw = request.getParameter("pricefrom");
        String priceTo_raw = request.getParameter("priceto");
        String numberPeople_raw = request.getParameter("numberpeople");
        String roomType_raw = request.getParameter("roomType");

        request.setAttribute("checkin", checkin_raw);
        request.setAttribute("checkout" ,checkout_raw);
        request.setAttribute("from" ,priceFrom_raw);
        request.setAttribute("to" ,priceTo_raw);
        request.setAttribute("numberPeople" ,numberPeople_raw);
        request.setAttribute("type" ,roomType_raw);
        
        Date checkin = Validation.parseStringToSqlDate(checkin_raw ,"yyyy-MM-dd");
        Date checkout = Validation.parseStringToSqlDate(checkout_raw , "yyyy-MM-dd");
        double priceTo = Validation.parseStringToDouble(priceTo_raw);
        double priceFrom = Validation.parseStringToDouble(priceFrom_raw);
        
        int numberPeople = Validation.parseStringToInt(numberPeople_raw);
        int roomType = Validation.parseStringToInt(roomType_raw);
        
        List<Room> listRoom = new dao.RoomDAO().getListRoom(checkin, checkout, 
                priceFrom, priceTo, numberPeople, 
                roomType, "", "all", "", 
                false, 0, 0, false , 0);
        request.setAttribute("listRoom", listRoom);
        request.setAttribute("listRoomType", new dao.RoomTypeDAO().getListRoomType());
        request.getRequestDispatcher("rooms.jsp").forward(request, response);
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
