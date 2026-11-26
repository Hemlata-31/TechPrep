package com.techprep.repository;

import com.techprep.entity.CodingTestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodingTestCaseRepository extends JpaRepository<CodingTestCase, Long> {
    List<CodingTestCase> findByProblemIdOrderByIdAsc(Long problemId);
    List<CodingTestCase> findByProblemIdAndSampleTrueOrderByIdAsc(Long problemId);
}

