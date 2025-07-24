/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.voucher;

import constant.MailUtil;
import dao.AuthenticationDAO;
import dao.MembershipDao;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import validation.Validation;

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

        String searchVoucher = request.getParameter("searchVoucher");
        String sortby = request.getParameter("sortby");
        String orderSort = request.getParameter("orderSort");

        VoucherDao voucherDao = new VoucherDao();
        List<Voucher> listVoucher = new ArrayList<>();
        
        if (index_raw == null || index_raw.isEmpty()) {
            Object indexAttr = request.getSession().getAttribute("index");
            if (indexAttr != null) {
                index_raw = indexAttr.toString();
                request.getSession().removeAttribute("index");
            }
        }
        int index = 1;
        if (index_raw != null) {
            index = Validation.parseStringToInt(index_raw);
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

        listVoucher = voucherDao.searchOrSortVoucher(searchVoucher, sortby, isDescending, index);
        count = voucherDao.countSearchResults(searchVoucher);
        int endPage = (int) Math.ceil(count / 6.0);
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
        } else if ("updateVoucher".equals(action)) {
            updateVoucher(request, response);
        } else if ("removeVoucher".equals(action)) {
            removeVoucher(request, response);
        }
    }

    protected void removeVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] voucherIdRe_Raw = request.getParameterValues("voucherIdRe");

        VoucherDao dao = new VoucherDao();
        if (voucherIdRe_Raw != null) {
            for (String id : voucherIdRe_Raw) {
                try {
                int voucherId = Integer.parseInt(id);
                System.out.println("Deleting voucherId: " + voucherId);
                dao.updateIsdeletedVoucher(voucherId);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid bookingID: " + id);
                    System.out.println(e);
                }
            }
        }
            String index_raw = request.getParameter("index");
            request.getSession().setAttribute("index", index_raw);
        request.getSession().setAttribute("success", "Remove Voucher successfully");
        response.sendRedirect(request.getContextPath() + "/vouchermanage");
    }

    protected void insertVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        VoucherDao voucherDao = new VoucherDao();
        String voucherCode = request.getParameter("voucherCode");
        String discout_str = request.getParameter("Discout");

        Date validfrom = Validation.parseSqlDate(request.getParameter("validfrom"));
        Date validto = Validation.parseSqlDate(request.getParameter("validto"));

        String memberShipId = request.getParameter("memberShipId");

        boolean valid = true;
        String errorMessage = null;

        double discount = Validation.parseStringToDouble(discout_str);

        if (voucherCode == null || voucherCode.isEmpty()) {
            errorMessage = "Please enter voucher code!";
            valid = false;
        } else if (discount <= 0) {
            errorMessage = "Voucher must more than 0%";
            valid = false;
        } else if (validfrom == null || validto == null) {
            errorMessage = "Please select a voucher expiration date!";
            valid = false;
        } else if (voucherDao.isDuplicateVoucher(voucherCode, discount, validfrom, validto)) {
            errorMessage = "Voucher already exists with same code, discount, and dates.";
            valid = false;
        } else if (memberShipId == null || memberShipId.trim().isEmpty()) {
            errorMessage = "Please choose at least one membership level.";
            valid = false;
        }

        if (!valid) {
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("openModal", "#addVoucherModal");
            doGet(request, response);
        }
        if (valid) {
            voucherDao.insertVoucher(voucherCode, discount, validfrom, validto);
            if (memberShipId != null && !memberShipId.trim().isEmpty()) {
                voucherDao.insertVoucherLevels(voucherCode, memberShipId);
                AuthenticationDAO authenDao = new AuthenticationDAO();
                String[] levelIds = memberShipId.split(", ");
                for (String levelIdStr : levelIds) {
                    int levelId = Validation.parseStringToInt(levelIdStr.trim());

                    List<String> emails = authenDao.getEmailByLevelId(levelId);
                    for (String email : emails) {
                        try {
                            MailUtil.sendVoucherByEmail(email, voucherCode);
                        } catch (Exception ex) {
                            Logger.getLogger(VoucherManage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            request.getSession().setAttribute("success", "Voucher created successfully");
            response.sendRedirect(request.getContextPath() + "/vouchermanage");
        }
    }

    protected void updateVoucher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        VoucherDao voucherDao = new VoucherDao();

        String voucherIdUdStr = request.getParameter("voucherIdUd");

        String voucherCodeUd = request.getParameter("voucherCodeUd");
        String discout_strUd = request.getParameter("DiscoutUd");

        Date validfromUd = Validation.parseSqlDate(request.getParameter("validfromUd"));
        Date validtoUd = Validation.parseSqlDate(request.getParameter("validtoUd"));

        String memberShipId = request.getParameter("memberShipId");

        boolean valid = true;
        String errorMessage = null;

        double discount = Validation.parseStringToDouble(discout_strUd);

        if (voucherCodeUd == null || voucherCodeUd.isEmpty()) {
            errorMessage = "Please enter voucher code!";
            valid = false;
        } else if (discount <= 0) {
            errorMessage = "Voucher must more than 0%";
            valid = false;
        } else if (validfromUd == null || validtoUd == null) {
            errorMessage = "Please select a voucher expiration date!";
            valid = false;
        } else if (voucherDao.isDuplicateVoucher(voucherCodeUd, discount, validfromUd, validtoUd)) {
            errorMessage = "Voucher already exists with same code, discount, and dates.";
            valid = false;
        } else if (memberShipId == null || memberShipId.trim().isEmpty()) {
            errorMessage = "Please choose at least one membership level.";
            valid = false;
        }
        int voucherId = Validation.parseStringToInt(voucherIdUdStr);
        if (!valid) {
            String index_raw = request.getParameter("index");
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("openModalEdit", "#editVoucherModal");
            request.setAttribute("levelId", memberShipId);
            request.setAttribute("index", index_raw);
            doGet(request, response);
            return;
        }
        if (valid) {
            Voucher voucher = new Voucher();
            voucher.setVoucherId(voucherId);
            voucher.setCode(voucherCodeUd);
            voucher.setDiscountPercentage(discount);
            voucher.setValidFrom(validfromUd);
            voucher.setValidTo(validtoUd);

            boolean update = voucherDao.updateVoucher(voucher);
            if (memberShipId != null && !memberShipId.trim().isEmpty()) {
                voucherDao.insertVoucherLevels(voucherCodeUd, memberShipId);
                AuthenticationDAO authenDao = new AuthenticationDAO();
                String[] levelIds = memberShipId.split(", ");
                for (String levelIdStr : levelIds) {
                    int levelId = Validation.parseStringToInt(levelIdStr.trim());

                    List<String> emails = authenDao.getEmailByLevelId(levelId);
                    for (String email : emails) {
                        try {
                            MailUtil.sendVoucherByEmail(email, voucherCodeUd);
                        } catch (Exception ex) {
                            Logger.getLogger(VoucherManage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            if (update) {
                request.getSession().setAttribute("success", "Update Voucher successfully");
            }
            request.getSession().setAttribute("levelId", memberShipId);
            response.sendRedirect(request.getContextPath() + "/vouchermanage");
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
