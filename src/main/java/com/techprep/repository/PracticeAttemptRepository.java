package com.techprep.repository;

import com.techprep.entity.PracticeAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PracticeAttemptRepository extends JpaRepository<PracticeAttempt, Long> {
    List<PracticeAttempt> findBySessionIdOrderByQuestionOrderAsc(Long sessionId);
    Optional<PracticeAttempt> findBySessionIdAndQuestionOrder(Long sessionId, Integer questionOrder);
    Optional<PracticeAttempt> findBySessionIdAndQuestionId(Long sessionId, Long questionId);
}
