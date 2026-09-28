package com.techprep.controller;

import com.techprep.dto.AIExplanationRequestDto;
import com.techprep.dto.AIExplanationResponseDto;
import com.techprep.dto.AIGenerateRequestDto;
import com.techprep.service.AIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;

    // ========================
    // Student – AI Explanation
    // ========================

    @PostMapping("/explain")
    public ResponseEntity<AIExplanationResponseDto> getAIExplanation(
            @RequestBody AIExplanationRequestDto request) {
        AIExplanationResponseDto response = aiService.generateExplanation(request);
        return ResponseEntity.ok(response);
    }

    // ==============================
    // Admin – AI Question Generation
    // Questions are saved directly into the Question table (no pending stage).
    // ==============================

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/generate")
    public ResponseEntity<Map<String, Object>> generateQuestions(
            @Valid @RequestBody AIGenerateRequestDto request) {
        int saved = aiService.generateAndSaveQuestions(request);
        return ResponseEntity.ok(Map.of(
                "message", saved + " question(s) generated and saved successfully.",
                "savedCount", saved
        ));
    }
}
