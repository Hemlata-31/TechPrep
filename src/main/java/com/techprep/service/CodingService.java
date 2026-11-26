package com.techprep.service;

import com.techprep.dto.*;
import java.util.List;

public interface CodingService {
    List<CodingProblemSummaryDto> getAllProblems(String username);
    CodingProblemDetailDto getProblemBySlug(String slug, String username);
    CodingRunResponseDto runSampleTests(Long problemId, CodingRunRequestDto request);
    CodingRunResponseDto submitSolution(Long problemId, CodingRunRequestDto request, String username);
    List<CodingSubmissionDto> getSubmissions(Long problemId, String username);
    CodingProblemSummaryDto createProblem(CreateCodingProblemRequestDto request);
}

