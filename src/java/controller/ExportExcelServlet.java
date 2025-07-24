/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ReportDAO;
import entity.CustomerReport;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author viet7
 */
@WebServlet(name = "ExportExcelServlet", urlPatterns = {"/exportExcel"})
public class ExportExcelServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ExportExcelServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExportExcelServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        List<CustomerReport> reportList = dao.exportCustomerReport(keyword, bookingMin, bookingMax, spentMin, spentMax, registerStartTS, registerEndTS, bookingStartTS, bookingEndTS, sort, order);

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Customer Report");

        Row header = sheet.createRow(0);
        String[] columns = {"User ID", "First Name", "Last Name", "Email", "Total Bookings", "Total Spent", "Last Booking", "Register Date", "Tier"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columns[i]);
        }

        int rowIndex = 1;
        for (CustomerReport c : reportList) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(c.getUserId());
            row.createCell(1).setCellValue(c.getFirstName());
            row.createCell(2).setCellValue(c.getLastName());
            row.createCell(3).setCellValue(c.getEmail());
            row.createCell(4).setCellValue(c.getTotalBookings());
            row.createCell(5).setCellValue(c.getTotalPaid() != null ? c.getTotalPaid().doubleValue() : 0.0);
            row.createCell(6).setCellValue(c.getLastBookingDate() != null ? c.getLastBookingDate().toString() : "N/A");
            row.createCell(7).setCellValue(c.getRegisterDate() != null ? c.getRegisterDate().toString() : "N/A");
            row.createCell(8).setCellValue(c.getTier());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=CustomerReport.xlsx");

        OutputStream os = response.getOutputStream();
        workbook.write(os);
        workbook.close();
        os.close();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
