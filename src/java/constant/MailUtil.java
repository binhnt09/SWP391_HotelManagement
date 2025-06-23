/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package constant;

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

    public static void send(String toEmail, String code) throws Exception {
        final String fromEmail = "dominhdangcap2@gmail.com";
        final String password = "nmxb rkyw fwoe ashb";      // App password

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
}
