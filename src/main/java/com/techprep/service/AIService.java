package com.techprep.service;

import com.techprep.dto.AIExplanationRequestDto;
import com.techprep.dto.AIExplanationResponseDto;
import com.techprep.dto.AIGenerateRequestDto;

public interface AIService {
    AIExplanationResponseDto generateExplanation(AIExplanationRequestDto request);

    /**
     * Generates MCQs via Gemini, deduplicates, and saves valid questions
     * directly into the Question table.
     *
     * @return the number of questions actually saved
     */
    int generateAndSaveQuestions(AIGenerateRequestDto request);
}
