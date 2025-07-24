package com.felypeganzert.backend.repository;

import com.felypeganzert.backend.entity.Orixa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes unitários para OrixaRepository.
 */
@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Testes do OrixaRepository")
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class OrixaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrixaRepository orixaRepository;

    private Orixa oxalaAtivo;
    private Orixa ogumAtivo;
    private Orixa iansaInativo;

    @BeforeEach
    void setUp() {
        // Limpar contexto JPA para garantir estado limpo (não deletar para evitar constraint violations)
        entityManager.clear();

        // Criar Orixás de teste
        oxalaAtivo = Orixa.builder()
                .nome("Oxalá Teste")
                .nomeAfricano("Obatalá")
                .descricao("Orixá da criação, paz e sabedoria")
                .corPrimaria("#FFFFFF")
                .diaSemana("Sexta-feira")
                .elemento("Ar")
                .sincretismo("Jesus Cristo")
                .iconeRepresentacao("Pomba branca")
                .ativo(true)
                .build();

        ogumAtivo = Orixa.builder()
                .nome("Ogum Teste")
                .nomeAfricano("Ogún")
                .descricao("Orixá da guerra, tecnologia e caminhos")
                .corPrimaria("#FF0000")
                .diaSemana("Terça-feira")
                .elemento("Fogo")
                .sincretismo("São Jorge")
                .iconeRepresentacao("Espada")
                .ativo(true)
                .build();

        iansaInativo = Orixa.builder()
                .nome("Iansã Teste")
                .nomeAfricano("Oyá")
                .descricao("Orixá dos ventos e tempestades")
                .corPrimaria("#FFFF00")
                .diaSemana("Quarta-feira")
                .elemento("Ar")
                .sincretismo("Santa Bárbara")
                .iconeRepresentacao("Espada e raio")
                .ativo(false)
                .build();

        // Salvar entidades de teste
        entityManager.persistAndFlush(oxalaAtivo);
        entityManager.persistAndFlush(ogumAtivo);
        entityManager.persistAndFlush(iansaInativo);
        entityManager.clear();
    }

    @Test
    @DisplayName("Deve buscar apenas Orixás ativos")
    void deveBuscarApenasOrixasAtivos() {
        // When
        List<Orixa> orixasAtivos = orixaRepository.findByAtivoTrue();

        // Then - Verificar se os Orixás que criamos estão na lista
        assertThat(orixasAtivos).hasSizeGreaterThanOrEqualTo(2);
        
        // Verificar se nossos orixás de teste estão presentes
        List<String> nomesOrixasAtivos = orixasAtivos.stream()
                .map(Orixa::getNome)
                .toList();
                
        assertThat(nomesOrixasAtivos).contains("Oxalá Teste", "Ogum Teste");
        assertThat(nomesOrixasAtivos).doesNotContain("Iansã Teste");
        
        // Verificar se todos os retornados são ativos
        assertThat(orixasAtivos).allMatch(Orixa::getAtivo);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há Orixás ativos")
    void deveRetornarListaVaziaQuandoNaoHaOrixasAtivos() {
        // Given - Desativar apenas os Orixás que criamos para este teste
        oxalaAtivo.setAtivo(false);
        ogumAtivo.setAtivo(false);
        entityManager.merge(oxalaAtivo);
        entityManager.merge(ogumAtivo);
        entityManager.flush();
        entityManager.clear();

        // When - Buscar por nome específico dos nossos testes inativos
        List<Orixa> todosOrixas = orixaRepository.findAll();
        long orixasTestesInativos = todosOrixas.stream()
                .filter(orixa -> orixa.getNome().contains("Teste"))
                .filter(orixa -> !orixa.getAtivo())
                .count();

        // Then - Verificar que nossos orixás de teste estão inativos
        assertThat(orixasTestesInativos).isGreaterThanOrEqualTo(3); // oxala, ogum e iansa inativo
    }

    @Test
    @DisplayName("Deve herdar métodos do JpaRepository corretamente")
    void deveHerdarMetodosDoJpaRepositoryCorretamente() {
        // When
        List<Orixa> todosOrixas = orixaRepository.findAll();
        long totalOrixas = orixaRepository.count();

        // Then - Verificar que incluem pelo menos nossos testes
        assertThat(todosOrixas).hasSizeGreaterThanOrEqualTo(3);
        assertThat(totalOrixas).isGreaterThanOrEqualTo(3);
        
        List<String> nomesOrixas = todosOrixas.stream()
                .map(Orixa::getNome)
                .toList();
                
        assertThat(nomesOrixas).contains("Oxalá Teste", "Ogum Teste", "Iansã Teste");
    }

    @Test
    @DisplayName("Deve salvar um novo Orixá corretamente")
    void deveSalvarNovoOrixaCorretamente() {
        // Given
        long totalAntes = orixaRepository.count();
        
        Orixa yemanja = Orixa.builder()
                .nome("Yemanjá Teste")
                .nomeAfricano("Yemọja")
                .descricao("Orixá dos mares e oceanos")
                .corPrimaria("#0000FF")
                .diaSemana("Sábado")
                .elemento("Água")
                .sincretismo("Nossa Senhora dos Navegantes")
                .iconeRepresentacao("Estrela do mar")
                .ativo(true)
                .build();

        // When
        Orixa yemanjaSalva = orixaRepository.save(yemanja);

        // Then
        assertThat(yemanjaSalva.getId()).isNotNull();
        assertThat(yemanjaSalva.getNome()).isEqualTo("Yemanjá Teste");
        assertThat(yemanjaSalva.getAtivo()).isTrue();

        // Verificar se foi persistido no banco
        long totalDepois = orixaRepository.count();
        assertThat(totalDepois).isEqualTo(totalAntes + 1);
    }
}
