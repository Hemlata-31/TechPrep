package com.techprep.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardProgressDto {

    // Summary Cards
    private Long questionsAttempted;
    private Long questionsCorrect;
    private Long questionsWrong;
    private Long questionsUnattempted;
    private Double overallAccuracy;
    private Long testsCompleted;
    private Long practiceSessionsCompleted;

    // Today's Progress
    private Long questionsAttemptedToday;
    private Integer dailyGoalQuestions;
    private Integer currentStreak;
    private Integer longestStreak;

    // Category-wise progress
    private List<CategoryProgressDto> categoryProgress;

    // Topic-wise performance
    private List<TopicPerformanceDto> topicPerformance;

    // Strongest & Weakest topics
    private List<TopicPerformanceDto> strongestTopics;
    private List<TopicPerformanceDto> weakestTopics;
    private List<TopicPerformanceDto> hardTopics; // topics with hard difficulty + low accuracy

    // Rule-based improvement tips
    private List<String> improvementTips;

    // Recent Attempts (Unified or distinct lists)
    private List<RecentAttemptDto> recentAttempts;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CategoryProgressDto {
        private Long categoryId;
        private String categoryName;
        private Long totalAttempted;
        private Long totalCorrect;
        private Double accuracy;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TopicPerformanceDto {
        private Long topicId;
        private String topicName;
        private String categoryName;
        private Long totalAttempted;
        private Long totalCorrect;
        private Double accuracy;
        private Double hardScore;      // composite difficulty score (0-100)
        private Integer wrongStreak;   // consecutive wrong answers for this topic
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RecentAttemptDto {
        private String type; // "PRACTICE" or "MOCK_TEST"
        private Long id;
        private String title; // Topic name or Test title
        private Integer score;
        private Double accuracy;
        private String date; // Formatted date string
        private String status;
    }
}
