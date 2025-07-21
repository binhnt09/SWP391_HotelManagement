/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.voucher;

import dao.VoucherDao;
import entity.MembershipLevel;
import entity.Voucher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "VoucherManage", urlPatterns = {"/vouchermanage"})
public class VoucherManage extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet VoucherManage</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VoucherManage at " + request.getContextPath() + "</h1>");
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
        String index_raw = request.getParameter("index");

        String searchVoucher = request.getParameter("searchAccount");
        String sortby = request.getParameter("sortby");
        String orderSort = request.getParameter("orderSort");

        VoucherDao voucherDao = new VoucherDao();
        List<Voucher> listVoucher = new ArrayList<>();

        int index = 1;
        if (index_raw != null) {
            index = validation.Validation.parseStringToInt(index_raw);
        }

//        Set<String> validSortFields = Set.of("Code", "DiscountPercentage", "CreatedAt");
        if (sortby == null || sortby.isEmpty() || sortby.equals("default")) {
            sortby = "Code";
        }
        if (orderSort == null || orderSort.isEmpty() || orderSort.equals("default")) {
            orderSort = "asc";
        }

        boolean isDescending = "desc".equals(orderSort);
        int count;

//        if(searchVoucher!=  null && !searchVoucher.isEmpty()){
        listVoucher = voucherDao.searchOrSortVoucher(searchVoucher, sortby, isDescending, index);
        count = voucherDao.countSearchResults(searchVoucher);
//        }else{
//            listVoucher = voucherDao.searchOrSortVoucher(null, sortby, isDescending, index);
//            count = voucherDao.countSearchResults(null);
//        }
//        int endPage = count / 5;
//        if (count % 5 != 0) {
//            endPage++;
//        }
        int endPage = (int) Math.ceil(count / 5.0);
        //memeber ship
        List<MembershipLevel> listMemberShip = voucherDao.getAllMembership();

        request.setAttribute("listMemberShip", listMemberShip);
        request.setAttribute("tag", index);
        request.setAttribute("endPage", endPage);
        request.setAttribute("count", count);

        request.setAttribute("sortby", sortby);
        request.setAttribute("orderSort", orderSort);
        request.setAttribute("searchVoucher", searchVoucher);
        request.setAttribute("searchVoucherEncoded", URLEncoder.encode(searchVoucher != null ? searchVoucher : "", StandardCharsets.UTF_8));

        request.setAttribute("listVoucher", listVoucher);
        request.getRequestDispatcher("/views/voucher/managevoucher.jsp").forward(request, response);
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
        if ("insertVoucher".equals(action)) {
            insertVoucher(request, response);
        }
    }

    protected void insertVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        VoucherDao voucherDao = new VoucherDao();
        String voucherCode = request.getParameter("voucherCode");
        String discout_str = request.getParameter("Discout");

        Date validfrom = Date.valueOf(request.getParameter("validfrom"));
        Date validto = Date.valueOf(request.getParameter("validto"));

        String memberShipId = request.getParameter("memberShipId");
//        String[] memberShipLv = memberShipId.split(", ");
        boolean valid = true;
        String errorMessage = null;

        int discount = validation.Validation.parseStringToInt(discout_str);

        if (!validto.after(validfrom)) {
            errorMessage = "Check-out date must be later than check-in date.";
            valid = false;
        }

        if (valid) {
            voucherDao.insertVoucher(voucherCode, discount, validfrom, validto);
            if (memberShipId != null && !memberShipId.trim().isEmpty()) {
                voucherDao.insertVoucherLevels(voucherCode, memberShipId);
            }
            request.getSession().setAttribute("success", "Voucher created successfully");
        } else {
            request.getSession().setAttribute("errorMessage", errorMessage);
        }
        response.sendRedirect(request.getContextPath() + "/vouchermanage");
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
