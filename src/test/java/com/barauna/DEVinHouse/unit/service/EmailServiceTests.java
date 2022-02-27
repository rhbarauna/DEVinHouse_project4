package com.barauna.DEVinHouse.unit.service;

import com.barauna.DEVinHouse.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmailServiceTests {

    private String sender;
    private EmailService emailService;
    private JavaMailSender mailSender;

    @BeforeEach
    public void beforeEach(){
        this.sender = "testSender@test.com";
        this.mailSender = mock(JavaMailSender.class);
        this.emailService = new EmailService(sender, mailSender);
    }

    @Test
    public void testSendNewPasswordEmail() {
        SimpleMailMessage message = mock(SimpleMailMessage.class);
        doNothing().when(mailSender).send(message);
        assertDoesNotThrow(() -> emailService.sendNewPassword("teste@teste.com", "abc"));
    }

    @Test
    public void expectErrorOnInvalidEmailOrInvalidPassword() {
        final Exception exception1 = assertThrows(Exception.class, () -> emailService.sendNewPassword(null, ""));
        final Exception exception2 = assertThrows(Exception.class, () -> emailService.sendNewPassword("", ""));
        final Exception exception3 = assertThrows(Exception.class, () -> emailService.sendNewPassword("teste@email.1com", ""));
        final Exception exception4 = assertThrows(Exception.class, () -> emailService.sendNewPassword("teste@.com", ""));
        final Exception exception5 = assertThrows(Exception.class, () -> emailService.sendNewPassword("teste@email", ""));
        final Exception exception6 = assertThrows(Exception.class, () -> emailService.sendNewPassword("1@ email.com", ""));
        final Exception exception7 = assertThrows(Exception.class, () -> emailService.sendNewPassword(" t@email.com", ""));

        assertEquals("Invalid argument. Email cannot be null", exception1.getMessage());
        assertEquals("Invalid argument. Email cannot be empty", exception2.getMessage());
        assertEquals("Invalid email", exception3.getMessage());
        assertEquals("Invalid email", exception4.getMessage());
        assertEquals("Invalid email", exception5.getMessage());
        assertEquals("Invalid email", exception6.getMessage());
        assertEquals("Invalid email", exception7.getMessage());

        final Exception exception8 = assertThrows(Exception.class, () -> emailService.sendNewPassword("teste@email.com", null));
        final Exception exception9 = assertThrows(Exception.class, () -> emailService.sendNewPassword("teste@email.com", ""));


        assertEquals("Invalid argument. Password cannot be null", exception8.getMessage());
        assertEquals("Invalid argument. Password cannot be empty", exception9.getMessage());
    }
}
