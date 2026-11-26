package com.techprep.dto;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CodingRunResponseDto {
    private String status; // ACCEPTED, WRONG_ANSWER, COMPILATION_ERROR, RUNTIME_ERROR, API_ERROR
    private int passedCount;
    private int totalCount;
    private String errorMessage;
    private List<TestCaseResultDto> results;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class TestCaseResultDto {
        private int testCaseNumber;
        private boolean passed;
        private boolean sample;
        private String input;
        private String expectedOutput;
        private String actualOutput;
        private String error;
    }
}

