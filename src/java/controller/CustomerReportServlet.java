/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CustomerDAO;
import dao.ReportDAO;
import entity.Authentication;
import entity.CustomerReport;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author viet7
 */

@WebServlet(name = "CustomerReportServlet", urlPatterns = {"/customerReport"})
public class CustomerReportServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CustomerReportServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerReportServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); 
        Authentication auth = (session != null) ? (Authentication) session.getAttribute("authLocal") : null;

        if (auth == null || auth.getUser().getUserRoleId() >2) {
            response.sendRedirect("loadtohome#login-modal");
            return;
        }
    
        int recordsPerPage = 10;

        //lấy trang
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int offset = (page - 1) * recordsPerPage;

        //lấy keyword 
        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }

        // --- Từ bộ lọc ---
        String tier = request.getParameter("tier");
        String bookingRange = request.getParameter("bookingRange");
        String spentRange = request.getParameter("spentRange");

        String registerStart = request.getParameter("registerStartDate");
        String registerEnd = request.getParameter("registerEndDate");

        String bookingStart = request.getParameter("bookingStartDate");
        String bookingEnd = request.getParameter("bookingEndDate");

        String sort = request.getParameter("sort");
        String order = request.getParameter("order");

        // --- Parse Booking Range ---
        int bookingMin = 0, bookingMax = Integer.MAX_VALUE;
        if (bookingRange != null && !bookingRange.isEmpty()) {
            if (bookingRange.equals("0")) {
                bookingMin = 0;
                bookingMax = 0;
            } else if (bookingRange.equals("50+")) {
                bookingMin = 51;
            } else if (bookingRange.contains("-")) {
                String[] parts = bookingRange.split("-");
                bookingMin = Integer.parseInt(parts[0]);
                bookingMax = Integer.parseInt(parts[1]);
            }
        }

// --- Parse Spent Range ---
        long spentMin = 0, spentMax = Long.MAX_VALUE;
        if (spentRange != null && !spentRange.isEmpty()) {
            switch (spentRange) {
                case "bronze":
                    spentMin = 0;
                    spentMax = 4999999;
                    break;
                case "silver":
                    spentMin = 5000000;
                    spentMax = 19999999;
                    break;
                case "gold":
                    spentMin = 20000000;
                    spentMax = 49999999;
                    break;
                case "platinum":
                    spentMin = 50000000;
                    break;
                default:
//                    if (spentRange.contains("-")) {
//                        String[] parts = spentRange.split("-");
//                        spentMin = Long.parseLong(parts[0]);
//                        spentMax = Long.parseLong(parts[1]);
//                    } else if (spentRange.endsWith("+")) {
//                        spentMin = Long.parseLong(spentRange.replace("+", ""));
//                    }
                    break;
            }
        }

        // --- Parse Date to Timestamp ---
        Timestamp registerStartTS = null, registerEndTS = null;
        Timestamp bookingStartTS = null, bookingEndTS = null;

        try {
            if (registerStart != null && !registerStart.isEmpty()) {
                registerStartTS = Timestamp.valueOf(registerStart + " 00:00:00");
            }
            if (registerEnd != null && !registerEnd.isEmpty()) {
                registerEndTS = Timestamp.valueOf(registerEnd + " 23:59:59");
            }
            if (bookingStart != null && !bookingStart.isEmpty()) {
                bookingStartTS = Timestamp.valueOf(bookingStart + " 00:00:00");
            }
            if (bookingEnd != null && !bookingEnd.isEmpty()) {
                bookingEndTS = Timestamp.valueOf(bookingEnd + " 23:59:59");
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace(); 
        }

        ReportDAO dao = new ReportDAO();
        List<CustomerReport> customerReport = dao.getCustomerReportWithPaging(keyword, bookingMin, bookingMax, spentMin, spentMax, registerStartTS, registerEndTS, bookingStartTS, bookingEndTS, sort, order, offset, recordsPerPage);
        int totalRecords = dao.getCustomerReportCount(keyword, bookingMin, bookingMax, spentMin, spentMax, registerStartTS, registerEndTS, bookingStartTS, bookingEndTS);

        int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

        request.setAttribute("keyword", keyword);
        request.setAttribute("tier", tier);
        request.setAttribute("bookingRange", bookingRange);
        request.setAttribute("spentRange", spentRange);
        request.setAttribute("registerStartDate", registerStart);
        request.setAttribute("registerEndDate", registerEnd);
        request.setAttribute("bookingStartDate", bookingStart);
        request.setAttribute("bookingEndDate", bookingEnd);
        request.setAttribute("sort", sort);
        request.setAttribute("order", order);
        request.setAttribute("customerReport", customerReport);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalCustomer", totalRecords);

        request.getRequestDispatcher("customer-report.jsp").forward(request, response);

    }
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
