package com.techprep.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techprep.dto.*;
import com.techprep.entity.*;
import com.techprep.exception.ResourceNotFoundException;
import com.techprep.repository.QuestionRepository;
import com.techprep.repository.TopicRepository;
import com.techprep.service.AIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiAIServiceImpl implements AIService {

    private final QuestionRepository questionRepository;
    private final TopicRepository topicRepository;
    private final ObjectMapper objectMapper;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    // ========================
    // Part 1 – AI Explanation
    // ========================

    @Override
    public AIExplanationResponseDto generateExplanation(AIExplanationRequestDto request) {
        String prompt = buildExplanationPrompt(request);
        String aiResponse = callGeminiApi(prompt);
        return AIExplanationResponseDto.builder()
                .aiExplanation(aiResponse)
                .build();
    }

    private String buildExplanationPrompt(AIExplanationRequestDto request) {
        return "You are a helpful placement preparation tutor for engineering students. " +
                "A student has just attempted the following MCQ question. " +
                "Please provide a clear, concise, and beginner-friendly explanation " +
                "of why the correct answer is correct and why the other options are wrong. " +
                "Keep the explanation focused on placement/interview preparation. " +
                "Use simple language.\n\n" +
                "Question: " + request.getQuestionText() + "\n" +
                "A) " + request.getOptionA() + "\n" +
                "B) " + request.getOptionB() + "\n" +
                "C) " + request.getOptionC() + "\n" +
                "D) " + request.getOptionD() + "\n" +
                "Correct Answer: " + request.getCorrectAnswer() + "\n" +
                (request.getExplanation() != null ? "Existing Explanation: " + request.getExplanation() + "\n" : "") +
                "\nProvide a detailed but simple explanation in 3-5 sentences.";
    }

    // ==============================
    // Part 2 – AI Question Generation (saves directly to Question table)
    // ==============================

    @Override
    @Transactional
    public int generateAndSaveQuestions(AIGenerateRequestDto request) {
        Topic topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id", request.getTopicId()));

        String categoryName = topic.getSubCategory().getCategory().getName();
        String subCategoryName = topic.getSubCategory().getName();
        String topicName = topic.getName();

        int totalNeeded = request.getNumberOfQuestions();
        int batchSize = 10; // Gemini returns reliable JSON for up to 10 at a time

        List<Map<String, Object>> allParsedQuestions = new ArrayList<>();
        int remaining = totalNeeded;
        while (remaining > 0) {
            int count = Math.min(remaining, batchSize);
            String prompt = buildGenerationPrompt(categoryName, subCategoryName, topicName,
                    request.getDifficulty().name(), count);
            try {
                String aiResponse = callGeminiApi(prompt);
                List<Map<String, Object>> batch = parseGeneratedQuestions(aiResponse);
                allParsedQuestions.addAll(batch);
            } catch (Exception e) {
                log.warn("Batch generation failed (remaining={}): {}", remaining, e.getMessage());
            }
            remaining -= count;
        }

        int defaultMarks = request.getDifficulty() == Difficulty.EASY ? 1
                : request.getDifficulty() == Difficulty.MEDIUM ? 2 : 3;

        int savedCount = 0;
        for (Map<String, Object> qMap : allParsedQuestions) {
            try {
                String questionText = getString(qMap, "questionText");
                String optionA     = getString(qMap, "optionA");
                String optionB     = getString(qMap, "optionB");
                String optionC     = getString(qMap, "optionC");
                String optionD     = getString(qMap, "optionD");
                String correctAns  = getString(qMap, "correctAnswer");

                // Validate required fields
                if (questionText == null || optionA == null || optionB == null
                        || optionC == null || optionD == null || correctAns == null) {
                    log.warn("Skipping AI question due to missing fields");
                    continue;
                }

                // Validate correct answer is one of A/B/C/D
                if (!correctAns.matches("[A-D]")) {
                    log.warn("Skipping AI question with invalid correctAnswer: {}", correctAns);
                    continue;
                }

                // Duplicate detection: skip if a question with the same text already exists
                if (questionRepository.existsByTopicIdAndQuestionTextIgnoreCase(topic.getId(), questionText)) {
                    log.info("Skipping duplicate AI question for topic {}: {}", topic.getId(), questionText);
                    continue;
                }

                Question question = Question.builder()
                        .questionText(questionText)
                        .optionA(optionA)
                        .optionB(optionB)
                        .optionC(optionC)
                        .optionD(optionD)
                        .correctAnswer(correctAns)
                        .explanation(getString(qMap, "explanation"))
                        .difficulty(request.getDifficulty())
                        .marks(getInteger(qMap, "marks", defaultMarks))
                        .active(true)
                        .topic(topic)
                        .build();

                questionRepository.save(question);
                savedCount++;

            } catch (Exception e) {
                log.warn("Skipping invalid AI-generated question: {}", e.getMessage());
            }
        }

        log.info("AI generation complete: {} questions saved to topic {} ({})",
                savedCount, topic.getId(), topicName);
        return savedCount;
    }

    private String buildGenerationPrompt(String category, String subCategory, String topic,
                                          String difficulty, int count) {
        return "You are an expert placement preparation question generator for engineering students.\n" +
                "Generate exactly " + count + " multiple-choice questions (MCQs) for placement preparation.\n\n" +
                "Category: " + category + "\n" +
                "SubCategory: " + subCategory + "\n" +
                "Topic: " + topic + "\n" +
                "Difficulty: " + difficulty + "\n\n" +
                "Requirements:\n" +
                "- Each question must have exactly 4 options (A, B, C, D)\n" +
                "- Correct answer must be exactly one of the letters: A, B, C, or D (single character)\n" +
                "- Include a brief explanation for each question\n" +
                "- Set marks based on difficulty: EASY=1, MEDIUM=2, HARD=3\n" +
                "- Questions should be suitable for campus placement exams\n" +
                "- Do NOT repeat or rephrase the same question\n\n" +
                "IMPORTANT: Respond with ONLY a valid JSON array, no other text. Each object must have these exact fields:\n" +
                "[\n" +
                "  {\n" +
                "    \"questionText\": \"...\",\n" +
                "    \"optionA\": \"...\",\n" +
                "    \"optionB\": \"...\",\n" +
                "    \"optionC\": \"...\",\n" +
                "    \"optionD\": \"...\",\n" +
                "    \"correctAnswer\": \"A or B or C or D\",\n" +
                "    \"explanation\": \"...\",\n" +
                "    \"marks\": 1\n" +
                "  }\n" +
                "]\n" +
                "Return ONLY the JSON array. No markdown, no code fences, no extra text.";
    }

    private List<Map<String, Object>> parseGeneratedQuestions(String aiResponse) {
        try {
            String jsonContent = aiResponse.trim();

            // Remove markdown code fences if present
            if (jsonContent.startsWith("```")) {
                int firstNewline = jsonContent.indexOf('\n');
                int lastFence = jsonContent.lastIndexOf("```");
                if (firstNewline != -1 && lastFence > firstNewline) {
                    jsonContent = jsonContent.substring(firstNewline + 1, lastFence).trim();
                }
            }

            // Find the JSON array
            int startIdx = jsonContent.indexOf('[');
            int endIdx = jsonContent.lastIndexOf(']');
            if (startIdx != -1 && endIdx != -1 && endIdx > startIdx) {
                jsonContent = jsonContent.substring(startIdx, endIdx + 1);
            }

            return objectMapper.readValue(jsonContent, new TypeReference<List<Map<String, Object>>>() {});
        } catch (JsonProcessingException e) {
            log.error("Failed to parse AI-generated questions JSON: {}", e.getMessage());
            throw new RuntimeException("Failed to parse AI response. The AI did not return valid JSON.");
        }
    }

    // ==============================
    // Gemini REST API Call
    // ==============================

    private String callGeminiApi(String prompt) {
        if (geminiApiKey == null || geminiApiKey.isBlank()) {
            throw new RuntimeException("Gemini API key is not configured. Set the GEMINI_API_KEY environment variable.");
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = geminiApiUrl + "?key=" + geminiApiKey;

        Map<String, Object> textPart = Map.of("text", prompt);
        Map<String, Object> contentPart = Map.of("parts", List.of(textPart));
        Map<String, Object> requestBody = Map.of("contents", List.of(contentPart));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return extractTextFromGeminiResponse(response.getBody());
            } else {
                throw new RuntimeException("Gemini API returned status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Gemini API call failed: {}", e.getMessage());
            throw new RuntimeException("Failed to get response from Gemini AI: " + e.getMessage());
        }
    }

    private String extractTextFromGeminiResponse(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode candidates = root.path("candidates");
            if (candidates.isArray() && candidates.size() > 0) {
                JsonNode content = candidates.get(0).path("content");
                JsonNode parts = content.path("parts");
                if (parts.isArray() && parts.size() > 0) {
                    return parts.get(0).path("text").asText();
                }
            }
            throw new RuntimeException("Unexpected Gemini API response format");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse Gemini API response");
        }
    }

    // ==============================
    // Helpers
    // ==============================

    private String getString(Map<String, Object> map, String key) {
        Object val = map.get(key);
        return val != null ? val.toString().trim() : null;
    }

    private Integer getInteger(Map<String, Object> map, String key, int defaultVal) {
        Object val = map.get(key);
        if (val instanceof Number) {
            return ((Number) val).intValue();
        }
        try {
            return val != null ? Integer.parseInt(val.toString()) : defaultVal;
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }
}
