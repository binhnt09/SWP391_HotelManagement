/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.StaffDAO;
import entity.User;
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
@WebServlet(name = "StaffUpdateServlet", urlPatterns = {"/staffUpdate"})
public class StaffUpdateServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StaffUpdateServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffUpdateServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        int roleID = Integer.parseInt(request.getParameter("roleID"));
        

        StaffDAO dao = new StaffDAO();
        User staff = new User();

        staff.setFirstName(firstName);
        staff.setLastName(lastName);
        staff.setEmail(email);
        staff.setPhone(phone);
        staff.setAddress(address);
        staff.setUserRoleId(roleID);

        boolean success = false;
        String action = request.getParameter("action");

        try {
            if ("add".equalsIgnoreCase(action)) {
                success = dao.insertUser(staff);
                if (success) {
                    request.getSession().setAttribute("successMessage", "Thêm khách hàng thành công!");
                }
            } else if ("update".equalsIgnoreCase(action)) {
                int userId = Integer.parseInt(request.getParameter("userId"));
                staff.setUserId(userId);
                success = dao.updateUser(staff);
                if (success) {
                    request.getSession().setAttribute("successMessage", "Cập nhật khách hàng thành công!");
                }
            }

            if (!success) {
                request.getSession().setAttribute("errorMessage", "Có lỗi xảy ra, vui lòng thử lại.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Xảy ra lỗi trong quá trình xử lý.");
        }

        // Redirect về lại list
        response.sendRedirect("staffList");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
