package com.techprep.dto;

import com.techprep.entity.TestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestAttemptSummaryDto {
    private Long id;
    private Long testId;
    private String testTitle;
    private Integer durationMinutes;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer score;
    private Integer totalMarks;
    private Double accuracy;
    private Double percentage;
    private TestStatus status;
}
