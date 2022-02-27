package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

@Service
public class EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);
    private JavaMailSender mailSender;
    private String sender;

    public EmailService(@Value("${spring.mail.default.sender}") String sender, JavaMailSender javaMailSender) {
        this.sender = sender;
        this.mailSender = javaMailSender;
    }

    public void sendNewPassword(String email, String newPass) throws Exception {
        validate(email, newPass);
        SimpleMailMessage sm = prepareNewPasswordEmail(email, newPass);
        sendEmail(sm);
    }


    public void send(String email, String title, String message) throws Exception {
        validate(email);
        SimpleMailMessage sm = prepareEmail(email);
        sm.setSubject(title);
        sm.setText(message);
        sendEmail(sm);
    }

    private void sendEmail(SimpleMailMessage message) {
        LOG.info("Enviando email...");
        mailSender.send(message);
        LOG.info("Email enviado");
    }

    private SimpleMailMessage prepareNewPasswordEmail(String email, String newPass) {
        SimpleMailMessage sm = prepareEmail(email);
        sm.setFrom(sender);
        sm.setSubject("Solicitação de nova senha");
        sm.setText("Nova senha: " + newPass);
        return sm;
    }

    private SimpleMailMessage prepareEmail(String email) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(email);
        sm.setSentDate(new Date(System.currentTimeMillis()));
        return sm;
    }

    public void sendEmailWithAttachment(String email, String title, String message, String fileName) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(email);
        helper.setSubject(title);
        helper.setText(message);

        String filePath = new File(fileName).getAbsolutePath();
        FileSystemResource file = new FileSystemResource(filePath);

        helper.addAttachment(fileName, file);

        mailSender.send(mimeMessage);
    }

    private void validate(String email) throws Exception {
        if(email == null) {
            throw new Exception("Invalid argument. Email cannot be null");
        }

        if(email.isEmpty()) {
            throw new Exception("Invalid argument. Email cannot be empty");
        }

        if(!UserUtils.isValidUsername(email)) {
            throw new Exception("Invalid email");
        }
    }

    private void validate(String email, String password) throws Exception {
        validate(email);

        if(password == null) {
            throw new Exception("Invalid argument. Password cannot be null");
        }

        if(password.isEmpty()) {
            throw new Exception("Invalid argument. Password cannot be empty");
        }
    }
}
