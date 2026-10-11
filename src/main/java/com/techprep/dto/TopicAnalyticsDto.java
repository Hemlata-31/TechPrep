package com.techprep.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicAnalyticsDto {
    private Long topicId;
    private String topicName;
    private Long subCategoryId;
    private String subCategoryName;
    private Long categoryId;
    private String categoryName;

    private long totalQuestionsAttempted;
    private long correctAnswers;
    private long wrongAnswers;
    private double accuracyPercentage;

    private Double averageSolvingTimeSeconds;
    private String averageSolvingTimeFormatted;

    private LocalDateTime lastPracticedDate;
    private Long daysSinceLastPracticed;

    private List<String> classifications; // e.g. ["STRONG", "IMPROVING"]
    private String primaryClassification; // "STRONG", "WEAK", "HARD", "NEEDS_REVISION", "MODERATE", "UNATTEMPTED"

    private TrendDirection trend; // IMPROVING, DECLINING, STABLE, INSUFFICIENT_DATA
    private Double trendDelta; // Percentage point change between recent and historical attempts
    private double hardScore; // Composite difficulty struggle metric [0-100]

    private int sessionCount;
}
