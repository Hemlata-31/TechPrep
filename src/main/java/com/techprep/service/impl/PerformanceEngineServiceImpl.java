package com.techprep.service.impl;

import com.techprep.constant.PerformanceEngineConstants;
import com.techprep.dto.*;
import com.techprep.entity.*;
import com.techprep.exception.ResourceNotFoundException;
import com.techprep.repository.*;
import com.techprep.service.PerformanceEngineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerformanceEngineServiceImpl implements PerformanceEngineService {

    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final PracticeSessionRepository practiceSessionRepository;
    private final PracticeAttemptRepository practiceAttemptRepository;
    private final TestAttemptRepository testAttemptRepository;
    private final TestQuestionAnswerRepository testQuestionAnswerRepository;

    private static final DateTimeFormatter TIMELINE_DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");

    @Override
    @Transactional(readOnly = true)
    public PerformanceAnalyticsResponseDto getStudentAnalytics(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + username));
        Long userId = user.getId();

        // 1. Fetch Practice and Test data for user
        List<PracticeSession> practiceSessions = practiceSessionRepository.findByUserIdOrderByStartedAtDesc(userId);
        List<TestAttempt> testAttempts = testAttemptRepository.findByUserIdOrderByStartTimeDesc(userId);
        List<PracticeAttempt> practiceAttempts = practiceAttemptRepository.findAnsweredByUserIdWithDetails(userId);
        List<TestQuestionAnswer> testAnswers = testQuestionAnswerRepository.findAnsweredSubmittedByUserIdWithDetails(userId);
        List<Topic> allTopics = topicRepository.findAll();

        // 2. Compute Overall Performance & Timing
        OverallPerformanceDto overall = calculateOverallPerformance(practiceSessions, testAttempts);

        // 3. Compute Difficulty Breakdown (EASY, MEDIUM, HARD)
        DifficultyBreakdownDto difficulty = calculateDifficultyBreakdown(practiceAttempts, testAnswers, practiceSessions, testAttempts);

        // 4. Compute Topic Analytics, Classifications & Trends
        List<TopicAnalyticsDto> topicAnalyticsList = calculateTopicAnalytics(allTopics, practiceAttempts, testAnswers, practiceSessions);

        // 5. Partition into Summary Lists
        PerformanceSummaryListsDto summary = buildSummaryLists(topicAnalyticsList);

        // 6. Build Timeline Points
        List<PerformanceTimelinePointDto> timeline = buildTimelinePoints(practiceSessions, testAttempts);

        // 7. Rule-based Actionable Recommendations
        List<String> recommendations = generateActionableRecommendations(overall, summary, difficulty);

        return PerformanceAnalyticsResponseDto.builder()
                .overall(overall)
                .difficulty(difficulty)
                .topics(topicAnalyticsList)
                .summary(summary)
                .timeline(timeline)
                .recommendations(recommendations)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public TopicAnalyticsDto getStudentTopicAnalytics(String username, Long topicId) {
        PerformanceAnalyticsResponseDto analytics = getStudentAnalytics(username);
        return analytics.getTopics().stream()
                .filter(t -> t.getTopicId().equals(topicId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: " + topicId));
    }

    // =========================================================================
    // CALCULATION LOGIC
    // =========================================================================

    private OverallPerformanceDto calculateOverallPerformance(
            List<PracticeSession> practiceSessions,
            List<TestAttempt> testAttempts) {

        long practiceAttempted = 0;
        long practiceCorrect = 0;
        long practiceWrong = 0;
        long practiceCompleted = 0;
        long practiceUnattempted = 0;
        LocalDateTime latestPracticeDate = null;

        double totalPracticeTimeSec = 0.0;
        long totalPracticeTimedQuestions = 0;

        for (PracticeSession ps : practiceSessions) {
            int attempted = ps.getAttemptedQuestions() != null ? ps.getAttemptedQuestions() : 0;
            int correct = ps.getCorrectAnswers() != null ? ps.getCorrectAnswers() : 0;
            int wrong = ps.getWrongAnswers() != null ? ps.getWrongAnswers() : 0;
            int total = ps.getTotalQuestions() != null ? ps.getTotalQuestions() : 0;

            practiceAttempted += attempted;
            practiceCorrect += correct;
            practiceWrong += wrong;
            practiceUnattempted += Math.max(0, total - attempted);

            if (ps.getStatus() == PracticeStatus.COMPLETED) {
                practiceCompleted++;
            }

            LocalDateTime sessionDate = ps.getCompletedAt() != null ? ps.getCompletedAt() : ps.getStartedAt();
            if (sessionDate != null && (latestPracticeDate == null || sessionDate.isAfter(latestPracticeDate))) {
                latestPracticeDate = sessionDate;
            }

            // Practice duration calculation if completed
            if (ps.getStatus() == PracticeStatus.COMPLETED && ps.getStartedAt() != null && ps.getCompletedAt() != null && attempted > 0) {
                long durationSec = Duration.between(ps.getStartedAt(), ps.getCompletedAt()).getSeconds();
                if (durationSec > 0) {
                    double avgQTime = Math.min((double) durationSec / attempted, PerformanceEngineConstants.MAX_VALID_QUESTION_TIME_SECONDS);
                    totalPracticeTimeSec += (avgQTime * attempted);
                    totalPracticeTimedQuestions += attempted;
                }
            }
        }

        long testAttempted = 0;
        long testCorrect = 0;
        long testWrong = 0;
        long testCompleted = 0;
        long testUnattempted = 0;
        LocalDateTime latestTestDate = null;

        double totalTestTimeSec = 0.0;
        long totalTestTimedQuestions = 0;

        for (TestAttempt ta : testAttempts) {
            if (ta.getStatus() == TestStatus.SUBMITTED) {
                testCompleted++;
                int correct = ta.getCorrectAnswers() != null ? ta.getCorrectAnswers() : 0;
                int wrong = ta.getWrongAnswers() != null ? ta.getWrongAnswers() : 0;
                int unattempted = ta.getUnattemptedQuestions() != null ? ta.getUnattemptedQuestions() : 0;
                int attempted = correct + wrong;

                testAttempted += attempted;
                testCorrect += correct;
                testWrong += wrong;
                testUnattempted += unattempted;

                LocalDateTime attemptDate = ta.getEndTime() != null ? ta.getEndTime() : ta.getStartTime();
                if (attemptDate != null && (latestTestDate == null || attemptDate.isAfter(latestTestDate))) {
                    latestTestDate = attemptDate;
                }

                // Test duration calculation
                if (ta.getStartTime() != null && ta.getEndTime() != null && attempted > 0) {
                    long durationSec = Duration.between(ta.getStartTime(), ta.getEndTime()).getSeconds();
                    if (durationSec > 0) {
                        double avgQTime = Math.min((double) durationSec / attempted, PerformanceEngineConstants.MAX_VALID_QUESTION_TIME_SECONDS);
                        totalTestTimeSec += (avgQTime * attempted);
                        totalTestTimedQuestions += attempted;
                    }
                }
            }
        }

        long totalAttempted = practiceAttempted + testAttempted;
        long totalCorrect = practiceCorrect + testCorrect;
        long totalWrong = practiceWrong + testWrong;
        long totalUnattempted = practiceUnattempted + testUnattempted;
        double accuracy = totalAttempted > 0 ? roundOneDecimal(((double) totalCorrect / totalAttempted) * 100.0) : 0.0;

        // Overall average solving time
        long combinedTimedQuestions = totalPracticeTimedQuestions + totalTestTimedQuestions;
        Double averageSolvingTimeSeconds = null;
        if (combinedTimedQuestions > 0) {
            averageSolvingTimeSeconds = roundOneDecimal((totalPracticeTimeSec + totalTestTimeSec) / combinedTimedQuestions);
        }

        LocalDateTime lastPracticedDate = latestPracticeDate;
        if (latestTestDate != null && (lastPracticedDate == null || latestTestDate.isAfter(lastPracticedDate))) {
            lastPracticedDate = latestTestDate;
        }

        return OverallPerformanceDto.builder()
                .totalQuestionsAttempted(totalAttempted)
                .correctAnswers(totalCorrect)
                .wrongAnswers(totalWrong)
                .unattemptedQuestions(totalUnattempted)
                .accuracyPercentage(accuracy)
                .averageSolvingTimeSeconds(averageSolvingTimeSeconds)
                .averageSolvingTimeFormatted(formatSolvingTime(averageSolvingTimeSeconds))
                .lastPracticedDate(lastPracticedDate)
                .practiceSessionsCompleted(practiceCompleted)
                .mockTestsCompleted(testCompleted)
                .totalSessionsAndTests(practiceSessions.size() + testAttempts.size())
                .build();
    }

    private DifficultyBreakdownDto calculateDifficultyBreakdown(
            List<PracticeAttempt> practiceAttempts,
            List<TestQuestionAnswer> testAnswers,
            List<PracticeSession> practiceSessions,
            List<TestAttempt> testAttempts) {

        Map<Difficulty, DifficultyStatsCollector> map = new EnumMap<>(Difficulty.class);
        map.put(Difficulty.EASY, new DifficultyStatsCollector("EASY"));
        map.put(Difficulty.MEDIUM, new DifficultyStatsCollector("MEDIUM"));
        map.put(Difficulty.HARD, new DifficultyStatsCollector("HARD"));

        // Process Practice Attempts
        for (PracticeAttempt pa : practiceAttempts) {
            if (pa.getQuestion() != null && pa.getQuestion().getDifficulty() != null) {
                Difficulty diff = pa.getQuestion().getDifficulty();
                DifficultyStatsCollector stats = map.get(diff);
                if (stats != null) {
                    stats.attempted++;
                    if (Boolean.TRUE.equals(pa.getIsCorrect())) {
                        stats.correct++;
                    } else {
                        stats.wrong++;
                    }
                }
            }
        }

        // Process Mock Test Answers
        for (TestQuestionAnswer tqa : testAnswers) {
            if (tqa.getQuestion() != null && tqa.getQuestion().getDifficulty() != null) {
                Difficulty diff = tqa.getQuestion().getDifficulty();
                DifficultyStatsCollector stats = map.get(diff);
                if (stats != null) {
                    stats.attempted++;
                    if (Boolean.TRUE.equals(tqa.getIsCorrect())) {
                        stats.correct++;
                    } else {
                        stats.wrong++;
                    }
                }
            }
        }

        // Map session durations by difficulty where session was targeted to a specific difficulty
        for (PracticeSession ps : practiceSessions) {
            if (ps.getStatus() == PracticeStatus.COMPLETED && ps.getStartedAt() != null && ps.getCompletedAt() != null) {
                int attempted = ps.getAttemptedQuestions() != null ? ps.getAttemptedQuestions() : 0;
                if (attempted > 0 && ps.getDifficulty() != null && map.containsKey(ps.getDifficulty())) {
                    long durationSec = Duration.between(ps.getStartedAt(), ps.getCompletedAt()).getSeconds();
                    if (durationSec > 0) {
                        double avgQ = Math.min((double) durationSec / attempted, PerformanceEngineConstants.MAX_VALID_QUESTION_TIME_SECONDS);
                        DifficultyStatsCollector stats = map.get(ps.getDifficulty());
                        stats.totalTimeSeconds += (avgQ * attempted);
                        stats.timedQuestions += attempted;
                    }
                }
            }
        }

        return DifficultyBreakdownDto.builder()
                .easy(map.get(Difficulty.EASY).toDto())
                .medium(map.get(Difficulty.MEDIUM).toDto())
                .hard(map.get(Difficulty.HARD).toDto())
                .build();
    }

    private List<TopicAnalyticsDto> calculateTopicAnalytics(
            List<Topic> allTopics,
            List<PracticeAttempt> practiceAttempts,
            List<TestQuestionAnswer> testAnswers,
            List<PracticeSession> practiceSessions) {

        // Map topicId -> TopicCollector
        Map<Long, TopicCollector> topicMap = new HashMap<>();
        for (Topic t : allTopics) {
            topicMap.put(t.getId(), new TopicCollector(t));
        }

        // Group Practice Attempts
        for (PracticeAttempt pa : practiceAttempts) {
            if (pa.getQuestion() != null && pa.getQuestion().getTopic() != null) {
                Topic topic = pa.getQuestion().getTopic();
                TopicCollector collector = topicMap.computeIfAbsent(topic.getId(), k -> new TopicCollector(topic));
                LocalDateTime timestamp = pa.getAnsweredAt() != null ? pa.getAnsweredAt() :
                        (pa.getSession() != null ? pa.getSession().getStartedAt() : LocalDateTime.now());
                boolean isCorrect = Boolean.TRUE.equals(pa.getIsCorrect());
                collector.addAttempt(isCorrect, pa.getQuestion().getDifficulty(), timestamp);
            }
        }

        // Group Mock Test Answers
        for (TestQuestionAnswer tqa : testAnswers) {
            if (tqa.getQuestion() != null && tqa.getQuestion().getTopic() != null) {
                Topic topic = tqa.getQuestion().getTopic();
                TopicCollector collector = topicMap.computeIfAbsent(topic.getId(), k -> new TopicCollector(topic));
                LocalDateTime timestamp = tqa.getAnsweredAt() != null ? tqa.getAnsweredAt() :
                        (tqa.getTestAttempt() != null ? tqa.getTestAttempt().getStartTime() : LocalDateTime.now());
                boolean isCorrect = Boolean.TRUE.equals(tqa.getIsCorrect());
                collector.addAttempt(isCorrect, tqa.getQuestion().getDifficulty(), timestamp);
            }
        }

        // Count sessions per topic and calculate topic-specific solving times
        for (PracticeSession ps : practiceSessions) {
            if (ps.getTopic() != null) {
                TopicCollector collector = topicMap.computeIfAbsent(ps.getTopic().getId(), k -> new TopicCollector(ps.getTopic()));
                collector.sessionCount++;

                if (ps.getStatus() == PracticeStatus.COMPLETED && ps.getStartedAt() != null && ps.getCompletedAt() != null) {
                    int attempted = ps.getAttemptedQuestions() != null ? ps.getAttemptedQuestions() : 0;
                    if (attempted > 0) {
                        long durationSec = Duration.between(ps.getStartedAt(), ps.getCompletedAt()).getSeconds();
                        if (durationSec > 0) {
                            double avgQ = Math.min((double) durationSec / attempted, PerformanceEngineConstants.MAX_VALID_QUESTION_TIME_SECONDS);
                            collector.totalTimeSeconds += (avgQ * attempted);
                            collector.timedQuestions += attempted;
                        }
                    }
                }
            }
        }

        LocalDate today = LocalDate.now();
        List<TopicAnalyticsDto> result = new ArrayList<>();

        for (TopicCollector collector : topicMap.values()) {
            Topic topic = collector.topic;
            long attempted = collector.attempted;
            long correct = collector.correct;
            long wrong = collector.wrong;
            double accuracy = attempted > 0 ? roundOneDecimal(((double) correct / attempted) * 100.0) : 0.0;

            Double avgSolvingTime = collector.timedQuestions > 0 ?
                    roundOneDecimal(collector.totalTimeSeconds / collector.timedQuestions) : null;

            LocalDateTime lastPracticed = collector.latestTimestamp;
            Long daysSinceLast = lastPracticed != null ?
                    ChronoUnit.DAYS.between(lastPracticed.toLocalDate(), today) : null;

            // Trend Calculation
            TrendCalculation trendCalc = computeTopicTrend(collector.attempts);

            // Hard Score Calculation: composite struggle score [0-100]
            double accuracyStruggle = attempted > 0 ? (1.0 - ((double) correct / attempted)) * 50.0 : 0.0;
            double hardDiffStruggle = collector.hardDifficultyAttempted > 0 ?
                    ((double) (collector.hardDifficultyAttempted - collector.hardDifficultyCorrect) / collector.hardDifficultyAttempted) * 30.0 : 0.0;
            double repeatStruggle = Math.min(20.0, collector.sessionCount * 4.0);
            double hardScore = attempted > 0 ? Math.min(100.0, roundOneDecimal(accuracyStruggle + hardDiffStruggle + repeatStruggle)) : 0.0;

            // Classifications derivation (Deterministic rule-based)
            List<String> classifications = new ArrayList<>();

            boolean isStrong = attempted >= PerformanceEngineConstants.STRONG_MIN_ATTEMPTS &&
                    accuracy >= PerformanceEngineConstants.STRONG_ACCURACY_THRESHOLD;

            boolean isWeak = attempted >= PerformanceEngineConstants.WEAK_MIN_ATTEMPTS &&
                    accuracy < PerformanceEngineConstants.WEAK_ACCURACY_THRESHOLD;

            boolean isHard = (collector.hardDifficultyAttempted >= PerformanceEngineConstants.HARD_MIN_HARD_ATTEMPTS &&
                    (collector.getHardDifficultyAccuracy() < PerformanceEngineConstants.HARD_ACCURACY_THRESHOLD)) ||
                    (hardScore >= PerformanceEngineConstants.HARD_SCORE_THRESHOLD && attempted >= 3) ||
                    (attempted >= PerformanceEngineConstants.HARD_VOLUME_ATTEMPTS && accuracy < PerformanceEngineConstants.HARD_VOLUME_ACCURACY_THRESHOLD);

            boolean isNeedsRevision = attempted >= PerformanceEngineConstants.REVISION_MIN_ATTEMPTS &&
                    accuracy >= PerformanceEngineConstants.REVISION_MIN_ACCURACY &&
                    daysSinceLast != null && daysSinceLast >= PerformanceEngineConstants.REVISION_DAYS_THRESHOLD;

            boolean isImproving = trendCalc.trend == TrendDirection.IMPROVING;
            boolean isDeclining = trendCalc.trend == TrendDirection.DECLINING;

            if (isStrong) classifications.add("STRONG");
            if (isWeak) classifications.add("WEAK");
            if (isHard) classifications.add("HARD");
            if (isNeedsRevision) classifications.add("NEEDS_REVISION");
            if (isImproving) classifications.add("IMPROVING");
            if (isDeclining) classifications.add("DECLINING");

            // Primary Classification badge
            String primaryClassification;
            if (isHard) {
                primaryClassification = "HARD";
            } else if (isWeak) {
                primaryClassification = "WEAK";
            } else if (isNeedsRevision) {
                primaryClassification = "NEEDS_REVISION";
            } else if (isDeclining) {
                primaryClassification = "DECLINING";
            } else if (isStrong) {
                primaryClassification = "STRONG";
            } else if (isImproving) {
                primaryClassification = "IMPROVING";
            } else if (attempted > 0) {
                primaryClassification = "MODERATE";
            } else {
                primaryClassification = "UNATTEMPTED";
            }

            String subCategoryName = topic.getSubCategory() != null ? topic.getSubCategory().getName() : "General";
            Long subCategoryId = topic.getSubCategory() != null ? topic.getSubCategory().getId() : null;
            String categoryName = (topic.getSubCategory() != null && topic.getSubCategory().getCategory() != null) ?
                    topic.getSubCategory().getCategory().getName() : "General";
            Long categoryId = (topic.getSubCategory() != null && topic.getSubCategory().getCategory() != null) ?
                    topic.getSubCategory().getCategory().getId() : null;

            result.add(TopicAnalyticsDto.builder()
                    .topicId(topic.getId())
                    .topicName(topic.getName())
                    .subCategoryId(subCategoryId)
                    .subCategoryName(subCategoryName)
                    .categoryId(categoryId)
                    .categoryName(categoryName)
                    .totalQuestionsAttempted(attempted)
                    .correctAnswers(correct)
                    .wrongAnswers(wrong)
                    .accuracyPercentage(accuracy)
                    .averageSolvingTimeSeconds(avgSolvingTime)
                    .averageSolvingTimeFormatted(formatSolvingTime(avgSolvingTime))
                    .lastPracticedDate(lastPracticed)
                    .daysSinceLastPracticed(daysSinceLast)
                    .classifications(classifications)
                    .primaryClassification(primaryClassification)
                    .trend(trendCalc.trend)
                    .trendDelta(trendCalc.delta)
                    .hardScore(hardScore)
                    .sessionCount(collector.sessionCount)
                    .build());
        }

        // Sort by attempted descending, then accuracy descending
        result.sort((a, b) -> {
            int comp = Long.compare(b.getTotalQuestionsAttempted(), a.getTotalQuestionsAttempted());
            if (comp != 0) return comp;
            return Double.compare(b.getAccuracyPercentage(), a.getAccuracyPercentage());
        });

        return result;
    }

    private TrendCalculation computeTopicTrend(List<ChronologicalAttempt> attempts) {
        if (attempts.size() < PerformanceEngineConstants.TREND_MIN_ATTEMPTS) {
            return new TrendCalculation(TrendDirection.INSUFFICIENT_DATA, null);
        }

        attempts.sort(Comparator.comparing(a -> a.timestamp));

        int total = attempts.size();
        int half = total / 2;

        List<ChronologicalAttempt> olderHalf = attempts.subList(0, half);
        List<ChronologicalAttempt> recentHalf = attempts.subList(half, total);

        long olderCorrect = olderHalf.stream().filter(a -> a.isCorrect).count();
        double olderAccuracy = ((double) olderCorrect / olderHalf.size()) * 100.0;

        long recentCorrect = recentHalf.stream().filter(a -> a.isCorrect).count();
        double recentAccuracy = ((double) recentCorrect / recentHalf.size()) * 100.0;

        double delta = roundOneDecimal(recentAccuracy - olderAccuracy);

        TrendDirection trend;
        if (delta >= PerformanceEngineConstants.TREND_IMPROVING_DELTA) {
            trend = TrendDirection.IMPROVING;
        } else if (delta <= PerformanceEngineConstants.TREND_DECLINING_DELTA) {
            trend = TrendDirection.DECLINING;
        } else {
            trend = TrendDirection.STABLE;
        }

        return new TrendCalculation(trend, delta);
    }

    private PerformanceSummaryListsDto buildSummaryLists(List<TopicAnalyticsDto> topics) {
        List<TopicAnalyticsDto> strong = topics.stream()
                .filter(t -> t.getClassifications().contains("STRONG"))
                .sorted((a, b) -> Double.compare(b.getAccuracyPercentage(), a.getAccuracyPercentage()))
                .collect(Collectors.toList());

        List<TopicAnalyticsDto> weak = topics.stream()
                .filter(t -> t.getClassifications().contains("WEAK"))
                .sorted(Comparator.comparingDouble(TopicAnalyticsDto::getAccuracyPercentage))
                .collect(Collectors.toList());

        List<TopicAnalyticsDto> hard = topics.stream()
                .filter(t -> t.getClassifications().contains("HARD"))
                .sorted((a, b) -> Double.compare(b.getHardScore(), a.getHardScore()))
                .collect(Collectors.toList());

        List<TopicAnalyticsDto> needsRevision = topics.stream()
                .filter(t -> t.getClassifications().contains("NEEDS_REVISION"))
                .sorted((a, b) -> Long.compare(
                        b.getDaysSinceLastPracticed() != null ? b.getDaysSinceLastPracticed() : 0L,
                        a.getDaysSinceLastPracticed() != null ? a.getDaysSinceLastPracticed() : 0L))
                .collect(Collectors.toList());

        List<TopicAnalyticsDto> improving = topics.stream()
                .filter(t -> t.getClassifications().contains("IMPROVING"))
                .sorted((a, b) -> Double.compare(
                        b.getTrendDelta() != null ? b.getTrendDelta() : 0.0,
                        a.getTrendDelta() != null ? a.getTrendDelta() : 0.0))
                .collect(Collectors.toList());

        List<TopicAnalyticsDto> declining = topics.stream()
                .filter(t -> t.getClassifications().contains("DECLINING"))
                .sorted((a, b) -> Double.compare(
                        a.getTrendDelta() != null ? a.getTrendDelta() : 0.0,
                        b.getTrendDelta() != null ? b.getTrendDelta() : 0.0))
                .collect(Collectors.toList());

        return PerformanceSummaryListsDto.builder()
                .strongTopics(strong)
                .weakTopics(weak)
                .hardTopics(hard)
                .needsRevisionTopics(needsRevision)
                .improvingTopics(improving)
                .decliningTopics(declining)
                .build();
    }

    private List<PerformanceTimelinePointDto> buildTimelinePoints(
            List<PracticeSession> practiceSessions,
            List<TestAttempt> testAttempts) {

        List<PerformanceTimelinePointDto> timeline = new ArrayList<>();

        for (PracticeSession ps : practiceSessions.stream().limit(10).collect(Collectors.toList())) {
            LocalDateTime date = ps.getStartedAt() != null ? ps.getStartedAt() : LocalDateTime.now();
            int attempted = ps.getAttemptedQuestions() != null ? ps.getAttemptedQuestions() : 0;
            Double avgSolvingTime = null;
            if (ps.getStatus() == PracticeStatus.COMPLETED && ps.getCompletedAt() != null && attempted > 0) {
                long durationSec = Duration.between(ps.getStartedAt(), ps.getCompletedAt()).getSeconds();
                if (durationSec > 0) {
                    avgSolvingTime = roundOneDecimal(Math.min((double) durationSec / attempted, PerformanceEngineConstants.MAX_VALID_QUESTION_TIME_SECONDS));
                }
            }

            timeline.add(PerformanceTimelinePointDto.builder()
                    .type("PRACTICE")
                    .id(ps.getId())
                    .title(ps.getTopic() != null ? ps.getTopic().getName() : "Practice Session")
                    .date(date)
                    .formattedDate(date.format(TIMELINE_DATE_FORMATTER))
                    .score(ps.getScore() != null ? ps.getScore() : 0)
                    .accuracy(ps.getAccuracy() != null ? ps.getAccuracy() : 0.0)
                    .attemptedQuestions(attempted)
                    .correctAnswers(ps.getCorrectAnswers() != null ? ps.getCorrectAnswers() : 0)
                    .averageSolvingTimeSeconds(avgSolvingTime)
                    .build());
        }

        for (TestAttempt ta : testAttempts.stream().filter(t -> t.getStatus() == TestStatus.SUBMITTED).limit(10).collect(Collectors.toList())) {
            LocalDateTime date = ta.getStartTime() != null ? ta.getStartTime() : LocalDateTime.now();
            int correct = ta.getCorrectAnswers() != null ? ta.getCorrectAnswers() : 0;
            int wrong = ta.getWrongAnswers() != null ? ta.getWrongAnswers() : 0;
            int attempted = correct + wrong;

            Double avgSolvingTime = null;
            if (ta.getEndTime() != null && attempted > 0) {
                long durationSec = Duration.between(ta.getStartTime(), ta.getEndTime()).getSeconds();
                if (durationSec > 0) {
                    avgSolvingTime = roundOneDecimal(Math.min((double) durationSec / attempted, PerformanceEngineConstants.MAX_VALID_QUESTION_TIME_SECONDS));
                }
            }

            timeline.add(PerformanceTimelinePointDto.builder()
                    .type("MOCK_TEST")
                    .id(ta.getId())
                    .title(ta.getTest() != null ? ta.getTest().getTitle() : "Mock Test")
                    .date(date)
                    .formattedDate(date.format(TIMELINE_DATE_FORMATTER))
                    .score(ta.getScore() != null ? ta.getScore() : 0)
                    .accuracy(ta.getAccuracy() != null ? ta.getAccuracy() : 0.0)
                    .attemptedQuestions(attempted)
                    .correctAnswers(correct)
                    .averageSolvingTimeSeconds(avgSolvingTime)
                    .build());
        }

        timeline.sort((a, b) -> b.getDate().compareTo(a.getDate()));
        if (timeline.size() > 10) {
            timeline = timeline.subList(0, 10);
        }

        return timeline;
    }

    private List<String> generateActionableRecommendations(
            OverallPerformanceDto overall,
            PerformanceSummaryListsDto summary,
            DifficultyBreakdownDto difficulty) {

        List<String> tips = new ArrayList<>();

        if (overall.getTotalQuestionsAttempted() == 0) {
            tips.add("🚀 Start your first practice session to get real-time performance insights and analytics!");
            return tips;
        }

        // 1. Weak topics recommendation
        if (!summary.getWeakTopics().isEmpty()) {
            TopicAnalyticsDto weakest = summary.getWeakTopics().get(0);
            tips.add("🎯 Priority Focus: Revisit \"" + weakest.getTopicName() + "\" where your accuracy is currently "
                    + weakest.getAccuracyPercentage() + "%. Review fundamentals before attempting more questions.");
        }

        // 2. Needs Revision recommendation
        if (!summary.getNeedsRevisionTopics().isEmpty()) {
            TopicAnalyticsDto rev = summary.getNeedsRevisionTopics().get(0);
            tips.add("⏰ Needs Revision: You have not practiced \"" + rev.getTopicName() + "\" in "
                    + rev.getDaysSinceLastPracticed() + " days. Schedule a quick practice session to maintain mastery.");
        }

        // 3. Hard topics alert
        if (!summary.getHardTopics().isEmpty()) {
            TopicAnalyticsDto hardest = summary.getHardTopics().get(0);
            tips.add("🔥 Challenge Area: \"" + hardest.getTopicName() + "\" exhibits persistent struggle on difficult questions. Try breaking problems into step-by-step sub-parts.");
        }

        // 4. Declining trend alert
        if (!summary.getDecliningTopics().isEmpty()) {
            TopicAnalyticsDto dec = summary.getDecliningTopics().get(0);
            tips.add("📉 Accuracy Alert: Recent performance in \"" + dec.getTopicName() + "\" dropped by "
                    + Math.abs(dec.getTrendDelta()) + "%. Review explanations of recent wrong answers.");
        }

        // 5. Improving trend praise
        if (!summary.getImprovingTopics().isEmpty()) {
            TopicAnalyticsDto imp = summary.getImprovingTopics().get(0);
            tips.add("📈 Great Progress: Your accuracy in \"" + imp.getTopicName() + "\" improved by +"
                    + imp.getTrendDelta() + "%! Keep up the consistent practice.");
        }

        // 6. Strong topics recognition
        if (!summary.getStrongTopics().isEmpty()) {
            TopicAnalyticsDto strong = summary.getStrongTopics().get(0);
            tips.add("⭐ Strong Foundation: Excellent performance in \"" + strong.getTopicName() + "\" with "
                    + strong.getAccuracyPercentage() + "% accuracy. Consider advancing to Hard difficulty questions.");
        }

        // 7. Difficulty distribution balance
        if (difficulty.getHard().getTotalAttempted() == 0 && overall.getAccuracyPercentage() >= 70.0) {
            tips.add("💡 You're performing well on Easy/Medium questions. Challenge yourself by attempting Hard difficulty questions to prepare for competitive placement rounds.");
        }

        // 8. Mock test vs Practice balance
        if (overall.getMockTestsCompleted() == 0 && overall.getTotalQuestionsAttempted() >= 15) {
            tips.add("💻 You have good practice momentum. Take a full-length timed Mock Test to simulate real placement conditions.");
        }

        if (tips.isEmpty()) {
            tips.add("🚀 Consistent daily practice is key to placement success. Aim for 10-15 targeted questions daily!");
        }

        return tips;
    }

    private static double roundOneDecimal(double val) {
        return Math.round(val * 10.0) / 10.0;
    }

    private static String formatSolvingTime(Double seconds) {
        if (seconds == null || seconds <= 0.0) {
            return "N/A";
        }
        if (seconds < 60.0) {
            return String.format("%.0fs", seconds);
        }
        long mins = (long) (seconds / 60.0);
        long secs = (long) (seconds % 60.0);
        return String.format("%dm %ds", mins, secs);
    }

    // =========================================================================
    // HELPER DATA HOLDERS
    // =========================================================================

    private static class DifficultyStatsCollector {
        String difficulty;
        long attempted = 0;
        long correct = 0;
        long wrong = 0;
        double totalTimeSeconds = 0.0;
        long timedQuestions = 0;

        DifficultyStatsCollector(String difficulty) {
            this.difficulty = difficulty;
        }

        DifficultyMetricDto toDto() {
            double accuracy = attempted > 0 ? roundOneDecimal(((double) correct / attempted) * 100.0) : 0.0;
            Double avgTime = timedQuestions > 0 ? roundOneDecimal(totalTimeSeconds / timedQuestions) : null;
            return DifficultyMetricDto.builder()
                    .difficulty(difficulty)
                    .totalAttempted(attempted)
                    .totalCorrect(correct)
                    .totalWrong(wrong)
                    .accuracyPercentage(accuracy)
                    .averageSolvingTimeSeconds(avgTime)
                    .averageSolvingTimeFormatted(formatSolvingTime(avgTime))
                    .build();
        }
    }

    private static class TopicCollector {
        Topic topic;
        long attempted = 0;
        long correct = 0;
        long wrong = 0;
        int sessionCount = 0;
        long hardDifficultyAttempted = 0;
        long hardDifficultyCorrect = 0;
        LocalDateTime latestTimestamp = null;
        double totalTimeSeconds = 0.0;
        long timedQuestions = 0;
        List<ChronologicalAttempt> attempts = new ArrayList<>();

        TopicCollector(Topic topic) {
            this.topic = topic;
        }

        void addAttempt(boolean isCorrect, Difficulty diff, LocalDateTime timestamp) {
            attempted++;
            if (isCorrect) correct++; else wrong++;

            if (diff == Difficulty.HARD) {
                hardDifficultyAttempted++;
                if (isCorrect) hardDifficultyCorrect++;
            }

            if (timestamp != null) {
                if (latestTimestamp == null || timestamp.isAfter(latestTimestamp)) {
                    latestTimestamp = timestamp;
                }
                attempts.add(new ChronologicalAttempt(isCorrect, diff, timestamp));
            }
        }

        double getHardDifficultyAccuracy() {
            return hardDifficultyAttempted > 0 ? ((double) hardDifficultyCorrect / hardDifficultyAttempted) * 100.0 : 0.0;
        }
    }

    private static class ChronologicalAttempt {
        boolean isCorrect;
        Difficulty difficulty;
        LocalDateTime timestamp;

        ChronologicalAttempt(boolean isCorrect, Difficulty difficulty, LocalDateTime timestamp) {
            this.isCorrect = isCorrect;
            this.difficulty = difficulty;
            this.timestamp = timestamp;
        }
    }

    private static class TrendCalculation {
        TrendDirection trend;
        Double delta;

        TrendCalculation(TrendDirection trend, Double delta) {
            this.trend = trend;
            this.delta = delta;
        }
    }
}
