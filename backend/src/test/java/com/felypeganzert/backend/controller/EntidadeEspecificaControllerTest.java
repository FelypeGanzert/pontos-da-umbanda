package com.felypeganzert.backend.controller;

import com.felypeganzert.backend.dto.EntidadeEspecificaDTO;
import com.felypeganzert.backend.service.EntidadeEspecificaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EntidadeEspecificaController.class)
@WithMockUser
@DisplayName("Testes do EntidadeEspecificaController")
class EntidadeEspecificaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EntidadeEspecificaService service;

    @Test
    @DisplayName("GET /api/v1/entidades-especificas - Deve retornar lista de entidades específicas ativas")
    void testFindAllAtivos() throws Exception {
        // Given
        EntidadeEspecificaDTO dto1 = EntidadeEspecificaDTO.builder().id(1L).nome("Entidade 1").ativo(true).build();
        EntidadeEspecificaDTO dto2 = EntidadeEspecificaDTO.builder().id(2L).nome("Entidade 2").ativo(true).build();
        List<EntidadeEspecificaDTO> dtos = Arrays.asList(dto1, dto2);

        when(service.findAllAtivos()).thenReturn(dtos);

        // When & Then
        mockMvc.perform(get("/api/v1/entidades-especificas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Entidade 1"))
                .andExpect(jsonPath("$[0].ativo").value(true))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Entidade 2"))
                .andExpect(jsonPath("$[1].ativo").value(true));

        verify(service).findAllAtivos();
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("GET /api/v1/entidades-especificas - Deve retornar lista vazia quando não há entidades específicas ativas")
    void testFindAllAtivos_EmptyList() throws Exception {
        // Given
        List<EntidadeEspecificaDTO> emptyList = Collections.emptyList();
        when(service.findAllAtivos()).thenReturn(emptyList);

        // When & Then
        mockMvc.perform(get("/api/v1/entidades-especificas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        verify(service).findAllAtivos();
        verifyNoMoreInteractions(service);
    }
}
