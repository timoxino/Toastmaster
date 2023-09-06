package com.timoxino.interview.toastmaster.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timoxino.interview.toastmaster.dto.CvProcessingRequest;
import com.timoxino.interview.toastmaster.dto.CvProcessingResponse;

@RestController
@RequestMapping("/cv")
public class CvController {

    @Value("gs://interview_cv")
    private Resource cvBucket;

    @PostMapping
    public CvProcessingResponse submitCvProcessing(CvProcessingRequest request) throws IOException {
        return CvProcessingResponse.builder().caseNumber(cvBucket.getURI().toString()).build();
    }
}
