package com.techprep.controller;

import com.techprep.dto.*;
import com.techprep.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-attempts")
@RequiredArgsConstructor
public class TestAttemptController {

    private final TestService testService;

    @GetMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<TestAttemptSummaryDto>> getStudentTestHistory(Authentication authentication) {
        return ResponseEntity.ok(testService.getStudentTestHistory(authentication.getName()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<TestAttemptResultDto> getTestAttemptResult(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(testService.getTestAttemptResult(id, authentication.getName()));
    }

    @GetMapping("/{id}/questions/{order}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<TestQuestionDto> getTestQuestionByOrder(@PathVariable Long id,
                                                                   @PathVariable Integer order,
                                                                   Authentication authentication) {
        return ResponseEntity.ok(testService.getTestQuestionByOrder(id, order, authentication.getName()));
    }

    @PostMapping("/{id}/questions/{order}/answer")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<TestQuestionDto> answerTestQuestion(@PathVariable Long id,
                                                               @PathVariable Integer order,
                                                               @RequestBody AnswerQuestionRequestDto request,
                                                               Authentication authentication) {
        return ResponseEntity.ok(testService.answerTestQuestion(id, order, request, authentication.getName()));
    }

    @PostMapping("/{id}/submit")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<TestAttemptResultDto> submitTest(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(testService.submitTest(id, authentication.getName()));
    }
}
