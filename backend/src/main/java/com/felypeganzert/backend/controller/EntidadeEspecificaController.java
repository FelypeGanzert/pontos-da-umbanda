package com.felypeganzert.backend.controller;

import com.felypeganzert.backend.dto.EntidadeEspecificaDTO;
import com.felypeganzert.backend.service.EntidadeEspecificaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/entidades-especificas")
@RequiredArgsConstructor
@Tag(name = "Entidades Específicas", description = "API para gerenciamento de entidades específicas")
public class EntidadeEspecificaController {

    private final EntidadeEspecificaService service;

    @GetMapping
    @Operation(summary = "Listar entidades específicas ativas", description = "Retorna uma lista de todas as entidades específicas ativas")
    public List<EntidadeEspecificaDTO> findAllAtivos() {
        return service.findAllAtivos();
    }
}
