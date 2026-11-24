package com.techprep.controller;

import com.techprep.dto.DailyChallengeDto;
import com.techprep.dto.DailyChallengeSubmitRequestDto;
import com.techprep.dto.DailyChallengeSubmitResponseDto;
import com.techprep.service.DailyChallengeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/daily-challenge")
@RequiredArgsConstructor
public class DailyChallengeController {

    private final DailyChallengeService dailyChallengeService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<DailyChallengeDto> getTodayChallenge(Authentication authentication) {
        return ResponseEntity.ok(dailyChallengeService.getTodayChallenge(authentication.getName()));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/submit")
    public ResponseEntity<DailyChallengeSubmitResponseDto> submitTodayChallenge(
            @Valid @RequestBody DailyChallengeSubmitRequestDto request,
            Authentication authentication) {
        return ResponseEntity.ok(dailyChallengeService.submitTodayChallenge(request, authentication.getName()));
    }
}
