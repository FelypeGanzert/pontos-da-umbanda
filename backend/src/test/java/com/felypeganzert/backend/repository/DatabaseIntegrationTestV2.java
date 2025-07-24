package com.felypeganzert.backend.repository;

import com.felypeganzert.backend.entity.Orixa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Teste de integração alternativo usando @SpringBootTest para garantir carregamento completo.
 */
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = {"/schema.sql", "/integration-test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DisplayName("Testes de Integração do Banco de Dados - V2")
class DatabaseIntegrationTestV2 {

    @Autowired
    private OrixaRepository orixaRepository;

    @Autowired
    private LinhaRepository linhaRepository;

    @Test
    @Transactional
    @DisplayName("Deve carregar dados do script de teste usando SpringBootTest")
    void deveCarregarDadosDoScriptTeste() {
        // When
        long totalOrixas = orixaRepository.count();
        long totalLinhas = linhaRepository.count();
        
        List<Orixa> todosOrixas = orixaRepository.findAll();
        List<Orixa> orixasAtivos = orixaRepository.findByAtivoTrue();
        
        // Debug
        System.out.println("=== SpringBootTest - Dados carregados ===");
        System.out.println("Total de Orixás: " + totalOrixas);
        System.out.println("Total de Linhas: " + totalLinhas);
        System.out.println("Orixás ativos: " + orixasAtivos.size());
        
        if (totalOrixas > 0) {
            System.out.println("Orixás encontrados:");
            todosOrixas.forEach(o -> System.out.println("- ID: " + o.getId() + ", Nome: " + o.getNome() + ", Ativo: " + o.getAtivo()));
        }
        
        // Then
        assertThat(totalOrixas).isGreaterThan(0).withFailMessage("SpringBootTest: Nenhum Orixá carregado");
        assertThat(totalLinhas).isGreaterThan(0).withFailMessage("SpringBootTest: Nenhuma Linha carregada");
        assertThat(orixasAtivos).isNotEmpty().withFailMessage("SpringBootTest: Nenhum Orixá ativo encontrado");
        
        // Verificações específicas do script
        assertThat(totalOrixas).isEqualTo(7);
        assertThat(totalLinhas).isEqualTo(8);
        assertThat(orixasAtivos.size()).isEqualTo(7); // Todos devem estar ativos
    }
}
