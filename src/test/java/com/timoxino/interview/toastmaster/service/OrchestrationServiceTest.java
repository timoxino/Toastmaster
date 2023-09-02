package com.timoxino.interview.toastmaster.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrchestrationServiceTest {

    @Mock
    EmailService emailService;

    @InjectMocks
    OrchestrationService orchestrationService;

    @Test
    void processQuestionsMessage() {
        orchestrationService.from = "from";
        orchestrationService.to = "to";

        orchestrationService.processQuestionsMessage(null);

        verify(emailService).sendEmail("from", "to", "Questions are ready!", "questions");
    }
}
