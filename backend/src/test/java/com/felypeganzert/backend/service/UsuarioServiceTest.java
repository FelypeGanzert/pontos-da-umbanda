package com.felypeganzert.backend.service;

import com.felypeganzert.backend.dto.UsuarioDTO;
import com.felypeganzert.backend.entity.Usuario;
import com.felypeganzert.backend.exception.EntityNotFoundException;
import com.felypeganzert.backend.mapper.UsuarioMapper;
import com.felypeganzert.backend.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do UsuarioService")
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private UsuarioMapper mapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuario;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id(1L)
                .nome("Usuario Teste")
                .email("teste@email.com")
                .senhaHash("hashedPassword")
                .role("USER")
                .status("ATIVO")
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();

        usuarioDTO = UsuarioDTO.builder()
                .id(1L)
                .nome("Usuario Teste")
                .email("teste@email.com")
                .senha("password123")
                .role("USER")
                .status("ATIVO")
                .build();
    }

    @Test
    @DisplayName("Deve retornar dados do usuário autenticado")
    void getAuthenticatedUser() {
        // Given
        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        when(auth.getName()).thenReturn("teste@email.com");
        when(repository.findByEmail("teste@email.com")).thenReturn(Optional.of(usuario));
        when(mapper.toDTO(usuario)).thenReturn(usuarioDTO);

        // When
        UsuarioDTO result = service.getAuthenticatedUser();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("teste@email.com");
        verify(repository).findByEmail("teste@email.com");
        verify(mapper).toDTO(usuario);
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário autenticado não encontrado")
    void getAuthenticatedUserNotFound() {
        // Given
        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        when(auth.getName()).thenReturn("teste@email.com");
        when(repository.findByEmail("teste@email.com")).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> service.getAuthenticatedUser())
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Usuário não encontrado");
    }

    @Test
    @DisplayName("Deve salvar novo usuário")
    void save() {
        // Given
        when(repository.existsByEmail("teste@email.com")).thenReturn(false);
        when(mapper.toEntity(usuarioDTO)).thenReturn(usuario);
        when(repository.save(any(Usuario.class))).thenReturn(usuario);
        when(mapper.toDTO(usuario)).thenReturn(usuarioDTO);
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");

        // When
        UsuarioDTO result = service.save(usuarioDTO);

        // Then
        assertThat(result).isNotNull();
        verify(repository).existsByEmail("teste@email.com");
        verify(mapper).toEntity(usuarioDTO);
        verify(repository).save(any(Usuario.class));
        verify(mapper).toDTO(usuario);
        verify(passwordEncoder).encode("password123");
    }

    @Test
    @DisplayName("Deve lançar exceção quando email já existe ao salvar")
    void saveEmailAlreadyExists() {
        // Given
        when(repository.existsByEmail("teste@email.com")).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> service.save(usuarioDTO))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Email já está em uso: teste@email.com");
    }

    @Test
    @DisplayName("Deve atualizar dados do usuário autenticado")
    void updateAuthenticatedUser() {
        // Given
        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        when(auth.getName()).thenReturn("teste@email.com");
        when(repository.findByEmail("teste@email.com")).thenReturn(Optional.of(usuario));
        when(repository.save(any(Usuario.class))).thenReturn(usuario);
        when(mapper.toDTO(usuario)).thenReturn(usuarioDTO);
        when(passwordEncoder.encode("newpassword123")).thenReturn("newHashedPassword");

        // When
        usuarioDTO.setSenha("newpassword123");
        UsuarioDTO result = service.updateAuthenticatedUser(usuarioDTO);

        // Then
        assertThat(result).isNotNull();
        verify(repository).findByEmail("teste@email.com");
        verify(repository).save(any(Usuario.class));
        verify(mapper).toDTO(usuario);
        verify(passwordEncoder).encode("newpassword123");
    }

    @Test
    @DisplayName("Deve excluir usuário autenticado")
    void deleteAuthenticatedUser() {
        // Given
        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        when(auth.getName()).thenReturn("teste@email.com");
        when(repository.findByEmail("teste@email.com")).thenReturn(Optional.of(usuario));

        // When
        service.deleteAuthenticatedUser();

        // Then
        verify(repository).findByEmail("teste@email.com");
        verify(repository).delete(usuario);
    }
}
