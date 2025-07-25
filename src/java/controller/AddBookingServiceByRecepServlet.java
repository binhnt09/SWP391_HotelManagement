/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BookingDao;
import dao.ServiceDAO;
import entity.Service;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;



/**
 *
 * @author viet7
 */
@WebServlet(name = "AddBookingServiceByRecepServlet", urlPatterns = {"/addBookingServiceByRecep"})
public class AddBookingServiceByRecepServlet extends HttpServlet {

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
            out.println("<title>Servlet AddBookingServiceByRecepServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddBookingServiceByRecepServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        ServiceDAO serviceDAO = new ServiceDAO();
        Service service = serviceDAO.getServiceById(serviceId);
        BigDecimal priceAtUse = service.getPrice();

        BookingDao bookingDAO = new BookingDao();
        if (bookingDAO.getStatusById(bookingId).equalsIgnoreCase("Confirmed") || bookingDAO.getStatusById(bookingId).equalsIgnoreCase("Checked-in")){
            bookingDAO.insertBookingService(bookingId, serviceId, quantity, priceAtUse);
            request.getSession().setAttribute("successMessage", "Thêm dịch vụ thành công!");
            response.sendRedirect("receptionistPage");
        }
        else{
            request.getSession().setAttribute("errorMessage", "Chỉ phòng đã xác nhận (đã thanh toán) mới được thêm dịch vụ.");
            response.sendRedirect("receptionistPage");
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

}
