package com.techprep.service.impl;

import com.techprep.dto.DashboardProgressDto;
import com.techprep.dto.DashboardProgressDto.*;
import com.techprep.entity.*;
import com.techprep.exception.ResourceNotFoundException;
import com.techprep.repository.*;
import com.techprep.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {

    private final UserRepository userRepository;
    private final PracticeSessionRepository practiceSessionRepository;
    private final PracticeAttemptRepository practiceAttemptRepository;
    private final TestAttemptRepository testAttemptRepository;
    private final CategoryRepository categoryRepository;
    private final TopicRepository topicRepository;

    @Override
    @Transactional(readOnly = true)
    public DashboardProgressDto getStudentProgress(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + username));

        Long userId = user.getId();

        // 1. Fetch Practice Sessions & Test Attempts for User
        List<PracticeSession> practiceSessions = practiceSessionRepository.findByUserIdOrderByStartedAtDesc(userId);
        List<TestAttempt> testAttempts = testAttemptRepository.findByUserIdOrderByStartTimeDesc(userId);

        // Calculate Practice metrics
        long practiceAttempted = 0;
        long practiceCorrect = 0;
        long practiceWrong = 0;
        long practiceCompletedSessions = 0;

        for (PracticeSession ps : practiceSessions) {
            practiceAttempted += (ps.getAttemptedQuestions() != null ? ps.getAttemptedQuestions() : 0);
            practiceCorrect += (ps.getCorrectAnswers() != null ? ps.getCorrectAnswers() : 0);
            practiceWrong += (ps.getWrongAnswers() != null ? ps.getWrongAnswers() : 0);
            if (ps.getStatus() == PracticeStatus.COMPLETED) {
                practiceCompletedSessions++;
            }
        }

        // Calculate Test metrics
        long testAttempted = 0;
        long testCorrect = 0;
        long testWrong = 0;
        long testUnattempted = 0;
        long testsCompleted = 0;

        for (TestAttempt ta : testAttempts) {
            if (ta.getStatus() == TestStatus.SUBMITTED) {
                testsCompleted++;
                testCorrect += (ta.getCorrectAnswers() != null ? ta.getCorrectAnswers() : 0);
                testWrong += (ta.getWrongAnswers() != null ? ta.getWrongAnswers() : 0);
                testUnattempted += (ta.getUnattemptedQuestions() != null ? ta.getUnattemptedQuestions() : 0);
                testAttempted += ((ta.getCorrectAnswers() != null ? ta.getCorrectAnswers() : 0) +
                                  (ta.getWrongAnswers() != null ? ta.getWrongAnswers() : 0));
            }
        }

        long totalAttempted = practiceAttempted + testAttempted;
        long totalCorrect = practiceCorrect + testCorrect;
        long totalWrong = practiceWrong + testWrong;
        long totalUnattempted = (practiceSessions.stream().mapToLong(ps -> Math.max(0, ps.getTotalQuestions() - ps.getAttemptedQuestions())).sum()) + testUnattempted;

        double overallAccuracy = totalAttempted > 0 ? Math.round(((double) totalCorrect / totalAttempted) * 100.0 * 10.0) / 10.0 : 0.0;

        // 2. Topic-wise Performance Calculation with enhanced hard score
        // Map of Topic ID -> {totalAttempted, totalCorrect, hardCount, Topic}
        Map<Long, TopicStats> topicStatsMap = new HashMap<>();

        // Aggregate from practice attempts
        for (PracticeSession ps : practiceSessions) {
            Topic topic = ps.getTopic();
            if (topic != null) {
                TopicStats stats = topicStatsMap.computeIfAbsent(topic.getId(), k -> new TopicStats(topic));
                stats.attempted += (ps.getAttemptedQuestions() != null ? ps.getAttemptedQuestions() : 0);
                stats.correct += (ps.getCorrectAnswers() != null ? ps.getCorrectAnswers() : 0);
                stats.sessionCount += 1;
                // Count HARD difficulty sessions as harder
                if ("HARD".equalsIgnoreCase(ps.getSelectedDifficulty())) {
                    stats.hardDifficultyCount++;
                }
            }
        }

        // Aggregate from test attempts
        for (TestAttempt ta : testAttempts) {
            if (ta.getStatus() == TestStatus.SUBMITTED && ta.getQuestionAnswers() != null) {
                for (TestQuestionAnswer tqa : ta.getQuestionAnswers()) {
                    if (tqa.getSelectedAnswer() != null && tqa.getQuestion() != null && tqa.getQuestion().getTopic() != null) {
                        Topic topic = tqa.getQuestion().getTopic();
                        TopicStats stats = topicStatsMap.computeIfAbsent(topic.getId(), k -> new TopicStats(topic));
                        stats.attempted += 1;
                        if (Boolean.TRUE.equals(tqa.getIsCorrect())) {
                            stats.correct += 1;
                        } else {
                            // Track hard questions answered wrong
                            if (tqa.getQuestion().getDifficulty() == Difficulty.HARD) {
                                stats.hardDifficultyCount++;
                            }
                        }
                    }
                }
            }
        }

        List<TopicPerformanceDto> topicPerformances = new ArrayList<>();
        for (TopicStats ts : topicStatsMap.values()) {
            double accuracy = ts.attempted > 0 ? Math.round(((double) ts.correct / ts.attempted) * 100.0 * 10.0) / 10.0 : 0.0;
            String categoryName = (ts.topic.getSubCategory() != null && ts.topic.getSubCategory().getCategory() != null)
                    ? ts.topic.getSubCategory().getCategory().getName() : "General";

            // Hard Score: composite metric [0-100]
            // Factors: low accuracy (weighted 50%), hard difficulty questions/sessions (weighted 30%), repeat sessions (weighted 20%)
            double accuracyFactor = ts.attempted > 0 ? (1.0 - (double) ts.correct / ts.attempted) * 50.0 : 0.0;
            double hardDiffFactor = ts.attempted > 0 ? Math.min(30.0, (double) ts.hardDifficultyCount / ts.attempted * 30.0) : 0.0;
            double repeatFactor = Math.min(20.0, ts.sessionCount * 4.0); // more sessions on same topic = repeated struggle
            double hardScore = Math.min(100.0, Math.round((accuracyFactor + hardDiffFactor + repeatFactor) * 10.0) / 10.0);

            topicPerformances.add(TopicPerformanceDto.builder()
                    .topicId(ts.topic.getId())
                    .topicName(ts.topic.getName())
                    .categoryName(categoryName)
                    .totalAttempted(ts.attempted)
                    .totalCorrect(ts.correct)
                    .accuracy(accuracy)
                    .hardScore(hardScore)
                    .wrongStreak(0) // placeholder – extended analytics can populate this
                    .build());
        }

        // Sort topics by accuracy descending
        topicPerformances.sort((a, b) -> Double.compare(b.getAccuracy(), a.getAccuracy()));

        // Strongest & Weakest topics (Filtered for topics with at least 1 attempted question)
        List<TopicPerformanceDto> attemptedTopics = topicPerformances.stream()
                .filter(t -> t.getTotalAttempted() > 0)
                .collect(Collectors.toList());

        // Strongest: accuracy >= 70%
        List<TopicPerformanceDto> strongestTopics = attemptedTopics.stream()
                .filter(t -> t.getAccuracy() >= 70.0)
                .limit(5)
                .collect(Collectors.toList());

        // If no topics have >= 70%, just take the top ones
        if (strongestTopics.isEmpty()) {
            strongestTopics = attemptedTopics.stream().limit(5).collect(Collectors.toList());
        }

        // Weakest: accuracy < 50%, sorted ascending
        List<TopicPerformanceDto> weakestTopics = new ArrayList<>(attemptedTopics);
        Collections.reverse(weakestTopics);
        weakestTopics = weakestTopics.stream()
                .filter(t -> t.getAccuracy() < 60.0)
                .limit(5)
                .collect(Collectors.toList());

        // Hard Topics: topics where accuracy < 60% AND attempted >= 2 AND hardScore >= 35
        // (student has tried but keeps getting wrong - likely hard or poorly understood)
        List<TopicPerformanceDto> hardTopics = topicPerformances.stream()
                .filter(t -> t.getTotalAttempted() >= 2 && t.getAccuracy() < 60.0 && t.getHardScore() >= 25.0)
                .sorted(Comparator.comparingDouble(TopicPerformanceDto::getHardScore).reversed())
                .limit(5)
                .collect(Collectors.toList());

        // 3. Rule-based Improvement Tips
        List<String> improvementTips = generateImprovementTips(
                overallAccuracy, totalAttempted, weakestTopics, hardTopics,
                strongestTopics, practiceSessions, testAttempts, testsCompleted
        );

        // 4. Category-wise Performance Calculation
        Map<Long, CategoryStats> categoryStatsMap = new HashMap<>();

        for (TopicPerformanceDto tp : topicPerformances) {
            Topic topic = topicRepository.findById(tp.getTopicId()).orElse(null);
            if (topic != null && topic.getSubCategory() != null && topic.getSubCategory().getCategory() != null) {
                Category cat = topic.getSubCategory().getCategory();
                CategoryStats cs = categoryStatsMap.computeIfAbsent(cat.getId(), k -> new CategoryStats(cat));
                cs.attempted += tp.getTotalAttempted();
                cs.correct += tp.getTotalCorrect();
            }
        }

        List<CategoryProgressDto> categoryProgresses = new ArrayList<>();
        for (CategoryStats cs : categoryStatsMap.values()) {
            double accuracy = cs.attempted > 0 ? Math.round(((double) cs.correct / cs.attempted) * 100.0 * 10.0) / 10.0 : 0.0;
            categoryProgresses.add(CategoryProgressDto.builder()
                    .categoryId(cs.category.getId())
                    .categoryName(cs.category.getName())
                    .totalAttempted(cs.attempted)
                    .totalCorrect(cs.correct)
                    .accuracy(accuracy)
                    .build());
        }

        // 5. Recent Attempts (Combined recent Practice sessions & Test attempts)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
        List<RecentAttemptDto> recentAttempts = new ArrayList<>();

        for (PracticeSession ps : practiceSessions.stream().limit(5).collect(Collectors.toList())) {
            recentAttempts.add(RecentAttemptDto.builder()
                    .type("PRACTICE")
                    .id(ps.getId())
                    .title(ps.getTopic() != null ? ps.getTopic().getName() : "Practice Session")
                    .score(ps.getScore())
                    .accuracy(ps.getAccuracy())
                    .date(ps.getStartedAt() != null ? ps.getStartedAt().format(formatter) : "")
                    .status(ps.getStatus().name())
                    .build());
        }

        for (TestAttempt ta : testAttempts.stream().limit(5).collect(Collectors.toList())) {
            recentAttempts.add(RecentAttemptDto.builder()
                    .type("MOCK_TEST")
                    .id(ta.getId())
                    .title(ta.getTest() != null ? ta.getTest().getTitle() : "Mock Test")
                    .score(ta.getScore())
                    .accuracy(ta.getAccuracy())
                    .date(ta.getStartTime() != null ? ta.getStartTime().format(formatter) : "")
                    .status(ta.getStatus().name())
                    .build());
        }

        recentAttempts.sort((a, b) -> b.getDate().compareTo(a.getDate()));
        if (recentAttempts.size() > 8) {
            recentAttempts = recentAttempts.subList(0, 8);
        }

        // 6. Today's progress
        java.time.LocalDate today = java.time.LocalDate.now();
        long questionsToday = 0L;
        if (user.getLastActiveDate() != null && user.getLastActiveDate().equals(today)) {
            questionsToday = user.getQuestionsAttemptedToday() != null ? user.getQuestionsAttemptedToday().longValue() : 0L;
        }

        return DashboardProgressDto.builder()
                .questionsAttempted(totalAttempted)
                .questionsCorrect(totalCorrect)
                .questionsWrong(totalWrong)
                .questionsUnattempted(totalUnattempted)
                .overallAccuracy(overallAccuracy)
                .testsCompleted(testsCompleted)
                .practiceSessionsCompleted(practiceCompletedSessions)
                .questionsAttemptedToday(questionsToday)
                .dailyGoalQuestions(user.getDailyGoalQuestions())
                .currentStreak(user.getCurrentStreak())
                .longestStreak(user.getLongestStreak())
                .categoryProgress(categoryProgresses)
                .topicPerformance(topicPerformances)
                .strongestTopics(strongestTopics)
                .weakestTopics(weakestTopics)
                .hardTopics(hardTopics)
                .improvementTips(improvementTips)
                .recentAttempts(recentAttempts)
                .build();
    }

    private List<String> generateImprovementTips(
            double overallAccuracy, long totalAttempted,
            List<TopicPerformanceDto> weakestTopics,
            List<TopicPerformanceDto> hardTopics,
            List<TopicPerformanceDto> strongestTopics,
            List<PracticeSession> practiceSessions,
            List<TestAttempt> testAttempts,
            long testsCompleted) {

        List<String> tips = new ArrayList<>();

        // Tip 1: No activity at all
        if (totalAttempted == 0) {
            tips.add("🚀 Start your first practice session to get personalized insights and performance tracking!");
            return tips;
        }

        // Tip 2: Very low overall accuracy
        if (overallAccuracy < 40.0) {
            tips.add("📖 Your overall accuracy is " + overallAccuracy + "%. Focus on understanding concepts deeply before attempting more questions.");
        } else if (overallAccuracy >= 40.0 && overallAccuracy < 60.0) {
            tips.add("📈 You're at " + overallAccuracy + "% accuracy. Review your wrong answers daily to identify patterns in your mistakes.");
        } else if (overallAccuracy >= 80.0) {
            tips.add("🏆 Excellent! You're at " + overallAccuracy + "% accuracy. Challenge yourself with harder topics and Mock Tests now.");
        }

        // Tip 3: Weakest topic
        if (!weakestTopics.isEmpty() && weakestTopics.get(0).getAccuracy() < 50.0) {
            String weakTopic = weakestTopics.get(0).getTopicName();
            double acc = weakestTopics.get(0).getAccuracy();
            tips.add("🎯 Revisit \"" + weakTopic + "\" (accuracy: " + acc + "%) — try reviewing theory before attempting questions in this topic.");
        }

        // Tip 4: Hard topics pattern
        if (hardTopics.size() >= 2) {
            String names = hardTopics.stream().map(TopicPerformanceDto::getTopicName).limit(3).collect(Collectors.joining(", "));
            tips.add("🔥 " + hardTopics.size() + " topics need repeated practice: " + names + ". Solve 5 questions per day in each of these.");
        } else if (hardTopics.size() == 1) {
            tips.add("⚡ \"" + hardTopics.get(0).getTopicName() + "\" is consistently challenging for you. Review fundamentals and solve step-by-step.");
        }

        // Tip 5: No mock tests taken
        if (!practiceSessions.isEmpty() && testsCompleted == 0) {
            tips.add("💻 You haven't taken any Mock Tests yet. Simulate real exam conditions with a full timed mock test!");
        }

        // Tip 6: No practice sessions, only tests
        if (testsCompleted > 0 && practiceSessions.isEmpty()) {
            tips.add("📝 Start topic-wise Practice sessions to strengthen individual weak areas before attempting more mock tests.");
        }

        // Tip 7: Strong topic encouragement
        if (!strongestTopics.isEmpty() && strongestTopics.get(0).getAccuracy() >= 80.0) {
            tips.add("✅ Great job on \"" + strongestTopics.get(0).getTopicName() + "\" (" + strongestTopics.get(0).getAccuracy() + "%)! Maintain this and tackle harder topics next.");
        }

        // Tip 8: Multiple weak areas
        if (weakestTopics.size() >= 3) {
            tips.add("📚 You have " + weakestTopics.size() + " weak topics. Dedicate at least 20 minutes daily to your weakest topic until accuracy crosses 70%.");
        }

        // Tip 9: Practice consistency
        long completedSessions = practiceSessions.stream().filter(ps -> ps.getStatus() == PracticeStatus.COMPLETED).count();
        long inProgressSessions = practiceSessions.stream().filter(ps -> ps.getStatus() != PracticeStatus.COMPLETED).count();
        if (inProgressSessions > completedSessions && practiceSessions.size() >= 3) {
            tips.add("💡 You have many incomplete sessions. Try finishing each session fully for better score calculations and progress tracking.");
        }

        // Ensure at least one tip
        if (tips.isEmpty()) {
            tips.add("🚀 Keep practicing consistently! Aim for at least 10 questions per day to build momentum.");
        }

        return tips;
    }

    private static class TopicStats {
        Topic topic;
        long attempted = 0;
        long correct = 0;
        int sessionCount = 0;
        int hardDifficultyCount = 0;

        TopicStats(Topic topic) {
            this.topic = topic;
        }
    }

    private static class CategoryStats {
        Category category;
        long attempted = 0;
        long correct = 0;

        CategoryStats(Category category) {
            this.category = category;
        }
    }
}
