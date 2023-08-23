package com.timoxino.interview.toastmaster.dto;

import lombok.Data;

@Data
public class CvProcessingRequest {
    private String roleName;
    private Integer levelExpected;
    private String cvContent;
}
