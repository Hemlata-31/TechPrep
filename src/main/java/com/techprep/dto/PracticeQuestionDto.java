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
public class PracticeQuestionDto {
    private Long attemptId;
    private Long questionId;
    private Integer questionIndex; // 1 to totalQuestions
    private Integer totalQuestions;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private Difficulty difficulty;
    private Integer marks;
    
    // Status if already answered
    private Boolean isAnswered;
    private String selectedAnswer;
    private String correctAnswer; // null if not answered yet
    private Boolean isCorrect;     // null if not answered yet
    private String explanation;    // null if not answered yet
}
