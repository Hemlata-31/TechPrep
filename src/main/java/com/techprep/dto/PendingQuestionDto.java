package com.techprep.dto;

import com.techprep.entity.Difficulty;
import com.techprep.entity.PendingQuestionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PendingQuestionDto {
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
    private PendingQuestionStatus status;
    private Long topicId;
    private String topicName;
    private String subCategoryName;
    private String categoryName;
    private LocalDateTime createdAt;
}
