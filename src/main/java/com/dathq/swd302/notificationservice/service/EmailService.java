package com.dathq.swd302.notificationservice.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author matve
 */

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Async // Gửi mail bất đồng bộ để không làm chậm luồng chính
    public void sendHtmlEmail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // true = gửi dưới dạng HTML

            mailSender.send(message);
            System.out.println("Email đã được gửi thành công tới: " + to);
        } catch (MessagingException e) {
            System.err.println("Lỗi gửi mail: " + e.getMessage());
        }
    }
}
