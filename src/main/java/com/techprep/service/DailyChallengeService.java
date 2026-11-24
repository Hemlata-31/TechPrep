package com.techprep.service;

import com.techprep.dto.DailyChallengeDto;
import com.techprep.dto.DailyChallengeSubmitRequestDto;
import com.techprep.dto.DailyChallengeSubmitResponseDto;

public interface DailyChallengeService {
    DailyChallengeDto getTodayChallenge(String username);
    DailyChallengeSubmitResponseDto submitTodayChallenge(DailyChallengeSubmitRequestDto request, String username);
}
