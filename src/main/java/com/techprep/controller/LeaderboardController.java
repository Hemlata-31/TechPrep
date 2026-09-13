package com.techprep.controller;

import com.techprep.dto.LeaderboardResponseDto;
import com.techprep.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping
    public ResponseEntity<LeaderboardResponseDto> getLeaderboard(
            @RequestParam(required = false, defaultValue = "OVERALL") String timeFrame,
            @RequestParam(required = false) Long categoryId,
            Authentication authentication) {
        return ResponseEntity.ok(leaderboardService.getLeaderboard(timeFrame, categoryId, authentication.getName()));
    }
}
