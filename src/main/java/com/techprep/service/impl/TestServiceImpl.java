package com.techprep.service.impl;

import com.techprep.dto.*;
import com.techprep.entity.*;
import com.techprep.exception.ResourceNotFoundException;
import com.techprep.repository.*;
import com.techprep.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final TestQuestionRepository testQuestionRepository;
    private final TestAttemptRepository testAttemptRepository;
    private final TestQuestionAnswerRepository testQuestionAnswerRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final TopicRepository topicRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TestDto createTest(TestDto dto) {
        Category category = null;
        if (dto.getCategoryId() != null) {
            category = categoryRepository.findById(dto.getCategoryId()).orElse(null);
        }
        SubCategory subCategory = null;
        if (dto.getSubCategoryId() != null) {
            subCategory = subCategoryRepository.findById(dto.getSubCategoryId()).orElse(null);
        }
        Topic topic = null;
        if (dto.getTopicId() != null) {
            topic = topicRepository.findById(dto.getTopicId()).orElse(null);
        }

        String diffStr = dto.getSelectedDifficulty() != null ? dto.getSelectedDifficulty().toUpperCase() : "ALL";
        Difficulty difficultyEnum = Difficulty.MEDIUM; // default fallback enum
        if (!"ALL".equals(diffStr)) {
            try {
                difficultyEnum = Difficulty.valueOf(diffStr);
            } catch (Exception ignored) {}
        }

        TestEntity test = TestEntity.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .category(category)
                .subCategory(subCategory)
                .topic(topic)
                .durationMinutes(dto.getDurationMinutes())
                .totalQuestions(dto.getTotalQuestions())
                .totalMarks(dto.getTotalMarks())
                .difficulty(difficultyEnum)
                .selectedDifficulty(diffStr)
                .active(dto.getActive() != null ? dto.getActive() : true)
                .build();

        TestEntity savedTest = testRepository.save(test);

        // Fetch candidate questions from database
        List<Question> candidateQuestions = fetchCandidateQuestions(category, subCategory, topic, diffStr);

        if (candidateQuestions.isEmpty()) {
            throw new IllegalArgumentException("No matching active questions found in the Question Bank to build this test.");
        }

        Collections.shuffle(candidateQuestions);
        int limit = Math.min(dto.getTotalQuestions(), candidateQuestions.size());
        List<Question> selectedQuestions = candidateQuestions.subList(0, limit);

        savedTest.setTotalQuestions(limit);

        List<TestQuestion> tqList = new ArrayList<>();
        int order = 1;
        int accumulatedMarks = 0;
        for (Question q : selectedQuestions) {
            TestQuestion tq = TestQuestion.builder()
                    .test(savedTest)
                    .question(q)
                    .questionOrder(order++)
                    .build();
            tqList.add(tq);
            accumulatedMarks += q.getMarks();
        }
        testQuestionRepository.saveAll(tqList);

        if (dto.getTotalMarks() == null || dto.getTotalMarks() <= 0) {
            savedTest.setTotalMarks(accumulatedMarks);
        }
        savedTest = testRepository.save(savedTest);

        return mapToDto(savedTest);
    }

    @Override
    @Transactional
    public TestDto updateTest(Long id, TestDto dto) {
        TestEntity test = testRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test not found with id: " + id));

        test.setTitle(dto.getTitle());
        test.setDescription(dto.getDescription());
        test.setDurationMinutes(dto.getDurationMinutes());
        test.setActive(dto.getActive());

        TestEntity updated = testRepository.save(test);
        return mapToDto(updated);
    }

    @Override
    @Transactional
    public void deleteTest(Long id) {
        TestEntity test = testRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test not found with id: " + id));
        testRepository.delete(test);
    }

    @Override
    @Transactional
    public TestDto toggleTestActiveStatus(Long id) {
        TestEntity test = testRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test not found with id: " + id));
        test.setActive(!test.getActive());
        return mapToDto(testRepository.save(test));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestDto> getAllActiveTests() {
        return testRepository.findByActiveTrue().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TestDto getTestById(Long id) {
        TestEntity test = testRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test not found with id: " + id));
        return mapToDto(test);
    }

    @Override
    @Transactional
    public TestAttemptResultDto startTest(Long testId, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        TestEntity test = testRepository.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("Test not found: " + testId));

        if (!test.getActive()) {
            throw new IllegalStateException("This mock test is currently inactive.");
        }

        // Return ongoing test attempt if available
        Optional<TestAttempt> existingOpt = testAttemptRepository.findByUserIdAndTestIdAndStatus(user.getId(), testId, TestStatus.IN_PROGRESS);
        if (existingOpt.isPresent()) {
            TestAttempt existing = existingOpt.get();
            // Check if timer expired
            if (isTestTimerExpired(existing)) {
                return autoSubmitTest(existing);
            }
            return buildAttemptResultDto(existing, false);
        }

        // Create new test attempt
        TestAttempt attempt = TestAttempt.builder()
                .user(user)
                .test(test)
                .startTime(LocalDateTime.now())
                .status(TestStatus.IN_PROGRESS)
                .score(0)
                .correctAnswers(0)
                .wrongAnswers(0)
                .unattemptedQuestions(test.getTotalQuestions())
                .accuracy(0.0)
                .percentage(0.0)
                .build();

        TestAttempt savedAttempt = testAttemptRepository.save(attempt);

        List<TestQuestion> testQuestions = testQuestionRepository.findByTestIdOrderByQuestionOrderAsc(testId);
        List<TestQuestionAnswer> answers = new ArrayList<>();
        for (TestQuestion tq : testQuestions) {
            TestQuestionAnswer answer = TestQuestionAnswer.builder()
                    .testAttempt(savedAttempt)
                    .question(tq.getQuestion())
                    .questionOrder(tq.getQuestionOrder())
                    .isMarkedForReview(false)
                    .marksObtained(0)
                    .build();
            answers.add(answer);
        }
        testQuestionAnswerRepository.saveAll(answers);

        return buildAttemptResultDto(savedAttempt, false);
    }

    @Override
    @Transactional(readOnly = true)
    public TestQuestionDto getTestQuestionByOrder(Long attemptId, Integer questionOrder, String username) {
        TestAttempt attempt = verifyAttemptOwner(attemptId, username);

        if (attempt.getStatus() == TestStatus.IN_PROGRESS && isTestTimerExpired(attempt)) {
            // Note: Auto-submit occurs during submission or status fetch
        }

        TestQuestionAnswer tqa = testQuestionAnswerRepository.findByTestAttemptIdAndQuestionOrder(attemptId, questionOrder)
                .orElseThrow(() -> new ResourceNotFoundException("Question #" + questionOrder + " not found in attempt " + attemptId));

        boolean revealAnswers = (attempt.getStatus() != TestStatus.IN_PROGRESS);
        return mapToQuestionDto(tqa, attempt.getTest().getTotalQuestions(), revealAnswers);
    }

    @Override
    @Transactional
    public TestQuestionDto answerTestQuestion(Long attemptId, Integer questionOrder, AnswerQuestionRequestDto request, String username) {
        TestAttempt attempt = verifyAttemptOwner(attemptId, username);

        if (attempt.getStatus() != TestStatus.IN_PROGRESS) {
            throw new IllegalStateException("Test has already been submitted or expired.");
        }

        if (isTestTimerExpired(attempt)) {
            autoSubmitTest(attempt);
            throw new IllegalStateException("Test time has expired. Your test has been submitted automatically.");
        }

        TestQuestionAnswer tqa = testQuestionAnswerRepository.findByTestAttemptIdAndQuestionOrder(attemptId, questionOrder)
                .orElseThrow(() -> new ResourceNotFoundException("Question #" + questionOrder + " not found in attempt " + attemptId));

        if (request.getSelectedAnswer() != null) {
            String selected = request.getSelectedAnswer().trim().toUpperCase();
            tqa.setSelectedAnswer(selected.isEmpty() ? null : selected);
        }
        if (request.getMarkForReview() != null) {
            tqa.setIsMarkedForReview(request.getMarkForReview());
        }
        tqa.setAnsweredAt(LocalDateTime.now());
        testQuestionAnswerRepository.save(tqa);

        return mapToQuestionDto(tqa, attempt.getTest().getTotalQuestions(), false);
    }

    @Override
    @Transactional
    public TestAttemptResultDto submitTest(Long attemptId, String username) {
        TestAttempt attempt = verifyAttemptOwner(attemptId, username);
        if (attempt.getStatus() == TestStatus.IN_PROGRESS) {
            return processTestSubmission(attempt, TestStatus.SUBMITTED);
        }
        return buildAttemptResultDto(attempt, true);
    }

    @Override
    @Transactional(readOnly = true)
    public TestAttemptResultDto getTestAttemptResult(Long attemptId, String username) {
        TestAttempt attempt = verifyAttemptOwner(attemptId, username);
        boolean reveal = attempt.getStatus() != TestStatus.IN_PROGRESS;
        return buildAttemptResultDto(attempt, reveal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestAttemptSummaryDto> getStudentTestHistory(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        List<TestAttempt> list = testAttemptRepository.findByUserIdOrderByStartTimeDesc(user.getId());
        return list.stream().map(a -> TestAttemptSummaryDto.builder()
                .id(a.getId())
                .testId(a.getTest().getId())
                .testTitle(a.getTest().getTitle())
                .durationMinutes(a.getTest().getDurationMinutes())
                .startTime(a.getStartTime())
                .endTime(a.getEndTime())
                .score(a.getScore())
                .totalMarks(a.getTest().getTotalMarks())
                .accuracy(a.getAccuracy())
                .percentage(a.getPercentage())
                .status(a.getStatus())
                .build()
        ).collect(Collectors.toList());
    }

    private TestAttemptResultDto autoSubmitTest(TestAttempt attempt) {
        return processTestSubmission(attempt, TestStatus.EXPIRED);
    }

    private TestAttemptResultDto processTestSubmission(TestAttempt attempt, TestStatus finalStatus) {
        attempt.setStatus(finalStatus);
        attempt.setEndTime(LocalDateTime.now());

        List<TestQuestionAnswer> answers = testQuestionAnswerRepository.findByTestAttemptIdOrderByQuestionOrderAsc(attempt.getId());

        int correct = 0;
        int wrong = 0;
        int unattempted = 0;
        int totalScore = 0;

        for (TestQuestionAnswer ans : answers) {
            Question q = ans.getQuestion();
            if (ans.getSelectedAnswer() != null && !ans.getSelectedAnswer().trim().isEmpty()) {
                boolean isCorrect = q.getCorrectAnswer().equalsIgnoreCase(ans.getSelectedAnswer().trim());
                ans.setIsCorrect(isCorrect);
                if (isCorrect) {
                    correct++;
                    int marks = q.getMarks() != null ? q.getMarks() : 1;
                    ans.setMarksObtained(marks);
                    totalScore += marks;
                } else {
                    wrong++;
                    ans.setMarksObtained(0);
                }
            } else {
                ans.setIsCorrect(false);
                ans.setMarksObtained(0);
                unattempted++;
            }
            testQuestionAnswerRepository.save(ans);
        }

        int totalAttempted = correct + wrong;
        double accuracy = totalAttempted > 0 ? ((double) correct / totalAttempted) * 100.0 : 0.0;
        int maxMarks = attempt.getTest().getTotalMarks() != null && attempt.getTest().getTotalMarks() > 0 
                ? attempt.getTest().getTotalMarks() : Math.max(1, attempt.getTest().getTotalQuestions());
        double percentage = ((double) totalScore / maxMarks) * 100.0;

        attempt.setScore(totalScore);
        attempt.setCorrectAnswers(correct);
        attempt.setWrongAnswers(wrong);
        attempt.setUnattemptedQuestions(unattempted);
        attempt.setAccuracy(Math.round(accuracy * 10.0) / 10.0);
        attempt.setPercentage(Math.round(percentage * 10.0) / 10.0);

        testAttemptRepository.save(attempt);

        return buildAttemptResultDto(attempt, true);
    }

    private boolean isTestTimerExpired(TestAttempt attempt) {
        if (attempt.getStartTime() == null) return false;
        long durationSec = attempt.getTest().getDurationMinutes() * 60L;
        long elapsedSec = Duration.between(attempt.getStartTime(), LocalDateTime.now()).getSeconds();
        return elapsedSec >= durationSec;
    }

    private TestAttempt verifyAttemptOwner(Long attemptId, String username) {
        TestAttempt attempt = testAttemptRepository.findById(attemptId)
                .orElseThrow(() -> new ResourceNotFoundException("Test attempt not found: " + attemptId));
        if (!attempt.getUser().getEmail().equalsIgnoreCase(username)) {
            throw new IllegalArgumentException("Unauthorized access to test attempt " + attemptId);
        }
        return attempt;
    }

    private List<Question> fetchCandidateQuestions(Category category, SubCategory subCategory, Topic topic, String difficultyStr) {
        Difficulty diff = null;
        if (!"ALL".equals(difficultyStr)) {
            try {
                diff = Difficulty.valueOf(difficultyStr);
            } catch (Exception ignored) {}
        }

        if (topic != null) {
            return diff != null ? questionRepository.findByTopicIdAndDifficulty(topic.getId(), diff, PageRequest.of(0, 100)).getContent()
                    : questionRepository.findByTopicIdAndActiveTrue(topic.getId(), PageRequest.of(0, 100)).getContent();
        }

        if (subCategory != null) {
            List<Topic> topics = topicRepository.findBySubCategoryId(subCategory.getId());
            List<Question> result = new ArrayList<>();
            for (Topic t : topics) {
                if (diff != null) {
                    result.addAll(questionRepository.findByTopicIdAndDifficulty(t.getId(), diff, PageRequest.of(0, 100)).getContent());
                } else {
                    result.addAll(questionRepository.findByTopicIdAndActiveTrue(t.getId(), PageRequest.of(0, 100)).getContent());
                }
            }
            return result;
        }

        if (category != null) {
            List<SubCategory> subs = subCategoryRepository.findByCategoryId(category.getId());
            List<Question> result = new ArrayList<>();
            for (SubCategory s : subs) {
                List<Topic> topics = topicRepository.findBySubCategoryId(s.getId());
                for (Topic t : topics) {
                    if (diff != null) {
                        result.addAll(questionRepository.findByTopicIdAndDifficulty(t.getId(), diff, PageRequest.of(0, 100)).getContent());
                    } else {
                        result.addAll(questionRepository.findByTopicIdAndActiveTrue(t.getId(), PageRequest.of(0, 100)).getContent());
                    }
                }
            }
            return result;
        }

        // Global candidate questions
        return questionRepository.findAll().stream().filter(Question::getActive).collect(Collectors.toList());
    }

    private TestDto mapToDto(TestEntity test) {
        return TestDto.builder()
                .id(test.getId())
                .title(test.getTitle())
                .description(test.getDescription())
                .categoryId(test.getCategory() != null ? test.getCategory().getId() : null)
                .categoryName(test.getCategory() != null ? test.getCategory().getName() : "All Categories")
                .subCategoryId(test.getSubCategory() != null ? test.getSubCategory().getId() : null)
                .subCategoryName(test.getSubCategory() != null ? test.getSubCategory().getName() : "All SubCategories")
                .topicId(test.getTopic() != null ? test.getTopic().getId() : null)
                .topicName(test.getTopic() != null ? test.getTopic().getName() : "All Topics")
                .durationMinutes(test.getDurationMinutes())
                .totalQuestions(test.getTotalQuestions())
                .totalMarks(test.getTotalMarks())
                .difficulty(test.getDifficulty())
                .selectedDifficulty(test.getSelectedDifficulty())
                .active(test.getActive())
                .createdAt(test.getCreatedAt())
                .updatedAt(test.getUpdatedAt())
                .build();
    }

    private TestQuestionDto mapToQuestionDto(TestQuestionAnswer tqa, int totalQuestions, boolean revealAnswers) {
        Question q = tqa.getQuestion();
        boolean isAnswered = tqa.getSelectedAnswer() != null && !tqa.getSelectedAnswer().trim().isEmpty();

        return TestQuestionDto.builder()
                .answerId(tqa.getId())
                .questionId(q.getId())
                .questionOrder(tqa.getQuestionOrder())
                .totalQuestions(totalQuestions)
                .questionText(q.getQuestionText())
                .optionA(q.getOptionA())
                .optionB(q.getOptionB())
                .optionC(q.getOptionC())
                .optionD(q.getOptionD())
                .difficulty(q.getDifficulty())
                .marks(q.getMarks())
                .isAnswered(isAnswered)
                .selectedAnswer(tqa.getSelectedAnswer())
                .isMarkedForReview(tqa.getIsMarkedForReview())
                .correctAnswer(revealAnswers ? q.getCorrectAnswer() : null)
                .isCorrect(revealAnswers ? tqa.getIsCorrect() : null)
                .explanation(revealAnswers ? q.getExplanation() : null)
                .build();
    }

    private TestAttemptResultDto buildAttemptResultDto(TestAttempt attempt, boolean revealAnswers) {
        long durationSec = attempt.getTest().getDurationMinutes() * 60L;
        LocalDateTime endOrNow = attempt.getEndTime() != null ? attempt.getEndTime() : LocalDateTime.now();
        long elapsedSec = Math.min(durationSec, Duration.between(attempt.getStartTime(), endOrNow).getSeconds());
        long remainingSec = Math.max(0, durationSec - elapsedSec);

        List<TestQuestionAnswer> tqaList = testQuestionAnswerRepository.findByTestAttemptIdOrderByQuestionOrderAsc(attempt.getId());
        List<TestQuestionDto> questionDtos = tqaList.stream()
                .map(tqa -> mapToQuestionDto(tqa, attempt.getTest().getTotalQuestions(), revealAnswers))
                .collect(Collectors.toList());

        int attempted = (int) tqaList.stream().filter(tqa -> tqa.getSelectedAnswer() != null && !tqa.getSelectedAnswer().trim().isEmpty()).count();

        return TestAttemptResultDto.builder()
                .attemptId(attempt.getId())
                .testId(attempt.getTest().getId())
                .testTitle(attempt.getTest().getTitle())
                .categoryName(attempt.getTest().getCategory() != null ? attempt.getTest().getCategory().getName() : "General Mock Test")
                .startTime(attempt.getStartTime())
                .endTime(attempt.getEndTime())
                .timeTakenSeconds(elapsedSec)
                .remainingSeconds(remainingSec)
                .totalQuestions(attempt.getTest().getTotalQuestions())
                .attemptedQuestions(attempted)
                .correctAnswers(attempt.getCorrectAnswers())
                .wrongAnswers(attempt.getWrongAnswers())
                .unattemptedQuestions(attempt.getTest().getTotalQuestions() - attempted)
                .score(attempt.getScore())
                .totalMarks(attempt.getTest().getTotalMarks())
                .accuracy(attempt.getAccuracy())
                .percentage(attempt.getPercentage())
                .status(attempt.getStatus())
                .questions(questionDtos)
                .build();
    }
}
