package com.techprep.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CodingRunRequestDto {
    @NotBlank
    private String language; // JAVA, PYTHON, CPP
    @NotBlank
    private String sourceCode;
}

