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

    @org.springframework.data.jpa.repository.Query("SELECT pa FROM PracticeAttempt pa " +
           "JOIN FETCH pa.session s " +
           "JOIN FETCH pa.question q " +
           "LEFT JOIN FETCH q.topic t " +
           "LEFT JOIN FETCH t.subCategory sc " +
           "LEFT JOIN FETCH sc.category c " +
           "WHERE s.user.id = :userId AND pa.selectedAnswer IS NOT NULL " +
           "ORDER BY pa.id ASC")
    List<PracticeAttempt> findAnsweredByUserIdWithDetails(@org.springframework.data.repository.query.Param("userId") Long userId);
}

