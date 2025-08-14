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

import com.felypeganzert.backend.dto.PontoCantadoDTO;
import com.felypeganzert.backend.entity.PontoCantado;
import com.felypeganzert.backend.entity.Tag;
import com.felypeganzert.backend.exception.EntityNotFoundException;
import com.felypeganzert.backend.mapper.PontoCantadoMapper;
import com.felypeganzert.backend.repository.PontoCantadoRepository;
import com.felypeganzert.backend.repository.TagRepository;

class PontoCantadoServiceTest {
    @Mock
    private PontoCantadoRepository repository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TagService tagService;
    @Mock
    private PontoCantadoMapper mapper;
    @InjectMocks
    private PontoCantadoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve listar pontos cantados ativos")
    void findAllAtivos() {
        when(repository.findByAtivoTrue()).thenReturn(List.of(new PontoCantado()));
        when(mapper.toDTOList(any())).thenReturn(List.of(new PontoCantadoDTO()));
        assertThat(service.findAllAtivos()).hasSize(1);
    }

    @Test
    @DisplayName("Deve buscar ponto cantado por id")
    void findById() {
        PontoCantado ponto = PontoCantado.builder().id(1L).build();
        when(repository.findById(1L)).thenReturn(Optional.of(ponto));
        when(mapper.toDTO(ponto)).thenReturn(PontoCantadoDTO.builder().id(1L).build());
        PontoCantadoDTO dto = service.findById(1L);
        assertThat(dto.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção se não encontrar ponto cantado")
    void findByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(1L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Deve salvar um ponto cantado")
    void save() {
        PontoCantadoDTO dto = PontoCantadoDTO.builder().id(1L).build();
        List<Tag> tags = List.of(Tag.builder().id(1L).build());
        PontoCantado entity = PontoCantado.builder().id(1L).build();
        when(tagRepository.findByNomeIn(any())).thenReturn(tags);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(dto);
        assertThat(service.save(dto)).isNotNull();
    }

    @Test
    @DisplayName("Deve atualizar um ponto cantado existente")
    void update() {
        PontoCantadoDTO dto = PontoCantadoDTO.builder().id(1L).build();
        when(repository.existsById(1L)).thenReturn(true);
        when(tagRepository.findByNomeIn(any())).thenReturn(List.of());
        when(mapper.toEntity(dto)).thenReturn(PontoCantado.builder().id(1L).build());
        when(repository.save(any())).thenReturn(PontoCantado.builder().id(1L).build());
        when(mapper.toDTO(any())).thenReturn(dto);
        assertThat(service.update(1L, dto)).isNotNull();
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar ponto cantado inexistente")
    void updateNotFound() {
        PontoCantadoDTO dto = PontoCantadoDTO.builder().id(1L).build();
        when(repository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> service.update(1L, dto)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Deve deletar um ponto cantado")
    void delete() {
        doNothing().when(repository).deleteById(1L);
        service.delete(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve salvar ponto cantado com lista de tags nula")
    void saveWithNullTags() {
        PontoCantadoDTO dto = PontoCantadoDTO.builder().id(1L).tags(null).build();
        PontoCantado entity = PontoCantado.builder().id(1L).build();
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(dto);
        assertThat(service.save(dto)).isNotNull();
    }

    @Test
    @DisplayName("Deve salvar ponto cantado com lista de tags vazia")
    void saveWithEmptyTags() {
        PontoCantadoDTO dto = PontoCantadoDTO.builder().id(1L).tags(List.of()).build();
        PontoCantado entity = PontoCantado.builder().id(1L).build();
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(dto);
        assertThat(service.save(dto)).isNotNull();
    }

    @Test
    @DisplayName("Deve salvar ponto cantado com todas as tags já existentes")
    void saveWithAllTagsExisting() {
        var tagDto = com.felypeganzert.backend.dto.TagDTO.builder().nome("EXISTENTE").build();
        PontoCantadoDTO dto = PontoCantadoDTO.builder().id(1L).tags(List.of(tagDto)).build();
        Tag tag = Tag.builder().id(1L).nome("EXISTENTE").build();
        PontoCantado entity = PontoCantado.builder().id(1L).build();
        when(tagRepository.findByNomeIn(any())).thenReturn(List.of(tag));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(dto);
        assertThat(service.save(dto)).isNotNull();
    }

    @Test
    @DisplayName("Deve salvar ponto cantado com todas as tags novas")
    void saveWithAllTagsNew() {
        var tagDto = com.felypeganzert.backend.dto.TagDTO.builder().nome("NOVA").build();
        PontoCantadoDTO dto = PontoCantadoDTO.builder().id(1L).tags(List.of(tagDto)).build();
        Tag novaTag = Tag.builder().id(2L).nome("NOVA").build();
        PontoCantado entity = PontoCantado.builder().id(1L).build();
        when(tagRepository.findByNomeIn(any())).thenReturn(List.of());
        when(tagRepository.saveAll(any())).thenReturn(List.of(novaTag));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(dto);
        assertThat(service.save(dto)).isNotNull();
    }

    @Test
    @DisplayName("Deve salvar ponto cantado com tags parcialmente existentes")
    void saveWithPartialTags() {
        var tagDto1 = com.felypeganzert.backend.dto.TagDTO.builder().nome("EXISTENTE").build();
        var tagDto2 = com.felypeganzert.backend.dto.TagDTO.builder().nome("NOVA").build();
        PontoCantadoDTO dto = PontoCantadoDTO.builder().id(1L).tags(List.of(tagDto1, tagDto2)).build();
        Tag existente = Tag.builder().id(1L).nome("EXISTENTE").build();
        Tag nova = Tag.builder().id(2L).nome("NOVA").build();
        PontoCantado entity = PontoCantado.builder().id(1L).build();
        when(tagRepository.findByNomeIn(any())).thenReturn(List.of(existente));
        when(tagRepository.saveAll(any())).thenReturn(List.of(nova));
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDTO(entity)).thenReturn(dto);
        assertThat(service.save(dto)).isNotNull();
    }

    @Test
    @DisplayName("Deve atualizar ponto cantado com tags novas")
    void updateWithNewTags() {
        var tagDto = com.felypeganzert.backend.dto.TagDTO.builder().nome("NOVA").build();
        PontoCantadoDTO dto = PontoCantadoDTO.builder().id(1L).tags(List.of(tagDto)).build();
        Tag novaTag = Tag.builder().id(2L).nome("NOVA").build();
        when(repository.existsById(1L)).thenReturn(true);
        when(tagRepository.findByNomeIn(any())).thenReturn(List.of());
        when(tagRepository.saveAll(any())).thenReturn(List.of(novaTag));
        when(mapper.toEntity(dto)).thenReturn(PontoCantado.builder().id(1L).build());
        when(repository.save(any())).thenReturn(PontoCantado.builder().id(1L).build());
        when(mapper.toDTO(any())).thenReturn(dto);
        assertThat(service.update(1L, dto)).isNotNull();
    }

    @Test
    @DisplayName("Deve atualizar ponto cantado com tags existentes")
    void updateWithExistingTags() {
        var tagDto = com.felypeganzert.backend.dto.TagDTO.builder().nome("EXISTENTE").build();
        PontoCantadoDTO dto = PontoCantadoDTO.builder().id(1L).tags(List.of(tagDto)).build();
        Tag existente = Tag.builder().id(1L).nome("EXISTENTE").build();
        when(repository.existsById(1L)).thenReturn(true);
        when(tagRepository.findByNomeIn(any())).thenReturn(List.of(existente));
        when(mapper.toEntity(dto)).thenReturn(PontoCantado.builder().id(1L).build());
        when(repository.save(any())).thenReturn(PontoCantado.builder().id(1L).build());
        when(mapper.toDTO(any())).thenReturn(dto);
        assertThat(service.update(1L, dto)).isNotNull();
    }

}
