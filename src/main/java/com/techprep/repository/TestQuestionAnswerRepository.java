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
}
