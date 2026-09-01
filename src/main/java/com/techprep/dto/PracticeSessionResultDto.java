package com.techprep.dto;

import com.techprep.entity.PracticeStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PracticeSessionResultDto {
    private Long sessionId;
    private Long topicId;
    private String topicName;
    private String subCategoryName;
    private String categoryName;
    private String difficulty;
    private Integer totalQuestions;
    private Integer attemptedQuestions;
    private Integer correctAnswers;
    private Integer wrongAnswers;
    private Integer unattemptedQuestions;
    private Integer score;
    private Double accuracy;
    private PracticeStatus status;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private Long timeTakenSeconds;
}
