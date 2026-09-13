package com.techprep.service.impl;

import com.techprep.dto.SavedQuestionDto;
import com.techprep.entity.Question;
import com.techprep.entity.SavedQuestion;
import com.techprep.entity.User;
import com.techprep.exception.ResourceNotFoundException;
import com.techprep.repository.QuestionRepository;
import com.techprep.repository.SavedQuestionRepository;
import com.techprep.repository.UserRepository;
import com.techprep.service.SavedQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavedQuestionServiceImpl implements SavedQuestionService {

    private final SavedQuestionRepository savedQuestionRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    @Override
    @Transactional
    public void bookmarkQuestion(Long userId, Long questionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));

        if (!savedQuestionRepository.existsByUserAndQuestion(user, question)) {
            SavedQuestion savedQuestion = SavedQuestion.builder()
                    .user(user)
                    .question(question)
                    .build();
            savedQuestionRepository.save(savedQuestion);
        }
    }

    @Override
    @Transactional
    public void removeBookmark(Long userId, Long questionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));

        savedQuestionRepository.deleteByUserAndQuestion(user, question);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SavedQuestionDto> getSavedQuestions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return savedQuestionRepository.findByUserOrderByCreatedAtDesc(user).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isBookmarked(Long userId, Long questionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));

        return savedQuestionRepository.existsByUserAndQuestion(user, question);
    }

    private SavedQuestionDto mapToDto(SavedQuestion savedQuestion) {
        Question q = savedQuestion.getQuestion();
        return SavedQuestionDto.builder()
                .id(savedQuestion.getId())
                .questionId(q.getId())
                .questionText(q.getQuestionText())
                .categoryName(q.getTopic().getSubCategory().getCategory().getName())
                .topicName(q.getTopic().getName())
                .difficulty(q.getDifficulty())
                .marks(q.getMarks())
                .savedAt(savedQuestion.getCreatedAt())
                .build();
    }
}
