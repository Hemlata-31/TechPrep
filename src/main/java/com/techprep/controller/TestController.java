package com.techprep.controller;

import com.techprep.dto.*;
import com.techprep.service.TestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    // Public / Active Tests listing for logged in users
    @GetMapping
    public ResponseEntity<List<TestDto>> getAllActiveTests() {
        return ResponseEntity.ok(testService.getAllActiveTests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestDto> getTestById(@PathVariable Long id) {
        return ResponseEntity.ok(testService.getTestById(id));
    }

    // Admin & Instructor management APIs
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<TestDto> createTest(@Valid @RequestBody TestDto testDto) {
        return new ResponseEntity<>(testService.createTest(testDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<TestDto> updateTest(@PathVariable Long id, @Valid @RequestBody TestDto testDto) {
        return ResponseEntity.ok(testService.updateTest(id, testDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
        testService.deleteTest(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle-active")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<TestDto> toggleTestActiveStatus(@PathVariable Long id) {
        return ResponseEntity.ok(testService.toggleTestActiveStatus(id));
    }

    // Student Test Execution Endpoint: Start Test
    @PostMapping("/{id}/start")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<TestAttemptResultDto> startTest(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(testService.startTest(id, authentication.getName()));
    }
}
