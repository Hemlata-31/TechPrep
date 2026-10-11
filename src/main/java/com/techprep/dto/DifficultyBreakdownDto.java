package com.techprep.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DifficultyBreakdownDto {
    private DifficultyMetricDto easy;
    private DifficultyMetricDto medium;
    private DifficultyMetricDto hard;
}
