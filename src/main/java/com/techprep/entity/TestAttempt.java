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
@Table(name = "test_attempts")
public class TestAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private TestEntity test;

    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Column(nullable = false)
    @Builder.Default
    private Integer score = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer correctAnswers = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer wrongAnswers = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer unattemptedQuestions = 0;

    @Column(nullable = false)
    @Builder.Default
    private Double accuracy = 0.0;

    @Column(nullable = false)
    @Builder.Default
    private Double percentage = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private TestStatus status = TestStatus.IN_PROGRESS;

    @OneToMany(mappedBy = "testAttempt", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<TestQuestionAnswer> questionAnswers = new ArrayList<>();
}
