package com.techprep.controller;

import com.techprep.dto.*;
import com.techprep.service.PracticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/practice")
@RequiredArgsConstructor
public class PracticeController {

    private final PracticeService practiceService;

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/start")
    public ResponseEntity<PracticeSessionSummaryDto> startPractice(
            @Valid @RequestBody StartPracticeRequestDto request,
            Authentication authentication) {
        return new ResponseEntity<>(practiceService.startPracticeSession(request, authentication.getName()), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/sessions/{sessionId}/questions/{questionOrder}")
    public ResponseEntity<PracticeQuestionDto> getQuestionByOrder(
            @PathVariable Long sessionId,
            @PathVariable Integer questionOrder,
            Authentication authentication) {
        return ResponseEntity.ok(practiceService.getQuestionByOrder(sessionId, questionOrder, authentication.getName()));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/sessions/{sessionId}/attempts/{attemptId}/submit")
    public ResponseEntity<SubmitAnswerResponseDto> submitAnswer(
            @PathVariable Long sessionId,
            @PathVariable Long attemptId,
            @Valid @RequestBody SubmitAnswerRequestDto request,
            Authentication authentication) {
        return ResponseEntity.ok(practiceService.submitAnswer(sessionId, attemptId, request, authentication.getName()));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/sessions/{sessionId}/complete")
    public ResponseEntity<PracticeSessionResultDto> completeSession(
            @PathVariable Long sessionId,
            Authentication authentication) {
        return ResponseEntity.ok(practiceService.completePracticeSession(sessionId, authentication.getName()));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/sessions/{sessionId}/result")
    public ResponseEntity<PracticeSessionResultDto> getSessionResult(
            @PathVariable Long sessionId,
            Authentication authentication) {
        return ResponseEntity.ok(practiceService.getPracticeSessionResult(sessionId, authentication.getName()));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/history")
    public ResponseEntity<List<PracticeSessionSummaryDto>> getPracticeHistory(Authentication authentication) {
        return ResponseEntity.ok(practiceService.getStudentPracticeHistory(authentication.getName()));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/stats")
    public ResponseEntity<StudentPracticeStatsDto> getStudentStats(Authentication authentication) {
        return ResponseEntity.ok(practiceService.getStudentPracticeStats(authentication.getName()));
    }
}
