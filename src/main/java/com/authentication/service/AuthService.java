package com.authentication.service;

import com.authentication.payload.JwtAuthRequest;

public interface AuthService {
    String login(JwtAuthRequest request);
}
