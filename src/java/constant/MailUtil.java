/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package constant;

import entity.BookingDetails;
import entity.BookingServices;
import entity.Invoice;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.UUID;

/**
 *
 * @author ASUS
 */
public class MailUtil {

    private MailUtil() {
    }

    private static final String EMAIL_CONFIG_EMAIL = "dominhdangcap2@gmail.com";
    private static final String PASS_CONFIG_EMAIL = "nmxb rkyw fwoe ashb";

    public static void send(String toEmail, String code) throws Exception {

        final String fromEmail = EMAIL_CONFIG_EMAIL;
        final String password = PASS_CONFIG_EMAIL;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(fromEmail, "Palatin Support", "UTF-8"));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        msg.setSubject(MimeUtility.encodeText("Mã xác minh tài khoản Palatin", "utf-8", "B"));
        msg.setContent("Mã xác minh tài khoản Palatin", "text/html; charset=UTF-8");
        msg.setHeader("Message-ID", "<" + UUID.randomUUID().toString() + "@palatin.vn>");
        String html = String.format("""
            <!DOCTYPE html>
            <html lang="vi">
            <head>
                <meta charset="UTF-8">
                <title>Xác minh tài khoản</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f6f8fa;
                        padding: 0;
                        margin: 0;
                    }
                    .container {
                        background-color: #ffffff;
                        max-width: 600px;
                        margin: 40px auto;
                        padding: 30px;
                        border-radius: 12px;
                        box-shadow: 0 4px 12px rgba(0,0,0,0.08);
                    }
                    .header {
                        text-align: center;
                        padding-bottom: 20px;
                    }
                    .header img {
                        width: 100px;
                    }
                    .code-box {
                        background-color: #f0f4ff;
                        color: #1a73e8;
                        font-size: 24px;
                        font-weight: bold;
                        text-align: center;
                        padding: 16px;
                        border-radius: 8px;
                        margin: 20px 0;
                    }
                    .footer {
                        font-size: 13px;
                        color: #6b7280;
                        margin-top: 30px;
                        text-align: center;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <img src="https://companieslogo.com/img/orig/PTN-435b2aff.png?t=1720244493" alt="Palatin Logo">
                        <h2>Chào mừng bạn đến với Palatin</h2>
                    </div>
                    <p>Xin chào,</p>
                    <p>Để hoàn tất việc đăng ký tài khoản Palatin, bạn vui lòng sử dụng mã xác minh bên dưới:</p>
                    <div class="code-box">%s</div>
                    <p>Mã có hiệu lực trong vòng <strong>1 phút</strong>. Vui lòng không chia sẻ mã này cho bất kỳ ai.</p>
                    <p>Trân trọng,<br>Đội ngũ Palatin</p>

                    <div class="footer">
                        Nếu bạn không yêu cầu đăng ký tài khoản, vui lòng bỏ qua email này.<br>
                        &copy; 2025 Palatin. All rights reserved.
                    </div>
                </div>
            </body>
        </html>
        """, code);
        msg.setContent(html, "text/html; charset=UTF-8");
        Transport.send(msg);
    }

    public static void sendInvoice(String toEmail, Invoice invoice) throws Exception {
        final String fromEmail = EMAIL_CONFIG_EMAIL;
        final String password = PASS_CONFIG_EMAIL;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(fromEmail, "Palatin Support", "UTF-8"));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        String subject = "Hóa đơn #" + invoice.getBooking().getBookingId() + " - " + System.currentTimeMillis();
        msg.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
