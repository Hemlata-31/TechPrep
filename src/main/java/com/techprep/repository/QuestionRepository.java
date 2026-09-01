package com.techprep.repository;

import com.techprep.entity.Difficulty;
import com.techprep.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findByTopicId(Long topicId, Pageable pageable);
    Page<Question> findByTopicIdAndActiveTrue(Long topicId, Pageable pageable);
    Page<Question> findByTopicIdAndDifficulty(Long topicId, Difficulty difficulty, Pageable pageable);
    
    // For counts
    long countByTopicId(Long topicId);
    long countByTopicIdAndActiveTrue(Long topicId);
}
