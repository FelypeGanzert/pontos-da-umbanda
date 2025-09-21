package com.felypeganzert.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felypeganzert.backend.dto.auth.LoginRequest;
import com.felypeganzert.backend.dto.auth.LoginResponse;
import com.felypeganzert.backend.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "API para autenticação de usuários")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(
        summary = "Realizar login", description = "Autentica um usuário com email e senha, retornando um token JWT para acesso aos recursos protegidos"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso, token JWT retornado"),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas" ),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"
        )
    })
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

        @GetMapping("/teste-login")
        @Operation(
            summary = "Testar login JWT",
            description = "Retorna o e-mail do usuário logado. Requer autenticação JWT.",
            security = {@io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")}
        )
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário autenticado, e-mail retornado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado ou token inválido")
        })
        @PreAuthorize("isAuthenticated()")
        public String testeLogin(@org.springframework.security.core.annotation.AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails) {
            return userDetails.getUsername();
        }
}