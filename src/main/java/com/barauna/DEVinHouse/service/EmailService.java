package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);

    private MailSender mailSender;
    private String sender;

    public EmailService(@Value("${spring.mail.default.sender}") String sender, MailSender mailSender) {
        this.sender = sender;
        this.mailSender = mailSender;
    }

    public void sendNewPassword(String email, String newPass) throws Exception {
        validate(email, newPass);
        SimpleMailMessage sm = prepareNewPasswordEmail(email, newPass);
        sendEmail(sm);
    }

    public void sendEmail(SimpleMailMessage message) {
        LOG.info("Enviando email...");
        mailSender.send(message);
        LOG.info("Email enviado");
    }

    private SimpleMailMessage prepareNewPasswordEmail(String email, String newPass) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(email);
        sm.setFrom(sender);
        sm.setSubject("Solicitação de nova senha");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Nova senha: " + newPass);
        return sm;
    }

    private void validate(String email, String password) throws Exception {

        if(email == null) {
            throw new Exception("Invalid argument. Email cannot be null");
        }

        if(email.isEmpty()) {
            throw new Exception("Invalid argument. Email cannot be empty");
        }

        if(!UserUtils.isValidUsername(email)) {
            throw new Exception("Invalid email");
        }

        if(password == null) {
            throw new Exception("Invalid argument. Password cannot be null");
        }

        if(password.isEmpty()) {
            throw new Exception("Invalid argument. Password cannot be empty");
        }
    }
}
