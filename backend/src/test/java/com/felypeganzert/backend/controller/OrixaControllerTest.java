package com.felypeganzert.backend.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.felypeganzert.backend.dto.OrixaDTO;
import com.felypeganzert.backend.service.OrixaService;

/**
 * Testes unitários para OrixaController.
 */
@WebMvcTest(OrixaController.class)
@AutoConfigureWebMvc
@DisplayName("Testes do OrixaController")
class OrixaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrixaService orixaService;

    private List<OrixaDTO> orixasAtivos;
    private OrixaDTO oxalaDTO;
    private OrixaDTO ogumDTO;

    @BeforeEach
    void setUp() {
        LocalDateTime agora = LocalDateTime.now();

        oxalaDTO = OrixaDTO.builder()
                .id(1L)
                .nome("Oxalá")
                .nomeAfricano("Obatalá")
                .descricao("Orixá da criação, paz e sabedoria")
                .corPrimaria("#FFFFFF")
                .corSecundaria("#E6E6FA")
                .diaSemana("Sexta-feira")
                .elemento("Ar")
                .sincretismo("Jesus Cristo")
                .iconeRepresentacao("Pomba branca")
                .dataCriacao(agora)
                .ativo(true)
                .build();

        ogumDTO = OrixaDTO.builder()
                .id(2L)
                .nome("Ogum")
                .nomeAfricano("Ogún")
                .descricao("Orixá da guerra, tecnologia e caminhos")
                .corPrimaria("#FF0000")
                .corSecundaria("#8B0000")
                .diaSemana("Terça-feira")
                .elemento("Fogo")
                .sincretismo("São Jorge")
                .iconeRepresentacao("Espada")
                .dataCriacao(agora)
                .ativo(true)
                .build();

        orixasAtivos = Arrays.asList(oxalaDTO, ogumDTO);
    }

    @Test
    @WithMockUser
    @DisplayName("GET /api/v1/orixas deve retornar lista de Orixás ativos")
    void deveRetornarListaDeOrixasAtivos() throws Exception {
        // Given
        when(orixaService.findAllAtivos()).thenReturn(orixasAtivos);

        // When & Then
        mockMvc.perform(get("/api/v1/orixas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Oxalá"))
                .andExpect(jsonPath("$[0].nomeAfricano").value("Obatalá"))
                .andExpect(jsonPath("$[0].elemento").value("Ar"))
                .andExpect(jsonPath("$[0].diaSemana").value("Sexta-feira"))
                .andExpect(jsonPath("$[0].corPrimaria").value("#FFFFFF"))
                .andExpect(jsonPath("$[0].ativo").value(true))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Ogum"))
                .andExpect(jsonPath("$[1].nomeAfricano").value("Ogún"))
                .andExpect(jsonPath("$[1].elemento").value("Fogo"))
                .andExpect(jsonPath("$[1].diaSemana").value("Terça-feira"))
                .andExpect(jsonPath("$[1].corPrimaria").value("#FF0000"))
                .andExpect(jsonPath("$[1].ativo").value(true));
    }

    @Test
    @WithMockUser
    @DisplayName("GET /api/v1/orixas deve retornar lista vazia quando não há Orixás ativos")
    void deveRetornarListaVaziaQuandoNaoHaOrixasAtivos() throws Exception {
        // Given
        when(orixaService.findAllAtivos()).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/api/v1/orixas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser
    @DisplayName("GET /api/v1/orixas deve retornar apenas Orixás com ativo=true")
    void deveRetornarApenasOrixasComAtivoTrue() throws Exception {
        // Given
        List<OrixaDTO> orixasApenasAtivos = Arrays.asList(oxalaDTO, ogumDTO);
        when(orixaService.findAllAtivos()).thenReturn(orixasApenasAtivos);

        // When & Then
        mockMvc.perform(get("/api/v1/orixas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].ativo").value(true))
                .andExpect(jsonPath("$[1].ativo").value(true));
    }

    @Test
    @WithMockUser
    @DisplayName("GET /api/v1/orixas deve retornar status 200 OK")
    void deveRetornarStatus200OK() throws Exception {
        // Given
        when(orixaService.findAllAtivos()).thenReturn(orixasAtivos);

        // When & Then
        mockMvc.perform(get("/api/v1/orixas"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("GET /api/v1/orixas deve retornar Content-Type application/json")
    void deveRetornarContentTypeApplicationJson() throws Exception {
        // Given
        when(orixaService.findAllAtivos()).thenReturn(orixasAtivos);

        // When & Then
        mockMvc.perform(get("/api/v1/orixas"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
