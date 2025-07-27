
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

import com.felypeganzert.backend.dto.PontoCantadoDTO;
import com.felypeganzert.backend.service.PontoCantadoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pontos-cantados")
@RequiredArgsConstructor
@Tag(name = "Pontos Cantados", description = "API para gerenciamento de pontos cantados")
public class PontoCantadoController {
    private final PontoCantadoService service;

    @GetMapping
    @Operation(summary = "Listar pontos cantados ativos", description = "Retorna uma lista de todos os pontos cantados ativos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de pontos cantados ativos retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public List<PontoCantadoDTO> findAllAtivos() {
        return service.findAllAtivos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ponto cantado por ID", description = "Retorna um ponto cantado pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ponto cantado encontrado"),
        @ApiResponse(responseCode = "404", description = "Ponto cantado não encontrado")
    })
    public PontoCantadoDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar ponto cantado", description = "Cria um novo ponto cantado")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Ponto cantado criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public PontoCantadoDTO create(@Valid @RequestBody PontoCantadoDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualizar ponto cantado", description = "Atualiza um ponto cantado existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ponto cantado atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Ponto cantado não encontrado")
    })
    public PontoCantadoDTO update(@PathVariable Long id, @Valid @RequestBody PontoCantadoDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir ponto cantado", description = "Exclui um ponto cantado pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Ponto cantado excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Ponto cantado não encontrado")
    })
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
