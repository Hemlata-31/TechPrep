package com.techprep.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "study_notes")
public class StudyNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category; // Aptitude, Reasoning, DSA, Technical

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String highlights; // Key formulas / quick points

    private String pdfUrl; // Static PDF path e.g. /notes/aptitude-percentage.pdf

    private String fileName;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] pdfBytes; // For admin-uploaded PDFs

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

