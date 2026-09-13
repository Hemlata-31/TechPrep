package com.techprep.service;

import com.techprep.dto.DashboardProgressDto;

public interface ProgressService {
    DashboardProgressDto getStudentProgress(String username);
}
