package com.felypeganzert.backend.controller;

import com.felypeganzert.backend.dto.UsuarioDTO;
import com.felypeganzert.backend.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "API para gerenciamento de usuários")
@PreAuthorize("permitAll()")  // Permite acesso a todos os endpoints deste controller
public class UsuarioController {
    private final UsuarioService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar usuário", description = "Cria um novo usuário")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public UsuarioDTO create(@Valid @RequestBody UsuarioDTO dto) {
        return service.save(dto);
    }

    @GetMapping("/me")
    @Operation(summary = "Buscar usuário autenticado", description = "Retorna os dados do usuário autenticado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Dados do usuário retornados com sucesso"),
        @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    public UsuarioDTO getMe() {
        return service.getAuthenticatedUser();
    }

    @PutMapping("/me")
    @Operation(summary = "Atualizar próprio perfil", description = "Atualiza os dados do usuário autenticado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    public UsuarioDTO updateMe(@Valid @RequestBody UsuarioDTO dto) {
        return service.updateAuthenticatedUser(dto);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir própria conta", description = "Remove a conta do usuário autenticado")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
        @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    public void deleteMe() {
        service.deleteAuthenticatedUser();
    }
}
