package com.felypeganzert.backend.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um Tipo de Entidade na Umbanda.
 * 
 * Os Tipos de Entidade definem as diferentes categorias de espíritos
 * que trabalham nas diversas linhas, cada um com suas características,
 * área de atuação e formas específicas de trabalho.
 */
@Entity
@Table(name = "tipos_entidade")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoEntidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 80, unique = true, nullable = false)
    @NotBlank(message = "O nome do Tipo de Entidade é obrigatório")
    @Size(max = 80, message = "O nome deve ter no máximo 80 caracteres")
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linha_id")
    private Linha linha;

    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "A descrição do Tipo de Entidade é obrigatória")
    private String descricao;

    @Column(name = "caracteristicas", columnDefinition = "TEXT")
    private String caracteristicas;

    @Column(name = "area_atuacao", length = 200)
    @Size(max = 200, message = "A área de atuação deve ter no máximo 200 caracteres")
    private String areaAtuacao;

    @Column(name = "icone_representacao", length = 50)
    @Size(max = 50, message = "O ícone de representação deve ter no máximo 50 caracteres")
    private String iconeRepresentacao;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @Column(name = "ativo", nullable = false)
    @Builder.Default
    private Boolean ativo = true;
}
