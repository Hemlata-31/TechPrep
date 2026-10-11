package com.techprep.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceTimelinePointDto {
    private String type; // "PRACTICE" or "MOCK_TEST"
    private Long id;
    private String title;
    private LocalDateTime date;
    private String formattedDate;
    private int score;
    private double accuracy;
    private int attemptedQuestions;
    private int correctAnswers;
    private Double averageSolvingTimeSeconds;
}
