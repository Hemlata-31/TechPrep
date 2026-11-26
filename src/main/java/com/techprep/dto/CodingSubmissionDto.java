package com.techprep.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CodingSubmissionDto {
    private Long id;
    private String language;
    private String status;
    private int passedTestCases;
    private int totalTestCases;
    private String errorMessage;
    private LocalDateTime submittedAt;
}

