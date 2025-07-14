/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package constant;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import entity.Invoice;
import entity.InvoiceServiceDetail;
import jakarta.servlet.ServletContext;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 *
 * @author ASUS
 */
public class PdfGenerator {

    public static String getInvoiceFolderPath(ServletContext context) {
        String path = context.getRealPath("/invoices");
        if (path == null) {
            throw new IllegalStateException("Không thể xác định đường dẫn thư mục invoices.");
        }

        File folder = new File(path);
        if (!folder.exists() && !folder.mkdirs()) {
            throw new IllegalStateException("Không thể tạo thư mục invoices tại: " + path);
        }

        return path;
    }

    public static String getInvoiceFolderPathStatic() {
        String path = "D:/FU Learning/Summer 2025/SWP391/SWP_HotelManagement_V1_1/SWP391_HotelManagement_V1/web/invoices";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return path;
    }

    public static void generateInvoicePdf(Invoice invoice, String outputPath) throws IOException {
        try (Document document = new Document()) {
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            // Load font Unicode (ví dụ Arial hỗ trợ tiếng Việt)
//            BaseFont bf = BaseFont.createFont("fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
            Font boldFont = new Font(Font.HELVETICA, 12, Font.BOLD);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            // === HEADER ===
            Paragraph header = new Paragraph("HÓA ĐƠN THANH TOÁN - PALATIN HOTEL", titleFont);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            document.add(new Paragraph(" "));

            // === THÔNG TIN BOOKING ===
            document.add(new Paragraph("Thông tin Booking:", boldFont));
            document.add(new Paragraph("Mã Booking: " + invoice.getBookingId(), normalFont));
            document.add(new Paragraph("Check-In: " + invoice.getPayment().getBooking().getCheckInDate(), normalFont));
            document.add(new Paragraph("Check-Out: " + invoice.getPayment().getBooking().getCheckOutDate(), normalFont));
            document.add(new Paragraph("Ngày booking: " + invoice.getPayment().getBooking().getBookingDate(), normalFont));
            document.add(new Paragraph(" "));

            // === THÔNG TIN KHÁCH HÀNG ===
            document.add(new Paragraph("Thông tin khách hàng:", boldFont));
            document.add(new Paragraph("Họ Tên: " + invoice.getFirstName() + " " + invoice.getLastName(), normalFont));
            document.add(new Paragraph("Email: " + invoice.getEmail(), normalFont));
            document.add(new Paragraph("Số điện thoại: " + invoice.getPhone(), normalFont));
            document.add(new Paragraph("Địa chỉ: " + invoice.getAddress(), normalFont));
            document.add(new Paragraph(" "));

            // === THÔNG TIN PHÒNG ===
            document.add(new Paragraph("Thông tin phòng:", boldFont));
            document.add(new Paragraph("Phòng: " + invoice.getRoomNumber(), normalFont));
            document.add(new Paragraph("Giá phòng: " + invoice.getRoomPrice() + " VND", normalFont));
            document.add(new Paragraph("Số đêm: " + invoice.getNights(), normalFont));
            document.add(new Paragraph("Voucher giảm giá: " + invoice.getDiscountAmount() + "%", normalFont));
            document.add(new Paragraph("Tổng tiền phòng: " + invoice.getTotalRoomPrice() + " VND", normalFont));
            document.add(new Paragraph(" "));

            // === DỊCH VỤ SỬ DỤNG ===
            if (invoice.getListService() != null && !invoice.getListService().isEmpty()) {
                document.add(new Paragraph("Dịch vụ sử dụng:", boldFont));
                PdfPTable serviceTable = new PdfPTable(4);
                serviceTable.setWidthPercentage(100);
                serviceTable.setWidths(new float[]{40, 20, 20, 20});

                serviceTable.addCell(new PdfPCell(new Phrase("Tên dịch vụ", boldFont)));
                serviceTable.addCell(new PdfPCell(new Phrase("Đơn giá", boldFont)));
                serviceTable.addCell(new PdfPCell(new Phrase("Số lượng", boldFont)));
                serviceTable.addCell(new PdfPCell(new Phrase("Thành tiền", boldFont)));

                for (InvoiceServiceDetail service : invoice.getListService()) {
                    serviceTable.addCell(new Phrase(service.getServiceName(), normalFont));
                    serviceTable.addCell(new Phrase(service.getPrice() + " VND", normalFont));
                    serviceTable.addCell(new Phrase(String.valueOf(service.getQuantity()), normalFont));
                    serviceTable.addCell(new Phrase(service.getPriceAtUse() + " VND", normalFont));
                }

                document.add(serviceTable);
                document.add(new Phrase(" "));
            }

            // === VOUCHER ===
            if (invoice.getVoucherCode() != null && !invoice.getVoucherCode().isEmpty()) {
                document.add(new Paragraph("Voucher áp dụng: " + invoice.getVoucherCode(), normalFont));
                document.add(new Paragraph("Giảm giá: " + invoice.getDiscountAmount() + "%", normalFont));
                document.add(new Paragraph(" "));
            }

            // === THANH TOÁN ===
            document.add(new Paragraph("Thanh toán:", boldFont));
            document.add(new Paragraph("Tổng tiền cần thanh toán: " + invoice.getPayment().getAmount() + " VND", normalFont));
            document.add(new Paragraph("Phương thức thanh toán: " + invoice.getPayment().getMethod(), normalFont));
            document.add(new Paragraph("Mã giao dịch: " + invoice.getPayment().getTransactionCode(), normalFont));
            document.add(new Paragraph("Ngân hàng: " + invoice.getPayment().getBankCode(), normalFont));
            document.add(new Paragraph("Trạng thái: " + invoice.getStatus(), normalFont));

            document.add(new Paragraph("Ngày xuất hóa đơn: " + sdf.format(invoice.getIssueDate()), normalFont));
        }
    }
}