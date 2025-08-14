package com.felypeganzert.backend.controller;

import com.felypeganzert.backend.dto.FinalidadeDTO;
import com.felypeganzert.backend.service.FinalidadeService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FinalidadeController.class)
@WithMockUser
@DisplayName("Testes do FinalidadeController")
class FinalidadeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FinalidadeService service;

    @Test
    @DisplayName("GET /api/v1/finalidades - Deve retornar lista de finalidades ativas")
    void testFindAllAtivos() throws Exception {
        // Given
        FinalidadeDTO dto1 = FinalidadeDTO.builder().id(1L).nome("Abertura").ativo(true).build();
        FinalidadeDTO dto2 = FinalidadeDTO.builder().id(2L).nome("Trabalho").ativo(true).build();
        List<FinalidadeDTO> dtos = Arrays.asList(dto1, dto2);

        when(service.findAllAtivos()).thenReturn(dtos);

        // When & Then
        mockMvc.perform(get("/api/v1/finalidades")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Abertura"))
                .andExpect(jsonPath("$[0].ativo").value(true))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Trabalho"))
                .andExpect(jsonPath("$[1].ativo").value(true));

        verify(service).findAllAtivos();
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("GET /api/v1/finalidades - Deve retornar lista vazia quando não há finalidades ativas")
    void testFindAllAtivosEmptyList() throws Exception {
        // Given
        List<FinalidadeDTO> emptyList = Collections.emptyList();
        when(service.findAllAtivos()).thenReturn(emptyList);

        // When & Then
        mockMvc.perform(get("/api/v1/finalidades")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        verify(service).findAllAtivos();
        verifyNoMoreInteractions(service);
    }
}
