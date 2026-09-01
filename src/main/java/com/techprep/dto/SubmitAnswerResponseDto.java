package com.techprep.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubmitAnswerResponseDto {
    private Long attemptId;
    private Integer questionIndex; // 1-based index
    private String selectedAnswer;
    private String correctAnswer;
    private Boolean isCorrect;
    private String explanation;
    private Integer marksObtained;
}
