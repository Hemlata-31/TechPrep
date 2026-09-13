package com.techprep.controller;

import com.techprep.dto.DashboardProgressDto;
import com.techprep.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping
    public ResponseEntity<DashboardProgressDto> getStudentProgress(Authentication authentication) {
        return ResponseEntity.ok(progressService.getStudentProgress(authentication.getName()));
    }
}
