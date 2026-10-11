package com.techprep.controller;

import com.techprep.dto.SavedQuestionDto;
import com.techprep.entity.User;
import com.techprep.repository.UserRepository;
import com.techprep.service.SavedQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student/bookmarks")
@RequiredArgsConstructor
public class SavedQuestionController {

    private final SavedQuestionService savedQuestionService;
    private final UserRepository userRepository;

    private User getUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/{questionId}")
    public ResponseEntity<?> addBookmark(@PathVariable Long questionId, Authentication authentication) {
        User user = getUser(authentication);
        savedQuestionService.bookmarkQuestion(user.getId(), questionId);
        return ResponseEntity.ok(Map.of("message", "Question bookmarked successfully"));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @DeleteMapping("/{questionId}")
    public ResponseEntity<?> removeBookmark(@PathVariable Long questionId, Authentication authentication) {
        User user = getUser(authentication);
        savedQuestionService.removeBookmark(user.getId(), questionId);
        return ResponseEntity.ok(Map.of("message", "Bookmark removed successfully"));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping
    public ResponseEntity<List<SavedQuestionDto>> getSavedQuestions(Authentication authentication) {
        User user = getUser(authentication);
        return ResponseEntity.ok(savedQuestionService.getSavedQuestions(user.getId()));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/{questionId}/status")
    public ResponseEntity<?> checkBookmarkStatus(@PathVariable Long questionId, Authentication authentication) {
        User user = getUser(authentication);
        boolean bookmarked = savedQuestionService.isBookmarked(user.getId(), questionId);
        return ResponseEntity.ok(Map.of("bookmarked", bookmarked));
    }
}
