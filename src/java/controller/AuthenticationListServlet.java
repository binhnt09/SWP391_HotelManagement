/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AuthenticationDAO;
import entity.Authentication;
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
@WebServlet(name = "AuthenticationListServlet", urlPatterns = {"/authenticationList"})
public class AuthenticationListServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AccountListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AccountListServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Authentication auth = (session != null) ? (Authentication) session.getAttribute("authLocal") : null;

        if (auth == null || auth.getUser().getUserRoleId() > 1) {
            response.sendRedirect("loadtohome#login-modal");
            return;
        }

        int page = 1;
        int recordsPerPage = 10;

        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        String keyword = getParam(request, "keyword");
        String role = getParam(request, "role");
        String status = getParam(request, "status");
        String createdFrom = getParam(request, "createdFrom");
        String createdTo = getParam(request, "createdTo");

        AuthenticationDAO dao = new AuthenticationDAO();
        List<Authentication> authList = dao.searchWithPagination(keyword, role, status, createdFrom, createdTo, page, recordsPerPage);

        int totalAuth = dao.countSearch(keyword, role, status, createdFrom, createdTo);
        int totalPages = (int) Math.ceil(totalAuth * 1.0 / recordsPerPage);

        request.setAttribute("authList", authList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalAuth", totalAuth);

        request.setAttribute("keyword", keyword);
        request.setAttribute("role", role);
        request.setAttribute("status", status);
        request.setAttribute("createdFrom", createdFrom);
        request.setAttribute("createdTo", createdTo);

        request.getRequestDispatcher("authentication-list.jsp").forward(request, response);
    }

    private String getParam(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        return value != null ? value.trim() : "";
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
