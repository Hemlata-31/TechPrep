package com.techprep.controller;

import com.techprep.dto.*;
import com.techprep.service.CodingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CodingController {

    private final CodingService codingService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/coding/problems")
    public ResponseEntity<List<CodingProblemSummaryDto>> getAllProblems(Authentication authentication) {
        return ResponseEntity.ok(codingService.getAllProblems(authentication.getName()));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/coding/problems/{slug}")
    public ResponseEntity<CodingProblemDetailDto> getProblemBySlug(
            @PathVariable String slug, Authentication authentication) {
        return ResponseEntity.ok(codingService.getProblemBySlug(slug, authentication.getName()));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/coding/problems/{id}/run")
    public ResponseEntity<CodingRunResponseDto> runSampleTests(
            @PathVariable Long id, @Valid @RequestBody CodingRunRequestDto request) {
        return ResponseEntity.ok(codingService.runSampleTests(id, request));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/coding/problems/{id}/submit")
    public ResponseEntity<CodingRunResponseDto> submitSolution(
            @PathVariable Long id,
            @Valid @RequestBody CodingRunRequestDto request,
            Authentication authentication) {
        return ResponseEntity.ok(codingService.submitSolution(id, request, authentication.getName()));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/coding/problems/{id}/submissions")
    public ResponseEntity<List<CodingSubmissionDto>> getSubmissions(
            @PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(codingService.getSubmissions(id, authentication.getName()));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    @PostMapping("/admin/coding-problems")
    public ResponseEntity<CodingProblemSummaryDto> createProblem(
            @Valid @RequestBody CreateCodingProblemRequestDto request) {
        return ResponseEntity.ok(codingService.createProblem(request));
    }
}

