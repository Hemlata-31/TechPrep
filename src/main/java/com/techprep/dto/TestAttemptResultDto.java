package com.techprep.dto;

import com.techprep.entity.TestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestAttemptResultDto {
    private Long attemptId;
    private Long testId;
    private String testTitle;
    private String categoryName;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long timeTakenSeconds;
    private Long remainingSeconds;

    private Integer totalQuestions;
    private Integer attemptedQuestions;
    private Integer correctAnswers;
    private Integer wrongAnswers;
    private Integer unattemptedQuestions;

    private Integer score;
    private Integer totalMarks;
    private Double accuracy;
    private Double percentage;

    private TestStatus status;

    private List<TestQuestionDto> questions;
}
