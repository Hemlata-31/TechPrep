package com.techprep.dto;

import com.techprep.entity.Difficulty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class QuestionAdminResponseDto {
    private Long id;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer;
    private String explanation;
    private Difficulty difficulty;
    private Integer marks;
    private Boolean active;
    private Long topicId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
