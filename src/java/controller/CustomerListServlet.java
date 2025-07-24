/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CustomerDAO;
import entity.Authentication;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author viet7
 */

@WebServlet(name = "CustomerListServlet", urlPatterns = {"/customerList"})
public class CustomerListServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CustomerListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerListServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); 
        Authentication auth = (session != null) ? (Authentication) session.getAttribute("authLocal") : null;

        if (auth == null || auth.getUser().getUserRoleId() >3) {
            response.sendRedirect("loadtohome#login-modal");
            return;
        }
        
        int page = 1;
        int recordsPerPage = 10;
        String keyword = request.getParameter("keyword");

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        int totalCustomer;
        List<User> customers;
        CustomerDAO dao = new CustomerDAO();
        if (keyword != null && !keyword.trim().isEmpty()) {
            customers = dao.searchCustomersByPage(keyword, (page - 1) * recordsPerPage, recordsPerPage);
            totalCustomer = dao.getTotalCustomerSearchCount(keyword);
        } else {
            customers = dao.getCustomersByPage((page - 1) * recordsPerPage, recordsPerPage);
            totalCustomer = dao.getTotalCustomerCount();
        }
        int totalPages = (int) Math.ceil(totalCustomer * 1.0 / recordsPerPage);

        request.setAttribute("customers", customers);
        request.setAttribute("totalCustomer", totalCustomer);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("customer-list.jsp").forward(request, response);
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
