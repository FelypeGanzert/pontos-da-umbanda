package com.felypeganzert.backend.repository;

import com.felypeganzert.backend.entity.Orixa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Teste de integração para verificar se o schema e os dados estão sendo carregados corretamente.
 */
@DataJpaTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = {"/integration-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DisplayName("Testes de Integração do Banco de Dados")
class DatabaseIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrixaRepository orixaRepository;

    @Autowired
    private LinhaRepository linhaRepository;

    @Test
    @DisplayName("Deve verificar se os dados do data.sql foram carregados corretamente")
    void deveCarregarDadosDoDataSql() {
        // Given & When
        long totalOrixas = orixaRepository.count();
        long totalLinhas = linhaRepository.count();
        
        // Then
        assertThat(totalOrixas).isEqualTo(7); // Esperamos 7 Orixás do data.sql
        assertThat(totalLinhas).isEqualTo(8); // Esperamos 7 Linhas do data.sql
        
        System.out.println("Total de Orixás carregados do data.sql: " + totalOrixas);
        System.out.println("Total de Linhas carregadas do data.sql: " + totalLinhas);
        
        // Verificar relacionamentos
        List<com.felypeganzert.backend.entity.Linha> linhas = linhaRepository.findAll();
        assertThat(linhas).allMatch(linha -> linha.getOrixaRegente() != null);
        assertThat(linhas).allMatch(linha -> linha.getOrixaRegente().getId() != null);
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
