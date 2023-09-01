package com.timoxino.interview.toastmaster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.timoxino.interview.shared.dto.CandidateQuestionsMessage;

@Service
public class OrchestrationService {

    final static String SUBJECT_QUESTIONS_READY = "Questions are ready!";

    @Value("${TOASTMASTER_EMAIL_FROM}")
    String from;

    @Value("${TOASTMASTER_EMAIL_TO}")
    String to;

    @Autowired
    EmailService emailService;

    public OrchestrationService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void processQuestionsMessage(CandidateQuestionsMessage message) {
        emailService.sendEmail(from, to, SUBJECT_QUESTIONS_READY, "questions");
    }
}
