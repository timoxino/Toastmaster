package com.timoxino.interview.toastmaster.service;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.timoxino.interview.shared.dto.CandidateBaseMessage;
import com.timoxino.interview.shared.dto.CandidateQuestionsMessage;
import com.timoxino.interview.toastmaster.dto.CvProcessingRequest;
import com.timoxino.interview.toastmaster.spring.PubSubSenderConfiguration.PubSubCvGateway;

@Service
public class OrchestrationService {

    final static String SUBJECT_QUESTIONS_READY = "Questions are ready!";

    private final static Logger LOGGER = LoggerFactory.getLogger(OrchestrationService.class);

    @Value("${toastmaster.email.from}")
    String from;

    @Value("${toastmaster.email.to}")
    String to;

    @Autowired
    EmailService emailService;

    @Autowired
    StorageService storageService;

    @Autowired
    PubSubCvGateway pubSubCvGateway;

    public OrchestrationService(EmailService emailService, StorageService storageService,
            PubSubCvGateway pubSubCvGateway) {
        this.emailService = emailService;
        this.storageService = storageService;
        this.pubSubCvGateway = pubSubCvGateway;
    }

    public void processQuestionsMessage(CandidateQuestionsMessage message) {
        emailService.sendEmail(from, to, SUBJECT_QUESTIONS_READY, "questions");
        LOGGER.info("Email sent with the questions compiled for CV file: {}", message != null ? message.getCvUri() : "unknown");
    }

    public String processCvRequest(CvProcessingRequest request) throws IOException {
        LOGGER.info("CV processing started.");

        String fileName = UUID.randomUUID().toString() + ".txt";
        storageService.writeCvFile(fileName, request.getCvContent());
        LOGGER.info("File for CV created with the name: {}", fileName);

        CandidateBaseMessage message = new CandidateBaseMessage();
        message.setCvUri(fileName);
        message.setLvlExpected(request.getLevelExpected());
        message.setRole(request.getRoleName());
        pubSubCvGateway.sendCvToPubSub(message);
        LOGGER.info("Message to PubSub sent with CV file name: {}", fileName);

        return fileName;
    }
}
