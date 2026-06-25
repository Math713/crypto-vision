package com.cryptovision.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados necessários para alterar a senha")
public record ChangePasswordRequest(

        @Schema(description = "Senha atual do usuário", example = "senha123")
        @NotBlank(message = "Senha atual é obrigatória")
        String currentPassword,

        @Schema(description = "Senha  nova do usuário", example = "senha456")
        @NotBlank(message = "Nova senha é obrigatória")
        @Size(min = 8, message = "Nova senha deve ter no mínimo 8 caracteres")
        String newPassword
) {}