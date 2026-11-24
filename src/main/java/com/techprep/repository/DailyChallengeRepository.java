package com.techprep.repository;

import com.techprep.entity.DailyChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DailyChallengeRepository extends JpaRepository<DailyChallenge, Long> {
    Optional<DailyChallenge> findByChallengeDate(LocalDate challengeDate);
    boolean existsByChallengeDate(LocalDate challengeDate);
}
