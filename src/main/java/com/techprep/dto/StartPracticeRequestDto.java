package com.techprep.dto;

import lombok.Data;

@Data
public class StartPracticeRequestDto {
    private Long topicId;
    private String difficulty; // "EASY", "MEDIUM", "HARD", "ALL"
    private Integer numberOfQuestions; // e.g. 5, 10, 15, 20
}
