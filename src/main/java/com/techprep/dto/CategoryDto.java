package com.techprep.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CategoryDto {
    private Long id;

    @NotBlank(message = "Category name is required")
    private String name;

    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Optional list of basic subcategory DTOs could be included if needed
    // But keeping it simple for now to avoid circular dependencies in serialization
}
