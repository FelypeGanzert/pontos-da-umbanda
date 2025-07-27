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
import com.felypeganzert.backend.dto.YoutubeUrlDTO;
import com.felypeganzert.backend.service.YoutubeUrlService;

@WebMvcTest(YoutubeUrlController.class)
@Import(TestSecurityConfig.class)
@WithMockUser
@AutoConfigureWebMvc
@DisplayName("Testes do YoutubeUrlController")
class YoutubeUrlControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private YoutubeUrlService youtubeUrlService;

    @Test
    @DisplayName("Deve listar URLs por ponto cantado")
    void findByPontoCantado() throws Exception {
        Mockito.when(youtubeUrlService.findByPontoCantado(1L)).thenReturn(List.of(YoutubeUrlDTO.builder().id(1L).url("url").build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/youtube-urls/ponto-cantado/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @DisplayName("Deve criar uma URL do Youtube")
    void create() throws Exception {
        YoutubeUrlDTO dto = YoutubeUrlDTO.builder().id(1L).url("url").build();
        Mockito.when(youtubeUrlService.save(any())).thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/youtube-urls")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\":\"url\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Deve atualizar uma URL do Youtube")
    void update() throws Exception {
        YoutubeUrlDTO dto = YoutubeUrlDTO.builder().id(1L).url("nova").build();
        Mockito.when(youtubeUrlService.update(eq(1L), any())).thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/youtube-urls/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\":\"nova\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value("nova"));
    }

    @Test
    @DisplayName("Deve deletar uma URL do Youtube")
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/youtube-urls/1"))
                .andExpect(status().isNoContent());
    }
}
