package com.techprep.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CodingProblemSummaryDto {
    private Long id;
    private String title;
    private String slug;
    private String difficulty;
    private String categoryTag;
    private boolean solved;
}

