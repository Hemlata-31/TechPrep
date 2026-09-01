package com.techprep.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TopicDto {
    private Long id;

    @NotBlank(message = "Topic name is required")
    private String name;

    private String description;
    
    private Long subCategoryId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
