package com.techprep.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentPracticeStatsDto {
    private Long totalSessionsCompleted;
    private Long totalQuestionsAttempted;
    private Long totalCorrectAnswers;
    private Long totalWrongAnswers;
    private Double overallAccuracy;
    
    // Daily Goals & Streaks
    private Integer dailyGoalQuestions;
    private Long questionsAttemptedToday;
    private Integer currentStreak;
    private Integer longestStreak;

    private List<PracticeSessionSummaryDto> recentSessions;
}
