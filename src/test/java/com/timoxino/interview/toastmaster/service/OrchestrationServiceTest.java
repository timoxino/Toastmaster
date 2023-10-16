package com.timoxino.interview.toastmaster.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.timoxino.interview.shared.dto.CandidateBaseMessage;
import com.timoxino.interview.shared.dto.CandidateQuestionsMessage;
import com.timoxino.interview.toastmaster.dto.CvProcessingRequest;
import com.timoxino.interview.toastmaster.spring.PubSubSenderConfiguration.PubSubCvGateway;

@ExtendWith(MockitoExtension.class)
public class OrchestrationServiceTest {

    @Mock
    EmailService emailService;

    @Mock
    StorageService storageService;

    @Mock
    PubSubCvGateway pubSubCvGateway;

    @InjectMocks
    OrchestrationService orchestrationService;

    @Captor
    ArgumentCaptor<CandidateBaseMessage> baseMessageCaptor;

    @Test
    void processQuestionsMessage() {
        orchestrationService.from = "from";
        orchestrationService.to = "to";

        orchestrationService.processQuestionsMessage(new CandidateQuestionsMessage());
        verify(emailService).sendEmail(eq("from"), eq("to"), eq("Questions are ready!"), anyString());
    }

    @Test
    void processCvRequest() throws IOException {
        CvProcessingRequest request = new CvProcessingRequest();
        request.setCvContent("cv");
        request.setLevelExpected(3);
        request.setRoleName("Project Manager");

        String response = orchestrationService.processCvRequest(request);

        verify(storageService).writeCvFile(anyString(), eq("cv"));
        verify(pubSubCvGateway).sendCvToPubSub(baseMessageCaptor.capture());

        assertNotNull(response);
        assertTrue(response.endsWith(".txt"), "File name must end with .txt");
        assertEquals(3, baseMessageCaptor.getValue().getLvlExpected(), "Unexpected Level value in the message");
        assertEquals("Project Manager", baseMessageCaptor.getValue().getRole(), "Unexpected Role value in the message");
        assertNotNull(baseMessageCaptor.getValue().getCvUri());
    }
}
