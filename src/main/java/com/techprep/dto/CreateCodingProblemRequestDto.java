package com.techprep.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateCodingProblemRequestDto {
    @NotBlank private String title;
    @NotBlank private String slug;
    @NotBlank private String difficulty; // EASY, MEDIUM, HARD
    @NotBlank private String categoryTag;
    @NotBlank private String description;
    private String constraintsText;
    @NotBlank private String starterCodeJava;
    @NotBlank private String starterCodePython;
    @NotBlank private String starterCodeCpp;
    @NotBlank private String driverCodeJava;
    @NotBlank private String driverCodePython;
    @NotBlank private String driverCodeCpp;

    @NotEmpty
    private List<TestCaseInputDto> testCases;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class TestCaseInputDto {
        @NotBlank private String input;
        @NotBlank private String expectedOutput;
        private String displayInput;
        private String explanation;
        private boolean sample;
    }
}

