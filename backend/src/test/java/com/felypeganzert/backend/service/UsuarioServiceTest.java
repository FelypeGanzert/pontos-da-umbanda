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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do UsuarioService")
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private UsuarioMapper mapper;

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
    @DisplayName("Deve listar usuários ativos")
    void findAllAtivos() {
        // Given
        when(repository.findByStatus("ATIVO")).thenReturn(List.of(usuario));
        when(mapper.toDTOList(any())).thenReturn(List.of(usuarioDTO));

        // When
        List<UsuarioDTO> result = service.findAllAtivos();

        // Then
        assertThat(result).hasSize(1);
        verify(repository).findByStatus("ATIVO");
        verify(mapper).toDTOList(any());
    }

    @Test
    @DisplayName("Deve buscar usuário por ID")
    void findById() {
        // Given
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(mapper.toDTO(usuario)).thenReturn(usuarioDTO);

        // When
        UsuarioDTO result = service.findById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(repository).findById(1L);
        verify(mapper).toDTO(usuario);
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não encontrado por ID")
    void findByIdNotFound() {
        // Given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> service.findById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Usuario não encontrado para o id: 1");
    }

    @Test
    @DisplayName("Deve buscar usuário por email")
    void findByEmail() {
        // Given
        when(repository.findByEmail("teste@email.com")).thenReturn(Optional.of(usuario));
        when(mapper.toDTO(usuario)).thenReturn(usuarioDTO);

        // When
        UsuarioDTO result = service.findByEmail("teste@email.com");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("teste@email.com");
        verify(repository).findByEmail("teste@email.com");
        verify(mapper).toDTO(usuario);
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não encontrado por email")
    void findByEmailNotFound() {
        // Given
        when(repository.findByEmail("teste@email.com")).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> service.findByEmail("teste@email.com"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Usuario não encontrado para o email: teste@email.com");
    }

    @Test
    @DisplayName("Deve salvar novo usuário")
    void save() {
        // Given
        when(repository.existsByEmail("teste@email.com")).thenReturn(false);
        when(mapper.toEntity(usuarioDTO)).thenReturn(usuario);
        when(repository.save(any(Usuario.class))).thenReturn(usuario);
        when(mapper.toDTO(usuario)).thenReturn(usuarioDTO);

        // When
        UsuarioDTO result = service.save(usuarioDTO);

        // Then
        assertThat(result).isNotNull();
        verify(repository).existsByEmail("teste@email.com");
        verify(mapper).toEntity(usuarioDTO);
        verify(repository).save(any(Usuario.class));
        verify(mapper).toDTO(usuario);
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
    @DisplayName("Deve atualizar usuário existente")
    void update() {
        // Given
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.findByEmail("teste@email.com")).thenReturn(Optional.empty());
        when(mapper.toEntity(usuarioDTO)).thenReturn(usuario);
        when(repository.save(any(Usuario.class))).thenReturn(usuario);
        when(mapper.toDTO(usuario)).thenReturn(usuarioDTO);

        // When
        UsuarioDTO result = service.update(1L, usuarioDTO);

        // Then
        assertThat(result).isNotNull();
        verify(repository).existsById(1L);
        verify(mapper).toEntity(usuarioDTO);
        verify(repository).save(any(Usuario.class));
        verify(mapper).toDTO(usuario);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar usuário inexistente")
    void updateNotFound() {
        // Given
        when(repository.existsById(1L)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> service.update(1L, usuarioDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Usuario não encontrado para o id: 1");
    }

    @Test
    @DisplayName("Deve deletar usuário")
    void delete() {
        // Given
        when(repository.existsById(1L)).thenReturn(true);

        // When
        service.delete(1L);

        // Then
        verify(repository).existsById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar usuário inexistente")
    void deleteNotFound() {
        // Given
        when(repository.existsById(1L)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> service.delete(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Usuario não encontrado para o id: 1");
    }

    @Test
    @DisplayName("Deve inativar usuário")
    void inativar() {
        // Given
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        // When
        service.inativar(1L);

        // Then
        verify(repository).findById(1L);
        verify(repository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve ativar usuário")
    void ativar() {
        // Given
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        // When
        service.ativar(1L);

        // Then
        verify(repository).findById(1L);
        verify(repository).save(any(Usuario.class));
    }
}
