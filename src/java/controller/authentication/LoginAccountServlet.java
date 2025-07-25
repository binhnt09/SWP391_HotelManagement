/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authentication;

import constant.MailUtil;
import dao.AuthenticationDAO;
import entity.Authentication;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "LoginAccountServlet", urlPatterns = {"/loginaccount"})
public class LoginAccountServlet extends HttpServlet {

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

        if (null != action) switch (action) {
            case "login" -> login(request, response);
            case "verifyEmail" -> verifyEmail(request, response);
            case "resendCode" -> resendVerifyCode(request, response);
            case "verifyCode" -> verifyCode(request, response);
            case "register" -> completeRegister(request, response);
            default -> {
            }
        }
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailName = "email";
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        String remember = request.getParameter("remember");

        String logout = request.getParameter("logout");

        if (logout != null && "true".equals(logout)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate(); // xóa session
            }
            response.sendRedirect("loadtohome");
            return;
        }

        AuthenticationDAO dao = new AuthenticationDAO();
        boolean isValid = dao.isValidLogin(email, pass);

        if (!dao.existEmail(email)) {
            response.sendRedirect("home.jsp?loginError=EmailNotExist&email=" + URLEncoder.encode(email, "UTF-8"));
        } else if (!isValid) {
            response.sendRedirect("home.jsp?loginError=WrongPassword&email=" + URLEncoder.encode(email, "UTF-8"));
        } else {
            HttpSession session = request.getSession();
            Authentication auth = dao.login(email);
            session.setAttribute("authLocal", auth);

            if ("on".equals(remember)) {
                Cookie ck = new Cookie(emailName, email);
                Cookie passCookie = new Cookie("password", pass);
                
                ck.setMaxAge(60 * 60 * 24 * 7);
                passCookie.setMaxAge(60 * 60 * 24 * 7);
                
                ck.setPath("/");
                passCookie.setPath("/");
                
                response.addCookie(ck);
                response.addCookie(passCookie);
            } else {
                Cookie ck = new Cookie(emailName, "");
                ck.setMaxAge(0);
                ck.setPath("/");
                response.addCookie(ck);
            }
            response.sendRedirect("loadtohome");
        }
    }

    private static final String EMAIL_TO_VERIFY = "email_to_verify";
    private static final String ERROR = "error";
    private static final String EXPIREDAT = "expiredAt";

    private static final String ERROR_EMAIL = "Email này đã được sử dụng.";
    private static final String ERROR_EMAIL_1 = "Không tìm thấy email cần xác minh.";

    private static final String OPENMODAL = "openModal";
    private static final String MODAL_VERIFY = "#verifyEmail-modal";
    private static final String MODAL_ENTER_VERIFY = "#enterVerifyCode-modal";

    public void verifyEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailvrf = request.getParameter("emailvrf");
        request.getSession().setAttribute(EMAIL_TO_VERIFY, emailvrf);

        AuthenticationDAO dao = new AuthenticationDAO();
        try {
            if (dao.existEmail(emailvrf)) {
                request.setAttribute(ERROR, ERROR_EMAIL);
                request.setAttribute(OPENMODAL, MODAL_VERIFY);
                forwardToHomeView(request, response);
                return;
            }
        } catch (ServletException | IOException e) {
            throw new ServletException("Lỗi kiểm tra email", e);
        }
        // Nếu đã có expiredAt trong session và vẫn còn hiệu lực thì ko gửi lại mã
        Timestamp expiredAt = (Timestamp) request.getSession().getAttribute(EXPIREDAT);
        if (expiredAt == null || expiredAt.before(new Timestamp(System.currentTimeMillis()))) {
            // Chỉ gửi mã nếu chưa có hoặc đã hết hạn
            generateAndSendVerificationCode(emailvrf);
            // Đặt lại thời gian hết hạn mới
            Timestamp newExpired = Timestamp.valueOf(LocalDateTime.now().plusSeconds(60));
            request.getSession().setAttribute(EXPIREDAT, newExpired);
        } else {
            Logger.getLogger(LoginAccountServlet.class.getName()).info("Mã vẫn còn hiệu lực, không gửi lại.");
        }

        request.setAttribute(OPENMODAL, MODAL_ENTER_VERIFY);
        forwardToHomeView(request, response);
    }

    public void resendVerifyCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailrs = (String) request.getSession().getAttribute(EMAIL_TO_VERIFY);
        if (emailrs == null) {
            request.setAttribute(ERROR, ERROR_EMAIL_1);
            request.setAttribute(OPENMODAL, MODAL_VERIFY);
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
        request.setAttribute(OPENMODAL, MODAL_ENTER_VERIFY);
        forwardToHomeView(request, response);
    }

    private String generateAndSendVerificationCode(String emailvrf) throws ServletException {
        String code = String.valueOf((int) (Math.random() * 900000 + 100000));
        try {
            AuthenticationDAO dao = new AuthenticationDAO();
            dao.invalidateOldCodes(emailvrf); // Hủy mã cũ chưa dùng
            dao.insertVerification(emailvrf, code); // Ghi mã mới

            MailUtil.send(emailvrf, code);
            return code;
        } catch (Exception e) {
            throw new ServletException("Lỗi gửi mã xác minh", e);
        }
    }

    public void verifyCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailvf = (String) request.getSession().getAttribute(EMAIL_TO_VERIFY);
        String codeVf = request.getParameter("codevrf");

        RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
        if (emailvf == null) {
            request.setAttribute(ERROR, ERROR_EMAIL_1);
            request.setAttribute(OPENMODAL, MODAL_VERIFY);
            dispatcher.forward(request, response);
            return;
        }

        try {
            AuthenticationDAO dao = new AuthenticationDAO();
            if (dao.existEmail(emailvf)) {
                request.setAttribute(ERROR, ERROR_EMAIL);
                request.setAttribute(OPENMODAL, MODAL_VERIFY);
                forwardToHomeView(request, response);
                return;
            }

            boolean validCode = dao.verifyCode(emailvf, codeVf);

            if (validCode) {
                request.setAttribute(OPENMODAL, "#register-modal");
            } else {
                request.setAttribute(ERROR, "Mã xác minh không hợp lệ hoặc đã hết hạn.");
                request.setAttribute(OPENMODAL, MODAL_ENTER_VERIFY);
            }

            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            Logger.getLogger(LoginAccountServlet.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void completeRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailvf = (String) request.getSession().getAttribute(EMAIL_TO_VERIFY);
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String pass = request.getParameter("pass");

        if (emailvf == null) {
            request.setAttribute(ERROR, ERROR_EMAIL_1);
            request.setAttribute(OPENMODAL, MODAL_VERIFY);
            forwardToHomeView(request, response);
            return;
        }

        try {
            AuthenticationDAO dao = new AuthenticationDAO();
            if (dao.existEmail(emailvf)) {
                request.setAttribute(ERROR, ERROR_EMAIL);
                request.setAttribute(OPENMODAL, "#register-modal");
                forwardToHomeView(request, response);
                return;
            }

            int userID = dao.createUser(emailvf, firstName, lastName);
            dao.createAuth(userID, pass);

            HttpSession session = request.getSession();
            session.removeAttribute(EMAIL_TO_VERIFY);
            session.removeAttribute(EXPIREDAT);

            request.setAttribute("success", "Đăng ký thành công! Mời bạn đăng nhập.");
            request.setAttribute(OPENMODAL, "#login-modal");
            forwardToHomeView(request, response);
        } catch (Exception ex) {
            Logger.getLogger(LoginAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
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
