package com.timoxino.interview.toastmaster.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timoxino.interview.toastmaster.dto.CvProcessingRequest;
import com.timoxino.interview.toastmaster.dto.CvProcessingResponse;

@RestController
@RequestMapping("/cv")
public class CvController {

    @PostMapping
    public CvProcessingResponse submitCvProcessing(CvProcessingRequest request) {
        return CvProcessingResponse.builder().caseNumber("777").build();
    }
}
