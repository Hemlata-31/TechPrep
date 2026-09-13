package com.techprep.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaderboardResponseDto {

    private LeaderboardEntryDto currentUserEntry;
    private List<LeaderboardEntryDto> leaderboardEntries;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LeaderboardEntryDto {
        private Integer rank;
        private Long userId;
        private String studentName;
        private Long totalScore;
        private Double accuracy;
        private Long questionsAttempted;
        private Boolean isCurrentUser;
    }
}
