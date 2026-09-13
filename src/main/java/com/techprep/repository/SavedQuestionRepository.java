package com.techprep.repository;

import com.techprep.entity.Question;
import com.techprep.entity.SavedQuestion;
import com.techprep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedQuestionRepository extends JpaRepository<SavedQuestion, Long> {
    List<SavedQuestion> findByUserOrderByCreatedAtDesc(User user);
    Optional<SavedQuestion> findByUserAndQuestion(User user, Question question);
    boolean existsByUserAndQuestion(User user, Question question);
    void deleteByUserAndQuestion(User user, Question question);
}
