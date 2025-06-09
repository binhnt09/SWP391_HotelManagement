/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import constant.MailUtil;
import dao.AuthenticationDAO;
import entity.Authentication;
import java.io.IOException;
import java.io.PrintWriter;
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
            out.println("<title>Servlet LoginAccountServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginAccountServlet at " + request.getContextPath() + "</h1>");
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
        String action = request.getParameter("action");

        if ("login".equals(action)) {
            login(request, response);
        } else if ("verifyEmail".equals(action)) {
            verifyEmail(request, response);
        } else if ("resendCode".equals(action)) {
            resendVerifyCode(request, response);
        } else if ("verifyCode".equals(action)) {
            verifyCode(request, response);
        } else if ("register".equals(action)) {
            completeRegister(request, response);
        } else if("verifyEmailForgotPassword".equals(action)){
            verifyEmailForgotPassword(request, response);
        }
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        String remember = request.getParameter("remember");

//        String loginErr = null;
        AuthenticationDAO dao = new AuthenticationDAO();
        boolean isValid = dao.isValidLogin(email, pass);

        if (!dao.existEmail(email)) {
//            loginErr = "Email not registered account";
            response.sendRedirect("home.jsp?loginError=EmailNotExist&email=" + URLEncoder.encode(email, "UTF-8"));
        } else if (!isValid) {
//            loginErr = "Wrong email or password";
            response.sendRedirect("home.jsp?loginError=WrongPassword&email=" + URLEncoder.encode(email, "UTF-8"));
        } else {
            HttpSession session = request.getSession();
            Authentication auth = dao.login(email);
            session.setAttribute("auth", auth);

            if ("on".equals(remember)) {
                Cookie ck = new Cookie("email", email);
                ck.setMaxAge(60 * 60 * 24 * 7); // 7 ngày
//                session.setMaxInactiveInterval(100);
                response.addCookie(ck);
            } else {
                Cookie ck = new Cookie("email", "");
                ck.setMaxAge(0);;
                response.addCookie(ck);
            }
            response.sendRedirect("loadtohome");
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
            Timestamp newExpired = Timestamp.valueOf(LocalDateTime.now().plusSeconds(20));
            request.getSession().setAttribute("expiredAt", newExpired);
        } else {
            System.out.println("Mã vẫn còn hiệu lực, không gửi lại.");
        }

        request.setAttribute("openModal", "#enterVerifyCode-modal");
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    public void verifyEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailvrf = request.getParameter("emailvrf");
        request.getSession().setAttribute("email_to_verify", emailvrf);

        AuthenticationDAO dao = new AuthenticationDAO();
        try {
            if (dao.existEmail(emailvrf)) {
                request.setAttribute("error", "Email này đã được sử dụng.");
                request.setAttribute("openModal", "#verifyEmail-modal");
                request.getRequestDispatcher("home.jsp").forward(request, response);
                return;
            }
        } catch (Exception e) {
            throw new ServletException("Lỗi kiểm tra email", e);
        }
        // Nếu đã có expiredAt trong session và vẫn còn hiệu lực thì ko gửi lại mã
        Timestamp expiredAt = (Timestamp) request.getSession().getAttribute("expiredAt");
        if (expiredAt == null || expiredAt.before(new Timestamp(System.currentTimeMillis()))) {
            // Chỉ gửi mã nếu chưa có hoặc đã hết hạn
            generateAndSendVerificationCode(emailvrf);
            // Đặt lại thời gian hết hạn mới
            Timestamp newExpired = Timestamp.valueOf(LocalDateTime.now().plusSeconds(20));
            request.getSession().setAttribute("expiredAt", newExpired);
        } else {
            System.out.println("Mã vẫn còn hiệu lực, không gửi lại.");
        }

        // Lưu email để dùng trong bước xác minh
        request.setAttribute("openModal", "#enterVerifyCode-modal");
//        request.setAttribute("openModalRegister", "#register-modal");
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    public void resendVerifyCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailrs = (String) request.getSession().getAttribute("email_to_verify");
        if (emailrs == null) {
            request.setAttribute("error", "Không tìm thấy email cần xác minh.");
            request.setAttribute("openModal", "#verifyEmail-modal");
            request.getRequestDispatcher("home.jsp").forward(request, response);
            return;
        }

        Timestamp expiredAt = (Timestamp) request.getSession().getAttribute("expiredAt");
        if (expiredAt != null && expiredAt.after(new Timestamp(System.currentTimeMillis()))) {
            System.out.println("Mã đã được gửi trước đó, chưa hết hạn.");
        } else {
//            request.getSession().removeAttribute("expiredAt");
            generateAndSendVerificationCode(emailrs);

            Timestamp newExpired = Timestamp.valueOf(LocalDateTime.now().plusSeconds(20));
            request.getSession().setAttribute("expiredAt", newExpired);
        }

        request.setAttribute("success", "Mã mới đã được gửi đến email của bạn.");
        request.setAttribute("openModal", "#enterVerifyCode-modal");
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

    public void verifyCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailvf = (String) request.getSession().getAttribute("email_to_verify");
        String codeVf = request.getParameter("codevrf");

        if (emailvf == null) {
            request.setAttribute("error", "Không tìm thấy email cần xác minh.");
            request.setAttribute("openModal", "#verifyEmail-modal");
            request.getRequestDispatcher("home.jsp").forward(request, response);
            return;
        }

        try {
            AuthenticationDAO dao = new AuthenticationDAO();
            if (dao.existEmail(emailvf)) {
                request.setAttribute("error", "Email này đã được sử dụng.");
                request.setAttribute("openModal", "#verifyEmail-modal");
                request.getRequestDispatcher("home.jsp").forward(request, response);
                return;
            }

            boolean validCode = dao.verifyCode(emailvf, codeVf);

            if (validCode) {
                request.setAttribute("openModalRegister", "#register-modal");
            } else {
                request.setAttribute("error", "Mã xác minh không hợp lệ hoặc đã hết hạn.");
                request.setAttribute("openModal", "#enterVerifyCode-modal");
            }

            request.getRequestDispatcher("home.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Lỗi xử lý xác minh mã", e);
        }
    }
    
    public void completeRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailvf = (String) request.getSession().getAttribute("email_to_verify");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String pass = request.getParameter("pass");

        if (emailvf == null) {
            request.setAttribute("error", "Không tìm thấy email cần xác minh.");
            request.setAttribute("openModal", "#verifyEmail-modal");
            request.getRequestDispatcher("home.jsp").forward(request, response);
            return;
        }

        try {
            AuthenticationDAO dao = new AuthenticationDAO();
            if (dao.existEmail(emailvf)) {
                request.setAttribute("error", "Email này đã được sử dụng.");
                request.setAttribute("openModalRegister", "#register-modal");
                request.getRequestDispatcher("home.jsp").forward(request, response);
                return;
            }

            int userID = dao.createUser(emailvf, firstName, lastName);
            dao.createAuth(userID, pass);

            request.getSession().removeAttribute("email_to_verify");
            request.getSession().removeAttribute("expiredAt");

            request.setAttribute("success", "Đăng ký thành công! Mời bạn đăng nhập.");
            request.setAttribute("openModalLogin", "#login-modal");
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(LoginAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

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
