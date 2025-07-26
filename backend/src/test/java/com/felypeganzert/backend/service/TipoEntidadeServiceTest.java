package com.felypeganzert.backend.service;

import com.felypeganzert.backend.dto.TipoEntidadeDTO;
import com.felypeganzert.backend.entity.TipoEntidade;
import com.felypeganzert.backend.entity.Linha;
import com.felypeganzert.backend.mapper.TipoEntidadeMapper;
import com.felypeganzert.backend.repository.TipoEntidadeRepository;
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
@DisplayName("Testes do TipoEntidadeService")
class TipoEntidadeServiceTest {

    @Mock
    private TipoEntidadeRepository repository;

    @Mock
    private TipoEntidadeMapper mapper;

    @InjectMocks
    private TipoEntidadeService tipoEntidadeService;

    @Test
    @DisplayName("Deve retornar lista de TipoEntidadeDTO para tipos de entidade ativos")
    void testFindAllAtivos() {
        // Given
        Linha linha = Linha.builder()
                .id(1L)
                .nome("Linha dos Pretos Velhos")
                .build();

        TipoEntidade tipoEntidade1 = TipoEntidade.builder()
                .id(1L)
                .nome("Preto Velho Teste")
                .linha(linha)
                .descricao("Entidades de sabedoria")
                .caracteristicas("Sábios e conselheiros")
                .areaAtuacao("Cura e aconselhamento")
                .ativo(true)
                .build();

        TipoEntidade tipoEntidade2 = TipoEntidade.builder()
                .id(2L)
                .nome("Caboclo Teste")
                .linha(linha)
                .descricao("Entidades da natureza")
                .caracteristicas("Guardiões das matas")
                .areaAtuacao("Proteção e cura")
                .ativo(true)
                .build();

        TipoEntidadeDTO tipoEntidadeDTO1 = TipoEntidadeDTO.builder()
                .id(1L)
                .nome("Preto Velho Teste")
                .linhaId(1L)
                .descricao("Entidades de sabedoria")
                .caracteristicas("Sábios e conselheiros")
                .areaAtuacao("Cura e aconselhamento")
                .ativo(true)
                .build();

        TipoEntidadeDTO tipoEntidadeDTO2 = TipoEntidadeDTO.builder()
                .id(2L)
                .nome("Caboclo Teste")
                .linhaId(1L)
                .descricao("Entidades da natureza")
                .caracteristicas("Guardiões das matas")
                .areaAtuacao("Proteção e cura")
                .ativo(true)
                .build();

        List<TipoEntidade> tiposEntidade = Arrays.asList(tipoEntidade1, tipoEntidade2);
        List<TipoEntidadeDTO> expectedDTOs = Arrays.asList(tipoEntidadeDTO1, tipoEntidadeDTO2);

        when(repository.findByAtivoTrue()).thenReturn(tiposEntidade);
        when(mapper.toDTOList(tiposEntidade)).thenReturn(expectedDTOs);

        // When
        List<TipoEntidadeDTO> result = tipoEntidadeService.findAllAtivos();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(tipoEntidadeDTO1, tipoEntidadeDTO2);

        verify(repository).findByAtivoTrue();
        verify(mapper).toDTOList(tiposEntidade);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há tipos de entidade ativos")
    void testFindAllAtivos_EmptyList() {
        // Given
        List<TipoEntidade> emptyList = Collections.emptyList();
        List<TipoEntidadeDTO> emptyDTOList = Collections.emptyList();

        when(repository.findByAtivoTrue()).thenReturn(emptyList);
        when(mapper.toDTOList(emptyList)).thenReturn(emptyDTOList);

        // When
        List<TipoEntidadeDTO> result = tipoEntidadeService.findAllAtivos();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(repository).findByAtivoTrue();
        verify(mapper).toDTOList(emptyList);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    @DisplayName("Deve retornar apenas tipos de entidade ativos")
    void testFindAllAtivos_OnlyActive() {
        // Given
        TipoEntidade tipoEntidadeAtivo = TipoEntidade.builder()
                .id(1L)
                .nome("Tipo Ativo Teste")
                .descricao("Tipo ativo")
                .ativo(true)
                .build();

        List<TipoEntidade> tiposEntidade = Arrays.asList(tipoEntidadeAtivo);
        
        TipoEntidadeDTO tipoEntidadeDTO = TipoEntidadeDTO.builder()
                .id(1L)
                .nome("Tipo Ativo Teste")
                .descricao("Tipo ativo")
                .ativo(true)
                .build();

        List<TipoEntidadeDTO> expectedDTOs = Arrays.asList(tipoEntidadeDTO);

        when(repository.findByAtivoTrue()).thenReturn(tiposEntidade);
        when(mapper.toDTOList(tiposEntidade)).thenReturn(expectedDTOs);

        // When
        List<TipoEntidadeDTO> result = tipoEntidadeService.findAllAtivos();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAtivo()).isTrue();

        verify(repository).findByAtivoTrue();
        verify(mapper).toDTOList(tiposEntidade);
        verifyNoMoreInteractions(repository, mapper);
    }
}
