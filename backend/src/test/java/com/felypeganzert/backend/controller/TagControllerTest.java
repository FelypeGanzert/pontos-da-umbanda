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
import com.felypeganzert.backend.dto.TagDTO;
import com.felypeganzert.backend.service.TagService;

@WebMvcTest(TagController.class)
@Import(TestSecurityConfig.class)
@WithMockUser
@AutoConfigureWebMvc
@DisplayName("Testes do TagController")
class TagControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private TagService tagService;

    @Test
    @DisplayName("Deve listar todas as tags")
    void findAll() throws Exception {
        Mockito.when(tagService.findAll()).thenReturn(List.of(TagDTO.builder().id(1L).nome("TESTE").build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @DisplayName("Deve buscar tag por id")
    void findById() throws Exception {
        Mockito.when(tagService.findById(1L)).thenReturn(TagDTO.builder().id(1L).nome("TESTE").build());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tags/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Deve criar uma tag")
    void create() throws Exception {
        TagDTO dto = TagDTO.builder().id(1L).nome("NOVA").build();
        Mockito.when(tagService.save(any())).thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"NOVA\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Deve atualizar uma tag")
    void update() throws Exception {
        TagDTO dto = TagDTO.builder().id(1L).nome("ATUALIZADA").build();
        Mockito.when(tagService.update(eq(1L), any())).thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/tags/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"ATUALIZADA\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("ATUALIZADA"));
    }

    @Test
    @DisplayName("Deve deletar uma tag")
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tags/1"))
                .andExpect(status().isNoContent());
    }
}