//        msg.setContent("Hóa đơn thanh toán - Palatin", "text/html; charset=UTF-8");
        msg.setHeader("Message-ID", "<" + UUID.randomUUID().toString() + "@palatin.vn>");

        String html = buildInvoiceHtml(invoice);
        msg.setContent(html, "text/html; charset=UTF-8");
        Transport.send(msg);
    }

    private static String buildInvoiceHtml(Invoice invoice) {
        StringBuilder sb = new StringBuilder();

        sb.append("<html><head><style>")
                .append("body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; }")
                .append("h2, h3 { color: #2c3e50; }")
                .append("table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }")
                .append("table, th, td { border: 1px solid #ccc; }")
                .append("th, td { padding: 10px; text-align: left; }")
                .append("th { background-color: #ecf0f1; }")
                .append("ul { list-style-type: none; padding: 0; }")
                .append("li { margin-bottom: 5px; }")
                .append("</style></head><body>");

        sb.append("<h2>🧾 HÓA ĐƠN ĐẶT PHÒNG - PALATIN HOTEL</h2>");

        if (invoice.getUser() != null) {
            sb.append("<h3>👤 Thông tin khách hàng</h3><ul>")
                    .append("<li><strong>Họ tên:</strong> ").append(invoice.getUser().getFirstName())
                    .append(" ").append(invoice.getUser().getLastName()).append("</li>")
                    .append("<li><strong>Email:</strong> ").append(invoice.getUser().getEmail()).append("</li>")
                    .append("<li><strong>Số điện thoại:</strong> ").append(invoice.getUser().getPhone()).append("</li>")
                    .append("</ul>");
        } else {
            sb.append("<p><strong>❌ Không tìm thấy thông tin người dùng.</strong></p>");
        }

        if (invoice.getBooking() != null) {
            sb.append("<h3>🛏️ Thông tin đặt phòng</h3><ul>")
                    .append("<li><strong>Mã Booking:</strong> ").append(invoice.getBooking().getBookingId()).append("</li>")
                    .append("<li><strong>Ngày đặt:</strong> ").append(invoice.getBooking().getBookingDate()).append("</li>")
                    .append("<li><strong>Check-in:</strong> ").append(invoice.getBooking().getCheckInDate()).append("</li>")
                    .append("<li><strong>Check-out:</strong> ").append(invoice.getBooking().getCheckOutDate()).append("</li>")
                    .append("</ul>");
        } else {
            sb.append("<p><strong>❌ Không tìm thấy thông tin booking.</strong></p>");
        }

        BookingDetails bd = invoice.getBookingDetails();
        if (bd != null && bd.getRoom() != null) {
            sb.append("<h3>🏨 Chi tiết phòng</h3>")
                    .append("<table><thead><tr>")
                    .append("<th>Số phòng</th><th>Giá mỗi đêm</th><th>Số đêm</th>")
                    .append("</tr></thead><tbody><tr>")
                    .append("<td>").append(bd.getRoom().getRoomNumber()).append("</td>")
                    .append("<td>").append(bd.getRoom().getPrice()).append("</td>")
                    .append("<td>").append(bd.getNights()).append("</td>")
                    .append("</tr></tbody></table>");
        } else {
            sb.append("<p><strong>❌ Không có chi tiết phòng.</strong></p>");
        }

        if (invoice.getBookingServices() != null && !invoice.getBookingServices().isEmpty()) {
            sb.append("<h3>🛎️ Dịch vụ đã sử dụng</h3>")
                    .append("<table><thead><tr>")
                    .append("<th>Tên dịch vụ</th><th>Giá</th><th>Số lượng</th><th>Giá lúc sử dụng</th><th>Thời gian sử dụng</th>")
                    .append("</tr></thead><tbody>");

            for (BookingServices bs : invoice.getBookingServices()) {
                sb.append("<tr>")
                        .append("<td>").append(bs.getService().getName()).append("</td>")
                        .append("<td>").append(bs.getService().getPrice()).append("</td>")
                        .append("<td>").append(bs.getQuantity()).append("</td>")
                        .append("<td>").append(bs.getPriceAtUse()).append("</td>")
                        .append("<td>").append(bs.getUsedAt()).append("</td>")
                        .append("</tr>");
            }

            sb.append("</tbody></table>");
        } else {
            sb.append("<p><strong>❌ Không có dịch vụ nào được sử dụng.</strong></p>");
        }

        if (invoice.getBooking() != null && invoice.getBooking().getVoucher() != null) {
            sb.append("<h3>🎟️ Voucher áp dụng</h3><ul>")
                    .append("<li><strong>Mã voucher:</strong> ").append(invoice.getBooking().getVoucher().getCode()).append("</li>")
                    .append("<li><strong>Giảm giá:</strong> ").append(invoice.getBooking().getVoucher().getDiscountPercentage()).append("%</li>")
                    .append("<li><strong>Hiệu lực:</strong> ").append(invoice.getBooking().getVoucher().getValidFrom())
                    .append(" → ").append(invoice.getBooking().getVoucher().getValidTo()).append("</li>")
                    .append("</ul>");
        } else {
            sb.append("<p><strong>❌ Không có voucher nào được sử dụng.</strong></p>");
        }

        if (invoice.getPayment() != null) {
            sb.append("<h3>💳 Thanh toán</h3><ul>")
                    .append("<li><strong>Phương thức:</strong> ").append(invoice.getPayment().getMethod()).append("</li>")
                    .append("<li><strong>Mã giao dịch:</strong> ").append(invoice.getPayment().getTransactionCode()).append("</li>")
                    .append("<li><strong>Ngân hàng:</strong> ").append(invoice.getPayment().getBankCode()).append("</li>")
                    .append("<li><strong>Tổng thanh toán:</strong> ").append(invoice.getPayment().getAmount()).append(" VND</li>")
                    .append("</ul>");
        } else {
            sb.append("<p><strong>❌ Không có thông tin thanh toán.</strong></p>");
        }

        sb.append("<p>Trân trọng,</p>")
                .append("<p><strong>Palatin Hotel</strong></p>");

        sb.append("</body></html>");
        return sb.toString();
    }

}
