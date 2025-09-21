package com.felypeganzert.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.felypeganzert.backend.dto.FavoritoDTO;
import com.felypeganzert.backend.service.FavoritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/favoritos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Favoritos", description = "API para gerenciamento de favoritos")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("isAuthenticated()")
public class FavoritoController {

    private final FavoritoService service;

    @GetMapping
    @Operation(
        summary = "Listar meus favoritos",
        description = "Retorna todos os pontos cantados favoritados pelo usuário logado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de favoritos retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public List<FavoritoDTO> findMeusFavoritos() {
        return service.findMeusFavoritos();
    }

    @PostMapping("/{pontoId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Adicionar aos favoritos",
        description = "Adiciona um ponto cantado aos favoritos do usuário logado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Ponto cantado adicionado aos favoritos"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
        @ApiResponse(responseCode = "404", description = "Ponto cantado não encontrado"),
        @ApiResponse(responseCode = "400", description = "Ponto cantado já está nos favoritos")
    })
    public void adicionar(@PathVariable Long pontoId) {
        service.adicionar(pontoId);
    }

    @DeleteMapping("/{pontoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Remover dos favoritos",
        description = "Remove um ponto cantado dos favoritos do usuário logado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Ponto cantado removido dos favoritos"),
        @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public void remover(@PathVariable Long pontoId) {
        service.remover(pontoId);
    }

    @GetMapping("/count")
    @Operation(
        summary = "Contador de favoritos",
        description = "Retorna o número de pontos cantados favoritados pelo usuário logado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Contador retornado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    public long count() {
        return service.count();
    }
}