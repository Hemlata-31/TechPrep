package com.techprep.service.impl;

import com.techprep.dto.LeaderboardResponseDto;
import com.techprep.dto.LeaderboardResponseDto.LeaderboardEntryDto;
import com.techprep.entity.*;
import com.techprep.repository.*;
import com.techprep.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LeaderboardServiceImpl implements LeaderboardService {

    private final UserRepository userRepository;
    private final PracticeSessionRepository practiceSessionRepository;
    private final TestAttemptRepository testAttemptRepository;

    @Override
    @Transactional(readOnly = true)
    public LeaderboardResponseDto getLeaderboard(String timeFrame, Long categoryId, String currentUsername) {
        // Find all students
        List<User> students = userRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.STUDENT)
                .toList();

        // Calculate time boundary filter
        LocalDateTime startDate = null;
        LocalDateTime now = LocalDateTime.now();

        if ("WEEKLY".equalsIgnoreCase(timeFrame)) {
            startDate = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).withHour(0).withMinute(0).withSecond(0).withNano(0);
        } else if ("MONTHLY".equalsIgnoreCase(timeFrame)) {
            startDate = now.with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0).withNano(0);
        }

        // Map student user ID to stats aggregator
        Map<Long, StudentLeaderboardStats> statsMap = new HashMap<>();
        for (User student : students) {
            statsMap.put(student.getId(), new StudentLeaderboardStats(student));
        }

        // Aggregate Practice Session performance
        List<PracticeSession> sessions = practiceSessionRepository.findAll();
        for (PracticeSession ps : sessions) {
            if (ps.getUser() == null || ps.getUser().getRole() != Role.STUDENT) continue;
            
            // Time check
            if (startDate != null) {
                LocalDateTime sessionTime = ps.getStartedAt();
                if (sessionTime == null || sessionTime.isBefore(startDate)) continue;
            }

            // Category check
            if (categoryId != null) {
                if (ps.getTopic() == null || ps.getTopic().getSubCategory() == null ||
                    ps.getTopic().getSubCategory().getCategory() == null ||
                    !ps.getTopic().getSubCategory().getCategory().getId().equals(categoryId)) {
                    continue;
                }
            }

            StudentLeaderboardStats stats = statsMap.get(ps.getUser().getId());
            if (stats != null) {
                stats.totalScore += (ps.getScore() != null ? ps.getScore() : 0);
                stats.attemptedQuestions += (ps.getAttemptedQuestions() != null ? ps.getAttemptedQuestions() : 0);
                stats.correctAnswers += (ps.getCorrectAnswers() != null ? ps.getCorrectAnswers() : 0);
            }
        }

        // Aggregate Mock Test Attempt performance
        List<TestAttempt> testAttempts = testAttemptRepository.findAll();
        for (TestAttempt ta : testAttempts) {
            if (ta.getUser() == null || ta.getUser().getRole() != Role.STUDENT) continue;
            if (ta.getStatus() != TestStatus.SUBMITTED) continue;

            // Time check
            if (startDate != null) {
                LocalDateTime attemptTime = ta.getStartTime();
                if (attemptTime == null || attemptTime.isBefore(startDate)) continue;
            }

            // Category check via test questions if applicable
            if (categoryId != null && ta.getQuestionAnswers() != null) {
                long categoryCorrect = 0;
                long categoryAttempted = 0;
                long categoryScore = 0;

                for (TestQuestionAnswer tqa : ta.getQuestionAnswers()) {
                    if (tqa.getSelectedAnswer() != null && tqa.getQuestion() != null &&
                        tqa.getQuestion().getTopic() != null &&
                        tqa.getQuestion().getTopic().getSubCategory() != null &&
                        tqa.getQuestion().getTopic().getSubCategory().getCategory() != null &&
                        tqa.getQuestion().getTopic().getSubCategory().getCategory().getId().equals(categoryId)) {
                        
                        categoryAttempted += 1;
                        if (Boolean.TRUE.equals(tqa.getIsCorrect())) {
                            categoryCorrect += 1;
                            categoryScore += (tqa.getMarksObtained() != null ? tqa.getMarksObtained() : 0);
                        }
                    }
                }

                StudentLeaderboardStats stats = statsMap.get(ta.getUser().getId());
                if (stats != null) {
                    stats.totalScore += categoryScore;
                    stats.attemptedQuestions += categoryAttempted;
                    stats.correctAnswers += categoryCorrect;
                }
            } else if (categoryId == null) {
                // No category filter, add entire test attempt stats
                StudentLeaderboardStats stats = statsMap.get(ta.getUser().getId());
                if (stats != null) {
                    stats.totalScore += (ta.getScore() != null ? ta.getScore() : 0);
                    stats.correctAnswers += (ta.getCorrectAnswers() != null ? ta.getCorrectAnswers() : 0);
                    stats.attemptedQuestions += ((ta.getCorrectAnswers() != null ? ta.getCorrectAnswers() : 0) + 
                                                  (ta.getWrongAnswers() != null ? ta.getWrongAnswers() : 0));
                }
            }
        }

        // Convert to DTO list & sort
        List<StudentLeaderboardStats> statsList = new ArrayList<>(statsMap.values());

        // Sort ranking criteria: Score DESC -> Accuracy DESC -> AttemptedQuestions DESC -> Name ASC
        statsList.sort((a, b) -> {
            if (!a.totalScore.equals(b.totalScore)) {
                return Long.compare(b.totalScore, a.totalScore);
            }
            double accA = a.getAccuracy();
            double accB = b.getAccuracy();
            if (Double.compare(accB, accA) != 0) {
                return Double.compare(accB, accA);
            }
            if (!a.attemptedQuestions.equals(b.attemptedQuestions)) {
                return Long.compare(b.attemptedQuestions, a.attemptedQuestions);
            }
            return a.student.getName().compareToIgnoreCase(b.student.getName());
        });

        // Assign ranks and build DTOs
        List<LeaderboardEntryDto> entries = new ArrayList<>();
        LeaderboardEntryDto currentUserEntry = null;

        for (int i = 0; i < statsList.size(); i++) {
            StudentLeaderboardStats st = statsList.get(i);
            int rank = i + 1;
            boolean isCurrentUser = st.student.getEmail().equalsIgnoreCase(currentUsername);

            LeaderboardEntryDto entry = LeaderboardEntryDto.builder()
                    .rank(rank)
                    .userId(st.student.getId())
                    .studentName(st.student.getName())
                    .totalScore(st.totalScore)
                    .accuracy(st.getAccuracy())
                    .questionsAttempted(st.attemptedQuestions)
                    .isCurrentUser(isCurrentUser)
                    .build();

            entries.add(entry);

            if (isCurrentUser) {
                currentUserEntry = entry;
            }
        }

        return LeaderboardResponseDto.builder()
                .currentUserEntry(currentUserEntry)
                .leaderboardEntries(entries)
                .build();
    }

    private static class StudentLeaderboardStats {
        User student;
        Long totalScore = 0L;
        Long attemptedQuestions = 0L;
        Long correctAnswers = 0L;

        StudentLeaderboardStats(User student) {
            this.student = student;
        }

        double getAccuracy() {
            if (attemptedQuestions <= 0) return 0.0;
            double acc = ((double) correctAnswers / attemptedQuestions) * 100.0;
            return Math.round(acc * 10.0) / 10.0;
        }
    }
}
