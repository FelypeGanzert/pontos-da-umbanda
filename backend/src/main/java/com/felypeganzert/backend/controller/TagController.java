
package com.felypeganzert.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.felypeganzert.backend.dto.TagDTO;
import com.felypeganzert.backend.service.TagService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
@Tag(name = "Tags", description = "API para gerenciamento de tags")
public class TagController {
    private final TagService service;

    @GetMapping
    @Operation(summary = "Listar tags", description = "Retorna uma lista de todas as tags")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de tags retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public List<TagDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tag por ID", description = "Retorna uma tag pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tag encontrada"),
        @ApiResponse(responseCode = "404", description = "Tag não encontrada")
    })
    public TagDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar tag", description = "Cria uma nova tag")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Tag criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public TagDTO create(@Valid @RequestBody TagDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualizar tag", description = "Atualiza uma tag existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tag atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Tag não encontrada")
    })
    public TagDTO update(@PathVariable Long id, @Valid @RequestBody TagDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir tag", description = "Exclui uma tag pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Tag excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Tag não encontrada")
    })
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
