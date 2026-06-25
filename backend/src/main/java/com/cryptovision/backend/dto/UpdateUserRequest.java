package com.cryptovision.backend.dto;

import com.cryptovision.backend.enums.ProfileType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados necessários para atualizar u usuário")
public record UpdateUserRequest(
        @Schema(description = "Nome do usuário", example = "João Lucas")
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 2, max = 255, message = "Nome deve ter entre 2 e 255 caracteres")
        String name,

        @Schema(description = "Perfil do usuário", example = "INICIANTE")
        @NotNull(message = "Perfil é obrigatório")
        ProfileType profileType
) {}