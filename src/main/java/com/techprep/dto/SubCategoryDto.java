package com.techprep.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SubCategoryDto {
    private Long id;

    @NotBlank(message = "SubCategory name is required")
    private String name;

    private String description;
    
    // We can just include the categoryId for creation/updates
    private Long categoryId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
