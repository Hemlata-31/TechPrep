package com.techprep.repository;

import com.techprep.entity.TestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {
    List<TestEntity> findByActiveTrue();
    Page<TestEntity> findByActiveTrue(Pageable pageable);
    List<TestEntity> findByCategoryIdAndActiveTrue(Long categoryId);
    List<TestEntity> findBySubCategoryIdAndActiveTrue(Long subCategoryId);
    List<TestEntity> findByTopicIdAndActiveTrue(Long topicId);
}
