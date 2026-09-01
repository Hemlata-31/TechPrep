package com.techprep.service;

import com.techprep.dto.AuthRequest;
import com.techprep.dto.AuthResponse;
import com.techprep.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse authenticate(AuthRequest request);
}
