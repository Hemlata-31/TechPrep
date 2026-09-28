package com.techprep.repository;

import com.techprep.entity.PendingQuestion;
import com.techprep.entity.PendingQuestionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PendingQuestionRepository extends JpaRepository<PendingQuestion, Long> {
    List<PendingQuestion> findByStatusOrderByCreatedAtDesc(PendingQuestionStatus status);
    List<PendingQuestion> findAllByOrderByCreatedAtDesc();
}
