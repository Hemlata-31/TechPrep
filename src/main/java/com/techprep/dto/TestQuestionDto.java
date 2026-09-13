package com.techprep.dto;

import com.techprep.entity.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestQuestionDto {
    private Long answerId;
    private Long questionId;
    private Integer questionOrder;
    private Integer totalQuestions;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private Difficulty difficulty;
    private Integer marks;
    
    private Boolean isAnswered;
    private String selectedAnswer;
    private Boolean isMarkedForReview;

    // Revealed ONLY after test is submitted or in review mode
    private String correctAnswer;
    private Boolean isCorrect;
    private String explanation;
}
