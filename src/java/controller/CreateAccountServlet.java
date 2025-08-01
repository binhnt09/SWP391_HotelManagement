/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AuthenticationDAO;
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
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author viet7
 */
@WebServlet(name = "CreateAccountServlet", urlPatterns = {"/createAccount"})
public class CreateAccountServlet extends HttpServlet {

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
            out.println("<title>Servlet CreateAccountServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateAccountServlet at " + request.getContextPath() + "</h1>");
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
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        Authentication auth = (session != null) ? (Authentication) session.getAttribute("authLocal") : null;

        if (auth == null) {
            response.sendRedirect("loadtohome#login-modal");
            return;
        }

        User admin = auth.getUser();

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        int roleId = Integer.parseInt(request.getParameter("roleId"));

        if (new AuthenticationDAO().existEmail(email)) {
            session.setAttribute("errorMessage", "Email đã tồn tại.");
            response.sendRedirect("authenticationList");
            return;
        }
        

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        StaffDAO userDAO = new StaffDAO();
        int userId = userDAO.insertUserBasic(firstName, lastName, email, roleId);

        if (userId > 0) {
            AuthenticationDAO authDAO = new AuthenticationDAO();
            boolean ok = authDAO.createAuthForAdmin(userId, email, hashedPassword);

            if (ok) {
                authDAO.logCreateUser(userId, admin.getUserId(), "");
                request.getSession().setAttribute("successMessage", "Tạo tài khoản thành công!");
                response.sendRedirect("authenticationList");
                return;
            }
        }

        request.getSession().setAttribute("errorMessage", "Tạo tài khoản thất bại!");
        request.getRequestDispatcher("authenticationList").forward(request, response);
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
