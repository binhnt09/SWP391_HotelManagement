/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package constant;

import entity.Invoice;
import entity.InvoiceServiceDetail;
import java.sql.Date;
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
        String subject = "Hóa đơn #" + invoice.getBookingId() + " - " + System.currentTimeMillis();
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

        if (invoice != null) {
            sb.append("<h3>👤 Thông tin khách hàng</h3><ul>")
                    .append("<li><strong>Họ tên:</strong> ").append(invoice.getFirstName())
                    .append(" ").append(invoice.getLastName()).append("</li>")
                    .append("<li><strong>Email:</strong> ").append(invoice.getEmail()).append("</li>")
                    .append("<li><strong>Số điện thoại:</strong> ").append(invoice.getPhone()).append("</li>")
                    .append("<li><strong>Địa chỉ:</strong> ").append(invoice.getAddress()).append("</li>")
                    .append("</ul>");

            sb.append("<h3>🛏️ Thông tin đặt phòng</h3><ul>")
                    .append("<li><strong>Mã Booking:</strong> ").append(invoice.getBookingId()).append("</li>")
                    .append("<li><strong>Ngày đặt:</strong> ").append(invoice.getPayment().getBooking().getBookingDate()).append("</li>")
                    .append("<li><strong>Check-in:</strong> ").append(invoice.getPayment().getBooking().getCheckInDate()).append("</li>")
                    .append("<li><strong>Check-out:</strong> ").append(invoice.getPayment().getBooking().getCheckOutDate()).append("</li>")
                    .append("</ul>");

            sb.append("<h3>🏨 Chi tiết phòng</h3>")
                    .append("<table><thead><tr>")
                    .append("<th>Số phòng</th><th>Giá mỗi đêm</th><th>Số đêm</th><th>Voucher giảm giá</th><th>Tổng tiền</th>")
                    .append("</tr></thead><tbody><tr>")
                    .append("<td>").append(invoice.getRoomNumber()).append("</td>")
                    .append("<td>").append(invoice.getRoomPrice()).append("</td>")
                    .append("<td>").append(invoice.getNights()).append("</td>")
                    .append("<td>").append(invoice.getDiscountAmount()).append("%</td>")
                    .append("<td>").append(invoice.getTotalRoomPrice()).append("</td>")
                    .append("</tr></tbody></table>");
        } else {
            sb.append("<p><strong>❌ Không tìm thấy thông tin người dùng.</strong></p>");
        }

        if (invoice.getListService() != null && !invoice.getListService().isEmpty()) {
            sb.append("<h3>🛎️ Dịch vụ đã sử dụng</h3>")
                    .append("<table><thead><tr>")
                    .append("<th>Tên dịch vụ</th><th>Giá</th><th>Số lượng</th><th>Giá lúc sử dụng</th><th>Thời gian sử dụng</th>")
                    .append("</tr></thead><tbody>");

            for (InvoiceServiceDetail bs : invoice.getListService()) {
                sb.append("<tr>")
                        .append("<td>").append(bs.getServiceName()).append("</td>")
                        .append("<td>").append(bs.getPrice()).append("</td>")
                        .append("<td>").append(bs.getQuantity()).append("</td>")
                        .append("<td>").append(bs.getPriceAtUse()).append("</td>")
                        .append("<td>").append(bs.getUsedAt()).append("</td>")
                        .append("</tr>");
            }

            sb.append("</tbody></table>");
        } else {
            sb.append("<p><strong>❌ Không có dịch vụ nào được sử dụng.</strong></p>");
        }

        if (invoice.getVoucherCode() != null && !invoice.getVoucherCode().isEmpty()) {
            sb.append("<h3>🎟️ Voucher áp dụng</h3><ul>")
                    .append("<li><strong>Mã voucher:</strong> ").append(invoice.getVoucherCode()).append("</li>")
                    .append("<li><strong>Giảm giá:</strong> ").append(invoice.getDiscountAmount()).append("%</li>")
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

        sb.append("<p>Trân trọng,</p>").append("<p><strong>Palatin Hotel</strong> Ngày tạo hóa đơn: ")
                .append(invoice.getIssueDate()).append("</p>");

        sb.append("</body></html>");
        return sb.toString();
    }

    public static void sendVoucherByEmail(String toEmail, String voucherCode, double discount, Date validFrom, Date validTo) throws Exception {
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
        String subject = "Voucher Code #" + voucherCode;
        msg.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
//        msg.setContent("Hóa đơn thanh toán - Palatin", "text/html; charset=UTF-8");
        msg.setHeader("Message-ID", "<" + UUID.randomUUID().toString() + "@palatin.vn>");

        String html = buildVoucherEmailHtml(voucherCode, discount, validFrom, validTo);
        msg.setContent(html, "text/html; charset=UTF-8");
        Transport.send(msg);
    }

    private static String buildVoucherEmailHtml(String voucherCode, double discount, Date validFrom, Date validTo) {
        StringBuilder sb = new StringBuilder();

        sb.append("<html><head><style>")
                .append("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f2f2f2; padding: 20px; margin: 0; }")
                .append(".container { max-width: 600px; background-color: #ffffff; padding: 30px; margin: 0 auto; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }")
                .append(".header { text-align: center; }")
                .append(".header h2 { color: #27ae60; margin-bottom: 10px; }")
                .append(".voucher-box { border: 2px dashed #27ae60; padding: 20px; border-radius: 8px; margin: 20px 0; background-color: #eafaf1; }")
                .append(".voucher-code { font-size: 24px; font-weight: bold; color: #2c3e50; letter-spacing: 2px; }")
                .append(".details { margin-top: 15px; }")
                .append(".details li { margin-bottom: 8px; font-size: 16px; color: #34495e; }")
                .append(".cta { display: block; width: fit-content; background-color: #27ae60; color: white; text-decoration: none; padding: 12px 20px; border-radius: 5px; margin: 20px auto 0; text-align: center; font-weight: bold; }")
                .append(".footer { text-align: center; margin-top: 30px; font-size: 14px; color: #7f8c8d; }")
                .append("</style></head><body>");

        sb.append("<div class='container'>")
                .append("<div class='header'>")
                .append("<h2>🎁 Voucher Ưu Đãi Đặc Biệt Từ Palatin Hotel</h2>")
                .append("<p>Cảm ơn bạn đã đồng hành cùng chúng tôi!</p>")
                .append("<p>Truy cập ngay website để nhận voucher liền tay: </p>")
                .append("<a href='http://localhost:8080/swp391_hotelmanagement/voucher' style='color:#27ae60; text-decoration:none;'>Nhận </a></p>")
                .append("</div>");

        if (voucherCode != null) {
            sb.append("<div class='voucher-box'>")
                    .append("<div class='voucher-code'>").append(voucherCode).append("</div>")
                    .append("<ul class='details'>")
                    .append("<li>🔻 <strong>Giảm giá:</strong> ").append(discount).append("%</li>")
                    .append("<li>📅 <strong>Hiệu lực từ:</strong> ").append(validFrom).append("</li>")
                    .append("<li>⏳ <strong>Đến hết ngày:</strong> ").append(validTo).append("</li>")
                    .append("</ul>")
                    .append("</div>")
                    .append("<a href='http://localhost:8080/swp391_hotelmanagement/searchroom' class='cta'>Đặt phòng ngay & áp dụng voucher</a>");
        } else {
            sb.append("<p style='color: red; text-align: center;'><strong>❌ Không có voucher nào được áp dụng.</strong></p>");
        }

        sb.append("<div class='footer'>")
                .append("📞 Mọi thắc mắc vui lòng liên hệ: 0123-456-789 hoặc <a href='mailto:support@palatinhotel.com'>support@palatinhotel.com</a>")
                .append("</div>");

        sb.append("</div></body></html>");
        return sb.toString();
    }

}
