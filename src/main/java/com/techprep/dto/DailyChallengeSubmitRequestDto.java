package com.techprep.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyChallengeSubmitRequestDto {

    @NotBlank(message = "Selected answer is required")
    private String selectedAnswer;

    private Integer timeTakenSeconds;
}
