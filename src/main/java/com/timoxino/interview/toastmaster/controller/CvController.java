package com.timoxino.interview.toastmaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timoxino.interview.shared.dto.CandidateQuestionsMessage;
import com.timoxino.interview.toastmaster.dto.CvProcessingRequest;
import com.timoxino.interview.toastmaster.dto.CvProcessingResponse;
import com.timoxino.interview.toastmaster.service.OrchestrationService;

@RestController
@RequestMapping("/cv")
public class CvController {

    @Autowired
    OrchestrationService orchestrationService;

    @PostMapping
    public CvProcessingResponse submitCvProcessing(CvProcessingRequest request) {
        orchestrationService.processQuestionsMessage(new CandidateQuestionsMessage());
        return CvProcessingResponse.builder().caseNumber("email sent").build();
    }
}
