package com.techprep.service;

import com.techprep.dto.PerformanceAnalyticsResponseDto;
import com.techprep.dto.TopicAnalyticsDto;

public interface PerformanceEngineService {

    /**
     * Aggregates real Practice and Mock Test data for the authenticated student,
     * computing overall metrics, difficulty breakdown, topic-wise classifications,
     * trends, solving times, and actionable recommendations.
     *
     * @param username The authenticated student's username/email
     * @return Complete performance analytics and recommendations
     */
    PerformanceAnalyticsResponseDto getStudentAnalytics(String username);

    /**
     * Returns detailed analytics and classifications for a specific topic for the authenticated student.
     *
     * @param username The authenticated student's username/email
     * @param topicId The ID of the topic to analyze
     * @return Detailed topic performance and classifications
     */
    TopicAnalyticsDto getStudentTopicAnalytics(String username, Long topicId);
}
