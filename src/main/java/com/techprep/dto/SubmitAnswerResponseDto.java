package com.techprep.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitAnswerResponseDto {
    private Long attemptId;
    private Integer questionIndex; // 1-based index
    private String selectedAnswer;
    private String correctAnswer;
    private Boolean isCorrect;
    private String explanation;
    private Integer marksObtained;
}
