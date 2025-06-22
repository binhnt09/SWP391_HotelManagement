/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.StaffDAO;
import entity.User;
import jakarta.servlet.ServletException;
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
public class StaffListSevelet extends HttpServlet {

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
            out.println("<title>Servlet StaffListSevelet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffListSevelet at " + request.getContextPath() + "</h1>");
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
        int page = 1;
        int recordsPerPage = 10;

        String keyword = request.getParameter("keyword");

        String roleStr = request.getParameter("role");
        Integer role = null;

        try {
            role = Integer.parseInt(roleStr);
        } catch (NumberFormatException e) {
            role = null;  // Nếu parse lỗi thì coi như null
        }

        if (role == null || (role != 3 && role != 4)) {
            role = 3;
        }

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        StaffDAO dao = new StaffDAO();
        List<User> staffList;
        int totalStaff;

        if (keyword != null && !keyword.trim().isEmpty()) {
            staffList = dao.searchStaffByPage(keyword, role, (page - 1) * recordsPerPage, recordsPerPage);
            totalStaff = dao.countSearchStaff(keyword, role);
        } else {
            staffList = dao.getStaffByRoleWithPaging(role, (page - 1) * recordsPerPage, recordsPerPage);
            totalStaff = dao.countTotalStaffByRole(role);
        }

        int totalPages = (int) Math.ceil(totalStaff * 1.0 / recordsPerPage);

        request.setAttribute("staffList", staffList);
        request.setAttribute("totalStaff", totalStaff);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("role", role);
        request.setAttribute("keyword", keyword);

        request.getRequestDispatcher("staff-list.jsp").forward(request, response);
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
