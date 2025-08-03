/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.payment;

import dao.PaymentDao;
import entity.Payment;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import validation.Validation;

/**
 *
 * @author ASUS
 */
@WebServlet(name="ManagePayment", urlPatterns={"/managepayment"})
public class ManagePayment extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ManagePayment</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagePayment at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String index_raw = request.getParameter("index");

        String searchPayment = request.getParameter("searchPayment");
        String sortby = request.getParameter("sortby");
        String orderSort = request.getParameter("orderSort");

        PaymentDao paymentDao = new PaymentDao();
        List<Payment> listPayment = new ArrayList<>();
        
        if (index_raw == null || index_raw.isEmpty()) {
            Object indexAttr = request.getSession().getAttribute("index");
            if (indexAttr != null) {
                index_raw = indexAttr.toString();
                request.getSession().removeAttribute("index");
            }
        }
        int index = 1;
        if (index_raw != null) {
            index = Validation.parseStringToInt(index_raw);
        }

//        Set<String> validSortFields = Set.of("Code", "DiscountPercentage", "CreatedAt");
        if (sortby == null || sortby.isEmpty() || sortby.equals("default")) {
            sortby = "paymentId";
        }
        if (orderSort == null || orderSort.isEmpty() || orderSort.equals("default")) {
            orderSort = "asc";
        }

        boolean isDescending = "desc".equals(orderSort);
        int count;

        listPayment = paymentDao.searchOrSortPayment(searchPayment, sortby, isDescending, index);
        count = paymentDao.countSearchResults(searchPayment);
        int endPage = (int) Math.ceil(count / 6.0);

        request.setAttribute("tag", index);
        request.setAttribute("endPage", endPage);
        request.setAttribute("count", count);

        request.setAttribute("sortby", sortby);
        request.setAttribute("orderSort", orderSort);
        request.setAttribute("searchPayment", searchPayment);
        request.setAttribute("searchEncoded", URLEncoder.encode(searchPayment != null ? searchPayment : "", StandardCharsets.UTF_8));

        request.setAttribute("listPayment", listPayment);
        request.getRequestDispatcher("/views/payment/managepayment.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
