package com.techprep.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "practice_sessions")
public class PracticeSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty; // EASY, MEDIUM, HARD, or ALL (we can store null or a string if ALL, or handle difficulty nullable if ALL)

    @Column(name = "selected_difficulty")
    private String selectedDifficulty; // "EASY", "MEDIUM", "HARD", "ALL"

    @Column(nullable = false)
    private Integer totalQuestions;

    @Column(nullable = false)
    private Integer attemptedQuestions = 0;

    @Column(nullable = false)
    private Integer correctAnswers = 0;

    @Column(nullable = false)
    private Integer wrongAnswers = 0;

    @Column(nullable = false)
    private Integer score = 0;

    @Column(nullable = false)
    private Double accuracy = 0.0; // Percentage e.g. 80.0

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PracticeStatus status = PracticeStatus.IN_PROGRESS;

    @Column(updatable = false)
    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PracticeAttempt> attempts = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        startedAt = LocalDateTime.now();
    }
}
