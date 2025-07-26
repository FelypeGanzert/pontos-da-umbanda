package com.felypeganzert.backend.service;

import com.felypeganzert.backend.dto.EntidadeEspecificaDTO;
import com.felypeganzert.backend.entity.EntidadeEspecifica;
import com.felypeganzert.backend.mapper.EntidadeEspecificaMapper;
import com.felypeganzert.backend.repository.EntidadeEspecificaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do EntidadeEspecificaService")
class EntidadeEspecificaServiceTest {

    @Mock
    private EntidadeEspecificaRepository repository;

    @Mock
    private EntidadeEspecificaMapper mapper;

    @InjectMocks
    private EntidadeEspecificaService service;

    @Test
    @DisplayName("Deve retornar lista de EntidadeEspecificaDTO para entidades ativas")
    void testFindAllAtivos() {
        // Given
        EntidadeEspecifica entidade1 = EntidadeEspecifica.builder().id(1L).nome("Entidade 1").ativo(true).build();
        EntidadeEspecifica entidade2 = EntidadeEspecifica.builder().id(2L).nome("Entidade 2").ativo(true).build();
        List<EntidadeEspecifica> entidades = Arrays.asList(entidade1, entidade2);

        EntidadeEspecificaDTO dto1 = EntidadeEspecificaDTO.builder().id(1L).nome("Entidade 1").ativo(true).build();
        EntidadeEspecificaDTO dto2 = EntidadeEspecificaDTO.builder().id(2L).nome("Entidade 2").ativo(true).build();
        List<EntidadeEspecificaDTO> expectedDTOs = Arrays.asList(dto1, dto2);

        when(repository.findByAtivoTrue()).thenReturn(entidades);
        when(mapper.toDTOList(entidades)).thenReturn(expectedDTOs);

        // When
        List<EntidadeEspecificaDTO> result = service.findAllAtivos();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(dto1, dto2);

        verify(repository).findByAtivoTrue();
        verify(mapper).toDTOList(entidades);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há entidades ativas")
    void testFindAllAtivos_EmptyList() {
        // Given
        List<EntidadeEspecifica> emptyList = Collections.emptyList();
        List<EntidadeEspecificaDTO> emptyDTOList = Collections.emptyList();

        when(repository.findByAtivoTrue()).thenReturn(emptyList);
        when(mapper.toDTOList(emptyList)).thenReturn(emptyDTOList);

        // When
        List<EntidadeEspecificaDTO> result = service.findAllAtivos();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(repository).findByAtivoTrue();
        verify(mapper).toDTOList(emptyList);
        verifyNoMoreInteractions(repository, mapper);
    }
}
