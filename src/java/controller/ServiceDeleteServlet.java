/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ServiceDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author viet7
 */
@WebServlet(name = "ServiceDeleteServlet", urlPatterns = {"/serviceDelete"})
public class ServiceDeleteServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServiceDeleteServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServiceDeleteServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int deletedBy = 2;
//        int deletedBy = ((User) request.getSession().getAttribute("loggedInUser")).getUserId();
        ServiceDAO dao = new ServiceDAO();
        if (!dao.isServiceInUse(id)) {
            if (dao.deleteService(id, deletedBy)) {
                request.getSession().setAttribute("successMessage", "Xóa dịch vụ thành công!");
            } else {
                request.getSession().setAttribute("errorMessage", "Có lỗi xảy ra, vui lòng thử lại.");
            }
        } else {
            request.getSession().setAttribute("errorMessage", "Không thể xóa, dịch vụ này đang được sử dụng.");
        }
        response.sendRedirect("serviceList");
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
