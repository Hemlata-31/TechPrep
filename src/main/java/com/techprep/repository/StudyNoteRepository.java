package com.techprep.repository;

import com.techprep.entity.StudyNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyNoteRepository extends JpaRepository<StudyNote, Long> {
    List<StudyNote> findAllByOrderByIdAsc();
    boolean existsByPdfUrl(String pdfUrl);
}

