package com.clinic.dentistry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Service
public class MailService {
    public static final String EMAIL_FROM = "dentistry.samsonenko@mail.ru";

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendNotification(String text, String EMAIL_TO) throws MailException {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(EMAIL_FROM);
        mail.setTo(EMAIL_TO);
        mail.setSubject(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(LocalDateTime.now())
        );

        mail.setText(text);

        javaMailSender.send(mail);
    }
}