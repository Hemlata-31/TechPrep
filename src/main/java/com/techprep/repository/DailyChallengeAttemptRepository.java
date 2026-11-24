package com.techprep.repository;

import com.techprep.entity.DailyChallengeAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DailyChallengeAttemptRepository extends JpaRepository<DailyChallengeAttempt, Long> {
    Optional<DailyChallengeAttempt> findByUserIdAndChallengeId(Long userId, Long challengeId);
    boolean existsByUserIdAndChallengeId(Long userId, Long challengeId);

    long countByChallengeId(Long challengeId);
    long countByChallengeIdAndIsCorrectTrue(Long challengeId);
}
