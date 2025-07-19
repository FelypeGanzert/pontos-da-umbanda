package com.felypeganzert.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.felypeganzert.backend.dto.OrixaDTO;
import com.felypeganzert.backend.entity.Orixa;
import com.felypeganzert.backend.repository.OrixaRepository;

/**
 * Testes unitários para OrixaService.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do OrixaService")
class OrixaServiceTest {

    @Mock
    private OrixaRepository orixaRepository;

    private OrixaService orixaService;

    private Orixa oxala;
    private Orixa ogum;

    @BeforeEach
    void setUp() {
        orixaService = new OrixaService(orixaRepository);
        
        oxala = Orixa.builder()
                .id(1L)
                .nome("Oxalá")
                .nomeAfricano("Obatalá")
                .descricao("Orixá da criação, paz e sabedoria")
                .corPrimaria("#FFFFFF")
                .diaSemana("Sexta-feira")
                .elemento("Ar")
                .dataCriacao(LocalDateTime.now())
                .ativo(true)
                .build();

        ogum = Orixa.builder()
                .id(2L)
                .nome("Ogum")
                .nomeAfricano("Ogún")
                .descricao("Orixá guerreiro, protetor dos caminhos")
                .corPrimaria("#008000")
                .diaSemana("Terça-feira")
                .elemento("Ferro")
                .dataCriacao(LocalDateTime.now())
                .ativo(true)
                .build();
    }

    @Test
    @DisplayName("Deve buscar todos os Orixás ativos")
    void deveBuscarTodosOrixasAtivos() {
        // Given
        List<Orixa> orixasAtivos = Arrays.asList(oxala, ogum);
        when(orixaRepository.findByAtivoTrue()).thenReturn(orixasAtivos);

        // When
        List<OrixaDTO> resultado = orixaService.findAllAtivos();

        // Then
        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getNome()).isEqualTo("Oxalá");
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
        assertThat(resultado.get(0).getAtivo()).isTrue();
        
        assertThat(resultado.get(1).getNome()).isEqualTo("Ogum");
        assertThat(resultado.get(1).getId()).isEqualTo(2L);
        assertThat(resultado.get(1).getAtivo()).isTrue();
        
        verify(orixaRepository).findByAtivoTrue();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há Orixás ativos")
    void deveRetornarListaVaziaQuandoNaoHaOrixasAtivos() {
        // Given
        when(orixaRepository.findByAtivoTrue()).thenReturn(Collections.emptyList());

        // When
        List<OrixaDTO> resultado = orixaService.findAllAtivos();

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado).isEmpty();
        
        verify(orixaRepository).findByAtivoTrue();
    }
}
