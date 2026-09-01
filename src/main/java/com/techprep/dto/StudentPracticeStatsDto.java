package com.techprep.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudentPracticeStatsDto {
    private Long totalSessionsCompleted;
    private Long totalQuestionsAttempted;
    private Long totalCorrectAnswers;
    private Long totalWrongAnswers;
    private Double overallAccuracy;
    private List<PracticeSessionSummaryDto> recentSessions;
}
