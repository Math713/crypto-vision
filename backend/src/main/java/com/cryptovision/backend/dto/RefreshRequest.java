package com.cryptovision.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados necessários para atualizar o token")
public record RefreshRequest(
        @Schema(description = "Refresh Token")
        @NotBlank(message = "refresh token é obrigatório")
        String refreshToken
) {}