package com.felypeganzert.backend.controller;

import com.felypeganzert.backend.config.TestSecurityConfig;
import com.felypeganzert.backend.dto.UsuarioDTO;
import com.felypeganzert.backend.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
@Import(TestSecurityConfig.class)
@WithMockUser
@AutoConfigureWebMvc
@DisplayName("Testes do UsuarioController")
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Deve listar usuários ativos")
    void findAllAtivos() throws Exception {
        // Given
        UsuarioDTO dto = UsuarioDTO.builder()
                .id(1L)
                .nome("Usuario Teste")
                .email("teste@email.com")
                .role("USER")
                .status("ATIVO")
                .build();
        when(usuarioService.findAllAtivos()).thenReturn(List.of(dto));

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/usuarios/ativos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Usuario Teste"));
    }

    @Test
    @DisplayName("Deve buscar usuário por ID")
    void findById() throws Exception {
        // Given
        UsuarioDTO dto = UsuarioDTO.builder()
                .id(1L)
                .nome("Usuario Teste")
                .email("teste@email.com")
                .role("USER")
                .status("ATIVO")
                .build();
        when(usuarioService.findById(1L)).thenReturn(dto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Usuario Teste"));
    }

    @Test
    @DisplayName("Deve buscar usuário por email")
    void findByEmail() throws Exception {
        // Given
        UsuarioDTO dto = UsuarioDTO.builder()
                .id(1L)
                .nome("Usuario Teste")
                .email("teste@email.com")
                .role("USER")
                .status("ATIVO")
                .build();
        when(usuarioService.findByEmail("teste@email.com")).thenReturn(dto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/usuarios/email/teste@email.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("teste@email.com"));
    }

    @Test
    @DisplayName("Deve criar usuário")
    void create() throws Exception {
        // Given
        UsuarioDTO dto = UsuarioDTO.builder()
                .id(1L)
                .nome("Usuario Teste")
                .email("teste@email.com")
                .senha("password123")
                .role("USER")
                .status("ATIVO")
                .build();
        when(usuarioService.save(any())).thenReturn(dto);

        String json = "{"
                + "\"nome\":\"Usuario Teste\","
                + "\"email\":\"teste@email.com\","
                + "\"senha\":\"password123\""
                + "}";

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Usuario Teste"));
    }

    @Test
    @DisplayName("Deve atualizar usuário")
    void update() throws Exception {
        // Given
        UsuarioDTO dto = UsuarioDTO.builder()
                .id(1L)
                .nome("Usuario Atualizado")
                .email("atualizado@email.com")
                .role("USER")
                .status("ATIVO")
                .build();
        when(usuarioService.update(eq(1L), any())).thenReturn(dto);

        String json = "{"
                + "\"nome\":\"Usuario Atualizado\","
                + "\"email\":\"atualizado@email.com\","
                + "\"senha\":\"newpassword123\""
                + "}";

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.nome").value("Usuario Atualizado"));
    }

    @Test
    @DisplayName("Deve deletar usuário")
    void delete() throws Exception {
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve inativar usuário")
    void inativar() throws Exception {
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/usuarios/1/inativar"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve ativar usuário")
    void ativar() throws Exception {
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/usuarios/1/ativar"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar erro 400 para dados inválidos na criação")
    void createInvalidData() throws Exception {
        String json = "{"
                + "\"nome\":\"\","
                + "\"email\":\"email-invalido\","
                + "\"senha\":\"123\""
                + "}";

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
}
