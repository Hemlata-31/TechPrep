package com.techprep.service;

import com.techprep.dto.SavedQuestionDto;
import java.util.List;

public interface SavedQuestionService {
    void bookmarkQuestion(Long userId, Long questionId);
    void removeBookmark(Long userId, Long questionId);
    List<SavedQuestionDto> getSavedQuestions(Long userId);
    boolean isBookmarked(Long userId, Long questionId);
}
