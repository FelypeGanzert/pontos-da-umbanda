package com.felypeganzert.backend.controller;

import com.felypeganzert.backend.dto.TipoEntidadeDTO;
import com.felypeganzert.backend.service.TipoEntidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tipos-entidade")
@RequiredArgsConstructor
@Tag(name = "Tipos de Entidade", description = "API para gerenciamento de tipos de entidade")
public class TipoEntidadeController {

    private final TipoEntidadeService service;

    @GetMapping
    @Operation(summary = "Listar tipos de entidade ativos", description = "Retorna uma lista de todos os tipos de entidade ativos")
    public List<TipoEntidadeDTO> findAllAtivos() {
        return service.findAllAtivos();
    }
}
