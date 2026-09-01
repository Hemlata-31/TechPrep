package com.techprep.service.impl;

import com.techprep.dto.*;
import com.techprep.entity.*;
import com.techprep.exception.ResourceNotFoundException;
import com.techprep.repository.PracticeAttemptRepository;
import com.techprep.repository.PracticeSessionRepository;
import com.techprep.repository.QuestionRepository;
import com.techprep.repository.TopicRepository;
import com.techprep.repository.UserRepository;
import com.techprep.service.PracticeService;
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
public class PracticeServiceImpl implements PracticeService {

    private final PracticeSessionRepository sessionRepository;
    private final PracticeAttemptRepository attemptRepository;
    private final QuestionRepository questionRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public PracticeSessionSummaryDto startPracticeSession(StartPracticeRequestDto request, String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + username));

        Topic topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: " + request.getTopicId()));

        String diffStr = request.getDifficulty() != null ? request.getDifficulty().toUpperCase() : "ALL";
        Difficulty difficultyEnum = null;
        if (!"ALL".equals(diffStr)) {
            try {
                difficultyEnum = Difficulty.valueOf(diffStr);
            } catch (IllegalArgumentException e) {
                diffStr = "ALL";
            }
        }

        // Fetch questions for this topic
        List<Question> questions;
        if (difficultyEnum != null) {
            questions = questionRepository.findByTopicIdAndDifficulty(topic.getId(), difficultyEnum, PageRequest.of(0, 100)).getContent();
        } else {
            questions = questionRepository.findByTopicIdAndActiveTrue(topic.getId(), PageRequest.of(0, 100)).getContent();
        }

        // Filter active questions
        questions = questions.stream().filter(Question::getActive).collect(Collectors.toList());

        if (questions.isEmpty()) {
            throw new IllegalArgumentException("No active questions available for topic: " + topic.getName() + " with difficulty: " + diffStr);
        }

        // Shuffle questions for practice variety
        Collections.shuffle(questions);

        int requestedNum = (request.getNumberOfQuestions() != null && request.getNumberOfQuestions() > 0) 
                ? request.getNumberOfQuestions() : 10;
        int limit = Math.min(requestedNum, questions.size());
        List<Question> selectedQuestions = questions.subList(0, limit);

        // Create practice session
        PracticeSession session = PracticeSession.builder()
                .user(user)
                .topic(topic)
                .difficulty(difficultyEnum)
                .selectedDifficulty(diffStr)
                .totalQuestions(limit)
                .attemptedQuestions(0)
                .correctAnswers(0)
                .wrongAnswers(0)
                .score(0)
                .accuracy(0.0)
                .status(PracticeStatus.IN_PROGRESS)
                .build();

        PracticeSession savedSession = sessionRepository.save(session);

        // Create attempts for each question
        List<PracticeAttempt> attempts = new ArrayList<>();
        for (int i = 0; i < selectedQuestions.size(); i++) {
            Question q = selectedQuestions.get(i);
            PracticeAttempt attempt = PracticeAttempt.builder()
                    .session(savedSession)
                    .question(q)
                    .questionOrder(i + 1)
                    .marksObtained(0)
                    .build();
            attempts.add(attempt);
        }
        attemptRepository.saveAll(attempts);

        return mapToSummaryDto(savedSession);
    }

    @Override
    @Transactional(readOnly = true)
    public PracticeQuestionDto getQuestionByOrder(Long sessionId, Integer questionOrder, String username) {
        PracticeSession session = verifySessionOwner(sessionId, username);

        PracticeAttempt attempt = attemptRepository.findBySessionIdAndQuestionOrder(sessionId, questionOrder)
                .orElseThrow(() -> new ResourceNotFoundException("Question #" + questionOrder + " not found in session " + sessionId));

        Question question = attempt.getQuestion();

        boolean isAnswered = attempt.getSelectedAnswer() != null;

        return PracticeQuestionDto.builder()
                .attemptId(attempt.getId())
                .questionId(question.getId())
                .questionIndex(questionOrder)
                .totalQuestions(session.getTotalQuestions())
                .questionText(question.getQuestionText())
                .optionA(question.getOptionA())
                .optionB(question.getOptionB())
                .optionC(question.getOptionC())
                .optionD(question.getOptionD())
                .difficulty(question.getDifficulty())
                .marks(question.getMarks())
                .isAnswered(isAnswered)
                .selectedAnswer(attempt.getSelectedAnswer())
                // Security check: ONLY reveal correct answer and explanation IF already answered
                .correctAnswer(isAnswered ? question.getCorrectAnswer() : null)
                .isCorrect(isAnswered ? attempt.getIsCorrect() : null)
                .explanation(isAnswered ? question.getExplanation() : null)
                .build();
    }

    @Override
    @Transactional
    public SubmitAnswerResponseDto submitAnswer(Long sessionId, Long attemptId, SubmitAnswerRequestDto request, String username) {
        PracticeSession session = verifySessionOwner(sessionId, username);

        if (session.getStatus() == PracticeStatus.COMPLETED) {
            throw new IllegalStateException("Cannot submit answer to a completed practice session.");
        }

        PracticeAttempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new ResourceNotFoundException("Attempt not found with id: " + attemptId));

        if (!attempt.getSession().getId().equals(sessionId)) {
            throw new IllegalArgumentException("Attempt does not belong to session " + sessionId);
        }

        boolean firstTimeAnswering = (attempt.getSelectedAnswer() == null);
        String selectedAns = request.getSelectedAnswer() != null ? request.getSelectedAnswer().toUpperCase().trim() : "";
        
        Question question = attempt.getQuestion();
        boolean isCorrect = question.getCorrectAnswer().equalsIgnoreCase(selectedAns);

        attempt.setSelectedAnswer(selectedAns);
        attempt.setIsCorrect(isCorrect);
        attempt.setAnsweredAt(LocalDateTime.now());
        attempt.setMarksObtained(isCorrect ? question.getMarks() : 0);
        attemptRepository.save(attempt);

        // Update session metrics
        if (firstTimeAnswering) {
            session.setAttemptedQuestions(session.getAttemptedQuestions() + 1);
        }
        
        // Recalculate stats from all attempts
        List<PracticeAttempt> allAttempts = attemptRepository.findBySessionIdOrderByQuestionOrderAsc(sessionId);
        int correctCount = 0;
        int wrongCount = 0;
        int totalScore = 0;
        int attemptedCount = 0;

        for (PracticeAttempt att : allAttempts) {
            if (att.getSelectedAnswer() != null) {
                attemptedCount++;
                if (Boolean.TRUE.equals(att.getIsCorrect())) {
                    correctCount++;
                    totalScore += att.getMarksObtained();
                } else {
                    wrongCount++;
                }
            }
        }

        session.setAttemptedQuestions(attemptedCount);
        session.setCorrectAnswers(correctCount);
        session.setWrongAnswers(wrongCount);
        session.setScore(totalScore);
        double accuracy = attemptedCount > 0 ? ((double) correctCount / attemptedCount) * 100.0 : 0.0;
        session.setAccuracy(Math.round(accuracy * 10.0) / 10.0);
        sessionRepository.save(session);

        return SubmitAnswerResponseDto.builder()
                .attemptId(attempt.getId())
                .questionIndex(attempt.getQuestionOrder())
                .selectedAnswer(selectedAns)
                .correctAnswer(question.getCorrectAnswer())
                .isCorrect(isCorrect)
                .explanation(question.getExplanation())
                .marksObtained(attempt.getMarksObtained())
                .build();
    }

    @Override
    @Transactional
    public PracticeSessionResultDto completePracticeSession(Long sessionId, String username) {
        PracticeSession session = verifySessionOwner(sessionId, username);

        if (session.getStatus() != PracticeStatus.COMPLETED) {
            session.setStatus(PracticeStatus.COMPLETED);
            session.setCompletedAt(LocalDateTime.now());
            sessionRepository.save(session);
        }

        return mapToResultDto(session);
    }

    @Override
    @Transactional(readOnly = true)
    public PracticeSessionResultDto getPracticeSessionResult(Long sessionId, String username) {
        PracticeSession session = verifySessionOwner(sessionId, username);
        return mapToResultDto(session);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PracticeSessionSummaryDto> getStudentPracticeHistory(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + username));

        List<PracticeSession> sessions = sessionRepository.findByUserIdOrderByStartedAtDesc(user.getId());
        return sessions.stream().map(this::mapToSummaryDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StudentPracticeStatsDto getStudentPracticeStats(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + username));

        Long completedCount = sessionRepository.countByUserIdAndStatus(user.getId(), PracticeStatus.COMPLETED);
        Long attemptedCount = sessionRepository.sumAttemptedQuestionsByUserId(user.getId());
        Long correctCount = sessionRepository.sumCorrectAnswersByUserId(user.getId());
        Long wrongCount = sessionRepository.sumWrongAnswersByUserId(user.getId());

        double overallAccuracy = (attemptedCount != null && attemptedCount > 0)
                ? ((double) correctCount / attemptedCount) * 100.0 : 0.0;

        List<PracticeSession> recentList = sessionRepository.findByUserIdOrderByStartedAtDesc(user.getId(), PageRequest.of(0, 5)).getContent();
        List<PracticeSessionSummaryDto> recentSummaries = recentList.stream().map(this::mapToSummaryDto).collect(Collectors.toList());

        return StudentPracticeStatsDto.builder()
                .totalSessionsCompleted(completedCount != null ? completedCount : 0L)
                .totalQuestionsAttempted(attemptedCount != null ? attemptedCount : 0L)
                .totalCorrectAnswers(correctCount != null ? correctCount : 0L)
                .totalWrongAnswers(wrongCount != null ? wrongCount : 0L)
                .overallAccuracy(Math.round(overallAccuracy * 10.0) / 10.0)
                .recentSessions(recentSummaries)
                .build();
    }

    private PracticeSession verifySessionOwner(Long sessionId, String username) {
        PracticeSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Practice session not found with id: " + sessionId));

        if (!session.getUser().getEmail().equalsIgnoreCase(username)) {
            throw new IllegalArgumentException("Unauthorized access to practice session " + sessionId);
        }

        return session;
    }

    private PracticeSessionSummaryDto mapToSummaryDto(PracticeSession session) {
        Topic topic = session.getTopic();
        SubCategory subCat = topic.getSubCategory();
        Category cat = subCat.getCategory();

        return PracticeSessionSummaryDto.builder()
                .id(session.getId())
                .topicId(topic.getId())
                .topicName(topic.getName())
                .subCategoryName(subCat.getName())
                .categoryName(cat.getName())
                .difficulty(session.getSelectedDifficulty())
                .totalQuestions(session.getTotalQuestions())
                .attemptedQuestions(session.getAttemptedQuestions())
                .correctAnswers(session.getCorrectAnswers())
                .wrongAnswers(session.getWrongAnswers())
                .score(session.getScore())
                .accuracy(session.getAccuracy())
                .status(session.getStatus())
                .startedAt(session.getStartedAt())
                .completedAt(session.getCompletedAt())
                .build();
    }

    private PracticeSessionResultDto mapToResultDto(PracticeSession session) {
        Topic topic = session.getTopic();
        SubCategory subCat = topic.getSubCategory();
        Category cat = subCat.getCategory();

        int unattempted = session.getTotalQuestions() - session.getAttemptedQuestions();

        long timeTakenSeconds = 0;
        if (session.getStartedAt() != null) {
            LocalDateTime end = session.getCompletedAt() != null ? session.getCompletedAt() : LocalDateTime.now();
            timeTakenSeconds = Duration.between(session.getStartedAt(), end).getSeconds();
        }

        return PracticeSessionResultDto.builder()
                .sessionId(session.getId())
                .topicId(topic.getId())
                .topicName(topic.getName())
                .subCategoryName(subCat.getName())
                .categoryName(cat.getName())
                .difficulty(session.getSelectedDifficulty())
                .totalQuestions(session.getTotalQuestions())
                .attemptedQuestions(session.getAttemptedQuestions())
                .correctAnswers(session.getCorrectAnswers())
                .wrongAnswers(session.getWrongAnswers())
                .unattemptedQuestions(unattempted)
                .score(session.getScore())
                .accuracy(session.getAccuracy())
                .status(session.getStatus())
                .startedAt(session.getStartedAt())
                .completedAt(session.getCompletedAt())
                .timeTakenSeconds(timeTakenSeconds)
                .build();
    }
}
