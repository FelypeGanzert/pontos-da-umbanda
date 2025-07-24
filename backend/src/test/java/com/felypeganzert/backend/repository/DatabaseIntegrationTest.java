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
@Sql(scripts = {"/schema.sql", "/integration-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
        
        // Debug: Listar todas as entidades para entender o que está acontecendo
        List<Orixa> orixas = orixaRepository.findAll();
        List<com.felypeganzert.backend.entity.Linha> linhas = linhaRepository.findAll();
        
        System.out.println("=== DEBUG - Dados carregados ===");
        System.out.println("Total de Orixás: " + totalOrixas);
        System.out.println("Total de Linhas: " + totalLinhas);
        
        if (totalOrixas == 0) {
            System.out.println("ATENÇÃO: Nenhum Orixá foi carregado!");
        } else {
            System.out.println("Orixás encontrados:");
            orixas.forEach(o -> System.out.println("- " + o.getNome()));
        }
        
        // Then - Ajustando expectativas baseado no integration-test-data.sql
        assertThat(totalOrixas).isGreaterThan(0).withFailMessage("Nenhum Orixá foi carregado do script de teste");
        assertThat(totalLinhas).isGreaterThan(0).withFailMessage("Nenhuma Linha foi carregada do script de teste");
        
        // Verificação mais flexível: esperamos pelo menos os dados básicos
        assertThat(totalOrixas).isEqualTo(7); // 7 Orixás no integration-test-data.sql
        assertThat(totalLinhas).isEqualTo(8); // 8 Linhas no integration-test-data.sql
        
        // Verificar relacionamentos
        assertThat(linhas).allMatch(linha -> linha.getOrixaRegente() != null);
        assertThat(linhas).allMatch(linha -> linha.getOrixaRegente().getId() != null);
    }

    @Test
    @DisplayName("Deve verificar se o auto-increment do ID está funcionando")
    void deveVerificarAutoIncrementDoId() {
        // Given - Verificar quantos registros já existem para determinar o próximo ID
        long registrosExistentes = orixaRepository.count();
        System.out.println("Registros existentes: " + registrosExistentes);
        
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
        assertThat(orixaSalvo.getId()).isGreaterThan(registrosExistentes);
        System.out.println("ID gerado automaticamente: " + orixaSalvo.getId());
        
        // Verificar que o total de registros aumentou
        assertThat(orixaRepository.count()).isEqualTo(registrosExistentes + 1);
    }

    @Test
    @DisplayName("Deve verificar se há Orixás ativos nos dados carregados")
    void deveVerificarSeHaOrixasAtivos() {
        // When
        List<Orixa> todosOrixas = orixaRepository.findAll();
        List<Orixa> orixasAtivos = orixaRepository.findByAtivoTrue();
        
        System.out.println("=== DEBUG - Todos os Orixás carregados ===");
        System.out.println("Total encontrado: " + todosOrixas.size());
        todosOrixas.forEach(o -> System.out.println("ID: " + o.getId() + ", Nome: '" + o.getNome() + "', Ativo: " + o.getAtivo()));
        
        System.out.println("=== DEBUG - Orixás Ativos ===");
        System.out.println("Total ativos: " + orixasAtivos.size());
        
        // Then - Verificações mais defensivas
        assertThat(todosOrixas).isNotEmpty().withFailMessage("Nenhum Orixá foi encontrado no banco");
        assertThat(orixasAtivos).isNotEmpty().withFailMessage("Nenhum Orixá ativo foi encontrado");
        assertThat(orixasAtivos.size()).isLessThanOrEqualTo(todosOrixas.size());
        
        // Todos os Orixás do script de teste devem estar ativos
        assertThat(orixasAtivos.size()).isEqualTo(7);
        
        System.out.println("✅ Teste passou - Total de Orixás: " + todosOrixas.size() + ", Ativos: " + orixasAtivos.size());
    }
}
