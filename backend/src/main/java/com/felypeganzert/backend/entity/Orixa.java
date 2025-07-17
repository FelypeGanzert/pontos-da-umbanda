package com.felypeganzert.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entidade que representa um Orixá na Umbanda.
 * 
 * Os Orixás são divindades da religião de matriz africana,
 * cada um com suas características, cores, elementos e sincretismos específicos.
 */
@Entity
@Table(name = "orixas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 50, unique = true, nullable = false)
    @NotBlank(message = "O nome do Orixá é obrigatório")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres")
    private String nome;

    @Column(name = "nome_africano", length = 50)
    @Size(max = 50, message = "O nome africano deve ter no máximo 50 caracteres")
    private String nomeAfricano;

    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "A descrição do Orixá é obrigatória")
    private String descricao;

    @Column(name = "cor_primaria", length = 7)
    @Size(max = 7, message = "A cor primária deve ser um código hexadecimal válido (#FFFFFF)")
    private String corPrimaria;

    @Column(name = "cor_secundaria", length = 7)
    @Size(max = 7, message = "A cor secundária deve ser um código hexadecimal válido (#FFFFFF)")
    private String corSecundaria;

    @Column(name = "dia_semana", length = 20)
    @Size(max = 20, message = "O dia da semana deve ter no máximo 20 caracteres")
    private String diaSemana;

    @Column(name = "elemento", length = 30)
    @Size(max = 30, message = "O elemento deve ter no máximo 30 caracteres")
    private String elemento;

    @Column(name = "sincretismo", length = 100)
    @Size(max = 100, message = "O sincretismo deve ter no máximo 100 caracteres")
    private String sincretismo;

    @Column(name = "icone_representacao", length = 50)
    @Size(max = 50, message = "O ícone de representação deve ter no máximo 50 caracteres")
    private String iconeRepresentacao;

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
        return "Orixa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", nomeAfricano='" + nomeAfricano + '\'' +
                ", elemento='" + elemento + '\'' +
                ", diaSemana='" + diaSemana + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
