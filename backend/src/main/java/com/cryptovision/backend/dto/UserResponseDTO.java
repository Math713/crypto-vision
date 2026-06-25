package com.cryptovision.backend.dto;

import com.cryptovision.backend.enums.ProfileType;

import java.time.LocalDateTime;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        ProfileType profileType,
        LocalDateTime createdAt
) {}