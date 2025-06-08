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

    public static void send(String toEmail, String code) throws Exception {
        final String fromEmail = "";
        final String password = "";     // App password

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
        String html = "Xin chào,<br><br>Mã xác minh email của bạn là: <b>" + code + "</b><br>Mã có hiệu lực trong 1 phút.<br><br>Trân trọng!";
        msg.setContent(html, "text/html; charset=UTF-8");

        Transport.send(msg);
    }
}
