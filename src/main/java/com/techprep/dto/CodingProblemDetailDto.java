package com.techprep.dto;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CodingProblemDetailDto {
    private Long id;
    private String title;
    private String slug;
    private String difficulty;
    private String categoryTag;
    private String description;
    private String constraintsText;
    private String starterCodeJava;
    private String starterCodePython;
    private String starterCodeCpp;
    private List<CodingTestCaseDto> sampleTestCases;
    private boolean solved;
}

