package com.felypeganzert.backend.repository;

import com.felypeganzert.backend.entity.Linha;
import com.felypeganzert.backend.entity.Orixa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Testes do LinhaRepository")
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class LinhaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LinhaRepository linhaRepository;

    @Test
    @DisplayName("Deve retornar apenas linhas ativas")
    void testFindByAtivoTrue() {
        // Given
        Orixa orixa = Orixa.builder()
                .nome("Oxalá Teste LinhaRepo")
                .descricao("Pai de todos os Orixás - Teste")
                .ativo(true)
                .build();
        entityManager.persistAndFlush(orixa);

        Linha linhaAtiva1 = Linha.builder()
                .nome("Linha de Oxalá Teste")
                .orixaRegente(orixa)
                .descricao("Linha da paz - Teste")
                .numeroOrdem(2)
                .ativo(true)
                .build();

        Linha linhaAtiva2 = Linha.builder()
                .nome("Linha de Iemanjá Teste")
                .orixaRegente(orixa)
                .descricao("Linha do mar - Teste")
                .numeroOrdem(1)
                .ativo(true)
                .build();

        Linha linhaInativa = Linha.builder()
                .nome("Linha Inativa Teste")
                .orixaRegente(orixa)
                .descricao("Linha inativa - Teste")
                .numeroOrdem(3)
                .ativo(false)
                .build();

        entityManager.persistAndFlush(linhaAtiva1);
        entityManager.persistAndFlush(linhaAtiva2);
        entityManager.persistAndFlush(linhaInativa);

        // When
        List<Linha> result = linhaRepository.findByAtivoTrue();

        // Then - Filtrar apenas nossas linhas de teste
        List<Linha> linhasDeTeste = result.stream()
                .filter(linha -> linha.getNome().contains("Teste"))
                .toList();
                
        assertThat(linhasDeTeste).hasSize(2);
        assertThat(linhasDeTeste.stream().allMatch(Linha::isAtivo)).isTrue();
        
        // Verificar que ambas as linhas estão presentes (sem garantir ordem)
        List<String> nomesLinhas = linhasDeTeste.stream()
                .map(Linha::getNome)
                .toList();
        assertThat(nomesLinhas).containsExactlyInAnyOrder("Linha de Oxalá Teste", "Linha de Iemanjá Teste");
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há linhas ativas")
    void testFindByAtivoTrueQuandoNaoHaLinhasAtivas() {
        // Given
        Orixa orixa = Orixa.builder()
                .nome("Oxalá Teste Inativo LinhaRepo")
                .descricao("Pai de todos os Orixás - Teste Inativo")
                .ativo(true)
                .build();
        entityManager.persistAndFlush(orixa);

        Linha linhaInativa = Linha.builder()
                .nome("Linha Inativa Teste Especial")
                .orixaRegente(orixa)
                .descricao("Linha inativa - Teste")
                .ativo(false)
                .build();
        entityManager.persistAndFlush(linhaInativa);

        // When
        List<Linha> result = linhaRepository.findByAtivoTrue();

        // Then - Verificar que nossa linha de teste não está no resultado (pois é inativa)
        List<Linha> linhasDeTesteInativas = result.stream()
                .filter(linha -> linha.getNome().contains("Teste Especial"))
                .toList();
                
        assertThat(linhasDeTesteInativas).isEmpty();
    }
}
