/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authentication;

import dao.AuthenticationDAO;
import entity.Authentication;
import entity.GoogleAccount;
import entity.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "LoginGoogleServlet", urlPatterns = {"/logingoogle"})
public class LoginGoogleServlet extends HttpServlet {

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
//        remove cache
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // LOGOUT 
        String logoutParam = request.getParameter("logout");
        if (logoutParam != null && logoutParam.equals("true")) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect("loadtohome");
            return;
        }

        // Đăng nhập Google
        String code = request.getParameter("code");
        String error = request.getParameter("error");

        if (error != null) {
            forwardToHomeView(response);
            return;
        }

        try {
            String accessToken = GoogleLogin.getToken(code);
            GoogleAccount acc = GoogleLogin.getUserInfo(accessToken);

            System.out.println("accessToken: " + accessToken);
            System.out.println("acc: " + acc);
            if (acc == null) {
                forwardToHomeView(response);
                return;
            }

            String fullName = acc.getName();

            String firstName = "Unknown";
            String lastName = "";
            String email = acc.getEmail();

            if (fullName != null && !fullName.trim().isEmpty()) {
                String[] paths = fullName.trim().split("\\s+");
                if (paths.length == 1) {
                    firstName = paths[0];
                } else {
                    firstName = paths[paths.length - 1];
                    lastName = String.join(" ", Arrays.copyOfRange(paths, 0, paths.length - 1));
                }
            }

            int userId;
            AuthenticationDAO dao = new AuthenticationDAO();
            if (!dao.existEmail(email)) {
                userId = dao.createUser(email, firstName, lastName);
                dao.createGoogleAuth(userId);
            } else {
                userId = dao.getUserIdByEmail(email);
            }

            User user = dao.findUserById(userId);
            Authentication auth = new Authentication(user);
            auth.setAuthType("google");

            HttpSession session = request.getSession();
            session.setAttribute("authLocal", auth);
            forwardToHomeView(response);
        } catch (Exception ex) {
            Logger.getLogger(LoginGoogleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void forwardToHomeView(HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("loadtohome");
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
        processRequest(request, response);
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
