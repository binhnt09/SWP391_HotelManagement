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
        final String password = PASS_CONFIG_EMAIL;     // App password

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
        msg.setSubject(MimeUtility.encodeText("Thông tin chi tiết booking và hóa đơn", "utf-8", "B"));
        msg.setContent("Hóa đơn thanh toán - Palatin", "text/html; charset=UTF-8");
        String html = buildInvoiceHtml(invoice);
        msg.setContent(html, "text/html; charset=UTF-8");
        Transport.send(msg);
    }

    private static String buildInvoiceHtml(Invoice invoice) {
        StringBuilder sb = new StringBuilder();

        sb.append("<h2>Hóa đơn đặt phòng - Palatin Hotel</h2>");
        if (invoice.getUser() != null) {
            sb.append("<p>Xin chào <strong>").append(invoice.getUser().getFirstName())
                    .append(invoice.getUser().getLastName()).append("</strong>,</p>");
            sb.append("<p>Email: <strong>").append(invoice.getUser().getEmail()).append("</strong>,</p>");
            sb.append("<p>Phone: <strong>").append(invoice.getUser().getPhone()).append("</strong>,</p>");
        } else {
            sb.append("<p><strong>Không tìm thấy thông tin người dùng.</strong></p>");
        }

        if (invoice.getBooking() != null) {
            sb.append("<h3>Thông tin đặt phòng</h3><ul>");
            sb.append("<li>Mã Booking: ").append(invoice.getBooking().getBookingId()).append("</li>");
            sb.append("<li>Ngày đặt: ").append(invoice.getBooking().getBookingDate()).append("</li>");
            sb.append("<li>Check-in: ").append(invoice.getBooking().getCheckInDate()).append("</li>");
            sb.append("<li>Check-out: ").append(invoice.getBooking().getCheckOutDate()).append("</li></ul>");
        } else {
            sb.append("<p><strong>Không tìm thấy thông tin booking.</strong></p>");
        }

        BookingDetails bd = invoice.getBookingDetails();
        if (bd != null && bd.getRoom() != null) {
            sb.append("<h3>Chi tiết phòng</h3><table border='1'><tr><th>Phòng</th><th>Giá</th><th>Số đêm</th></tr>");
            sb.append("<tr>")
                    .append("<td>").append(bd.getRoom().getRoomNumber()).append("</td>")
                    .append("<td>").append(bd.getRoom().getPrice()).append("</td>")
                    .append("<td>").append(bd.getNights()).append("</td>")
                    .append("</tr>");
            sb.append("</table>");
        } else {
            sb.append("<p><strong>Không có chi tiết phòng.</strong></p>");
        }

        if (invoice.getBookingServices() != null && !invoice.getBookingServices().isEmpty()) {
            sb.append("<h3>Thông tin dịch vụ đã sử dụng</h3><ul>");
            for (BookingServices bs : invoice.getBookingServices()) {
                sb.append("<li>Mã dịch vụ: ").append(bs.getService().getServiceId()).append("</li>");
                sb.append("<li>Tên dịch vụ: ").append(bs.getService().getName()).append("</li>");
                sb.append("<li>Price: ").append(bs.getService().getPrice()).append("</li>");
                sb.append("<li>BookingServiceId: ").append(bs.getBookingServiceId()).append("</li>");
                sb.append("<li>Số lượng: ").append(bs.getQuantity()).append("</li></ul>");
                sb.append("<li>PriceUse: ").append(bs.getPriceAtUse()).append("</li></ul>");
                sb.append("<li>UseAt: ").append(bs.getUsedAt()).append("</li></ul>");
            }
        } else {
            sb.append("<p><strong>Không có dịch vụ nào được sử dụng.</strong></p>");
        }

        if (invoice.getBooking() != null && invoice.getBooking().getVoucher() != null) {
            sb.append("<h3>Voucher</h3><ul>");
            sb.append("<h3>Thông tin đặt phòng</h3><ul>");
            sb.append("<li>VoucherId: ").append(invoice.getBooking().getVoucher().getVoucherId()).append("</li>");
            sb.append("<li>Mã voucher: ").append(invoice.getBooking().getVoucher().getCode()).append("</li>");
            sb.append("<li>Giảm giá: ").append(invoice.getBooking().getVoucher().getDiscountPercentage()).append("</li>");
            sb.append("<li>Có hiệu lực từ: ").append(invoice.getBooking().getVoucher().getValidFrom()).append("</li></ul>");
            sb.append("<li>Ngày hết hạn: ").append(invoice.getBooking().getVoucher().getValidTo()).append("</li></ul>");
        } else {
            sb.append("<p><strong>Không có voucher nào được sử dụng.</strong></p>");
        }

        if (invoice.getPayment()!= null) {
            sb.append("<h3>Thông tin thanh toán</h3><ul>");
            sb.append("<li>Phương thức: ").append(invoice.getPayment().getPaymentID()).append("</li>");
            sb.append("<li>Phương thức: ").append(invoice.getPayment().getMethod()).append("</li>");
            sb.append("<li>Mã giao dịch: ").append(invoice.getPayment().getTransactionCode()).append("</li>");
            sb.append("<li>Ngân hàng: ").append(invoice.getPayment().getBankCode()).append("</li>");
            sb.append("<li>Tổng thanh toán: ").append(invoice.getPayment().getAmount()).append(" VND</li></ul>");
        } else {
            sb.append("<p><strong>Không có payment.</strong></p>");
        }

        sb.append("<p>Trân trọng,</p><p><strong>Palatin Hotel</strong></p>");

        return sb.toString();
    }
}
