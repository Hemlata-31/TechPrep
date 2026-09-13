package com.techprep.service;

import com.techprep.dto.*;

import java.util.List;

public interface TestService {
    // Admin / Instructor test management
    TestDto createTest(TestDto testDto);
    TestDto updateTest(Long id, TestDto testDto);
    void deleteTest(Long id);
    TestDto toggleTestActiveStatus(Long id);

    // Student & Public Test Queries
    List<TestDto> getAllActiveTests();
    TestDto getTestById(Long id);

    // Test Execution Flow
    TestAttemptResultDto startTest(Long testId, String username);
    TestQuestionDto getTestQuestionByOrder(Long attemptId, Integer questionOrder, String username);
    TestQuestionDto answerTestQuestion(Long attemptId, Integer questionOrder, AnswerQuestionRequestDto request, String username);
    TestAttemptResultDto submitTest(Long attemptId, String username);
    TestAttemptResultDto getTestAttemptResult(Long attemptId, String username);
    List<TestAttemptSummaryDto> getStudentTestHistory(String username);
}
