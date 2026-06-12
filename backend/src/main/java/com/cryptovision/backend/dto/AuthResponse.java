package com.cryptovision.backend.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {}