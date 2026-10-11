package com.techprep.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceSummaryListsDto {
    @Builder.Default
    private List<TopicAnalyticsDto> strongTopics = new ArrayList<>();

    @Builder.Default
    private List<TopicAnalyticsDto> weakTopics = new ArrayList<>();

    @Builder.Default
    private List<TopicAnalyticsDto> hardTopics = new ArrayList<>();

    @Builder.Default
    private List<TopicAnalyticsDto> needsRevisionTopics = new ArrayList<>();

    @Builder.Default
    private List<TopicAnalyticsDto> improvingTopics = new ArrayList<>();

    @Builder.Default
    private List<TopicAnalyticsDto> decliningTopics = new ArrayList<>();
}
