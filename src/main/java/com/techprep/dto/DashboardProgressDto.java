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

    // Category-wise progress
    private List<CategoryProgressDto> categoryProgress;

    // Topic-wise performance
    private List<TopicPerformanceDto> topicPerformance;

    // Strongest & Weakest topics
    private List<TopicPerformanceDto> strongestTopics;
    private List<TopicPerformanceDto> weakestTopics;

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
