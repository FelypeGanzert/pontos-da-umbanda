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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
@Import(TestSecurityConfig.class)
@AutoConfigureWebMvc
@DisplayName("Testes do UsuarioController")
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Deve criar usuário")
    void create() throws Exception {
        // Given
        UsuarioDTO dto = UsuarioDTO.builder()
                .id(1L)
                .nome("Usuario Teste")
                .email("teste@email.com")
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
    @WithMockUser
    @DisplayName("Deve retornar dados do usuário autenticado")
    void getMe() throws Exception {
        // Given
        UsuarioDTO dto = UsuarioDTO.builder()
                .id(1L)
                .nome("Usuario Autenticado")
                .email("auth@email.com")
                .role("USER")
                .status("ATIVO")
                .build();
        when(usuarioService.getAuthenticatedUser()).thenReturn(dto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/usuarios/me"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Usuario Autenticado"));
    }

    @Test
    @WithMockUser
    @DisplayName("Deve atualizar dados do usuário autenticado")
    void updateMe() throws Exception {
        // Given
        UsuarioDTO dto = UsuarioDTO.builder()
                .id(1L)
                .nome("Usuario Atualizado")
                .email("atualizado@email.com")
                .role("USER")
                .status("ATIVO")
                .build();
        when(usuarioService.updateAuthenticatedUser(any())).thenReturn(dto);

        String json = "{"
                + "\"nome\":\"Usuario Atualizado\","
                + "\"email\":\"atualizado@email.com\","
                + "\"senha\":\"newpassword123\""
                + "}";

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/usuarios/me")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.nome").value("Usuario Atualizado"));
    }

    @Test
    @WithMockUser
    @DisplayName("Deve excluir conta do usuário autenticado")
    void deleteMe() throws Exception {
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/usuarios/me"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar 401 ao tentar acessar dados sem autenticação")
    void getMeUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/usuarios/me"))
                .andExpect(status().isUnauthorized());
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
