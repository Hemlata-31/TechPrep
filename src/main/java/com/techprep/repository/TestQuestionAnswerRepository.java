package com.techprep.repository;

import com.techprep.entity.TestQuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestQuestionAnswerRepository extends JpaRepository<TestQuestionAnswer, Long> {
    List<TestQuestionAnswer> findByTestAttemptIdOrderByQuestionOrderAsc(Long testAttemptId);
    Optional<TestQuestionAnswer> findByTestAttemptIdAndQuestionOrder(Long testAttemptId, Integer questionOrder);

    @org.springframework.data.jpa.repository.Query("SELECT tqa FROM TestQuestionAnswer tqa " +
           "JOIN FETCH tqa.testAttempt ta " +
           "JOIN FETCH tqa.question q " +
           "LEFT JOIN FETCH q.topic t " +
           "LEFT JOIN FETCH t.subCategory sc " +
           "LEFT JOIN FETCH sc.category c " +
           "WHERE ta.user.id = :userId AND ta.status = 'SUBMITTED' AND tqa.selectedAnswer IS NOT NULL " +
           "ORDER BY tqa.id ASC")
    List<TestQuestionAnswer> findAnsweredSubmittedByUserIdWithDetails(@org.springframework.data.repository.query.Param("userId") Long userId);
}

