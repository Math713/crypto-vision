package com.cryptovision.backend.controller;

import com.cryptovision.backend.dto.ChangePasswordRequest;
import com.cryptovision.backend.dto.UpdateUserRequest;
import com.cryptovision.backend.dto.UserResponseDTO;
import com.cryptovision.backend.entity.User;
import com.cryptovision.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Gerenciamento do perfil do usuário autenticado")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Retorna os dados do usuário autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido")
    })
    public ResponseEntity<UserResponseDTO> getMe(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getMe(user));
    }

    @PutMapping("/me")
    @Operation(summary = "Atualiza nome e perfil do usuário autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido")
    })
    public ResponseEntity<UserResponseDTO> updateMe(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        return ResponseEntity.ok(userService.updateMe(user, request));
    }

    @PutMapping("/me/password")
    @Operation(summary = "Altera a senha do usuário autenticado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou senha atual incorreta"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido")
    })
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        userService.changePassword(user, request);
        return ResponseEntity.noContent().build();
    }
}