package com.techprep.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyChallengeSubmitResponseDto {
    private boolean isCorrect;
    private String selectedAnswer;
    private String correctAnswer;
    private String explanation;
    private int bonusPointsAwarded;
    private int currentStreak;
    private int longestStreak;
    private String message;
    private long participantsToday;
    private long totalSolvedCorrect;
    private double successRate;
}
