package com.techprep.controller;

import com.techprep.dto.SavedQuestionDto;
import com.techprep.entity.User;
import com.techprep.service.SavedQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student/bookmarks")
@RequiredArgsConstructor
public class SavedQuestionController {

    private final SavedQuestionService savedQuestionService;

    @PostMapping("/{questionId}")
    public ResponseEntity<?> addBookmark(@PathVariable Long questionId, @AuthenticationPrincipal User user) {
        savedQuestionService.bookmarkQuestion(user.getId(), questionId);
        return ResponseEntity.ok(Map.of("message", "Question bookmarked successfully"));
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<?> removeBookmark(@PathVariable Long questionId, @AuthenticationPrincipal User user) {
        savedQuestionService.removeBookmark(user.getId(), questionId);
        return ResponseEntity.ok(Map.of("message", "Bookmark removed successfully"));
    }

    @GetMapping
    public ResponseEntity<List<SavedQuestionDto>> getSavedQuestions(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(savedQuestionService.getSavedQuestions(user.getId()));
    }

    @GetMapping("/{questionId}/status")
    public ResponseEntity<?> checkBookmarkStatus(@PathVariable Long questionId, @AuthenticationPrincipal User user) {
        boolean bookmarked = savedQuestionService.isBookmarked(user.getId(), questionId);
        return ResponseEntity.ok(Map.of("bookmarked", bookmarked));
    }
}
