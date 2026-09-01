package com.techprep.dto;

import com.techprep.entity.Difficulty;
import lombok.Data;

@Data
public class QuestionStudentResponseDto {
    private Long id;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    // Notice: correctAnswer and explanation are excluded for security
    private Difficulty difficulty;
    private Integer marks;
    private Long topicId;
}
