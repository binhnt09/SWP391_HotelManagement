/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authentication;

import constant.MailUtil;
import dao.AuthenticationDAO;
import entity.Authentication;
import java.io.IOException;
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
@WebServlet(name = "ForgotPasswordServlet", urlPatterns = {"/forgotpassword"})
public class ForgotPasswordServlet extends HttpServlet {

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

        if ("verifyEmailForgotPassword".equals(action)) {
            verifyEmailForgotPassword(request, response);
        } else if ("verifyCodeForgot".equals(action)) {
            verifyCodeForgot(request, response);
        } else if ("resendCodeFogot".equals(action)) {
            resendVerifyCodeForgot(request, response);
        } else if ("completeForgotPassword".equals(action)) {
            completeForgotPassword(request, response);
        }
    }

    private static final String ERROR_1 = "Email này chưa được đăng ký.";
    private static final String ERROR_2 = "Không tìm thấy email cần xác minh.";
    private static final String ERRORFG = "errorfg";
    private static final String EMAILFORGOT = "emailForgot";
    private static final String OPENMODAL = "openModal";
    private static final String EXPIREDAT = "expiredAt";

    private static final String MODAL_FOGOT = "#forgot-password-modal";
    private static final String MODAL_VERIFY = "#enterVerifyCodeForgot-modal";

    public void completeForgotPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailfg = (String) request.getSession().getAttribute(EMAILFORGOT);
        String pass = request.getParameter("pass");

        if (emailfg == null) {
            request.setAttribute(ERRORFG, ERROR_2);
            request.setAttribute(OPENMODAL, MODAL_FOGOT);
            forwardToHomeView(request, response);
            return;
        }

        try {
            AuthenticationDAO dao = new AuthenticationDAO();
            if (!dao.existEmail(emailfg)) {
                request.setAttribute(ERRORFG, ERROR_1);
                request.setAttribute(OPENMODAL, "#completeForgotPassword-modal");
                forwardToHomeView(request, response);
                return;
            }

            dao.resetPasswordByEmail(emailfg, pass);
            request.getSession().removeAttribute(EXPIREDAT);
            request.getSession().removeAttribute(EMAILFORGOT);

            request.setAttribute("success", "Reset password thành công! Mời bạn đăng nhập.");
            request.setAttribute(OPENMODAL, "#login-modal");
            forwardToHomeView(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(LoginAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void verifyEmailForgotPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailfg = request.getParameter("emailfg");
        request.getSession().setAttribute(EMAILFORGOT, emailfg);
        try {
            AuthenticationDAO dao = new AuthenticationDAO();
            Authentication auth = dao.findAuthTypeByEmail(emailfg);
            if (!dao.existEmail(emailfg) || auth == null) {
                request.setAttribute(ERRORFG, ERROR_1);
                request.setAttribute(OPENMODAL, MODAL_FOGOT);
                forwardToHomeView(request, response);
                return;
            }
            
            if ("google".equals(auth.getAuthType())) {
                request.setAttribute(ERRORFG, "Tài khoản Google không được sử dụng quên mật khẩu.");
                request.setAttribute(OPENMODAL, MODAL_FOGOT);
                forwardToHomeView(request, response);
                return;
            }
        } catch (ServletException | IOException ex) {
            Logger.getLogger(LoginAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        Timestamp expiredAt = (Timestamp) request.getSession().getAttribute(EXPIREDAT);
        if (expiredAt == null || expiredAt.before(new Timestamp(System.currentTimeMillis()))) {
            // Chỉ gửi mã nếu chưa có hoặc đã hết hạn
            generateAndSendVerificationCode(emailfg);
            // Đặt lại thời gian hết hạn mới
            Timestamp newExpired = Timestamp.valueOf(LocalDateTime.now().plusSeconds(60));
            request.getSession().setAttribute(EXPIREDAT, newExpired);
        } else {
            Logger.getLogger(LoginAccountServlet.class.getName()).info("Mã vẫn còn hiệu lực, không gửi lại.");
        }

        request.setAttribute(OPENMODAL, MODAL_VERIFY);
        forwardToHomeView(request, response);
    }

    public void verifyCodeForgot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailfg = (String) request.getSession().getAttribute(EMAILFORGOT);
        String codefg = request.getParameter("codefg");

        if (emailfg == null) {
            request.setAttribute(ERRORFG, ERROR_2);
            request.setAttribute(OPENMODAL, MODAL_FOGOT);
            forwardToHomeView(request, response);
            return;
        }

        try {
            AuthenticationDAO dao = new AuthenticationDAO();
            if (!dao.existEmail(emailfg)) {
                request.setAttribute(ERRORFG, ERROR_1);
                request.setAttribute(OPENMODAL, MODAL_FOGOT);
                forwardToHomeView(request, response);
                return;
            }

            boolean validCode = dao.verifyCode(emailfg, codefg);

            if (validCode) {
                request.setAttribute(OPENMODAL, "#completeForgotPassword-modal");
            } else {
                request.setAttribute(ERRORFG, "Mã xác minh không hợp lệ hoặc đã hết hạn.");
                request.setAttribute(OPENMODAL, MODAL_VERIFY);
            }

            forwardToHomeView(request, response);
        } catch (ServletException | IOException e) {
            throw new ServletException("Lỗi xử lý xác minh mã", e);
        }
    }

    public void resendVerifyCodeForgot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailrs = (String) request.getSession().getAttribute(EMAILFORGOT);
        if (emailrs == null) {
            request.setAttribute(ERRORFG, ERROR_2);
            request.setAttribute(OPENMODAL, MODAL_FOGOT);
            forwardToHomeView(request, response);
            return;
        }

        Timestamp expiredAt = (Timestamp) request.getSession().getAttribute(EXPIREDAT);
        if (expiredAt != null && expiredAt.after(new Timestamp(System.currentTimeMillis()))) {
            Logger.getLogger(LoginAccountServlet.class.getName()).info("Mã đã được gửi trước đó, chưa hết hạn.");
        } else {
            generateAndSendVerificationCode(emailrs);

            Timestamp newExpired = Timestamp.valueOf(LocalDateTime.now().plusSeconds(60));
            request.getSession().setAttribute(EXPIREDAT, newExpired);
        }

        request.setAttribute("success", "Mã mới đã được gửi đến email của bạn.");
        request.setAttribute(OPENMODAL, MODAL_VERIFY);
        forwardToHomeView(request, response);
    }

    private String generateAndSendVerificationCode(String emailvrf) throws ServletException {
        String code = String.valueOf((int) (Math.random() * 900000 + 100000));
        try {
            AuthenticationDAO dao = new AuthenticationDAO();
            dao.invalidateOldCodes(emailvrf); // Hủy mã cũ chưa dùng
            dao.insertVerification(emailvrf, code); // Ghi mã mới

            // Gửi email thật
            MailUtil.send(emailvrf, code);
            return code;
        } catch (Exception e) {
            throw new ServletException("Lỗi gửi mã xác minh", e);
        }
    }

    private void forwardToHomeView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("home.jsp").forward(request, response);
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
