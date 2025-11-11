package service;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {

    // Đọc cấu hình từ biến môi trường để tránh hardcode secret
    private static final String SMTP_HOST = System.getenv().getOrDefault("SMTP_HOST", "smtp.gmail.com");
    private static final String SMTP_PORT = System.getenv().getOrDefault("SMTP_PORT", "587");
    private static final String SMTP_USER = System.getenv("SMTP_USER");
    private static final String SMTP_PASS = System.getenv("SMTP_PASS");
    private static final String FROM_NAME = System.getenv().getOrDefault("FROM_NAME", "Group6_VMS");
    private static final String FROM_EMAIL = System.getenv().getOrDefault("FROM_EMAIL", SMTP_USER != null ? SMTP_USER : "");

    // Gửi email text đơn giản. Trả về true nếu gửi thành công, false nếu lỗi.
    public static boolean sendEmail(String to, String subject, String body) throws MessagingException {
        if (SMTP_USER == null || SMTP_PASS == null || FROM_EMAIL.isEmpty()) {
            throw new MessagingException("SMTP credentials are not configured (SMTP_USER/SMTP_PASS/FROM_EMAIL)");
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER, SMTP_PASS);
            }
        });

        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(FROM_EMAIL, FROM_NAME));
        } catch (java.io.UnsupportedEncodingException e) {
            message.setFrom(new InternetAddress(FROM_EMAIL));
        }
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
        return true;
    }
}