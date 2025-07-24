package com.felypeganzert.backend.service;

import com.felypeganzert.backend.dto.LinhaDTO;
import com.felypeganzert.backend.entity.Linha;
import com.felypeganzert.backend.entity.Orixa;
import com.felypeganzert.backend.mapper.LinhaMapper;
import com.felypeganzert.backend.repository.LinhaRepository;
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
@DisplayName("Testes do LinhaService")
class LinhaServiceTest {

    @Mock
    private LinhaRepository repository;

    @Mock
    private LinhaMapper mapper;

    @InjectMocks
    private LinhaService linhaService;

    @Test
    @DisplayName("Deve retornar lista de LinhaDTO para linhas ativas")
    void testFindAllAtivos() {
        // Given
        Orixa orixa = Orixa.builder()
                .id(1L)
                .nome("Oxalá")
                .build();

        Linha linha1 = Linha.builder()
                .id(1L)
                .nome("Linha de Oxalá")
                .orixaRegente(orixa)
                .descricao("Linha da paz")
                .numeroOrdem(1)
                .ativo(true)
                .build();

        Linha linha2 = Linha.builder()
                .id(2L)
                .nome("Linha de Iemanjá")
                .orixaRegente(orixa)
                .descricao("Linha do mar")
                .numeroOrdem(2)
                .ativo(true)
                .build();

        List<Linha> linhas = Arrays.asList(linha1, linha2);

        LinhaDTO linhaDTO1 = LinhaDTO.builder()
                .id(1L)
                .nome("Linha de Oxalá")
                .orixaRegenteId(1L)
                .descricao("Linha da paz")
                .numeroOrdem(1)
                .ativo(true)
                .build();

        LinhaDTO linhaDTO2 = LinhaDTO.builder()
                .id(2L)
                .nome("Linha de Iemanjá")
                .orixaRegenteId(1L)
                .descricao("Linha do mar")
                .numeroOrdem(2)
                .ativo(true)
                .build();

        List<LinhaDTO> linhaDTOs = Arrays.asList(linhaDTO1, linhaDTO2);

        when(repository.findByAtivoTrue()).thenReturn(linhas);
        when(mapper.toDTOList(linhas)).thenReturn(linhaDTOs);

        // When
        List<LinhaDTO> result = linhaService.findAllAtivos();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getNome()).isEqualTo("Linha de Oxalá");
        assertThat(result.get(1).getNome()).isEqualTo("Linha de Iemanjá");
        assertThat(result.stream().allMatch(LinhaDTO::getAtivo)).isTrue();

        verify(repository).findByAtivoTrue();
        verify(mapper).toDTOList(linhas);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há linhas ativas")
    void testFindAllAtivosQuandoNaoHaLinhasAtivas() {
        // Given
        List<Linha> linhasVazias = Collections.emptyList();
        List<LinhaDTO> linhaDTOsVazias = Collections.emptyList();

        when(repository.findByAtivoTrue()).thenReturn(linhasVazias);
        when(mapper.toDTOList(linhasVazias)).thenReturn(linhaDTOsVazias);

        // When
        List<LinhaDTO> result = linhaService.findAllAtivos();

        // Then
        assertThat(result).isEmpty();

        verify(repository).findByAtivoTrue();
        verify(mapper).toDTOList(linhasVazias);
        verifyNoMoreInteractions(repository, mapper);
    }
}
