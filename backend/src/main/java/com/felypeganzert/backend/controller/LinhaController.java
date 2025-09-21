package com.felypeganzert.backend.controller;

import com.felypeganzert.backend.dto.LinhaDTO;
import com.felypeganzert.backend.service.LinhaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/linhas")
@RequiredArgsConstructor
@Tag(name = "Linhas", description = "API para gerenciamento de linhas espirituais")
public class LinhaController {

    private final LinhaService service;

    @GetMapping
    @Operation(summary = "Listar linhas ativas", description = "Retorna uma lista de todas as linhas ativas")
    public List<LinhaDTO> findAllAtivos() {
        return service.findAllAtivos();
    }
}
