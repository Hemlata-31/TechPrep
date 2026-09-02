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
    private List<PracticeSessionSummaryDto> recentSessions;
}
