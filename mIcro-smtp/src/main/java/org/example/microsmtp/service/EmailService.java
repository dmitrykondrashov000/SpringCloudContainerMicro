package org.example.microsmtp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


//    Для SMTP
//    docker run -d --name mailhog -p 1025:1025 -p 8025:8025  mailhog/mailhog
//http://localhost:8025
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendAccountCreated(String email) {
        send(email, "Аккаунт создан", "Здравствуйте! Ваш аккаунт успешно создан.");
    }

    public void sendAccountDeleted(String email) {
        send(email, "Аккаунт удалён", "Здравствуйте! Ваш аккаунт был удалён.");
    }

    private void send(String to, String subject, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(message);
        mailSender.send(mail);
        System.out.println("svsfsfsfsfsfs");
    }

}
