package com.techprep.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerQuestionRequestDto {
    private String selectedAnswer; // "A", "B", "C", "D" or "" to clear
    private Boolean markForReview;
}
