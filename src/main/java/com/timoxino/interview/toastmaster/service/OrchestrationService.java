package com.timoxino.interview.toastmaster.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.Multimap;
import com.timoxino.interview.shared.dto.CandidateBaseMessage;
import com.timoxino.interview.shared.dto.CandidateQuestionsMessage;
import com.timoxino.interview.shared.dto.Question;
import com.timoxino.interview.toastmaster.dto.CvProcessingRequest;
import com.timoxino.interview.toastmaster.spring.PubSubSenderConfiguration.PubSubCvGateway;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrchestrationService {

    final static String SUBJECT_QUESTIONS_READY = "Questions are ready!";

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
        Multimap<String, Question> questionsMap = message.getQuestions();
        log.info("Sending out an email with prepared questions");
        emailService.sendEmail(from, to, SUBJECT_QUESTIONS_READY, formatEmailBody(questionsMap));
        log.info("Email sent with the questions compiled for CV file: {}",
                message != null ? message.getCvUri() : "unknown");
    }

    public String processCvRequest(CvProcessingRequest request) throws IOException {
        log.info("CV processing started.");

        String fileName = UUID.randomUUID().toString() + ".txt";
        storageService.writeCvFile(fileName, request.getCvContent());
        log.info("File for CV created with the name: {}", fileName);

        CandidateBaseMessage message = new CandidateBaseMessage();
        message.setCvUri(fileName);
        message.setLvlExpected(request.getLevelExpected());
        message.setRole(request.getRoleName());
        pubSubCvGateway.sendCvToPubSub(message);
        log.info("Message to PubSub sent with CV file name: {}", fileName);

        return fileName;
    }

    private String formatEmailBody(Multimap<String, Question> questionsMap) {
        StringBuilder emailBody = new StringBuilder();
        if (questionsMap != null) {
            for (String key : questionsMap.keySet()) {
                emailBody.append(key).append(": ");
                emailBody.append(questionsMap.get(key)).append("\n");
            }
        }
        return emailBody.toString();
    }
}
