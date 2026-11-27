package com.techprep.controller;

import com.techprep.dto.MistakeDto;
import com.techprep.entity.*;
import com.techprep.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/mistakes")
@RequiredArgsConstructor
public class MistakeController {

    private final UserRepository userRepository;
    private final PracticeAttemptRepository practiceAttemptRepository;
    private final TestQuestionAnswerRepository testQuestionAnswerRepository;

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping
    public ResponseEntity<List<MistakeDto>> getMyMistakes(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<Long, MistakeDto> byQuestion = new LinkedHashMap<>();

        for (PracticeAttempt pa : practiceAttemptRepository.findAnsweredByUserIdWithDetails(user.getId())) {
            if (Boolean.FALSE.equals(pa.getIsCorrect())) {
                Question q = pa.getQuestion();
                byQuestion.put(q.getId(), toDto(q, pa.getSelectedAnswer(), "PRACTICE", pa.getAnsweredAt()));
            }
        }

        for (TestQuestionAnswer tqa : testQuestionAnswerRepository.findAnsweredSubmittedByUserIdWithDetails(user.getId())) {
            if (Boolean.FALSE.equals(tqa.getIsCorrect())) {
                Question q = tqa.getQuestion();
                byQuestion.put(q.getId(), toDto(q, tqa.getSelectedAnswer(), "MOCK_TEST", tqa.getAnsweredAt()));
            }
        }

        List<MistakeDto> result = new ArrayList<>(byQuestion.values());
        Collections.reverse(result); // newest mistakes first
        return ResponseEntity.ok(result);
    }

    private MistakeDto toDto(Question q, String selectedAnswer, String source, java.time.LocalDateTime answeredAt) {
        String topicName = q.getTopic() != null ? q.getTopic().getName() : "General";
        String catName = (q.getTopic() != null && q.getTopic().getSubCategory() != null
                && q.getTopic().getSubCategory().getCategory() != null)
                ? q.getTopic().getSubCategory().getCategory().getName()
                : "General";

        return MistakeDto.builder()
                .questionId(q.getId())
                .questionText(q.getQuestionText())
                .optionA(q.getOptionA())
                .optionB(q.getOptionB())
                .optionC(q.getOptionC())
                .optionD(q.getOptionD())
                .correctAnswer(q.getCorrectAnswer())
                .selectedAnswer(selectedAnswer)
                .explanation(q.getExplanation())
                .difficulty(q.getDifficulty() != null ? q.getDifficulty().name() : "EASY")
                .topicName(topicName)
                .categoryName(catName)
                .source(source)
                .answeredAt(answeredAt)
                .build();
    }
}

