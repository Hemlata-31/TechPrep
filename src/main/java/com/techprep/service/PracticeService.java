package com.techprep.service;

import com.techprep.dto.*;

import java.util.List;

public interface PracticeService {
    PracticeSessionSummaryDto startPracticeSession(StartPracticeRequestDto request, String username);
    
    PracticeQuestionDto getQuestionByOrder(Long sessionId, Integer questionOrder, String username);
    
    SubmitAnswerResponseDto submitAnswer(Long sessionId, Long attemptId, SubmitAnswerRequestDto request, String username);
    
    PracticeSessionResultDto completePracticeSession(Long sessionId, String username);
    
    PracticeSessionResultDto getPracticeSessionResult(Long sessionId, String username);
    
    List<PracticeSessionSummaryDto> getStudentPracticeHistory(String username);
    
    StudentPracticeStatsDto getStudentPracticeStats(String username);
}
