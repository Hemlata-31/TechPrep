package com.techprep.controller;

import com.techprep.dto.QuestionRequestDto;
import com.techprep.entity.Difficulty;
import com.techprep.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    private boolean isAdminOrInstructor(Authentication authentication) {
        if (authentication == null) return false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_INSTRUCTOR"));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    @PostMapping("/questions")
    public ResponseEntity<?> createQuestion(@Valid @RequestBody QuestionRequestDto request) {
        return new ResponseEntity<>(questionService.createQuestion(request), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    @PutMapping("/questions/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long id, @Valid @RequestBody QuestionRequestDto request) {
        return ResponseEntity.ok(questionService.updateQuestion(id, request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    @DeleteMapping("/questions/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok("Question deleted successfully");
    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable Long id, Authentication authentication) {
        if (isAdminOrInstructor(authentication)) {
            return ResponseEntity.ok(questionService.getQuestionById(id));
        }
        return ResponseEntity.ok(questionService.getStudentQuestionById(id));
    }

    @GetMapping("/topics/{topicId}/questions")
    public ResponseEntity<?> getQuestionsByTopic(
            @PathVariable Long topicId,
            @RequestParam(required = false) Difficulty difficulty,
            Pageable pageable,
            Authentication authentication) {
        
        if (isAdminOrInstructor(authentication)) {
            return ResponseEntity.ok(questionService.getQuestionsByTopic(topicId, difficulty, pageable));
        }
        return ResponseEntity.ok(questionService.getStudentQuestionsByTopic(topicId, difficulty, pageable));
    }

    @GetMapping("/topics/{topicId}/questions/count")
    public ResponseEntity<Long> getQuestionCountByTopic(@PathVariable Long topicId, Authentication authentication) {
        boolean activeOnly = !isAdminOrInstructor(authentication);
        return ResponseEntity.ok(questionService.getQuestionCountByTopic(topicId, activeOnly));
    }
}
