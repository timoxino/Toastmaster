package com.timoxino.interview.toastmaster.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.timoxino.interview.shared.dto.CandidateQuestionsMessage;
import com.timoxino.interview.toastmaster.dto.CvProcessingRequest;

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

    public OrchestrationService(EmailService emailService, StorageService storageService) {
        this.emailService = emailService;
        this.storageService = storageService;
    }

    public void processQuestionsMessage(CandidateQuestionsMessage message) {
        emailService.sendEmail(from, to, SUBJECT_QUESTIONS_READY, "questions");
    }

    public String processCvRequest(CvProcessingRequest request) throws IOException {
        String fileName = UUID.randomUUID().toString() + ".txt";
        storageService.writeCvFile(fileName, request.getCvContent());
        return fileName;
    }
}
