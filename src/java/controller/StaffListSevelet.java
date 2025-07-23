/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.StaffDAO;
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
@WebServlet(name = "StaffListServlet", urlPatterns = {"/staffList"})
public class StaffListSevelet extends HttpServlet {


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


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false); 
        Authentication auth = (session != null) ? (Authentication) session.getAttribute("authLocal") : null;

        if (auth == null || auth.getUser().getUserRoleId() >2) {
            response.sendRedirect("loadtohome#login-modal");
            return;
        }

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
