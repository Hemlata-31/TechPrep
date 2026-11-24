package com.techprep.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyChallengeDto {
    private Long challengeId;
    private LocalDate challengeDate;
    private Long questionId;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String topicName;
    private String subCategoryName;
    private String categoryName;
    private String difficulty;
    private Integer bonusPoints;

    private boolean isAttempted;
    private String userSelectedAnswer;
    private Boolean isCorrect;
    private String correctAnswer;
    private String explanation;
    private Integer timeTakenSeconds;

    // Social / community stats
    private long participantsToday;
    private long totalSolvedCorrect;
    private double successRate;
}
