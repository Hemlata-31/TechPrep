package com.techprep.service;

import com.techprep.dto.LeaderboardResponseDto;

public interface LeaderboardService {
    LeaderboardResponseDto getLeaderboard(String timeFrame, Long categoryId, String currentUsername);
}
