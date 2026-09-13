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

        // 2. Topic-wise Performance Calculation
        // Map of Topic ID -> {totalAttempted, totalCorrect, Topic}
        Map<Long, TopicStats> topicStatsMap = new HashMap<>();

        // Aggregate from practice attempts
        for (PracticeSession ps : practiceSessions) {
            Topic topic = ps.getTopic();
            if (topic != null) {
                TopicStats stats = topicStatsMap.computeIfAbsent(topic.getId(), k -> new TopicStats(topic));
                stats.attempted += (ps.getAttemptedQuestions() != null ? ps.getAttemptedQuestions() : 0);
                stats.correct += (ps.getCorrectAnswers() != null ? ps.getCorrectAnswers() : 0);
            }
        }

        // Aggregate from test attempts (questions in mock tests may belong to topics if question has topic)
        for (TestAttempt ta : testAttempts) {
            if (ta.getStatus() == TestStatus.SUBMITTED && ta.getQuestionAnswers() != null) {
                for (TestQuestionAnswer tqa : ta.getQuestionAnswers()) {
                    if (tqa.getSelectedAnswer() != null && tqa.getQuestion() != null && tqa.getQuestion().getTopic() != null) {
                        Topic topic = tqa.getQuestion().getTopic();
                        TopicStats stats = topicStatsMap.computeIfAbsent(topic.getId(), k -> new TopicStats(topic));
                        stats.attempted += 1;
                        if (Boolean.TRUE.equals(tqa.getIsCorrect())) {
                            stats.correct += 1;
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

            topicPerformances.add(TopicPerformanceDto.builder()
                    .topicId(ts.topic.getId())
                    .topicName(ts.topic.getName())
                    .categoryName(categoryName)
                    .totalAttempted(ts.attempted)
                    .totalCorrect(ts.correct)
                    .accuracy(accuracy)
                    .build());
        }

        // Sort topics by accuracy descending
        topicPerformances.sort((a, b) -> Double.compare(b.getAccuracy(), a.getAccuracy()));

        // Strongest & Weakest topics (Filtered for topics with at least 1 attempted question)
        List<TopicPerformanceDto> attemptedTopics = topicPerformances.stream()
                .filter(t -> t.getTotalAttempted() > 0)
                .collect(Collectors.toList());

        List<TopicPerformanceDto> strongestTopics = attemptedTopics.stream()
                .limit(5)
                .collect(Collectors.toList());

        List<TopicPerformanceDto> weakestTopics = new ArrayList<>(attemptedTopics);
        Collections.reverse(weakestTopics);
        weakestTopics = weakestTopics.stream().limit(5).collect(Collectors.toList());

        // 3. Category-wise Performance Calculation
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

        // 4. Recent Attempts (Combined recent Practice sessions & Test attempts)
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

        // Sort combined recent attempts by date descending (we can compare formatted string or do prior to DTO conversion, but since date string is sorted, let's keep original ordering or limit)
        recentAttempts.sort((a, b) -> b.getDate().compareTo(a.getDate()));
        if (recentAttempts.size() > 8) {
            recentAttempts = recentAttempts.subList(0, 8);
        }

        return DashboardProgressDto.builder()
                .questionsAttempted(totalAttempted)
                .questionsCorrect(totalCorrect)
                .questionsWrong(totalWrong)
                .questionsUnattempted(totalUnattempted)
                .overallAccuracy(overallAccuracy)
                .testsCompleted(testsCompleted)
                .practiceSessionsCompleted(practiceCompletedSessions)
                .categoryProgress(categoryProgresses)
                .topicPerformance(topicPerformances)
                .strongestTopics(strongestTopics)
                .weakestTopics(weakestTopics)
                .recentAttempts(recentAttempts)
                .build();
    }

    private static class TopicStats {
        Topic topic;
        long attempted = 0;
        long correct = 0;

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
