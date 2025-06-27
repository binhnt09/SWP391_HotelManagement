/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ServiceDAO;
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
@WebServlet(name = "ServiceListServlet", urlPatterns = {"/serviceList"})
public class ServiceListServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServiceListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServiceListServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int recordsPerPage = 10;

        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        int offset = (page - 1) * recordsPerPage;

        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }
        String category = request.getParameter("category");
        String statusParam = request.getParameter("status");
        Boolean status = null;
        if (statusParam != null && !statusParam.isEmpty()) {
            status = "true".equals(statusParam);
        }

        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");

        ServiceDAO dao = new ServiceDAO();
        List<Service> serviceList = dao.searchServicesByFilter(keyword, category, status, sortField, sortOrder, offset, recordsPerPage);

        int totalRecords = dao.countServicesByFilter(keyword, category, status);
        int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

        request.setAttribute("serviceList", serviceList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalService", totalRecords);
        request.setAttribute("keyword", keyword);
        request.setAttribute("category", category);
        request.setAttribute("status", statusParam);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortOrder", sortOrder);

        request.getRequestDispatcher("service-manager.jsp").forward(request, response);
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
