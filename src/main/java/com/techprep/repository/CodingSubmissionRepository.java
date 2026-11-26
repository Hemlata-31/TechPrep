package com.techprep.repository;

import com.techprep.entity.CodingSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodingSubmissionRepository extends JpaRepository<CodingSubmission, Long> {
    List<CodingSubmission> findByUserIdAndProblemIdOrderBySubmittedAtDesc(Long userId, Long problemId);

    @Query("SELECT DISTINCT s.problem.id FROM CodingSubmission s WHERE s.user.id = :userId AND s.status = 'ACCEPTED'")
    List<Long> findSolvedProblemIdsByUserId(@Param("userId") Long userId);
}

