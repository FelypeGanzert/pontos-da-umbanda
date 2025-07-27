package com.felypeganzert.backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.felypeganzert.backend.config.TestSecurityConfig;
import com.felypeganzert.backend.dto.PontoCantadoDTO;
import com.felypeganzert.backend.service.PontoCantadoService;

@WebMvcTest(PontoCantadoController.class)
@Import(TestSecurityConfig.class)
@WithMockUser
@AutoConfigureWebMvc
@DisplayName("Testes do PontoCantadoController")
class PontoCantadoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private PontoCantadoService pontoCantadoService;

    @Test
    @DisplayName("Deve listar pontos cantados ativos")
    void findAllAtivos() throws Exception {
        PontoCantadoDTO dto = PontoCantadoDTO.builder()
                .id(1L)
                .titulo("TITULO")
                .letraCompleta("Letra completa obrigatória")
                .usuarioContribuidorId(10L)
                .build();
        Mockito.when(pontoCantadoService.findAllAtivos()).thenReturn(List.of(dto));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pontos-cantados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @DisplayName("Deve buscar ponto cantado por id")
    void findById() throws Exception {
        PontoCantadoDTO dto = PontoCantadoDTO.builder()
                .id(1L)
                .titulo("TITULO")
                .letraCompleta("Letra completa obrigatória")
                .usuarioContribuidorId(10L)
                .build();
        Mockito.when(pontoCantadoService.findById(1L)).thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pontos-cantados/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser
    @DisplayName("Deve criar um ponto cantado")
    void create() throws Exception {
        PontoCantadoDTO dto = PontoCantadoDTO.builder()
                .id(1L)
                .titulo("NOVO")
                .letraCompleta("Letra completa obrigatória")
                .usuarioContribuidorId(10L)
                .build();
        Mockito.when(pontoCantadoService.save(any())).thenReturn(dto);
        String json = "{" +
                "\"titulo\":\"NOVO\"," +
                "\"letraCompleta\":\"Letra completa obrigatória\"," +
                "\"usuarioContribuidorId\":10" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/pontos-cantados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Deve atualizar um ponto cantado")
    void update() throws Exception {
        PontoCantadoDTO dto = PontoCantadoDTO.builder()
                .id(1L)
                .titulo("ATUALIZADO")
                .letraCompleta("Letra completa obrigatória")
                .usuarioContribuidorId(10L)
                .build();
        Mockito.when(pontoCantadoService.update(eq(1L), any())).thenReturn(dto);
        String json = "{" +
                "\"titulo\":\"ATUALIZADO\"," +
                "\"letraCompleta\":\"Letra completa obrigatória\"," +
                "\"usuarioContribuidorId\":10" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/pontos-cantados/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("ATUALIZADO"));
    }

    @Test
    @DisplayName("Deve deletar um ponto cantado")
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/pontos-cantados/1"))
                .andExpect(status().isNoContent());
    }
}
