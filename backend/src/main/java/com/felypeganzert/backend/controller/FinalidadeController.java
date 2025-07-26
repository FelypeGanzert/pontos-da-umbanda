package com.felypeganzert.backend.controller;

import com.felypeganzert.backend.dto.FinalidadeDTO;
import com.felypeganzert.backend.service.FinalidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/finalidades")
@RequiredArgsConstructor
@Tag(name = "Finalidades", description = "API para gerenciamento de finalidades")
public class FinalidadeController {
    private final FinalidadeService service;

    @GetMapping
    @Operation(summary = "Listar finalidades ativas", description = "Retorna uma lista de todas as finalidades ativas")
    public List<FinalidadeDTO> findAllAtivos() {
        return service.findAllAtivos();
    }
}
