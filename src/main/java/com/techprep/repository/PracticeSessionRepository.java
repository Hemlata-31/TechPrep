package com.techprep.repository;

import com.techprep.entity.PracticeSession;
import com.techprep.entity.PracticeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PracticeSessionRepository extends JpaRepository<PracticeSession, Long> {
    List<PracticeSession> findByUserIdOrderByStartedAtDesc(Long userId);
    Page<PracticeSession> findByUserIdOrderByStartedAtDesc(Long userId, Pageable pageable);
    List<PracticeSession> findByUserIdAndStatusOrderByStartedAtDesc(Long userId, PracticeStatus status);

    @Query("SELECT COUNT(s) FROM PracticeSession s WHERE s.user.id = :userId AND s.status = :status")
    Long countByUserIdAndStatus(@Param("userId") Long userId, @Param("status") PracticeStatus status);

    @Query("SELECT COALESCE(SUM(s.attemptedQuestions), 0) FROM PracticeSession s WHERE s.user.id = :userId")
    Long sumAttemptedQuestionsByUserId(@Param("userId") Long userId);

    @Query("SELECT COALESCE(SUM(s.correctAnswers), 0) FROM PracticeSession s WHERE s.user.id = :userId")
    Long sumCorrectAnswersByUserId(@Param("userId") Long userId);

    @Query("SELECT COALESCE(SUM(s.wrongAnswers), 0) FROM PracticeSession s WHERE s.user.id = :userId")
    Long sumWrongAnswersByUserId(@Param("userId") Long userId);
}
