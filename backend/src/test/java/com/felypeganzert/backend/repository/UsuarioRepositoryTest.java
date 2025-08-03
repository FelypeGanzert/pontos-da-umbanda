package com.felypeganzert.backend.repository;

import com.felypeganzert.backend.entity.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:schema.sql")
@DisplayName("Testes do UsuarioRepository")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Deve encontrar usuários por status")
    void findByStatus() {
        // Given
        Usuario ativo = Usuario.builder()
                .nome("Usuario Ativo Teste")
                .email("ativo.teste@email.com")
                .senhaHash("hash123")
                .status("ATIVO")
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();

        Usuario inativo = Usuario.builder()
                .nome("Usuario Inativo Teste")
                .email("inativo.teste@email.com")
                .senhaHash("hash456")
                .status("INATIVO")
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();

        entityManager.persistAndFlush(ativo);
        entityManager.persistAndFlush(inativo);

        // When
        List<Usuario> usuariosAtivos = repository.findByStatus("ATIVO");
        List<Usuario> usuariosInativos = repository.findByStatus("INATIVO");

        // Then
        assertThat(usuariosAtivos).hasSize(1);
        assertThat(usuariosAtivos.get(0).getNome()).isEqualTo("Usuario Ativo Teste");
        assertThat(usuariosInativos).hasSize(1);
        assertThat(usuariosInativos.get(0).getNome()).isEqualTo("Usuario Inativo Teste");
    }

    @Test
    @DisplayName("Deve encontrar usuário por email")
    void findByEmail() {
        // Given
        Usuario usuario = Usuario.builder()
                .nome("Usuario Email Teste")
                .email("email.teste@email.com")
                .senhaHash("hash123")
                .status("ATIVO")
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();

        entityManager.persistAndFlush(usuario);

        // When
        Optional<Usuario> found = repository.findByEmail("email.teste@email.com");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getNome()).isEqualTo("Usuario Email Teste");
    }

    @Test
    @DisplayName("Deve retornar vazio quando email não existir")
    void findByEmailNotFound() {
        // When
        Optional<Usuario> found = repository.findByEmail("naoexiste@email.com");

        // Then
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Deve verificar se email existe")
    void existsByEmail() {
        // Given
        Usuario usuario = Usuario.builder()
                .nome("Usuario Existe Teste")
                .email("existe.teste@email.com")
                .senhaHash("hash123")
                .status("ATIVO")
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();

        entityManager.persistAndFlush(usuario);

        // When & Then
        assertThat(repository.existsByEmail("existe.teste@email.com")).isTrue();
        assertThat(repository.existsByEmail("naoexiste.teste@email.com")).isFalse();
    }
}
