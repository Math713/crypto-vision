package com.cryptovision.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados necessários para cadastrar um usuário")
public record RegisterRequest(
        @Schema(description = "Nome do usuário", example = "João Lucas")
        @NotBlank(message = "nome é obrigatório")
        String name,

        @Schema(description = "Email do usuário", example = "joao@gmail.com")
        @NotBlank(message = "email é obrigatório")
        @Email(message = "email com formato inválido")
        String email,

        @Schema(description = "Senha do usuário", example = "senha123")
        @NotBlank(message = "senha é obrigatória")
        @Size(min = 8, message = "senha tem que ter no mínimo 8 caracteres")
        String password
) {}