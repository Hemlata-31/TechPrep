package com.techprep.repository;

import com.techprep.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findBySubCategoryId(Long subCategoryId);
    Optional<Topic> findByNameAndSubCategoryId(String name, Long subCategoryId);
}
