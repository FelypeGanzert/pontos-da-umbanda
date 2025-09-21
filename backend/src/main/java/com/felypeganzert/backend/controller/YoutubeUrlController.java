
package com.felypeganzert.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.felypeganzert.backend.dto.YoutubeUrlDTO;
import com.felypeganzert.backend.service.YoutubeUrlService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/youtube-urls")
@RequiredArgsConstructor
@Tag(name = "Youtube URLs", description = "API para gerenciamento de URLs do Youtube")
@PreAuthorize("permitAll()")  // Permite acesso a todos os endpoints deste controller
public class YoutubeUrlController {
    private final YoutubeUrlService service;

    @GetMapping("/ponto-cantado/{pontoCantadoId}")
    @Operation(summary = "Listar URLs por ponto cantado", description = "Retorna uma lista de URLs do Youtube associadas a um ponto cantado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de URLs retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public List<YoutubeUrlDTO> findByPontoCantado(@PathVariable Long pontoCantadoId) {
        return service.findByPontoCantado(pontoCantadoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar URL do Youtube", description = "Cria uma nova URL do Youtube")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "URL criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public YoutubeUrlDTO create(@Valid @RequestBody YoutubeUrlDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualizar URL do Youtube", description = "Atualiza uma URL do Youtube existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "URL atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "URL não encontrada")
    })
    public YoutubeUrlDTO update(@PathVariable Long id, @Valid @RequestBody YoutubeUrlDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir URL do Youtube", description = "Exclui uma URL do Youtube pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "URL excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "URL não encontrada")
    })
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
