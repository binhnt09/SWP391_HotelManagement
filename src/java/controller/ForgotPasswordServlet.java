/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import constant.MailUtil;
import dao.AuthenticationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
@WebServlet(name="ForgotPasswordServlet", urlPatterns={"/forgotpassword"})
public class ForgotPasswordServlet extends HttpServlet {
   
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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ForgotPasswordServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ForgotPasswordServlet at " + request.getContextPath () + "</h1>");
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
        processRequest(request, response);
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
        String action = request.getParameter("action");
        
        if("verifyEmailForgotPassword".equals(action)){
            verifyEmailForgotPassword(request, response);
        } else if("verifyCodeForgot".equals(action)){
            verifyCodeForgot(request, response);
        } else if("resendCodeFogot".equals(action)){
            resendVerifyCodeForgot(request, response);
        } else if("completeForgotPassword".equals(action)){
            completeForgotPassword(request, response);
        }
    }

    public void completeForgotPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailfg = (String) request.getSession().getAttribute("emailforgot");
        String pass = request.getParameter("pass");

        if (emailfg == null) {
            request.setAttribute("error", "Không tìm thấy email cần xác minh.");
            request.setAttribute("openModal", "#forgot-password-modal");
            request.getRequestDispatcher("home.jsp").forward(request, response);
            return;
        }

        try {
            AuthenticationDAO dao = new AuthenticationDAO();
            if (!dao.existEmail(emailfg)) {
                request.setAttribute("error", "Email này chưa được đăng ký.");
                request.setAttribute("openModal", "#completeForgotPassword-modal");
                request.getRequestDispatcher("home.jsp").forward(request, response);
                return;
            }

            dao.resetPasswordByEmail(emailfg, pass);
            request.getSession().removeAttribute("expiredAt");
            request.getSession().removeAttribute("emailforgot");

            request.setAttribute("success", "Reset password thành công! Mời bạn đăng nhập.");
            request.setAttribute("openModal", "#login-modal");
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(LoginAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void verifyEmailForgotPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailfg = request.getParameter("emailfg");
        request.getSession().setAttribute("emailforgot", emailfg);
        try {
            AuthenticationDAO dao = new AuthenticationDAO();
            if (!dao.existEmail(emailfg)) {
                request.setAttribute("error", "Email này chưa được đăng ký.");
                request.setAttribute("openModal", "#forgot-password-modal");
                request.getRequestDispatcher("home.jsp").forward(request, response);
                return;
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Timestamp expiredAt = (Timestamp) request.getSession().getAttribute("expiredAt");
        if (expiredAt == null || expiredAt.before(new Timestamp(System.currentTimeMillis()))) {
            // Chỉ gửi mã nếu chưa có hoặc đã hết hạn
            generateAndSendVerificationCode(emailfg);
            // Đặt lại thời gian hết hạn mới
            Timestamp newExpired = Timestamp.valueOf(LocalDateTime.now().plusSeconds(60));
            request.getSession().setAttribute("expiredAt", newExpired);
        } else {
            System.out.println("Mã vẫn còn hiệu lực, không gửi lại.");
        }

        request.setAttribute("openModal", "#enterVerifyCodeForgot-modal");
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }
    
    public void verifyCodeForgot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailfg = (String) request.getSession().getAttribute("emailforgot");
        String codefg = request.getParameter("codefg");

        if (emailfg == null) {
            request.setAttribute("error", "Không tìm thấy email cần xác minh.");
            request.setAttribute("openModal", "#forgot-password-modal");
            request.getRequestDispatcher("home.jsp").forward(request, response);
            return;
        }

        try {
            AuthenticationDAO dao = new AuthenticationDAO();
            if (!dao.existEmail(emailfg)) {
                request.setAttribute("error", "Email này chưa được đăng ký.");
                request.setAttribute("openModal", "#forgot-password-modal");
                request.getRequestDispatcher("home.jsp").forward(request, response);
                return;
            }

            boolean validCode = dao.verifyCode(emailfg, codefg);

            if (validCode) {
                request.setAttribute("openModal", "#completeForgotPassword-modal");
            } else {
                request.setAttribute("error", "Mã xác minh không hợp lệ hoặc đã hết hạn.");
                request.setAttribute("openModal", "#enterVerifyCodeForgot-modal");
            }

            request.getRequestDispatcher("home.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Lỗi xử lý xác minh mã", e);
        }
    }
    
    public void resendVerifyCodeForgot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailrs = (String) request.getSession().getAttribute("emailforgot");
        if (emailrs == null) {
            request.setAttribute("error", "Không tìm thấy email cần xác minh.");
            request.setAttribute("openModal", "#forgot-password-modal");
            request.getRequestDispatcher("home.jsp").forward(request, response);
            return;
        }

        Timestamp expiredAt = (Timestamp) request.getSession().getAttribute("expiredAt");
        if (expiredAt != null && expiredAt.after(new Timestamp(System.currentTimeMillis()))) {
            System.out.println("Mã đã được gửi trước đó, chưa hết hạn.");
        } else {
//            request.getSession().removeAttribute("expiredAt");
            generateAndSendVerificationCode(emailrs);

            Timestamp newExpired = Timestamp.valueOf(LocalDateTime.now().plusSeconds(60));
            request.getSession().setAttribute("expiredAt", newExpired);
        }

        request.setAttribute("success", "Mã mới đã được gửi đến email của bạn.");
        request.setAttribute("openModal", "#enterVerifyCodeForgot-modal");
//        request.setAttribute("openModalRegister", "#register-modal");
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }
    
    private String generateAndSendVerificationCode(String emailvrf) throws ServletException {
        String code = String.valueOf((int) (Math.random() * 900000 + 100000));
        try {
            AuthenticationDAO dao = new AuthenticationDAO();
//            Timestamp expiredAt = Timestamp.valueOf(LocalDateTime.now().plusSeconds(20));
            dao.invalidateOldCodes(emailvrf); // Hủy mã cũ chưa dùng
            dao.insertVerification(emailvrf, code); // Ghi mã mới

            // Gửi email thật
            MailUtil.send(emailvrf, code);
            System.out.println("Gửi mã đến email: " + emailvrf + ", mã: " + code);
            return code;
        } catch (Exception e) {
            throw new ServletException("Lỗi gửi mã xác minh", e);
        }
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
