package com.techprep.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceAnalyticsResponseDto {
    private OverallPerformanceDto overall;
    private DifficultyBreakdownDto difficulty;
    
    @Builder.Default
    private List<TopicAnalyticsDto> topics = new ArrayList<>();

    private PerformanceSummaryListsDto summary;

    @Builder.Default
    private List<PerformanceTimelinePointDto> timeline = new ArrayList<>();

    @Builder.Default
    private List<String> recommendations = new ArrayList<>();
}
