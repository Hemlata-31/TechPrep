package com.techprep.dto;

import com.techprep.entity.Difficulty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SavedQuestionDto {
    private Long id;
    private Long questionId;
    private String questionText;
    private String categoryName;
    private String topicName;
    private Difficulty difficulty;
    private Integer marks;
    private LocalDateTime savedAt;
}
