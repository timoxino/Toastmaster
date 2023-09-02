package com.timoxino.interview.toastmaster.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {

    @Mock
    JavaMailSender sender;

    @InjectMocks
    EmailServiceImpl emailService;

    @Captor
    ArgumentCaptor<SimpleMailMessage> messageCaptor;

    @Test
    void sendEmail() {
        doNothing().when(sender).send(messageCaptor.capture());

        emailService.sendEmail("from", "to", "subject", "text");

        verify(sender).send(any(SimpleMailMessage.class));
        assertEquals("from", messageCaptor.getValue().getFrom(), "Unexpected value in from field");
        assertEquals("to", messageCaptor.getValue().getTo()[0], "Unexpected value in to field");
        assertEquals("subject", messageCaptor.getValue().getSubject(), "Unexpected value in subject field");
        assertEquals("text", messageCaptor.getValue().getText(), "Unexpected value in text field");
    }
}
