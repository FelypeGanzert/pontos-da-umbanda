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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa uma Linha na Umbanda.
 * 
 * As Linhas são agrupamentos espirituais dirigidos por Orixás,
 * cada uma com suas características, cores, elementos e formas de trabalho
 * específicas.
 */
@Entity
@Table(name = "linhas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Linha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 80, unique = true, nullable = false)
    @NotBlank(message = "O nome da Linha é obrigatório")
    @Size(max = 80, message = "O nome deve ter no máximo 80 caracteres")
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orixa_regente_id", nullable = false)
    @NotNull(message = "O Orixá regente é obrigatório")
    private Orixa orixaRegente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orixa_adjunto_id")
    private Orixa orixaAdjunto;

    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "A descrição da Linha é obrigatória")
    private String descricao;

    @Column(name = "caracteristicas", columnDefinition = "TEXT")
    private String caracteristicas;

    @Column(name = "cores", length = 100)
    @Size(max = 100, message = "As cores devem ter no máximo 100 caracteres")
    private String cores;

    @Column(name = "elementos_trabalho", length = 200)
    @Size(max = 200, message = "Os elementos de trabalho devem ter no máximo 200 caracteres")
    private String elementosTrabalho;

    @Column(name = "numero_ordem")
    private Integer numeroOrdem;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @Column(name = "ativo", nullable = false)
    @NotNull(message = "O status ativo é obrigatório")
    @Builder.Default
    private Boolean ativo = true;

    public boolean isAtivo() {
        return ativo != null && ativo;
    }

    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }

    @Override
    public String toString() {
        return "Linha{"
                + "id=" + id
                + ", nome='" + nome + '\''
                + ", orixaRegenteId=" + (orixaRegente != null ? orixaRegente.getId() : null)
                + ", orixaAdjuntoId=" + (orixaAdjunto != null ? orixaAdjunto.getId() : null)
                + ", numeroOrdem=" + numeroOrdem
                + ", ativo=" + ativo
                + '}';
    }
}
