package com.techprep.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DifficultyMetricDto {
    private String difficulty; // "EASY", "MEDIUM", "HARD"
    private long totalAttempted;
    private long totalCorrect;
    private long totalWrong;
    private double accuracyPercentage;
    private Double averageSolvingTimeSeconds;
    private String averageSolvingTimeFormatted;
}
