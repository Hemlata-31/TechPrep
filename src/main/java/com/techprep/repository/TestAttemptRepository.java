package com.techprep.repository;

import com.techprep.entity.TestAttempt;
import com.techprep.entity.TestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestAttemptRepository extends JpaRepository<TestAttempt, Long> {
    List<TestAttempt> findByUserIdOrderByStartTimeDesc(Long userId);
    Optional<TestAttempt> findByUserIdAndTestIdAndStatus(Long userId, Long testId, TestStatus status);

    @Query("SELECT COUNT(ta) FROM TestAttempt ta WHERE ta.user.id = :userId AND ta.status = 'SUBMITTED'")
    Long countSubmittedByUserId(@Param("userId") Long userId);
}
