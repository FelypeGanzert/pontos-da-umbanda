package com.felypeganzert.backend.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.felypeganzert.backend.dto.LinhaDTO;
import com.felypeganzert.backend.service.LinhaService;

@WebMvcTest(LinhaController.class)
@WithMockUser
@DisplayName("Testes do LinhaController")
class LinhaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LinhaService linhaService;

    @Test
    @DisplayName("GET /api/v1/linhas/ativos - Deve retornar lista de linhas ativas")
    void testFindAllAtivos() throws Exception {
        // Given
        LinhaDTO linhaDTO1 = LinhaDTO.builder()
                .id(1L)
                .nome("Linha de Oxalá")
                .orixaRegenteId(1L)
                .descricao("Linha da paz")
                .numeroOrdem(1)
                .ativo(true)
                .build();

        LinhaDTO linhaDTO2 = LinhaDTO.builder()
                .id(2L)
                .nome("Linha de Iemanjá")
                .orixaRegenteId(2L)
                .descricao("Linha do mar")
                .numeroOrdem(2)
                .ativo(true)
                .build();

        List<LinhaDTO> linhaDTOs = Arrays.asList(linhaDTO1, linhaDTO2);

        when(linhaService.findAllAtivos()).thenReturn(linhaDTOs);

        // When & Then
        mockMvc.perform(get("/api/v1/linhas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Linha de Oxalá"))
                .andExpect(jsonPath("$[0].orixaRegenteId").value(1))
                .andExpect(jsonPath("$[0].descricao").value("Linha da paz"))
                .andExpect(jsonPath("$[0].numeroOrdem").value(1))
                .andExpect(jsonPath("$[0].ativo").value(true))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Linha de Iemanjá"))
                .andExpect(jsonPath("$[1].orixaRegenteId").value(2))
                .andExpect(jsonPath("$[1].descricao").value("Linha do mar"))
                .andExpect(jsonPath("$[1].numeroOrdem").value(2))
                .andExpect(jsonPath("$[1].ativo").value(true));

        verify(linhaService).findAllAtivos();
        verifyNoMoreInteractions(linhaService);
    }

    @Test
    @DisplayName("GET /api/v1/linhas/ativos - Deve retornar lista vazia quando não há linhas ativas")
    void testFindAllAtivos_QuandoNaoHaLinhasAtivas() throws Exception {
        // Given
        List<LinhaDTO> linhaDTOsVazias = Collections.emptyList();

        when(linhaService.findAllAtivos()).thenReturn(linhaDTOsVazias);

        // When & Then
        mockMvc.perform(get("/api/v1/linhas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        verify(linhaService).findAllAtivos();
        verifyNoMoreInteractions(linhaService);
    }
}
