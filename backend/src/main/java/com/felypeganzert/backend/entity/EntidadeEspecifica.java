package com.felypeganzert.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "entidades_especificas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntidadeEspecifica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo_entidade_id", nullable = false)
    private TipoEntidade tipoEntidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linha_id")
    private Linha linha;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(columnDefinition = "TEXT")
    private String historia;

    @Column(name = "caracteristicas_especificas", columnDefinition = "TEXT")
    private String caracteristicasEspecificas;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    @Builder.Default
    private Boolean ativo = true;
}
