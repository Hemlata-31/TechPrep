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
@Table(name = "coding_problems")
public class CodingProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Column(nullable = false)
    private String categoryTag; // e.g. "Array", "String", "Stack", "Dynamic Programming"

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String constraintsText;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String starterCodeJava;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String starterCodePython;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String starterCodeCpp;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String driverCodeJava;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String driverCodePython;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String driverCodeCpp;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

