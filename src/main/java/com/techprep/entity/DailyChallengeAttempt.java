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
@Table(name = "daily_challenge_attempts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "daily_challenge_id"})
})
public class DailyChallengeAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_challenge_id", nullable = false)
    private DailyChallenge challenge;

    @Column(name = "selected_answer", nullable = false, length = 10)
    private String selectedAnswer;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

    @Column(name = "time_taken_seconds")
    private Integer timeTakenSeconds;

    @Builder.Default
    @Column(name = "attempted_at", nullable = false)
    private LocalDateTime attemptedAt = LocalDateTime.now();
}
