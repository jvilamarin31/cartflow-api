package com.cartflow.backend.common.notification;

import com.cartflow.backend.common.exceptions.EmailSendException;
import com.cartflow.backend.user.entity.UserEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${app.email.from}")
    private String fromEmail;

     // URL base del enlace de verificación.
    @Value("${app.verification.url}")
    private String verificationBaseUrl;

    @Value("${app.email.verification.expiration-hours}")
    private long expirationHours;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(UserEntity user, String token) {
        String verificationLink = verificationBaseUrl + "?token=" + token;
        String subject = "Verifica tu cuenta de CartFlow";
        String body = buildVerificationEmailBody(user.getName(), verificationLink, expirationHours);
        sendHtmlEmail(user.getEmail(), subject, body);
    }

    private void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailSendException("Error al enviar el correo a " + to, e);
        }
    }

    private String buildVerificationEmailBody(String userName, String verificationLink, long hours) {
        return """
            <html>
                <body style="font-family: Arial, sans-serif; max-width: 600px; margin: auto;">
                    <h2>¡Hola, %s!</h2>
                    <p>Gracias por registrarte en CartFlow.</p>
                    <p>Para activar tu cuenta, haz clic en el siguiente enlace:</p>
                    <p style="text-align: center; margin: 30px 0;">
                        <a href="%s"
                           style="background-color: #4CAF50; color: white; padding: 12px 24px;
                                  text-decoration: none; border-radius: 5px; font-weight: bold;">
                            Verificar mi cuenta
                        </a>
                    </p>
                    <p>Este enlace expira en %d horas.</p>
                    <p>Si no creaste esta cuenta, ignora este mensaje.</p>
                    <br>
                    <p>Saludos,<br>El equipo de CartFlow</p>
                </body>
            </html>
            """.formatted(userName, verificationLink, hours);
    }
}
