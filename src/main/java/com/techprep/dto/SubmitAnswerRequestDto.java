package com.techprep.dto;

import lombok.Data;

@Data
public class SubmitAnswerRequestDto {
    private String selectedAnswer; // "A", "B", "C", "D"
}
