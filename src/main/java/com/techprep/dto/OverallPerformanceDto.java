package com.techprep.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OverallPerformanceDto {
    private long totalQuestionsAttempted;
    private long correctAnswers;
    private long wrongAnswers;
    private long unattemptedQuestions;
    private double accuracyPercentage;
    private Double averageSolvingTimeSeconds;
    private String averageSolvingTimeFormatted;
    private LocalDateTime lastPracticedDate;
    private long practiceSessionsCompleted;
    private long mockTestsCompleted;
    private long totalSessionsAndTests;
}
