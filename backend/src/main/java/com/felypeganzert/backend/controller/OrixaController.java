package com.felypeganzert.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felypeganzert.backend.dto.OrixaDTO;
import com.felypeganzert.backend.service.OrixaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orixas")
@RequiredArgsConstructor
@Tag(name = "Orixás", description = "API para gerenciamento de Orixás da Umbanda")
public class OrixaController {

    private final OrixaService orixaService;

    @GetMapping
    @Operation(summary = "Listar Orixás ativos", description = "Retorna uma lista de todos os Orixás ativos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de Orixás ativos retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public List<OrixaDTO> findAllAtivos() {
        return orixaService.findAllAtivos();
    }
    
}
