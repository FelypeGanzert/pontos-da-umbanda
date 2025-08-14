package com.felypeganzert.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.felypeganzert.backend.dto.YoutubeUrlDTO;
import com.felypeganzert.backend.entity.YoutubeUrl;
import com.felypeganzert.backend.mapper.YoutubeUrlMapper;
import com.felypeganzert.backend.repository.YoutubeUrlRepository;

import jakarta.persistence.EntityNotFoundException;

class YoutubeUrlServiceTest {
    @Mock
    private YoutubeUrlRepository repository;
    @Mock
    private YoutubeUrlMapper mapper;
    @InjectMocks
    private YoutubeUrlService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve listar URLs por ponto cantado")
    void findByPontoCantado() {
        when(repository.findByPontoCantadoId(1L)).thenReturn(List.of(new YoutubeUrl()));
        when(mapper.toDTOList(any())).thenReturn(List.of(new YoutubeUrlDTO()));
        assertThat(service.findByPontoCantado(1L)).hasSize(1);
    }

    @Test
    @DisplayName("Deve salvar uma URL")
    void save() {
        YoutubeUrlDTO dto = YoutubeUrlDTO.builder().url("url").build();
        YoutubeUrl entity = YoutubeUrl.builder().url("url").build();
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(dto);
        assertThat(service.save(dto)).isNotNull();
    }

    @Test
    @DisplayName("Deve atualizar uma URL existente")
    void update() {
        YoutubeUrlDTO dto = YoutubeUrlDTO.builder().id(1L).url("url").build();
        when(repository.existsById(1L)).thenReturn(true);
        when(mapper.toEntity(dto)).thenReturn(YoutubeUrl.builder().id(1L).url("url").build());
        when(repository.save(any())).thenReturn(YoutubeUrl.builder().id(1L).url("url").build());
        when(mapper.toDTO(any())).thenReturn(dto);
        assertThat(service.update(1L, dto)).isNotNull();
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar URL inexistente")
    void updateNotFound() {
        YoutubeUrlDTO dto = YoutubeUrlDTO.builder().id(1L).url("url").build();
        when(repository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> service.update(1L, dto)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Deve deletar uma URL")
    void delete() {
        doNothing().when(repository).deleteById(1L);
        service.delete(1L);
        verify(repository).deleteById(1L);
    }
}
