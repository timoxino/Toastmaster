package com.timoxino.interview.toastmaster.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.timoxino.interview.toastmaster.dto.CvProcessingRequest;

@ExtendWith(MockitoExtension.class)
public class OrchestrationServiceTest {

    @Mock
    EmailService emailService;

    @Mock
    StorageService storageService;

    @InjectMocks
    OrchestrationService orchestrationService;

    @Test
    void processQuestionsMessage() {
        orchestrationService.from = "from";
        orchestrationService.to = "to";

        orchestrationService.processQuestionsMessage(null);

        verify(emailService).sendEmail("from", "to", "Questions are ready!", "questions");
    }

    @Test
    void processCvRequest() throws IOException {
        CvProcessingRequest request = new CvProcessingRequest();
        request.setCvContent("cv");

        String response = orchestrationService.processCvRequest(request);

        verify(storageService).writeCvFile(anyString(), eq("cv"));
        assertNotNull(response);
        assertTrue(response.endsWith(".txt"), "File name must end with .txt");
    }
}
