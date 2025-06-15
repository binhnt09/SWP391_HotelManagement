/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AuthenticationDAO;
import entity.Authentication;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "changePasswordServlet", urlPatterns = {"/changeassword"})
public class changePasswordServlet extends HttpServlet {

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
        response.getWriter().println("Hello from doGet.");
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
        String action = request.getParameter("action");

        if ("changePassword".equals(action)) {
            changePassword(request, response);
        } else if("changePasswordInProfile".equals(action)){
            changePasswordInProfile(request, response);
        }
    }

    public void changePasswordInProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Authentication authLocal = (Authentication) request.getSession().getAttribute("authLocal");

        int userId = -1;

        userId = authLocal.getUser().getUserId();

        // Nếu không tìm thấy user hợp lệ
        if (userId == -1) {
            response.sendRedirect("loadtohome");
            return;
        }

        // Lấy mật khẩu từ form
        String oldPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("pass");

        AuthenticationDAO dao = new AuthenticationDAO();
        boolean success = dao.changePassword(userId, oldPassword, newPassword);
        
        if (success) {
            request.setAttribute("successChange", "Đổi mật khẩu thành công!");
        } else {
            request.setAttribute("errorChange", "Mật khẩu hiện tại không đúng.");
        }
        request.setAttribute("openTab", "#password-security");
        request.getRequestDispatcher("/profile/profile.jsp").forward(request, response);
    }
    
    public void changePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Authentication authLocal = (Authentication) request.getSession().getAttribute("authLocal");

        int userId = -1;

        userId = authLocal.getUser().getUserId();

        // Nếu không tìm thấy user hợp lệ
        if (userId == -1) {
            response.sendRedirect("loadtohome");
            return;
        }

        // Lấy mật khẩu từ form
        String oldPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("pass");

        AuthenticationDAO dao = new AuthenticationDAO();
        boolean success = dao.changePassword(userId, oldPassword, newPassword);
        
        if (success) {
            request.setAttribute("success", "Đổi mật khẩu thành công!");
        } else {
            request.setAttribute("error", "Mật khẩu hiện tại không đúng.");
        }
        request.setAttribute("openModal", "#changePassword-modal");
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

//    public void changePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession(false);
//        Authentication authLocal = null;
//        GoogleAccount authGoogle = null;
//
//        int userId = -1;
//        String authType = null;
//
//        // Ưu tiên tài khoản local
//        if (session.getAttribute("authLocal") != null) {
//            authLocal = (Authentication) session.getAttribute("authLocal");
//            userId = authLocal.getUserID();
//            authType = authLocal.getAuthType();
//        } else if (session.getAttribute("authGoogle") != null) {
//            authGoogle = (GoogleAccount) session.getAttribute("authGoogle");
//            authType = "google";
//
//            // Lấy userId từ DB theo email Google
//            AuthenticationDAO dao = new AuthenticationDAO();
//            userId = dao.getUserIdByEmail(authGoogle.getEmail());
//        }
//
//        // Nếu không tìm thấy user hợp lệ
//        if (userId == -1) {
//            response.sendRedirect("loadtohome");
//            return;
//        }
//
//        // Lấy mật khẩu từ form
//        String oldPassword = request.getParameter("currentPassword");
//        String newPassword = request.getParameter("pass");
//
//        AuthenticationDAO dao = new AuthenticationDAO();
//        boolean success = false;
//
//        if ("local".equals(authType)) {
//            success = dao.changePassword(userId, oldPassword, newPassword);
//            if (success) {
//                request.setAttribute("success", "Đổi mật khẩu thành công!");
//            } else {
//                request.setAttribute("error", "Mật khẩu hiện tại không đúng.");
//            }
//        } else if ("google".equals(authType)) {
//            success = dao.setPasswordForGoogleUser(userId, newPassword);
//            if (success) {
//                request.setAttribute("success", "Đặt mật khẩu mới thành công. Lần sau bạn có thể đăng nhập bằng email & mật khẩu.");
//            } else {
//                request.setAttribute("error", "Không thể cập nhật mật khẩu.");
//            }
//        }
//
//        request.setAttribute("openModal", "#changePassword-modal");
//        request.getRequestDispatcher("home.jsp").forward(request, response);
//    }

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
