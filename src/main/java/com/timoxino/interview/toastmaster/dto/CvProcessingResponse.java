package com.timoxino.interview.toastmaster.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CvProcessingResponse {
    private String cvFileName;
}
