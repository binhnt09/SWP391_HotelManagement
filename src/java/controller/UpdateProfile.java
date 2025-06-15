/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AuthenticationDAO;
import entity.Authentication;
import entity.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import validation.Validation;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "UpdateProfile", urlPatterns = {"/updateprofile"})
public class UpdateProfile extends HttpServlet {

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

        if ("updateProfile".equals(action)) {
            updateProfile(request, response);
        }
    }

    public void updateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Authentication auth = getCurrentAuth(request);
        if (auth == null) {
            response.sendRedirect("home.jsp");

            return;
        }

        User user = auth.getUser();
        String firstName = Validation.trimSafe(request.getParameter("firstName"));
        String lastName = Validation.trimSafe(request.getParameter("lastName"));
        String gender = Validation.trimSafe(request.getParameter("gender"));
        String phone = Validation.trimSafe(request.getParameter("phone"));
        String city = Validation.trimSafe(request.getParameter("city"));
        String birthDay = Validation.trimSafe(request.getParameter("birthDay"));
        String birthMonth = Validation.trimSafe(request.getParameter("birthMonth"));
        String birthYear = Validation.trimSafe(request.getParameter("birthYear"));

        AuthenticationDAO dao = new AuthenticationDAO();
        int day = 0;
        int month = 0;
        int year = 0;
        try {
            if (!birthDay.isEmpty() && !birthMonth.isEmpty() && !birthYear.isEmpty()) {
                day = Integer.parseInt(birthDay);
                month = Integer.parseInt(birthMonth);
                year = Integer.parseInt(birthYear);
                LocalDate birth = LocalDate.of(year, month, day);

                Timestamp timestamp = Timestamp.valueOf(birth.atStartOfDay());
                user.setBirthDay(timestamp);
            }
        } catch (NumberFormatException e) {
            Logger.getLogger(UpdateProfile.class.getName()).log(Level.SEVERE, null, e);
            return;
        }
        
        if(dao.existPhone(phone)){
            request.setAttribute("errorUpProfile", "Số điện thoại này đã tồn tại!");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setSex(gender);
        user.setAddress(city);

        boolean isUpdateUser = dao.updateUser(user);

        if (isUpdateUser) {
            auth.setUser(user);

            request.getSession().setAttribute("authLocal", auth);

            request.getSession().setAttribute("successUpProfile", "Cập nhật thông tin cá nhân thành công!");
            response.sendRedirect("profile.jsp");
//            request.getRequestDispatcher("profile.jsp").forward(request, response);
        } else {
            request.setAttribute("errorUpProfile", "Cập nhật thất bại!");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }
    }

    private Authentication getCurrentAuth(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }

        Authentication auth = (Authentication) session.getAttribute("authLocal");
        if (auth == null) {
            return null;
        }

        return auth;
    }

}
