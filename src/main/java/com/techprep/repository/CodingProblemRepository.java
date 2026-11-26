package com.techprep.repository;

import com.techprep.entity.CodingProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodingProblemRepository extends JpaRepository<CodingProblem, Long> {
    List<CodingProblem> findByActiveTrueOrderByIdAsc();
    Optional<CodingProblem> findBySlug(String slug);
    boolean existsBySlug(String slug);
}

