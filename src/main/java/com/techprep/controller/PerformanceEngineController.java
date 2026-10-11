package com.techprep.controller;

import com.techprep.dto.PerformanceAnalyticsResponseDto;
import com.techprep.dto.TopicAnalyticsDto;
import com.techprep.service.PerformanceEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/performance")
@RequiredArgsConstructor
public class PerformanceEngineController {

    private final PerformanceEngineService performanceEngineService;

    /**
     * Centralized Performance Analytics API for the authenticated student.
     * Computes overall statistics, difficulty breakdown, topic classifications
     * (Strong, Weak, Hard, Needs Revision, Improving, Declining), trends,
     * solving times, and rule-based recommendations.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/analytics")
    public ResponseEntity<PerformanceAnalyticsResponseDto> getStudentAnalytics(Authentication authentication) {
        return ResponseEntity.ok(performanceEngineService.getStudentAnalytics(authentication.getName()));
    }

    /**
     * Alias endpoint for performance summary.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/summary")
    public ResponseEntity<PerformanceAnalyticsResponseDto> getStudentSummary(Authentication authentication) {
        return ResponseEntity.ok(performanceEngineService.getStudentAnalytics(authentication.getName()));
    }

    /**
     * Detailed analytics for a specific topic for the authenticated student.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/topic/{topicId}")
    public ResponseEntity<TopicAnalyticsDto> getTopicAnalytics(
            @PathVariable Long topicId,
            Authentication authentication) {
        return ResponseEntity.ok(performanceEngineService.getStudentTopicAnalytics(authentication.getName(), topicId));
    }
}
