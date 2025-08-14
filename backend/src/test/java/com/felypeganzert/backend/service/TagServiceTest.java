package com.felypeganzert.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.felypeganzert.backend.dto.TagDTO;
import com.felypeganzert.backend.entity.Tag;
import com.felypeganzert.backend.exception.EntityNotFoundException;
import com.felypeganzert.backend.mapper.TagMapper;
import com.felypeganzert.backend.repository.PontoCantadoRepository;
import com.felypeganzert.backend.repository.TagRepository;

class TagServiceTest {
    @Mock
    private TagRepository repository;
    @Mock
    private PontoCantadoRepository pontoCantadoRepository;
    @Mock
    private TagMapper mapper;
    @InjectMocks
    private TagService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar todas as tags")
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(new Tag()));
        when(mapper.toDTOList(any())).thenReturn(List.of(new TagDTO()));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("Deve retornar tag por id")
    void findById() {
        Tag tag = Tag.builder().id(1L).nome("T").build();
        when(repository.findById(1L)).thenReturn(Optional.of(tag));
        when(mapper.toDTO(tag)).thenReturn(TagDTO.builder().id(1L).nome("T").build());
        TagDTO dto = service.findById(1L);
        assertThat(dto.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção se não encontrar tag")
    void findByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(1L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Deve salvar uma tag")
    void save() {
        TagDTO dto = TagDTO.builder().nome("T").build();
        Tag tag = Tag.builder().nome("T").build();
        when(mapper.toEntity(dto)).thenReturn(tag);
        when(repository.save(tag)).thenReturn(tag);
        when(mapper.toDTO(tag)).thenReturn(dto);
        assertThat(service.save(dto)).isNotNull();
    }

    @Test
    @DisplayName("Deve atualizar uma tag existente")
    void update() {
        TagDTO dto = TagDTO.builder().id(1L).nome("T").build();
        when(repository.existsById(1L)).thenReturn(true);
        when(mapper.toEntity(dto)).thenReturn(Tag.builder().id(1L).nome("T").build());
        when(repository.save(any())).thenReturn(Tag.builder().id(1L).nome("T").build());
        when(mapper.toDTO(any())).thenReturn(dto);
        assertThat(service.update(1L, dto)).isNotNull();
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar tag inexistente")
    void updateNotFound() {
        TagDTO dto = TagDTO.builder().id(1L).nome("T").build();
        when(repository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> service.update(1L, dto)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Deve deletar uma tag não utilizada")
    void delete() {
        when(pontoCantadoRepository.existsByTagsId(1L)).thenReturn(false);
        doNothing().when(repository).deleteById(1L);
        service.delete(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("Não deve deletar tag em uso")
    void deleteInUse() {
        when(pontoCantadoRepository.existsByTagsId(1L)).thenReturn(true);
        assertThatThrownBy(() -> service.delete(1L)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("Deve verificar se tag está em uso")
    void existsTagInUse() {
        when(pontoCantadoRepository.existsByTagsId(1L)).thenReturn(true);
        assertThat(pontoCantadoRepository.existsByTagsId(1L)).isTrue();
    }
}
