package com.timoxino.interview.toastmaster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.timoxino.interview.shared.dto.CandidateQuestionsMessage;

@Service
public class OrchestrationService {

    final static String SUBJECT_QUESTIONS_READY = "Questions are ready!";

    @Value("${toastmaster.email.from}")
    String from;

    @Value("${toastmaster.email.to}")
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
