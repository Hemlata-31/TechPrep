package com.techprep.service;

import com.techprep.dto.QuestionAdminResponseDto;
import com.techprep.dto.QuestionRequestDto;
import com.techprep.dto.QuestionStudentResponseDto;
import com.techprep.entity.Difficulty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {
    QuestionAdminResponseDto createQuestion(QuestionRequestDto request);
    QuestionAdminResponseDto updateQuestion(Long id, QuestionRequestDto request);
    void deleteQuestion(Long id);
    
    // Admin access
    QuestionAdminResponseDto getQuestionById(Long id);
    Page<QuestionAdminResponseDto> getQuestionsByTopic(Long topicId, Difficulty difficulty, Pageable pageable);
    
    // Student access
    QuestionStudentResponseDto getStudentQuestionById(Long id);
    Page<QuestionStudentResponseDto> getStudentQuestionsByTopic(Long topicId, Difficulty difficulty, Pageable pageable);
    
    long getQuestionCountByTopic(Long topicId, boolean activeOnly);
}
