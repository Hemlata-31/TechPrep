package com.techprep.service.impl;

import com.techprep.dto.DailyChallengeDto;
import com.techprep.dto.DailyChallengeSubmitRequestDto;
import com.techprep.dto.DailyChallengeSubmitResponseDto;
import com.techprep.entity.DailyChallenge;
import com.techprep.entity.DailyChallengeAttempt;
import com.techprep.entity.Question;
import com.techprep.entity.User;
import com.techprep.exception.ResourceNotFoundException;
import com.techprep.repository.DailyChallengeAttemptRepository;
import com.techprep.repository.DailyChallengeRepository;
import com.techprep.repository.QuestionRepository;
import com.techprep.repository.UserRepository;
import com.techprep.service.DailyChallengeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DailyChallengeServiceImpl implements DailyChallengeService {

    private final DailyChallengeRepository dailyChallengeRepository;
    private final DailyChallengeAttemptRepository attemptRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public DailyChallengeDto getTodayChallenge(String username) {
        User user = getUserByUsername(username);
        LocalDate today = LocalDate.now();

        DailyChallenge challenge = getOrCreateTodayChallenge(today);
        Question question = challenge.getQuestion();

        Optional<DailyChallengeAttempt> attemptOpt = attemptRepository.findByUserIdAndChallengeId(user.getId(), challenge.getId());

        long participants = attemptRepository.countByChallengeId(challenge.getId());
        long correctCount = attemptRepository.countByChallengeIdAndIsCorrectTrue(challenge.getId());
        double successRate = participants > 0 ? Math.round(((double) correctCount / participants) * 1000.0) / 10.0 : 0.0;

        boolean isAttempted = attemptOpt.isPresent();

        String subCatName = question.getTopic() != null && question.getTopic().getSubCategory() != null
                ? question.getTopic().getSubCategory().getName() : "";
        String catName = question.getTopic() != null && question.getTopic().getSubCategory() != null && question.getTopic().getSubCategory().getCategory() != null
                ? question.getTopic().getSubCategory().getCategory().getName() : "";
        String topicName = question.getTopic() != null ? question.getTopic().getName() : "";

        DailyChallengeDto.DailyChallengeDtoBuilder builder = DailyChallengeDto.builder()
                .challengeId(challenge.getId())
                .challengeDate(challenge.getChallengeDate())
                .questionId(question.getId())
                .questionText(question.getQuestionText())
                .optionA(question.getOptionA())
                .optionB(question.getOptionB())
                .optionC(question.getOptionC())
                .optionD(question.getOptionD())
                .topicName(topicName)
                .subCategoryName(subCatName)
                .categoryName(catName)
                .difficulty(question.getDifficulty() != null ? question.getDifficulty().name() : "MEDIUM")
                .bonusPoints(challenge.getBonusPoints())
                .isAttempted(isAttempted)
                .participantsToday(participants)
                .totalSolvedCorrect(correctCount)
                .successRate(successRate);

        if (isAttempted) {
            DailyChallengeAttempt attempt = attemptOpt.get();
            builder.userSelectedAnswer(attempt.getSelectedAnswer())
                    .isCorrect(attempt.getIsCorrect())
                    .timeTakenSeconds(attempt.getTimeTakenSeconds())
                    .correctAnswer(question.getCorrectAnswer())
                    .explanation(question.getExplanation());
        }

        return builder.build();
    }

    @Override
    @Transactional
    public DailyChallengeSubmitResponseDto submitTodayChallenge(DailyChallengeSubmitRequestDto request, String username) {
        User user = getUserByUsername(username);
        LocalDate today = LocalDate.now();

        DailyChallenge challenge = getOrCreateTodayChallenge(today);

        if (attemptRepository.existsByUserIdAndChallengeId(user.getId(), challenge.getId())) {
            throw new IllegalStateException("You have already completed today's Daily Challenge!");
        }

        Question question = challenge.getQuestion();
        String selected = request.getSelectedAnswer() != null ? request.getSelectedAnswer().trim().toUpperCase() : "";
        boolean isCorrect = question.getCorrectAnswer() != null && question.getCorrectAnswer().equalsIgnoreCase(selected);

        DailyChallengeAttempt attempt = DailyChallengeAttempt.builder()
                .user(user)
                .challenge(challenge)
                .selectedAnswer(selected)
                .isCorrect(isCorrect)
                .timeTakenSeconds(request.getTimeTakenSeconds() != null ? request.getTimeTakenSeconds() : 0)
                .attemptedAt(LocalDateTime.now())
                .build();
        attemptRepository.save(attempt);

        // Update streak & today's progress
        if (user.getLastActiveDate() == null || user.getLastActiveDate().isBefore(today)) {
            if (user.getLastActiveDate() != null && user.getLastActiveDate().equals(today.minusDays(1))) {
                user.setCurrentStreak(user.getCurrentStreak() + 1);
            } else {
                user.setCurrentStreak(1);
            }
            if (user.getCurrentStreak() > user.getLongestStreak()) {
                user.setLongestStreak(user.getCurrentStreak());
            }
            user.setQuestionsAttemptedToday(1);
            user.setLastActiveDate(today);
        } else {
            user.setQuestionsAttemptedToday(user.getQuestionsAttemptedToday() + 1);
        }
        userRepository.save(user);

        long participants = attemptRepository.countByChallengeId(challenge.getId());
        long correctCount = attemptRepository.countByChallengeIdAndIsCorrectTrue(challenge.getId());
        double successRate = participants > 0 ? Math.round(((double) correctCount / participants) * 1000.0) / 10.0 : 0.0;

        int bonus = isCorrect ? challenge.getBonusPoints() : 0;
        String msg = isCorrect
                ? "Awesome job! You solved today's Daily Challenge and earned " + bonus + " points!"
                : "Good try! Keep up the daily practice to strengthen your skills.";

        return DailyChallengeSubmitResponseDto.builder()
                .isCorrect(isCorrect)
                .selectedAnswer(selected)
                .correctAnswer(question.getCorrectAnswer())
                .explanation(question.getExplanation())
                .bonusPointsAwarded(bonus)
                .currentStreak(user.getCurrentStreak())
                .longestStreak(user.getLongestStreak())
                .message(msg)
                .participantsToday(participants)
                .totalSolvedCorrect(correctCount)
                .successRate(successRate)
                .build();
    }

    private synchronized DailyChallenge getOrCreateTodayChallenge(LocalDate today) {
        return dailyChallengeRepository.findByChallengeDate(today).orElseGet(() -> {
            long totalActive = questionRepository.countByActiveTrue();
            if (totalActive == 0) {
                throw new IllegalStateException("No active questions available to create Daily Challenge.");
            }

            // Deterministic rotation based on epoch day
            long epochDay = today.toEpochDay();
            int pageIndex = (int) (Math.abs(epochDay * 37 + 13) % totalActive);

            Page<Question> page = questionRepository.findByActiveTrue(PageRequest.of(pageIndex, 1));
            Question selectedQuestion;
            if (page.hasContent()) {
                selectedQuestion = page.getContent().get(0);
            } else {
                selectedQuestion = questionRepository.findAll(PageRequest.of(0, 1)).getContent().get(0);
            }

            DailyChallenge newChallenge = DailyChallenge.builder()
                    .challengeDate(today)
                    .question(selectedQuestion)
                    .bonusPoints(15)
                    .createdAt(LocalDateTime.now())
                    .build();

            return dailyChallengeRepository.save(newChallenge);
        });
    }

    private User getUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + username));
    }
}
