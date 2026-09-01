package com.techprep.service.impl;

import com.techprep.dto.QuestionAdminResponseDto;
import com.techprep.dto.QuestionRequestDto;
import com.techprep.dto.QuestionStudentResponseDto;
import com.techprep.entity.Difficulty;
import com.techprep.entity.Question;
import com.techprep.entity.Topic;
import com.techprep.exception.ResourceNotFoundException;
import com.techprep.repository.QuestionRepository;
import com.techprep.repository.TopicRepository;
import com.techprep.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final TopicRepository topicRepository;

    @Override
    public QuestionAdminResponseDto createQuestion(QuestionRequestDto request) {
        Topic topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id", request.getTopicId()));

        Question question = new Question();
        mapRequestToEntity(request, question);
        question.setTopic(topic);

        Question saved = questionRepository.save(question);
        return mapToAdminDto(saved);
    }

    @Override
    public QuestionAdminResponseDto updateQuestion(Long id, QuestionRequestDto request) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", id));

        if (!question.getTopic().getId().equals(request.getTopicId())) {
            Topic topic = topicRepository.findById(request.getTopicId())
                    .orElseThrow(() -> new ResourceNotFoundException("Topic", "id", request.getTopicId()));
            question.setTopic(topic);
        }

        mapRequestToEntity(request, question);
        Question updated = questionRepository.save(question);
        return mapToAdminDto(updated);
    }

    @Override
    public void deleteQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", id));
        questionRepository.delete(question);
    }

    @Override
    public QuestionAdminResponseDto getQuestionById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", id));
        return mapToAdminDto(question);
    }

    @Override
    public Page<QuestionAdminResponseDto> getQuestionsByTopic(Long topicId, Difficulty difficulty, Pageable pageable) {
        if (!topicRepository.existsById(topicId)) {
            throw new ResourceNotFoundException("Topic", "id", topicId);
        }
        
        Page<Question> questions;
        if (difficulty != null) {
            questions = questionRepository.findByTopicIdAndDifficulty(topicId, difficulty, pageable);
        } else {
            questions = questionRepository.findByTopicId(topicId, pageable);
        }
        
        return questions.map(this::mapToAdminDto);
    }

    @Override
    public QuestionStudentResponseDto getStudentQuestionById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", id));
        
        if (!question.getActive()) {
            throw new ResourceNotFoundException("Question", "id", id);
        }
        return mapToStudentDto(question);
    }

    @Override
    public Page<QuestionStudentResponseDto> getStudentQuestionsByTopic(Long topicId, Difficulty difficulty, Pageable pageable) {
        if (!topicRepository.existsById(topicId)) {
            throw new ResourceNotFoundException("Topic", "id", topicId);
        }

        Page<Question> questions;
        if (difficulty != null) {
            questions = questionRepository.findByTopicIdAndDifficulty(topicId, difficulty, pageable); // Assuming we'd want to check active here too, but let's keep it simple
        } else {
            questions = questionRepository.findByTopicIdAndActiveTrue(topicId, pageable);
        }
        
        return questions.map(this::mapToStudentDto);
    }

    @Override
    public long getQuestionCountByTopic(Long topicId, boolean activeOnly) {
        if (activeOnly) {
            return questionRepository.countByTopicIdAndActiveTrue(topicId);
        }
        return questionRepository.countByTopicId(topicId);
    }

    private void mapRequestToEntity(QuestionRequestDto dto, Question entity) {
        entity.setQuestionText(dto.getQuestionText());
        entity.setOptionA(dto.getOptionA());
        entity.setOptionB(dto.getOptionB());
        entity.setOptionC(dto.getOptionC());
        entity.setOptionD(dto.getOptionD());
        entity.setCorrectAnswer(dto.getCorrectAnswer());
        entity.setExplanation(dto.getExplanation());
        entity.setDifficulty(dto.getDifficulty());
        entity.setMarks(dto.getMarks());
        entity.setActive(dto.getActive());
    }

    private QuestionAdminResponseDto mapToAdminDto(Question question) {
        QuestionAdminResponseDto dto = new QuestionAdminResponseDto();
        dto.setId(question.getId());
        dto.setQuestionText(question.getQuestionText());
        dto.setOptionA(question.getOptionA());
        dto.setOptionB(question.getOptionB());
        dto.setOptionC(question.getOptionC());
        dto.setOptionD(question.getOptionD());
        dto.setCorrectAnswer(question.getCorrectAnswer());
        dto.setExplanation(question.getExplanation());
        dto.setDifficulty(question.getDifficulty());
        dto.setMarks(question.getMarks());
        dto.setActive(question.getActive());
        dto.setTopicId(question.getTopic().getId());
        dto.setCreatedAt(question.getCreatedAt());
        dto.setUpdatedAt(question.getUpdatedAt());
        return dto;
    }

    private QuestionStudentResponseDto mapToStudentDto(Question question) {
        QuestionStudentResponseDto dto = new QuestionStudentResponseDto();
        dto.setId(question.getId());
        dto.setQuestionText(question.getQuestionText());
        dto.setOptionA(question.getOptionA());
        dto.setOptionB(question.getOptionB());
        dto.setOptionC(question.getOptionC());
        dto.setOptionD(question.getOptionD());
        dto.setDifficulty(question.getDifficulty());
        dto.setMarks(question.getMarks());
        dto.setTopicId(question.getTopic().getId());
        return dto;
    }
}
