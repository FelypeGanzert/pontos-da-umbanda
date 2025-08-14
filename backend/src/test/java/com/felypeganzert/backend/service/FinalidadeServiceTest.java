package com.felypeganzert.backend.service;

import com.felypeganzert.backend.dto.FinalidadeDTO;
import com.felypeganzert.backend.entity.Finalidade;
import com.felypeganzert.backend.mapper.FinalidadeMapper;
import com.felypeganzert.backend.repository.FinalidadeRepository;
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
@DisplayName("Testes do FinalidadeService")
class FinalidadeServiceTest {

    @Mock
    private FinalidadeRepository repository;

    @Mock
    private FinalidadeMapper mapper;

    @InjectMocks
    private FinalidadeService service;

    @Test
    @DisplayName("Deve retornar lista de FinalidadeDTO para finalidades ativas")
    void testFindAllAtivos() {
        // Given
        Finalidade finalidade1 = Finalidade.builder().id(1L).nome("Abertura").ativo(true).build();
        Finalidade finalidade2 = Finalidade.builder().id(2L).nome("Trabalho").ativo(true).build();
        List<Finalidade> finalidades = Arrays.asList(finalidade1, finalidade2);

        FinalidadeDTO dto1 = FinalidadeDTO.builder().id(1L).nome("Abertura").ativo(true).build();
        FinalidadeDTO dto2 = FinalidadeDTO.builder().id(2L).nome("Trabalho").ativo(true).build();
        List<FinalidadeDTO> expectedDTOs = Arrays.asList(dto1, dto2);

        when(repository.findByAtivoTrue()).thenReturn(finalidades);
        when(mapper.toDTOList(finalidades)).thenReturn(expectedDTOs);

        // When
        List<FinalidadeDTO> result = service.findAllAtivos();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(dto1, dto2);

        verify(repository).findByAtivoTrue();
        verify(mapper).toDTOList(finalidades);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há finalidades ativas")
    void testFindAllAtivosEmptyList() {
        // Given
        List<Finalidade> emptyList = Collections.emptyList();
        List<FinalidadeDTO> emptyDTOList = Collections.emptyList();

        when(repository.findByAtivoTrue()).thenReturn(emptyList);
        when(mapper.toDTOList(emptyList)).thenReturn(emptyDTOList);

        // When
        List<FinalidadeDTO> result = service.findAllAtivos();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(repository).findByAtivoTrue();
        verify(mapper).toDTOList(emptyList);
        verifyNoMoreInteractions(repository, mapper);
    }
}
