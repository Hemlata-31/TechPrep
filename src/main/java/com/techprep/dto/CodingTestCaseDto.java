package com.techprep.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CodingTestCaseDto {
    private Long id;
    private String displayInput;
    private String expectedOutput;
    private String explanation;
}

