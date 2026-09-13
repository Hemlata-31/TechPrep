package com.techprep.dto;

import com.techprep.entity.Difficulty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestDto {
    private Long id;

    @NotBlank(message = "Test title is required")
    private String title;

    private String description;

    private Long categoryId;
    private String categoryName;

    private Long subCategoryId;
    private String subCategoryName;

    private Long topicId;
    private String topicName;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer durationMinutes;

    @NotNull(message = "Total questions is required")
    @Min(value = 1, message = "Must have at least 1 question")
    private Integer totalQuestions;

    @NotNull(message = "Total marks is required")
    private Integer totalMarks;

    private Difficulty difficulty;
    private String selectedDifficulty;

    private Boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
