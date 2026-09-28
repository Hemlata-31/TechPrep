package com.techprep.dto;

import com.techprep.entity.Difficulty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AIGenerateRequestDto {

    @NotNull(message = "Topic ID is required")
    private Long topicId;

    @NotNull(message = "Difficulty is required")
    private Difficulty difficulty;

    @NotNull(message = "Number of questions is required")
    @Min(value = 1, message = "Must generate at least 1 question")
    @Max(value = 50, message = "Cannot generate more than 50 questions at a time")
    private Integer numberOfQuestions;
}
