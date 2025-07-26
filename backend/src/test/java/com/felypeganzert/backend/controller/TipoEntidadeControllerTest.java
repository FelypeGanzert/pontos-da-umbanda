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
import com.felypeganzert.backend.dto.TipoEntidadeDTO;
import com.felypeganzert.backend.service.TipoEntidadeService;

@WebMvcTest(TipoEntidadeController.class)
@WithMockUser
@DisplayName("Testes do TipoEntidadeController")
class TipoEntidadeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TipoEntidadeService tipoEntidadeService;

    @Test
    @DisplayName("GET /api/v1/tipos-entidade - Deve retornar lista de tipos de entidade ativos")
    void testFindAllAtivos() throws Exception {
        // Given

        LinhaDTO linhaDTO1 = LinhaDTO.builder().id(1L).nome("Linha de Oxalá").build();
        LinhaDTO linhaDTO2 = LinhaDTO.builder().id(2L).nome("Linha de Iemanjá").build();

        TipoEntidadeDTO tipoEntidadeDTO1 = TipoEntidadeDTO.builder()
                .id(1L)
                .nome("Preto Velho Teste")
                .linha(linhaDTO1)
                .descricao("Entidades de sabedoria")
                .caracteristicas("Sábios e conselheiros")
                .areaAtuacao("Cura e aconselhamento")
                .ativo(true)
                .build();

        TipoEntidadeDTO tipoEntidadeDTO2 = TipoEntidadeDTO.builder()
                .id(2L)
                .nome("Caboclo Teste")
                .linha(linhaDTO2)
                .descricao("Entidades da natureza")
                .caracteristicas("Guardiões das matas")
                .areaAtuacao("Proteção e cura")
                .ativo(true)
                .build();

        List<TipoEntidadeDTO> tiposEntidadeDTO = Arrays.asList(tipoEntidadeDTO1, tipoEntidadeDTO2);

        when(tipoEntidadeService.findAllAtivos()).thenReturn(tiposEntidadeDTO);

        // When & Then
        mockMvc.perform(get("/api/v1/tipos-entidade")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Preto Velho Teste"))
                .andExpect(jsonPath("$[0].linha.id").value(1))
                .andExpect(jsonPath("$[0].descricao").value("Entidades de sabedoria"))
                .andExpect(jsonPath("$[0].caracteristicas").value("Sábios e conselheiros"))
                .andExpect(jsonPath("$[0].areaAtuacao").value("Cura e aconselhamento"))
                .andExpect(jsonPath("$[0].ativo").value(true))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Caboclo Teste"))
                .andExpect(jsonPath("$[1].linha.id").value(2))
                .andExpect(jsonPath("$[1].descricao").value("Entidades da natureza"))
                .andExpect(jsonPath("$[1].caracteristicas").value("Guardiões das matas"))
                .andExpect(jsonPath("$[1].areaAtuacao").value("Proteção e cura"))
                .andExpect(jsonPath("$[1].ativo").value(true));

        verify(tipoEntidadeService).findAllAtivos();
        verifyNoMoreInteractions(tipoEntidadeService);
    }

    @Test
    @DisplayName("GET /api/v1/tipos-entidade - Deve retornar lista vazia quando não há tipos de entidade")
    void testFindAllAtivos_EmptyList() throws Exception {
        // Given
        List<TipoEntidadeDTO> emptyList = Collections.emptyList();
        when(tipoEntidadeService.findAllAtivos()).thenReturn(emptyList);

        // When & Then
        mockMvc.perform(get("/api/v1/tipos-entidade")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        verify(tipoEntidadeService).findAllAtivos();
        verifyNoMoreInteractions(tipoEntidadeService);
    }

    @Test
    @DisplayName("GET /api/v1/tipos-entidade - Deve retornar tipo de entidade sem relacionamento")
    void testFindAllAtivos_WithoutRelationship() throws Exception {
        // Given
        TipoEntidadeDTO tipoEntidadeDTO = TipoEntidadeDTO.builder()
                .id(1L)
                .nome("Tipo Independente Teste")
                .descricao("Entidade sem linha específica")
                .caracteristicas("Características gerais")
                .areaAtuacao("Atuação geral")
                .ativo(true)
                .build();

        List<TipoEntidadeDTO> tiposEntidadeDTO = Arrays.asList(tipoEntidadeDTO);

        when(tipoEntidadeService.findAllAtivos()).thenReturn(tiposEntidadeDTO);

        // When & Then
        mockMvc.perform(get("/api/v1/tipos-entidade")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Tipo Independente Teste"))
                .andExpect(jsonPath("$[0].linha").isEmpty())
                .andExpect(jsonPath("$[0].descricao").value("Entidade sem linha específica"))
                .andExpect(jsonPath("$[0].caracteristicas").value("Características gerais"))
                .andExpect(jsonPath("$[0].areaAtuacao").value("Atuação geral"))
                .andExpect(jsonPath("$[0].ativo").value(true));

        verify(tipoEntidadeService).findAllAtivos();
        verifyNoMoreInteractions(tipoEntidadeService);
    }
}
