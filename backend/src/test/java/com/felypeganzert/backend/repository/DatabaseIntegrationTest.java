package com.felypeganzert.backend.repository;

import com.felypeganzert.backend.entity.Orixa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Teste de integração para verificar se o schema e os dados estão sendo carregados corretamente.
 */
@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Testes de Integração do Banco de Dados")
class DatabaseIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrixaRepository orixaRepository;

    @Test
    @DisplayName("Deve verificar se os dados do data.sql foram carregados corretamente")
    void deveCarregarDadosDoDataSql() {
        // Given & When
        long totalOrixas = orixaRepository.count();
        
        // Then
        assertThat(totalOrixas).isGreaterThan(0);
        System.out.println("Total de Orixás carregados do data.sql: " + totalOrixas);
    }

    @Test
    @DisplayName("Deve verificar se o auto-increment do ID está funcionando")
    void deveVerificarAutoIncrementDoId() {
        // Given
        Orixa novoOrixa = Orixa.builder()
                .nome("Teste Auto Increment")
                .descricao("Teste para verificar auto increment")
                .ativo(true)
                .build();

        // When
        Orixa orixaSalvo = entityManager.persistAndFlush(novoOrixa);
        entityManager.clear();

        // Then
        assertThat(orixaSalvo.getId()).isNotNull();
        assertThat(orixaSalvo.getId()).isGreaterThan(0L);
        System.out.println("ID gerado automaticamente: " + orixaSalvo.getId());
    }

    @Test
    @DisplayName("Deve verificar se há Orixás ativos nos dados carregados")
    void deveVerificarSeHaOrixasAtivos() {
        // When
        List<Orixa> todosOrixas = orixaRepository.findAll();
        List<Orixa> orixasAtivos = orixaRepository.findByAtivoTrue();
        
        System.out.println("=== Todos os Orixás carregados ===");
        todosOrixas.forEach(o -> System.out.println("ID: " + o.getId() + ", Nome: '" + o.getNome() + "', Ativo: " + o.getAtivo()));
        
        // Then
        assertThat(todosOrixas).isNotEmpty();
        assertThat(orixasAtivos).isNotEmpty();
        assertThat(orixasAtivos.size()).isLessThanOrEqualTo(todosOrixas.size());
        
        System.out.println("Total de Orixás: " + todosOrixas.size());
        System.out.println("Orixás ativos: " + orixasAtivos.size());
    }
}
